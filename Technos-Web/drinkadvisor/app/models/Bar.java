package models;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.MapKey;
import javax.persistence.MapKeyColumn;
import javax.persistence.OneToMany;
import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import com.google.gson.Gson;



import play.data.validation.Constraints;
import play.db.ebean.Model;

@Entity
public class Bar  extends Model{


	@Id
	public String idbar ;
	@Constraints.Required
	public String lon ;
	@Constraints.Required
	public String lat;
	@Constraints.Required
	public String name;

	@ManyToMany
	public List<BeerInBar> beerlist ;

	@ManyToMany
	public List<User> likes;
	@ManyToMany
	public List<User> plusone;
	@ManyToMany
	public List<Comment>comments;
	public int note;
	public String mail;
	public String adresse;
	public String telephone;
	public String website;


	public Bar(String id2, String name ,String latitude, String longitude) 
	{
		this.idbar=id2;
		this.name = name;
		this.lat = latitude;
		this.lon = longitude;
		this.plusone = new ArrayList<User>();
		this.beerlist= new ArrayList<BeerInBar>();
		this.likes = new ArrayList<User>();
		this.note = 0;
		this.mail="";
				
		this.telephone="";
		this.website="";
		// TODO Auto-generated constructor stub
	}

	public Bar(String id2 ,String name, String adresse, String tel, String mail, String website, String lat, String lon) 
	{
		this.idbar=id2;
		this.name = name;
		this.adresse=adresse;
		this.lat = lat;
		this.lon = lon;
		this.mail=mail;
		this.website=website;
		this.telephone=tel;
		this.plusone = new ArrayList<User>();
		this.beerlist= new ArrayList<BeerInBar>();
		this.likes = new ArrayList<User>();
		this.note = 0;
		
		// TODO Auto-generated constructor stub
	}
	
	public void searchAdress(Bar ba){
		try {
			ba.adresse=getJSON(lon,lat);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static final long serialVersionUID = 1L;

	public static Finder<Long, Bar> barfinder = new Finder<Long, Bar>(Long.class, Bar.class);



	public static List<Bar> allBars() throws IOException, SAXException, ParserConfigurationException
	{
		List<Bar> osmNodesInVicinity = ConnectOSMApi.getOSMNodesInVicinity(49, 8.3, 0.005);
		//		for (OSMNode osmNode : osmNodesInVicinity) {
		//			System.out.println(osmNode.getTags());
		//			System.out.println("Logitude : "+osmNode.getLon());
		//			System.out.println("Latitude : "+osmNode.getLat());
		//		}
		return osmNodesInVicinity;
	}

	public static Finder<Long,Bar> find = new Finder<Long,Bar>(
			Long.class,Bar.class);


	public static String getJSON(String lon, String lat) throws InterruptedException {
		try {
			Thread.sleep(1000);

			URL u = new URL("http://maps.googleapis.com/maps/api/geocode/json?latlng="+lat+","+lon+"&sensor=false");
			HttpURLConnection c = (HttpURLConnection) u.openConnection();
			c.setRequestMethod("GET");
			c.setRequestProperty("Content-length", "0");
			c.setUseCaches(false);
			c.setAllowUserInteraction(false);
			c.setConnectTimeout(10000);
			c.setReadTimeout(10000);
			c.connect();
			int status = c.getResponseCode();

			System.out.println("status " + status);
			switch (status) {
			case 200:
			case 201:
				BufferedReader br = new BufferedReader(new InputStreamReader(c.getInputStream()));
				StringBuilder sb = new StringBuilder();
				String line;
				while ((line = br.readLine()) != null) {
					sb.append(line+"\n");
				}
				br.close();	                
				return getgeocoding(sb.toString());
			}

		} catch (MalformedURLException ex) {
		} catch (IOException ex) {
		}
		return null;
	}

	public static String getgeocoding(String jsonobj){
		String result = "";

		Gson gson = new Gson();
		GoogleGeoCodeResponse results = gson.fromJson(jsonobj,  GoogleGeoCodeResponse.class);

		//  double lat = Double.parseDouble(results.results[0].geometry.location.lat);

		//  double lng = Double.parseDouble(results.results[0].geometry.location.lng);
		String street="";
		if(results.results[0]!=null){
			street = results.results[0].formatted_address;

			System.out.println(street);
		}
		return street;
	}


	public String getId() {
		return idbar;
	}

	public void setId(String id) {
		this.idbar = id;
	}

	public String getLat() {
		return lat;
	}

	public void setLat(String lat) {
		this.lat = lat;
	}

	public String getLon() {
		return lon;
	}

	public void setLon(String lon) {
		this.lon = lon;
	}





}