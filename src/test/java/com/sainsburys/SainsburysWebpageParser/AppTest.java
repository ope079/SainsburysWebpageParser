package com.sainsburys.SainsburysWebpageParser;

import org.junit.After;
import org.junit.Before;

import org.junit.Test;

import com.fasterxml.jackson.core.JsonProcessingException;

import static org.junit.Assert.*;

import java.util.ArrayList;

public class AppTest 
{
	Scraper scraper;
    String url;
    
    public AppTest( )
    {
    	
    }


    @Before
    public void setUp() {
        
        {
            url = "https://jsainsburyplc.github.io/serverside-test/site/www.sainsburys.co.uk/webapp/wcs/stores/servlet/gb/groceries/berries-cherries-currants6039.html";
            scraper = new Scraper(url);
        }
    }

    @After
    public void tearDown() {
        scraper = null;
    }

    @Test
    public void testGetJsonDoesNotReturnEmptyJson() throws JsonProcessingException {
        String json = scraper.getJson();
        assertTrue(!json.isEmpty());
    }
    
    @Test
    public void testGetJsonReturnsResultAndTotal() throws JsonProcessingException {
    	String json = scraper.getJson().toString();
    	assertTrue(json.contains("results") && json.contains("total"));
    }
    
    @Test
    public void testGetJsonReturnsProduct() throws JsonProcessingException{
        String json = scraper.getJson().toString();
        assertTrue(json.contains("Sainsbury's Mixed Berry Twin Pack 200g"));
    }
    
    @Test
    public void testGetProductNotEmpty(){
    	ArrayList<Result> results = scraper.getProduct(url);
    	assertTrue(!results.isEmpty());
    	assertTrue(!results.get(1).getDescription().isEmpty());
    	assertTrue(!results.get(2).getTitle().isEmpty());
    	assertTrue(results.get(3).getKcal_per_100g() > 0.0f);
    	assertTrue(results.get(4).getUnit_price() > 0.0f);
    }

}
