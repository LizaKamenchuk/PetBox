package com.example.petbox;

import android.app.Activity;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class ControllerForSearch {

    private static ControllerForSearch controller;
    private ControllerForSearch(){}
    private AsyncHttpClient client;

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    private Activity activity;
    private ArrayList<User> allUsers ;
    private User user;
    private final String URLusers = "http://"+Data.IP+"/Gradle___com_example___PetBoxEE_1_0_SNAPSHOT_war/GetAllUsersServlet";

    public static synchronized ControllerForSearch getController() {
        if (controller == null)
            controller = new ControllerForSearch();
        return controller;
    }

    public ArrayList<User> getAllUsers() {

        if(this.allUsers == null) setAllUsers();
        return allUsers;
    }

    public void setAllUsers() {
        allUsers = new ArrayList<>();
        client=new AsyncHttpClient();
        client.get(URLusers,new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                Animal animal;
                int i = 0;
                try {
                    for (; i < response.getInt("size"); i++) {
                        user = new User();
                        user.setEmail(response.getString("email" + i));
                        user.setAddress(response.getString("address" + i));
                        user.setShelter(response.getString("shelter" + i));
                        user.setName(response.getString("name" + i));
                        user.setLastname(response.getString("lastname"+i));

                        System.out.println(user.getEmail()+","+user.getLastname()+","+user.getName()+","+user.getShelter()+","+user.getAddress());

                        allUsers.add(user);
                        System.out.println(allUsers.get(i).getEmail());
                    }
                    activity.recreate();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String response, Throwable throwable) {
                super.onFailure(statusCode, headers, response, throwable);
                System.out.println("failure");
            }
        });

    }
    public  void setAllUsers(ArrayList<User> users){
    }

    public User getUserByEmail(String email){
        for(User user:this.allUsers){
            if(email.equals(user.getEmail())){
                return user;
            }
        }
        return null;
    }


}
