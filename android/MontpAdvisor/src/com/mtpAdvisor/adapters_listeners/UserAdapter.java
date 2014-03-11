package com.mtpAdvisor.adapters_listeners;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.projet.*;

import com.mtpAdvisor.activites.Activity_Fragments;
import com.mtpAdvisor.activites.ListCateogry;
import com.mtpAdvisor.classes.Interest;
import com.mtpAdvisor.database.InterestDbHelper;
import com.mtpAdvisor.fragments.Fragment_InterestList;
import com.mtpAdvisor.fragments.Fragment_Map;
import com.mtpAdvisor.services.ServiceAddNoteServer;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Filter;
import android.widget.TextView;

// S'occuper de remplir la liste des intérêts favoris dans le menu de l'application 

public class UserAdapter extends BaseAdapter {

	List<Interest> interests;
	List<String> oldlikes = new ArrayList<String>();

	LayoutInflater inflater;
	Context context;

	InterestDbHelper db;
	public Map<Integer,Boolean> ischecked;

	//Stocke l'ensemble des données de la liste
	private List<Interest> initialList;
	public String category;


	public UserAdapter(Context context,List<Interest> interests, InterestDbHelper db) {
		inflater = LayoutInflater.from(context);
		this.interests = interests;
		this.db=db;
		this.context = context;
		//this.originalData=interests;
		initialList=interests;
		ischecked=new HashMap<Integer, Boolean>();
		for(int i=0;i<this.getCount();i++){
			ischecked.put(i, false);
		}
		category = Activity_Fragments.inputString;
		if(category.equals("Bars/Pubs")){
			category="bars";
		}
		if(category.equals("Hôtels") || category.equals("Hotels")){
			category="hotels";
		}
		if(category.equals("restaurant")){
			category="restaurant";
		}
	}


	// Filtre pour atocomplétion
	public Filter getFilter() {
		// TODO Auto-generated method stub

		return new Filter()
		{

			@Override
			protected FilterResults performFiltering(CharSequence charSequence)
			{
				FilterResults results = new FilterResults();
				ArrayList<Interest> FilteredList= new ArrayList<Interest>();
				//If there's nothing to filter on, return the original data for your list
				if(charSequence == null || charSequence.length() == 0)
				{
					results.values = initialList;
					results.count = initialList.size();
				}
				else
				{
					ArrayList<Interest> filterResultsData = new ArrayList<Interest>();

					for(Interest data : initialList)
					{
						//In this loop, you'll filter through originalData and compare each item to charSequence.
						//If you find a match, add it to your new ArrayList
						//I'm not sure how you're going to do comparison, so you'll need to fill out this conditional
						String filterableString = data.getNameInterest();
						if(filterableString.toLowerCase().contains(charSequence.toString())){
							FilteredList.add(data);

						}
					}            

					results.values = FilteredList;
					results.count = FilteredList.size();
				}

				return results;
			}

			// On change le liste affichée 
			@Override
			protected void publishResults(CharSequence constraint, FilterResults results) 
			{	            

				interests = (ArrayList<Interest>)results.values;
				ischecked=new HashMap<Integer, Boolean>();
				ischecked=new HashMap<Integer, Boolean>();
				for(int i=0;i<interests.size();i++){
					ischecked.put(i, false);
				}

				notifyDataSetChanged();
				notifyDataSetInvalidated();
			}

		};
	}



	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub

		ViewHolder holder;
		List<String> likes = new ArrayList<String>();
		if(convertView == null) {

			holder = new ViewHolder();
			convertView = inflater.inflate(R.layout.tab_list_item, null);

			holder.name = (TextView)convertView.findViewById(R.id.name);


			holder.check = (CheckBox)convertView.findViewById(R.id.checkBox1);

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.name.setText(interests.get(position).getNameInterest());

		holder.check.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
		{
			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {

				if(isChecked){
					ischecked.put(position, true);

					if(!oldlikes.contains(interests.get(position).getNameInterest())){
						// TODO Auto-generated method stub
						if(!db.ExistsLike(Fragment_InterestList.currentUser.getUserid(), interests.get(position).getNameInterest(), interests.get(position).getCategory())){
							db.updatestatus(interests.get(position).getNameInterest(),1);
							db.createLike(interests.get(position).getNameInterest(),Fragment_InterestList.currentUser.getUserid(),interests.get(position).getCategory());
							Intent addlikeintent = new Intent((Activity)context,ServiceAddNoteServer.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK); 
							addlikeintent.putExtra("interestname", interests.get(position).getNameInterest());
							((Activity)context).startService(addlikeintent);

						}
						if(!Fragment_Map.favoritelist.contains(interests.get(position).getNameInterest())){
							oldlikes.add(interests.get(position).getNameInterest());
							Fragment_Map.favoritelist.add(interests.get(position).getNameInterest());
						}

						Intent i= new Intent("news");

						LocalBroadcastManager.getInstance(context).sendBroadcast(i);
						ListCateogry.stopservice();
						ListCateogry.startService();
					}
				}
				else{
					ischecked.put(position, false);
					if(oldlikes.contains(interests.get(position).getNameInterest())){

						db.updatestatus(interests.get(position).getNameInterest(),0);
						db.deletelike(interests.get(position).getNameInterest(),Fragment_InterestList.currentUser.getUserid());
						oldlikes.remove(interests.get(position).getNameInterest());

						//Fragment_Map.favoritelist.remove(interests.get(position).getNameInterest());
						Fragment_Map.removeFavorite(interests.get(position).getNameInterest());
						Intent i= new Intent("news");
						LocalBroadcastManager.getInstance(context).sendBroadcast(i);
						Fragment_InterestList.settingAdapter();
						ListCateogry.stopservice();
						ListCateogry.startService();
					}
				}


			}

		});
		holder.check.setChecked(ischecked.get(position));
		likes = db.getAllLikesByCategory(Fragment_InterestList.currentUser.getUserid(),category);
		for(String l : likes){
			if(!Fragment_Map.favoritelist.contains(l)){
				//Fragment_Map.favoritelist.add(l);
			}
		}

		String currentinterest = interests.get(position).getNameInterest();

		if(likes.contains(currentinterest)){
			holder.check.setChecked(true);
			ischecked.put(position, true);
			oldlikes.add(interests.get(position).getNameInterest());

		}

		return convertView;

	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return interests.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return interests.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	private class ViewHolder {
		TextView name;
		CompoundButton check;

	}

}
