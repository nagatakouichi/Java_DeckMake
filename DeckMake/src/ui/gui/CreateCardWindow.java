package ui.gui;

import card.data.Card;
import card.data.Creature;
import card.data.Spell;
import card.type.CardType;
import card.type.EnumColor;

import javax.swing.*;
import java.awt.*;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CreateCardWindow extends JFrame {
    private CreateCardGUIManager manager;
    private JComboBox<CardType> cardTypeComboBox;
    private JTextField cardNameTextField;
    //private JFormattedTextField costTextField;
    private JTextField costTextField;
    private Map<EnumColor, JCheckBox> colorCheckBoxMap;
    private JPanel creaturePanel = new JPanel();
    private JTextField powerTextField;
    private JPanel spellPanel = new JPanel();
    private JTextField numTextField;

    private final int WINDOW_WIDTH = 400;
    private final int WINDOW_HEIGHT = 400;

    public CreateCardWindow(CreateCardGUIManager manager) {
        this.manager = manager;
        this.setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));
        this.setSize(this.WINDOW_WIDTH, WINDOW_HEIGHT);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);

        this.addCardTypeComboBox();
        this.addCardNameTextField();
        this.addCostTextField();
        this.addColorCheckBox();

        this.addCreaturePanel();
        this.addSpellPanel();

        this.addNumTextField();
        this.addRegisterButtonGUI();

        this.setVisible(true);
    }

    private void addCardTypeComboBox() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(2,1));
        JLabel typeLabel = new JLabel("カードの種類");
        panel.add(typeLabel);

        this.cardTypeComboBox = new JComboBox<>(CardType.values());
        this.cardTypeComboBox.addActionListener(e -> this.selectCardType());
        panel.add(this.cardTypeComboBox);

        this.add(panel);
        this.selectCardType();
    }

    private void selectCardType() {
        CardType type = (CardType) this.cardTypeComboBox.getSelectedItem();

        switch (type) {
            case CardType.Creature -> {
                this.creaturePanel.setVisible(true);
                this.spellPanel.setVisible(false);
            }
            case Spell -> {
                this.creaturePanel.setVisible(false);
                this.spellPanel.setVisible(true);
            }
            case null, default -> {
                this.creaturePanel.setVisible(false);
                this.spellPanel.setVisible(false);
            }
        }
    }

    private void addCardNameTextField() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(2, 1));
        JLabel label = new JLabel("カード名");
        panel.add(label);

        this.cardNameTextField = new JTextField();
        this.cardNameTextField.setMaximumSize(new Dimension(WINDOW_WIDTH, 30));
        this.cardNameTextField.setCaretPosition(this.cardNameTextField.getText().length());
        panel.add(this.cardNameTextField);

        this.add(panel);
    }

    private void addCostTextField() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(2, 1));
        JLabel label = new JLabel("コスト");
        panel.add(label);

        this.costTextField = new JTextField();
        this.costTextField.setMaximumSize(new Dimension(WINDOW_WIDTH, 30));
        this.costTextField.setCaretPosition(this.costTextField.getText().length());
        panel.add(this.costTextField);

        this.add(panel);
    }

    private void addColorCheckBox() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(2, 1));
        JLabel label = new JLabel("文明");
        panel.add(label);

        this.colorCheckBoxMap = new HashMap<>();
        JPanel checkBoxPanel = new JPanel();
        checkBoxPanel.setLayout(new BoxLayout(checkBoxPanel, BoxLayout.X_AXIS));
        for (EnumColor color : EnumColor.values()) {
            JCheckBox checkBox = new JCheckBox(color.getJapanese());
            checkBoxPanel.add(checkBox);
            this.colorCheckBoxMap.put(color, checkBox);
        }

        panel.add(checkBoxPanel);
        this.add(panel);
    }

    private void addCreaturePanel() {
        this.creaturePanel.setLayout(new GridLayout(2, 1));
        JLabel label = new JLabel("パワー");
        this.creaturePanel.add(label);

        this.powerTextField = new JTextField();
        this.powerTextField.setMaximumSize(new Dimension(WINDOW_WIDTH, 30));
        this.powerTextField.setCaretPosition(this.powerTextField.getText().length());
        this.creaturePanel.add(this.powerTextField);

        this.add(this.creaturePanel);
    }

    private void addSpellPanel() {
        this.add(this.spellPanel);
    }

    private void addNumTextField() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(2, 1));
        JLabel label = new JLabel("枚数");
        panel.add(label);

        this.numTextField = new JTextField();
        this.numTextField.setMaximumSize(new Dimension(WINDOW_WIDTH, 10));
        this.numTextField.setCaretPosition(this.numTextField.getText().length());
        panel.add(this.numTextField);

        this.add(panel);
    }

    private void addRegisterButtonGUI() {
        JButton button = new JButton("追加");
        button.setAlignmentX(0.5f);
        button.addActionListener(e -> this.registerAndClose());
        this.add(button);
    }

    private void registerAndClose() {
        Card card = null;
        //カードデータ作成
        String cardName = this.cardNameTextField.getText();
        if (cardName.isEmpty()) {
            this.showErrorWindow("カード名が入力されていません。");
            return;
        }

        Integer cost = this.getIntFromTextField(this.costTextField, "コストが正しく入力されていません。");
        if (cost == null) {
            return;
        } else if (cost < 0) {
            this.showErrorWindow("コストがマイナスになっています");
            return;
        }

        ArrayList<EnumColor> colors = new ArrayList<>();
        for (EnumColor color : EnumColor.values()) {
            JCheckBox checkBox = this.colorCheckBoxMap.get(color);
            if (checkBox.isSelected()) {
                colors.add(color);
            }
        }
        if (colors.size() <= 0) {
            this.showErrorWindow("文明が選ばれていません。");
            return;
        }

        CardType type = (CardType) this.cardTypeComboBox.getSelectedItem();

        switch (type) {
            case Creature -> {
                Integer power = 0;
                if (this.powerTextField != null) {
                    power = this.getIntFromTextField(this.powerTextField, "パワーが正しく入力されていません。");
                    if (power == null) {
                        return;
                    }
                } else {
                    showErrorWindow("パワーが入力されていません。");
                    return;
                }

                card = new Creature(cardName, cost, colors, power);
            }
            case Spell -> {
                card = new Spell(cardName, cost, colors);
            }
            case null, default -> {
                this.showErrorWindow("エラー：カードタイプが不明です。");
                return;
            }
        }

        Integer num = this.getIntFromTextField(this.numTextField, "カード枚数が正しく入力されていません。");
        if (num == null) {
            return;
        } else if (num <= 0) {
            this.showErrorWindow("カード枚数が0以下になっています。");
            return;
        }

        this.manager.setCardData(card, num);
        this.dispose();
    }

    private Integer getIntFromTextField(JTextField textField, String error) {
        Integer num = null;
        String str = textField.getText();
        try {
            num = Integer.parseInt(str);
        } catch (NumberFormatException e) {
            this.showErrorWindow(error);
        }

        return num;
    }

    private void showErrorWindow(String errorMessage) {
        JFrame frame = new JFrame();
        frame.setSize(300, 120);
        frame.setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS));

        frame.add(Box.createVerticalGlue());

        JLabel label = new JLabel(errorMessage);
        label.setAlignmentX(0.5f);
        frame.add(label);

        frame.add(Box.createVerticalGlue());

        JButton closeButton = new JButton("閉じる");
        closeButton.setAlignmentX(0.5f);
        closeButton.addActionListener(e -> frame.dispose());
        frame.add(closeButton);

        frame.add(Box.createVerticalGlue());

        frame.setVisible(true);
    }
}
