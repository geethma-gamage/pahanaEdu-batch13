package com.mycompany.pahanabackend.resources;

import Utils.Customer;
import Utils.CustomerDAO;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.sql.SQLException;
import java.util.List;

@Path("customers")
public class CustomerService {

    private final CustomerDAO customerDAO = new CustomerDAO();

    // Helper to return JSON error
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
            List<Customer> customers = customerDAO.getAllCustomers();
            return Response.ok(customers).build();
        } catch (SQLException e) {
            return jsonError("DB Error: " + e.getMessage());
        }
    }

    @GET
    @Path("{accountNumber}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getOne(@PathParam("accountNumber") String accountNumber) {
        try {
            Customer customer = customerDAO.getCustomer(accountNumber);
            if (customer == null) {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("{\"error\": \"Customer not found\"}")
                        .type(MediaType.APPLICATION_JSON)
                        .build();
            }
            return Response.ok(customer).build();
        } catch (SQLException e) {
            return jsonError("DB Error: " + e.getMessage());
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response add(Customer customer) {
        try {
            if (customerDAO.addCustomer(customer)) {
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
    @Path("{accountNumber}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response update(@PathParam("accountNumber") String accNum, Customer customer) {
        try {
            customer.setAccountNumber(accNum);
            if (customerDAO.updateCustomer(customer)) {
                return Response.ok().build();
            } else {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("{\"error\": \"Customer not found\"}")
                        .type(MediaType.APPLICATION_JSON)
                        .build();
            }
        } catch (SQLException e) {
            return jsonError("DB Error: " + e.getMessage());
        }
    }

    @DELETE
    @Path("{accountNumber}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response delete(@PathParam("accountNumber") String accountNumber) {
        try {
            if (customerDAO.deleteCustomer(accountNumber)) {
                return Response.ok().build();
            } else {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("{\"error\": \"Customer not found\"}")
                        .type(MediaType.APPLICATION_JSON)
                        .build();
            }
        } catch (SQLException e) {
            return jsonError("DB Error: " + e.getMessage());
        }
    }
}
