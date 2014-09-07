package com.crunchytech.breeze;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class FeedArrayAdapter extends ArrayAdapter<String> {

	private LayoutInflater mInflater;

	public FeedArrayAdapter(Context context, int vID) {
		super(context, vID);
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
        holder.hide.setImageDrawable(Breeze.getAppContext().getResources().getDrawable(R.drawable.yes_icon));
        holder.connect.setImageDrawable(Breeze.getAppContext().getResources().getDrawable(R.drawable.yes_icon));

        return convertView;
	}
}