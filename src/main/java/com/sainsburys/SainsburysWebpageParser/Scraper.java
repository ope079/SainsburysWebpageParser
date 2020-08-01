package com.sainsburys.SainsburysWebpageParser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;


public class Scraper {

	String url;
	ArrayList<Result> searchResults = new ArrayList<Result>();

	public Scraper(String url) {
		this.url = url;
	}

	// class for getting the product information into an array
	public ArrayList<Result> getProduct(String theUrl) {

		String title = "";
		float kcal_Per_100g = 0.0f;
		float unit_Price = 0.0f;
		String description = "";
		boolean hasTable = false;

		try {
			Document page = Jsoup.connect(theUrl).userAgent("Jsoup Scraper").get();

			Elements elements = page.select("ul.productLister").first().select("li.gridItem");

			for (Element element : elements) {
				// Get the product title
				Element titleElement = element.getElementsByTag("h3").first();
				// if product title is empty continue
				if (titleElement == null) {
					title = "";
				} else {
					title = titleElement.text();
				}

				// Get price per unit
				String price = element.select("p.pricePerUnit").text();
				if (price == null) {
					unit_Price = 0.0f;
				} else {
					
					price = price.replace("£", "");
					price = price.replace("/unit", "");
					price = price.replaceAll("\\s(.*)", "");
					float pricePerUnit = Float.parseFloat(price);
					unit_Price = pricePerUnit;
					
				}

				// Get the link for itemName and scrape
				String innerUrl = element.select("div.productNameAndPromotions").first().getElementsByTag("a").first()
						.attr("abs:href").toString();
				Document thePage = Jsoup.connect(innerUrl).userAgent("Jsoup Scraper").get();

				// Get the kCal per 100g
				Elements el = thePage.select("div.tabs");
				Elements pageBodyChildren = new Elements();
				// Check if subpage element contains a table element
				Elements hasChild = el.select("div.tableWrapper");
				
					if (hasChild.size()> 0){
					Element kCalElement = el.first().getElementsByTag("tbody").first().getElementsByTag("tr")
						.first().nextElementSibling().getElementsByTag("td").first();
					String kCalStr = kCalElement.text();
					kCalStr = kCalStr.replaceAll("\\s(.*)", "");
					kCalStr = kCalStr.replace("kcal", "");
					float kcalPer100 = Float.parseFloat(kCalStr);
					kcal_Per_100g = kcalPer100;	}	
					else{
						kcal_Per_100g  = 0;
					}
				
				// Get the product description
				Element descEl = thePage.select("div.productText").first().getElementsByTag("p").first();
				if (descEl == null) {
					description = "";
				} else {
					description = descEl.text();
			}

				// Add the Product search results to arraylist
				searchResults.add(new Result(title, kcal_Per_100g, unit_Price, description));
			}
			
		} catch (IOException ex) {
			Logger.getLogger(Scraper.class.getName()).log(Level.SEVERE, null, ex);
		}
		return searchResults;
		
	}

	
	public String getJson(){
		// create variables for vat and gross
		float vat = 0.0f;
		float gross = 0.0f;
		// create JSON object mapper and JSON arrayNode
		ObjectMapper mapper = new ObjectMapper();
		ObjectNode json = mapper.createObjectNode();
		ArrayNode arrayNode = mapper.createArrayNode();
		//Create arrayNode to hold gross and vat
		ArrayNode totalArray = mapper.createArrayNode();
		
		//extract gross from product results and add to JSON ArrayNode
		ArrayList<Result> results = getProduct(url);
		for (Result result : results) {
			gross += result.getUnit_price();
			arrayNode.add(result.toJSON());
			json.put("results", arrayNode);
		}
		;
		
		//Add gross and vat to total arrayNode. Add to final JSon ArrayNode
		vat = (float) (gross * 0.2);
		Total total = new Total(gross, vat);
		totalArray.add(total.toJson());
		json.put("total", totalArray);

		return json.toPrettyString();

	}

}
