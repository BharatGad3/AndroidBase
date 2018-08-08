package com.base.networking.retrofit.serializer;

import androidx.annotation.NonNull;

import com.google.gson.FieldNamingPolicy;

import org.joda.time.LocalDate;

/**
 * This class binds classes with custom serializers and deserializers
 */
public class BaseGsonBuilder {

    /**
     * Provides a basic {@link com.google.gson.GsonBuilder} that already has bindings
     * to perform common serializations and deserializations with Dates using JodaTime library.
     *
     * @return Returns and instance of {@link com.google.gson.GsonBuilder} with basic bindings
     */
    @NonNull
    public static com.google.gson.GsonBuilder getBaseGsonBuilder() {
        return new com.google.gson.GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .registerTypeAdapter(LocalDate.class, new LocalDateSerializer());
    }

}