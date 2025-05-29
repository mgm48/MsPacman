import es.ucm.fdi.ici.c2324.practica2.grupo03.Ghosts;
import es.ucm.fdi.ici.c2324.practica2.grupo03.MsPacMan;
import pacman.Executor;
import pacman.controllers.GhostController;
import pacman.controllers.PacmanController;

public class ExecutorTest {

    public static void main(String[] args) {
        Executor executor = new Executor.Builder()
                .setTickLimit(4000)
                .setVisual(true)
                .setScaleFactor(2.5)
                .build();

        PacmanController pacMan = new MsPacMan();
        GhostController ghosts = new Ghosts();
        
        System.out.println( 
            executor.runGame(pacMan, ghosts, 150) //last parameter defines speed
        );     
    }
	
}
