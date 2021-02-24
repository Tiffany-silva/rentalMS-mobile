package com.lanka.rentalmangment.utils;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import lombok.SneakyThrows;

public class DateConverterRentalDate extends TypeAdapter<Date> {

    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");

    @Override
    public void write(JsonWriter out, Date date) throws IOException {
        if (date == null) {
            out.nullValue();
        } else {
            simpleDateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
            out.value(simpleDateFormat.format(date));
        }
    }

    @SneakyThrows
    @Override
    public Date read(JsonReader in) throws IOException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("E, MMM d yyyy");
//        LocalDate date = format.parse(in.nextName());
//        Date Date = Date.parse(in.nextName(), formatter);
        Date d=simpleDateFormat.parse(in.nextName());
        return  d;
//       LocalDate d= simpleDateFormat.parse(in.nextName()).toInstant()
//                .atZone(ZoneId.systemDefault())
//                .toLocalDate();;
    }
}