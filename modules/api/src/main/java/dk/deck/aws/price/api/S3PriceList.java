package dk.deck.aws.price.api;

import dk.deck.aws.price.api.model.Price;
import dk.deck.aws.price.api.model.PricePeriod;
import dk.deck.aws.price.api.model.Product;
import dk.deck.aws.price.api.model.s3.DataTier;
import dk.deck.aws.price.api.model.s3.RequestType;
import dk.deck.aws.price.api.model.s3.StorageType;
import dk.deck.aws.price.api.model.s3.DataTier;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Currency;
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

    // Caching
    private Map<String, Object> storagePrices;
    private Map<String, Object> requestsPrices;
    private Map<String, Object> dataTransferPrices;

    public List<Product> getMonthlyStorageCostForAllTiers(AwsRegion region, StorageType storageType) throws IOException {
        List<Product> products = new ArrayList<Product>();
        List<Map<String, Object>> tiers = getTiers(region);
        if(tiers != null){
            for (Map<String, Object> tier : tiers) {
                String tierId = (String) tier.get("name");
                Price price = getStoragePrice(getSubList(tier, "storageTypes"), storageType);
                products.add(new Product(DataTier.getTierById(tierId).getName(), price));
            }
        }

        return products;
    }

    public Product getMonthlyStorageCostForTier(AwsRegion region, StorageType storageType, DataTier dataTier) throws IOException{
        List<Map<String, Object>> tiers = getTiers(region);
        if(dataTier != null){
            for(Map<String, Object> tierElement : tiers){
                String tierId = (String) tierElement.get("name");
                if(tierId.equals(dataTier.getId())){
                    Price price = getStoragePrice(getSubList(tierElement, "storageTypes"), storageType);
                    return new Product(dataTier.getName(), price);
                }
            }
        }
        return null;
    }

    private List<Map<String, Object>> getTiers(AwsRegion region) throws IOException{
        Map<String, Object> regionMap = getRegion(region.getRegionId(), getStoragePrices());
        return getSubList(regionMap, "tiers");
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

    public Product getRequestCostByType(AwsRegion region, RequestType requestType) throws IOException{
        Map<String, Object> regionMap = getRegion(region.getRegionId(), getRequestsPrices());
        List<Map<String, Object>> types = getSubList(regionMap, "tiers");
        for(Map<String, Object> type : types){
            if(type.get("name").equals(requestType.getId())){
                String priceText = (String) ((Map<String, Object>)type.get("prices")).get("USD");
                Price price = Price.createUsdWithRate(new BigDecimal(priceText), requestType.getRate());
                return new Product(requestType.getName(), price);
            }
        }

        return null;
    }


    private Map<String, Object> getStoragePrices() throws IOException{
        if (storagePrices == null) {
            String result = getRequestAsString(storage);
            storagePrices = mapper.readValue(result, Map.class);
        }
        return storagePrices;
    }

    private Map<String, Object> getRequestsPrices() throws IOException{
        if (requestsPrices == null) {
            String result = getRequestAsString(requests);
            requestsPrices = mapper.readValue(result, Map.class);
        }
        return requestsPrices;
    }

    private Map<String, Object> getDataTransferPrices() throws IOException{
        if (dataTransferPrices == null) {
            String result = getRequestAsString(dataTransfer);
            dataTransferPrices = mapper.readValue(result, Map.class);
        }
        return dataTransferPrices;
    }
}
