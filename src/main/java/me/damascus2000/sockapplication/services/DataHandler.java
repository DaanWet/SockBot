package me.damascus2000.sockapplication.services;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import net.dv8tion.jda.api.entities.Guild;
import org.springframework.boot.json.JsonParserFactory;


import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class DataHandler {

    protected String path;
    protected ObjectMapper mapper;

    DataHandler(){
        mapper = JsonMapper.builder().findAndAddModules()
                .build();
        mapper.enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY);
    }

    void save(Object obj) throws IOException{
        mapper.writeValue(new File(path), obj);
    }

}
