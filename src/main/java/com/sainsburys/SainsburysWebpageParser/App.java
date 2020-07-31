package com.sainsburys.SainsburysWebpageParser;

import java.io.IOException;


/**
 * Hello world!
 *
 */
public class App 
{
	public static void main( String[] args ) throws IOException{
		String url = "https://jsainsburyplc.github.io/serverside-test/site/www.sainsburys.co.uk/webapp/wcs/stores/servlet/gb/groceries/berries-cherries-currants6039.html";
		
		Scraper scraper = new Scraper(url);
		System.out.println(scraper.getJson());
	}
}
