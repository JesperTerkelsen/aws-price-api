/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.deck.aws.price.api.model.ec2;

/**
 * Rds types are defined here.
 * 
 * http://docs.aws.amazon.com/AmazonRDS/latest/CommandLineReference/CLIReference-cmd-CreateDBInstance.html
 * 
 * @author Jesper Terkelsen
 */
public class RdsTypeClassMapping {
    public static String getInstanceNameFromPriceNames(String typeGroup, String size) {
        if (typeGroup.equals("dbInstClass") || typeGroup.equals("multiAZDBInstClass")){
            if (size.equals("uDBInst")){
                return "db.t1.micro";
            }
            else if (size.equals("smDBInst")){
                return "db.m1.small";
            }
            else if (size.equals("medDBInst")){
                return "db.m1.medium";
            }
            else if (size.equals("lgDBInst")){
                return "db.m1.large";
            }
            else if (size.equals("xlDBInst")){
                return "db.m1.xlarge";
            }
        }
        else if (typeGroup.equals("hiMemDBInstClass") || typeGroup.equals("multiAZHiMemInstClass")){
            if (size.equals("xlDBInst")){
                return "db.m2.xlarge";
            }
            else if (size.equals("xxlDBInst")){
                return "db.m2.2xlarge";
            }
            else if (size.equals("xxxxDBInst")){
                return "db.m2.4xlarge";
            }
        }
        return null;
    } 
}
