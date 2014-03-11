package com.mtpAdvisor.database;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.mtpAdvisor.classes.Interest;
import com.mtpAdvisor.classes.User;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class InterestDbHelper extends SQLiteOpenHelper {
	private final SimpleDateFormat dateFormatter = new SimpleDateFormat("dd-MMM-yyyy");

	private static final int DATABASE_VERSION = 1;

	// Database Name
	private static final String DATABASE_NAME = "interestdatabase";

	// table names
	private static final String TABLE_USER = "user";
	private static final String TABLE_INTEREST = "interest";

	// Table Columns names user
	private static final String USER_ID = "userid";
	private static final String USER_NAME = "username";


	//Table Colums interest
	private static final String INTEREST_CAT = "category";
	private static final String INTEREST_STATUS = "status";
	private static final String INTEREST_NAME = "nameinterest";
	private static final String INTEREST_NOTE = "note";
	private static final String INTEREST_LAT = "lat";
	private static final String INTEREST_LON = "lon";
	private static final String INTEREST_ADRESSE = "adresse";


	public InterestDbHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	public boolean ExistsInterest(String name, String category) {
		SQLiteDatabase db = this.getWritableDatabase();

		Cursor cursor = db.rawQuery("SELECT  * FROM " + TABLE_INTEREST +" where "+INTEREST_CAT+" = ? and "+INTEREST_NAME+"= ?", 
				new String[] {category,name });
				boolean exists = (cursor.getCount() > 0);
		cursor.close();
		db.close();
		return exists;
		
	}
	
	public boolean ExistsLike(String userid, String name, String category) {
		SQLiteDatabase db = this.getWritableDatabase();

		Cursor cursor = db.rawQuery("SELECT * FROM LIKES where USERID = ? and INTERESTNAME = ? and CATEGORY = ? ", 
				new String[] {userid,name,category });
				boolean exists = (cursor.getCount() > 0);
		cursor.close();
		db.close();
		return exists;
		
	}
	
	public boolean ExistsUser(String userid) {
		SQLiteDatabase db = this.getWritableDatabase();

		Cursor cursor = db.rawQuery("SELECT * FROM "+TABLE_USER+" where "+USER_ID+" = ?", 
				new String[] {userid});
				boolean exists = (cursor.getCount() > 0);
		cursor.close();
		db.close();
		return exists;
		
	}


	@Override
	public void onCreate(SQLiteDatabase db) {

		String sqlinterst = "CREATE TABLE IF NOT EXISTS "+TABLE_INTEREST
				+" ( "+INTEREST_CAT+ " TEXT , "
				+INTEREST_NAME+ " TEXT, "
				+INTEREST_NOTE+ " INTEGER, "
				+INTEREST_STATUS+ " INTEGER, " +
				INTEREST_LAT+ " TEXT, " +
				INTEREST_LON+ " TEXT, " +
				INTEREST_ADRESSE+ " TEXT, " +
				"PRIMARY KEY (nameinterest));";

		String sqluser = "CREATE TABLE IF NOT EXISTS "+TABLE_USER
				+" ( "+USER_ID+ " TEXT PRIMARY KEY, "
				+USER_NAME+ " TEXT);";


		String sqllikes = "CREATE TABLE IF NOT EXISTS LIKES " +
				"(USERID TEXT, " +
				"INTERESTNAME TEXT, " +
				"CATEGORY TEXT ," +
				" FOREIGN KEY(USERID) REFERENCES user(userid), " +
				"FOREIGN KEY(INTERESTNAME) REFERENCES interest(nameinterest)," +
				"PRIMARY KEY (USERID,INTERESTNAME));";

		Log.d("database", sqlinterst);
		Log.d("database", sqluser);
		Log.d("database", sqllikes);

		db.execSQL(sqlinterst);
		db.execSQL(sqluser);
		db.execSQL(sqllikes);


	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldV, int newV) {
		// Drop older table if existed
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_INTEREST);
		// Create tables again
		onCreate(db);
	}

	public void addInterest(Interest interest) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();


		values.put(INTEREST_CAT, interest.getCategory());
		values.put(INTEREST_NAME, interest.getNameInterest());
		values.put(INTEREST_NOTE, interest.getNote());
		values.put(INTEREST_STATUS, interest.getStatus());
		values.put(INTEREST_LAT, interest.getLat());
		values.put(INTEREST_LON, interest.getLon());
		values.put(INTEREST_ADRESSE, interest.adresse);

		String selectQuery = "SELECT  * FROM " + TABLE_INTEREST +" where "+INTEREST_NAME+" = '"+interest.getNameInterest()+"'";
		Cursor cursor = db.rawQuery(selectQuery, null);
		if(!cursor.moveToFirst()){
			//db.rawQuery("REPLACE INTO " + TABLE_INTEREST +"(category,nameinterest,note,status,lat,lon) VALUES" +
			//	"(?,?,?,?,?,?)",new String[]{interest.getCategory(),interest.getNameInterest(),interest.getNote(),interest.getStatus(),interest.getLat()});
			db.insert(TABLE_INTEREST, null, values);
		}
		db.close();


	}

	public void addUser(User user) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();

		values.put(USER_ID, user.getUserid());
		values.put(USER_NAME, user.getUsername());


		db.rawQuery("REPLACE INTO " + TABLE_USER +"(userid,username) VALUES" +
				"(?,?)",new String[]{user.getUserid(),user.getUsername()});

		//	db.insert(TABLE_USER, null, values);
		db.close(); // Closing database connection
	}

	public ArrayList<Interest> getAllInsterests(String cat) {
		SQLiteDatabase db = this.getWritableDatabase();

		ArrayList<Interest> intersetlist = new ArrayList<Interest>();
		// Select All Query
		String selectQuery = "SELECT  * FROM " + TABLE_INTEREST +" where "+INTEREST_CAT+" = '"+cat+"'";
		Cursor cursor = db.rawQuery(selectQuery, null);
		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			do {
				Interest interest = new Interest();
				interest.setCategory(cursor.getString(0));
				interest.setNameInterest(cursor.getString(1));
				interest.setNote(cursor.getInt(2));
				interest.setStatus(cursor.getInt(3));
				interest.setLat(cursor.getString(4));
				interest.setLon(cursor.getString(5));
				interest.adresse=cursor.getString(6);

				// Adding contact to list
				intersetlist.add(interest);
			} while (cursor.moveToNext());
		}
		db.close();
		// return task list
		return intersetlist;
	}
	
	public ArrayList<Interest> getEveryINterest() {
		SQLiteDatabase db = this.getWritableDatabase();

		ArrayList<Interest> intersetlist = new ArrayList<Interest>();
		// Select All Query
		String selectQuery = "SELECT  * FROM " + TABLE_INTEREST;
		Cursor cursor = db.rawQuery(selectQuery, null);
		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			do {
				Interest interest = new Interest();
				interest.setCategory(cursor.getString(0));
				interest.setNameInterest(cursor.getString(1));
				interest.setNote(cursor.getInt(2));
				interest.setStatus(cursor.getInt(3));
				interest.setLat(cursor.getString(4));
				interest.setLon(cursor.getString(5));
				interest.adresse=cursor.getString(6);

				// Adding contact to list
				intersetlist.add(interest);
			} while (cursor.moveToNext());
		}
		db.close();
		// return task list
		return intersetlist;
	}

	public void updateTask(Interest task) {
		// updating row
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		db.close();

	}

	public void droptables(){
		SQLiteDatabase db = this.getWritableDatabase();

		db.execSQL("DROP TABLE IF EXISTS " + TABLE_INTEREST);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
		db.execSQL("DROP TABLE IF EXISTS LIKES");

		this.onCreate(db);
		db.close();

	}

	public void updatestatus(String name, int verif) {
		// TODO Auto-generated method stub
		SQLiteDatabase db = this.getWritableDatabase();

		//name = name.replace("'"," ");


		// Select All Query
		// looping through all rows and adding to list

		if(verif==1){
			db.rawQuery("UPDATE " + TABLE_INTEREST +" SET status = status + 1 where "+INTEREST_NAME+" = ?",new String[]{name});

		}
		if(verif==0){
			db.rawQuery("UPDATE " + TABLE_INTEREST +" SET status = status - 1 where "+INTEREST_NAME+" = ?",new String[]{name});
		}

		//db.execSQL(selectQuery);
		db.close(); // Closing database connection

		// return task list
	}


	public void createLike(String name, String id, String category){
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();

		values.put("USERID", id);
		values.put("INTERESTNAME", name);
		values.put("CATEGORY", category);

		db.insert("LIKES", null, values);
		db.close(); // Closing database connection
	}


	public List<String> getAllLikesByCategory(String id,String category){
		SQLiteDatabase db = this.getWritableDatabase();

		List<String> intersetlist = new ArrayList<String>();
		// Select All Query
		
		Cursor cursor = db.rawQuery("SELECT INTERESTNAME FROM LIKES where USERID = ? and CATEGORY = ?"
				,new String[]{id,category});

//		String selectQuery = "SELECT INTERESTNAME FROM LIKES where USERID = '"+id+"' and CATEGORY = '"+category+"'";
//		Cursor cursor = db.rawQuery(selectQuery, null);
//		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			do {
				intersetlist.add(cursor.getString(0));

			} while (cursor.moveToNext());
		}
		db.close();

		// return task list
		return intersetlist;
	}
	
	public List<String> getEveryLike(String userid){
		SQLiteDatabase db = this.getWritableDatabase();

		List<String> intersetlist = new ArrayList<String>();
		// Select All Query
		String selectQuery = "SELECT INTERESTNAME FROM LIKES where USERID = '"+userid+"'";
		Cursor cursor = db.rawQuery(selectQuery, null);
		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			do {
				intersetlist.add(cursor.getString(0));
			} while (cursor.moveToNext());
		}
		db.close();

		// return task list
		return intersetlist;
	}

	public void deletelike(String name, String userid) {
		//name = name.replace("'"," ");

		// TODO Auto-generated method stub
		SQLiteDatabase db = this.getWritableDatabase();

		//List<String> intersetlist = new ArrayList<String>();
		// Select All Query
		//String deleteQuery = "DELETE FROM LIKES where USERID=? and INTERESTNAME=?";
		db.delete("LIKES","USERID=? and INTERESTNAME=?", new String[]{userid,name});
		//Cursor curs = db.rawQuery("DELETE FROM LIKES where USERID=? and INTERESTNAME=?",new String[]{userid,name});
		//curs.moveToFirst();
		//  String found =curs.getString(1);
		db.close(); // Closing database connection
	}

}
