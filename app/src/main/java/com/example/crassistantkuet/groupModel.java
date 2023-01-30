package com.example.crassistantkuet;

public class groupModel {

    groupModel()
    {

    }
    String UID , Name , Image , Message , Time ;

    public groupModel(String UID, String name, String image, String message, String time) {
        this.UID = UID;
        Name = name;
        Image = image;
        Message = message;
        Time = time;
    }

    public String getUID() {
        return UID;
    }

    public void setUID(String UID) {
        this.UID = UID;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }
}
