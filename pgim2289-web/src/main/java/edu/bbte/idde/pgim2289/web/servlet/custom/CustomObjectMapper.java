package edu.bbte.idde.pgim2289.web.servlet.custom;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.text.SimpleDateFormat;


public class CustomObjectMapper {
    public static ObjectMapper loadObjectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
        return mapper;
    }
}
