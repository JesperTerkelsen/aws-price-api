/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.deck.aws.price.api;

/**
 * Enum representing the various aws endpoints
 *
 * http://docs.amazonwebservices.com/general/latest/gr/rande.html
 *
 * @author Jesper Terkelsen
 */
public enum AwsRegion {
    // Constants
    US_EAST_1("US East", "Northern Virginia", "us-east-1", "us-east"),
    US_WEST_2("US West", "Oregon", "us-west-2", "us-west-2"),
    US_WEST_1("US West", "Northern California", "us-west-1", "us-west"),
    EU_WEST_1("EU", "Ireland", "eu-west-1", "eu-ireland"),
    AP_SOUTHEAST_1("Asia Pacific", "Singapore", "ap-southeast-1", "apac-sin"),
    AP_SOUTHEAST_2("Asia Pacific", "Sydney", "ap-southeast-2", "apac-tokyo"),
    AP_NORTHEAST_1("Asia Pacific", "Tokyo", "ap-northeast-1", "apac-syd"),
    SA_EAST_1("South America", "Sao Paulo", "sa-east-1", "sa-east-1");
    
    // Fields
    private final String name; // Region name
    private final String city; // The city name
    private final String regionId; // name embedded in the DNS names, and used in the aws api
    private final String priceName; // From the json price list   

    AwsRegion(String name, String city, String regionId, String priceName) {
        this.name = name;
        this.city = city;
        this.regionId = regionId;
        this.priceName = priceName;
    }

    public String getName() {
        return name;
    }

    public String getCity() {
        return city;
    }

    public String getRegionId() {
        return regionId;
    }

    public String getPriceName() {
        return priceName;
    }

    public String getEc2Endpoint(){
        return "https://ec2." + regionId + ".amazonaws.com";
    }
    
    public String getRdsEndpoint(){
        return "https://rds." + regionId + ".amazonaws.com";
    }
    
    public String getS3Endpoint(){
        return "https://s3-" + regionId + ".amazonaws.com";
    }
    
    public String getEc2LbEndpoint(){
        return "https://elasticloadbalancing." + regionId + ".amazonaws.com";
    }
    
    public String getIamEndpoint(){
        return "https://iam.amazonaws.com";
    }
    
    
    public static AwsRegion getByRegionId(String regionId) {
        AwsRegion[] regions = AwsRegion.values();
        for (AwsRegion region : regions) {
            if (region.regionId.equals(regionId)){
                return region;
            }
        }
        return null;
    }
}
