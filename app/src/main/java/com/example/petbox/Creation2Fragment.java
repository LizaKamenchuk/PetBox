package com.example.petbox;

import static com.example.petbox.Controller.getController;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;

public class Creation2Fragment extends Fragment {
    TabLayout tabLayout;
    TabItem tabAnimal,tabHelp;
    ViewPager2 viewPager;
    PagerCreationAdapter adapter;
    Button create;
    ImageButton back_btn;
    int forCreateTabPosition;
   


    public void setForCreateTabPosition(int forCreateTabPosition) {
        this.forCreateTabPosition = forCreateTabPosition;
    }

    public int getForCreateTabPosition() {
        return this.forCreateTabPosition;
    }


    public interface DataSender{
        public void sender();
    }
    public interface BackGoing{
        public void goBack(Class cls);
    }
    private BackGoing goBack;
    private DataSender sender;
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        sender = (DataSender) context;
        goBack = (BackGoing) context;

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_creation2, container, false);
        tabLayout = view.findViewById(R.id.tabLayout);
        tabAnimal = view.findViewById(R.id.tabAnimal);
        tabHelp = view.findViewById(R.id.tabHelp);
        viewPager = view.findViewById(R.id.viewPager);

        adapter=new PagerCreationAdapter(getActivity());
        viewPager.setAdapter(adapter);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
               viewPager.setCurrentItem(tab.getPosition());
                setForCreateTabPosition(tab.getPosition());
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


        create = view.findViewById(R.id.create_btn);
        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println(forCreateTabPosition);
                sender.sender();
            }
        });

        back_btn = view.findViewById(R.id.back_btn);
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goBack.goBack(Creation2Fragment.class);
            }
        });


        return view;
    }
}