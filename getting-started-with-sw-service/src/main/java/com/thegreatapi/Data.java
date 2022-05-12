package com.thegreatapi;

public class Data {

    private final String name;
    private final String language;

    public Data(String name, String language) {
        this.name = name;
        this.language = language;
    }

    public String getName() {
        return name;
    }

    public String getLanguage() {
        return language;
    }
}
