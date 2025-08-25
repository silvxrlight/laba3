package org.example.ui;

import org.example.model.Monster;

import javax.swing.*;
import java.awt.*;

public class MonsterDialog extends JDialog {
    private JTextField habitatField;
    private JSpinner dangerSpinner;

    public MonsterDialog(Frame owner, Monster monster) {
        super(owner, "Инфо: " + monster.getName(), true);
        setSize(400, 400);
        setLayout(new GridLayout(0, 2, 5, 5));

        add(new JLabel("Имя:"));
        add(new JLabel(monster.getName()));

        add(new JLabel("Описание:"));
        add(new JLabel("<html><p style='width:200px;'>" + monster.getDescription() + "</p></html>"));

        add(new JLabel("Опасность:"));
        dangerSpinner = new JSpinner(new SpinnerNumberModel(monster.getDangerLevel(), 0, 10, 1));
        add(dangerSpinner);

        add(new JLabel("Среда обитания:"));
        habitatField = new JTextField(monster.getHabitat());
        add(habitatField);

        add(new JLabel("Первое упоминание:"));
        add(new JLabel(monster.getFirstMention()));

        add(new JLabel("Иммунитеты:"));
        add(new JLabel(monster.getImmunities()));

        add(new JLabel("Активность:"));
        add(new JLabel(monster.getActivity()));

        add(new JLabel("Рецепт:"));
        add(new JLabel("<html><p style='width:200px;'>" + monster.getRecipe() + "</p></html>"));

        // Кнопка "Сохранить"
        JButton save = new JButton("Сохранить изменения");
        save.addActionListener(e -> {
            monster.setHabitat(habitatField.getText());
            monster.setDangerLevel((Integer) dangerSpinner.getValue());
            dispose();
        });

        // пустая ячейка для выравнивания
        add(new JLabel());
        add(save);
    }
}
