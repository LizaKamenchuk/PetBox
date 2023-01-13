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

public class AnimalsAdapter extends RecyclerView.Adapter<AnimalsAdapter.ViewHolder> {

    private final LayoutInflater inflater;
    private final List<Animal> animals ;

    interface OnAnimalClickListener{
        void onAnimalClick( int position);
    }

    private  OnAnimalClickListener onClickListener;

    AnimalsAdapter(Context context, ArrayList<Animal> animals, OnAnimalClickListener onClickListener) {
        this.animals = animals;
        this.inflater = LayoutInflater.from(context);
        this.onClickListener = onClickListener;
    }

    AnimalsAdapter(Context context, ArrayList<Animal> animals) {
        this.animals = animals;
        this.inflater = LayoutInflater.from(context);
    }


    @Override
    public AnimalsAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.list_animal, viewGroup, false);
        return new ViewHolder(view);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(AnimalsAdapter.ViewHolder viewHolder, int position) {
        position=viewHolder.getAdapterPosition();
        Animal animal = animals.get(position);
        if(animal.getType().equals("кот")) viewHolder.imageView.setImageResource(R.drawable.kotik);
        else if(animal.getType().equals("собака")) viewHolder.imageView.setImageResource(R.drawable.dog);
        else  viewHolder.imageView.setImageResource(R.drawable.others);
        viewHolder.nameView.setText(animal.getName());
        viewHolder.characterView.setText(animal.getCharacters());
        viewHolder.gender_ageView.setText(animal.getGender()+", "+animal.getAge()+" "+animal.getAgeMeasure());

        int finalPosition = position;
        viewHolder.itemView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v)
            {
                onClickListener.onAnimalClick(finalPosition);
            }
        });

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return animals.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        final ImageView imageView;
        final TextView nameView, characterView, gender_ageView;

        public ViewHolder(View view) {
            super(view);
            imageView = view.findViewById(R.id.photo);
            nameView = view.findViewById(R.id.name);
            gender_ageView = view.findViewById(R.id.gender_age);
            characterView = view.findViewById(R.id.character);
        }
    }
}
