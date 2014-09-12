package com.crunchytech.breeze;

import java.util.ArrayList;
import java.util.List;

import com.crunchytech.breeze.server.ServerApi;

import android.app.ListFragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

public class FeedFragment extends ListFragment {

	private FeedArrayAdapter mFeedAdapter;
	private ListView mFeedListView;

	final static String ARG_POSITION = "position";
	int mCurrentPosition = -1;
	  
	@Override
	 public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		mFeedAdapter = new FeedArrayAdapter(getActivity(), Breeze.getAppContext(), -1);
	  	setListAdapter(mFeedAdapter);
	 }
	
	@Override
	public void onResume() {
		super.onResume();
		ServerApi.updateNearbyUsers();
		
		updateList();
	}

	 @Override
	 public void onListItemClick(ListView lv, View v, int position, long id) {
			String mID = lv.getAdapter().getItem(position).toString();
			Toast.makeText(getActivity(), mID, Toast.LENGTH_LONG).show();
	 }

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);

		// Save the current article selection in case we need to recreate the fragment
		outState.putInt(ARG_POSITION, mCurrentPosition);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		if (savedInstanceState != null) {
			mCurrentPosition = savedInstanceState.getInt(ARG_POSITION);
		}

		View v = inflater.inflate(R.layout.fragment_feed_list, container, false);
		mFeedListView = (ListView) v.findViewById(R.id.lstFeed);

		return v;
	}

	public void updateList() {
		ServerApi.updateNearbyUsers();
		
		/* Clear the adapter then re-populate */
		mFeedAdapter.clear();

		for (int i = 0; i < ServerApi.nearbyUsers.size(); i++) {
			if(!ServerApi.removedUsers.contains(ServerApi.nearbyUsers.get(i).profileurl)){
				mFeedAdapter.add(ServerApi.nearbyUsers.get(i).profileurl);
			}
		}
	}
	
	public void openProfile(String url) {
		Intent intent = new Intent(getActivity(), LinkedInProfileViewer.class);
		intent.putExtra("profileurl", url);
		startActivity(intent);
	}
	
	public void hideProfile(String url) {
		mFeedAdapter.remove(url);
		ServerApi.removeUser(url);

		updateList();
	}
	
	public void connectProfile(String url) {

	}
}

