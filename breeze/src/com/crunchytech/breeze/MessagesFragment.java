package com.crunchytech.breeze;

import java.util.ArrayList;
import java.util.List;

import com.crunchytech.breeze.chat.MessageService;

import android.app.ListFragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class MessagesFragment extends ListFragment {

	private MessagesArrayAdapter mMessagesAdapter;
	private ListView mMessagesListView;

    private static final String TAG = MessagesFragment.class.getSimpleName();

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
		
		updateList();
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
        String sender = Breeze.getProfile().profile.getFirstName().trim();
        Log.d(TAG, "Starting message for " + id + " with " + sender);
        Intent intent = new Intent(getActivity(), MessageService.class);
        intent.putExtra(MessageService.INTENT_EXTRA_USERNAME, sender);
        intent.putExtra(MessageService.INTENT_EXTRA_PEER, id);
        getActivity().startService(intent);			
	}

	public void updateList() {
		ArrayList<String> messages = new ArrayList<String>();
		messages.add("Hanna");
		messages.add("Brian");
		messages.add("Quoc");
		messages.add("Greg");
		messages.add("Matthew");
		
		/* Clear the adapter then re-populate */
		mMessagesAdapter.clear();
		
		for (int i = 0; i < messages.size(); i++) {
			mMessagesAdapter.add(messages.get(i));
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
}

