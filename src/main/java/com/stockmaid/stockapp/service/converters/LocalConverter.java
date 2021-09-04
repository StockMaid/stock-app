package com.stockmaid.stockapp.service.converters;

import com.opencsv.bean.AbstractBeanField;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class LocalConverter extends AbstractBeanField<LocalDate, String> {

    @Override
    protected Object convert(String value) {
        if (value.equals("")) {
            value = "2030-01-02";
        }
        return LocalDate.parse(value, DateTimeFormatter.ISO_LOCAL_DATE);
    }
}
