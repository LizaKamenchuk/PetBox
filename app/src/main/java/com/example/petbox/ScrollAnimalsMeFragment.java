package com.example.petbox;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;


public class ScrollAnimalsMeFragment extends Fragment  {
    ArrayList<Animal> animals;
    private String email;
    //String email = Controller.getController().getEmail();

    ScrollAnimalsMeFragment(String email){
        this.email = email;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_scroll_animals_me, container, false);
        setInitialData();
        RecyclerView recyclerView = view.findViewById(R.id.scrollanimalsMeList);
        // создаем адаптер
        System.out.println("Animal in ScrollAnimalMeFragment 1");
            AnimalsAdapter.OnAnimalClickListener animalClickListener = new AnimalsAdapter.OnAnimalClickListener() {
                @Override
                public void onAnimalClick(int position) {
                    if(ControllerForUser.getController().getUser() != null) {
                        if (ControllerForUser.getController().getUser().getEmail().equals(email)) {
                            ChangeAnimalFragment changeAnimalFragment = new ChangeAnimalFragment(animals.get(position));
                            getParentFragmentManager().beginTransaction()
                                    .replace(R.id.fragment_place, changeAnimalFragment)
                                    .addToBackStack(null)
                                    .commit();
                        }
                    }
                }
            };
        AnimalsAdapter adapter = new AnimalsAdapter(getContext(), animals, animalClickListener);
        // устанавливаем для списка адаптер
        recyclerView.setAdapter(adapter);
        return view;
    }

    private void setInitialData() {
        animals = ControllerDB.getController().getAnimalsByEmail(email);
    }

}