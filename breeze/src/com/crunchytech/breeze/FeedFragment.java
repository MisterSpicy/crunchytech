package com.crunchytech.breeze;

import java.util.ArrayList;
import java.util.List;

import android.app.ListFragment;
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
	public void onStart() {
		super.onStart();
		
		updateList();
	}

	 @Override
	 public void onListItemClick(ListView lv, View v, int position, long id) {
			String mID = lv.getAdapter().getItem(position).toString();
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
		ArrayList<String> messages = new ArrayList<String>();
		messages.add("Hanna");
		messages.add("Brian");
		messages.add("Quoc");
		messages.add("Greg");
		messages.add("Matt");
		
		/* Clear the adapter then re-populate */
		mFeedAdapter.clear();
		
		for (int i = 0; i < messages.size(); i++) {
			mFeedAdapter.add(messages.get(i));
		}
	}
	
	public List<String> getAllMessages() {
		ArrayList<String> msgs = new ArrayList<String>();
		msgs.add("Hanna");
		msgs.add("Brian");
		msgs.add("Quoc");
		msgs.add("Greg");
		msgs.add("Matt");
		
		return msgs;
	}
	
	public void openProfile() {
		
	}
	
	public void hideProfile() {
		
	}
	
	public void connectProfile() {

	}
}

