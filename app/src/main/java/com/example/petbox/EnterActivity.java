package com.example.petbox;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class EnterActivity extends AppCompatActivity  {
    private User user;
    private final String URL = "http://"+Data.IP+"/Gradle___com_example___PetBoxEE_1_0_SNAPSHOT_war/enter";
    private RequestParams params;
    private AsyncHttpClient client;

    private Button enter,register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter);

        enter=(Button) findViewById(R.id.enterButton);
        register=(Button)findViewById(R.id.registerButton);
        enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView textView = findViewById(R.id.wrong);
                EditText email = findViewById(R.id.email);
                EditText pass = findViewById(R.id.password);

                String emailS=email.getText().toString();
                String password = pass.getText().toString();

                System.out.println(emailS+"  "+password);

                params=new RequestParams();
                params.put("email",emailS);
                params.put("password",password);

                client=new AsyncHttpClient();
                client.get(URL, params, new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        super.onSuccess(statusCode, headers, response);
                        try{
                            if (response.getInt("message") == Errors.SUCCESSFUL_AUTHORIZATION.getCode()) {
                                user = new User();
                                user.setEmail(response.getString("email"));
                                user.setPassword(response.getString("password"));
                                user.setName(response.getString("name"));
                                user.setLastname(response.getString("lastname"));
                                user.setAddress(response.getString("address"));
                                user.setShelter(response.getString("shelterName"));
                                System.out.println("В EnterActivity activity authorization: " + user.getEmail());
                                Toast.makeText(EnterActivity.this, Errors.SUCCESSFUL_AUTHORIZATION.getMessage().toString(), Toast.LENGTH_LONG).show();

                                ControllerForUser.getController().setUser(user);
                                System.out.println("ДАННЫЕ КОНТРОЛЛЕРА "+ControllerForUser.getController().getUser());
                                //ControllerForUser.getController().setUserEmail(response.getString("email"));
                                Intent intent = new Intent(EnterActivity.this, MajorActivity.class);
                                startActivity(intent);
                                finish();
                            }else Toast.makeText(EnterActivity.this, Errors.NON_EXISTENT_USER.getMessage(), Toast.LENGTH_SHORT).show();
                        }catch (Exception e){
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, String response, Throwable throwable) {
                        super.onFailure(statusCode, headers, response, throwable);
                        Toast.makeText(EnterActivity.this,Errors.NON_SUCCESSFUL_AUTHORIZATION.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });


            }
        });


        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EnterActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

    }



}