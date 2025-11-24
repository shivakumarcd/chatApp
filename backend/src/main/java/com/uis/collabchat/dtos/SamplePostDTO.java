package com.uis.collabchat.dtos;


/*
TODO: Use lombok to remove below boilerplate code
 */
public class SamplePostDTO {
    private String message;

    public SamplePostDTO() { }

    public SamplePostDTO(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}