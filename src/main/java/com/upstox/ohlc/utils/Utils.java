package com.upstox.ohlc.utils;

import java.io.IOException;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * This class is used to json and dejson the object
 * @author Bipin Anghan
 *
 */
public class Utils {

	public static <T> T toJsonObject(String jsonSource, Class<T> cls) {
		try
		{
			ObjectMapper om = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			return om.readValue(jsonSource, cls);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static String toJsonString(Object obj) {
		try
		{
			ObjectMapper om = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			return om.writeValueAsString(obj);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		return "";
	}
}
