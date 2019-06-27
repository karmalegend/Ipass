package nl.hu.ipass.project.webservices;


import nl.hu.ipass.project.persistance.CustomerDaoPosgressImpl;
import nl.hu.ipass.project.persistance.ServiceDaoPostgressImpl;
import nl.hu.ipass.project.persistance.pojos.Customer;
import nl.hu.ipass.project.persistance.pojos.Order;
import nl.hu.ipass.project.persistance.pojos.Service;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.ArrayList;

@Path("/customer")
public class CustomerResource {

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


    @POST
    @RolesAllowed("admin")
    @Produces("application/json")
    public Response createCustomer (@FormParam("customerID") int customerID,
                                   @FormParam("compname") String companyName,
                                   @FormParam("kvknum") int kvkNumber,
                                   @FormParam("email") String email,
                                   @FormParam("phonenumber") int phonenumber,
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

        if(service1 != 0){
            Service Service1 = serviceDao.getServiceByID(service1);
            services.add(Service1);
        }
        if (service2 != 0){
            Service Service2 = serviceDao.getServiceByID(service2);
            services.add(Service2);
        }
        if (service3 != 0){
            Service Service3 = serviceDao.getServiceByID(service3);
            services.add(Service3);
        }
        if (service4 != 0){
            Service Service4 = serviceDao.getServiceByID(service4);
            services.add(Service4);
        }
        if (service5 != 0){
            Service Service5 = serviceDao.getServiceByID(service5);
            services.add(Service5);
        }
        if (service6 != 0){
            Service Service6 = serviceDao.getServiceByID(service6);
            services.add(Service6);
        }

        Order order = new Order(orderID,services);

        //TODO: get packageID from front-end means storing it in localstorage and giving it the same treatment as package / customer ID

        Package packagee = new Package();


        System.out.println(customerID);
        System.out.println(companyName);
        System.out.println(kvkNumber);
        System.out.println(email);
        System.out.println(phonenumber);
        System.out.println(packageName);
        System.out.println(packagePrice);
        System.out.println(orderID);
        System.out.println(service1);
        System.out.println(service2);
        System.out.println(service3);
        System.out.println(service4);
        System.out.println(service5);
        System.out.println(service6);
        return Response.ok().build();

    }


}
