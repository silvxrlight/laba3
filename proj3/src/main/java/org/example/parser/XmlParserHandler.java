package org.example.parser;

import org.example.model.Monster;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.util.Collections;
import java.util.List;

public class XmlParserHandler implements ParserHandler {
    private ParserHandler next;

    @Override
    public void setNext(ParserHandler next) {
        this.next = next;
    }

    @Override
    public List<Monster> handle(File file) {
        if (file.getName().endsWith(".xml")) {
            try {
                JAXBContext context = JAXBContext.newInstance(MonsterListWrapper.class, Monster.class);
                Unmarshaller unmarshaller = context.createUnmarshaller();
                MonsterListWrapper wrapper = (MonsterListWrapper) unmarshaller.unmarshal(file);

                if (wrapper != null && wrapper.getMonsters() != null) {
                    wrapper.getMonsters().forEach(m -> m.setSource("XML"));
                    return wrapper.getMonsters();
                }
            } catch (Exception e) {
                System.err.println("Ошибка чтения XML: " + e.getMessage());
                e.printStackTrace();
            }
        }
        return (next != null) ? next.handle(file) : Collections.emptyList();
    }
}
