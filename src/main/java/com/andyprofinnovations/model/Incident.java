package com.andyprofinnovations.model;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
public class Incident {
    private int incident_id;
    private String name;
    private String description;
    private String causes;
    private int location_id;
    private String created_by;
    private Timestamp created_date;
    private String updated_by;
    private Timestamp last_updated_date;

    public Incident() {
    }

    public Incident(int incident_id, String name, String description, String causes, int location_id, String created_by, Timestamp created_date, String updated_by, Timestamp last_updated_date) {
        this.incident_id = incident_id;
        this.name = name;
        this.description = description;
        this.causes = causes;
        this.location_id = location_id;
        this.created_by = created_by;
        this.created_date = created_date;
        this.updated_by = updated_by;
        this.last_updated_date = last_updated_date;
    }
}


//package com.andyprofinnovations.model;
//
//import lombok.Getter;
//import lombok.Setter;
////import org.apache.commons.lang3.time.DateParser;
//
//@Getter
//@Setter
//public class Incident {
//    private int incident_id;
//    private String name;
//    private String description;
//    private String causes;
//    private int location_id;
//    private String created_by;
//    private String created_date;
//    private String updated_by;
//    private String last_updated_date;
//
//    public Incident() {
//    }
//
//    public Incident(int incident_id,
//                    String name,
//                    String description,
//                    String causes,
//                    int location_id,
//                    String created_by,
//                    String created_date,
//                    String updated_by,
//                    String last_updated_date) {
//        this.incident_id = incident_id;
//        this.name = name;
//        this.description = description;
//        this.causes = causes;
//        this.location_id = location_id;
//        this.created_by = created_by;
//        this.created_date = created_date;
//        this.updated_by = updated_by;
//        this.last_updated_date = last_updated_date;
//    }
//}
