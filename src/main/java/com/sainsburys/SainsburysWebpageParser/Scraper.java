package com.sainsburys.SainsburysWebpageParser;

import java.io.IOException;
import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Scraper {

	String url;
	ArrayList<SearchResult> searchResults = new ArrayList<SearchResult>();

	public Scraper(String url) {
		this.url = url;
	}

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
				continue;
			} else {
				title = titleElement.text();
			}
			
			// Get price per unit
			String priceElement = element.select("p.pricePerUnit").first().text();
			if (priceElement == null) {
				unit_Price = 0.0f;
				continue;
			} else {
				priceElement = priceElement.replace("£", "");
				priceElement = priceElement.replace("/unit", "");
				float pricePerUnit = Float.parseFloat(priceElement);
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
				continue;
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
				continue;
			} else {
				description = el.text();
			}
			
			//Add the Product search results to arraylist
			searchResults.add(new SearchResult(title, kcal_Per_100g, unit_Price, description));
		}
		return searchResults;
	}

}
