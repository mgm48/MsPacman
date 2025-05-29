package es.ucm.fdi.ici.c2324.practica4.grupo03;

import java.io.File;
import java.util.Iterator;

import jess.Fact;
import jess.JessException;
import jess.Rete;

public class Retetest {

	public static void main(String args[]) {
		String RULES_PATH = "es"+File.separator+"ucm"+File.separator+"fdi"+File.separator+"ici"+File.separator+"c2324"+File.separator+"practica4"+File.separator+"grupo03"+File.separator+"blinkyrules.clp";
		String rulesFile = String.format("%s", RULES_PATH);
		Rete jess = new Rete();
		try {
			jess.batch(rulesFile);
			jess.reset();
			jess.run();
			Iterator<?> it = jess.listFacts();
			while(it.hasNext()){ 
			    Fact dd = (Fact)it.next();
			    System.out.println(dd.toString());
			}
		} catch (JessException e) {
			e.printStackTrace();
		}
	}
}