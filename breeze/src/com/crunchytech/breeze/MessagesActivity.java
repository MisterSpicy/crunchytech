package com.crunchytech.breeze;

import java.math.BigDecimal;

import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;

import android.app.ActionBar;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
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

import com.crunchytech.breeze.server.ServerApi;

public class MessagesActivity extends FragmentActivity {
	private final String TAG = "Breezee";

	private ListView mDrawerListView;
	private DrawerLayout mDrawerLayout;
	private ActionBarDrawerToggle mDrawerToggle;
	private String[] mNavigationDrawerItemTitles;
	
    public static final String STATE_SELECTED_NAVIGATION_ITEM = "selected_navigation_item";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.main_activity);

		if (savedInstanceState != null) {
			int selectedTabIndex = savedInstanceState.getInt("STATE_SELECTED_NAVIGATION_ITEM");
			//getActionBar().setSelectedNavigationItem(selectedTabIndex);
		}
		
		if (findViewById(R.id.content_frame) != null) {

			if (savedInstanceState != null) {
				return;
			}

			FeedFragment firstFragment = new FeedFragment();
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
		
		if(!Breeze.getProfile().isLogin()) {
			Intent intent = new Intent(this, FirstTimeActivity.class);
		    startActivity(intent);
		}		
				
		getActionBar().setIcon(null);
		getActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_TITLE 
		        | ActionBar.DISPLAY_SHOW_HOME 
		        | ActionBar.DISPLAY_HOME_AS_UP);
		
        Intent intent = new Intent(this, PayPalService.class);
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);
        startService(intent);
		
		
		ServerApi.updateNearbyUsers();
	}

	@Override
	protected void onStart() {
		super.onStart();
	}
	@Override
	protected void onResume() {
		super.onResume();
	}

	@Override
	protected void onPause() {
		super.onPause();
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
			//getActionBar().setSelectedNavigationItem(savedInstanceState.getInt(STATE_SELECTED_NAVIGATION_ITEM));
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
		menu.findItem(R.id.discover).setVisible(!drawerOpen);
		menu.findItem(R.id.messages).setVisible(!drawerOpen);

		return super.onPrepareOptionsMenu(menu);
	}

    private static final String CONFIG_ENVIRONMENT = PayPalConfiguration.ENVIRONMENT_NO_NETWORK;

    // note that these credentials will differ between live & sandbox environments.
    private static final String CONFIG_CLIENT_ID = "credential from developer.paypal.com";

    private static final int REQUEST_CODE_PAYMENT = 1;
    private static final int REQUEST_CODE_FUTURE_PAYMENT = 2;
    private static final int REQUEST_CODE_PROFILE_SHARING = 3;

    private static PayPalConfiguration config = new PayPalConfiguration()
            .environment(CONFIG_ENVIRONMENT)
            .clientId(CONFIG_CLIENT_ID)
            // The following are only used in PayPalFuturePaymentActivity.
            .merchantName("Hipster Store")
            .merchantPrivacyPolicyUri(Uri.parse("https://www.example.com/privacy"))
            .merchantUserAgreementUri(Uri.parse("https://www.example.com/legal"));
    
    private PayPalPayment getThingToBuy(String paymentIntent) {
    	return new PayPalPayment(new BigDecimal("5.00"), "USD", "Send a Starbuck Giftcard",
            paymentIntent);
    }
    
    public void buyCoffee() {
        PayPalPayment thingToBuy = getThingToBuy(PayPalPayment.PAYMENT_INTENT_SALE);

        Intent intent = new Intent(MessagesActivity.this, PaymentActivity.class);
        intent.putExtra(PaymentActivity.EXTRA_PAYMENT, thingToBuy);
        startActivityForResult(intent, REQUEST_CODE_PAYMENT);    	
    }
    
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (mDrawerToggle.onOptionsItemSelected(item)) {
			return true;
		}
		
		switch (item.getItemId()) {
			case R.id.discover:
				selectDrawerItem(0);
				return true;
			case R.id.messages:
				selectDrawerItem(1);
			default:
				return super.onOptionsItemSelected(item);
		}
		
	}

	public void initializeDrawerList(Bundle savedInstanceState) {

		mNavigationDrawerItemTitles = getResources().getStringArray(R.array.drawer_itemz);
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

		mDrawerListView = (ListView) findViewById(R.id.lstDrawer);
		
		NavItem[] drawerItem = new NavItem[4];
		
		drawerItem[0] = new NavItem(R.drawable.discover, "Discover");
		drawerItem[1] = new NavItem(R.drawable.drawer_messages, "Messages");
		drawerItem[2] = new NavItem(R.drawable.invite, "Invite");
		drawerItem[3] = new NavItem(R.drawable.drawer_logout, "Log out");
		
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
			fragment = new FeedFragment();
			break;
		case 1:
			fragment = new MessagesFragment();
			break;
		case 2:
			//fragment = new InviteFragment();
			return;
		case 3:

			{
				Breeze.getProfile().logout();
				Intent intent = new Intent(this, FirstTimeActivity.class);
				startActivity(intent);
				return;
			}

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
