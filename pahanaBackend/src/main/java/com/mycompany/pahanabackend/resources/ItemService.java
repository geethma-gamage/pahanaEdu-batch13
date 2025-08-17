package com.mycompany.pahanabackend.resources;

import Utils.Item;
import Utils.ItemDAO;
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
        }
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
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
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
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
        }
    }

    @PUT
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
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
        }
    }

    @DELETE
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
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
}
