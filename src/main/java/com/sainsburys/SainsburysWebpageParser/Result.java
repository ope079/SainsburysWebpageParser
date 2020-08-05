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

	public int getKcal_per_100g() {
		return kcal_per_100g;
	}

	public float getUnit_price() {
		return unit_price;
	}

	
	public String getDescription() {
		return description;
	}

	
	
	
}
