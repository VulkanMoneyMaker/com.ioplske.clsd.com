package com.ioplske.clsd.com.models;

public class Spin {

    private int id;
    private String title;
    private String uiid;
    private long date;

    private Spin() {}

    public class Builder {

        private Builder(int id) {
            Spin.this.id = id;
        }

        public Builder setTitle(String title) {
            Spin.this.title = title;
            return this;
        }

        public Builder setUiid(String uiid) {
            Spin.this.uiid = uiid;
            return this;
        }

        public Builder setDate(long date) {
            Spin.this.date = date;
            return this;
        }

        public Spin build() {
            return Spin.this;
        }
    }

    public static Builder getBuilder(int id) {
        return new Spin().new Builder(id);
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getUiid() {
        return uiid;
    }

    public long getDate() {
        return date;
    }
}
