package com.example.petbox;

public class Controller {
    private int forCreationTabPosition;
    private static Controller controller1;
    private String email;
    private  Controller(){};

    public static synchronized Controller getController(){
        if(controller1==null)
            controller1 =new Controller();
        return controller1;
    }

    public void setPosition(int forCreationTabPosition){
        this.forCreationTabPosition=forCreationTabPosition;
    }

    public int getPosition(){
        return this.forCreationTabPosition;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
