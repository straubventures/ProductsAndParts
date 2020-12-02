package model;
/** This class creates InHouse objects from Part Objects*/
public class InHouse extends Part {

    private int machineID;

    /** This method returns the MachineID of the part.
     * @return the machineID. */
    public int getMachineId() {
        return machineID;
    }
    /** This method sets the MachineID of the part.
     * @param machineID is the id which will be set. */
    public void setMachineId(int machineID) {
        this.machineID = machineID;
    }
    /** This method is the constructor of the InHouse subclass of a Part.
     * @param id is the id of the new object.
     * @param name is the name.
     * @param price is the price.
     * @param stock is the inventory level.
     * @param min is the min amount of inventory allowed.
     * @param max is the max amount of inventory allowed.
     * @param machineId is the machineId. */

    public InHouse(int id, String name, double price, int stock, int min, int max, int machineId) {
        super(id, name, price, stock, min, max);
        this.machineID = machineId;
    }
}
