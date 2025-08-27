package org.example.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "monster")
public class Monster {
    private String name;
    private String description;
    private int dangerLevel;
    private String habitat;
    private String firstMention;
    private String immunities;
    private String activity;
    private String recipe;
    private String source;

    public Monster() {} // обязательно нужен пустой конструктор для JAXB

    @XmlElement
    public String getName() { return name; }
    public void setName(String n) { this.name = n; }

    @XmlElement
    public String getDescription() { return description; }
    public void setDescription(String d) { this.description = d; }

    @XmlElement
    public int getDangerLevel() { return dangerLevel; }
    public void setDangerLevel(int dl) { this.dangerLevel = dl; }

    @XmlElement
    public String getHabitat() { return habitat; }
    public void setHabitat(String h) { this.habitat = h; }

    @XmlElement
    public String getFirstMention() { return firstMention; }
    public void setFirstMention(String f) { this.firstMention = f; }

    @XmlElement
    public String getImmunities() { return immunities; }
    public void setImmunities(String i) { this.immunities = i; }

    @XmlElement
    public String getActivity() { return activity; }
    public void setActivity(String a) { this.activity = a; }

    @XmlElement
    public String getRecipe() { return recipe; }
    public void setRecipe(String r) { this.recipe = r; }

    @XmlElement
    public String getSource() { return source; }
    public void setSource(String s) { this.source = s; }

    @Override
    public String toString() {
        return name + " (" + source + ")";
    }
}
