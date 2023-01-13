package com.example.petbox;

import static java.util.Collections.reverse;
import static java.util.Collections.sort;

import android.app.Activity;
import android.view.View;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import cz.msebera.android.httpclient.Header;

public class ControllerDB {
    private static ControllerDB controller;
    private  ControllerDB(){
        //animals = new ArrayList<>();
        animalsForMe = new ArrayList<>();
        //helpAds = new ArrayList<>();
        helpAdsForMe = new ArrayList<>();
    }
    private View viewAnimal;
    private View viewHelpAd;
    private ArrayList<Animal> animals ;
    private ArrayList<HelpAd> helpAds;
    private ArrayList<Animal> animalsForMe;
    private ArrayList<HelpAd> helpAdsForMe;
    private final String URLanimal = "http://"+Data.IP+"/Gradle___com_example___PetBoxEE_1_0_SNAPSHOT_war/animal";
    private final String URLhelp = "http://"+Data.IP+"/Gradle___com_example___PetBoxEE_1_0_SNAPSHOT_war/HelpAdServlet";
    private final String URLupdateAnimal = "http://"+Data.IP+"/Gradle___com_example___PetBoxEE_1_0_SNAPSHOT_war/UpdateAnimalServlet";
    private final String URLdeleteAnimal = "http://"+Data.IP+"/Gradle___com_example___PetBoxEE_1_0_SNAPSHOT_war/DeleteAnimalServlet";
    private final String URLdeleteHelpAd = "http://"+Data.IP+"/Gradle___com_example___PetBoxEE_1_0_SNAPSHOT_war/DeleteHelpAdServlet";
    private AsyncHttpClient client;
    private Activity activity;

    public void setActivity(Activity activity){
        this.activity= activity;
    }

    public View getViewAnimal() {
        return viewAnimal;
    }

    public void setViewAnimal(View view) {
        this.viewAnimal = view;
    }

    public View getViewHelpAd() {
        return viewHelpAd;
    }

    public void setViewHelpAd(View viewHelpAd) {
        this.viewHelpAd = viewHelpAd;
    }


    public static synchronized ControllerDB getController() {
        if (controller == null)
            controller = new ControllerDB();
        return controller;
    }

    public void updateAnimal(Animal animal){
        updateAnimalForMe(animal);
        System.out.println(animalsForMe.size());
        for(int i = 0;i<this.animals.size();i++){
            if(this.animals.get(i).getId()==animal.getId()){
                this.animals.set(i,animal);
                updateAnimalInDB(animal);
                break;
            }

        }
    }

    public void updateAnimalForMe(Animal animal){
        System.out.println(animal.getId()+", "+animal.getName()+", "+animal.getType()+", "+animal.getAge()+", "+animal.getColor()+", "+animal.getGender()+", "+animal.getAddress());
        for(int i = 0;i<this.animalsForMe.size();i++){
            System.out.println(animal.getId()+", "+animal.getName()+", "+animal.getType()+", "+animal.getAge()+", "+animal.getColor()+", "+animal.getGender()+", "+animal.getAddress());
            if(this.animalsForMe.get(i).getId()==animal.getId()){
                this.animalsForMe.set(i,animal);
                break;
            }

        }
    }

    public void deleteAnimal(Animal animal){
        deleteAnimalForMe(animal);
        for(int i = 0;i<animals.size();i++){
            if(animals.get(i).getId()==animal.getId()){
                this.animals.remove(i);
                deleteAnimalInDB(animal);
                break;
            }
        }
    }
    public void deleteAnimalForMe(Animal animal){
        for(int i = 0;i<animalsForMe.size();i++){
            if(animalsForMe.get(i).getId()==animal.getId()){
                this.animalsForMe.remove(i);
                deleteAnimalInDB(animal);
                break;
            }
        }
    }

    public void deleteAnimalInDB(Animal animal){
        AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("id",animal.getId());

        asyncHttpClient.post(URLdeleteAnimal,params,new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                System.out.println(Errors.SUCCESSFUL_DELETE.getMessage());
                //try {
                   // if (response.getInt("message") == Errors.SUCCESSFUL_EDIT.getCode())
                        Toast.makeText(activity, Errors.SUCCESSFUL_DELETE.getMessage(), Toast.LENGTH_SHORT).show();
                //}catch (Exception e){
                  //  e.printStackTrace();
               // }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                System.out.println(Errors.SOMETHING_IS_WRONG.getMessage());
                Toast.makeText(activity,Errors.SOMETHING_IS_WRONG.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void deleteHelpAd(HelpAd helpAd){
        deleteHelpAdForMe(helpAd);
        for(int i = 0;i<helpAds.size();i++){
            if(helpAds.get(i).getId()==helpAd.getId()){
                this.helpAds.remove(i);
                deleteHelpAdInDB(helpAd);
                break;
            }
        }
    }

    public void deleteHelpAdForMe(HelpAd helpAd){
        for(int i = 0;i<helpAdsForMe.size();i++){
            if(helpAdsForMe.get(i).getId()==helpAd.getId()){
                this.helpAdsForMe.remove(i);
                break;
            }
        }
    }
    public void deleteHelpAdInDB(HelpAd helpAd){
        AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("id",helpAd.getId());

        asyncHttpClient.post(URLdeleteHelpAd,params,new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                try {
                    if (response.getInt("message") == Errors.SUCCESSFUL_EDIT.getCode())
                        Toast.makeText(activity, Errors.SUCCESSFUL_DELETE.getMessage(), Toast.LENGTH_SHORT).show();
                }catch (Exception e){
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                Toast.makeText(activity,Errors.SOMETHING_IS_WRONG.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void updateAnimalInDB(Animal animal){
        AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("id",animal.getId());
        params.put("photo",animal.getPhoto());
        params.put("color",animal.getColor());
        params.put("type",animal.getType());
        params.put("name",animal.getName());
        params.put("characters",animal.getCharacters());
        params.put("gender", animal.getGender());
        params.put("age",animal.getAge());
        params.put("ageMeasure",animal.getAgeMeasure());
        params.put("shelter_id",animal.getContactEmail());

        asyncHttpClient.post(URLupdateAnimal,params,new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                try {
                    if(response.getInt("message") == Errors.SUCCESSFUL_EDIT.getCode())
                        System.out.println(Errors.SUCCESSFUL_EDIT);
                    Toast.makeText(activity,Errors.SUCCESSFUL_EDIT.getMessage(),Toast.LENGTH_SHORT).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                System.out.println(Errors.SOMETHING_IS_WRONG);
                Toast.makeText(activity,Errors.SOMETHING_IS_WRONG.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
        setAnimalsFromDB();
    }

    public  ArrayList<Animal> getAnimalsList()  {
        System.out.println("return Animal list");
        if(this.animals == null) setAnimalsFromDB();
        return this.animals;
    }

    /*public void setAnimalsList(ArrayList<Animal> animals){
        System.out.println("set new List");
        this.animals = animals;
    }*/
    public void setAnimalsFromDB() {
        System.out.println("set Animals List");
        animals = new ArrayList<>();
        client=new AsyncHttpClient();
        client.get(URLanimal,new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                Animal animal;
                int i = 0;
                try {
                        for (; i < response.getInt("size"); i++) {
                            animal = new Animal();
                            animal.setId(response.getInt("id" + i));
                            animal.setType(response.getString("type" + i));
                            animal.setPhoto(response.getInt("photo" + i));
                            animal.setName(response.getString("name" + i));
                            animal.setColor(response.getString("color" + i));
                            animal.setAge(response.getInt("age" + i));
                            animal.setAgeMeasure(response.getString("ageMeasure" + i));
                            animal.setGender(response.getString("gender" + i));
                            animal.setCharacters(response.getString("characters" + i));
                            animal.setAddress(response.getString("address" + i));
                            animal.setShelterName(response.getString("shelterName" + i));
                            animal.setContactName(response.getString("contactName" + i));
                            animal.setContactEmail(response.getString("contactEmail" + i));
                            animals.add(animal);
                            System.out.println(animals.get(i).getId());
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

    public ArrayList<HelpAd> getHelpAdsList() {
        System.out.println("return List HelpAds");
        if(this.helpAds == null) setHelpAdsList();
        return this.helpAds;
    }

    public void setHelpAdsList(ArrayList<HelpAd> helpAds){
        System.out.println("set new List");
        this.helpAds = helpAds;
    }

    public void addHelpAd(HelpAd helpAd){
        this.helpAds.add(helpAd);
        this.helpAdsForMe.add(helpAd);
    }

    public void setHelpAdsList() {
        System.out.println("set HelpAds List");
        helpAds = new ArrayList<>();
        client=new AsyncHttpClient();
        client.get(URLhelp, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                HelpAd helpAd;
                try {
                    for (int i = 0; i < response.getInt("size"); i++) {
                        helpAd =  new HelpAd();
                        helpAd.setId(response.getInt("id"+i));
                        helpAd.setType(response.getString("type"+i));
                        helpAd.setDescription(response.getString("description"+i));
                        helpAd.setAddress(response.getString("address"+i));
                        helpAd.setShelterName(response.getString("shelterName"+i));
                        helpAd.setContactName(response.getString("contactName"+i));
                        helpAd.setContactEmail(response.getString("contactEmail"+i));
                        helpAds.add(helpAd);
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

    public static ArrayList<Animal> sortUpAge(ArrayList<Animal> animals){
        sort(animals, Animal.ageComparator);
        return animals;
    }

    public static ArrayList<Animal> sortDownAge(ArrayList<Animal> animals){
        sort(animals, Animal.ageComparator);
        reverse(animals);
        return animals;
    }

    public static ArrayList<HelpAd> sortAlphabet(ArrayList<HelpAd> helpAds){
        sort(helpAds,HelpAd.alphabetComparator);
        return helpAds;
    }

    public static ArrayList<HelpAd> sortFirstUrgency(ArrayList<HelpAd> helpAds){
        sort(helpAds,HelpAd.alphabetComparator);
        reverse(helpAds);
        return helpAds;
    }

    public static ArrayList<HelpAd> filterHelpAds(ArrayList<HelpAd> helpAds,String... types){
        ArrayList<HelpAd> newHelpAds = new ArrayList<>();
        for(HelpAd helpAd:helpAds){
            for(String type:types){
                if(helpAd.getType().equals(type)){
                    newHelpAds.add(helpAd);
                    break;
                }
            }
        }
        return newHelpAds;
    }

    public ArrayList<Animal> getAnimalsByEmail(String email){
        ArrayList<Animal> array = new ArrayList<>();
        for(Animal animal: this.animals){
            if(email.equals(animal.getContactEmail())){
                array.add(animal);
            }
        }
        return array;
    }

    public ArrayList<HelpAd> getHelpAdsByEmail(String email){
        ArrayList<HelpAd> array = new ArrayList<>();
        for(HelpAd helpAd: this.helpAds){
            if(email.equals(helpAd.getContactEmail())){
                array.add(helpAd);
            }
        }
        return array;
    }

    public ArrayList<Animal> getAnimalsForMe(String email){
        return animalsForMe;
    }

    public void setAnimalsForMe(String email){
        System.out.println("setAnimalsForMe " + email);
        this.animalsForMe = new ArrayList<>();
        for(Animal animal:this.animals){
            System.out.println(animal.getContactEmail() + " " + animal.getName());
            if(animal.getContactEmail().equals(email)){
                this.animalsForMe.add(animal);
            }
        }
    }

    public ArrayList<HelpAd> getHelpAdsForMe(String email){
        if(helpAdsForMe==null) setHelpAdsForMe(email);
        return helpAdsForMe;
    }

    public void setHelpAdsForMe(String email){
        helpAdsForMe = new ArrayList<>();
        System.out.println("sethelpadsforme controller" + email);
        for(HelpAd helpAd: helpAds){
            if(helpAd.getContactEmail().equals(email)){
                helpAdsForMe.add(helpAd);

            }
            System.out.println(helpAd.getContactEmail());
        }
    }

    public void addAnimal(Animal animal){
        this.animals.add(animal);
        this.animalsForMe.add(animal);
    }

    public void clearAnimals(){
        this.animals = new ArrayList<>();
    }

    public void clearAnimalsForMe(){
        this.animalsForMe = new ArrayList<>();
    }

    public void clearHelpAds(){
        this.helpAds = new ArrayList<>();
    }

    public void clearHelpAdsForMe(){
        this.helpAdsForMe = new ArrayList<>();
    }


}
