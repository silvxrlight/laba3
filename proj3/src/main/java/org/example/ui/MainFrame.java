package org.example.ui;

import org.example.model.Monster;
import org.example.parser.*;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import java.awt.*;
import java.io.File;
import java.util.*;
import java.util.List;

public class MainFrame extends JFrame {
    private JTree tree;
    private DefaultMutableTreeNode root;

    // Храним именно File → List<Monster>, чтобы сохранять обратно туда же
    private Map<File, List<Monster>> storage = new HashMap<>();

    public MainFrame() {
        setTitle("Bestiarum Manager");
        setSize(600, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        root = new DefaultMutableTreeNode("Bestiarum");
        tree = new JTree(root);
        add(new JScrollPane(tree), BorderLayout.CENTER);

        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("Файл");

        JMenuItem loadItem = new JMenuItem("Загрузить...");
        loadItem.addActionListener(e -> loadFiles());

        JMenuItem saveItem = new JMenuItem("Сохранить всё");
        saveItem.addActionListener(e -> saveAll());

        fileMenu.add(loadItem);
        fileMenu.add(saveItem);
        menuBar.add(fileMenu);
        setJMenuBar(menuBar);

        // обработка клика по узлу дерева
        tree.addTreeSelectionListener(e -> {
            DefaultMutableTreeNode node = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
            if (node == null) return;

            Object obj = node.getUserObject();
            if (obj instanceof Monster) {
                Monster m = (Monster) obj;
                new MonsterDialog(this, m).setVisible(true);
                // обновляем отображение узла
                ((DefaultTreeModel) tree.getModel()).nodeChanged(node);
            }
        });
    }

    private void loadFiles() {
        JFileChooser chooser = new JFileChooser(System.getProperty("user.home"));
        chooser.setMultiSelectionEnabled(true);
        if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            File[] files = chooser.getSelectedFiles();

            // создаём цепочку обработчиков
            ParserHandler json = new JsonParserHandler();
            ParserHandler xml = new XmlParserHandler();
            ParserHandler yaml = new YamlParserHandler();

            json.setNext(xml);
            xml.setNext(yaml);

            for (File f : files) {
                List<Monster> monsters = json.handle(f);
                if (!monsters.isEmpty()) {
                    storage.put(f, monsters);

                    DefaultMutableTreeNode sourceNode = new DefaultMutableTreeNode(f.getName());
                    for (Monster m : monsters) {
                        sourceNode.add(new DefaultMutableTreeNode(m));
                    }
                    root.add(sourceNode);
                } else {
                    JOptionPane.showMessageDialog(this,
                            "Не удалось загрузить файл: " + f.getName(),
                            "Ошибка", JOptionPane.ERROR_MESSAGE);
                }
            }
            ((DefaultTreeModel) tree.getModel()).reload();
        }
    }

    private void saveAll() {
        for (Map.Entry<File, List<Monster>> entry : storage.entrySet()) {
            File file = entry.getKey();
            List<Monster> monsters = entry.getValue();

            if (file.getName().endsWith(".json")) {
                Exporter.exportToJson(monsters, file);
            } else if (file.getName().endsWith(".xml")) {
                Exporter.exportToXml(monsters, file);
            } else if (file.getName().endsWith(".yaml") || file.getName().endsWith(".yml")) {
                Exporter.exportToYaml(monsters, file);
            }
        }
        JOptionPane.showMessageDialog(this, "Все изменения сохранены!");
    }
}
