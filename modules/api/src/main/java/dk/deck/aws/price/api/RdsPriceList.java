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
package dk.deck.aws.price.api;

import dk.deck.aws.price.api.model.Price;
import dk.deck.aws.price.api.model.ec2.RdsTypeClassMapping;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Jesper Terkelsen
 */
public class RdsPriceList extends AbstractPriceList {
    // RDS 

    private static final String rdsMysqlMultiAz = "http://aws.amazon.com/rds/pricing/mysql/pricing-multiAZ-deployments.json";
    private static final String rdsMysqlStandard = "http://aws.amazon.com/rds/pricing/mysql/pricing-standard-deployments.json";
    private Map<String, Object> mysqlStandard;
    private Map<String, Object> mysqlMultiAz;

    public RdsPriceList() {
    }

    public Price getRdsHourRate(String regionName, String dbEngine, String instanceTypeClass, boolean multiAz) throws IOException {
        if (dbEngine.equals("mysql")) {
            getMysqlPrices();
            Map<String, Object> region;
            if (multiAz) {
                region = getRegion(regionName, mysqlMultiAz);
            } else {
                region = getRegion(regionName, mysqlStandard);
            }
            if (region != null) {
                // System.out.println(region);
                Map<String, Object> size = getRdsInstanceSize(region, instanceTypeClass);
                if (size != null){
                    // System.out.println(size);
                    Map<String,String> prices = (Map<String,String>) size.get("prices");
                    String price = prices.get("USD");
                    return Price.createUsdHourly(new BigDecimal(price));
                }
            }
        }

        return null;
    }

    private Map<String, Object> getRdsInstanceSize(Map<String, Object> region, String instanceType) {
        if (region != null) {
            List<Map<String, Object>> instanceTypes = getSubList(region,"types");
            for (Map<String, Object> map : instanceTypes) {
                String typeGroup = (String) map.get("name");
                List<Map<String, Object>> sizes = getSubList(map,"tiers");
                for (Map<String, Object> size : sizes) {
                    String sizeName = (String) size.get("name");
                    // System.out.println(typeGroup + " "+ sizeName);
                    String instanceTypeValue = RdsTypeClassMapping.getInstanceNameFromPriceNames(typeGroup, sizeName);
                    if (instanceTypeValue != null && instanceTypeValue.equals(instanceType)) {
                        return size;
                    }
                }
            }
        }
        return null;
    }

    private void getMysqlPrices() throws IOException {
        if (mysqlStandard == null) {
            String result = getRequestAsString(rdsMysqlStandard);
            mysqlStandard = mapper.readValue(result, Map.class);
        }
        if (mysqlMultiAz == null) {
            String result = getRequestAsString(rdsMysqlMultiAz);
            mysqlMultiAz = mapper.readValue(result, Map.class);
        }
    }
}
