package com.example.petbox;

import static java.lang.Integer.valueOf;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

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
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;


public class CreateAnimalFragment extends Fragment implements View.OnClickListener{
    private Animal animal;
    private ArrayList<Animal> animals = ControllerDB.getController().getAnimalsList();

    private  AutoCompleteTextView autoCompleteType,autoCompleteAge,autoCompleteColor;
    private TextView age_tv;
    private RadioButton boy,girl;
    private EditText characterET,nameET;
    private ImageView photo;
    private String type="",ageMeasure="", gender="",character="",name="",color="",shelter_id;
    private int age = 0;

    int position;

    private final String URL = "http://"+Data.IP+"/Gradle___com_example___PetBoxEE_1_0_SNAPSHOT_war/animal";
    private RequestParams params;
    private AsyncHttpClient client;

    public void setPosition(int position) {
        this.position = position;
        sendInDB();
    }


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_create_animal, container, false);

        ControllerDB.getController().setViewAnimal(view);
        autoCompleteType = view.findViewById(R.id.autoCompleteType);
        autoCompleteAge = view.findViewById(R.id.autoCompleteAge);
        autoCompleteColor = view.findViewById(R.id.autoCompleteColor);

        //We will use this data to inflate the drop-down items
        String[] types = new String[]{"кот", "собака", "другое животное"};
        String[] ages = new String[]{"месяцев","лет"};
        String[] colors = new String[]{"белый","чёрный","серый","рыжий","разноцветный"};

        photo=view.findViewById(R.id.photoAdded);
        characterET = view.findViewById(R.id.characterET);
        nameET = view.findViewById(R.id.name);

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
              if(types[position].toString().equals("кот"))
                  photo.setImageResource(R.drawable.kotik);
              if(types[position].toString().equals("собака"))
                  photo.setImageResource(R.drawable.dog);
              if(types[position].toString().equals("другое животное"))
                  photo.setImageResource(R.drawable.others);
            }
        });

        age_tv=view.findViewById(R.id.ageView);
        Button plus = view.findViewById(R.id.addButton),
                minus = view.findViewById(R.id.removeButton);
        plus.setOnClickListener(this);
        minus.setOnClickListener(this);

         boy = view.findViewById(R.id.boy_rbtn);
         girl=view.findViewById(R.id.girl_rbtn);
       boy.setOnClickListener(this);
        girl.setOnClickListener(this);

        /*ImageButton photo = view.findViewById(R.id.photoAdded);
        photo.setOnClickListener(this);*/


        return view;
    }

    @Override
    public void onClick(View view) {
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
            /*case R.id.boy_rbtn:
                gender = "мальчик";
                break;
            case R.id.girl_rbtn:
                gender = "девочка";
                break;*/
            /*case R.id.photoAdded:
                //imageButton.setImageResource();
                break;*/
        }
    }

    private void sendInDB() {

        System.out.println("ANIMAL");
        if (position == 0) {

            View view = ControllerDB.getController().getViewAnimal();
            photo=view.findViewById(R.id.photoAdded);
            characterET = view.findViewById(R.id.characterET);
            nameET = view.findViewById(R.id.name);
            age_tv= view.findViewById(R.id.ageView);
            autoCompleteType = view.findViewById(R.id.autoCompleteType);
            autoCompleteAge = view.findViewById(R.id.autoCompleteAge);
            autoCompleteColor = view.findViewById(R.id.autoCompleteColor);
            boy = view.findViewById(R.id.boy_rbtn);

            if(boy.isChecked())gender = "мальчик";
            else gender = "девочка";
ControllerForUser controller = ControllerForUser.getController();

            name = nameET.getText().toString();
            character = characterET.getText().toString();
            shelter_id =  controller.getUser().getEmail();
            System.out.println("shelter_id в создании животного " + shelter_id);
            age = valueOf(age_tv.getText().toString());
            int photoAnimal = photo.getId();
            type = autoCompleteType.getText().toString();
            color = autoCompleteColor.getText().toString();
            ageMeasure = autoCompleteAge.getText().toString();

            if (!name.equals("") && !type.equals("") && !gender.equals("") && !ageMeasure.equals("") && !character.equals("")
                    && !color.equals("") ) {
                animal = new Animal(type,name,character,color,gender,age,ageMeasure,photoAnimal,shelter_id);
                animal.setContactName(controller.getUser().getName());
                animal.setAddress(controller.getUser().getAddress());
                animal.setShelterName(controller.getUser().getShelter());
               //animal.setContactEmail(ControllerForUser.getController().getUser().getEmail());
                params = new RequestParams();

                params.put("name", animal.getName());
                params.put("type", animal.getType());
                params.put("color", animal.getColor());
                params.put("age",animal.getAge());
                params.put("gender", animal.getGender());
                params.put("characters", animal.getCharacters());
                params.put("photo", animal.getPhoto());
                params.put("ageMeasure",animal.getAgeMeasure());
                params.put("shelter_id", shelter_id);

                client = new AsyncHttpClient();
                client.post(URL, params, new JsonHttpResponseHandler() {

                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        super.onSuccess(statusCode, headers, response);
                        try {
                            if (response.getInt("message") == Errors.SUCCESSFUL_CREATION.getCode()) {
                                System.out.println(Errors.SUCCESSFUL_CREATION.getMessage());
                                 Toast.makeText(view.getContext(), Errors.SUCCESSFUL_CREATION.getMessage(), Toast.LENGTH_SHORT).show();

                            } else
                             Toast.makeText(view.getContext(), Errors.SOMETHING_IS_WRONG.getMessage(), Toast.LENGTH_SHORT).show();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        ControllerDB.getController().addAnimal(animal);

                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                        super.onFailure(statusCode, headers, responseString, throwable);
                        Toast.makeText(view.getContext(),Errors.SOMETHING_IS_WRONG.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
            else {
                Toast.makeText(view.getContext(), "Заполните все поля", Toast.LENGTH_SHORT).show();
        }
    }
}
}
