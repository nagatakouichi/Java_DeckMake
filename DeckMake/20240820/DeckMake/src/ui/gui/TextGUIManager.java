package ui.gui;

import javax.swing.*;
import java.util.concurrent.atomic.AtomicReference;

public class TextGUIManager {
    private final float CENTER_ALIGNMENT = 0.5f;

    private AtomicReference<String> inputtedText = new AtomicReference<>();
    private JTextField textField;

    public String getTextFromGUI(String labelText){
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(300, 150);
        frame.setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS));
        frame.addWindowListener(new CloseWindowListener(this));

        JLabel label = new JLabel(labelText);
        label.setAlignmentX(CENTER_ALIGNMENT);
        frame.add(label);

        JPanel textPanel = new JPanel();
        JLabel textLabel = new JLabel("入力欄");
        this.textField = new JTextField(20);
        this.textField.addActionListener(new GetTextListener(this));
        textPanel.add(textLabel);
        textPanel.add(this.textField);
        frame.add(textPanel);
        JButton okButton = new JButton("OK");
        okButton.addActionListener(new GetTextListener(this));
        frame.add(okButton);

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

        if (inputtedText == null) {
            return null;
        }
        return inputtedText.get();
    }

    public void setInputtedText(){
        if (textField != null) {
            inputtedText.set(textField.getText());
        }
    }
}
