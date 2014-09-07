package com.crunchytech.breeze;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class MessagesArrayAdapter extends ArrayAdapter<String> {

	private LayoutInflater mInflater;

	public MessagesArrayAdapter(Context context, int vID) {
		super(context, vID);
		mInflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}
	
	static class ViewHolder {
		
	    ImageView icon;
	    TextView name;
	    TextView msg;
	    TextView time;
	    
	    ViewHolder(View row) {
	    	this.icon = (ImageView) row.findViewById(R.id.icon);
	    	this.name = (TextView) row.findViewById(R.id.name);
	    	this.msg = (TextView) row.findViewById(R.id.msg);
	    	this.time = (TextView) row.findViewById(R.id.time);
	    }
	}
	
	public View getView(int position, View convertView, ViewGroup parent) {
        
        final ViewHolder holder;

        String id = getItem(position);
        
        if (convertView == null) {
        	convertView = mInflater.inflate(R.layout.message_item, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.name.setText("test");
        holder.msg.setText("test");
        holder.time.setText("test");
        System.out.println("printting row = "+ position);

        return convertView;
	}
}