package org.example.parser;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.model.Monster;

import java.io.File;
import java.util.Collections;
import java.util.List;

public class JsonParserHandler implements ParserHandler {
    private ParserHandler next;

    @Override
    public void setNext(ParserHandler next) { this.next = next; }

    @Override
    public List<Monster> handle(File file) {
        if (file.getName().endsWith(".json")) {
            try {
                ObjectMapper mapper = new ObjectMapper();
                List<Monster> monsters =
                        mapper.readValue(file, new TypeReference<List<Monster>>() {});
                monsters.forEach(m -> m.setSource("JSON"));
                return monsters;
            } catch (Exception e) { e.printStackTrace(); }
        }
        return (next != null) ? next.handle(file) : Collections.emptyList();
    }
}
