/**
 * A customer (store) that a sales rep can place orders for.
 * Field list matches the client's new customer form exactly.
 */
public class Customer {
    //initializing all the fields
    private String name;
    private String street;
    private String city;
    private String state;
    private String zip;
    private String beerLicNum;
    private String formOfPayment;
    private String loadDockCapa;
    private String deliConstraints;
    private String POC;
    private String phoneNum;
    //account id (15 characters) links the customer to orders,
    //owner rep controls which rep can open this customer
    private String accountId;
    private String ownerRepId;

    //created a constructor to enter all information at once
    public Customer(String name, String street, String city, String state, String zip, String beerLicNum, String formOfPayment, String loadDockCapa, String deliConstraints, String POC, String phoneNum, String accountId, String ownerRepId) {
        this.name = name;
        this.street = street;
        this.city = city;
        this.state = state;
        this.zip = zip;
        this.beerLicNum = beerLicNum;
        this.formOfPayment = formOfPayment;
        this.loadDockCapa = loadDockCapa;
        this.deliConstraints = deliConstraints;
        this.POC = POC;
        this.phoneNum = phoneNum;
        this.accountId = accountId;
        this.ownerRepId = ownerRepId;
    }

    //prints out the customer form according to the sample
    @Override
    public String toString() {
        return "Customer name: " + this.name + "\n" +
                "Account ID: " + this.accountId + "\n" +
                "Street Address: " + this.street + "\n" +
                "City: " + this.city + "\n" +
                "State: " + this.state + "\n" +
                "Zip: " + this.zip + "\n" +
                "Beer license #: " + this.beerLicNum + "\n" +
                "Form of Payment (cash/EBT/Fintech): " + this.formOfPayment + "\n" +
                "Loading dock Capabilities: " + this.loadDockCapa + "\n" +
                "Delivery constraints: " + this.deliConstraints + "\n" +
                "POC: " + this.POC + "\n" +
                "Phone: " + this.phoneNum + "\n";
    }

    //gives access to the name field for the customer list
    public String getName() {
        return name;
    }

    public String getAccountId() {
        return accountId;
    }

    public String getOwnerRepId() {
        return ownerRepId;
    }

    // getters for the editable fields, used by the edit customer screen
    public String getStreet() { return street; }
    public String getCity() { return city; }
    public String getState() { return state; }
    public String getZip() { return zip; }
    public String getBeerLicNum() { return beerLicNum; }
    public String getFormOfPayment() { return formOfPayment; }
    public String getLoadDockCapa() { return loadDockCapa; }
    public String getDeliConstraints() { return deliConstraints; }
    public String getPOC() { return POC; }
    public String getPhoneNum() { return phoneNum; }

    // setters for the fields a rep is allowed to change. company name and
    // form of payment are left out on purpose, those are locked to the
    // business office
    public void setStreet(String newStreet){
        street = newStreet;
    }
    public void setCity(String newCity){
        city = newCity;
    }
    public void setState(String newState){
        state = newState;
    }
    public void setZip(String newZip){
        zip = newZip;
    }
    public void setBeerLicNum(String beerLicNum) {
        this.beerLicNum = beerLicNum;
    }
    public void setLoadDockCapa(String loadDockCapa) {
        this.loadDockCapa = loadDockCapa;
    }
    public void setDeliConstraints(String deliConstraints) {
        this.deliConstraints = deliConstraints;
    }
    public void setPOC(String POC) {
        this.POC = POC;
    }
    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    /**
     * Turns the customer back into one tab separated line so it
     * can be appended to the customers file (our simulated database).
     */
    public String toFileLine() {
        return String.join("\t", name, accountId, street, city, state, zip, beerLicNum, formOfPayment, loadDockCapa, deliConstraints, POC, phoneNum, ownerRepId);
    }
}
