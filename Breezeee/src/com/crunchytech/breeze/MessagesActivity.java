package com.crunchytech.breeze;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class MessagesActivity extends FragmentActivity {

	String code;

	private ListView mDrawerListView;
	private DrawerLayout mDrawerLayout;
	private ActionBarDrawerToggle mDrawerToggle;
	private String[] mNavigationDrawerItemTitles;
	
    public static final String STATE_SELECTED_NAVIGATION_ITEM = "selected_navigation_item";

	private IntentFilter mFilter = new IntentFilter("com.google.android.c2dm.intent.RECEIVE");

	private BroadcastReceiver mReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {

			String msg = null;
			try {
				// TODO: DO STUFF

			} catch (NumberFormatException e) {
				e.printStackTrace();
			}

			if (msg != null) {

				// TODO: INSERT MESSAGE INTO DB

				/* Update fragment and abort notification */
				//MessagesFragment fragment = (MessagesFragment) getFragmentManager().findFragmentById(R.id.content_frame);
				//fragment.updateList();
				
				abortBroadcast();
			}
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.main_activity);

		if (savedInstanceState != null) {
			int selectedTabIndex = savedInstanceState.getInt("STATE_SELECTED_NAVIGATION_ITEM");
			getActionBar().setSelectedNavigationItem(selectedTabIndex);
		}


		mFilter.setPriority(IntentFilter.SYSTEM_HIGH_PRIORITY);

		// TODO: Setup backend
		
		if (findViewById(R.id.content_frame) != null) {

			if (savedInstanceState != null) {
				return;
			}

			MessagesFragment firstFragment = new MessagesFragment();
			firstFragment.setArguments(getIntent().getExtras());
			FragmentManager fragmentManager = getFragmentManager();
			FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
			fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
			fragmentTransaction.add(R.id.content_frame, firstFragment);
			fragmentTransaction.commit();
		}

		initializeDrawerList(savedInstanceState);

		if (savedInstanceState == null) {
			selectDrawerItem(0);
		}
	}

	@Override
	protected void onResume() {
		super.onResume();
		NotificationManager mNotificationManager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
		mNotificationManager.cancel(Constants.NOTIFICATION_ID);

		registerReceiver(mReceiver, mFilter);
	}

	@Override
	protected void onPause() {
		super.onPause();

		if (mReceiver != null) {
			unregisterReceiver(mReceiver);
		}
	}

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		mDrawerToggle.syncState();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		mDrawerToggle.onConfigurationChanged(newConfig);
	}

	@Override
	public void onRestoreInstanceState(Bundle savedInstanceState) {
		if (savedInstanceState.containsKey(STATE_SELECTED_NAVIGATION_ITEM)) {
			getActionBar().setSelectedNavigationItem(savedInstanceState.getInt(STATE_SELECTED_NAVIGATION_ITEM));
		}
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);

		outState.putInt(STATE_SELECTED_NAVIGATION_ITEM, getActionBar().getSelectedNavigationIndex());
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		getMenuInflater().inflate(R.menu.main, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerListView);
		menu.findItem(R.id.settings).setVisible(!drawerOpen);
		menu.findItem(R.id.messages).setVisible(!drawerOpen);

		return super.onPrepareOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		if (mDrawerToggle.onOptionsItemSelected(item)) {
			return true;
		}

		switch (item.getItemId()) {
			case R.id.settings:
				selectDrawerItem(1);
				return true;
			case R.id.messages:
				selectDrawerItem(2);
			default:
				return super.onOptionsItemSelected(item);
		}
	}

	public void initializeDrawerList(Bundle savedInstanceState) {

		mNavigationDrawerItemTitles = getResources().getStringArray(R.array.drawer_itemz);
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

		mDrawerListView = (ListView) findViewById(R.id.lstDrawer);
		
		NavItem[] drawerItem = new NavItem[3];
		
		drawerItem[0] = new NavItem(R.drawable.sym_action_chat, "Messaging");
		drawerItem[1] = new NavItem(R.drawable.ic_sysbar_quicksettings, "Settings");
		drawerItem[2] = new NavItem(R.drawable.ic_menu_invite, "Invite");
		
		NavArrayAdapter mDrawerAdapter = new NavArrayAdapter(this, R.layout.navigation_drawer_item, drawerItem);

		mDrawerListView.setAdapter(mDrawerAdapter);
		mDrawerListView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView parent, View view, int position, long id) {
				selectDrawerItem(position);
			}
		});

		mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);

		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setHomeButtonEnabled(true);

		mDrawerToggle = new ActionBarDrawerToggle(
				this,
				mDrawerLayout,
				R.drawable.ic_drawer,
				R.string.open_drawer,
				R.string.close_drawer
				) {
			public void onDrawerClosed(View view) {
				invalidateOptionsMenu();
			}

			public void onDrawerOpened(View drawerView) {
				invalidateOptionsMenu();
				getActionBar().setTitle(R.string.app_name);
			}
		};
		mDrawerLayout.setDrawerListener(mDrawerToggle);
	}
	
	private void selectDrawerItem(int position) {

		mDrawerLayout.closeDrawer(mDrawerListView);

		Fragment fragment = null;
		Bundle args = new Bundle();
		
		switch (position) {
		case 0:
			fragment = new MessagesFragment();
			break;
		case 1:
			//fragment = new SettingsFragment();
			break;
		case 2:
			//fragment = new OtherFragment();
			break;
		default:
			break;
		}
		
		args.putInt(Constants.DRAWER_ITEM, position);
		fragment.setArguments(args);
		
		FragmentManager fragmentManager = getFragmentManager();
		FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
		fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
		fragmentTransaction.replace(R.id.content_frame, fragment);
		fragmentTransaction.commit();

		// update selected item and title, then close the drawer
		mDrawerListView.setItemChecked(position, true);
		getActionBar().setTitle(getResources().getStringArray(R.array.drawer_itemz)[position]);
	}
}
