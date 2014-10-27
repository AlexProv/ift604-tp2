package common;

import com.google.gson.*;

public class SerializateurJson {

	public static String objectToJson(Object voObj) {
		Gson gson = new Gson();
		gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation()
				.create();
		String s = gson.toJson(voObj);

		return s;

	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Object jsonToObject(String jsonObjStr, Class type) {
		Gson gson = new Gson();
		gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation()
				.create();
		Object o = gson.fromJson(jsonObjStr, type);
		return o;
	}

}
