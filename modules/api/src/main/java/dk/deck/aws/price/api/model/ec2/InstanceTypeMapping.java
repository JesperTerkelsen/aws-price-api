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
 ** Instance types are defined here
 * 
 * http://docs.amazonwebservices.com/AWSEC2/latest/UserGuide/instance-types.html
 * 
 * @author Jesper Terkelsen
 */
public class InstanceTypeMapping {

    public static String getInstanceNameFromPriceNames(String typeGroup, String size) {
        if (typeGroup.equals("stdODI")) {
            if (size.equals("sm")) {
                return "m1.small";
            } else if (size.equals("med")) {
                return "m1.medium";
            } else if (size.equals("lg")) {
                return "m1.large";
            } else if (size.equals("xl")) {
                return "m1.xlarge";
            }
        } else if (typeGroup.equals("secgenstdODI")) {
            if (size.equals("xl")) {
                return "m3.xlarge";
            } else if (size.equals("xxl")) {
                return "m3.2xlarge";
            }
        } else if (typeGroup.equals("uODI")) {
            if (size.equals("u")) {
                return "t1.micro";
            }
        } else if (typeGroup.equals("hiMemODI")) {
            if (size.equals("xl")) {
                return "m2.xlarge";
            }
            if (size.equals("xxl")) {
                return "m2.2xlarge";
            }
            if (size.equals("xxxxl")) {
                return "m2.4xlarge";
            }             
        } else if (typeGroup.equals("hiCPUODI")) {
            if (size.equals("med")) {
                return "c1.medium";
            }
            if (size.equals("xl")) {
                return "c1.xlarge";
            }
        } else if (typeGroup.equals("clusterComputeI")) {
            if (size.equals("xxxxl")) {
                return "cc1.4xlarge";
            }
            else if (size.equals("xxxxxxxxl")) {
                return "xxxxxxxxl";
            }
        } else if (typeGroup.equals("clusterGPUI")) {
            if (size.equals("xxxxl")) {
                return "cg1.4xlarge";
            }
        } else if (typeGroup.equals("hiIoODI")) {
            if (size.equals("xxxxl")) {
                return "hi1.4xlarge";
            }
        } else if (typeGroup.equals("hiStoreODI")) {
            if (size.equals("xxxxxxxxl")) {
                return "hs1.8xlarge";
            }
        }
        return null;
    }
}
