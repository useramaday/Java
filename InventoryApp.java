import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

// Classe représentant un objet de l'inventaire
class Item {
    private String name;
    private int quantity;

    public Item(String name, int quantity) {
        this.name = name;
        this.quantity = quantity;
    }

    public String getName() {
        return name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return name + " (Quantité : " + quantity + ")";
    }
}

// Classe gérant l'inventaire
class Inventory {
    private List<Item> items;

    public Inventory() {
        items = new ArrayList<>();
    }

    public void addItem(String name, int quantity) {
        for (Item item : items) {
            if (item.getName().equalsIgnoreCase(name)) {
                item.setQuantity(item.getQuantity() + quantity);
                return;
            }
        }
        items.add(new Item(name, quantity));
    }

    public void removeItem(String name) {
        items.removeIf(item -> item.getName().equalsIgnoreCase(name));
    }

    public List<Item> getItems() {
        return items;
    }
}

// Classe principale de l'application Swing
public class InventoryApp {
    private Inventory inventory;
    private JFrame frame;
    private JPanel inventoryPanel;

    public InventoryApp() {
        inventory = new Inventory();
        initializeUI();
    }

    private void initializeUI() {
        frame = new JFrame("Gestion d'Inventaire");
        frame.setSize(600, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        // Panneau pour l'inventaire (Grille)
        inventoryPanel = new JPanel();
        inventoryPanel.setLayout(new GridLayout(0, 3, 10, 10)); // 3 colonnes, lignes dynamiques
        JScrollPane scrollPane = new JScrollPane(inventoryPanel);
        frame.add(scrollPane, BorderLayout.CENTER);

        // Panneau pour les actions (Ajouter / Supprimer)
        JPanel actionPanel = new JPanel(new GridLayout(3, 1));
        frame.add(actionPanel, BorderLayout.EAST);

        // Champs pour ajouter un objet
        JPanel addPanel = new JPanel(new GridLayout(3, 2));
        JLabel nameLabel = new JLabel("Nom :");
        JTextField nameField = new JTextField();
        JLabel quantityLabel = new JLabel("Quantité :");
        JTextField quantityField = new JTextField();
        JButton addButton = new JButton("Ajouter");
        addPanel.add(nameLabel);
        addPanel.add(nameField);
        addPanel.add(quantityLabel);
        addPanel.add(quantityField);
        addPanel.add(new JLabel());
        addPanel.add(addButton);
        actionPanel.add(addPanel);

        // Bouton pour supprimer un objet
        JPanel removePanel = new JPanel(new GridLayout(2, 1));
        JLabel removeLabel = new JLabel("Nom de l'objet à supprimer :");
        JTextField removeField = new JTextField();
        JButton removeButton = new JButton("Supprimer");
        removePanel.add(removeLabel);
        removePanel.add(removeField);
        actionPanel.add(removePanel);

        // Ajouter le bouton de suppression
        actionPanel.add(removeButton);

        // Actions des boutons
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = nameField.getText();
                String quantityText = quantityField.getText();
                if (!name.isEmpty() && !quantityText.isEmpty()) {
                    try {
                        int quantity = Integer.parseInt(quantityText);
                        inventory.addItem(name, quantity);
                        updateInventoryDisplay();
                        nameField.setText("");
                        quantityField.setText("");
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(frame, "Quantité invalide !", "Erreur", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });

        removeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = removeField.getText();
                if (!name.isEmpty()) {
                    inventory.removeItem(name);
                    updateInventoryDisplay();
                    removeField.setText("");
                }
            }
        });

        // Afficher la fenêtre
        frame.setVisible(true);
        updateInventoryDisplay();
    }

    private void updateInventoryDisplay() {
        inventoryPanel.removeAll(); // Supprime les composants existants avant de réafficher

        // Ajoute les objets dans la grille
        for (Item item : inventory.getItems()) {
            JLabel nameLabel = new JLabel(item.getName());
            JLabel quantityLabel = new JLabel(String.valueOf(item.getQuantity()));
            inventoryPanel.add(nameLabel);
            inventoryPanel.add(quantityLabel);
        }

        inventoryPanel.revalidate();
        inventoryPanel.repaint();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new InventoryApp());
    }
}
