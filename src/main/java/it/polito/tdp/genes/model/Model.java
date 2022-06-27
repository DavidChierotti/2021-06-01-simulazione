package it.polito.tdp.genes.model;

import java.util.HashMap;
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
		
		
		for(Adiacenza a:dao.getArchi(geni, grafo.vertexSet())) {
			Graphs.addEdge(grafo,geni.get(a.getG1()),geni.get(a.getG2()),a.getPeso());
		}
		System.out.print(grafo.edgeSet().size());
		
		
	}
	
	
}
