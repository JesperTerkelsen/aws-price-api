package dk.deck.aws.price.api.model.s3;

/**
 *
 * @author Rasmus Holmann Sorensen
 */
public enum DataTier {
    FIRST_1TB("First 1 TB / month", "firstTBstorage", 1000),
    NEXT_49TB("Next 49 TB / month", "next49TBstorage", 49000),
    NEXT_450TB("Next 450 TB / month", "next450TBstorage", 450000),
    NEXT_500TB("Next 500 TB / month", "next500TBstorage", 500000),
    NEXT_4000TB("Next 4000 TB / month", "next4000TBstorage", 4000000),
    NEXT_5000TB("Over 5000 TB / month", "over5000TBstorage", 5000000);

    private final String name;
    private final String id;
    private final int sizeInGigabytes;

    DataTier(String name, String id, int sizeInGigabytes){
        this.name = name;
        this.id = id;
        this.sizeInGigabytes = sizeInGigabytes;
    }

    public String getName(){
        return name;
    }

    public String getId(){
        return id;
    }

    public int getSizeInGigabytes() {
        return sizeInGigabytes;
    }

    public static DataTier getTierById(String id){
        for(DataTier dataTier : DataTier.values()){
            if(dataTier.getId().equals(id)){
                return dataTier;
            }
        }
        return null;
    }
}
