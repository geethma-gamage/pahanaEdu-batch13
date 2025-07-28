/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.pahanabackend.resources;

import Utils.Customer;
import Utils.CustomerDAO;
import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;

import java.sql.SQLException;
import java.util.List;

@Path("customers")
public class CustomerService {

    private final CustomerDAO customerDAO = new CustomerDAO();
    private final Jsonb jsonb = JsonbBuilder.create();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllCustomers() {
        try {
            List<Customer> customers = customerDAO.getAllCustomers();
            return Response.ok(jsonb.toJson(customers)).build();
        } catch (SQLException e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                           .entity(jsonb.toJson(new ErrorResponse("Database error: " + e.getMessage())))
                           .build();
        }
    }

    @GET
    @Path("{accountNumber}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCustomerById(@PathParam("accountNumber") String accountNumber) {
        try {
            Customer customer = customerDAO.getCustomerByAccountNumber(accountNumber);
            if (customer != null) {
                return Response.ok(jsonb.toJson(customer)).build();
            } else {
                return Response.status(Response.Status.NOT_FOUND)
                               .entity(jsonb.toJson(new ErrorResponse("Customer with account number " + accountNumber + " not found")))
                               .build();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                           .entity(jsonb.toJson(new ErrorResponse("Database error: " + e.getMessage())))
                           .build();
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addCustomer(Customer customer, @Context UriInfo uriInfo) {
        try {
            if (customer.getAccountNumber() == null || customer.getAccountNumber().isEmpty()
                || customer.getName() == null || customer.getName().isEmpty()) {
                return Response.status(Response.Status.BAD_REQUEST)
                               .entity(jsonb.toJson(new ErrorResponse("Required fields 'accountNumber' and 'name' cannot be empty")))
                               .build();
            }

            boolean added = customerDAO.addCustomer(customer);
            if (added) {
                UriBuilder builder = uriInfo.getAbsolutePathBuilder().path(customer.getAccountNumber());
                return Response.created(builder.build())
                               .entity(jsonb.toJson(new SuccessResponse("Customer added successfully")))
                               .build();
            } else {
                return Response.status(Response.Status.BAD_REQUEST)
                               .entity(jsonb.toJson(new ErrorResponse("Failed to add customer")))
                               .build();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                           .entity(jsonb.toJson(new ErrorResponse("Database error: " + e.getMessage())))
                           .build();
        }
    }

    @PUT
    @Path("{accountNumber}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateCustomer(@PathParam("accountNumber") String accountNumber, Customer customer) {
        try {
            customer.setAccountNumber(accountNumber);

            if (customer.getName() == null || customer.getName().isEmpty()) {
                return Response.status(Response.Status.BAD_REQUEST)
                               .entity(jsonb.toJson(new ErrorResponse("Required field 'name' cannot be empty")))
                               .build();
            }

            boolean updated = customerDAO.updateCustomer(customer);
            if (updated) {
                return Response.ok(jsonb.toJson(new SuccessResponse("Customer updated successfully"))).build();
            } else {
                return Response.status(Response.Status.NOT_FOUND)
                               .entity(jsonb.toJson(new ErrorResponse("Customer not found for account number: " + accountNumber)))
                               .build();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                           .entity(jsonb.toJson(new ErrorResponse("Database error: " + e.getMessage())))
                           .build();
        }
    }

    @DELETE
    @Path("{accountNumber}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteCustomer(@PathParam("accountNumber") String accountNumber) {
        try {
            boolean deleted = customerDAO.deleteCustomer(accountNumber);
            if (deleted) {
                return Response.ok(jsonb.toJson(new SuccessResponse("Customer deleted successfully"))).build();
            } else {
                return Response.status(Response.Status.NOT_FOUND)
                               .entity(jsonb.toJson(new ErrorResponse("Customer not found with account number: " + accountNumber)))
                               .build();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                           .entity(jsonb.toJson(new ErrorResponse("Database error: " + e.getMessage())))
                           .build();
        }
    }

    public static class ErrorResponse {
        private String error;

        public ErrorResponse(String error) {
            this.error = error;
        }

        public String getError() { return error; }
        public void setError(String error) { this.error = error; }
    }

    public static class SuccessResponse {
        private String message;

        public SuccessResponse(String message) {
            this.message = message;
        }

        public String getMessage() { return message; }
        public void setMessage(String message) { this.message = message; }
    }
}

