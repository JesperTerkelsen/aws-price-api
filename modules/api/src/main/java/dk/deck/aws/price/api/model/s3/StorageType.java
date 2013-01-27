package dk.deck.aws.price.api.model.s3;

/**
 *
 * @author Rasmus Holmann Sorensen
 */
public enum StorageType {
    STANDARD("Standard Storage", "storage"),
    REDUCED_REDUNDANCY("Reduced Redundancy Storage", "reducedRedundancyStorage"),
    GLACIER("Glacier Storage", "glacierStorage");

    private final String name;
    private final String id;

    StorageType(String name, String id){
        this.name = name;
        this.id = id;
    }

    public String getName(){
        return name;
    }

    public String getId(){
        return id;
    }
}
