package nl.hu.ipass.project.persistance.pojos;

public class Service {
    private int serviceID;
    private String serviceName;
    private String serviceDienst;
    private String serviceFrequency;

    public Service(int serviceID, String serviceName, String serviceDienst, String serviceFrequency) {
        this.serviceID = serviceID;
        this.serviceName = serviceName;
        this.serviceDienst = serviceDienst;
        this.serviceFrequency = serviceFrequency;
    }

    public int getServiceID() {
        return serviceID;
    }

    public void setServiceID(int serviceID) {
        this.serviceID = serviceID;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getServiceDienst() {
        return serviceDienst;
    }

    public void setServiceDienst(String serviceDienst) {
        this.serviceDienst = serviceDienst;
    }

    public String getServiceFrequency() {
        return serviceFrequency;
    }

    public void setServiceFrequency(String serviceFrequency) {
        this.serviceFrequency = serviceFrequency;
    }

    @Override
    public String toString() {
        return "Service{" +
                "serviceID=" + serviceID +
                ", serviceName='" + serviceName + '\'' +
                ", serviceDienst='" + serviceDienst + '\'' +
                ", serviceFrequency='" + serviceFrequency + '\'' +
                '}';
    }
}
