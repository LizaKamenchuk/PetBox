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
import android.widget.Button;
import android.widget.ImageButton;

import java.util.ArrayList;

import javax.xml.transform.sax.SAXResult;

public class FilterForHelpFragment extends Fragment {
    String []typesOfHelp = {"Срочная","Материальная","Профессиональная","Обустройство приюта","Время с животными","Волонтёрство на мероприятиях","Перевозка","Передержка"};
   ArrayList<String> chosenTypes;

   interface FilterHelpAds{
       public void putFilterdHelpAds(ArrayList<HelpAd> helpAds);
   }

  private FilterHelpAds filterHelpAds;

   interface Back{
       public void goBack(Class cls);
   }

   private Back back;
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        filterHelpAds = (FilterHelpAds) context;
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
        View view = inflater.inflate(R.layout.fragment_help_typs, container, false);

        chosenTypes = new ArrayList<>();
        RecyclerView recyclerView = view.findViewById(R.id.helpTypesList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        // создаем адаптер
        CityAdapter.OnCityClickListener cityClickListener = new CityAdapter.OnCityClickListener() {
            @Override
            public void onCityClick(String city) {
                chosenTypes.add(city);
                System.out.println(chosenTypes.toString());
            }
            @Override
            public void onCityUnClick(String city) {
                chosenTypes.remove(city);
                System.out.println(chosenTypes.toString());
            }

        };
        CityAdapter adapter = new CityAdapter(getContext(),typesOfHelp,cityClickListener);
        // устанавливаем для списка адаптер
        recyclerView.setAdapter(adapter);

        Button acceptFilter = view.findViewById(R.id.accept_btn);
        acceptFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println(chosenTypes.toString());
                filterHelpAds.putFilterdHelpAds(ControllerDB.filterHelpAds(ControllerDB.getController().getHelpAdsList(),chosenTypes.toArray(new String[chosenTypes.size()])));
            }
        });

        ImageButton back_btn = view.findViewById(R.id.back_btn);
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                back.goBack(this.getClass());
            }
        });
        return view;
    }
}