package com.lanka.rentalmangment.utils;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.lang.reflect.Type;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


//public class DateConverter implements JsonDeserializer<LocalDate>, JsonSerializer<LocalDate> {
//    @Override
//    public LocalDate deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
//            throws JsonParseException {
//
//        return OffsetDateTime.parse(json.getAsString(), DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS[xxx][xx][X]"))
//                .atZoneSameInstant(ZoneOffset.systemDefault())
//                .toLocalDate();
////        return LocalDateTime.parse(json.getAsString(),
////                DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"));
//    }
//
//
//    @Override
//    public JsonElement serialize(LocalDate src, Type typeOfSrc, JsonSerializationContext context) {
//        JsonElement element = new JsonPrimitive(src.format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSZ")));
//
//        return element;
//    }
//}
public class DateConverter extends TypeAdapter<LocalDate> {


    @Override
    public void write(final JsonWriter jsonWriter, final LocalDate localDate ) throws IOException {
        jsonWriter.value(localDate.toString());
    }

    @Override
    public LocalDate read( final JsonReader jsonReader ) throws IOException {
//        return LocalDate.parse(jsonReader.nextString());

                return OffsetDateTime.parse(jsonReader.nextString(), DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSZZZ"))
                        .atZoneSameInstant(ZoneOffset.systemDefault())
                        .toLocalDate();
        }
}
