package ui.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GetTextListener implements ActionListener {
    private TextGUIManager textGUIManager;

    public GetTextListener(TextGUIManager textGUIManager) {
        this.textGUIManager = textGUIManager;
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        synchronized (textGUIManager) {
            textGUIManager.setInputtedText();
            textGUIManager.notifyAll();
        }
    }
}
