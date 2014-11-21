package edu.unh.cs.cs619_2014_project2.g4.gui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import edu.unh.cs.cs619_2014_project2.g4.R;

/**
 * Created by Tay on 11/14/2014.
 */

public class GridAdapter extends BaseAdapter {
    private Context context;

    UIEntity[] entities = new UIEntity[257];


    public GridAdapter(Context context, UIEntity[] entities) {
        this.context = context;
        this.entities = entities;
    }

    public View getView(int position, View convertView, ViewGroup parent) {




        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View gridView;

        if (convertView == null) {

            gridView = new View(context);
            gridView = inflater.inflate(R.layout.grid, null);

            // set value into textview
            TextView textView = (TextView) gridView
                    .findViewById(R.id.gridText);

            System.out.println("ENTITY: " + entities[position] + " at "+ position);

              textView.setText(entities[position].toString());

        } else {
            gridView = (View) convertView;
        }

        return gridView;
    }

    @Override
    public int getCount() {
        return entities.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

}