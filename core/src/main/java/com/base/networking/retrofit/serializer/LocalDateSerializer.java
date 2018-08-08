package com.base.networking.retrofit.serializer;

import androidx.annotation.NonNull;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.lang.reflect.Type;

/**
 * Transforms a serialized {@link JsonElement} representing a {@link java.util.Date} into
 * a {@link LocalDate} instance. It also works the other way around, serializing a {@link LocalDate}
 * into a {@link JsonElement}.
 * <p>
 * This class is useful for sending and receiving {@link java.util.Date} instances over the network.
 */
public class LocalDateSerializer implements JsonDeserializer<LocalDate>, JsonSerializer<LocalDate> {

    private static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd";

    /**
     * Transforms a {@link JsonElement} representing a {@link java.util.Date} into a
     * {@link LocalDate} class from the JodaTime library.
     * This is useful for receiving data over the network.
     *
     * @param element a {@link JsonElement} representing a {@link java.util.Date}
     * @return returns an instance of {@link LocalDate} representing a {@link java.util.Date}
     */
    @Override
    public LocalDate deserialize(JsonElement element, Type type,
                                 JsonDeserializationContext context) throws JsonParseException {
        String date = element.toString();
        date = date.substring(1, date.length() - 1);
        DateTimeFormatter dtf = DateTimeFormat.forPattern(getDateFormat());
        return dtf.parseLocalDate(date);
    }

    /**
     * Transforms and instance of {@link LocalDate} from the JodaTime library into a
     * {@link JsonElement}. This is useful for sending the data over the network.
     *
     * @param date an instance of {@link LocalDate} to be serialized into a {@link JsonElement}
     * @return an instance of {@link JsonElement} representing a {@link LocalDate}
     */
    @Override
    public JsonElement serialize(LocalDate date, Type type, JsonSerializationContext context) {
        return new JsonParser().parse(date.toString());
    }

    /**
     * Override if needed.
     * This method returns the format of the {@link java.util.Date} class that is serialized.
     * Usually, this should match the format that is being received from the API over the network.
     * <p>
     * The default return value is the constant DEFAULT_DATE_FORMAT, available in this class.
     *
     * @return returns the format of the serialized {@link java.util.Date}
     */
    @NonNull
    protected String getDateFormat() {
        return DEFAULT_DATE_FORMAT;
    }
}