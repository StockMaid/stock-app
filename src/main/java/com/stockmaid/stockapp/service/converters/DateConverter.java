package com.stockmaid.stockapp.service.converters;

import com.opencsv.bean.AbstractBeanField;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateConverter extends AbstractBeanField<LocalDate, String> {

    @Override
    protected Object convert(String value) {
        return LocalDate.parse(value, DateTimeFormatter.ISO_DATE_TIME);
    }
}
