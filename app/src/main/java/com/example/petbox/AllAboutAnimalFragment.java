package com.example.petbox;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class AllAboutAnimalFragment extends Fragment {
private Animal animal;
    ImageView photo;
    TextView name,genderAndAge,character,address,contact;

    AllAboutAnimalFragment(){}
    AllAboutAnimalFragment(Animal animal){
        this.animal = animal;
    }

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
 View view = inflater.inflate(R.layout.fragment_all_about_animal, container, false);
        photo=view.findViewById(R.id.photo_animals);
        photo.setImageResource(R.drawable.kotik);

        name=view.findViewById(R.id.name);
        name.setText(animal.getName());

        genderAndAge=view.findViewById(R.id.gender_age);
        genderAndAge.setText(animal.getGender()+", "+animal.getAge()+" "+animal.getAgeMeasure());

        character=view.findViewById(R.id.character);
        character.setText(animal.getCharacters());

        address=view.findViewById(R.id.address);
        address.setText("Место: "+ animal.getShelterName()+", "+animal.getAddress());

        contact=view.findViewById(R.id.contact);
        contact.setText("Контакты: "+animal.getContactName()+",  эл.почта: "+animal.getContactEmail());

        ImageButton back_btn = view.findViewById(R.id.back_btn);
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("BACK");
                back.goBack(AllAboutAnimalFragment.class);
            }
        });
        return view;
    }
}