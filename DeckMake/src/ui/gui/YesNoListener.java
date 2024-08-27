package ui.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class YesNoListener implements ActionListener {
    private boolean isYes = true;
    private YesNoGUIManager manager;
    public YesNoListener(boolean isYes, YesNoGUIManager manager) {
        this.isYes = isYes;
        this.manager = manager;
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        manager.setIsYes(this.isYes);
        synchronized (manager) {
            manager.notifyAll();
        }
    }
}
