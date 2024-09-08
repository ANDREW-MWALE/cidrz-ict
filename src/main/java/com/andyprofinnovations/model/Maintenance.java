package com.andyprofinnovations.model;

import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

@Getter
@Setter

public class Maintenance {
    private int id;
    private String gudgetName;
    private int serialnumber;
    private String problemDescription;
    private String status;
    private String storageArea;
    private String broughtBy;
    private String receivedBy;
    private String updateby;
    private Date dateBrought;
    private  Date lastUpDated;

    public Maintenance() {
    }

    public Maintenance(int id, String gudgetName, int serialnumber, String problemDescription, String status, String storageArea, String broughtBy, String receivedBy, String updateby, Date dateBrought, Date lastUpDated) {
        this.id = id;
        this.gudgetName = gudgetName;
        this.serialnumber = serialnumber;
        this.problemDescription = problemDescription;
        this.status = status;
        this.storageArea = storageArea;
        this.broughtBy = broughtBy;
        this.receivedBy = receivedBy;
        this.updateby = updateby;
        this.dateBrought = dateBrought;
        this.lastUpDated = lastUpDated;
    }


}
