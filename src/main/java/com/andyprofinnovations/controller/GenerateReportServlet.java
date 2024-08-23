package com.andyprofinnovations.controller;

import com.andyprofinnovations.dao.IncidentDAO;
import com.andyprofinnovations.model.Incident;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GenerateReportServlet extends HttpServlet {
    private static final Logger LOGGER = Logger.getLogger(GenerateReportServlet.class.getName());
    private IncidentDAO incidentDAO;

    @Override
    public void init() throws ServletException {
        super.init();
        incidentDAO = new IncidentDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String period = request.getParameter("period");
        if (period == null || period.isEmpty()) {
            LOGGER.severe("Period parameter is missing");
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing period parameter");
            return;
        }

        List<Incident> incidents;
        try {
            incidents = getIncidentsForPeriod(period);
            if (incidents != null) {
                generateExcelReport(response, incidents, period);
            } else {
                LOGGER.severe("Invalid period: " + period);
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid period: " + period);
            }
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error generating report for period: " + period, e);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error generating report");
        }
    }

    private List<Incident> getIncidentsForPeriod(String period) {
        switch (period) {
            case "daily":
                return incidentDAO.getDailyIncidents();
            case "weekly":
                return incidentDAO.getWeeklyIncidents();
            case "monthly":
                return incidentDAO.getMonthlyIncidents();
            case "quarterly":
                return incidentDAO.getQuarterlyIncidents();
            case "yearly":
                return incidentDAO.getYearlyIncidents();
            default:
                return null;
        }
    }

    private void generateExcelReport(HttpServletResponse response, List<Incident> incidents, String period) throws IOException {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Incident Report - " + period);

        Row headerRow = sheet.createRow(0);
        String[] headers = {"Incident ID", "Name", "Description", "Causes", "Location ID", "Created By", "Created Date", "Updated By", "Last Updated Date"};

        for (int i = 0; i < headers.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(headers[i]);
        }

        int rowNum = 1;
        for (Incident incident : incidents) {
            Row row = sheet.createRow(rowNum++);

            row.createCell(0).setCellValue(incident.getIncident_id());
            row.createCell(1).setCellValue(incident.getName());
            row.createCell(2).setCellValue(incident.getDescription());
            row.createCell(3).setCellValue(incident.getCauses());
            row.createCell(4).setCellValue(incident.getLocation_id());
            row.createCell(5).setCellValue(incident.getCreated_by());
            row.createCell(6).setCellValue(incident.getCreated_date().toString());
            row.createCell(7).setCellValue(incident.getUpdated_by());
            row.createCell(8).setCellValue(incident.getLast_updated_date().toString());
        }

        response.setContentType("application/vnd.ms-excel");
        response.setHeader("Content-Disposition", "attachment; filename=Incident_Report_" + period + ".xlsx");

        workbook.write(response.getOutputStream());
        workbook.close();
    }
}
