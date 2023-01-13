package com.example.petbox;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RadioButton;

import java.util.ArrayList;

public class SortForAnimalsFragment extends Fragment implements View.OnClickListener {

    ArrayList<Animal> animals;
    ControllerDB controllerDB = ControllerDB.getController();
    int i=2;
    interface CallScrollFragment{
        public void callScrollAnimalsFragment(ArrayList<Animal> animals);
    }
    private  CallScrollFragment callScrollFragment;

    interface Back{
        public void goBack(Class cls);
    }

    private Back back;
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        callScrollFragment = (CallScrollFragment) context;
        back = (Back) context;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sort_for_animals, container, false);

        RadioButton
                newAds = view.findViewById(R.id.radio_newAds),
                oldAds = view.findViewById(R.id.radio_oldAds);
        newAds.setOnClickListener(this);
        oldAds.setOnClickListener(this);

        ImageButton back = view.findViewById(R.id.back_btn);
        back.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.radio_newAds:
                i=0;
                break;
            case R.id.radio_oldAds:
                i=1;
                break;
            case R.id.back_btn:
                if(i == 2) { back.goBack(this.getClass());
                }else {
                    if (i == 0) {
                        animals = ControllerDB.sortUpAge(controllerDB.getAnimalsList());
                        //controllerDB.setAnimalsFromDB(animals);
                    } else if (i == 1) {
                        animals = ControllerDB.sortDownAge(controllerDB.getAnimalsList());
                        //controllerDB.setAnimalsFromDB(animals);
                    }
                    callScrollFragment.callScrollAnimalsFragment(animals);
                }
                break;
        }
    }
}