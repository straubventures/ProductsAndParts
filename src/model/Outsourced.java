package model;

/** This class creates InHouse objects from Part Objects. */
public class Outsourced extends Part {

    private String companyName;

    /** This method gets the company name of the outsourced part.
     * @return name of company. */
    public String getCompanyName() {
        return companyName;
    }

   /** Sets the name of the company.
    * @param companyName is the desired name input */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    /** This method is the constructor of the outsourced subclass of a Part.
     * @param id is the id of the new object.
     * @param name is the name.
     * @param price is the price.
     * @param stock is the inventory level.
     * @param min is the min amount of inventory allowed.
     * @param max is the max amount of inventory allowed.
     * @param companyName  is the company name. */
    public Outsourced(int id, String name, double price, int stock, int min, int max, String companyName) {
        super(id, name, price, stock, min, max);
        this.companyName = companyName;
    }

}
