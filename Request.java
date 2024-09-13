import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SimpleUI extends JFrame {

    public SimpleUI() {
        // Set the title of the window
        setTitle("Digital UI System");
        
        // Set the default close operation
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        // Set the layout for the frame
        setLayout(new FlowLayout());

        // Create a label
        JLabel label = new JLabel("Enter your name:");
        add(label);

        // Create a text field
        JTextField textField = new JTextField(20);
        add(textField);

        // Create a button
        JButton button = new JButton("Submit");
        add(button);

        // Create an action listener for the button
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Display a message dialog with the entered text
                JOptionPane.showMessageDialog(null, "Hello, " + textField.getText() + "!");
            }
        });

        // Set the size of the window
        setSize(300, 150);
        
        // Set the location of the window to the center of the screen
        setLocationRelativeTo(null);
    }

    public static void main(String[] args) {
        // Create and display the UI
        SwingUtilities.invokeLater(() -> {
            SimpleUI ui = new SimpleUI();
            ui.setVisible(true);
        });
    }
                                 }
