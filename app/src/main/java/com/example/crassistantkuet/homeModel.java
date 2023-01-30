package com.example.crassistantkuet;

import java.util.ArrayList;
import java.util.HashMap;

public class homeModel {
    String HomeSenderName , HomeSenderImage , HomeTime , MessageText , MessageImage , Like , Dislike,Comment ,LayoutType;
    String checkUserID;
    HashMap<String,String> checkUserHashmap;

    public homeModel(String homeSenderName, String homeSenderImage, String homeTime, String messageText, String messageImage, String like, String dislike, String comment, String layoutType, String checkUserID, HashMap<String, String> checkUserHashmap) {
        HomeSenderName = homeSenderName;
        HomeSenderImage = homeSenderImage;
        HomeTime = homeTime;
        MessageText = messageText;
        MessageImage = messageImage;
        Like = like;
        Dislike = dislike;
        Comment = comment;
        LayoutType = layoutType;
        this.checkUserID = checkUserID;
        this.checkUserHashmap = checkUserHashmap;
    }

    public homeModel()
    {

    }


    public String getHomeSenderName() {
        return HomeSenderName;
    }

    public void setHomeSenderName(String homeSenderName) {
        HomeSenderName = homeSenderName;
    }

    public String getHomeSenderImage() {
        return HomeSenderImage;
    }

    public void setHomeSenderImage(String homeSenderImage) {
        HomeSenderImage = homeSenderImage;
    }

    public String getHomeTime() {
        return HomeTime;
    }

    public void setHomeTime(String homeTime) {
        HomeTime = homeTime;
    }

    public String getMessageText() {
        return MessageText;
    }

    public void setMessageText(String messageText) {
        MessageText = messageText;
    }

    public String getMessageImage() {
        return MessageImage;
    }

    public void setMessageImage(String messageImage) {
        MessageImage = messageImage;
    }

    public String getLike() {
        return Like;
    }

    public void setLike(String like) {
        Like = like;
    }

    public String getDislike() {
        return Dislike;
    }

    public void setDislike(String dislike) {
        Dislike = dislike;
    }

    public String getComment() {
        return Comment;
    }

    public void setComment(String comment) {
        Comment = comment;
    }

    public String getLayoutType() {
        return LayoutType;
    }

    public void setLayoutType(String layoutType) {
        LayoutType = layoutType;
    }

    public String getCheckUserID() {
        return checkUserID;
    }

    public void setCheckUserID(String checkUserID) {
        this.checkUserID = checkUserID;
    }

    public HashMap<String, String> getCheckUserHashmap() {
        return checkUserHashmap;
    }

    public void setCheckUserHashmap(HashMap<String, String> checkUserHashmap) {
        this.checkUserHashmap = checkUserHashmap;
    }
}
