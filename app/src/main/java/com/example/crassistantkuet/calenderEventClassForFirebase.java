package com.example.crassistantkuet;

public class calenderEventClassForFirebase {
    public String courseNumber , courseTime, courseDetails, courseType;

    public calenderEventClassForFirebase(){

    }

    public calenderEventClassForFirebase(String courseNumber, String courseTime, String courseDetails, String courseType) {

        this.courseNumber = courseNumber;
        this.courseTime = courseTime;
        this.courseDetails = courseDetails;
        this.courseType = courseType;
    }

    public String getCourseNumber() {
        return courseNumber;
    }

    public void setCourseNumber(String courseNumber) {
        this.courseNumber = courseNumber;
    }

    public String getCourseTime() {
        return courseTime;
    }

    public void setCourseTime(String courseTime) {
        this.courseTime = courseTime;
    }

    public String getCourseDetails() {
        return courseDetails;
    }

    public void setCourseDetails(String courseDetails) {
        this.courseDetails = courseDetails;
    }

    public String getCourseType() {
        return courseType;
    }

    public void setCourseType(String courseType) {
        this.courseType = courseType;
    }
}
