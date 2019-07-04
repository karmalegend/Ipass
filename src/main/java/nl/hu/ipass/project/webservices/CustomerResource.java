package nl.hu.ipass.project.webservices;


import nl.hu.ipass.project.persistance.CustomerDaoPosgressImpl;
import nl.hu.ipass.project.persistance.OrderDaoPostgressImpl;
import nl.hu.ipass.project.persistance.PackageDaoPosgressImpl;
import nl.hu.ipass.project.persistance.ServiceDaoPostgressImpl;
import nl.hu.ipass.project.domein.Customer;
import nl.hu.ipass.project.domein.Order;
import nl.hu.ipass.project.domein.Service;
import nl.hu.ipass.project.domein.Package;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.ArrayList;

@Path("/customer")
public class CustomerResource {

    /*
    *
    * Get all the customers
    *
    *
    * return an arraylist
    * */

    @GET
    @Path("/all")
    @Produces("application/json")
    @RolesAllowed("admin")
    public ArrayList<Customer> allCust(){
        CustomerDaoPosgressImpl custDao = new CustomerDaoPosgressImpl();
        ArrayList<Customer> arlist = custDao.getAllCustomers();

        return arlist;
    }

    @DELETE
    @Path("del/{customerid}")
    @Produces("application/json")
    @RolesAllowed("admin")
    public Response deleteCustomer(@PathParam("customerid") int id){
        CustomerDaoPosgressImpl custdao = new CustomerDaoPosgressImpl();

        if (!custdao.deleteCustomerbyId(id)){
            return Response.status(404).build();
        }

        return Response.ok().build();

    }

    /*
    *
    * This function adds a new customer to the database using the formdata provided
    * It does this by using the POST request.
    *
    * */

    @POST
    @RolesAllowed("admin")
    @Produces("application/json")
    public Response createCustomer (@FormParam("customerID") int customerID,
                                   @FormParam("compname") String companyName,
                                   @FormParam("kvknum") int kvkNumber,
                                   @FormParam("email") String email,
                                   @FormParam("phonenumber") int phonenumber,
                                   @FormParam("packageID") int packageID,
                                   @FormParam("packagename") String packageName,
                                   @FormParam("price") int packagePrice,
                                   @FormParam("orderID") int orderID,
                                   @FormParam("service1") int service1,
                                   @FormParam("service2") int service2,
                                   @FormParam("service3") int service3,
                                   @FormParam("service4") int service4,
                                   @FormParam("service5") int service5,
                                   @FormParam("service6") int service6){


            ServiceDaoPostgressImpl serviceDao = new ServiceDaoPostgressImpl();

            ArrayList<Service> services = new ArrayList<>();


            /* Check if they have an input value*/

            if (service1 != 0) {
                Service Service1 = serviceDao.getServiceByID(service1);
                services.add(Service1);
            }
            if (service2 != 0) {
                Service Service2 = serviceDao.getServiceByID(service2);
                services.add(Service2);
            }
            if (service3 != 0) {
                Service Service3 = serviceDao.getServiceByID(service3);
                services.add(Service3);
            }
            if (service4 != 0) {
                Service Service4 = serviceDao.getServiceByID(service4);
                services.add(Service4);
            }
            if (service5 != 0) {
                Service Service5 = serviceDao.getServiceByID(service5);
                services.add(Service5);
            }
            if (service6 != 0) {
                Service Service6 = serviceDao.getServiceByID(service6);
                services.add(Service6);
            }

            Order order = new Order(orderID, services);

            ArrayList<Order> orders = new ArrayList<>();

            orders.add(order);

            System.out.println(order);

            CustomerDaoPosgressImpl custdao = new CustomerDaoPosgressImpl();
            PackageDaoPosgressImpl packdao = new PackageDaoPosgressImpl();
            OrderDaoPostgressImpl orderdao = new OrderDaoPostgressImpl();


            Package pakket = new Package(packageID, packageName, packagePrice, orders);

            Customer customer = new Customer(customerID, companyName, kvkNumber, email, phonenumber, pakket);

            if(!custdao.addCustomer(customer)){
                return Response.status(409).build();
            }
            packdao.addPackage(pakket, customer);

            for (int i = 0; i < services.size(); i++) {
                orderdao.addOrder(pakket, services.get(i), order);
            }

            return Response.ok(customer).build();
    }


    /*edit a customers info*/
    @PUT
    @RolesAllowed("admin")
    @Produces("application/json")
    public Response editCustomer(@FormParam("customerName") String name,
                                 @FormParam("customerEmail") String email,
                                 @FormParam("customerPhone") int phoneNumber,
                                 @FormParam("customerID") int customerID){

        CustomerDaoPosgressImpl custdao = new CustomerDaoPosgressImpl();

        System.out.println(name + "," + email + "," + phoneNumber + "," + customerID);

        if(!custdao.updateCustomerInfo(name,email,phoneNumber,customerID)){
            return Response.status(404).build();
        }
        return Response.ok().build();
    }


}
