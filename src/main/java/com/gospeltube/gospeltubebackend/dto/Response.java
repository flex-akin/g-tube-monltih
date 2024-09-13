package com.gospeltube.gospeltubebackend.dto;
import java.util.Set;

public class Response {
   private String responseCode;
   private String responseDescription;
   private boolean status;
   private Set<?> data;

    public Response(String responseCode, String responseDescription, boolean status, Set<?> data) {
        this.responseCode = responseCode;
        this.responseDescription = responseDescription;
        this.status = status;
        this.data = data;
    }

    public String getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(String responseCode) {
        this.responseCode = responseCode;
    }

    public String getResponseDescription() {
        return responseDescription;
    }

    public void setResponseDescription(String responseDescription) {
        this.responseDescription = responseDescription;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public Set<?> getData() {
        return data;
    }

    public void setData(Set<?> data) {
        this.data = data;
    }
}
