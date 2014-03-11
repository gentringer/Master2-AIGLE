package com.gestion.model.neo4j;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class ReadCSV_Regions {

	public static void main(String[] args) {

		ReadCSV_Regions obj = new ReadCSV_Regions();
		//obj.run();

	}

	public static ArrayList<String[]> run() {

		String csvFile = "/home/gentringer/universite/Master2-Aigle/gestion-donnees-complexes/ProjetJEE/files/region.csv";
		BufferedReader br = null;
		String line = "";
		String cvsSplitBy = "\t";
		ArrayList <String[]> listdep = new ArrayList<String[]>();
		try {
			System.out.println("start reading");
			br = new BufferedReader(new FileReader(csvFile));
			while ((line = br.readLine()) != null) {
				// use comma as separator
				String[] depsplitter = line.split(cvsSplitBy);
				listdep.add(depsplitter);
				/*System.out.println("Commune [CDC= " + country[0] 
						+ " , CHEFLIEU=" + country[1] +
						" , REG=" + country[2]+
						" , DEP=" + country[3]+
						" , COM=" + country[4]+
						" , AR= " + country[5]+
						" , CT= " + country[6]+
						" , TNCC= " + country[7]+
						" , ARTMAJ= " + country[8]+
						" , NCC= " + country[9]+
						" , ARTMIN= " + country[10]+
						" , NCCENR= " + country[11]+
						"]");*/
				

			}
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		System.out.println("Done");
		return listdep;
	}

}