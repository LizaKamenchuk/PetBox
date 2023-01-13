package com.example.petbox;

import java.io.Serializable;
import java.util.Comparator;

public class Animal implements Serializable {
    private int id;
    private String color;

    private String type;
    private String name;
    private String characters;
    private String gender;
    private int age;
    private String ageMeasure;
    private int photo;

    private String address;
    private String contactName;
    private String contactEmail;
    private String shelterName;

     public Animal( String type, String name, String characters, String color, String gender, int age, String ageMeasure, int photo, String contactEmail){
         this.type=type;
         this.name=name;
         this.characters=characters;
         this.color= color;
         this.gender=gender;
         this.age=age;
         this.ageMeasure = ageMeasure;
         this.photo = photo;
         this.contactEmail=contactEmail;
     }

     public Animal(){

     }

    public String getColor() {
        return color;
    }
    public void setColor(String color) {
        this.color = color;
    }

    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }

     public String getName(){
         return this.name;
     }
     public void setName(String name){
         this.name=name;
     }

     public String getCharacters(){
         return this.characters;
     }
     public  void  setCharacters(String characters){
         this.characters=characters;
     }

     public String getGender(){return this.gender;}
    public void setGender(String gender){this.gender=gender;}

     public int getPhoto(){
         return this.photo;
     }
     public  void setPhoto(int photo){
         this.photo = photo;
     }

    public int getAge() {
        return this.age;
    }
    public void setAge(int age){
         this.age=age;
    }

    public String getAgeMeasure() {
        return ageMeasure;
    }
    public void setAgeMeasure(String ageMeasure) {
        this.ageMeasure = ageMeasure;
    }

    public int getId() {return this.id;}
    public void setId(int id) {this.id = id;}

    public String getAddress(){return this.address;}
    public void setAddress(String address) {this.address = address;}

    public String getContactName() {
        return contactName;
    }
    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getContactEmail() {
        return contactEmail;
    }
    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }

    public String getShelterName() {
        return shelterName;
    }
    public void setShelterName(String shelterName) {
        this.shelterName = shelterName;
    }

    public static Comparator<Animal> ageComparator = new Comparator<Animal>() {
        @Override
        public int compare(Animal o1, Animal o2) {
            int o1Age = o1.getAge(),o2Age= o2.getAge();
            if(o1.getAgeMeasure() == "лет") o1Age = o1.getAge()*12;
            if(o2.getAgeMeasure() == "лет") o2Age = o2.getAge()*12;
            return (int) (o1Age-o2Age);
        }
    };

}
