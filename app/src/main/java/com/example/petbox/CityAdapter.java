package com.example.petbox;

import android.content.Context;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CityAdapter extends RecyclerView.Adapter<CityAdapter.ViewHolder> {
    private final LayoutInflater inflater;
    String[] cities;

    interface OnCityClickListener {
        void onCityClick(String city);
        void onCityUnClick(String city);
    }

    private final CityAdapter.OnCityClickListener onClickListener;

    CityAdapter(Context context, String[] cities,CityAdapter.OnCityClickListener onClickListener) {
        this.cities=cities;
        this.inflater = LayoutInflater.from(context);
        this.onClickListener = onClickListener;
    }


    @Override
    public CityAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.city_list, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CityAdapter.ViewHolder viewHolder, int position) {
        position = viewHolder.getAdapterPosition();
        String city = cities[position];

        viewHolder.checkBox.setText(cities[position]);

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!viewHolder.checkBox.isChecked()) {
                    viewHolder.checkBox.setChecked(true);
                    onClickListener.onCityClick(city);
                } else {
                   viewHolder.checkBox.setChecked(false);
                    onClickListener.onCityUnClick(city);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return cities.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        final CheckBox checkBox;

        public ViewHolder(View view) {
            super(view);
            checkBox = view.findViewById(R.id.checkbox);
            checkBox.setClickable(false);
        }
        public void setOnClickListener(View.OnClickListener onClickListener) {
            itemView.setOnClickListener(onClickListener);
        }
    }
}