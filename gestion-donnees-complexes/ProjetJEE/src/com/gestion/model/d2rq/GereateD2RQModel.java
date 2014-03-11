package com.gestion.model.d2rq;


import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.query.ResultSetFormatter;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.vocabulary.RDF;
import com.hp.hpl.jena.graph.query.QueryHandler;

import de.fuberlin.wiwiss.d2rq.jena.ModelD2RQ;

public class GereateD2RQModel {
	public static final String NL = System.getProperty("line.separator") ;

	public static String path2 = "/home/gentringer/universite/Master2-Aigle/" +
			"gestion-donnees-complexes/ProjetJEE/WebContent/mapping/mapping-partie2.n3";
	
	public static Model d2rqModelImpots(){
		
		Model d2rqModel = new ModelD2RQ("file:"+path2);
		System.out.println("d2rq size"+ d2rqModel.size());
		
	
		
		return d2rqModel;

	}

}
