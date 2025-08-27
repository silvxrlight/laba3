package org.example.parser;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.model.Monster;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import java.io.File;
import java.io.FileWriter;
import java.util.List;

public class Exporter {

    public static void exportToJson(List<Monster> monsters, File file) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.writerWithDefaultPrettyPrinter().writeValue(file, monsters);
            System.out.println("JSON сохранён: " + file.getAbsolutePath());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void exportToXml(List<Monster> monsters, File file) {
        try {
            JAXBContext context = JAXBContext.newInstance(MonsterListWrapper.class, Monster.class);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            MonsterListWrapper wrapper = new MonsterListWrapper();
            wrapper.setMonsters(monsters);

            marshaller.marshal(wrapper, file);
            System.out.println("XML сохранён: " + file.getAbsolutePath());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void exportToYaml(List<Monster> monsters, File file) {
        try (FileWriter writer = new FileWriter(file)) {
            DumperOptions options = new DumperOptions();
            options.setPrettyFlow(true);
            options.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);

            Yaml yaml = new Yaml(options);
            yaml.dump(monsters, writer);
            System.out.println("YAML сохранён: " + file.getAbsolutePath());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
