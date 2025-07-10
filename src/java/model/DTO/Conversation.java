/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.DTO;

/**
 *
 * @author LEGION
 */
public class Conversation {

    private String request;
    private String response;

    public Conversation() {
    }

    public Conversation(String request, String response) {
        this.request = request;
        this.response = response;
    }

    public String getRequest() {
        return request;
    }

    public void setRequest(String request) {
        this.request = request;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    @Override
    public String toString() {
        return "Conversation{" + "request=" + request + ", response=" + response + '}';
    }

}
