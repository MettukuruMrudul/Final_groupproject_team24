package property.backend.users;

import java.io.Serializable;

/**
 * Represents an amenity or feature of a property
 */
public class Amenity implements Serializable {
    private String name;
    private String description;
    private boolean isPremium; // Whether this is a premium amenity (might affect rent)
    
    // Add a serialVersionUID to maintain compatibility between versions
    private static final long serialVersionUID = 1L;
    
    // Default constructor
    public Amenity() {
    }
    
    // Constructor with name
    public Amenity(String name) {
        this.name = name;
    }
    
    // Constructor with all fields
    public Amenity(String name, String description, boolean isPremium) {
        this.name = name;
        this.description = description;
        this.isPremium = isPremium;
    }
    
    // Getters and setters
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public boolean isPremium() {
        return isPremium;
    }
    
    public void setPremium(boolean isPremium) {
        this.isPremium = isPremium;
    }
    
    @Override
    public String toString() {
        return name + (isPremium ? " (Premium)" : "");
    }
}