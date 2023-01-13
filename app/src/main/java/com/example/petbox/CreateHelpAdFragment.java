package com.example.petbox;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;


public class CreateHelpAdFragment extends Fragment{
    HelpAd helpAd;
    private ArrayList<HelpAd> helpAds = ControllerDB.getController().getHelpAdsList();
    EditText descriptionET;
    AutoCompleteTextView autoCompleteType;
    String type="",description="",shelter_id;
    int position;

    private final String URL = "http://"+Data.IP+"/Gradle___com_example___PetBoxEE_1_0_SNAPSHOT_war/HelpAdServlet";
    private RequestParams params;
    private AsyncHttpClient client;

    public void setPosition(int position) {
        this.position = position;
        sendInDB();
    }

    String []typesOfHelp = {"Срочная","Материальная","Профессиональная","Обустройство приюта","Время с животными","Волонтёрство на мероприятиях","Перевозка","Передержка"};
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_create_help_ad, container, false);
        ControllerDB.getController().setViewHelpAd(view);
        autoCompleteType = view.findViewById(R.id.autoCompleteType);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(),android.R.layout.simple_dropdown_item_1line,typesOfHelp);
        autoCompleteType.setAdapter(adapter);

        autoCompleteType.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                type=typesOfHelp[position];
            }
        });

        descriptionET = view.findViewById(R.id.helpDescription);
        return view;
    }

    public void sendInDB() {
        if(position==1){
            View view = ControllerDB.getController().getViewHelpAd();
            descriptionET=view.findViewById(R.id.helpDescription);
            autoCompleteType = view.findViewById(R.id.autoCompleteType);
            System.out.println("help");

            ControllerForUser controller = ControllerForUser.getController();

            description = descriptionET.getText().toString();
            type = autoCompleteType.getText().toString();
            shelter_id = controller.getUser().getEmail();

            if(!type.equals("") && !description.equals("")){
                helpAd = new HelpAd(type,description,shelter_id);
                helpAd.setContactName(controller.getUser().getName());
                helpAd.setAddress(controller.getUser().getAddress());
                helpAd.setShelterName(controller.getUser().getShelter());


                params = new RequestParams();
                params.put("type",helpAd.getType());
                params.put("description",helpAd.getDescription());
                params.put("shelter_id",shelter_id);

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
                        //helpAds.add(helpAd);
                       //ControllerDB.getController().setHelpAdsList(helpAds);
                        ControllerDB.getController().addHelpAd(helpAd);
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                        super.onFailure(statusCode, headers, responseString, throwable);
                        Toast.makeText(view.getContext(),Errors.SOMETHING_IS_WRONG.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

            }else{
                Toast.makeText(view.getContext(), "Заполните все поля", Toast.LENGTH_SHORT).show();
            }
        }
    }
}