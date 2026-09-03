// Required libraries for the calculator UI and event handling
import java.awt.*;
import java.awt.event.*;
import java.util.Arrays;
import javax.swing.*;
import javax.swing.border.LineBorder;

public class Calculator {

    // Window dimensions
    int boardWidth = 360;
    int boardHeight = 540;

    // Custom colors used throughout the calculator
    Color customLightGray = new Color(212, 212, 210);
    Color customDarkGray = new Color(80, 80, 80);
    Color customBlack = new Color(28, 28, 28);
    Color customOrange = new Color(255, 149, 0);

    // Button values and symbols
    String[] buttonValues = {
        "AC", "+/-", "%", "÷",
        "7", "8", "9", "×",
        "4", "5", "6", "-",
        "1", "2", "3", "+",
        "0", ".", "√", "="
    };
    String[] rightSymbols = {"÷", "×", "-", "+", "="};
    String[] topSymbols = {"AC", "+/-", "%"};

    // Main window and display components
    JFrame frame = new JFrame("Calculator");
    JLabel displayLabel = new JLabel();
    JPanel displayPanel = new JPanel();
    JPanel buttonsPanel = new JPanel();

    // Variables of the operations
    String A = "0";
    String operator = null;
    String B = null;

    // Calculator initialization
    Calculator() {

        // Configure the main window
        frame.setSize(boardWidth, boardHeight);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
    
        // Configure the display label
        displayLabel.setBackground(customBlack);
        displayLabel.setForeground(Color.white);
        displayLabel.setFont(new Font("Arial", Font.PLAIN, 80));
        displayLabel.setHorizontalAlignment(JLabel.RIGHT);
        displayLabel.setText("0");
        displayLabel.setOpaque(true);

        // Configure the display panel
        displayPanel.setLayout(new BorderLayout());
        displayPanel.add(displayLabel);
        frame.add(displayPanel, BorderLayout.NORTH);

        // Implementation of the button panel
        buttonsPanel.setLayout(new GridLayout(5, 4));
        buttonsPanel.setBackground(customBlack);
        frame.add(buttonsPanel);

        // Implementation of the buttons en each box
        for (int i = 0; i < buttonValues.length; i++) {
            JButton button = new JButton();
            String buttonValue = buttonValues[i];
            button.setFont(new Font("Arial", Font.PLAIN, 30));
            button.setText(buttonValue);
            button.setFocusable(false);
            button.setBorder(new LineBorder(customBlack));


            if (Arrays.asList(topSymbols).contains(buttonValue)) {
                button.setBackground(customLightGray);
                button.setForeground(customBlack);
            }
            else if (Arrays.asList(rightSymbols).contains(buttonValue)) {
                button.setBackground(customOrange);
                button.setForeground(Color.white);
            }
            else {
                button.setBackground(customDarkGray);
                button.setForeground(Color.white);
            }
            buttonsPanel.add(button);

            // Handle button clicks and determine the type of input 
            button.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    JButton button = (JButton) e.getSource();
                    String buttonValue = button.getText();

                    // ÷, ×, -, +, =
                    if (Arrays.asList(rightSymbols).contains(buttonValue)) {
                        if (buttonValue.equals("=")) {
                            if (A != null) {
                                B = displayLabel.getText();
                                double numA = Double.parseDouble(A);
                                double numB = Double.parseDouble(B);

                                switch (operator) {
                                    case "+" -> displayLabel.setText(removeZeroDecimal(numA + numB));
                                    case "-" -> displayLabel.setText(removeZeroDecimal(numA - numB));
                                    case "×" -> displayLabel.setText(removeZeroDecimal(numA * numB));
                                    case "÷" -> displayLabel.setText(removeZeroDecimal(numA / numB));
                                    default -> { } // No operation selected
                                }
                                clearAll();
                            }
                        }
                        else if ("+-×÷".contains(buttonValue)) {
                            if (operator == null) {
                                A = displayLabel.getText();
                                displayLabel.setText("0");
                                B = "0";
                            }
                            operator = buttonValue;
                        }
                    }
                    // AC, +/-, %
                    else if (Arrays.asList(topSymbols).contains(buttonValue)) {
                        if (buttonValue.equals("AC")) {
                            clearAll();
                            displayLabel.setText("0");
                        }
                        else if (buttonValue.equals("+/-")) {
                            double numDisplay = Double.parseDouble(displayLabel.getText());
                            numDisplay *= -1;
                            displayLabel.setText(removeZeroDecimal(numDisplay));
                        }
                        else if (buttonValue.equals("%")) {
                            double numDisplay = Double.parseDouble(displayLabel.getText());
                            numDisplay /= 100;
                            displayLabel.setText(removeZeroDecimal(numDisplay)); 
                        }
                    }
                    else { // Digits or "."
                        if (buttonValue.equals(".")) {
                            if (!displayLabel.getText().contains(buttonValue)) {
                                displayLabel.setText(displayLabel.getText() + buttonValue);
                            }
                        }
                        else if ("0123456789".contains(buttonValue)) {
                            if (displayLabel.getText().equals("0")) {
                                displayLabel.setText(buttonValue);
                            }
                            else {
                                displayLabel.setText(displayLabel.getText() + buttonValue);
                            }
                        }
                        // Implementation of the square root operation
                        else if (buttonValue.equals("√")) {
                            double number = Double.parseDouble(displayLabel.getText());
                            double result = Math.sqrt(number);
                            displayLabel.setText(String.valueOf(result));
                        }
                    }

                }
            });
            // Display the window after initializing all components
            frame.setVisible(true);
        }
    }

    // Resets the operation variables to zero and null
    void clearAll() {
        A = "0";
        operator = null;
        B = null;
    }

    // Removes the decimal zero after changing the sign of the number
    String removeZeroDecimal(double numDisplay) {
        if (numDisplay % 1 == 0) {
            return Integer.toString((int) numDisplay);
        }
        return Double.toString(numDisplay);
    }
}