package com.example.pianoafrik.volleytest.adapter;


import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.pianoafrik.volleytest.R;
import com.example.pianoafrik.volleytest.model.Feed;
import java.util.List;



public class FeedsAdapter extends ArrayAdapter<Feed> {

    private static class ViewHolder {

        TextView body, mestId, time;
        ImageView image;

    }

    public FeedsAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<Feed> objects) {
        super(context, resource, objects);
    }

    @Nullable
    @Override
    public Feed getItem(int position) {
        return super.getItem(position);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Feed feed = getItem(position);


        ViewHolder viewHolder;

        if(convertView == null){

            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.feeds_item, parent, false);
            viewHolder.body = (TextView) convertView.findViewById(R.id.body);
            viewHolder.image = (ImageView) convertView.findViewById(R.id.image);
            viewHolder.mestId = (TextView) convertView.findViewById(R.id.mester_id);
            viewHolder.time = (TextView)convertView.findViewById(R.id.time);

            convertView.setTag(viewHolder);
        } else {

            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.body.setText(feed.getBody());
        viewHolder.mestId.setText(feed.getMesterId());
        viewHolder.time.setText(feed.getTime());


        return  convertView;

    }
}
