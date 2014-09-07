package com.crunchytech.breeze;

import android.app.ListFragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class MessagesFragment extends ListFragment {

	private MessagesArrayAdapter mMessagesAdapter;
	private ListView mMessagesListView;

	final static String ARG_POSITION = "position";
	int mCurrentPosition = -1;
	  
	@Override
	 public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	  	mMessagesAdapter = new MessagesArrayAdapter(Breeze.getAppContext(), -1);
	  	setListAdapter(mMessagesAdapter);
	 }
	
	@Override
	public void onStart() {
		super.onStart();

		Bundle args = getArguments();
		
	}

	 @Override
	 public void onListItemClick(ListView lv, View v, int position, long id) {
			String mID = lv.getAdapter().getItem(position).toString();
			loadConversation(mID);
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

		View v = inflater.inflate(R.layout.fragment_messages_list, container, false);
		mMessagesListView = (ListView) v.findViewById(R.id.lstMessages);

		return v;
	}

	public void loadConversation(String id) {
        
		Intent intent = new Intent(getActivity(), MessagesActivity.class);
		intent.putExtra("id", id);
		startActivity(intent);
	}

//	public void updateList() {
//		List<String> messages = getAllMessages();
//
//		/* Clear the adapter then re-populate */
//		mMessagesAdapter.clear();
//		
//		for (int i = 0; i < messages.size(); i++) {
//			mMessagesAdapter.add(messages.get(i));
//		}
//	}
}

