package com.example.Crud_opearations.DTO;

public class DeleteResponse {
    private String message;

    public DeleteResponse(String message){
        this.message = message;
    }
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
