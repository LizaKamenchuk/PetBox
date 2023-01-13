package com.example.petbox;

import static java.lang.Integer.valueOf;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

public class ChangeAnimalFragment extends Fragment implements View.OnClickListener {
    private Animal animal;

    ControllerDB controllerDB = ControllerDB.getController();
    private AutoCompleteTextView autoCompleteType,autoCompleteAge,autoCompleteColor;
    private TextView age_tv;
    private EditText characterET,nameET;
    ImageView photo;
    private String type="",ageMeasure="", gender="",character="",name="",color="";
    private int age = 0;

    int position;
    ChangeAnimalFragment(Animal animal){
        this.animal = animal;
    }

    interface Back{
        public void goBack(Class cls);
    }
    private Back back;

    interface GoToFragment{
        public void goToMeFragment();
    }
private GoToFragment goToFragment;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        back = (Back) context;
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
        View view = inflater.inflate(R.layout.fragment_change_animal, container, false);

        autoCompleteType = view.findViewById(R.id.autoCompleteType);
        autoCompleteAge = view.findViewById(R.id.autoCompleteAge);
        autoCompleteColor = view.findViewById(R.id.autoCompleteColor);

        //We will use this data to inflate the drop-down items
        String[] types = new String[]{"кот", "собака", "другое животное"};
        String[] ages = new String[]{"месяцев","лет"};
        String[] colors = new String[]{"белый","чёрный","серый","рыжий","разноцветный"};


        ArrayAdapter<String> adapterType = new ArrayAdapter<>(getContext(), R.layout.type_menu_item, types);
        autoCompleteType.setAdapter(adapterType);
        ArrayAdapter<String> genderAdapter = new ArrayAdapter<>(getContext(),R.layout.age_menu_item,ages);
        autoCompleteAge.setAdapter(genderAdapter);
        ArrayAdapter<String> adapterColor = new ArrayAdapter<>(getContext(),R.layout.type_menu_item,colors);
        autoCompleteColor.setAdapter(adapterColor);


        autoCompleteType.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                type=types[position];
            }
        });
        autoCompleteAge.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ageMeasure=ages[position];
            }
        });
        autoCompleteColor.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                color = colors[position];
            }
        });

        age_tv=view.findViewById(R.id.ageView);
        Button plus = view.findViewById(R.id.addButton),
                minus = view.findViewById(R.id.removeButton),
                change = view.findViewById(R.id.changeBtn),
                delete = view.findViewById(R.id.deleteBtn);
        plus.setOnClickListener(this);
        minus.setOnClickListener(this);
        change.setOnClickListener(this);
        delete.setOnClickListener(this);

        RadioButton boy = view.findViewById(R.id.boy_rbtn),
                girl=view.findViewById(R.id.girl_rbtn);
        boy.setOnClickListener(this);
        girl.setOnClickListener(this);

        photo = view.findViewById(R.id.photoAdded);
        ImageButton back = view.findViewById(R.id.back_btn);
        back.setOnClickListener(this);

        characterET = view.findViewById(R.id.characterET);
        nameET = view.findViewById(R.id.name);

        photo.setImageResource(R.drawable.kotik);
        nameET.setText(animal.getName());
        autoCompleteType.setText(animal.getType());
        if(animal.getGender().equals("девочка")) girl.setChecked(true);
        else boy.setChecked(true);
        String age = Integer.toString(animal.getAge());
        age_tv.setText(age);
        autoCompleteColor.setText(animal.getColor());
        autoCompleteAge.setText(animal.getAgeMeasure());
        characterET.setText(animal.getCharacters());
        gender = animal.getGender();

        return view;
    }

    @Override
    public void onClick(View view) {
        age = valueOf(age_tv.getText().toString());
        ImageButton imageButton = view.findViewById(R.id.photoAdded);
        String a;
        switch (view.getId()) {
            case R.id.addButton:
                age = age + 1;
                a = Integer.toString(age);
                age_tv.setText(a);
                break;
            case R.id.removeButton:
                age = age - 1;
                if (age < 0) age = 0;
                a = Integer.toString(age);
                age_tv.setText(a);
                break;
            case R.id.boy_rbtn:
                gender = "мальчик";
                break;
            case R.id.girl_rbtn:
                gender = "девочка";
                break;
           /* case R.id.photoAdded:
                //imageButton.setImageResource();
                break;*/
            case R.id.back_btn:
                back.goBack(this.getClass());
                break;
            case R.id.changeBtn:
                animal.setName(nameET.getText().toString());
                animal.setGender(gender);
                animal.setType(autoCompleteType.getText().toString());
                animal.setCharacters(characterET.getText().toString());
                animal.setAge(age);
                animal.setAgeMeasure(autoCompleteAge.getText().toString());
                animal.setColor(autoCompleteColor.getText().toString());
                animal.setPhoto(photo.getId());

                controllerDB.updateAnimal(animal);
                goToFragment.goToMeFragment();
                break;
            case R.id.deleteBtn:
                DeleteDialogFragment deleteDialogFragment = new DeleteDialogFragment(animal);
                FragmentManager manager = getParentFragmentManager();
                deleteDialogFragment.show(manager, "myDialog2");
                break;
        }
    }

}