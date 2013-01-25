/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
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
