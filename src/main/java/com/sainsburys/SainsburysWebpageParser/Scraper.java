package com.sainsburys.SainsburysWebpageParser;

import java.io.IOException;
import java.util.ArrayList;

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
	ArrayList<SearchResult> searchResults = new ArrayList<SearchResult>();

	public Scraper(String url) {
		this.url = url;
	}
	
	//class for getting the product information into an array
	public ArrayList<SearchResult> getProduct(String theUrl) throws IOException {

		String title = "";
		float kcal_Per_100g = 0.0f;
		float unit_Price = 0.0f;
		String description = "";

		Document page = Jsoup.connect(theUrl).userAgent("Jsoup Scraper").get();

		Elements elements = page.select("ul.productLister").first().getElementsByTag("li");

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
			Element priceElement = element.select("p.pricePerUnit").first();
			if (priceElement == null) {
				unit_Price = 0.0f;
			} else {
				String price = priceElement.text();
				price = price.replace("£", "");
				price = price.replace("/unit", "");
				float pricePerUnit = Float.parseFloat(price);
				unit_Price = pricePerUnit;
			}

			// Get the link for itemName and scrape
			theUrl = element.select("div.productNameAndPromotions").first().getElementsByTag("a").first()
					.attr("abs:href").toString();
			Document thePage = Jsoup.connect(theUrl).userAgent("Jsoup Scraper").get();

			// Get the kCal per 100g
			Element el = thePage.select("tr.tableRow0").first();
			if (el == null) {
				kcal_Per_100g = 0.0f;
			} else {
				Element kCalElement = el.getElementsByTag("td").first();
				String kCalStr = el.text();
				kCalStr = kCalStr.replaceAll("\\s(.*)", "");
				kCalStr = kCalStr.replace("kcal", "");
				float kcalPer100 = Float.parseFloat(kCalStr);
				kcal_Per_100g = kcalPer100;
			}

			// Get the product description
			el = thePage.select("div.productText").first().getElementsByTag("p").first();
			if (el == null) {
				description = " ";
			} else {
				description = el.text();
			}

			// Add the Product search results to arraylist
			searchResults.add(new SearchResult(title, kcal_Per_100g, unit_Price, description));
		}
		return searchResults;
	}

	
	//Class for putting all products in JSon file
	public String getJson() throws JsonGenerationException, JsonMappingException, IOException {
		// create variables for vat and gross
		float totalPrice = 0.0f;
		float vat = 0.0f;
		float gross = 0.0f;
		// Create JSON array
		ObjectMapper mapper = new ObjectMapper();
		ObjectNode json = mapper.createObjectNode();
		ArrayNode arrayNode = mapper.createArrayNode();

		ArrayList<SearchResult> results = getProduct(url);
		
		// Get total price and add each product info to JSon array
		for (SearchResult result : results) {
			totalPrice += result.getUnit_price();
			arrayNode.add(result.toJSON());
			json.put("results", arrayNode);
		}
			
		
		vat = (float) (gross * 0.2);
		
		// Add gross and vat to JSon array
		Total total = new Total(gross, vat);
		ArrayNode totalArray = mapper.createArrayNode();
		totalArray.add(total.toJson());

		json.put("total", totalArray);

		return json.toPrettyString();

	}

}
