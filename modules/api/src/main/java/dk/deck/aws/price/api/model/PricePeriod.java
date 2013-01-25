/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.deck.aws.price.api.model;

/**
 *
 * @author Jesper Terkelsen
 */
public enum PricePeriod {
    HOURLY, MONTHLY;

    public String getDisplayString() {
        switch (this){
            case HOURLY: {
                return "/hour";
            }
            case MONTHLY : {
                return "/month";
            }
            default : {
                throw new IllegalStateException("Period not supported: " + this);
            }
        }
    }
    
    
}
