package controllers;

import java.io.File;

import play.mvc.Controller;
import play.mvc.Result;

public class FileService extends Controller {
	static String path = "/public/data/";
	public static Result getFile(String file){

		File myfile;
		do{
			myfile = new File (System.getenv("PWD")+path+file);
			System.out.println(myfile);
		}while(!myfile.exists());
		return ok(myfile);
	}



	public static Result geturi(String file){

		System.out.println();
		File myfile = new File (System.getenv("PWD")+path+file);
		return ok();
	}


}