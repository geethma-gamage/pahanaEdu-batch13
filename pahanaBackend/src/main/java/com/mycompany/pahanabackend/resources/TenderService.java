package com.mycompany.pahanabackend.resources;

import Utils.Tender;
import Utils.TenderDAO;
import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Path("tenders")
public class TenderService {
    private final TenderDAO tenderDAO = new TenderDAO();
    private final Jsonb jsonb = JsonbBuilder.create();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllTenders() {
        try {
            List<Tender> tenders = tenderDAO.getAllTenders();
            return Response.ok(jsonb.toJson(tenders)).build();
        } catch (SQLException e) {
            return internalServerError("Database error: " + e.getMessage());
        }
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTenderById(@PathParam("id") int id) {
        try {
            Tender tender = tenderDAO.getTenderById(id);
            if (tender != null) {
                return Response.ok(jsonb.toJson(tender)).build();
            } else {
                return notFound("Tender with ID " + id + " not found");
            }
        } catch (SQLException e) {
            return internalServerError("Database error: " + e.getMessage());
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addTender(Tender tender, @Context UriInfo uriInfo) {
        try {
            if (tender.getTitle() == null || tender.getTitle().isEmpty()) {
                return badRequest("Required field 'title' cannot be null or empty");
            }
            int id = tenderDAO.addTender(tender);
            if (id != -1) {
                UriBuilder builder = uriInfo.getAbsolutePathBuilder().path(String.valueOf(id));
                Map<String, Object> response = new HashMap<>();
                response.put("id", id);
                response.put("message", "Tender created successfully");
                return Response.created(builder.build()).entity(jsonb.toJson(response)).build();
            } else {
                return internalServerError("Failed to add tender");
            }
        } catch (SQLException e) {
            return internalServerError("Database error: " + e.getMessage());
        }
    }

    @PUT
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateTender(@PathParam("id") int id, Tender updatedTender) {
        try {
            updatedTender.setTenderId(id);
            if (updatedTender.getTitle() == null || updatedTender.getTitle().isEmpty()) {
                return badRequest("Required field 'title' cannot be null or empty");
            }
            if (tenderDAO.updateTender(updatedTender)) {
                return Response.ok(jsonb.toJson(new SuccessResponse("Tender updated successfully"))).build();
            } else {
                return notFound("Tender with ID " + id + " not found");
            }
        } catch (SQLException e) {
            return internalServerError("Database error: " + e.getMessage());
        }
    }

    @DELETE
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteTender(@PathParam("id") int id) {
        try {
            if (tenderDAO.deleteTender(id)) {
                return Response.ok(jsonb.toJson(new SuccessResponse("Tender deleted successfully"))).build();
            } else {
                return notFound("Tender with ID " + id + " not found");
            }
        } catch (SQLException e) {
            return internalServerError("Database error: " + e.getMessage());
        }
    }

    // --- Utility methods for consistent responses ---

    private Response badRequest(String message) {
        return Response.status(Response.Status.BAD_REQUEST)
                .entity(jsonb.toJson(new ErrorResponse(message))).build();
    }

    private Response notFound(String message) {
        return Response.status(Response.Status.NOT_FOUND)
                .entity(jsonb.toJson(new ErrorResponse(message))).build();
    }

    private Response internalServerError(String message) {
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity(jsonb.toJson(new ErrorResponse(message))).build();
    }

    // --- Inner classes for JSON responses ---

    public static class ErrorResponse {
        private String error;
        public ErrorResponse(String error) { this.error = error; }
        public String getError() { return error; }
        public void setError(String error) { this.error = error; }
    }

    public static class SuccessResponse {
        private String message;
        public SuccessResponse(String message) { this.message = message; }
        public String getMessage() { return message; }
        public void setMessage(String message) { this.message = message; }
    }
}
