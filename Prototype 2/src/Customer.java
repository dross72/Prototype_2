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

    //created a constructor to enter all information at once
    Customer(String name,String street,String city,String state,String zip,String beerLicNum,String formOfPayment,String loadDockCapa,String deliConstraints,String POC,String phoneNum){
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
    }

    @Override
    public String toString(){
        return "Customer name:"+this.name+"\n" +
                "Street Address: "+this.street+"\n" +
                "City: "+this.city+"\n" +
                "State: "+this.state+"\n" +
                "Zip "+this.zip+"\n" +
                "Beer license #: "+this.beerLicNum+"\n" +
                "Form of Payment(cash/EBT/Fintech) "+this.formOfPayment+"\n" +
                "Loading dock Capabilities: "+this.loadDockCapa+"\n" +
                "Delivery constraints: "+this.deliConstraints+"\n" +
                "POC "+this.POC+"\n" +
                "Phone "+this.phoneNum+"\n";
    }
    //gives access to the name field for the customer list
    public String getName() {
        return name;
    }
}
