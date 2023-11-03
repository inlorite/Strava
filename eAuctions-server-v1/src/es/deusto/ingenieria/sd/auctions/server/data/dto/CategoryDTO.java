package es.deusto.ingenieria.sd.auctions.server.data.dto;

import java.io.Serializable;

//This class implements DTO pattern
public class CategoryDTO implements Serializable {	
	//This attribute is needed to implement the "Serializable" interface.
	private static final long serialVersionUID = 1L;	
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Override
	public String toString() {
		return this.name;
	}
}