package com.mycompany.pahanabackend.resources;

import Utils.Item;
import Utils.ItemDAO;
import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Path("items")
public class ItemService {
    private final ItemDAO itemDAO = new ItemDAO();
    private final Jsonb jsonb = JsonbBuilder.create();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllItems() {
        try {
            List<Item> items = itemDAO.getAllItems();
            return Response.ok(jsonb.toJson(items)).build();
        } catch (SQLException e) {
            return internalServerError("Database error: " + e.getMessage());
        }
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getItemById(@PathParam("id") int id) {
        try {
            Item item = itemDAO.getItemById(id);
            if (item != null) {
                return Response.ok(jsonb.toJson(item)).build();
            } else {
                return notFound("Item with ID " + id + " not found");
            }
        } catch (SQLException e) {
            return internalServerError("Database error: " + e.getMessage());
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addItem(Item item, @Context UriInfo uriInfo) {
        try {
            if (item.getName() == null || item.getName().isEmpty()) {
                return badRequest("Required field 'name' cannot be null or empty");
            }
            int id = itemDAO.addItem(item);
            if (id != -1) {
                UriBuilder builder = uriInfo.getAbsolutePathBuilder().path(String.valueOf(id));
                Map<String, Object> response = new HashMap<>();
                response.put("id", id);
                response.put("message", "Item created successfully");
                return Response.created(builder.build()).entity(jsonb.toJson(response)).build();
            } else {
                return internalServerError("Failed to add item");
            }
        } catch (SQLException e) {
            if (e.getMessage().contains("Duplicate entry")) {
                return Response.status(Response.Status.CONFLICT)
                        .entity(jsonb.toJson(new ErrorResponse("Duplicate entry: " + e.getMessage())))
                        .build();
            }
            return internalServerError("Database error: " + e.getMessage());
        }
    }

    @PUT
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateItem(@PathParam("id") int id, Item updatedItem) {
        try {
            updatedItem.setId(id);
            if (updatedItem.getName() == null || updatedItem.getName().isEmpty()) {
                return badRequest("Required field 'name' cannot be null or empty");
            }
            if (itemDAO.updateItem(updatedItem)) {
                return Response.ok(jsonb.toJson(new SuccessResponse("Item updated successfully"))).build();
            } else {
                return notFound("Item with ID " + id + " not found");
            }
        } catch (SQLException e) {
            if (e.getMessage().contains("Duplicate entry")) {
                return Response.status(Response.Status.CONFLICT)
                        .entity(jsonb.toJson(new ErrorResponse("Duplicate entry: " + e.getMessage())))
                        .build();
            }
            return internalServerError("Database error: " + e.getMessage());
        }
    }

    @DELETE
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteItem(@PathParam("id") int id) {
        try {
            if (itemDAO.deleteItem(id)) {
                return Response.ok(jsonb.toJson(new SuccessResponse("Item deleted successfully"))).build();
            } else {
                return notFound("Item with ID " + id + " not found");
            }
        } catch (SQLException e) {
            return internalServerError("Database error: " + e.getMessage());
        }
    }

    // --- Utility Methods for Consistent Responses ---

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

    // --- Inner Classes for JSON Responses ---

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
