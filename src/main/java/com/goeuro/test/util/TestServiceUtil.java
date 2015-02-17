package com.goeuro.test.util;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import com.goeuro.test.model.Place;

/**
 * The util class
 *
 */
public class TestServiceUtil {
	static final String SEPARATOR = "\n";
	static final String FILE_NAME = "places.csv";
    
	
	/**
	 * The method writes to a CSV file Places objects in the following format:
	 * 	_id, name, type, latitude, longitude
	 * 
	 * @param places a list of Place objects
	 * @throws JsonGenerationException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	public static void printCVS(List<Place> places) throws JsonGenerationException, JsonMappingException, IOException {
		CsvMapper mapper = new CsvMapper();
		CsvSchema schema = mapper.schemaFor(Place.class);
		
		ObjectWriter writer = mapper.writer(schema);
		System.out.println("File " + FILE_NAME + " has been successfuly created.");
		writer.writeValue(new File(FILE_NAME), places);
	}
	
	/**
	 * The method deserializes Place objects from the JSON string.
	 * 
	 * @param placesJson json array
	 * @return list of Place objects
	 * @throws JsonProcessingException
	 * @throws IOException
	 */
	public static List<Place> json2Places(String placesJson) throws JsonProcessingException, IOException {
		ObjectMapper mapper = new ObjectMapper();
        // omit uninteresting fields
        mapper.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        
        JsonNode arrNode = mapper.readTree(placesJson);
        
        if(arrNode.isArray()) {
        	List<Place> places = new ArrayList<Place>(arrNode.size());
        	for (final JsonNode objNode : arrNode) {
        		Place place = mapper.readValue(objNode, Place.class);
            	places.add(place);
        	}            	
        	return places;
        } else {
        	System.err.println("Service response is not an array.");
        	return new ArrayList<Place>(0);
        }
	}
}
