package com.example.crassistantkuet;

public class calenderEventAllDataModelClass {
    public String courseDetails,courseNumber,courseTime,courseType,courseYear,courseMonth,courseDate;
    public calenderEventAllDataModelClass ()
    {
        
    }

    public calenderEventAllDataModelClass(String courseDetails, String courseNumber, String courseTime, String courseType, String courseYear, String courseMonth, String courseDate) {
        this.courseDetails = courseDetails;
        this.courseNumber = courseNumber;
        this.courseTime = courseTime;
        this.courseType = courseType;
        this.courseYear = courseYear;
        this.courseMonth = courseMonth;
        this.courseDate = courseDate;
    }

    public String getCourseDetails() {
        return courseDetails;
    }

    public void setCourseDetails(String courseDetails) {
        this.courseDetails = courseDetails;
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

    public String getCourseType() {
        return courseType;
    }

    public void setCourseType(String courseType) {
        this.courseType = courseType;
    }

    public String getCourseYear() {
        return courseYear;
    }

    public void setCourseYear(String courseYear) {
        this.courseYear = courseYear;
    }

    public String getCourseMonth() {
        return courseMonth;
    }

    public void setCourseMonth(String courseMonth) {
        this.courseMonth = courseMonth;
    }

    public String getCourseDate() {
        return courseDate;
    }

    public void setCourseDate(String courseDate) {
        this.courseDate = courseDate;
    }
}
