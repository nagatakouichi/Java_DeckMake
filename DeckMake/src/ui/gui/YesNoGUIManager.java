package ui.gui;

import javax.swing.*;
import java.awt.*;
import java.util.concurrent.atomic.AtomicBoolean;

public class YesNoGUIManager {
    private final float CENTER_ALIGNMENT = 0.5f;
    private AtomicBoolean isYes = new AtomicBoolean(false);
    private YesNoListener yesListener;
    private YesNoListener noListener;

    public YesNoGUIManager(){
        yesListener = new YesNoListener(true, this);
        noListener = new YesNoListener(false, this);
    }

    public void setIsYes(boolean isYes) {
        this.isYes.set(isYes);
    }

    public boolean yesNoGUI(String labelText){
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(300, 150);
        frame.setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS));
        frame.addWindowListener(new CloseWindowListener(this));

        JLabel label = new JLabel(labelText);
        label.setAlignmentX(CENTER_ALIGNMENT);
        frame.add(label);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
        JButton yesButton = new JButton("はい");
        yesButton.addActionListener(yesListener);
        JButton noButton = new JButton("いいえ");
        noButton.addActionListener(noListener);
        buttonPanel.add(yesButton);
        buttonPanel.add(Box.createRigidArea(new Dimension(50, 50)));
        buttonPanel.add(noButton);
        frame.add(buttonPanel);

        frame.setVisible(true);

        synchronized (this) {
            try {
                this.wait();
            } catch (InterruptedException e) {
                System.out.println("GUI待機中エラー:" + e.getMessage());
                this.notifyAll();
                throw new IllegalStateException("GUIの処理が失敗");
            }
        }
        frame.dispose();
        return this.isYes.get();
    }


}
