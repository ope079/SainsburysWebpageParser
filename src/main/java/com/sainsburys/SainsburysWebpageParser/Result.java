package com.sainsburys.SainsburysWebpageParser;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class Result {
	
	private String title;
	
	//Exclude kcal_per_100g fields with 0.00
	@JsonInclude(Include.NON_DEFAULT)
	private int kcal_per_100g;
	
	private float unit_price;
	
	//Exclude description fields with no values
	@JsonInclude(Include.NON_DEFAULT)
	private String description;
	
	ObjectMapper mapper = new ObjectMapper();
	
	
	public Result(String title, int kcal_per_100g, float unit_price, String description) {
		super();
		this.title = title;
		this.kcal_per_100g = kcal_per_100g;
		this.unit_price = unit_price;
		this.description = description;
	}
	
	//create Json object for search result class
	public ObjectNode toJSON() throws JsonProcessingException 
			{
		Result result = new Result(title, kcal_per_100g, unit_price, description);
		//map result class to json object using object mapper
		ObjectNode jString = mapper.valueToTree(result);
        return jString;
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

	public void setKcal_per_100g(int kcal_per_100g) {
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
		result = prime * result + kcal_per_100g;
		result = prime * result + ((mapper == null) ? 0 : mapper.hashCode());
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
		Result other = (Result) obj;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (kcal_per_100g != other.kcal_per_100g)
			return false;
		if (mapper == null) {
			if (other.mapper != null)
				return false;
		} else if (!mapper.equals(other.mapper))
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
		return "[title=" + title + ", kcal_per_100g=" + kcal_per_100g + ", unit_price=" + unit_price
				+ ", description=" + description + "]";
	}
	
	
	
}
