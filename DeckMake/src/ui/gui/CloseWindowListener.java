package ui.gui;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

public class CloseWindowListener implements WindowListener {
    private Object syncObj;

    public CloseWindowListener(Object syncObj) {
        this.syncObj = syncObj;
    }

    @Override
    public void windowOpened(WindowEvent e) {

    }

    @Override
    public void windowClosing(WindowEvent e) {

    }

    @Override
    public void windowClosed(WindowEvent e) {
        synchronized (syncObj) {
            syncObj.notifyAll();
        }
    }

    @Override
    public void windowIconified(WindowEvent e) {

    }

    @Override
    public void windowDeiconified(WindowEvent e) {

    }

    @Override
    public void windowActivated(WindowEvent e) {

    }

    @Override
    public void windowDeactivated(WindowEvent e) {

    }
}
