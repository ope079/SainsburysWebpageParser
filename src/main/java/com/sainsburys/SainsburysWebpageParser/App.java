package com.sainsburys.SainsburysWebpageParser;

import java.io.IOException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.fasterxml.jackson.core.JsonProcessingException;

public class App 
{
	public static void main( String[] args ){
			//Ask for url link input
		/*
		 * "https://jsainsburyplc.github.io/serverside-test/site/www.sainsburys.co.uk/webapp/wcs/stores/servlet/gb/groceries/berries-cherries-currants6039.html"
		 */
		Scanner scanner = new Scanner(System.in);
		System.out.println("Input sainsbury's groceries website address:");
		String url = scanner.nextLine();

		Scraper scraper = new Scraper(url);
		try {
			System.out.println(scraper.getJson());
		} catch (JsonProcessingException e) {
			Logger.getLogger(Scraper.class.getName()).log(Level.WARNING, null, e);
		}
	}
}
