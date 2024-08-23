package com.andyprofinnovations.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class Maintenance {
    private int identity;
    private String gudgetName;
    private int serialnumber;
    private String problemDescription;
    private String status;
    private String storageArea;
    private String broughtBy;
    private String receivedBy;
    private String updateby;
    private String dateBrought;
    private  String lastUpDated;

    public Maintenance() {
    }

    public Maintenance(int identity, String gudgetName, int serialnumber, String problemDescription, String status, String storageArea, String broughtBy, String receivedBy, String updateby, String dateBrought, String lastUpDated) {
        this.identity = identity;
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
