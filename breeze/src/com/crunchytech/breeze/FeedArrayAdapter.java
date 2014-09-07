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
import android.widget.LinearLayout;
import android.widget.TextView;

public class FeedArrayAdapter extends ArrayAdapter<String> {

	private LayoutInflater mInflater;
	private FeedFragment fragment;
	private Activity mActivity;
	
	public FeedArrayAdapter(Activity activity, Context context, int vID) {
		super(context, vID);
		mActivity = activity;
		fragment = (FeedFragment) activity.getFragmentManager().findFragmentById(R.id.content_frame);
		mInflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}
	
	static class ViewHolder {
		
	    ImageView picture;
	    TextView name;
	    TextView title;
	    ImageView hide;
	    ImageView connect;
	    Button coffeeBtn;
	    
	    ViewHolder(View row) {
	    	this.picture = (ImageView) row.findViewById(R.id.feed_picture);
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

        holder.picture.setImageDrawable(Breeze.getAppContext().getResources().getDrawable(R.drawable.yes_icon));
        holder.name.setText("Michelle");
        holder.title.setText("Breezemaster");
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
       			fragment.openProfile(id);
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
	     String id;
	     public OnProfileClickListener(String id) {
	          this.id = id;
	     }
	     
        @Override
        public void onClick(View v) {
    		fragment.openProfile(id);
        }
    };
    
	public class OnHideClickListener implements OnClickListener {
	     String id;
	     public OnHideClickListener(String id) {
	          this.id = id;
	     }
	     
       @Override
       public void onClick(View v) {
   		fragment.hideProfile(id);
       }
   };
    
	public class OnConnectClickListener implements OnClickListener {
	     String id;
	     public OnConnectClickListener(String id) {
	          this.id = id;
	     }
	     
       @Override
       public void onClick(View v) {
   		fragment.connectProfile(id);
       }
   };
}