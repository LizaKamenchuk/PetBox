package com.example.petbox;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ScrollHelpAdMeFragment extends Fragment  {
    ArrayList<HelpAd> helpAds;
    String email;
    //ArrayList<Animal> animals = ControllerDB.getController().getAnimalsByEmail(email);

    ScrollHelpAdMeFragment(String email){
        this.email = email;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_scroll_animals_me, container, false);
        setInitialData();
        // получаем элемент ListView
        RecyclerView recyclerView = view.findViewById(R.id.scrollanimalsMeList);
        System.out.println("ScrollAnimalMe "+ email);
        // создаем адаптер
        HelpAdAdapter.OnHelpClickListener helpClickListener = new HelpAdAdapter.OnHelpClickListener() {
            @Override

            public void onHelpAdClick(int position) {
                if(ControllerForUser.getController().getUser() != null) {
                    if (ControllerForUser.getController().getUser().getEmail().equals(email)) {
                        HelpAd helpAd = helpAds.get(position);
                        DeleteDialogFragment deleteDialogFragment = new DeleteDialogFragment(helpAd);
                        FragmentManager manager = getParentFragmentManager();
                        deleteDialogFragment.show(manager, "myDialog");
                    }
                }
            }
        };
        HelpAdAdapter adapter = new HelpAdAdapter(getContext(), helpAds, helpClickListener);
        // устанавливаем для списка адаптер
        recyclerView.setAdapter(adapter);

        return view;
    }

    private void setInitialData(){
        helpAds = ControllerDB.getController().getHelpAdsByEmail(email);
    }

}
