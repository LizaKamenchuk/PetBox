package com.example.petbox;

import static java.sql.Types.NULL;

import java.util.ArrayList;

public class ControllerFilter {
    private static ControllerFilter controllerFilter;
    private String ageMeasure="";
    int fromAge, toAge;
    ArrayList<String> colors;
    ArrayList<String> cities;
    ArrayList<String> types;

    public static void deleteAnimal(Animal animal) {
    }

    public ArrayList<String> getTypes() {
        return types;
    }

    public void setTypes(ArrayList<String> types) {
        this.types = types;
    }

    public String getAgeMeasure() {
        return ageMeasure;
    }

    public void setAgeMeasure(String ageMeasure) {
        this.ageMeasure = ageMeasure;
    }

    public int getFromAge() {
        return fromAge;
    }

    public void setFromAge(int fromAge) {
        this.fromAge = fromAge;
    }

    public int getToAge() {
        return toAge;
    }

    public void setToAge(int toAge) {
        this.toAge = toAge;
    }

    public ArrayList<String> getColors() {
        return colors;
    }

    public void setColors(ArrayList<String> colors) {
        this.colors = colors;
    }

    public ArrayList<String> getCities() {
        return cities;
    }

    public void setCities(ArrayList<String> cities) {
        this.cities = cities;
    }

    private ControllerFilter(){
        colors = new ArrayList<>();
        cities = new ArrayList<>();
        types = new ArrayList<>();
    }

    public static synchronized ControllerFilter getController(){
        if(controllerFilter == null)
            controllerFilter = new ControllerFilter();
        return controllerFilter;
    }

    public void clearFilter(){
        ageMeasure = "";
        cities = new ArrayList<>();
        colors = new ArrayList<>();
        types = new ArrayList<>();
        fromAge = NULL;
        toAge = NULL;
    }

    public static ArrayList<Animal> filterByCities(ArrayList<Animal> animals,ArrayList<String> cities){
        ArrayList<Animal> newAnimals = new ArrayList<>();
        for(Animal animal:animals){
            for(String city: cities){
                if(animal.getAddress().equals(city)){
                    newAnimals.add(animal);
                    break;
                }
            }
        }
        return newAnimals;
    }

    public static ArrayList<Animal> filterByColors(ArrayList<Animal> animals,ArrayList<String> colors){
        ArrayList<Animal> newAnimals = new ArrayList<>();
        for(Animal animal:animals){
            for(String color: colors){
                if(animal.getColor().equals(color)){
                    newAnimals.add(animal);
                    break;
                }
            }
        }
        return newAnimals;
    }

    public static ArrayList<Animal> filterByAge(ArrayList<Animal> animals,int fromAge,int toAge,String ageMeasure){
        ArrayList<Animal> newAnimals = new ArrayList<>();
        int animalAge;
        for(Animal animal:animals){
           if(ageMeasure.equals("м") && animal.getAgeMeasure().equals("лет")) {animalAge = animal.getAge()*12;}
           else if(ageMeasure.equals("г") && animal.getAgeMeasure().equals("месяцев")) {break;}
           else animalAge = animal.getAge();

           if(animalAge >= fromAge && animalAge <= toAge){
               newAnimals.add(animal);
           }
        }
        return newAnimals;
    }

    public static ArrayList<Animal> filterByType(ArrayList<Animal> animals,ArrayList<String> types){
        ArrayList<Animal> newAnimals = new ArrayList<>();
        for(Animal animal:animals){
            for(String type: types){
                if(animal.getType().equals(type)){
                    newAnimals.add(animal);
                    break;
                }
            }
        }
        return newAnimals;
    }
}
