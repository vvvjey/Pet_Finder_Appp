package com.example.pet_finder_app;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class SearchingLostPetAdapter extends ArrayAdapter<String> {
    private Activity context;

    public SearchingLostPetAdapter(Activity context, int layoutID, ArrayList<String> objects){
        super(context, layoutID, objects);
        this.context = context;
    }
    public View getView(final int position, View convertView, ViewGroup parent){
        if (convertView == null) {
            convertView =
                    LayoutInflater.from(context).inflate(R.layout.searching_lost_pet_item, null,
                            false);
        }
        return convertView;
    }
}
