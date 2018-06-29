package com.example.mohamed.newsakhbar;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Mohamed on 6/28/2018.
 */

public class ListNewsAdapter extends BaseAdapter {
    private Activity activity;
    private ArrayList<HashMap<String, String>> data;

    public ListNewsAdapter(Activity a, ArrayList<HashMap<String, String>> d) {
        activity = a;
        data = d;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertview, ViewGroup parent) {
        ListNewsViewHolder holder = null;
        if (convertview == null) {
            holder = new ListNewsViewHolder();
            convertview = LayoutInflater.from(activity).inflate(R.layout.list_row, parent, false);
            holder.gallaryimage = convertview.findViewById(R.id.galleryImage);
            holder.author = convertview.findViewById(R.id.author);
            holder.title = convertview.findViewById(R.id.title);
            holder.sddetails = convertview.findViewById(R.id.sdetails);
            holder.time = convertview.findViewById(R.id.time);
            convertview.setTag(holder);
        } else {
            holder = (ListNewsViewHolder) convertview.getTag();
        }

        holder.gallaryimage.setId(position);
        holder.author.setId(position);
        holder.time.setId(position);
        holder.title.setId(position);
        holder.sddetails.setId(position);
        HashMap<String, String> song = new HashMap<String, String>();
        song = data.get(position);

        try {
            holder.author.setText(song.get(MainActivity.Key_Author));
            holder.title.setText(song.get(MainActivity.Key_Title));
            holder.time.setText(song.get(MainActivity.Key_PublishedAt));
            holder.sddetails.setText(song.get(MainActivity.Key_Description));

            if (song.get(MainActivity.Key_UrlToImage).toString().length() < 5) {
                holder.gallaryimage.setVisibility(View.GONE);
            } else {
                Picasso.with(activity)
                        .load(song.get(MainActivity.Key_UrlToImage).toString())
                        .resize(300, 200)
                        .into(holder.gallaryimage);
            }
        } catch (Exception e) {
        }
        return convertview;
    }
}

    class ListNewsViewHolder{
        ImageView gallaryimage;
        TextView author,title,time,sddetails;
    }

