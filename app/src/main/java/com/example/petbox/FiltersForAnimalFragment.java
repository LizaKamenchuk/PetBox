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
import android.widget.ImageButton;

import java.util.ArrayList;

public class FiltersForAnimalFragment extends Fragment implements View.OnClickListener {

    boolean flagC=true;
    boolean flagD=true;
    boolean flagO=true;
    private ControllerFilter controllerFilter = ControllerFilter.getController();
    ArrayList<Animal> animals;

    public enum Filters{
                AGE ,
                COLOR ,
                CITY
    }
    ArrayList<String> chosenAnimals = new ArrayList<>();

    interface GoToFragment{
        public void goToFilter(Class cls);
        public void goToScrollFragment(ArrayList<Animal> animals);
    }

    private GoToFragment goToFragment;

    interface Back{
        public void goBack(Class cls);
    }

    private Back back;

    interface RecreateFragments{
        public void  recreateAgeColorCity();
    }

    private RecreateFragments recreateFragments;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        goToFragment= (GoToFragment) context;
        back = (Back) context;
        recreateFragments = (RecreateFragments) context;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_filter, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.filters_list);
        // создаем адаптер
       FiltersAdapter.OnFilterClickListener filterClickListener = new FiltersAdapter.OnFilterClickListener() {
            @Override
            public void onFilterClick(int position) {
                if (savedInstanceState == null) {
                    Filters filter = Filters.values()[position];
                    switch (filter){
                        case AGE:
                            goToFragment.goToFilter(AgeFragment.class);
                            break;
                        case COLOR:
                            goToFragment.goToFilter(ColorFragment.class);
                            break;
                        case CITY:
                            goToFragment.goToFilter(CityFragment.class);
                            break;

                    }
                }
            }
        };
        FiltersAdapter adapter = new FiltersAdapter(getContext(),filterClickListener);
        // устанавливаем для списка адаптер
        recyclerView.setAdapter(adapter);

        ImageButton
                cats=view.findViewById(R.id.btn_cats),
                dogs=view.findViewById(R.id.btn_dogs),
                others=view.findViewById(R.id.btn_others),
                back = view.findViewById(R.id.back_btn);
        cats.setOnClickListener(this);
        dogs.setOnClickListener(this);
        others.setOnClickListener(this);
        back.setOnClickListener(this);

        Button reset = view.findViewById(R.id.reset_dtn),
                accept = view.findViewById(R.id.show_btn);

        reset.setOnClickListener(this);
        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                animals = new ArrayList<>();
                animals = ControllerDB.getController().getAnimalsList();
                if(!chosenAnimals.isEmpty()) {
                    controllerFilter.setTypes(chosenAnimals);
                    animals = ControllerFilter.filterByType(animals,controllerFilter.getTypes());
                }
                if(!controllerFilter.getAgeMeasure().equals("")){
                    animals = ControllerFilter.filterByAge(animals,controllerFilter.getFromAge(),
                            controllerFilter.getToAge(),controllerFilter.getAgeMeasure());
                }
                if(!controllerFilter.getColors().isEmpty()){
                    animals = ControllerFilter.filterByColors(animals,controllerFilter.getColors());
                }
                if(!controllerFilter.getCities().isEmpty()){
                    animals = ControllerFilter.filterByCities(animals,controllerFilter.getCities());
                }

                goToFragment.goToScrollFragment(animals);
            }

        });

        return view;
    }
    @Override
    public void onClick(View view){
        ImageButton cats=view.findViewById(R.id.btn_cats);
        ImageButton dogs = view.findViewById(R.id.btn_dogs);
        ImageButton others = view.findViewById(R.id.btn_others);
        switch (view.getId()) {
            case R.id.btn_cats:
                if (flagC) {
                    cats.setImageResource(R.drawable.cat_b);
                    chosenAnimals.add("кот");
                } else {cats.setImageResource(R.drawable.cat_w);
                chosenAnimals.remove("кот");}
                flagC = !flagC;
                break;
            case R.id.btn_dogs:
                if (flagD) {
                    dogs.setImageResource(R.drawable.dog_b);
                    chosenAnimals.add("собака");
                } else{ dogs.setImageResource(R.drawable.dog_w);
                chosenAnimals.remove("собака");}
                flagD = !flagD;
                break;
            case R.id.btn_others:
                if (flagO) {
                    others.setImageResource(R.drawable.others_b);
                    chosenAnimals.add("другое");
                } else {others.setImageResource(R.drawable.others_w);
                    chosenAnimals.remove("другое");}
                flagO = !flagO;
                break;
            case R.id.back_btn:
                back.goBack(FiltersForAnimalFragment.class);
                break;
            case R.id.reset_dtn:
                if(!flagC) cats.setImageResource(R.drawable.cat_w);
                if(!flagD) dogs.setImageResource(R.drawable.dog_w);
                if(!flagO) others.setImageResource(R.drawable.others_w);
                chosenAnimals.clear();
                ControllerFilter.getController().clearFilter();
                recreateFragments.recreateAgeColorCity();
                break;

        }
    }
}