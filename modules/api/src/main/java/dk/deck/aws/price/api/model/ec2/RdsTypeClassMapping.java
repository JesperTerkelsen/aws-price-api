/*
 * Copyright 2013 Jesper Terkelsen.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 * 
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
