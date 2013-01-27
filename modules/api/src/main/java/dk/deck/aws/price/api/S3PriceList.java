package dk.deck.aws.price.api;

import dk.deck.aws.price.api.model.Price;
import dk.deck.aws.price.api.model.Product;
import dk.deck.aws.price.api.model.s3.StorageType;
import dk.deck.aws.price.api.model.s3.Tier;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Rasmus Holmann Sorensen
 */
public class S3PriceList extends AbstractPriceList {

    //S3 prices
    private static final String storage = "http://aws.amazon.com/s3/pricing/pricing-storage.json";
    private static final String requests = "http://aws.amazon.com/s3/pricing/pricing-requests.json";
    private static final String dataTransfer = "http://aws.amazon.com/s3/pricing/pricing-data-transfer.json";

    public List<Product> getMonthlyStorageCostForAllTiers(String regionName, StorageType storageType) throws IOException {
        List<Product> products = new ArrayList<Product>();
        List<Map<String, Object>> tiers = getTiers(regionName);
        if(tiers != null){
            for (Map<String, Object> tier : tiers) {
                String tierId = (String) tier.get("name");
                Price price = getStoragePrice(getSubList(tier, "storageTypes"), storageType);
                products.add(new Product(Tier.getTierById(tierId).getName(), price));
            }
        }

        return products;
    }

    public Product getMonthlyStorageCostForTier(String regionName, StorageType storageType, Tier tier) throws IOException{
        List<Map<String, Object>> tiers = getTiers(regionName);
        if(tier != null){
            for(Map<String, Object> tierElement : tiers){
                String tierId = (String) tierElement.get("name");
                if(tierId.equals(tier.getId())){
                    Price price = getStoragePrice(getSubList(tierElement, "storageTypes"), storageType);
                    return new Product(tier.getName(), price);
                }
            }
        }
        return null;
    }

    private List<Map<String, Object>> getTiers(String regionName) throws IOException{
        List<Product> products = new ArrayList<Product>();
        String result = getRequestAsString(storage);
        Map<String, Object> storagePrices = mapper.readValue(result, Map.class);
        Map<String, Object> region = getRegion(regionName, storagePrices);
        return getSubList(region, "tiers");
    }



    private Price getStoragePrice(List<Map<String, Object>> storageTypes, StorageType storageType){
        for(Map<String, Object> st : storageTypes){
            String type = (String) st.get("type");
            if(type.equals(storageType.getId())){
                String price = (String) ((Map<String, Object>)st.get("prices")).get("USD");
                return Price.createUsdMonthly(new BigDecimal(price));
            }
        }

        return null;
    }
}
