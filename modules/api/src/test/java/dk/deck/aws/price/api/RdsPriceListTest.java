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
import java.io.IOException;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author Jesper Terkelsen
 */
public class RdsPriceListTest {
    
    public RdsPriceListTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    private RdsPriceList instance;
    
    @Before
    public void setUp() {
        instance = new RdsPriceList();
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of getRdsHourRate method, of class RdsPriceList.
     */
    @Test
    public void testGetRdsHourRate() throws Exception {
        System.out.println("getRdsHourRate");
        getRdsHourRate("eu-west-1", "mysql", "db.t1.micro", false);
        getRdsHourRate("eu-west-1", "mysql", "db.t1.micro", true);
        getRdsHourRate("eu-west-1", "mysql", "db.m1.small", false);
        getRdsHourRate("eu-west-1", "mysql", "db.m1.small", true);
        getRdsHourRate("eu-west-1", "mysql", "db.m1.medium", false);
        getRdsHourRate("eu-west-1", "mysql", "db.m1.medium", true);
        getRdsHourRate("eu-west-1", "mysql", "db.m1.large", false);
        getRdsHourRate("eu-west-1", "mysql", "db.m1.large", true);
        getRdsHourRate("eu-west-1", "mysql", "db.m1.xlarge", false);
        getRdsHourRate("eu-west-1", "mysql", "db.m1.xlarge", true);
        getRdsHourRate("eu-west-1", "mysql", "db.m2.xlarge", false);
        getRdsHourRate("eu-west-1", "mysql", "db.m2.xlarge", true);
        getRdsHourRate("eu-west-1", "mysql", "db.m2.2xlarge", false);
        getRdsHourRate("eu-west-1", "mysql", "db.m2.2xlarge", true);
        getRdsHourRate("eu-west-1", "mysql", "db.m2.4xlarge", false);
        getRdsHourRate("eu-west-1", "mysql", "db.m2.4xlarge", true);
    }
    
    private Price getRdsHourRate(String regionName, String dbEngine, String instanceTypeClass, boolean multiAz) throws IOException{
        Price result = instance.getRdsHourRate(regionName, dbEngine, instanceTypeClass, multiAz);
        System.out.println(regionName + " " + dbEngine + " " + instanceTypeClass + " " + multiAz + ": " + result);
        return result;
    }
}
