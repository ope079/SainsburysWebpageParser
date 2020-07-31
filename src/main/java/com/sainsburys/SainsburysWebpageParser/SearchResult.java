package com.sainsburys.SainsburysWebpageParser;

import java.io.IOException;
import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.fasterxml.jackson.databind.node.ObjectNode;

public class SearchResult {
	
	private String title;
	private float kcal_per_100g;
	private float unit_price;
	private String description;
	
	public SearchResult(String title, float kcal_per_100g, float unit_price, String description) {
		super();
		this.title = title;
		this.kcal_per_100g = kcal_per_100g;
		this.unit_price = unit_price;
		this.description = description;
	}
	
	public ObjectNode toJSON() {
		ObjectNode jObj = new ObjectNode(null);
		jObj.put("title", title);
		jObj.put("kcal_per_100g", kcal_per_100g);
		jObj.put("unit_price", unit_price);
		jObj.put("description", description);
        
        return jObj;
    }

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public float getKcal_per_100g() {
		return kcal_per_100g;
	}

	public void setKcal_per_100g(float kcal_per_100g) {
		this.kcal_per_100g = kcal_per_100g;
	}

	public float getUnit_price() {
		return unit_price;
	}

	public void setUnit_price(float unit_price) {
		this.unit_price = unit_price;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + Float.floatToIntBits(kcal_per_100g);
		result = prime * result + ((title == null) ? 0 : title.hashCode());
		result = prime * result + Float.floatToIntBits(unit_price);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SearchResult other = (SearchResult) obj;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (Float.floatToIntBits(kcal_per_100g) != Float.floatToIntBits(other.kcal_per_100g))
			return false;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		if (Float.floatToIntBits(unit_price) != Float.floatToIntBits(other.unit_price))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "SearchResult [title=" + title + ", kcal_per_100g=" + kcal_per_100g + ", unit_price=" + unit_price
				+ ", description=" + description + "]";
	}
	
	
	
}
