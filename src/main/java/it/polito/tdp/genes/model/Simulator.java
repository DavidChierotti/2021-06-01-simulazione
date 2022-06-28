package it.polito.tdp.genes.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;


public class Simulator {

	//INPUT
	private int n;
    private SimpleWeightedGraph<Genes,DefaultWeightedEdge> grafo;
    private Map<String,Genes> geni;
    private Genes partenza;
	
	//OUTPUT
	private Map<Genes,Integer> fine;
	
	//CODA
	private PriorityQueue<Event> queue;
	
	
	//MONDO
	private List<Studioso> studiosi;
	
	
	public Simulator(int n,SimpleWeightedGraph<Genes,DefaultWeightedEdge> grafo,Map<String,Genes> geni,Genes partenza) {
		this.n=n;
		this.grafo=grafo;
		this.geni=geni;
		this.partenza=partenza;
		this.queue=new PriorityQueue<>();
			
	}
	
	public void generaStudiosi() {
		this.studiosi=new ArrayList<>();
		for(int i=0;i<this.n;i++) {
			Studioso s=new Studioso(i);
			studiosi.add(s);
		}
	}
	
	public void generaEventi() {
		for(Studioso s:studiosi) {
			Event e=new Event(s,this.partenza,1);
		    s.getGeni().add(partenza);
		}
	}
	
	public void run(){
		while(!queue.isEmpty()) {
			Event e = queue.poll();
			processaEvento(e);
		}
		this.calcolaStatistiche();
	}

	private void processaEvento(Event e) {
		double x=Math.random();
		if(x<0.3) {
			if(e.getMese()<36) {
			Event e2=new Event(e.getS(),e.getG(),e.getMese()+1);
			studiosi.get(e.getS().getId()).getGeni().add(e2.getG());
			queue.add(e2);
			}
		}
		else {
			if(e.getMese()<36) {
				Event e2=new Event(e.getS(),this.successivo(e.getG()),e.getMese()+1);
				studiosi.get(e.getS().getId()).getGeni().add(e2.getG());
				queue.add(e2);
			}
		}
	}
	
	private void calcolaStatistiche() {
		this.fine=new HashMap<Genes,Integer>();
		for(Studioso s:studiosi) {
			//fine.put(s.getGeni().get(s.getGeni().size()-1),0);
			Genes g=s.getGeni().get(s.getGeni().size()-1);
			int c=0;
			for(Studioso ss:studiosi) {
				if(g.equals(ss.getGeni().get(ss.getGeni().size()-1))) {
					c++;
				}
			}
			fine.put(g, c);
		}
		
	}
	
	
	public Map<Genes, Integer> getFine() {
		return fine;
	}

	public void setFine(Map<Genes, Integer> fine) {
		this.fine = fine;
	}

	private Genes successivo(Genes g) {
		int tot=0;
		List<Adiacente> ritorno=new ArrayList<>();
		for(Genes gg:Graphs.neighborListOf(grafo,g)) {
			DefaultWeightedEdge e=grafo.getEdge(g, gg);
			Adiacente a=new Adiacente(gg,grafo.getEdgeWeight(e));
			tot+=grafo.getEdgeWeight(e);
			ritorno.add(a);
		}
		
		Collections.sort(ritorno);

		Map<Genes,Double> adiacenti=new HashMap<>();
		for(Adiacente gg:ritorno) {
			adiacenti.put(gg.getG(),gg.getPeso()/tot);
		}
		double x=Math.random();
          Genes successivo=new Genes(null,null,0);
          double parziale=0.0;
		for(Adiacente gg:ritorno) {
			parziale+=gg.getPeso()/tot;
			if(x<parziale&&x>=parziale-gg.getPeso()/tot) {
				successivo=gg.getG();
			}
		}
		
		return successivo;
	}
	
	
}
