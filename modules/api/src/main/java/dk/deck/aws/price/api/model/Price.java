/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.deck.aws.price.api.model;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Currency;

/**
 * Represents a price for a aws product
 * 
 * @author Jesper Terkelsen
 */
public class Price {
    private final Integer quantity; // 1
    private final Currency currency; // USD
    private final BigDecimal value; // 0.025
    private final PricePeriod period; // HOURLY
    private final NumberFormat FORMAT;
    
    public static Price createUsdHourly(BigDecimal value) {
        return new Price(1, Currency.getInstance("USD"), value, PricePeriod.HOURLY);
    }
    
    public static Price createUsdMonthly(BigDecimal value) {
        return new Price(1, Currency.getInstance("USD"), value, PricePeriod.MONTHLY);
    }
    
    public static Price createHourly(Currency currency, BigDecimal value) {
        return new Price(1, currency, value, PricePeriod.HOURLY);
    }
    
    public static Price create(Currency currency, BigDecimal value, PricePeriod period) {
        return new Price(1, currency, value, period);
    }
    
    public static Price createWithQuantity(Integer quantity, Currency currency, BigDecimal value, PricePeriod period){
        return new Price(quantity, currency, value, period);
    }
    
    protected Price(Integer quantity, Currency currency, BigDecimal value, PricePeriod period) {
        this.quantity = quantity;
        this.currency = currency;
        this.value = value;
        this.period = period;
        FORMAT = NumberFormat.getCurrencyInstance();
        FORMAT.setMaximumFractionDigits(5);
        FORMAT.setMinimumFractionDigits(2);
        FORMAT.setCurrency(currency);
    }

    public Integer getQuantity() {
        return quantity;
    }

    public Currency getCurrency() {
        return currency;
    }

    public PricePeriod getPeriod() {
        return period;
    }

    public BigDecimal getValue() {
        return value;
    }

    public BigDecimal getTotalValue(){
        return value.multiply(new BigDecimal(quantity));
    }

    public Price add(Price other){
        if (this.currency != other.currency){
            throw new IllegalArgumentException("Currencies do not match " + this.currency + "!=" + other.currency);
        }
        if (this.period != other.period){
            throw new IllegalArgumentException("Periods do not match " + this.period + "!=" + other.period);
        }
        return Price.create(currency, this.getTotalValue().add(other.getTotalValue()), period);
    }
    
    public Price withQuantity(int quantity){
        return new Price(quantity, currency, value, period);
    }
    
    public Price withCurrency(Currency currency, BigDecimal exchangeRate){
        return new Price(quantity, currency, value.multiply(exchangeRate), period);
    }
    
    /**
     * Returns the monthly price
     * @param daysInMonth How many days there is in the month you are calculating
     * @return The monthly price
     */
    public Price monthly(int daysInMonth) {
        if (period == PricePeriod.MONTHLY){
            return this;
        }
        else if (period == PricePeriod.HOURLY){
            BigDecimal newValue = value.multiply(new BigDecimal(24)).multiply(new BigDecimal(daysInMonth));
            return new Price(quantity, currency, newValue, PricePeriod.MONTHLY);
        }
        else {
            throw new IllegalStateException("Period not supported " + period);
        }
    }
    
    @Override
    public String toString() {
        return FORMAT.format(getTotalValue()) + period.getDisplayString();
    }
    
    
}
