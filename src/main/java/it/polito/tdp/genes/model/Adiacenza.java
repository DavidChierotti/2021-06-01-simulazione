package it.polito.tdp.genes.model;

public class Adiacenza implements Comparable<Adiacenza>{
	
	private String g1;
	private String g2;
	private double peso;
	public Adiacenza(String g1, String g2, double peso) {
		super();
		this.g1 = g1;
		this.g2 = g2;
		this.peso = peso;
	}
	public String getG1() {
		return g1;
	}
	public void setG1(String g1) {
		this.g1 = g1;
	}
	public String getG2() {
		return g2;
	}
	public void setG2(String g2) {
		this.g2 = g2;
	}
	public double getPeso() {
		return peso;
	}
	public void setPeso(double peso) {
		this.peso = peso;
	}
	@Override
	public int compareTo(Adiacenza o) {
		// TODO Auto-generated method stub
		return g1.compareTo(o.getG2());
	}
	

}
