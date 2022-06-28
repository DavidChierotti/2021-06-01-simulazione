package it.polito.tdp.genes.model;

public class Event {
	
	private Studioso s;
	private Genes g;
	private int mese;
	public Event(Studioso s, Genes g, int mese) {
		super();
		this.s = s;
		this.g = g;
		this.mese = mese;
	}
	public Studioso getS() {
		return s;
	}
	public void setS(Studioso s) {
		this.s = s;
	}
	public Genes getG() {
		return g;
	}
	public void setG(Genes g) {
		this.g = g;
	}
	public int getMese() {
		return mese;
	}
	public void setMese(int mese) {
		this.mese = mese;
	}
	
	
	

}
