package dk.deck.aws.price.api.model;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Currency;

/**
 * Created with IntelliJ IDEA.
 * User: rasmus
 * Date: 1/28/13
 * Time: 12:03 AM
 * To change this template use File | Settings | File Templates.
 */
public class RequestPrice extends Price{
    private final Integer rate; // 1000

    protected RequestPrice(Integer quantity, Currency currency, BigDecimal value, PricePeriod period) {
        super(quantity, currency, value, period);
        rate = null;
    }

    protected RequestPrice(Integer quantity, Currency currency, BigDecimal value, PricePeriod period, Integer rate) {
        super(quantity, currency, value, period);
        this.rate = rate;
    }

    public static Price createUsdWithRate(BigDecimal value, Integer rate){
        return new RequestPrice(1, Currency.getInstance("USD"), value, PricePeriod.MONTHLY, rate);
    }

    @Override
    public String toString() {
        NumberFormat nf = NumberFormat.getInstance();
        return FORMAT.format(getTotalValue()) + "/" + nf.format(rate) + " requests";
    }
}
