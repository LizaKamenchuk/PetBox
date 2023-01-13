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
import android.widget.EditText;
import android.widget.ImageButton;

import java.util.ArrayList;

public class SearchFragment extends Fragment {

ImageButton searchBtn;
EditText searchET;
ArrayList<User> users;
ArrayList<User> searchedUsers;

interface GoToFragment{
    public void goToShelterPage(User user);
}

private GoToFragment goToFragment;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        goToFragment = (GoToFragment) context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_book, container, false);

        searchBtn = view.findViewById(R.id.searchBtn);
        searchET = view.findViewById(R.id.searchET);
        users = ControllerForSearch.getController().getAllUsers();
        searchedUsers = new ArrayList<>();

        RecyclerView recyclerView = view.findViewById(R.id.searchedUsers);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        // создаем адаптер
        BookAdapter.OnBookClickListener onBookClickListener = new BookAdapter.OnBookClickListener() {
            @Override
            public void onBookClick(int position) {
                goToFragment.goToShelterPage(searchedUsers.get(position));
            }
        };
        BookAdapter adapter = new BookAdapter(getContext(),searchedUsers,onBookClickListener);
        // устанавливаем для списка адаптер
        recyclerView.setAdapter(adapter);

        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchedUsers.clear();
                System.out.println(searchET.getText().toString());
               if (!searchET.getText().toString().equals(null)){
                   for (User user :users) {
                       if (searchET.getText().toString().equals(user.getShelter())){
                           searchedUsers.add(user);
                           System.out.println(user.getEmail());
                       }
                   }
               }
                // устанавливаем для списка адаптер
                recyclerView.setAdapter(adapter);
            }
        });


        return view;
    }
}