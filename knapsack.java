import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class knapsack
{
    static JTextPane listArea;
    static JPanel topHeaderPanel;
    static JPanel leftHeaderPanel;
    static JPanel middleGridPanel;
    static JPanel middlePanel;
    static JButton[][] resultButtons;
    static JFrame frame;
    static JLabel maxValueText;
    static JScrollPane tableScroller;

    static int capacity;
    static int numberOfItems;
    static int maxValue = 0;

    // setting up colors
    static final Color primaryColor = new Color(100, 230, 230);
    static final Color primaryTextColor = new Color(10, 50, 50);
    static final Color textFieldColor = new Color(160, 200, 200);
    static final Color textFieldTextColor = new Color(30, 50, 50);
    static final Color buttonColor = new Color(35, 70, 100);
    static final Color middleGridColor = new Color(35, 85, 115);
    static final Color buttonTextColor = new Color(135, 215, 215);
    static final Color middleAreaColor = new Color(100, 150, 180);
    static final Color middleAreaTextColor = new Color(0, 20, 20);

    public static void createGrids() {
        // removes all elements currently in the middle panel
        middlePanel.removeAll();

        // creating three small panels to make up the table
        topHeaderPanel = new JPanel(new GridLayout(1, capacity+1));
        leftHeaderPanel = new JPanel(new GridLayout(numberOfItems+1, 1));
        middleGridPanel = new JPanel(new GridLayout(numberOfItems+1, capacity+1));
        topHeaderPanel.setBackground(buttonColor);
        leftHeaderPanel.setBackground(buttonColor);
        topHeaderPanel.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
        leftHeaderPanel.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
        middleGridPanel.setBorder(BorderFactory.createBevelBorder(1));
        resultButtons = new JButton[numberOfItems+1][capacity+1];

        // setting up top header panel, uses labels
        for (int i = 0; i <= capacity; i++) {
            JLabel topTemp = new JLabel(Integer.toString(i));
            topTemp.setForeground(buttonTextColor);
            topTemp.setBorder(BorderFactory.createLineBorder(buttonTextColor));
            topTemp.setHorizontalAlignment(SwingConstants.CENTER);
            topHeaderPanel.add(topTemp);
        }

        // setting up left header panel, uses labels
        for (int i = 0; i <= numberOfItems; i++) {
            JLabel topTemp = new JLabel("  Item #" + i + "  ");
            topTemp.setForeground(buttonTextColor);
            topTemp.setBorder(BorderFactory.createLineBorder(buttonTextColor));
            topTemp.setHorizontalAlignment(SwingConstants.CENTER);
            leftHeaderPanel.add(topTemp);
        }

        // setting up the main middle table, uses buttons saved in a 2D array
        for (int i = 0; i <= numberOfItems; i++) {
            for (int j = 0; j <= capacity; j++) {
                resultButtons[i][j] = new JButton("");
                resultButtons[i][j].setBackground(middleGridColor);
                resultButtons[i][j].setForeground(buttonTextColor);
                resultButtons[i][j].setText(Integer.toString(0));
                middleGridPanel.add(resultButtons[i][j]);
            }
        }

        // adding the three smaller panels to the middle panel
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = capacity+1;
        gbc.weighty = 1;
        gbc.fill = GridBagConstraints.BOTH;
        middlePanel.add(topHeaderPanel, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 1;
        gbc.weighty = numberOfItems+1;
        middlePanel.add(leftHeaderPanel, gbc);

        gbc.gridx = 1;
        gbc.weightx = capacity+1;
        middlePanel.add(middleGridPanel, gbc);

        // change background to match the dominant color
        middlePanel.setBackground(primaryColor);

        // update frame
        frame.invalidate();
        frame.validate();
        frame.repaint();



    }


    public static void knapsackGUI() {

        frame = new JFrame("Knapsack Problem");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(640, 680);
        frame.setLayout(new GridBagLayout());
        frame.setResizable(false);

        // setting up main panels in the frame
        JPanel topPanel = new JPanel(new GridBagLayout());
        topPanel.setBackground(primaryColor);
        topPanel.setBorder(BorderFactory.createLineBorder(primaryColor, 10));

        JPanel bottomPanel = new JPanel(new GridBagLayout());
        bottomPanel.setBackground(primaryColor);
        bottomPanel.setBorder(BorderFactory.createLineBorder(primaryColor, 10));

        middlePanel = new JPanel(new GridBagLayout());
        middlePanel.setBorder(BorderFactory.createLineBorder(primaryColor, 4));
        middlePanel.setBackground(middleAreaColor);

        tableScroller = new JScrollPane(middlePanel);
        tableScroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        tableScroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        tableScroller.setBorder(BorderFactory.createLineBorder(primaryColor, 10));
        tableScroller.setBackground(primaryColor);


        // top panel labels
        JLabel capacityLabel = new JLabel("Enter capacity: ");
        JLabel weightLabel = new JLabel("Enter weights: ");
        JLabel valueLabel = new JLabel("Enter values: ");
        capacityLabel.setForeground(primaryTextColor);
        weightLabel.setForeground(primaryTextColor);
        valueLabel.setForeground(primaryTextColor);
        capacityLabel.setBorder(BorderFactory.createLineBorder(primaryColor, 5));
        weightLabel.setBorder(BorderFactory.createLineBorder(primaryColor, 5));
        valueLabel.setBorder(BorderFactory.createLineBorder(primaryColor, 5));

        // top panel text fields
        JTextField capacityInput = new JTextField();
        JTextField weightInput = new JTextField();
        JTextField valueInput = new JTextField();
        capacityInput.setBackground(textFieldColor);
        capacityInput.setForeground(textFieldTextColor);
        weightInput.setBackground(textFieldColor);
        weightInput.setForeground(textFieldTextColor);
        valueInput.setBackground(textFieldColor);
        valueInput.setForeground(textFieldTextColor);
        capacityInput.setBorder(BorderFactory.createEtchedBorder(primaryColor, middleAreaColor));
        weightInput.setBorder(BorderFactory.createEtchedBorder(primaryColor, middleAreaColor));
        valueInput.setBorder(BorderFactory.createEtchedBorder(primaryColor, middleAreaColor));

        // top panel buttons
        JButton solveButton = new JButton("SOLVE");
        JButton randomButton = new JButton("RANDOM");
        solveButton.setBackground(buttonColor);
        solveButton.setForeground(buttonTextColor);
        randomButton.setBackground(buttonColor);
        randomButton.setForeground(buttonTextColor);
        solveButton.setBorder(BorderFactory.createLineBorder(primaryColor, 5));
        randomButton.setBorder(BorderFactory.createLineBorder(primaryColor, 5));

        // adding all elements to the top panel
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        topPanel.add(capacityLabel, gbc);
        gbc.gridy = 1;
        topPanel.add(weightLabel, gbc);
        gbc.gridy = 2;
        topPanel.add(valueLabel, gbc);
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.BOTH;
        topPanel.add(capacityInput, gbc);
        gbc.gridy = 1;
        gbc.weightx = 3;
        gbc.gridwidth = 2;
        topPanel.add(weightInput, gbc);
        gbc.gridy = 2;
        topPanel.add(valueInput, gbc);
        gbc.gridx = 3;
        gbc.gridy = 2;
        gbc.weightx = 1;
        gbc.gridwidth = 1;
        topPanel.add(solveButton, gbc);
        gbc.gridx = 4;
        topPanel.add(randomButton, gbc);

        // bottom tings in the bottom panel

        // maximum value panel
        JPanel maxValuePanel = new JPanel(new GridBagLayout());
        maxValuePanel.setBackground(middleAreaColor);
        maxValuePanel.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));

        maxValueText = new JLabel("");
        maxValueText.setForeground(middleAreaTextColor);
        maxValueText.setFont(new Font("Verdana", Font.BOLD, 14));
        maxValuePanel.add(maxValueText);

        // empty placeholder panel
        JPanel emptyPanel = new JPanel();
        emptyPanel.setBackground(primaryColor);

        // items label
        JLabel itemsLabel = new JLabel("List of Items");
        itemsLabel.setHorizontalAlignment(SwingConstants.CENTER);

        // items list panel
        JPanel listPanel = new JPanel(new GridBagLayout());
        listPanel.setBackground(middleAreaColor);
        listPanel.setBorder(BorderFactory.createLineBorder(middleAreaColor, 7));
        listPanel.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));

        // items list text pane
        listArea = new JTextPane();
        StyledDocument doc = listArea.getStyledDocument();
        SimpleAttributeSet center = new SimpleAttributeSet();
        StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);
        doc.setParagraphAttributes(0, doc.getLength(), center, false);
        listArea.setEditable(false);
        listArea.setBackground(middleAreaColor);
        listArea.setForeground(middleAreaTextColor);

        /*

        JPanel emptyTallPanel = new JPanel();
        emptyTallPanel.setBackground(primaryColor);

        // bottom panel buttons
        JButton startButton = new JButton("<<");
        JButton previousButton = new JButton("<");
        JButton nextButton = new JButton(">");
        JButton endButton = new JButton(">>");
        startButton.setBackground(buttonColor);
        startButton.setForeground(buttonTextColor);
        previousButton.setBackground(buttonColor);
        previousButton.setForeground(buttonTextColor);
        nextButton.setBackground(buttonColor);
        nextButton.setForeground(buttonTextColor);
        endButton.setBackground(buttonColor);
        endButton.setForeground(buttonTextColor);
        */

        // adding list area to the list panel to center
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.BOTH;
        listPanel.add(listArea, gbc);

        // adding the elements to the bottom panel
        gbc.weightx = 10;
        gbc.weighty = 2;
        bottomPanel.add(maxValuePanel, gbc);
        gbc.gridy = 1;
        gbc.weighty = 1;
        bottomPanel.add(emptyPanel, gbc);
        gbc.gridy = 2;
        bottomPanel.add(itemsLabel, gbc);
        gbc.gridy = 3;
        gbc.weighty = 6;
        bottomPanel.add(listPanel, gbc);

        /*
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        bottomPanel.add(emptyTallPanel, gbc);
        gbc.gridx = 2;
        bottomPanel.add(startButton, gbc);
        gbc.gridx = 3;
        bottomPanel.add(previousButton, gbc);
        gbc.gridx = 4;
        bottomPanel.add(nextButton, gbc);
        gbc.gridx = 5;
        bottomPanel.add(endButton, gbc);
        */

        // adding the three main panels to the frame
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridheight = 1;
        gbc.weighty = 1;
        gbc.fill = GridBagConstraints.BOTH;
        frame.add(topPanel, gbc);
        gbc.gridy = 1;
        gbc.weighty = 10;
        frame.add(tableScroller, gbc);
        gbc.gridy = 2;
        gbc.weighty = 4;
        frame.add(bottomPanel, gbc);




        // random button action
        randomButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String randomValueString = "";
                String randomWeightString = "";
                Random rand = new Random();

                capacityInput.setText(String.valueOf(rand.nextInt(95)+5));

                int randomItems = rand.nextInt(10)+1;

                for (int i = 0; i < randomItems; i++) {
                    randomWeightString += rand.nextInt(20)+1 + " ";
                }
                weightInput.setText(randomWeightString);

                for (int i = 0; i < randomItems; i++) {
                    randomValueString += rand.nextInt(30)+1 + " ";
                }
                valueInput.setText(randomValueString);
            }
        });



        // solve button action
        solveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    capacity = Integer.parseInt(capacityInput.getText());
                    String arr1 = weightInput.getText();
                    String arr2 = valueInput.getText();
                    String arr1_arr[] = arr1.split(" ");
                    String arr2_arr[] = arr2.split(" ");

                    int weight[] = new int[arr2_arr.length];
                    int value[] = new int[arr1_arr.length];
                    numberOfItems = value.length;

                    for (int i = 0; i < arr1_arr.length; i++) {
                        weight[i] = Integer.parseInt(arr1_arr[i]);
                    }

                    for (int j = 0; j < arr2_arr.length; j++) {
                        value[j] = Integer.parseInt(arr2_arr[j]);
                    }

                    createGrids();
                    knapsackDP(weight, value, capacity, numberOfItems);
                    frame.repaint();

                }
                catch (Exception catcher) {
                    JOptionPane.showMessageDialog(null, "Oops. Something's not right. Please check your input.");
                }
            }
        });

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

    }


    public static void knapsackDP(int weight[], int value[], int capacity, int numOfItems) throws InterruptedException {
        //create table
        int table[][] = new int[numOfItems + 1][capacity + 1];
        //set default values to 0
        for (int i=0; i<=numOfItems; i++) {
            for (int j=0; j<=capacity; j++) {
                table[i][j] = 0;
            }
        }


        //build the table in bottom-up manner.
        for (int i = 1; i <= numOfItems; i++) {
            for (int j = 0; j <= capacity; j++) {
                table[i][j] = table[i - 1][j];

                if ((j >= weight[i-1]) && (table[i][j] < table[i - 1][j - weight[i - 1]] + value[i - 1]))
                {
                    table[i][j] = table[i - 1][j - weight[i - 1]] + value[i - 1];
                }
                //show each row being performed in gui
                resultButtons[i][j].setText(Integer.toString(table[i][j]));
            }
        }

        // save the items kept and corresponding weights and values
        maxValue = table[numOfItems][capacity];
        String temp = "";

        while (numOfItems != 0) {
            if (table[numOfItems][capacity] != table[numOfItems - 1][capacity]) {
                temp = ("Item " + numOfItems + " :  Weight " + weight[numOfItems - 1] + ", Value " + value[numOfItems - 1] + "\n") + temp;
                capacity = capacity - weight[numOfItems-1];
            }

            numOfItems--;
        }

        // update frame with max value and items
        maxValueText.setText("Max value: " + maxValue);
        listArea.setText(temp);

    }

    public static void main(String args[]) { knapsackGUI();}
}