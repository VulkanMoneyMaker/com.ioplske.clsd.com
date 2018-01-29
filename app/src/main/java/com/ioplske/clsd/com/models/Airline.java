package com.ioplske.clsd.com.models;

import java.util.ArrayList;

public class Airline{
    private String name;
    private ArrayList<String> partners = new ArrayList<>();

    public Airline() {}

    //pre: data != null, data.length > 0
    public Airline(String[] data){
        assert data != null && data.length > 0 : "Failed precondition";
        name = data[0];
        partners = new ArrayList<String>();
        for(int i = 1; i < data.length; i++)
            partners.add( data[i] );
    }

    public String[] getPartners(){
        return partners.toArray(new String[partners.size()]);
    }

    public boolean isPartner(String name){
        return partners.contains(name);
    }

    public String getName(){
        return name;
    }

    public String toString(){
        return name + ", partners: " + partners;
    }
}