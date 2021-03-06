package com.crunchytech.breeze;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;
import com.crunchytech.breeze.server.ServerApi;
import com.crunchytech.breeze.server.UserInfo;

public class FeedArrayAdapter extends ArrayAdapter<String> {

	private LayoutInflater mInflater;
	private FeedFragment fragment;
	private Activity mActivity;
	private com.android.volley.toolbox.ImageLoader volleyImageLoader;
	
	public FeedArrayAdapter(Activity activity, Context context, int vID) {
		super(context, vID);
		mActivity = activity;
		volleyImageLoader = VolleySingleton.getInstance().getImageLoader();
		fragment = (FeedFragment) activity.getFragmentManager().findFragmentById(R.id.content_frame);
		mInflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}
	
	static class ViewHolder {
		
	    NetworkImageView picture;
	    TextView name;
	    TextView title;
	    ImageView hide;
	    ImageView connect;
	    Button coffeeBtn;
	    
	    ViewHolder(View row) {
	    	this.picture = (NetworkImageView) row.findViewById(R.id.feed_picture);
	    	this.name = (TextView) row.findViewById(R.id.feed_name);
	    	this.title = (TextView) row.findViewById(R.id.feed_title);
		    this.hide = (ImageView) row.findViewById(R.id.feed_hide);
		    this.connect = (ImageView) row.findViewById(R.id.feed_connect);
		    this.coffeeBtn = (Button) row.findViewById(R.id.sendCoffeeBtn);
	    }
	}
	
	public View getView(int position, View convertView, ViewGroup parent) {
        
        final ViewHolder holder;

        final String id = getItem(position);
        
        if (convertView == null) {
        	convertView = mInflater.inflate(R.layout.feed_item, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        
        UserInfo user = ServerApi.getUserInfoFromURL(id);
        
        ServerApi.nearbyUsers.get(position);

        holder.picture.setImageUrl(user.picurl, volleyImageLoader);
        holder.picture.setDefaultImageResId(R.drawable.missing_linkedin);
        holder.picture.setErrorImageResId(R.drawable.missing_linkedin);
        holder.name.setText(user.name);
        holder.title.setText(user.headline);
        holder.hide.setImageDrawable(Breeze.getAppContext().getResources().getDrawable(R.drawable.no_icon));
        holder.connect.setImageDrawable(Breeze.getAppContext().getResources().getDrawable(R.drawable.yes_icon));
        
        final ViewGroup layoutOld = (ViewGroup) convertView.findViewById(R.id.feed_actions_layout);
        final ViewGroup layoutNew = (ViewGroup) convertView.findViewById(R.id.feed_invite_layout);
        
       
        holder.picture.setOnClickListener(new OnProfileClickListener(id));
        holder.name.setOnClickListener(new OnProfileClickListener(id));
        holder.title.setOnClickListener(new OnProfileClickListener(id));
        holder.hide.setOnClickListener(new OnHideClickListener(id));
        holder.connect.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
       			//fragment.openProfile(id);
       			layoutOld.setVisibility(View.GONE);
       			layoutNew.setVisibility(View.VISIBLE);
           }
       });

        holder.coffeeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            	((MessagesActivity)mActivity).buyCoffee();
            }
        });
        return convertView;
	}
	
	public class OnProfileClickListener implements OnClickListener {
	     String url;
	     public OnProfileClickListener(String url) {
	          this.url = url;
	     }
	     
        @Override
        public void onClick(View v) {
    		fragment.openProfile(url);
        }
    };
    
	public class OnHideClickListener implements OnClickListener {
	     String url;
	     public OnHideClickListener(String url) {
	          this.url = url;
	     }
	     
       @Override
       public void onClick(View v) {
   		fragment.hideProfile(url);
       }
   };
    
	public class OnConnectClickListener implements OnClickListener {
	     String url;
	     public OnConnectClickListener(String url) {
	          this.url = url;
	     }
	     
       @Override
       public void onClick(View v) {
   		fragment.connectProfile(url);
       }
   };
}