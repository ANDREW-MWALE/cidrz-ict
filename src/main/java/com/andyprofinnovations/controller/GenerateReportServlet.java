package com.andyprofinnovations.controller;

import com.andyprofinnovations.dao.IncidentDAO;
import com.andyprofinnovations.model.Incident;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class GenerateReportServlet extends HttpServlet {
    private IncidentDAO incidentDAO;

    public void init() {
        incidentDAO = new IncidentDAO();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String period = request.getParameter("period");
       if(period == null){
           period = "";
        }
        switch (period) {
            case "weekly":
                period = "SELECT * FROM incident WHERE created_date >= DATED(day, -7, GET DATE())";
                break;
            case "monthly":
                period = "SELECT * FROM incident WHERE created_date >= DATED(month, -1, GET DATE())";
                break;
            case "annual":
                period = "SELECT * FROM incident WHERE created_date >= DATED(year, -1, GET DATE())";
                break;
            default:
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid report period.");
                return;
        }

        // Set response content type
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=" + period + "_report.pdf");

        try {
            List<Incident> listIncidents = incidentDAO.listIncident();

            // Generate PDF
            Document document = new Document();
            PdfWriter.getInstance(document, response.getOutputStream());
            document.open();

            document.add(new Paragraph(period.substring(0, 1).toUpperCase() + period.substring(1) + " Report"));
            document.add(new Paragraph("Generated on: " + new java.util.Date()));
            document.add(new Paragraph(" "));

            PdfPTable table = new PdfPTable(9);
            table.addCell("Incident ID");
            table.addCell("Name");
            table.addCell("Description");
            table.addCell("Causes");
            table.addCell("Location ID");
            table.addCell("Created By");
            table.addCell("Created Date");
            table.addCell("Updated By");
            table.addCell("Last Updated Date");

            for (Incident incident : listIncidents) {
                table.addCell(Long.toString(incident.getIncident_id()));
                table.addCell(incident.getName());
                table.addCell(incident.getDescription());
                table.addCell(incident.getCauses());
                table.addCell(Integer.toString(incident.getLocation_id()));
                table.addCell(incident.getCreated_by());
                table.addCell(String.valueOf(incident.getCreated_date()));
                table.addCell(incident.getUpdated_by());
                table.addCell(incident.getLast_updated_date().toString());
            }

            document.add(table);
            document.close();

        } catch (DocumentException e) {
            throw new ServletException(e);
        }
    }
}
