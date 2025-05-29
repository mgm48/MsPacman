package es.ucm.fdi.ici.c2324.practica3.grupo03.ghost;

import es.ucm.fdi.gaia.jcolibri.cbrcore.Attribute;
import es.ucm.fdi.gaia.jcolibri.cbrcore.CaseComponent;

public class GhostResult implements CaseComponent {

	Integer id;
	Double score;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Double getScore() {
		return score;
	}

	public void setScore(Double score) {
		this.score = score;
	}

	@Override
	public Attribute getIdAttribute() {
		return new Attribute("id", GhostResult.class);
	}
	
	@Override
	public String toString() {
		return "GhostResult [id=" + id + ", score=" + score + "]";
	} 
	
	

}
