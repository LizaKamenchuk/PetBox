package com.example.petbox;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class CityFragment extends Fragment {
    String[] cities={"Минск","Мозырь","Баранович","Ельск","Гродно","Витебск"};
     ArrayList<String> chosenCities;

    interface Back{
        public void goBack(Class cls);
    }

    private Back back;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        back = (Back) context;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_city, container, false);

        chosenCities=new ArrayList<>();
        RecyclerView recyclerView = view.findViewById(R.id.cityList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        // создаем адаптер
        CityAdapter.OnCityClickListener cityClickListener = new CityAdapter.OnCityClickListener() {
            @Override
            public void onCityClick(String city) {
                chosenCities.add(city);
                System.out.println(chosenCities.toString());
            }
            @Override
            public void onCityUnClick(String city) {
                chosenCities.remove(city);
                System.out.println(chosenCities.toString());
            }

        };
        CityAdapter adapter = new CityAdapter(getContext(),cities,cityClickListener);
        // устанавливаем для списка адаптер
        recyclerView.setAdapter(adapter);


        AutoCompleteTextView autoCompleteTextView = view.findViewById(R.id.autocomplete);
        // Создаем адаптер для автозаполнения элемента AutoCompleteTextView
        ArrayAdapter adapterComplete = new ArrayAdapter (getContext(),android.R.layout.simple_dropdown_item_1line, cities);
        autoCompleteTextView.setAdapter(adapterComplete);
        AutoCompleteTextView autoComplete = view.findViewById(R.id.autocomplete);
        autoComplete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String cityET = autoComplete.getText().toString();
                int i=0;
                for (String s : cities) {
                    if (s.equals(cityET)) {
                        String cityChange = cities[0];
                        cities[0]=cityET;
                        cities[i] = cityChange;
                        chosenCities.removeAll(chosenCities);
                        recyclerView.setAdapter(adapter);
                    }
                    i++;
                }
            }
        });


        Button acceptFilter = view.findViewById(R.id.accept_btn);
        acceptFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(chosenCities.isEmpty()){back.goBack(this.getClass());}
                else ControllerFilter.getController().setCities(chosenCities);
            }
        });

        ImageButton back_btn = view.findViewById(R.id.back_btn);
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               back.goBack(CityFragment.class);
            }
        });

        return view;
    }
}