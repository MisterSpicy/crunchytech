package com.crunchytech.breeze;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class FeedArrayAdapter extends ArrayAdapter<String> {

	private LayoutInflater mInflater;
	private FeedFragment fragment;

	public FeedArrayAdapter(Activity activity, Context context, int vID) {
		super(context, vID);
		fragment = (FeedFragment) activity.getFragmentManager().findFragmentById(R.id.content_frame);
		mInflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}
	
	static class ViewHolder {
		
	    ImageView picture;
	    TextView name;
	    TextView title;
	    ImageView hide;
	    ImageView connect;
	    
	    ViewHolder(View row) {
	    	this.picture = (ImageView) row.findViewById(R.id.feed_picture);
	    	this.name = (TextView) row.findViewById(R.id.feed_name);
	    	this.title = (TextView) row.findViewById(R.id.feed_title);
		    this.hide = (ImageView) row.findViewById(R.id.feed_hide);
		    this.connect = (ImageView) row.findViewById(R.id.feed_connect);
	    }
	}
	
	public View getView(int position, View convertView, ViewGroup parent) {
        
        final ViewHolder holder;

        String id = getItem(position);
        
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
        
        holder.picture.setOnClickListener(new OnProfileClickListener(id));
        holder.name.setOnClickListener(new OnProfileClickListener(id));
        holder.title.setOnClickListener(new OnProfileClickListener(id));
        holder.hide.setOnClickListener(new OnHideClickListener(id));
        holder.connect.setOnClickListener(new OnConnectClickListener(id));

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