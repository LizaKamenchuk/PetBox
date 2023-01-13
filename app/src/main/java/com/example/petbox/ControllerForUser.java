package com.example.petbox;

public class ControllerForUser {
    private static ControllerForUser controller;
    private  ControllerForUser(){};
    private User user;
    //private String email="";

    public static synchronized ControllerForUser getController() {
        if (controller == null)
            controller = new ControllerForUser();
        return controller;
    }

    public void setUser(User user){
        this.user=user;
    }

    public User getUser(){
        return this.user;
    }

    /*public  void setUserEmail(String email){this.email = email;}
    public String getUserEmail(){return this.email;}*/

    public void removeUser(){
        this.user = null;
        //this.email = "";
    }
}
