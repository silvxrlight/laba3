package org.example.parser;

import org.example.model.Monster;
import java.io.File;
import java.util.List;

public interface ParserHandler {
    void setNext(ParserHandler next);
    List<Monster> handle(File file);
}
