import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import javax.swing.*;
import javax.swing.border.LineBorder;


public class Calculator {

    int boardWidth = 360;
    int boardHeight = 540;

    Color customLightGray = new Color(212, 212, 210);
    Color customDarkGray = new Color(80, 80, 80);
    Color customBlack = new Color(28, 28, 28);
    Color customOrange = new Color(255, 149, 0);

    String[] buttonValues = {
            "AC", "DEC", "BIN", "/",
            "7", "8", "9", "x",
            "4", "5", "6", "-",
            "1", "2", "3", "+",
            "0", ".", "=",
    };
    String[] rightSymbols = {"/", "x", "+", "=", "-"};
    String[] topSymbols = {"AC", "DEC", "BIN"};

    JFrame frame = new JFrame("Binary Calculator");

    JLabel displayLabel = new JLabel();
    JLabel binaryLabel = new JLabel();
    JPanel displayPanel = new JPanel();
    JPanel buttonsPanel = new JPanel();

    String A = "0";
    String operator = null;
    String B = null;

    void calculator () {
        frame.setVisible(true);
        frame.setSize(boardWidth, boardHeight);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());


        displayLabel.setBackground(customBlack);
        displayLabel.setForeground(Color.white);
        displayLabel.setFont(new Font("zzzzzz", Font.PLAIN, 60));
        displayLabel.setHorizontalAlignment(JLabel.RIGHT);
        displayLabel.setText("0");
        displayLabel.setOpaque(true);


        binaryLabel.setBackground(customBlack);
        binaryLabel.setForeground(new Color(100, 200, 255));
        binaryLabel.setFont(new Font("aaaaaaa", Font.BOLD, 20));
        binaryLabel.setHorizontalAlignment(JLabel.RIGHT);
        binaryLabel.setText("Binary: 0");
        binaryLabel.setOpaque(true);

        displayPanel.setLayout(new GridLayout(2, 1));
        displayPanel.add(displayLabel);
        displayPanel.add(binaryLabel);
        frame.add(displayPanel, BorderLayout.NORTH);


        buttonsPanel.setLayout(new GridLayout(5, 4));
        buttonsPanel.setBackground(customBlack);
        frame.add(buttonsPanel, BorderLayout.CENTER);

        for (int i = 0; i < buttonValues.length; i++) {
            JButton button = new JButton();
            String buttonValue = buttonValues[i];
            button.setFont(new Font("zzzz", Font.PLAIN, 26));
            button.setText(buttonValue);
            button.setFocusable(false);
            button.setBorder(new LineBorder(customBlack));

            if (Arrays.asList(topSymbols).contains(buttonValue)) {
                button.setBackground(customLightGray);
                button.setForeground(customBlack);
            } else if (Arrays.asList(rightSymbols).contains(buttonValue)) {
                button.setBackground(customOrange);
                button.setForeground(Color.white);
            } else {
                button.setBackground(customDarkGray);
                button.setForeground(Color.white);
            }

            buttonsPanel.add(button);

            button.addActionListener(new ActionListener() {

                public void actionPerformed(ActionEvent e) {

                    JButton button = (JButton) e.getSource();
                    String buttonValue = button.getText();

                    if (Arrays.asList(rightSymbols).contains(buttonValue)) {

                        if(buttonValue.equals("=")){
                            if(A != null && operator != null){
                                B = displayLabel.getText();
                                long result = performBinaryCalculation();
                                displayLabel.setText(String.valueOf(result));
                                updateBinaryDisplay(result);
                                A = String.valueOf(result);
                                operator = null;
                                B = null;
                            }
                        }
                        else if("+-/x".contains(buttonValue)){
                            if(operator == null){
                                A = displayLabel.getText();
                                displayLabel.setText("0");
                                B = "0";
                            } else {

                                B = displayLabel.getText();
                                long result = performBinaryCalculation();
                                displayLabel.setText(String.valueOf(result));
                                updateBinaryDisplay(result);
                                A = String.valueOf(result);
                                displayLabel.setText("0");
                            }
                            operator = buttonValue;
                        }
                    } else if (buttonValue.equals("AC")) {
                        clearAll();
                        displayLabel.setText("0");
                        binaryLabel.setText("Binary: 0");
                    } else if (buttonValue.equals("BIN")) {

                        try {
                            long num = Long.parseLong(displayLabel.getText());
                            updateBinaryDisplay(num);
                        } catch (NumberFormatException ex) {
                            binaryLabel.setText("Binary: Error");
                        }
                    } else if (buttonValue.equals("DEC")) {

                        try {
                            long num = Long.parseLong(displayLabel.getText());
                            updateBinaryDisplay(num);
                        } catch (NumberFormatException ex) {
                            binaryLabel.setText("Binary: Error");
                        }
                    } else if ("0123456789".contains(buttonValue)) {

                        if (displayLabel.getText().equals("0")) {
                            displayLabel.setText(buttonValue);
                        } else {
                            displayLabel.setText(displayLabel.getText() + buttonValue);
                        }
                        try {
                            long num = Long.parseLong(displayLabel.getText());
                            updateBinaryDisplay(num);
                        } catch (NumberFormatException ex) {
                            binaryLabel.setText("Binary: Error");
                        }
                    } else if (buttonValue.equals(".")) {

                    }
                }
            });
        }
    }

    long performBinaryCalculation() {

        long numA = Long.parseLong(A);
        long numB = Long.parseLong(B);


        String binA = Long.toBinaryString(numA);
        String binB = Long.toBinaryString(numB);


        long binaryNumA = Long.parseLong(binA, 2);
        long binaryNumB = Long.parseLong(binB, 2);

        long result = 0;


        switch(operator) {
            case "+":
                result = Math.addExact(binaryNumA, binaryNumB);
                break;
            case "-":
                result = Math.subtractExact(binaryNumA, binaryNumB);
                break;
            case "x":
                result = Math.multiplyExact(binaryNumA, binaryNumB);
                break;
            case "/":
                if (binaryNumB != 0) {
                    result = binaryNumA / binaryNumB;
                } else {
                    return 0;
                }
                break;
        }

        return result;
    }

    void updateBinaryDisplay(long decimal) {
        String binary = Long.toBinaryString(decimal);
        binaryLabel.setText("Binary: " + binary);
    }

    void clearAll(){
        A = "0";
        operator = null;
        B = null;
    }

    public static void main(String[] args) {
        Calculator calc = new Calculator();
        calc.calculator();
    }
}