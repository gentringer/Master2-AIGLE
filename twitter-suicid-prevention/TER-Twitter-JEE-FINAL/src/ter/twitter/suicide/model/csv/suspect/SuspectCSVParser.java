package ter.twitter.suicide.model.csv.suspect;


import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;



public class SuspectCSVParser {

	// -> Methods
	public ArrayList<SuspectClass> readList(BufferedReader buffer) throws IOException{

		ArrayList<SuspectClass> suspects = new ArrayList<SuspectClass>();	
		BufferedReader br = null;
		String line = "";
		String cvsSplitBy = "\t";

		try {
			br = buffer;
			while ((line = br.readLine()) != null) {

				// use comma as separator
				String[] suspect 			= line.split(cvsSplitBy);
				if(suspect.length > 0){
					if(suspect[2].equals("y")){
						SuspectClass th 	= new SuspectClass(suspect[0],suspect[1]);
						if(suspects.size() == 0){
							suspects.add(th);
						}
						else if(!suspects.get(suspects.size()-1).getIdtweet().equals(th.getIdtweet())){
							suspects.add(th);
						}
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

		System.out.println(" CSV Parsing Done");
		return suspects;
	}


}
