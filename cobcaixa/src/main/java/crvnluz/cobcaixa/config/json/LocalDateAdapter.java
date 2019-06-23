package crvnluz.cobcaixa.config.json;

import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public class LocalDateAdapter implements JsonSerializer<LocalDate> {

	@Override
	public JsonElement serialize(LocalDate localDate, Type arg1, JsonSerializationContext context) {
		return new JsonPrimitive(localDate.format(DateTimeFormatter.ISO_LOCAL_DATE));
	}

}
