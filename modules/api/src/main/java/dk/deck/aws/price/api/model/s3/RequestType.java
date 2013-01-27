package dk.deck.aws.price.api.model.s3;

/**
 * Created with IntelliJ IDEA.
 * User: rasmus
 * Date: 1/27/13
 * Time: 10:37 PM
 * To change this template use File | Settings | File Templates.
 */
public enum RequestType {
    PUT_COPY_POST_LIST("PUT, COPY, POST, or LIST Requests", "putcopypost", "per  1,000 requests", 1000),
    CLACIER_ARCHIVE_AND_RESTORE("Glacier Archive and Restore Requests", "glacierRequests", "per  1,000 requests", 1000),
    DELETE("Delete Requests", "deleteRequests", "", 0),
    GET_AND_OTHER("GET and all other Requests", "getEtc", "per  10,000 requests", 10000),
    GLACIER_DATA_RESTORE("Glacier Data Restores", "glacierDataRestore", "", 0);

    private final String name;
    private final String id;
    private final String rateName;
    private final int rate;

    RequestType(String name, String id, String rateName, int rate){
        this.name = name;
        this.id = id;
        this.rateName = rateName;
        this.rate = rate;
    }

    public String getName(){
        return name;
    }

    public String getId(){
        return id;
    }

    public String getRateName() {
        return rateName;
    }

    public int getRate() {
        return rate;
    }
}
