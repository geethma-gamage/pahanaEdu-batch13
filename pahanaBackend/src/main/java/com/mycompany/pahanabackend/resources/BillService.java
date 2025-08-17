package com.mycompany.pahanabackend.resources;

import Utils.Bill;
import Utils.BillDAO;
import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.sql.SQLException;
import java.util.List;

@Path("bills")
public class BillService {
    private final BillDAO billDAO = new BillDAO();
    private final Jsonb jsonb = JsonbBuilder.create();

    // ✅ POST: Save bill
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response saveBill(Bill bill) {
        try {
            System.out.println("📥 Received Bill: " + jsonb.toJson(bill)); // ✅ Debugging
            int billId = billDAO.saveBill(bill);
            return Response.status(Response.Status.CREATED)
                           .entity(jsonb.toJson(billId))
                           .build();
        } catch (SQLException e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(jsonb.toJson(new ErrorResponse("Error saving bill: " + e.getMessage())))
                    .build();
        }
    }

    // ✅ GET all bills
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllBills() {
        try {
            List<Bill> bills = billDAO.getAllBills();
            return Response.ok(jsonb.toJson(bills)).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(jsonb.toJson(new ErrorResponse("Error retrieving bills: " + e.getMessage())))
                    .build();
        }
    }

    // ✅ Error response helper
    public static class ErrorResponse {
        private String error;
        public ErrorResponse(String error) { this.error = error; }
        public String getError() { return error; }
    }
}
