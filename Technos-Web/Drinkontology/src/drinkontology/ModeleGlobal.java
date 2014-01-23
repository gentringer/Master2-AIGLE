package drinkontology;

import java.io.FileOutputStream;
import java.io.IOException;

import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.ontology.OntModelSpec;
import com.hp.hpl.jena.rdf.model.ModelFactory;

public class ModeleGlobal {

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub

		OntModel generalmodel = ModelFactory.createOntologyModel(OntModelSpec.RDFS_MEM);

		
		generalmodel.add(Drinkontholoy.creerModel());
		generalmodel.add(UserOntology.useront());
		generalmodel.add(BarOntology.barontology());
		
		generalmodel.write( System.out, "RDF/XML-ABBREV" );
		FileOutputStream outStream = new FileOutputStream("drinkadvisorOntology.rdf");
        // exporte le resultat dans un fichier
		generalmodel.write(outStream, "RDF/XML-ABBREV");
        outStream.close();

		
	}

}
