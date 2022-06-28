package it.polito.tdp.genes.model;

import java.util.ArrayList;
import java.util.List;

public class Studioso {

	private int id;
	private List<Genes> geni;
	public Studioso(int id) {
		super();
		this.id = id;
		this.geni = new ArrayList<>();
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public List<Genes> getGeni() {
		return geni;
	}
	public void setGeni(List<Genes> geni) {
		this.geni = geni;
	}
	
	
	
	
	
}
