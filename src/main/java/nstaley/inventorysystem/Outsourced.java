package nstaley.inventorysystem;

/**
 * This class is for creating parts that were made by another company.
 * @author Nicholas Staley
 */
public class Outsourced extends Part {
    /**
     * Holds the company name for the part.
     */
    private String companyName;

    /**
     *The constructor that initializes the Outsourced parts.
     * @param id part id
     * @param name part name
     * @param price part price
     * @param stock part stock
     * @param min minimum part stock
     * @param max maximum part stock
     * @param companyName company that made part
     */
    public Outsourced(int id, String name, double price, int stock, int min, int max, String companyName) {
        super(id, name, price, stock, min, max);
        this.companyName = companyName;
    }
    /**
     * This method sets the company Name for the part.
     * @param companyName company name for the part
     */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    /**
     * This method returns the company name of the part.
     * @return Returns a String value representing the company name.
     */
    public String getCompanyName() {
        return companyName;
    }
}
