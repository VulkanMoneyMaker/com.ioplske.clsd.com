package com.ioplske.clsd.com.models;


public class Car {

    public static class Builder {

        private String name;

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Car build() {
            return new Car(this);
        }
    }

    private final String name;

    private Car(Builder builder) {
        name = builder.name;
    }

    public String getName() {
        return name;
    }
}