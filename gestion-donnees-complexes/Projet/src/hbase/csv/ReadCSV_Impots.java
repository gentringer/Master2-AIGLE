package hbase.csv;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class ReadCSV_Impots {
	 
	  public static void main(String[] args) {
	 
		ReadCSV_Impots obj = new ReadCSV_Impots();
		obj.run();
	 
	  }
	 
	  public static ArrayList<String[]> run() {
	 
		String csvFile = "isf_2002.csv";
		BufferedReader br = null;
		String line = "";
		String cvsSplitBy = ";";
		ArrayList<String[]> listreturn = new ArrayList<String[]>();
		try {
	 
			br = new BufferedReader(new FileReader(csvFile));
			while ((line = br.readLine()) != null) {
	 
			        // use comma as separator
				String[] country = line.split(cvsSplitBy);
				
				listreturn.add(country);
	 
				/*System.out.println("Impots [codeINSEE= " + country[1] 
	                                 + " , nbreRedevable=" + country[2] + 
	                                 " , PatrimoineM=" + country[3]+
	                                 " , ImpotMoyen=" + country[4]+
	                                 " , annee=" + country[5]+"]");*/
	 
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
		return listreturn;
	  }
	 
	}