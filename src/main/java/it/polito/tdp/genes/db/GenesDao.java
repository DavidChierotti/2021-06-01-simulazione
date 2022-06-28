package it.polito.tdp.genes.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import it.polito.tdp.genes.model.Adiacenza;
import it.polito.tdp.genes.model.Genes;


public class GenesDao {
	
	public List<Genes> getAllGenes(){
		String sql = "SELECT DISTINCT GeneID, Essential, Chromosome FROM Genes";
		List<Genes> result = new ArrayList<Genes>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			while (res.next()) {

				Genes genes = new Genes(res.getString("GeneID"), 
						res.getString("Essential"), 
						res.getInt("Chromosome"));
				result.add(genes);
			}
			res.close();
			st.close();
			conn.close();
			return result;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
	public List<Genes> getVertici(Map<String,Genes> geni){
		String sql = "SELECT DISTINCT(GeneID),Essential,Chromosome "
				+ "FROM genes "
				+ "WHERE Essential='Essential'";
		List<Genes> result = new ArrayList<Genes>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			while (res.next()) {

				Genes genes = new Genes(res.getString("GeneID"), 
						res.getString("Essential"), 
						res.getInt("Chromosome"));
				if(geni.get(genes.getGeneId())!=null)
				result.add(genes);
			}
			res.close();
			st.close();
			conn.close();
			return result;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
	public List<Adiacenza> getArchi(Map<String,Genes> geni){
		
		String sql = "SELECT i.GeneID1,i.GeneID2,i.Expression_Corr, t1.Chromosome AS c1,t2.Chromosome AS c2 "
				+ "FROM genes t1, genes  t2, interactions i "
				+ "WHERE  t1.GeneID<t2.GeneID   AND (t1.GeneID=i.GeneID1 AND t2.GeneID=i.GeneID2) OR (t1.GeneID=i.GeneID2 AND t2.GeneID=i.GeneID1) "
				+ " AND i.GeneID1!=i.GeneID2 AND "
				+ "t1.Essential='Essential' AND t1.Essential=t2.Essential "
				+ "GROUP BY i.GeneID1,i.GeneID2";
		List<Adiacenza> result = new ArrayList<>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			while (res.next()) {
				if(geni.containsKey(res.getString("GeneID1"))&&geni.containsKey(res.getString("GeneID2"))) {
					double peso=0;
					if(res.getInt("c1")==res.getInt("c2")&&res.getDouble("Expression_Corr")>0) {
						peso=2*res.getDouble("Expression_Corr");
					}
					else if(res.getInt("c1")==res.getInt("c2")&&res.getDouble("Expression_Corr")<0){
						peso=-2*res.getDouble("Expression_Corr");
					}
					else if(res.getInt("c1")!=res.getInt("c2")&&res.getDouble("Expression_Corr")>0) {
						peso=res.getDouble("Expression_Corr");
					}
					else {
						peso=-res.getDouble("Expression_Corr");
					}
					Adiacenza a=new Adiacenza(res.getString("GeneID1"),res.getString("GeneID2"),peso);
					result.add(a);
				}
				
			}
			res.close();
			st.close();
			conn.close();
			return result;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}


	
}
