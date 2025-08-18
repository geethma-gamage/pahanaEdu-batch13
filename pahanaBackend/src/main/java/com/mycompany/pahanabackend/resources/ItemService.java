package com.mycompany.pahanabackend.resources;

import Utils.Item;
import Utils.ItemDAO;
<<<<<<< HEAD
=======
<<<<<<< HEAD
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.sql.SQLException;
import java.util.List;

@Path("items")
public class ItemService {

    private final ItemDAO itemDAO = new ItemDAO();

    // Helper JSON error response
    private Response jsonError(String message) {
        return Response.serverError()
                .entity("{\"error\": \"" + message.replace("\"", "\\\"") + "\"}")
                .type(MediaType.APPLICATION_JSON)
                .build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAll() {
        try {
            List<Item> items = itemDAO.getAllItems();
            return Response.ok(items).build();
        } catch (SQLException e) {
            return jsonError("DB Error: " + e.getMessage());
=======
>>>>>>> 8d4a862db93c08c7927b522ee21514342ea94507
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
<<<<<<< HEAD
=======
>>>>>>> a9faf870ba723fa441e582c5a98c095f91a1489a
>>>>>>> 8d4a862db93c08c7927b522ee21514342ea94507
        }
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
<<<<<<< HEAD
=======
<<<<<<< HEAD
    public Response getOne(@PathParam("id") int id) {
        try {
            Item item = itemDAO.getItem(id);
            if (item == null) {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("{\"error\": \"Item not found\"}")
                        .type(MediaType.APPLICATION_JSON)
                        .build();
            }
            return Response.ok(item).build();
        } catch (SQLException e) {
            return jsonError("DB Error: " + e.getMessage());
=======
>>>>>>> 8d4a862db93c08c7927b522ee21514342ea94507
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
<<<<<<< HEAD
=======
>>>>>>> a9faf870ba723fa441e582c5a98c095f91a1489a
>>>>>>> 8d4a862db93c08c7927b522ee21514342ea94507
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
<<<<<<< HEAD
=======
<<<<<<< HEAD
    public Response add(Item item) {
        try {
            if (itemDAO.addItem(item)) {
                return Response.status(Response.Status.CREATED).build();
            } else {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity("{\"error\": \"Insert failed\"}")
                        .type(MediaType.APPLICATION_JSON)
                        .build();
            }
        } catch (SQLException e) {
            return jsonError("DB Error: " + e.getMessage());
=======
>>>>>>> 8d4a862db93c08c7927b522ee21514342ea94507
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
<<<<<<< HEAD
=======
>>>>>>> a9faf870ba723fa441e582c5a98c095f91a1489a
>>>>>>> 8d4a862db93c08c7927b522ee21514342ea94507
        }
    }

    @PUT
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
<<<<<<< HEAD
=======
<<<<<<< HEAD
    public Response update(@PathParam("id") int id, Item item) {
        try {
            item.setId(id);
            if (itemDAO.updateItem(item)) {
                return Response.ok().build();
            } else {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("{\"error\": \"Item not found\"}")
                        .type(MediaType.APPLICATION_JSON)
                        .build();
            }
        } catch (SQLException e) {
            return jsonError("DB Error: " + e.getMessage());
=======
>>>>>>> 8d4a862db93c08c7927b522ee21514342ea94507
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
<<<<<<< HEAD
=======
>>>>>>> a9faf870ba723fa441e582c5a98c095f91a1489a
>>>>>>> 8d4a862db93c08c7927b522ee21514342ea94507
        }
    }

    @DELETE
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
<<<<<<< HEAD
=======
<<<<<<< HEAD
    public Response delete(@PathParam("id") int id) {
        try {
            if (itemDAO.deleteItem(id)) {
                return Response.ok().build();
            } else {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("{\"error\": \"Item not found\"}")
                        .type(MediaType.APPLICATION_JSON)
                        .build();
            }
        } catch (SQLException e) {
            return jsonError("DB Error: " + e.getMessage());
        }
    }
=======
>>>>>>> 8d4a862db93c08c7927b522ee21514342ea94507
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
<<<<<<< HEAD
=======
>>>>>>> a9faf870ba723fa441e582c5a98c095f91a1489a
>>>>>>> 8d4a862db93c08c7927b522ee21514342ea94507
}
