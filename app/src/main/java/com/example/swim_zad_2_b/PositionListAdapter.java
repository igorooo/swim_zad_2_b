package com.example.swim_zad_2_b;

import android.content.Context;
import android.media.Image;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class PositionListAdapter extends ArrayAdapter<Position> {

    private static final String TAG = "PositionListAdapter";


    private Context mContext;
    private int mResource;

    public PositionListAdapter(Context context, int resource,List<Position> objects) {
        super(context, resource, objects);
        mContext = context;
        mResource = resource;
    }

    @NonNull
    @Override
    public View getView(int position,View convertView, ViewGroup parent) {

        String Author = getItem(position).getAuthor();
        String Title = getItem(position).getTitle();
        String Date = getItem(position).getDate();
        Boolean isFilm = getItem(position).getFilm();
        Float Rating = getItem(position).getRating();

        Position pos = new Position(Title,Author,Date,Rating,isFilm);

        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource, parent, false);

        TextView title = (TextView) convertView.findViewById(R.id.title);
        TextView authordate = (TextView) convertView.findViewById(R.id.authordate);
        TextView rating = (TextView) convertView.findViewById(R.id.rating);

        ImageView imageView = (ImageView) convertView.findViewById(R.id.image);
        title.setText(Title);
        authordate.setText((String)(Author + " [" + Date + "]"));
        rating.setText((String)("Rating: " + Float.toString(Rating) + "/4"));

        if(isFilm)
            imageView.setImageResource(R.drawable.film);

        else
            imageView.setImageResource(R.drawable.book);

        return convertView;



    }
}
