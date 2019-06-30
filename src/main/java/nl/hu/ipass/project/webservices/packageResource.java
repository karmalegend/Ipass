package nl.hu.ipass.project.webservices;

import nl.hu.ipass.project.persistance.OrderDaoPostgressImpl;
import nl.hu.ipass.project.persistance.PackageDaoPosgressImpl;
import nl.hu.ipass.project.domein.Order;
import nl.hu.ipass.project.domein.Package;
import nl.hu.ipass.project.domein.Service;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.ArrayList;

@Path("/package")
public class packageResource {


    @POST
    @Path("/edit")
    @RolesAllowed("admin")
    @Produces("application/json")
    public Response editPackage(@FormParam("defaultPackageID") int defaultId,
                                @FormParam("currentOrderID") int currentOrderId,
                                @FormParam("customerID") int customerID,
                                @FormParam("currentPackageId") int currentPackageID){
        try {
            OrderDaoPostgressImpl orderdao = new OrderDaoPostgressImpl();
            PackageDaoPosgressImpl packageDao = new PackageDaoPosgressImpl();

            //delete customers current order
            orderdao.deleteOrderById(currentOrderId);

            //get the default package name and price
            Package basePackage = packageDao.getPackagebyID(defaultId);
            String packageName = basePackage.getPackageName();
            int packagePrice = basePackage.getPagckagePrice();

            //set the old package name and price to the new package
            packageDao.editPackage(packageName, packagePrice, customerID);

            //get the package default services.
            Order BasePackageOrder = orderdao.getOrdersByPackageID(defaultId);
            ArrayList<Service> services = BasePackageOrder.getServices();

            //loop through defaultpackage services and add them to new package
            for (Service service : services) {
                int serviceID = service.getServiceID();
                orderdao.addServiceToOrder(currentOrderId, serviceID, currentPackageID);
            }
            return Response.ok().build();
        }
        catch(Exception e){
            e.printStackTrace();
            return Response.status(404).build();
        }

    }

}
