
/**
 * Write a description of class Item here.
 *
 * @author Darryl Hellams
 * @version 2017.12.11
 */
public class Item
{
    // instance variables - replace the example below with your own
    private String description;
    private double weight;

    /**
     * Constructor for objects of class Item
     */
    public Item(String description, double weight)
    {
        this.description = description;
        this.weight = weight;
    }
    
    //Getter for description and weight
    public String getDescription()
    {
        return description;
    }
    
    public double getWeight()
    {
        return weight;
    }
}
