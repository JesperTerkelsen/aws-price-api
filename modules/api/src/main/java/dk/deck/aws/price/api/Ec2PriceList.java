/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.deck.aws.price.api;

import dk.deck.aws.price.api.model.Price;
import dk.deck.aws.price.api.model.ec2.InstanceTypeMapping;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * The aws pricelists are embedded in the official price list page as json
 * objects.
 *
 * This enables us to get the updated list via those resources automatically
 *
 * @author Jesper Terkelsen
 */
public class Ec2PriceList extends AbstractPriceList {
    // Ec2 prices

    private static final String ec2OnDemand = "http://aws.amazon.com/ec2/pricing/pricing-on-demand-instances.json";
    private static final String ec2Reserved = "http://aws.amazon.com/ec2/pricing/pricing-reserved-instances.json";
    private static final String ec2Transfer = "http://aws.amazon.com/ec2/pricing/pricing-data-transfer.json";
    private static final String ec2ElasticIp = "http://aws.amazon.com/ec2/pricing/pricing-elastic-ips.json";
    private static final String ec2Lb = "http://aws.amazon.com/ec2/pricing/pricing-elb.json";
    private static final String ec2Ebs = "http://aws.amazon.com/ec2/pricing/pricing-ebs.json";

    // S3
    private static final String s3Storage = "http://aws.amazon.com/s3/pricing/pricing-storage.json";
    

    // Caching
    private Map<String, Object> onDemandPrices;
    private Map<String, Object> lbPrices;
    private Map<String, Object> ebsPrices;

    public Ec2PriceList() {
    }
    
    public Price getVolumeGigabyteRate(String regionName) throws IOException {
        getEc2OnDemandPrices();
        Map<String, Object> region = getRegion(regionName, ebsPrices);
        Map<String, Object> ebsVols = getJsonElementFromList(region, "types", "name", "ebsVols");
        Map<String, Object> prices = getJsonElementFromList(ebsVols, "values", "rate", "perGBmoProvStorage");
        if (prices != null){
            String price = (String) getSubElement(prices, "prices").get("USD");
            return Price.createUsdMonthly(new BigDecimal(price));
        }
        return null;
    }
    
    public Price getVolumeSnapshotGigabyteRate(String regionName) throws IOException {
        getEc2OnDemandPrices();
        Map<String, Object> region = getRegion(regionName, ebsPrices);
        Map<String, Object> ebsVols = getJsonElementFromList(region, "types", "name", "ebsSnapsToS3");
        Map<String, Object> prices = getJsonElementFromList(ebsVols, "values", "rate", "perGBmoDataStored");   
        if (prices != null){
            String price = (String) getSubElement(prices, "prices").get("USD");
            return Price.createUsdMonthly(new BigDecimal(price));
        }
        return null;
    }

    public Price getLbHourRate(String regionName) throws IOException {
        getLoadBalancerPrices();
        Map<String, Object> region = getRegion(regionName, lbPrices);
        List<Map<String, Object>> types = getSubList(region,"types");
        for (Map<String, Object> type : types) {
            Map<String, Object> prices = getJsonElementFromList(type, "values", "rate", "perELBHour");
            if (prices != null){
                String price = (String) getSubElement(prices, "prices").get("USD");
                return Price.createUsdHourly(new BigDecimal(price));
            }
        }
        return null;
    }
    
    public Price getEc2HourRate(String regionName, String instanceType, String platform) throws IOException {
        getEc2OnDemandPrices(); // Make sure cache is loaded
        Map<String, Object> region = getRegion(regionName, onDemandPrices);
        if (region != null) {
            // System.out.println("found region " + region.get("region"));
            Map<String, Object> size = getEx2InstanceSize(region, instanceType);
            if (size != null) {
                // System.out.println("found size " + size.get("size"));
                List<Map<String, Object>> valueColumns = getSubList(size,"valueColumns");
                for (Map<String, Object> column : valueColumns) {
                    String name = (String) column.get("name");
                    Map<String,String> prices = (Map<String,String>) column.get("prices");
                    String price = prices.get("USD");
                    if (name.equals("linux")){
                        if (platform == null || !platform.equals("windows")){
                            return Price.createUsdHourly(new BigDecimal(price));
                        }
                    }
                    else { // Windows
                        if (platform != null && platform.equals("windows")){
                            return Price.createUsdHourly(new BigDecimal(price));
                        }                        
                    }
                }
            }
        }
        return null;
    }

    private Map<String, Object> getEx2InstanceSize(Map<String, Object> region, String instanceType) {
        if (region != null) {
            List<Map<String, Object>> instanceTypes = getSubList(region,"instanceTypes");
            for (Map<String, Object> map : instanceTypes) {
                String typeGroup = (String) map.get("type");
                List<Map<String, Object>> sizes = getSubList(map,"sizes");
                for (Map<String, Object> size : sizes) {
                    String sizeName = (String) size.get("size");
                    String instanceTypeValue = InstanceTypeMapping.getInstanceNameFromPriceNames(typeGroup, sizeName);
                    if (instanceTypeValue != null && instanceTypeValue.equals(instanceType)) {
                        return size;
                    }
                }
            }
        }
        return null;
    }

    private void getLoadBalancerPrices() throws IOException {
        if (lbPrices == null) {
            String result = getRequestAsString(ec2Lb);
            lbPrices = mapper.readValue(result, Map.class);
        }
    }    
    
    private void getEc2OnDemandPrices() throws IOException {
        if (onDemandPrices == null) {
            String result = getRequestAsString(ec2OnDemand);
            onDemandPrices = mapper.readValue(result, Map.class);
        }
        if (ebsPrices == null){
            String result = getRequestAsString(ec2Ebs);
            ebsPrices = mapper.readValue(result, Map.class);
        }
    }

}
