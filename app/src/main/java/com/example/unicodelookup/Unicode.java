package com.example.unicodelookup;

import java.io.Serializable;

public class Unicode implements Serializable {
    private String character;
    private String name;
    public boolean isFavorited;

    Unicode(String c, String n){
        this.character = c;
        this.name = n;
        this.isFavorited = false;
    }

    public String getCharacter() {
        return character;
    }

    public String getName() {
        return name;
    }
}
