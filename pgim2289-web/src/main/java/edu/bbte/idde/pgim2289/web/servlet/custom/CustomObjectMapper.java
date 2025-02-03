package edu.bbte.idde.pgim2289.web.servlet.custom;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.Setter;

import java.text.SimpleDateFormat;
import java.util.Locale;

@Setter
public class CustomObjectMapper {
    public static ObjectMapper createConfiguredObjectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();

        objectMapper.registerModule(new JavaTimeModule());

        objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH));

        return objectMapper;
    }
}
