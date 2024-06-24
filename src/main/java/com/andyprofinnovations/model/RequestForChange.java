package com.andyprofinnovations.model;

import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

@Getter
@Setter
public class RequestForChange {
    private int id;
    private String requestNo;
    private Date logDate;
    private Date changeDate;
    private String descriptionOfChange;
    private String motivation;
    private String adInfo;
    private String Department;


    public RequestForChange() {
    }

    public RequestForChange(int id,
                            String requestNo,
                            Date logDate,
                            Date changeDate,
                            String descriptionOfChange,
                            String motivation,
                            String adInfo,
                            String department) {
        this.id = id;
        this.requestNo = requestNo;
        this.logDate = logDate;
        this.changeDate = changeDate;
        this.descriptionOfChange = descriptionOfChange;
        this.motivation = motivation;
        this.adInfo = adInfo;
        Department = department;
    }
}
