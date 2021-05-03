package it.polito.tdp.artsmia.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.artsmia.db.Adiacenza;
import it.polito.tdp.artsmia.db.ArtsmiaDAO;

public class Model {
 
	private Graph<ArtObject,DefaultWeightedEdge> grafo;
	private ArtsmiaDAO dao;
	private Map<Integer, ArtObject> idMap;
	
	
	public Model() {
		dao= new ArtsmiaDAO();
		idMap= new HashMap<Integer,ArtObject>();
	}
	
	public void creaGrafo() {
		grafo= new SimpleWeightedGraph<ArtObject, DefaultWeightedEdge>(DefaultWeightedEdge.class);
		
		//aggiunta vertici
		//1. -> recupero tutti gli ArtObject dal db
		//2. li inseerisco come vertici 
		
		//List<ArtObject> vertici= dao.listObjects();
		dao.listObjects(idMap);
		Graphs.addAllVertices(grafo, idMap.values());
		
		//aggiungere gli archi 
		//APPROCCIO 1
		//doppio ciclo for sui vertici 
		// dati due vertici controllo se devono essere collegati 
		
		/*
		for(ArtObject a1: this.grafo.vertexSet()) {
			for(ArtObject a2: this.grafo.vertexSet()) {
				if(!a1.equals(a2) && !this.grafo.containsEdge(a1,a2)) {
					//devo collegare a1 ad a2?
					int peso= dao.getPeso(a1,a2);
					if(peso>0) {
						Graphs.addEdge(this.grafo, a1, a2, peso);
					}
				}
			}
		}*/    //non giunge a termine ci sono troppi vertici 
		
		//APPROCCIO 3 (2 saltato perchè intermedio e comunquetroppo lungo)
		
		for(Adiacenza a: dao.getAdiacenze()) {
			
				Graphs.addEdge(grafo, idMap.get(a.getId1()),idMap.get(a.getId2()), a.getPeso());
			
				
		}
		
		System.out.println("grafo creato!");
		System.out.println("num vertici "+grafo.vertexSet().size());
		System.out.println("numero archi "+grafo.edgeSet().size());
		
	}
}
