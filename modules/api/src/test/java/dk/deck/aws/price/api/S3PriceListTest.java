package dk.deck.aws.price.api;

import dk.deck.aws.price.api.model.Product;
import dk.deck.aws.price.api.model.s3.StorageType;
import dk.deck.aws.price.api.model.s3.Tier;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.List;

/**
 *
 * @author Rasmus Holmann Sorensen
 */
public class S3PriceListTest {
    private S3PriceList s3PriceList = new S3PriceList();

    @Test
    public void testGetMonthlyStorageCostsForAllTiers() throws Exception {
        List<Product> products = s3PriceList.getMonthlyStorageCostForAllTiers(AwsRegion.EU_WEST_1.getRegionId(), StorageType.STANDARD);
        System.out.println("Storage type: " + StorageType.STANDARD.getName());
        for(Product product : products){
            System.out.println(product.getDescription() + ": " + product.getPrice());
        }
        assert 0 < products.size();
    }

    @Test
    public void testGetMonthlyStorageCostsForSpecificTier() throws Exception {
        Product product = s3PriceList.getMonthlyStorageCostForTier(AwsRegion.EU_WEST_1.getRegionId(), StorageType.STANDARD, Tier.NEXT_450TB);
        System.out.println(product.getDescription() + ": " + product.getPrice());
        assert product.getPrice().getValue().compareTo(new BigDecimal("0.070")) == 0;
    }
}
