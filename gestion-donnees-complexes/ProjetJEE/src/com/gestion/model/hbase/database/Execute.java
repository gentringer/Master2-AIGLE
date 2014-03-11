package com.gestion.model.hbase.database;
// Utilisé lors de la création de la base de données

import java.util.ArrayList;

import com.gestion.model.hbase.csv.ReadCSV_Communes;


public class Execute {

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub

		final String nameImpots = "Impots";
		//ArrayList<String[]> read = ReadCSV_Impots.run();
		String[] colums = {"infosgenerales"};
		/*	CreateTable.creatTable(nameImpots,colums );

		for(String[] tab : read){

			CreateTable.addRecord(nameImpots, tab[1], "infosgenerales", "nbreRedevable", tab[2]);
			CreateTable.addRecord(nameImpots, tab[1], "infosgenerales", "PatrimoineM", tab[3]);
			CreateTable.addRecord(nameImpots, tab[1], "infosgenerales", "ImpotMoyen", tab[4]);
			CreateTable.addRecord(nameImpots, tab[1], "infosgenerales", "annee", tab[5]);

		} */

		//	CreateTable.getAllRecord(nameImpots);

	/*	CreateTable.getOneRecord(nameImpots, "34172");


		final String nameCommunes = "Communes";
		ArrayList<String[]> readCommunes = ReadCSV_Communes.run();
		String[] columCommune = {"infosCommunes"};
		CreateTable.creatTable(nameCommunes,columCommune );
		int ii = 0;

		for(String[] tabComm : readCommunes){
			String insee = tabComm[3]+tabComm[4];
			CreateTable.addRecord(nameCommunes, insee, "infosCommunes", "CDC", tabComm[0]);
			CreateTable.addRecord(nameCommunes, insee, "infosCommunes", "CHEFLIEU", tabComm[1]);
			CreateTable.addRecord(nameCommunes, insee, "infosCommunes", "REG", tabComm[2]);
			CreateTable.addRecord(nameCommunes, insee, "infosCommunes", "DEP", tabComm[3]);
			CreateTable.addRecord(nameCommunes, insee, "infosCommunes", "COM", tabComm[4]);
			CreateTable.addRecord(nameCommunes, insee, "infosCommunes", "AR", tabComm[5]);
			CreateTable.addRecord(nameCommunes, insee, "infosCommunes", "CT", tabComm[6]);
			//CreateTable.addRecord(nameCommunes, insee, "infosCommunes", "TNCC", tabComm[7]);
			//CreateTable.addRecord(nameCommunes, insee, "infosCommunes", "ARTMAJ", tabComm[8]);
			CreateTable.addRecord(nameCommunes, insee, "infosCommunes", "NCC", tabComm[9]);
			//CreateTable.addRecord(nameCommunes, insee, "infosCommunes", "ARTMIN", tabComm[10]);
			//CreateTable.addRecord(nameCommunes, insee, "infosCommunes", "NCCENR", tabComm[11]);
		}

		System.out.println("end");*/

		//	CreateTable.getAllRecord(nameImpots);

		CreateTable.getOneRecord("Communes", "34172");
		//CreateTable.getAllRecord("Communes");
//		for(String s : CreateTable.getRecordsByDepartement("Communes","34")){
//			System.out.println(s);
//		}
	}

}
