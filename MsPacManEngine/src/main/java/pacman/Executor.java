package pacman;

import static pacman.game.Constants.DELAY;
import static pacman.game.Constants.INTERVAL_WAIT;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.Random;
import java.util.function.Function;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pacman.controllers.Controller;
import pacman.controllers.GhostController;
import pacman.controllers.HumanController;
import pacman.game.Constants.GHOST;
import pacman.game.Constants.MOVE;
import pacman.game.Drawable;
import pacman.game.Game;
import pacman.game.GameObserver;
import pacman.game.GameView;
import pacman.game.comms.BasicMessenger;
import pacman.game.comms.Messenger;
import pacman.game.internal.POType;
import pacman.game.util.Stats;

/**
 * This class may be used to execute the game in timed or un-timed modes, with or without
 * visuals. Competitors should implement their controllers in game.entries.ghosts and
 * game.entries.pacman respectively. The skeleton classes are already provided. The package
 * structure should not be changed (although you may create sub-packages in these packages).
 */
@SuppressWarnings("unused")
public class Executor {
    private final boolean pacmanPO;
    private final boolean ghostPO;
    private final boolean ghostsMessage;
    private final Messenger messenger;
    private final double scaleFactor;
    private final boolean setDaemon;
    private final boolean visuals;
    private final int tickLimit;
    private final int timeLimit;
    private final POType poType;
    private final int sightLimit;
    private final Random rnd = new Random();
    private final Function<Game, String> peek;
    private final Logger logger = LoggerFactory.getLogger(Executor.class);
	private boolean pacmanPOvisual;
	private boolean ghostsPOvisual;
	private static String VERSION = "4.2.0 (ICI 23-24 stable version)";
	
	private static int ERROR_LOG_LEVEL = 2; //0: no log, 1: error+debug messages, 2: error+debug messages + stack trace

    public static class Builder {
        private boolean pacmanPO = false;
        private boolean ghostPO = false;
        private boolean ghostsMessage = false;
        private Messenger messenger = new BasicMessenger();
        private double scaleFactor = 3.0d;
        private boolean setDaemon = false;
        private boolean visuals = false;
        private int tickLimit = 4000;
        private int timeLimit = 40;
        private POType poType = POType.LOS;
        private int sightLimit = 50;
        private Function<Game, String> peek = null;
		private boolean pacmanPOvisual;
		private boolean ghostsPOvisual;
		
		
        public Builder setPacmanPO(boolean po) {
            this.pacmanPO = po;
            return this;
        }

        public Builder setGhostPO(boolean po) {
            this.ghostPO = po;
            return this;
        }

        public Builder setGhostsMessage(boolean canMessage) {
            this.ghostsMessage = canMessage;
            if (canMessage) {
                messenger = new BasicMessenger();
            } else {
                messenger = null;
            }
            return this;
        }

        public Builder setMessenger(Messenger messenger) {
            this.ghostsMessage = true;
            this.messenger = messenger;
            return this;
        }

        public Builder setScaleFactor(double scaleFactor) {
            this.scaleFactor = scaleFactor;
            return this;
        }

        public Builder setGraphicsDaemon(boolean daemon) {
            this.setDaemon = daemon;
            return this;
        }

        public Builder setVisual(boolean visual) {
            this.visuals = visual;
            return this;
        }

        public Builder setTickLimit(int tickLimit) {
            this.tickLimit = tickLimit;
            return this;
        }

        public Builder setTimeLimit(int timeLimit) {
            this.timeLimit = timeLimit;
            return this;
        }

        public Builder setPOType(POType poType) {
            this.poType = poType;
            return this;
        }

        public Builder setSightLimit(int sightLimit) {
            this.sightLimit = sightLimit;
            return this;
        }

        public Builder setPeek(Function<Game, String> peek){
            this.peek = peek;
            return this;
        }

        public Executor build() {
        	logInfo("MsPacMan Engine - Ingenierķa de Comportamientos Inteligentes. Version "+Executor.VERSION);
            return new Executor(pacmanPO, ghostPO, ghostsMessage, messenger, scaleFactor, setDaemon, visuals, tickLimit, timeLimit, poType, sightLimit, peek, pacmanPOvisual, ghostsPOvisual);
        }

		public Builder setPacmanPOvisual(boolean b) {
			this.pacmanPOvisual = b;
			return this;
		}
		
		public Builder setGhostsPOvisual(boolean b) {
			this.ghostsPOvisual = b;
			return this;
		}
    }

    private Executor(
            boolean pacmanPO,
            boolean ghostPO,
            boolean ghostsMessage,
            Messenger messenger,
            double scaleFactor,
            boolean setDaemon,
            boolean visuals,
            int tickLimit,
            int timeLimit,
            POType poType,
            int sightLimit,
            Function<Game, String> peek,
            boolean pacmanPOvisual,
            boolean ghostsPOvisual
        ) {
        this.pacmanPO = pacmanPO;
        this.ghostPO = ghostPO;
        this.ghostsMessage = ghostsMessage;
        this.messenger = messenger;
        this.scaleFactor = scaleFactor;
        this.setDaemon = setDaemon;
        this.visuals = visuals;
        this.tickLimit = tickLimit;
        this.timeLimit = timeLimit;
        this.poType = poType;
        this.sightLimit = sightLimit;
        this.peek = peek;
        this.pacmanPOvisual = pacmanPOvisual;
        this.ghostsPOvisual = ghostsPOvisual;
    }

    private static void writeStat(FileWriter writer, Stats stat, int i) throws IOException {
        writer.write(String.format("%s, %d, %f, %f, %f, %f, %d, %f, %f, %f, %d%n",
                stat.getDescription(),
                i,
                stat.getAverage(),
                stat.getSum(),
                stat.getSumsq(),
                stat.getStandardDeviation(),
                stat.getN(),
                stat.getMin(),
                stat.getMax(),
                stat.getStandardError(),
                stat.getMsTaken()));
    }

    //save file for replays
    public static void saveToFile(String data, String name, boolean append) {
        try (FileOutputStream outS = new FileOutputStream(name, append)) {
            PrintWriter pw = new PrintWriter(outS);

            pw.println(data);
            pw.flush();
            outS.close();

        } catch (IOException e) {
            System.out.println("Could not save data!");
        }
    }

    //load a replay
    private static ArrayList<String> loadReplay(String fileName) {
        ArrayList<String> replay = new ArrayList<String>();

        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(fileName)))) {
            String input = br.readLine();

            while (input != null) {
                if (!input.equals("")) {
                    replay.add(input);
                }

                input = br.readLine();
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }

        return replay;
    }

    /**
     * For running multiple games without visuals. This is useful to get a good idea of how well a controller plays
     * against a chosen opponent: the random nature of the game means that performance can vary from game to game.
     * Running many games and looking at the average score (and standard deviation/error) helps to get a better
     * idea of how well the controller is likely to do in the competition.
     *
     * @param pacManController The Pac-Man controller
     * @param ghostController  The Ghosts controller
     * @param trials           The number of trials to be executed
     * @param description      Description for the stats
     * @return Stats[] containing the scores in index 0 and the ticks in position 1
     */
    public Stats[] runExperiment(Controller<MOVE> pacManController, GhostController ghostController, int trials, String description) {
        Stats stats = new Stats(description);
        Stats ticks = new Stats(description + " Ticks");
        GhostController ghostControllerCopy = ghostController.copy(ghostPO);
        Game game;


        Long startTime = System.currentTimeMillis();
        for (int i = 0; i < trials; ) {
            try {
                game = setupGame();
                precompute(pacManController, ghostControllerCopy);
                while (!game.gameOver()) {
                    if (tickLimit != -1 && tickLimit < game.getTotalTime()) {
                        break;
                    }
                    handlePeek(game);
                    game.advanceGame(
                            pacManController.getMove(getPacmanCopy(game), System.currentTimeMillis() + timeLimit),
                            ghostControllerCopy.getMove(getGhostsCopy(game), System.currentTimeMillis() + timeLimit));
                }
                stats.add(game.getScore());
                ticks.add(game.getCurrentLevelTime());
                i++;
                postcompute(pacManController, ghostController);
                System.out.println("Game finished: " + i + "   " + description);
            } catch (Exception e) {
            	System.err.println("ERROR runExperiment: "+pacManController.getClass().getCanonicalName() + " vs "+ghostControllerCopy.getClass().getCanonicalName());
                e.printStackTrace();
            }
        }
        long timeTaken = System.currentTimeMillis() - startTime;
        stats.setMsTaken(timeTaken);
        ticks.setMsTaken(timeTaken);

        
        return new Stats[]{stats, ticks};
    }

    private Game setupGame() {
        return (this.ghostsMessage) ? new Game(rnd.nextLong(), 0, messenger.copy(), poType, sightLimit) : new Game(rnd.nextLong(), 0, null, poType, sightLimit);
    }

    private void handlePeek(Game game){
        if(peek != null) logger.info(peek.apply(game));
    }

    public Stats[] runExperimentTicks(Controller<MOVE> pacManController, GhostController ghostController, int trials, String description) {
        Stats stats = new Stats(description);
        Stats ticks = new Stats(description);

        GhostController ghostControllerCopy = ghostController.copy(ghostPO);
        Game game;

        Long startTime = System.currentTimeMillis();
        for (int i = 0; i < trials; i++) {
            game = setupGame();
            precompute(pacManController, ghostController);

            while (!game.gameOver()) {
                handlePeek(game);
                game.advanceGame(
                        pacManController.getMove(getPacmanCopy(game), System.currentTimeMillis() + timeLimit),
                        ghostControllerCopy.getMove(getGhostsCopy(game), System.currentTimeMillis() + timeLimit));
            }
            stats.add(game.getScore());
            ticks.add(game.getTotalTime());
            postcompute(pacManController, ghostController);

        }
        stats.setMsTaken(System.currentTimeMillis() - startTime);
        ticks.setMsTaken(System.currentTimeMillis() - startTime);


        return new Stats[]{stats, ticks};
    }

    /**
     * Run a game in asynchronous mode: the game waits until a move is returned. In order to slow thing down in case
     * the controllers return very quickly, a time limit can be used. If fasted gameplay is required, this delay
     * should be put as 0.
     *
     * @param pacManController The Pac-Man controller
     * @param ghostController  The Ghosts controller
     * @param delay            The delay between time-steps
     */
    public int runGame(Controller<MOVE> pacManController, GhostController ghostController, int delay) {
        Game game = setupGame();

        precompute(pacManController, ghostController);
        
        GameView gv = (visuals) ? setupGameView(pacManController, game) : null;

        GhostController ghostControllerCopy = ghostController.copy(ghostPO);

        while (!game.gameOver()) {
            if (tickLimit != -1 && tickLimit < game.getTotalTime()) {
                break;
            }
            handlePeek(game);
            game.advanceGame(
                    pacManController.getMove(getPacmanCopy(game), System.currentTimeMillis() + timeLimit),
                    ghostControllerCopy.getMove(getGhostsCopy(game), System.currentTimeMillis() + timeLimit));

            try {
                Thread.sleep(delay);
            } catch (Exception e) {
            }

            if (visuals) {
                gv.repaint();
            }
        }
        System.out.println(game.getScore());
        
        postcompute(pacManController, ghostController);
        
        return game.getScore();
    }
    
    /**
     * Run a game in asynchronous mode: the game waits until a move is returned. In order to slow thing down in case
     * the controllers return very quickly, a time limit can be used. If fasted gameplay is required, this delay
     * should be put as 0.
     *
     * @param pacManController The Pac-Man controller
     * @param ghostController  The Ghosts controller
     * @param delay            The delay between time-steps
     */
    public int runGame(Controller<MOVE> pacManController, GhostController ghostController, int delay, String title) {
        Game game = setupGame();

        precompute(pacManController, ghostController);
        
        GameView gv = (visuals) ? setupGameView(pacManController, game, title) : null;

        GhostController ghostControllerCopy = ghostController.copy(ghostPO);

        while (!game.gameOver()) {
            if (tickLimit != -1 && tickLimit < game.getTotalTime()) {
                break;
            }
            handlePeek(game);
            game.advanceGame(
                    pacManController.getMove(getPacmanCopy(game), System.currentTimeMillis() + timeLimit),
                    ghostControllerCopy.getMove(getGhostsCopy(game), System.currentTimeMillis() + timeLimit));

            try {
                Thread.sleep(delay);
            } catch (Exception e) {
            }

            if (visuals) {
                gv.repaint();
            }
        }
        System.out.println(game.getScore());
        
        postcompute(pacManController, ghostController);
        
        return game.getScore();
    }

    private void postcompute(Controller<MOVE> pacManController, GhostController ghostController) {
		pacManController.postCompute();
		ghostController.postCompute();
	}

	private void precompute(Controller<MOVE> pacManController, GhostController ghostController) {
		String ghostName = ghostController.getClass().getCanonicalName();
		String pacManName = pacManController.getClass().getCanonicalName();
		long initTime = System.currentTimeMillis();
		pacManController.preCompute(ghostName);
		logInfo("Precompute "+ghostName+": "+(System.currentTimeMillis()-initTime)+"ms");
		initTime = System.currentTimeMillis();
		ghostController.preCompute(pacManName);
		logInfo("Precompute "+pacManName+": "+(System.currentTimeMillis()-initTime)+"ms");

	}

	private Game getPacmanCopy(Game game) {
        return game.copy((pacmanPO) ? Game.PACMAN : Game.CLONE);
    }
    
    private Game getGhostsCopy(Game game) {
    	return game.copy((ghostPO) ? Game.ANY_GHOST : Game.CLONE);
       
    }

    private GameView setupGameView(Controller<MOVE> pacManController, Game game) {
        GameView gv;
        gv = new GameView(game, setDaemon);
        gv.setScaleFactor(scaleFactor);
        gv.showGame("");
        if(pacmanPOvisual) gv.setPacManPO(this.pacmanPO);
        if(ghostsPOvisual) gv.setGhostPO(this.ghostPO);
        if (pacManController instanceof HumanController) {
            gv.setFocusable(true);
            gv.requestFocus();
            gv.setPacManPO(this.pacmanPO);
            gv.addKeyListener(((HumanController) pacManController).getKeyboardInput());
        }

        if (pacManController instanceof Drawable) {
            gv.addDrawable((Drawable) pacManController);
        }
        return gv;
    }

    private GameView setupGameView(Controller<MOVE> pacManController, Game game, String title) {
        GameView gv;
        gv = new GameView(game, setDaemon);
        gv.setScaleFactor(scaleFactor);
        gv.showGame(title);
        if(pacmanPOvisual) gv.setPacManPO(this.pacmanPO);
        if(ghostsPOvisual) gv.setGhostPO(this.ghostPO);
        if (pacManController instanceof HumanController) {
            gv.setFocusable(true);
            gv.requestFocus();
            gv.setPacManPO(this.pacmanPO);
            gv.addKeyListener(((HumanController) pacManController).getKeyboardInput());
        }

        if (pacManController instanceof Drawable) {
            gv.addDrawable((Drawable) pacManController);
        }
        return gv;
    }   
    
    
    /**
     * Run the game with time limit (asynchronous mode).
     * Can be played with and without visual display of game states.
     *
     * @param pacManController The Pac-Man controller
     * @param ghostController  The Ghosts controller
     */
    public void runGameTimed(Controller<MOVE> pacManController, GhostController ghostController) {
        Game game = setupGame();

        GameView gv = (visuals) ? setupGameView(pacManController, game) : null;
        GhostController ghostControllerCopy = ghostController.copy(ghostPO);

        precompute(pacManController, ghostController);
        
        new Thread(pacManController).start();
        new Thread(ghostControllerCopy).start();

        while (!game.gameOver()) {
            if (tickLimit != -1 && tickLimit < game.getTotalTime()) {
                break;
            }
            handlePeek(game);
            pacManController.update(getPacmanCopy(game), System.currentTimeMillis() + DELAY);
            ghostControllerCopy.update(getGhostsCopy(game), System.currentTimeMillis() + DELAY);

            try {
                Thread.sleep(DELAY);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            game.advanceGame(pacManController.getMove(), ghostControllerCopy.getMove());

            if (visuals) {
                gv.repaint();
            }
        }

        pacManController.terminate();
        ghostControllerCopy.terminate();
        
        postcompute(pacManController, ghostController);

    }

    /**
     * Run the game in asynchronous mode but proceed as soon as both controllers replied. The time limit still applies so
     * so the game will proceed after 40ms regardless of whether the controllers managed to calculate a turn.
     *
     * @param pacManController The Pac-Man controller
     * @param ghostController  The Ghosts controller
     * @param fixedTime        Whether or not to wait until 40ms are up even if both controllers already responded
     * @param desc             the description for the stats
     * @return Stat score achieved by Ms. Pac-Man
     */
    public Stats runGameTimedSpeedOptimised(Controller<MOVE> pacManController, GhostController ghostController, boolean fixedTime, String desc) {
        Game game = setupGame();

        GameView gv = (visuals) ? setupGameView(pacManController, game) : null;
        GhostController ghostControllerCopy = ghostController.copy(ghostPO);
        Stats stats = new Stats(desc);

        precompute(pacManController, ghostController);

        new Thread(pacManController).start();
        new Thread(ghostControllerCopy).start();
        while (!game.gameOver()) {
            if (tickLimit != -1 && tickLimit < game.getTotalTime()) {
                break;
            }
            handlePeek(game);
            pacManController.update(getPacmanCopy(game), System.currentTimeMillis() + DELAY);
            ghostControllerCopy.update(getGhostsCopy(game), System.currentTimeMillis() + DELAY);

            try {
                long waited = DELAY / INTERVAL_WAIT;

                for (int j = 0; j < DELAY / INTERVAL_WAIT; j++) {
                    Thread.sleep(INTERVAL_WAIT);

                    if (pacManController.hasComputed() && ghostControllerCopy.hasComputed()) {
                        waited = j;
                        break;
                    }
                }

                if (fixedTime) {
                    Thread.sleep(((DELAY / INTERVAL_WAIT) - waited) * INTERVAL_WAIT);
                }

                game.advanceGame(pacManController.getMove(), ghostControllerCopy.getMove());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if (visuals) {
                gv.repaint();
            }
        }

        pacManController.terminate();
        ghostControllerCopy.terminate();
        stats.add(game.getScore());
        
        postcompute(pacManController, ghostController);

        return stats;
    }
    
    
    /**
     * Run the game in asynchronous mode but proceed as soon as both controllers replied. The time limit still applies so
     * so the game will proceed after 40ms regardless of whether the controllers managed to calculate a turn.
     *
     * @param pacManController The Pac-Man controller
     * @param ghostController  The Ghosts controller
     * @param fixedTime        Whether or not to wait until 40ms are up even if both controllers already responded
     * @param desc             the description for the stats
     * @return Stat score achieved by Ms. Pac-Man
     */
    public Stats runGameTimeFixed(Controller<MOVE> pacManController, GhostController ghostController, int time, String desc) {
        Game game = setupGame();

        GameView gv = (visuals) ? setupGameView(pacManController, game) : null;
        GhostController ghostControllerCopy = ghostController.copy(ghostPO);
        Stats stats = new Stats(desc);

        precompute(pacManController, ghostController);
        
        long gDELAY = time;
        long gINTERVAL_WAIT = gDELAY / 10;
        
        new Thread(pacManController).start();
        new Thread(ghostControllerCopy).start();
        while (!game.gameOver()) {
            if (tickLimit != -1 && tickLimit < game.getTotalTime()) {
                break;
            }
            handlePeek(game);
            pacManController.update(getPacmanCopy(game), System.currentTimeMillis() + DELAY);
            ghostControllerCopy.update(getGhostsCopy(game), System.currentTimeMillis() + DELAY);

            try {
                long waited = gDELAY / gINTERVAL_WAIT;

                for (int j = 0; j < gDELAY / gINTERVAL_WAIT; j++) {
                    Thread.sleep(gINTERVAL_WAIT);

                    if (pacManController.hasComputed() && ghostControllerCopy.hasComputed()) {
                        waited = j;
                        break;
                    }
                }
                game.advanceGame(pacManController.getMove(), ghostControllerCopy.getMove());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if (visuals) {
                gv.repaint();
            }
        }

        pacManController.terminate();
        ghostControllerCopy.terminate();
        stats.add(game.getScore());
        
        postcompute(pacManController, ghostController);

        return stats;
    }

    /**
     * Run a game in asynchronous mode and recorded.
     *
     * @param pacManController The Pac-Man controller
     * @param ghostController  The Ghosts controller
     * @param fileName         The file name of the file that saves the replay
     * @return Stats the statistics for the run
     */
    public Stats runGameTimedRecorded(Controller<MOVE> pacManController, GhostController ghostController, String fileName) {
        Stats stats = new Stats("");
        StringBuilder replay = new StringBuilder();

        Game game = setupGame();

        precompute(pacManController, ghostController);
        
        GameView gv = null;
        GhostController ghostControllerCopy = ghostController.copy(ghostPO);

        if (visuals) {
            gv = new GameView(game, setDaemon);
            gv.setScaleFactor(scaleFactor);
            gv.showGame("");

            if (pacManController instanceof HumanController) {
                gv.getFrame().addKeyListener(((HumanController) pacManController).getKeyboardInput());
            }

            if (pacManController instanceof Drawable) {
                gv.addDrawable((Drawable) pacManController);
            }
        }

        new Thread(pacManController).start();
        new Thread(ghostControllerCopy).start();

        while (!game.gameOver()) {
            if (tickLimit != -1 && tickLimit < game.getTotalTime()) {
                break;
            }
            handlePeek(game);
            pacManController.update(getPacmanCopy(game), System.currentTimeMillis() + DELAY);
            ghostControllerCopy.update(getGhostsCopy(game), System.currentTimeMillis() + DELAY);

            try {
                Thread.sleep(DELAY);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            game.advanceGame(pacManController.getMove(), ghostControllerCopy.getMove());

            if (visuals) {
                gv.repaint();
            }

            replay.append(game.getGameState() + "\n");
        }
        stats.add(game.getScore());

        pacManController.terminate();
        ghostControllerCopy.terminate();

        postcompute(pacManController, ghostController);

        
        saveToFile(replay.toString(), fileName, false);
        return stats;
    }

    /**
     * Replay a previously saved game.
     *
     * @param fileName The file name of the game to be played
     * @param visual   Indicates whether or not to use visuals
     */
    public void replayGame(String fileName, boolean visual) {
        ArrayList<String> timeSteps = loadReplay(fileName);

        Game game = setupGame();

        GameView gv = null;

        if (visual) {
            gv = new GameView(game, setDaemon);
            gv.setScaleFactor(scaleFactor);
            gv.showGame("");
        }

        for (int j = 0; j < timeSteps.size(); j++) {
            game.setGameState(timeSteps.get(j));

            try {
                Thread.sleep(DELAY);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (visual) {
                gv.repaint();
            }
        }
    }
    
    
    /**
     * Run a game in asynchronous mode and notifies observer: the game waits until a move is returned. In order to slow thing down in case
     * the controllers return very quickly, a time limit can be used. If fasted gameplay is required, this delay
     * should be put as 0.
     *
     * @param pacManController The Pac-Man controller
     * @param ghostController  The Ghosts controller
     * @param delay            The delay between time-steps
     */
    public int runGame(Controller<MOVE> pacManController, GhostController ghostController, int delay, GameObserver observer) {
        Game game = setupGame();

        precompute(pacManController, ghostController);
        
        GameView gv = (visuals) ? setupGameView(pacManController, game) : null;

        GhostController ghostControllerCopy = ghostController.copy(ghostPO);

        while (!game.gameOver()) {
            if (tickLimit != -1 && tickLimit < game.getTotalTime()) {
                break;
            }
            handlePeek(game);
            MOVE pacManMove = pacManController.getMove(getPacmanCopy(game), System.currentTimeMillis() + timeLimit);
            EnumMap<GHOST,MOVE> ghostsMove = ghostControllerCopy.getMove(getGhostsCopy(game), System.currentTimeMillis() + timeLimit);
            
            EnumMap<GHOST,Boolean> ghostsJunction = new EnumMap<GHOST,Boolean>(GHOST.class);
            for(GHOST g: GHOST.values())
            	ghostsJunction.put(g, game.isJunction(game.getGhostCurrentNodeIndex(g)));
            observer.ghostsMove(ghostsMove, ghostsJunction);
            
            observer.pacManMove(pacManMove, game.isJunction(game.getPacmanCurrentNodeIndex()));
            
            game.advanceGame(pacManMove, ghostsMove);

            try {
                Thread.sleep(delay);
            } catch (Exception e) {
            }

            if (visuals) {
                gv.repaint();
            }
        }
        System.out.println(game.getScore());
        
        postcompute(pacManController, ghostController);
        
        return game.getScore();
    }
    
    /**
     * Run a game in asynchronous mode and notifies observer: the game waits until a move is returned. In order to slow thing down in case
     * the controllers return very quickly, a time limit can be used. If fasted gameplay is required, this delay
     * should be put as 0.
     *
     * @param pacManController The Pac-Man controller
     * @param ghostController  The Ghosts controller
     * @param delay            The delay between time-steps
     */
    public int runGame(Controller<MOVE> pacManController, GhostController ghostController, int delay, GameObserver observer, String title) {
        Game game = setupGame();

        precompute(pacManController, ghostController);
        
        GameView gv = (visuals) ? setupGameView(pacManController, game, title) : null;

        GhostController ghostControllerCopy = ghostController.copy(ghostPO);

        while (!game.gameOver()) {
            if (tickLimit != -1 && tickLimit < game.getTotalTime()) {
                break;
            }
            handlePeek(game);
            MOVE pacManMove = pacManController.getMove(getPacmanCopy(game), System.currentTimeMillis() + timeLimit);
            EnumMap<GHOST,MOVE> ghostsMove = ghostControllerCopy.getMove(getGhostsCopy(game), System.currentTimeMillis() + timeLimit);
            
            EnumMap<GHOST,Boolean> ghostsJunction = new EnumMap<GHOST,Boolean>(GHOST.class);
            for(GHOST g: GHOST.values())
            	ghostsJunction.put(g, game.isJunction(game.getGhostCurrentNodeIndex(g)));
            observer.ghostsMove(ghostsMove, ghostsJunction);
            
            observer.pacManMove(pacManMove, game.isJunction(game.getPacmanCurrentNodeIndex()));
            
            game.advanceGame(pacManMove, ghostsMove);

            try {
                Thread.sleep(delay);
            } catch (Exception e) {
            }

            if (visuals) {
                gv.repaint();
            }
        }
        System.out.println(game.getScore());
        
        postcompute(pacManController, ghostController);
        
        return game.getScore();
    }
    
    public static void logError(String msg, Exception e)
    {
    	if(Executor.ERROR_LOG_LEVEL>0)
    		System.err.println(msg);
    	if(Executor.ERROR_LOG_LEVEL>1)
    		e.printStackTrace(System.err);	
    }
    
    
    public static void logInfo(String msg)
    {
    	if(Executor.ERROR_LOG_LEVEL>0)
    		System.out.println(msg);
    }
    
}