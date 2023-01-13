package com.example.petbox;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;

public class MeFragment extends Fragment {
private User user;

private  TextView shelterName,contacts,address,petsAmount,postsAmount;
    private  TabLayout tabLayout;
    private TabItem tabAnimal,tabHelp;
   private ViewPager2 viewPager;
   private PagerScrollAdapter adapter;
   private ControllerDB controllerDB = ControllerDB.getController();
   private String searchedEmail;

   MeFragment(){}
    MeFragment(User user){
       this.user = user;
    }
    MeFragment(String searchedEmail){
       this.searchedEmail = searchedEmail;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fragment_me, container, false);
        if(searchedEmail == null)searchedEmail = ControllerForUser.getController().getUser().getEmail();
        if(this.user == null){
            this.user = ControllerForSearch.getController().getUserByEmail(searchedEmail);
        }
        //if(key == false) user =  ControllerForUser.getController().getUser();
        //ControllerDB.getController().setHelpAdsForMe(user.getEmail());
        //ControllerDB.getController().setAnimalsForMe(user.getEmail());
        //Controller.getController().setEmail(user.getEmail());
        System.out.println("MeFragment "+user.getEmail());
        shelterName = view.findViewById(R.id.shelter_name);
        contacts = view.findViewById(R.id.shelterContact);
        address= view.findViewById(R.id.shelterAddress);
        petsAmount = view.findViewById(R.id.pets_amountTV);
        postsAmount= view.findViewById(R.id.posts_amountTV);


        petsAmount.setText(String.valueOf(controllerDB.getAnimalsByEmail(user.getEmail()).size()));
        postsAmount.setText(String.valueOf(controllerDB.getHelpAdsByEmail(user.getEmail()).size()));
        shelterName.setText(user.getShelter());
        contacts.setText(user.getName()+" "+user.getLastname()+" почта: "+user.getEmail());
        address.setText(user.getAddress());

        tabLayout =view.findViewById(R.id.tabLayout);
        tabAnimal = view.findViewById(R.id.tabAnimal);
        tabHelp= view.findViewById(R.id.tabHelp);

        viewPager = view.findViewById(R.id.viewPager);

        adapter=new PagerScrollAdapter(getActivity(), user.getEmail());
        viewPager.setAdapter(adapter);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }

        });

        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                tabLayout.getTabAt(position).select();
            }
        });

        return view;
    }
}