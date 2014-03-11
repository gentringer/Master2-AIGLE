package com.mtpAdvisor.classes;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import android.os.Parcel;
import android.os.Parcelable;


//Parclable => pour envoi par intent
public class Interest implements Parcelable{

	public String category;
	public String nameInterest;
	public int status = 0;
	public int note = 0;
	public String lat;
	public String lon;
	public String adresse;
	
	public Interest(){

	}

	public Interest(String category, String lat, String lon, String nameInterest, String adresse) 
	{
		super();
		this.category = category;
		this.lat = lat;
		this.lon= lon;
		this.nameInterest = nameInterest;
		this.adresse=adresse;
	}

	private Interest(Parcel in) {
		this.category=in.readString();;
		this.lat = in.readString();;
		this.lon = in.readString();;
		this.nameInterest = in.readString();;
		this.adresse=in.readString();;


	}

	@Override
	public void writeToParcel(Parcel dest, int flags)
	{
		dest.writeString(category);
		dest.writeString(lat);
		dest.writeString(lon);
		dest.writeString(nameInterest);
		dest.writeString(adresse);


	}


	public static final Parcelable.Creator<Interest> CREATOR = new Parcelable.Creator<Interest>() {
		@Override
		public Interest createFromParcel(Parcel source)
		{
			return new Interest(source);
		}


		@Override
		public Interest[] newArray(int size)
		{
			return new Interest[size];
		}

	};








	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getNameInterest() {
		return nameInterest;
	}

	public void setNameInterest(String nameInterest) {
		this.nameInterest = nameInterest;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getNote() {
		return note;
	}

	public void setNote(int note) {
		this.note = note;
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