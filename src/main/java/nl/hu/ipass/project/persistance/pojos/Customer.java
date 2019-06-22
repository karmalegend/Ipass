package nl.hu.ipass.project.persistance.pojos;

public class Customer {
    private int customerID;
    private String companyname;
    private int kvkNumber;
    private String emailadress;
    private int phonenumber;
    private Package pakket;


    public Customer(int customerID, String companyname, int kvkNumber, String emailadress, int phonenumber, Package pakket) {
        this.customerID = customerID;
        this.companyname = companyname;
        this.kvkNumber = kvkNumber;
        this.emailadress = emailadress;
        this.phonenumber = phonenumber;
        this.pakket = pakket;
    }

    public Package getPakket() {
        return pakket;
    }

    public void setPakket(Package pakket) {
        this.pakket = pakket;
    }

    public int getCustomerID() {
        return customerID;
    }

    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    public String getCompanyname() {
        return companyname;
    }

    public void setCompanyname(String companyname) {
        this.companyname = companyname;
    }

    public int getKvkNumber() {
        return kvkNumber;
    }

    public void setKvkNumber(int kvkNumber) {
        this.kvkNumber = kvkNumber;
    }

    public String getEmailadress() {
        return emailadress;
    }

    public void setEmailadress(String emailadress) {
        this.emailadress = emailadress;
    }

    public int getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(int phonenumber) {
        this.phonenumber = phonenumber;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "customerID=" + customerID +
                ", companyname='" + companyname + '\'' +
                ", kvkNumber=" + kvkNumber +
                ", emailadress='" + emailadress + '\'' +
                ", phonenumber=" + phonenumber +
                ", pakket=" + pakket +
                '}';
    }
}
