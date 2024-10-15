package ui.gui;

import card.data.Card;
import deck.Deck;

import javax.swing.*;

public class DeckDataGUIManager {
    private JFrame frame = new JFrame("デッキ内容");

    public DeckDataGUIManager() {
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.setSize(700, 500);
        frame.setLocation(500, 0);
        frame.setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS));

        frame.setVisible(true);
    }

    public void update(Deck deck) {
        frame.getContentPane().removeAll();

        JPanel cardPanel = new JPanel();
        cardPanel.setLayout(new BoxLayout(cardPanel, BoxLayout.Y_AXIS));
        if (deck != null) {
            for (Card c : deck.getCardList()) {
                JLabel cardLabel = new JLabel(c.toString());
                cardPanel.add(cardLabel);
            }
        }
        frame.add(cardPanel);

        frame.validate();
        frame.repaint();
    }

    public void close() {
        frame.dispose();
    }
}
