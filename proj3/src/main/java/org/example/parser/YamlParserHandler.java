package org.example.parser;

import org.example.model.Monster;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.FileInputStream;
import java.util.*;

public class YamlParserHandler implements ParserHandler {
    private ParserHandler next;

    @Override
    public void setNext(ParserHandler next) {
        this.next = next;
    }

    @Override
    public List<Monster> handle(File file) {
        if (file.getName().endsWith(".yaml") || file.getName().endsWith(".yml")) {
            try (FileInputStream fis = new FileInputStream(file)) {
                Yaml yaml = new Yaml();
                List<Map<String, Object>> data = yaml.load(fis);

                List<Monster> monsters = new ArrayList<>();
                for (Map<String, Object> map : data) {
                    Monster m = new Monster();
                    m.setName((String) map.get("name"));
                    m.setDescription((String) map.get("description"));

                    // dangerLevel может быть Integer или Long → приводим к int
                    Object danger = map.get("dangerLevel");
                    if (danger instanceof Number) {
                        m.setDangerLevel(((Number) danger).intValue());
                    }

                    m.setHabitat((String) map.get("habitat"));
                    m.setFirstMention((String) map.get("firstMention"));
                    m.setImmunities((String) map.get("immunities"));
                    m.setActivity((String) map.get("activity"));
                    m.setRecipe((String) map.get("recipe"));
                    m.setSource("YAML");
                    monsters.add(m);
                }
                return monsters;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return (next != null) ? next.handle(file) : Collections.emptyList();
    }
}
