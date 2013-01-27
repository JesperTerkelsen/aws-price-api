package dk.deck.aws.price.api.model.s3;

/**
 *
 * @author Rasmus Holmann Sorensen
 */
public enum Tier {
    FIRST_1TB("First 1 TB / month", "firstTBstorage"),
    NEXT_49TB("Next 49 TB / month", "next49TBstorage"),
    NEXT_450TB("Next 450 TB / month", "next450TBstorage"),
    NEXT_500TB("Next 500 TB / month", "next500TBstorage"),
    NEXT_4000TB("Next 4000 TB / month", "next4000TBstorage"),
    NEXT_5000TB("Over 5000 TB / month", "over5000TBstorage");

    private final String name;
    private final String id;

    Tier(String name, String id){
        this.name = name;
        this.id = id;
    }

    public String getName(){
        return name;
    }

    public String getId(){
        return id;
    }

    public static Tier getTierById(String id){
        for(Tier tier : Tier.values()){
            if(tier.getId().equals(id)){
                return tier;
            }
        }
        return null;
    }
}
