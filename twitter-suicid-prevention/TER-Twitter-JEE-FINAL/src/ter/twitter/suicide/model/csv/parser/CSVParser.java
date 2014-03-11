package ter.twitter.suicide.model.csv.parser;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import ter.twitter.suicide.model.hibernate.jpa.Thematics;


public class CSVParser {


	public ArrayList<Thematics> readList(String filename){

		ArrayList<Thematics> thems 	= new ArrayList<Thematics>();	
		BufferedReader br 			= null;
		String line 		= "";
		String cvsSplitBy 	= "\t";
        File file 			= new File("./"+filename);

		try {

			br = new BufferedReader(new FileReader(file.getCanonicalPath()));
			while ((line = br.readLine()) != null) {

				// use comma as separator
				String[] thematicsv = line.split(cvsSplitBy);
				if(thematicsv.length > 0){
					Thematics th 	= new Thematics(thematicsv[0],thematicsv[2],thematicsv[1]);
					if(thems.size() == 0){
						thems.add(th);
					}
					else if(!thems.get(thems.size()-1).getTerm().equals(th.getTerm())){
						thems.add(th);
					}
				}
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

		System.out.println("CSV Parsing Done");
		return thems;
	}


}
