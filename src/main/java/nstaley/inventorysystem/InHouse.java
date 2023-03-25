package nstaley.inventorysystem;
/**
 * This class is for creating parts that are made in house to be added to the inventory system.
 * @author Nicholas Staley
 */
public class InHouse extends Part{
    /**
     * Holds the machine ID of the part.
     */
    private int machineID;


    /**
     *The constructor that initializes the InHouse parts.
     * @param id id of part
     * @param name name of part
     * @param price price of part
     * @param stock stock of part
     * @param min minimum number of stock
     * @param max maximum number of stock
     * @param machineID machine ID that made part
     */
    public InHouse(int id, String name, double price, int stock, int min, int max, int machineID) {
        super(id, name, price, stock, min, max);
        this.machineID = machineID;
    }

    /**
     * This method sets the machine id.
     * @param machineID the machine id to set
     */
    public void setMachineID(int machineID) {
        this.machineID = machineID;
    }

    /**
     * This method returns the Machine ID.
     * @return Returns an int value representing the Machine ID.
     */
    public int getMachineID() {
        return this.machineID;
    }
}
