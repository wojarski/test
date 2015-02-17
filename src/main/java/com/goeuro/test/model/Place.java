package com.goeuro.test.model;

import org.codehaus.jackson.annotate.JsonProperty;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({ "_id", "name", "type", "latitude", "longitude"})
public final class Place {
	
	@JsonProperty(value = "_id")
	@com.fasterxml.jackson.annotation.JsonProperty(value = "_id")
	private long id;
	
	private String name;
	
	private String type;
	
	@JsonProperty(value = "geo_position")
	@JsonIgnore
	private GeoPosition geoPosition;

	@com.fasterxml.jackson.annotation.JsonProperty(value = "latitude")
	public double getLatitude() {
		return getGeoPosition().getLatitude();
	}
	
	@com.fasterxml.jackson.annotation.JsonProperty(value = "longitude")
	public double getLongitude() {
		return getGeoPosition().getLongitude();
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public GeoPosition getGeoPosition() {
		return geoPosition;
	}

	public void setGeoPosition(GeoPosition geoPosition) {
		this.geoPosition = geoPosition;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}
