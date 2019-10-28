package com.diesgut.medical.app.misc;

import java.io.IOException;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.annotation.JsonInclude;

public class JsonHelper {

	public static String toJson(Object object) {
		try {
			ObjectMapper mapper = new ObjectMapper();
			mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
			return mapper.writeValueAsString(object);

		} catch (Exception e) {
			e.printStackTrace();
			// logger.debug("Error al Serializar/Marshall");
			return "";
		}

	}

	public static Object fromJson(String json, Class clazz) {
		try {

			ObjectMapper mapper = new ObjectMapper();
			mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

			Object object = (Object) mapper.readValue(json, clazz);
			return object;

		} catch (IOException ex) {
			ex.printStackTrace();
			// logger.debug("Error al Deserializar/Unmarshall {}",
			// ex.getLocalizedMessage());
			return null;
		}

	}
}
