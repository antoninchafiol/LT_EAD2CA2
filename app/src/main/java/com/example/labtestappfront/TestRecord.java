package com.example.labtestappfront;

import com.google.gson.annotations.SerializedName;
import java.util.Date;

public class TestRecord {
    @SerializedName("id")
    private int id;

    @SerializedName("patientName")
    private String patientName;

    @SerializedName("testType")
    private String testType;

    @SerializedName("testDate")
    private Date testDate;

    @SerializedName("result")
    private String result;

    public TestRecord(String patientName, String testType, Date testDate, String result) {
        this.patientName = patientName;
        this.testType = testType;
        this.testDate = testDate;
        this.result = result;
    }

    // Getters and setters
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getPatientName() {
        return patientName;
    }
    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public String getTestType() {
        return testType;
    }
    public void setTestType(String testType) {
        this.testType = testType;
    }

    public Date getTestDate() {
        return testDate;
    }
    public void setTestDate(Date testDate) {
        this.testDate = testDate;
    }

    public String getResult() {
        return result;
    }
    public void setResult(String result) {
        this.result = result;
    }


}
