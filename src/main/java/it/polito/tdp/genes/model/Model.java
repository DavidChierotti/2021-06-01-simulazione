package it.polito.tdp.genes.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.genes.db.GenesDao;

public class Model {
	
	private GenesDao dao;
	private SimpleWeightedGraph<Genes,DefaultWeightedEdge> grafo;
	private Map<String,Genes> geni;
	
	public Model() {
		dao=new GenesDao();
		geni=new HashMap<>();
		for(Genes g:dao.getAllGenes()) {
			geni.put(g.getGeneId(), g);
		}
	}
	
	
	public void creaGrafo() {
		grafo=new SimpleWeightedGraph<Genes,DefaultWeightedEdge> (DefaultWeightedEdge.class);
		
		Graphs.addAllVertices(grafo,dao.getVertici(geni));
		
		System.out.print(grafo.vertexSet().size());
		Map<String,Genes> essentialGeni=new HashMap<>() ;
		for(Genes g:dao.getVertici(geni)) {
			essentialGeni.put(g.getGeneId(), g);
		}
			
		
		List<Adiacenza> lista=dao.getArchi(essentialGeni);
		Collections.sort(lista);
		for(Adiacenza a:lista) {
			Graphs.addEdge(grafo,geni.get(a.getG1()),geni.get(a.getG2()),a.getPeso());
		}
		System.out.print(grafo.edgeSet().size());
		
		
	}
	
	public int nVert() {
		return grafo.vertexSet().size();
	}
	
	public int nArchi() {
		return grafo.edgeSet().size();
	}
	
	public List<Genes> geni(){
		List<Genes> geni=new ArrayList<>(grafo.vertexSet());
		Collections.sort(geni);
		return geni;
	}
	
	public List<Adiacente> adiacenti(Genes g){
		List<Adiacente> ritorno=new ArrayList<>();
		for(Genes gg:Graphs.neighborListOf(grafo,g)) {
			DefaultWeightedEdge e=grafo.getEdge(g, gg);
			Adiacente a=new Adiacente(gg,grafo.getEdgeWeight(e));
			ritorno.add(a);
		}
		
		Collections.sort(ritorno);
		return ritorno;
		
	}
	
	public String simula(Genes g,int n) {
		Simulator sim=new Simulator(n,grafo,geni,g);
		sim.generaStudiosi();
		sim.generaEventi();
		sim.run();
		String s=new String();
		Map<Genes,Integer> stat=sim.getFine();
		for(Genes gg:stat.keySet()) {
			s+="\n"+gg.toString()+" numero studiosi: "+stat.get(gg);
		}
		return s;
	}
	
	
}
