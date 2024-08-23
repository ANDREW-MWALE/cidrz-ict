package com.andyprofinnovations.dao;

import com.andyprofinnovations.model.Status;

import javax.xml.transform.Result;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StatusDAO {

  Connection con = DBConnection.getConn();

    public List<Status> listStatus() throws SQLException {

        List<Status> list = new ArrayList<>();

        String sql = "select * from Status";
        Statement stmt = con.createStatement();
        ResultSet rst = stmt.executeQuery(sql);

        while (rst.next()){
            Status status = new Status();
            status.setId(rst.getInt("id"));
            status.setName(rst.getString("name"));
            list.add(status);

        }
        return list;
    }
}
