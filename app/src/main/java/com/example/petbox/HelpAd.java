package com.example.petbox;

import java.util.Comparator;

public class HelpAd {
    private int id;
    private String type;
    private String description;
    private String address;

    private String contactName;
    private String contactEmail;
    private String shelterName;

    public HelpAd(String type, String description,String address,String contactName,String contactEmail,String shelterName) {
        this.type=type;
        this.description=description;
        this.address=address;
        this.contactName = contactName;
        this.contactEmail = contactEmail;
        this.shelterName = shelterName;

    }


    public HelpAd(){}

    public HelpAd(String type, String description, String contactEmail) {
        this.type=type;
        this.description=description;
        this.contactEmail=contactEmail;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
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

    public static Comparator<HelpAd> alphabetComparator = new Comparator<HelpAd>() {
        @Override
        public int compare(HelpAd o1, HelpAd o2) {
            return o1.getType().compareTo(o2.getType());
        }
    };

}
