package com.example.petbox;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.ViewHolder> {
    private final LayoutInflater inflater;
    ArrayList<User> users;

    interface OnBookClickListener {
        void onBookClick(int position);
    }

    private final OnBookClickListener onClickListener;

    BookAdapter(Context context, ArrayList<User> users,OnBookClickListener onClickListener) {
        this.users = users;
        this.inflater = LayoutInflater.from(context);
        this.onClickListener = onClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.book_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        position = viewHolder.getAdapterPosition();
        User user = users.get(position);

        viewHolder.name.setText(user.getShelter());
        viewHolder.question.setText(user.getName()+" "+user.getLastname());

        int finalPosition = position;
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickListener.onBookClick(finalPosition);
            }
        });
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        final TextView name,question;
        final ImageView image;

        public ViewHolder(View view) {
            super(view);
           name = view.findViewById(R.id.book_name);
           question = view.findViewById(R.id.book_question);
           image = view.findViewById(R.id.book_img);
        }
        public void setOnClickListener(View.OnClickListener onClickListener) {
            itemView.setOnClickListener(onClickListener);
        }
    }
}
