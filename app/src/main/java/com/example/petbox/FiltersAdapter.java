package com.example.petbox;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class FiltersAdapter extends RecyclerView.Adapter<FiltersAdapter.ViewHolder> {

    private final LayoutInflater inflater;
    private enum Filters{
        AGE ("Возраст"),
        COLOR ("Окрас"),
        CITY ("Город");
        private String title;

        Filters(String title) {
            this.title = title;
        }

        public String getTitle() {
            return title;
        }
    }

    interface OnFilterClickListener{
        void onFilterClick(int position);
    }

    private final FiltersAdapter.OnFilterClickListener onClickListener;

    FiltersAdapter(Context context, FiltersAdapter.OnFilterClickListener onClickListener) {
        this.inflater = LayoutInflater.from(context);
        this.onClickListener = onClickListener;
    }

    @Override
    public FiltersAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.filters, viewGroup, false);
        return new ViewHolder(view);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(FiltersAdapter.ViewHolder viewHolder, int position) {
        position=viewHolder.getAdapterPosition();

        viewHolder.filter_nameView.setText(Filters.values()[position].getTitle());

        int finalPosition = position;
        viewHolder.itemView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v)
            {
                onClickListener.onFilterClick(finalPosition);
            }
        });

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return Filters.values().length;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        final TextView filter_nameView;

        public ViewHolder(View view) {
            super(view);
            filter_nameView = view.findViewById(R.id.filter_name);
        }
    }
}
