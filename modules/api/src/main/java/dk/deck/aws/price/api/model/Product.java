/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.deck.aws.price.api.model;

/**
 *
 * @author Jesper Terkelsen
 */
public class Product {
    private final String description;
    private final Price price;

    public Product(String description, Price price) {
        this.description = description;
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public Price getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return description + " " + price.toString();
    }
    
    
}
