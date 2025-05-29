package es.ucm.fdi.ici.c2324.practica5.grupoYY.ghosts;

import java.util.HashMap;
import java.util.Map.Entry;

import es.ucm.fdi.ici.Action;
import es.ucm.fdi.ici.fuzzy.ActionSelector;

public class MaxActionSelectorghost implements ActionSelector {

	Action[] actions;
	
	public MaxActionSelectorghost(Action[] actions)
	{
		this.actions = actions;
	}
	
	@Override
	public Action selectAction(HashMap<String, Double> fuzzyOutput) {
		double max = Double.NEGATIVE_INFINITY;
		String actionName = null;
		for(Entry<String,Double> entry : fuzzyOutput.entrySet()) {
			double value = entry.getValue();
			if(value > max)
			{
				max = value;
				actionName = entry.getKey(); 
			}
		}
		
		if(actionName==null)
			return null;
		
		for(Action a : actions)
			if(a.getActionId().equals(actionName))
				return a;
		
		return null;
		
	} 
}
