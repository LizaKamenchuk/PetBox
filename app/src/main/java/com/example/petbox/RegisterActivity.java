package com.example.petbox;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONObject;

import java.util.Calendar;

import cz.msebera.android.httpclient.Header;


public class RegisterActivity extends AppCompatActivity {

    private User user;

    private EditText shelterNameET,cityET,nameET,lastnameET,emailET,passwordET, confirmpasswordET;
    private Button register;


    private final String URL = "http://"+Data.IP+"/Gradle___com_example___PetBoxEE_1_0_SNAPSHOT_war/register";
    private RequestParams params;
    private AsyncHttpClient client;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        shelterNameET = (EditText)findViewById(R.id.shelter_name);
        cityET=(EditText) findViewById(R.id.city);
        nameET=(EditText) findViewById(R.id.name);
        lastnameET=(EditText) findViewById(R.id.lastname);
        emailET=(EditText) findViewById(R.id.email);
        passwordET=(EditText) findViewById(R.id.password);
        confirmpasswordET = (EditText) findViewById(R.id.confirm_password);
        register=(Button) findViewById(R.id.registerButton);


        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String shelterName="",address = "", name = "", lastname = "", email = "", password = "", confirmPassword = "";
                user = new User();
                shelterName = shelterNameET.getText().toString();
                address = cityET.getText().toString();
                name = nameET.getText().toString();
                lastname = lastnameET.getText().toString();
                email = emailET.getText().toString();
                password = passwordET.getText().toString();
                confirmPassword = confirmpasswordET.getText().toString();

                if (!shelterName.equals("") && !address.equals("") && !name.equals("") && !lastname.equals("") && !email.equals("") && !password.equals("") && !confirmPassword.equals(password)) {
                    Toast.makeText(RegisterActivity.this, "Проверьте корректность введённых данных!", Toast.LENGTH_SHORT).show();
                } else {
                    user.setShelter(shelterName);
                    user.setAddress(address);
                    user.setName(name);
                    user.setLastname(lastname);
                    user.setEmail(email);
                    user.setPassword(password);

                    params = new RequestParams();
                    params.put("name", user.getName());
                    params.put("lastname", user.getLastname());
                    params.put("email", user.getEmail());
                    params.put("password", user.getPassword());
                    params.put("address", user.getAddress());
                    params.put("shelterName", user.getShelter());

                    client = new AsyncHttpClient();
                    client.post(URL, params, new JsonHttpResponseHandler() {

                        @Override
                        public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                            super.onSuccess(statusCode, headers, response);
                            try {
                                if (response.getInt("message") == Errors.SUCCESSFUL_REGISTRATION.getCode()) {
                                    Toast.makeText(RegisterActivity.this, Errors.SUCCESSFUL_REGISTRATION.getMessage(), Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(RegisterActivity.this, MajorActivity.class);
                                    startActivity(intent);
                                    System.out.println(user.getEmail()+" "+user.getShelter());
                                    ControllerForUser.getController().setUser(user);
                                    //ControllerForUser.getController().setUserEmail(user.getEmail());
                                    finish();
                                } else  Toast.makeText(RegisterActivity.this, Errors.EXISTENT_USER.getMessage(), Toast.LENGTH_SHORT).show();
                            }catch(Exception e){
                                e.printStackTrace();
                            }

                            ControllerForSearch.getController().setAllUsers();
                        }

                        @Override
                        public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                            super.onFailure(statusCode, headers, responseString, throwable);
                            Toast.makeText(RegisterActivity.this,Errors.SOMETHING_IS_WRONG.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });

    }

}