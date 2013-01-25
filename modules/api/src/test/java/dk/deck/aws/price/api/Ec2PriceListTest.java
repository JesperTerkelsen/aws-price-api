/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.deck.aws.price.api;

import dk.deck.aws.price.api.model.Price;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author Jesper Terkelsen
 */
public class Ec2PriceListTest {
    
    public Ec2PriceListTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of getEc2OnDemandPrices method, of class AwsPriceList.
     */
    @Test
    public void getEc2HourRate() throws Exception {
        System.out.println("getEc2HourRate");
        Ec2PriceList instance = new Ec2PriceList();
        Price hourRate = instance.getEc2HourRate("eu-west-1", "t1.micro", null);
        System.out.println("t1.micro: " + hourRate);
        Price hourRate2 = instance.getEc2HourRate("eu-west-1", "m1.small", null);
        System.out.println("m1.small: " + hourRate2);
    }
    
    @Test
    public void getLbHourRate() throws Exception {
        Ec2PriceList instance = new Ec2PriceList();
        Price hourRate = instance.getLbHourRate("eu-west-1");
        System.out.println("Lb " + hourRate);
    }
    
    @Test
    public void getVolumeGigabyteRate() throws Exception {
        Ec2PriceList instance = new Ec2PriceList();
        Price gbRate = instance.getVolumeGigabyteRate("eu-west-1");
        System.out.println("Vol: " + gbRate);
    }
    
    @Test
    public void getSnapshotGigabyteRate() throws Exception {
        Ec2PriceList instance = new Ec2PriceList();
        Price gbRate = instance.getVolumeSnapshotGigabyteRate("eu-west-1");
        System.out.println("Snapshot: " + gbRate);
    }
}
