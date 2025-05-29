package es.ucm.fdi.ici.c2324.practica3.grupo03.ghost;

import java.io.File;
import java.util.Collection;

import es.ucm.fdi.gaia.jcolibri.cbraplications.StandardCBRApplication;
import es.ucm.fdi.gaia.jcolibri.cbrcore.Attribute;
import es.ucm.fdi.gaia.jcolibri.cbrcore.CBRCase;
import es.ucm.fdi.gaia.jcolibri.cbrcore.CBRCaseBase;
import es.ucm.fdi.gaia.jcolibri.cbrcore.CBRQuery;
import es.ucm.fdi.gaia.jcolibri.exception.ExecutionException;
import es.ucm.fdi.gaia.jcolibri.method.retrieve.RetrievalResult;
import es.ucm.fdi.gaia.jcolibri.method.retrieve.NNretrieval.NNConfig;
import es.ucm.fdi.gaia.jcolibri.method.retrieve.NNretrieval.NNScoringMethod;
import es.ucm.fdi.gaia.jcolibri.method.retrieve.NNretrieval.similarity.local.Equal;
import es.ucm.fdi.gaia.jcolibri.method.retrieve.NNretrieval.similarity.local.Interval;
import es.ucm.fdi.gaia.jcolibri.method.retrieve.selection.SelectCases;
import es.ucm.fdi.gaia.jcolibri.util.FileIO;

import es.ucm.fdi.ici.c2324.practica3.grupo03.CBRengine.Average;
import es.ucm.fdi.ici.c2324.practica3.grupo03.CBRengine.CachedLinearCaseBase;
import es.ucm.fdi.ici.c2324.practica3.grupo03.CBRengine.CustomPlainTextConnector;
import pacman.game.Constants.MOVE;

public class GhostCBRengine implements StandardCBRApplication {

	private String opponent;
	private MOVE action;
	private GhostStorageManager storageManager;
	CustomPlainTextConnector connectorteam;
	

	CBRCaseBase caseTeam;
	NNConfig simConfig;
	
	
	final static String TEAM = "grupo03";  //Cuidado!! poner el grupo aquÃ­
	
	//la base de datos de cada equipo
	 String TEAM_CONNECTOR_FILE_PATH = "es/ucm/fdi/ici/c2324/practica3/grupo03/ghost/plaintextconfig.xml";
	 String TEAM_CASE_BASE_PATH = "cbrdata"+File.separator+opponent+File.separator+"ghost"+File.separator;

	
	public GhostCBRengine(GhostStorageManager storageManager)
	{
		this.storageManager = storageManager;
	}
	
	public void setOpponent(String opponent) {
		this.opponent = opponent;
	}
	
	@Override
	public void configure() throws ExecutionException {
		connectorteam = new CustomPlainTextConnector();
		caseTeam = new CachedLinearCaseBase();
		connectorteam.initFromXMLfile(FileIO.findFile(TEAM_CONNECTOR_FILE_PATH));
		connectorteam.setCaseBaseFile(TEAM_CASE_BASE_PATH, opponent+".csv");
	
		
		this.storageManager.setCaseBase(caseTeam);
		
		simConfig = new NNConfig();
		simConfig.setDescriptionSimFunction(new Average());
		//distancias ghost
		Attribute distGLEFT = new Attribute("distanciaGhostLEFT",GhostDescription.class);
		simConfig.addMapping(distGLEFT, new Interval(650));
		simConfig.setWeight(distGLEFT, 0.025);

		Attribute distGRIGHT = new Attribute("distanciaGhostRIGHT",GhostDescription.class);
		simConfig.addMapping(distGRIGHT, new Interval(650));
		simConfig.setWeight(distGRIGHT, 0.025);
		
		Attribute distGUP = new Attribute("distanciaGhostUP",GhostDescription.class);
		simConfig.addMapping(distGUP, new Interval(650));
		simConfig.setWeight(distGUP, 0.025);
		
		Attribute distGDOWN = new Attribute("distanciaGhostDOWN",GhostDescription.class);
		simConfig.addMapping(distGDOWN, new Interval(650));
		simConfig.setWeight(distGDOWN, 0.025);
		
		//TIEMPOS COMESTIBLES 
		Attribute tComestible = new Attribute("tiempoComestible",GhostDescription.class);
		simConfig.addMapping(tComestible, new Interval(250));
		simConfig.setWeight(tComestible, 0.3);
		
		//Distancia PP
		Attribute distPPLEFT = new Attribute("distanciaPPLEFT",GhostDescription.class);
		simConfig.addMapping(distPPLEFT, new Interval(650));
		simConfig.setWeight(distPPLEFT, 0.05);
		Attribute distPPRight =new Attribute("distanciaPPRIGHT",GhostDescription.class);
		simConfig.addMapping(distPPRight, new Interval(650));
		simConfig.setWeight(distPPRight, 0.05);
		Attribute distPPUp =new Attribute("distanciaPPUP",GhostDescription.class);
		simConfig.addMapping(distPPUp, new Interval(650));
		simConfig.setWeight(distPPUp, 0.05);
		Attribute distPPDown =new Attribute("distanciaPPDOWN",GhostDescription.class);
		simConfig.addMapping(distPPDown, new Interval(650));
		simConfig.setWeight(distPPDown, 0.05);
		
		//Distancia pacman
		Attribute distanciaPacmanLEFT =new Attribute("distanciaPacmanLEFT",GhostDescription.class);
		simConfig.addMapping(distanciaPacmanLEFT, new Interval(650));
		simConfig.setWeight(distanciaPacmanLEFT, 0.1);
		Attribute distanciaPacmanRIGHT =new Attribute("distanciaPacmanRIGHT",GhostDescription.class);
		simConfig.addMapping(distanciaPacmanRIGHT, new Interval(650));
		simConfig.setWeight(distanciaPacmanRIGHT, 0.1);
		Attribute distanciaPacmanUP =new Attribute("distanciaPacmanUP",GhostDescription.class);
		simConfig.addMapping(distanciaPacmanUP, new Interval(650));
		simConfig.setWeight(distanciaPacmanUP, 0.1);
		Attribute distanciaPacmanDOWN =new Attribute("distanciaPacmanDOWN",GhostDescription.class);
		simConfig.addMapping(distanciaPacmanDOWN, new Interval(650));
		simConfig.setWeight(distanciaPacmanDOWN, 0.1);
		// 0.4 distanciaPacman+0.1distanciaGhost+0.2distanciapp+0.3tiempocomestible;		
	}

	@Override
	public CBRCaseBase preCycle() throws ExecutionException {
		caseTeam.init(connectorteam);
		return caseTeam;
	}

	@Override
	public void cycle(CBRQuery query) throws ExecutionException {
		RetrievalResult masSimilar=null;
		boolean esSimilar=false;
		if(caseTeam.getCases().isEmpty()) {
			this.action = MOVE.NEUTRAL;
		}
		else {
			//Compute retrieve
			Collection<RetrievalResult> eval = NNScoringMethod.evaluateSimilarity(caseTeam.getCases(), query, simConfig);
			//Compute reuse
			this.action = reuse(eval,masSimilar);
			if(masSimilar!=null) {
				esSimilar=masSimilar.getEval()>0.95;
			}else {
				esSimilar=false;
			}
			
		}
		//Compute revise & retain
		CBRCase newCase = createNewCase(query);
		//Nos sirve para si guardamos el caso o no
		this.storageManager.reviseAndRetain(newCase,esSimilar);
		
	}

	private MOVE reuse(Collection<RetrievalResult> eval,RetrievalResult masSimilar)
	{
		//Cogemos los 3 mejores casos
		Collection<RetrievalResult> top3 = SelectCases.selectTopKRR(eval, 3);
		if(!top3.isEmpty())
			masSimilar=(RetrievalResult) top3.toArray()[0];
			
		//para saber si son buenos los casos
		double puntuacion=-1;
		//calculamos la solución teniendo en cuenta el score y la similitud
		MOVE action = calcularSolucion(top3,puntuacion);
		// si los casos seleccionados son muy malos puntuacion negativa(mal score) hacemos un movimientoa aleatorio
		if(puntuacion<0) {
			int index = (int)Math.floor(Math.random()*4);
			if(MOVE.values()[index]==action) 
				index= (index+1)%4;
			action = MOVE.values()[index];
		}
		return action;
	}
	
	
	

	private MOVE calcularSolucion(Collection<RetrievalResult> top3,double puntuacion) {
	 double maxSimilarityProduct = Double.NEGATIVE_INFINITY;
	    MOVE selectedAction = MOVE.NEUTRAL;
	    if(!top3.isEmpty()) {
		    for (RetrievalResult result : top3) {
		        // Obtener la similitud y el puntaje del caso actual
		        double similarity = result.getEval();
		        GhostResult result2 = (GhostResult) result.get_case().getResult();
				double score = result2.getScore();
	
		        // Calcular el producto de similitud y puntaje
		        double similarityProduct = similarity * score;
		        //Si tienen la misma solución le sumo el producto
		        if(selectedAction.equals(result2)) {
		        	maxSimilarityProduct +=similarityProduct;
		        }
		        // Actualizar la acción seleccionada si encontramos un producto mayor
		        if (similarityProduct > maxSimilarityProduct) {
		            maxSimilarityProduct = similarityProduct;
		            selectedAction = ((GhostSolution) result.get_case().getSolution()).getAction();
		        }
		        puntuacion=maxSimilarityProduct;
		       
		    }
	    }else {// No hay casos en la BBDD
	    	int index = (int)Math.floor(Math.random()*4);
			selectedAction = MOVE.values()[index];
	    }
	    // Devolver la acción seleccionada
	    return selectedAction;
	}

	/**
	 * Creates a new case using the query as description, 
	 * storing the action into the solution and 
	 * setting the proper id number
	 */
	private CBRCase createNewCase(CBRQuery query) {
		CBRCase newCase = new CBRCase();
		GhostDescription newDescription = (GhostDescription) query.getDescription();
		GhostResult newResult = new GhostResult();
		GhostSolution newSolution = new GhostSolution();
		int newId = this.caseTeam.getCases().size();
		newId+= storageManager.getPendingCases();
		newDescription.setId(newId);
		newResult.setId(newId);
		newSolution.setId(newId);
		newSolution.setAction(this.action);
		newCase.setDescription(newDescription);
		newCase.setResult(newResult);
		newCase.setSolution(newSolution);
		return newCase;
	}
	
	public MOVE getSolution() {
		return this.action;
	}

	@Override
	public void postCycle() throws ExecutionException {
		this.storageManager.close();
		this.caseTeam.close();
	}

}
