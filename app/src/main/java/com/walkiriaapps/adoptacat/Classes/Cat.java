package com.walkiriaapps.adoptacat.Classes;

import org.json.JSONException;
import org.json.JSONObject;

public class Cat {

    private int id;
    private String name, pictureUrl;
    private boolean adoption;


    public Cat(int id, String name, String pictureUrl, boolean adoption) {
        this.id = id;
        this.name = name;
        this.pictureUrl = pictureUrl;
        this.adoption = adoption;
    }

    public Cat(JSONObject jsonCat)
    {
        try {
            this.id = Integer.parseInt(jsonCat.getString("id"));
            this.name = jsonCat.getString("name");
            this.pictureUrl = jsonCat.getString("picture_url");
            this.adoption = Integer.parseInt(jsonCat.getString("adoption")) != 0;
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPictureUrl() {
        return AppData.ROOT_URL+"/"+pictureUrl;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }

    public boolean isAdoption() {
        return adoption;
    }

    public void setAdoption(boolean adoption) {
        this.adoption = adoption;
    }

    @Override
    public String toString() {
        return "Cat{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", pictureUrl='" + pictureUrl + '\'' +
                ", adoption=" + adoption +
                '}';
    }
}
