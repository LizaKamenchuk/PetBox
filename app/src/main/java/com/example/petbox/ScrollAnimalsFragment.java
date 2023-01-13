package com.example.petbox;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.ArrayList;


public class ScrollAnimalsFragment extends Fragment implements View.OnClickListener {
    ArrayList<Animal> animals;

    public interface GoToAllaboutAnimal {
        public void goToAnimal(Animal animal);
    }
   private GoToAllaboutAnimal goToAnimal;

    ScrollAnimalsFragment(){
        setInitialData();
    }
    ScrollAnimalsFragment(ArrayList<Animal> animals){
        this.animals=animals;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        goToAnimal = (GoToAllaboutAnimal) context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_app_bar, container, false);
        //setInitialData();
        // получаем элемент ListView
        RecyclerView recyclerView = view.findViewById(R.id.scrollanimalsList);
        // создаем адаптер
        AnimalsAdapter.OnAnimalClickListener animalClickListener = new AnimalsAdapter.OnAnimalClickListener() {
            @Override
            public void onAnimalClick(int position) {
                goToAnimal.goToAnimal(animals.get(position));
            }
        };
        AnimalsAdapter adapter = new AnimalsAdapter(getContext(), animals,animalClickListener);
        // устанавливаем для списка адаптер
        recyclerView.setAdapter(adapter);


        Button
                filter_btn = view.findViewById(R.id.filter_btn),
                sort_btn = view.findViewById(R.id.sort_btn);
        filter_btn.setOnClickListener(this);
        sort_btn.setOnClickListener(this);

        return view;//inflater.inflate(R.layout.fragment_scroll_animals, container, false);
    }


    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.filter_btn:
                getParentFragmentManager().beginTransaction()
                        .replace(R.id.fragment_place, new FiltersForAnimalFragment())
                        .addToBackStack(null)
                        .commit();
                break;
            case R.id.sort_btn:
                getParentFragmentManager().beginTransaction()
                        .replace(R.id.fragment_place, new SortForAnimalsFragment())
                        .addToBackStack(null)
                        .commit();
                break;

        }
    }

    private void setInitialData() {
        System.out.println("initialDataAnimals");
        animals =  ControllerDB.getController().getAnimalsList();
    }



}