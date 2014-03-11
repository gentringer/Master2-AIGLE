package com.gestion.model.neo4j;

public class Departement {

	public String nom;
	public String coderegion;
	public String codedepartement;
	public String cheflieu;
	public String tnccdepartement;

	public Departement(String nom, String coderegion, String codeDepartement, String cheflieu, String tnccdepartement){
		this.nom=nom;
		this.coderegion=coderegion;
		this.codedepartement=codeDepartement;
		this.cheflieu = cheflieu;
		this.tnccdepartement = tnccdepartement;
	}
	
	
	
	

}
