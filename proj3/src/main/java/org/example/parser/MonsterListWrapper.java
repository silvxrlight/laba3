package org.example.parser;

import org.example.model.Monster;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement(name = "monsters")
public class MonsterListWrapper {

    private List<Monster> monsters = new ArrayList<>();

    public MonsterListWrapper() {}

    public MonsterListWrapper(List<Monster> monsters) {
        this.monsters = monsters;
    }

    @XmlElement(name = "monster")
    public List<Monster> getMonsters() {
        return monsters;
    }

    public void setMonsters(List<Monster> monsters) {
        this.monsters = monsters;
    }
}
