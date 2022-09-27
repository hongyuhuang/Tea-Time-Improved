/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package web;

import dao.CustomerDAO;
import domain.Customer;
import io.jooby.Jooby;
import io.jooby.StatusCode;

/**
 *
 * @author hongyuhuang
 */
public class CustomerModule extends Jooby {

    public CustomerModule(CustomerDAO dao) {
        post("/api/register/", ctx -> {
            Customer customer = ctx.body().to(Customer.class);
            dao.save(customer);
            return ctx.send(StatusCode.CREATED);
        });
        
        get("/api/customers/{username}", ctx -> {
            String username = ctx.path("username").value();
            Customer customer = dao.getByUsername(username);
            customer.setPassword(null);
            customer.setShippingAddress(null);
            customer.setEmailAddress(null);
            if (customer == null) {
                // no customer with that username found, so return a 404/Not Found error
                return ctx.send(StatusCode.NOT_FOUND);
            } else {
                return customer;
            }

        });
    }
}
