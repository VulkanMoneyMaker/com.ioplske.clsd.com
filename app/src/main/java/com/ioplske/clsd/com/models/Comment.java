package com.ioplske.clsd.com.models;


public class Comment {
    private int authorId;
    private String comment;

    public Comment(int authorId, String comment) {
        this.authorId = authorId;
        this.comment = comment;
    }

    public int getAuthorId() {
        return authorId;
    }

    public void setAuthorId(int authorId) {
        this.authorId = authorId;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
