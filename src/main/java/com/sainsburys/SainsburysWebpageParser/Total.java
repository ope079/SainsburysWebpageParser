package com.sainsburys.SainsburysWebpageParser;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class Total {
	
	float gross;
	float vat;
	
	ObjectMapper mapper = new ObjectMapper();
	
	public Total(float gross, float vat) {
		super();
		this.gross = gross;
		this.vat = vat;
	}
	
	//create JSON array for gross and vat
	public ObjectNode toJson(){
		ObjectNode jObj = mapper.createObjectNode();
		jObj.put("gross", gross);
		jObj.put("vat", vat);
		return jObj;
	}

	public float getGross() {
		return gross;
	}

	public void setGross(float gross) {
		this.gross = gross;
	}

	public float getVat() {
		return vat;
	}

	public void setVat(float vat) {
		this.vat = vat;
	}
	
	
}
