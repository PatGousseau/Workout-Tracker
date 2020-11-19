package ui;

import model.Analyzer;
import model.Exercise;
import model.Routine;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.StandardChartTheme;
import org.jfree.data.category.DefaultCategoryDataset;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

public class GUI extends JFrame implements ActionListener, ListSelectionListener {

    private static final int WIDTH = 700;
    private static final int HEIGHT = 500;
    String name = null;
    boolean gettingUserInput = true;
    private JFrame frame;
    private JPanel panel;
    private DefaultListModel listModel;
    private JsonReader reader;
    private JsonWriter writer = new JsonWriter("data/data.json");
    private JList list;
    private JList routineList;
    private DefaultListModel routineModel;
    private JList statsList;
    private DefaultListModel statsModel;
    private JButton addExerciseB;
    private JButton addNewExerciseB;
    private JButton doneB;
    private JButton quitB;
    private JButton viewStatsB;
    private JButton viewGraphB;
    private Routine routine;
    private JTextField exerciseName;
    private String storedName;
    private JLabel enterExercise;
    private JTextField numSetsText;
    private JLabel numSetsLabel;
    private Analyzer analyzer;
    private JSeparator separator = new JSeparator();
    private JLabel orLabel;
    private JFreeChart chart;
    private JButton removeB;
    private JLabel routineLabel;
    private JLabel pastExLabel;
    private JLabel improvementLabel;
    private int numSets;

    public GUI() {
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
        String strDate = formatter.format(date);
        System.out.println(strDate);
        routine = new Routine("routine");
        setGui();
        addExercise();
    }

    public static void main(String[] args) {
        GUI gui = new GUI();
    }

    //EFFECTS: Creates and adds all components to GUI
    private void setGui() {

        frame = new JFrame("Workout Tracker");
        panel = new JPanel();
        frame.getContentPane();
        panel.setLayout(null);
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.add(panel);
        frame.setSize(WIDTH, HEIGHT);
        frame.setVisible(true);
        Color backgroundColor = new Color(254, 250, 224);
        panel.setBackground(backgroundColor);
        separator.setOrientation(SwingConstants.VERTICAL);
        separator.setVisible(true);
        panel.add(separator);

        done();
        displayExercises();
        displayRoutine();
        removeButton();
    }

    //EFFECTS: Adds list of past exercises to GUI
    private String displayExercises() {

        try {
            reader = new JsonReader("data/data.json");
            listModel = new DefaultListModel();
            Set<String> exerciseNames = reader.getKeys();
            for (String name : exerciseNames) {
                listModel.addElement(name);
            }

            list = new JList(listModel);
            list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            list.setSelectedIndex(0);
            list.addListSelectionListener(this);
            list.setVisibleRowCount(5);
            list.setBounds(30, 30, 200, 200);
            list.setFont(new Font("open sans", Font.PLAIN, 15));
            JScrollPane listScrollPane = new JScrollPane(list);
            list.setVisible(true);
            listScrollPane.setVisible(true);
            panel.add(list);
            addListLabels();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return name;
    }

    //EFFECTS: Adds all labels to GUI
    private void addListLabels() {
        orLabel = new JLabel("or");
        orLabel.setBounds(250, 205, 20, 20);
        ;
        orLabel.setFont(new Font("open sans", Font.PLAIN, 15));
        orLabel.setVisible(true);
        pastExLabel = new JLabel("Past Exercises");
        pastExLabel.setBounds(80, 10, 100, 20);
        pastExLabel.setFont(new Font("open sans", Font.PLAIN, 15));
        pastExLabel.setVisible(true);
        routineLabel = new JLabel("Current Routine");
        routineLabel.setBounds(513, 10, 200, 20);
        routineLabel.setFont(new Font("open sans", Font.PLAIN, 15));
        routineLabel.setVisible(true);
        panel.add(routineLabel);
        panel.add(pastExLabel);
        panel.add(orLabel);
    }

    // EFFECTS: Displays the exercises performed by user
    private void displayRoutine() {

        routineModel = new DefaultListModel();
        routineList = new JList();
        for (int i = 0; i < routine.getSize(); i++) {
            routineModel.addElement(routine.getExercise(i).getName());
        }

        routineList = new JList(routineModel);
        routineList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        routineList.setSelectedIndex(0);
        routineList.addListSelectionListener(this);
        routineList.setVisibleRowCount(5);
        routineList.setBounds(470, 30, 200, 200);
        routineList.setFont(new Font("open sans", Font.PLAIN, 15));
        JScrollPane routineListScrollPane = new JScrollPane(routineList);
        routineList.setVisible(true);
        routineListScrollPane.setVisible(true);
        panel.add(routineListScrollPane);
        panel.add(routineList);
        panel.add(routineListScrollPane);
    }

    // EFFECTS: Adds Done button
    private void done() {
        doneB = new JButton("Done");
        doneB.setBackground(new Color(40, 54, 24));
        doneB.setForeground(new Color(255, 255, 255));
        doneB.setFont(new Font("open sans", Font.PLAIN, 15));
        Dimension doneBDim = doneB.getPreferredSize();
        doneB.setBounds(170, 400, doneBDim.width, doneBDim.height);
        doneB.setVisible(true);
        panel.add(doneB);
        doneB.addActionListener(this);
    }

    // EFFECTS: Adds all components related to adding an exercise
    private void addExercise() {
        addSelectButton(); // Add exercise from list
        typeExerciseButton(); // Add typed in exercise
        getNumSets(); // Text field and label for number of sets
    }

    // EFFECTS: Adds enter number of sets functionality
    private void getNumSets() {
        numSetsText = new JTextField();
        numSetsText.setFont(new Font("open sans", Font.PLAIN, 15));
        numSetsLabel = new JLabel("Enter the number of sets:");
        numSetsLabel.setFont(new Font("open sans", Font.PLAIN, 15));
        numSetsText.setBounds(220, 250, 190, 30);
        numSetsLabel.setBounds(30, 255, (int) numSetsLabel.getPreferredSize().getWidth(),
                (int) numSetsLabel.getPreferredSize().getHeight());
        numSetsLabel.setVisible(true);
        numSetsText.setVisible(true);
        panel.add(numSetsLabel);
        panel.add(numSetsText);
        try {
            numSets = Integer.parseInt(numSetsText.getText());
        } catch (NumberFormatException e) {
            System.out.println();
        }
    }

    // EFFECTS: Adds add selected exercise button
    private void addSelectButton() {
        addExerciseB = new JButton("Begin exercise");
        addExerciseB.setFont(new Font("open sans", Font.PLAIN, 15));
        addExerciseB.setVisible(true);
        Dimension addExSize = addExerciseB.getPreferredSize();
        addExerciseB.setBounds(30, 310, addExSize.width, addExSize.height);
        panel.add(addExerciseB);
        addExerciseB.setBackground(new Color(96, 108, 58));
        addExerciseB.setForeground(new Color(255, 255, 255));
        addExerciseB.addActionListener(this);
    }

    //EFFECTS: Adds components related to adding typed in exercise
    private void typeExerciseButton() {
        // Button
        addNewExerciseB = new JButton("Begin new exercise");
        addNewExerciseB.setFont(new Font("open sans", Font.PLAIN, 15));
        Dimension size = addNewExerciseB.getPreferredSize();
        addNewExerciseB.setVisible(true);
        addNewExerciseB.setBounds(260, 310, size.width, size.height);
        addNewExerciseB.addActionListener(this);
        panel.add(addNewExerciseB);
        addNewExerciseB.setBackground(new Color(96, 108, 58));
        addNewExerciseB.setForeground(new Color(255, 255, 255));

        // Label
        enterExercise = new JLabel("Enter exercise name:");
        enterExercise.setFont(new Font("open sans", Font.PLAIN, 15));
        exerciseName = new JTextField();
        exerciseName.setFont(new Font("open sans", Font.PLAIN, 15));
        enterExercise.setBounds(280, 165, 150, 30);
        exerciseName.setBounds(280, 200, 130, 30);
        enterExercise.setVisible(true);
        exerciseName.setVisible(true);
        panel.add(enterExercise);
        panel.add(exerciseName);
    }

    // EFFECTS: adds remove exercise from current routine button
    private void removeButton() {
        removeB = new JButton("Remove");
        removeB.setFont(new Font("open sans", Font.PLAIN, 15));
        Dimension size = removeB.getPreferredSize();
        removeB.setVisible(true);
        removeB.setBounds(470, 250, 200, size.height);
        removeB.addActionListener(this);
        panel.add(removeB);
        removeB.setBackground(new Color(96, 108, 58));
        removeB.setForeground(new Color(255, 255, 255));
    }

    private void getData() {

        JTextField rep1 = new JTextField(5);
        JTextField rep2 = new JTextField();
        JTextField rep3 = new JTextField();
        JTextField rep4 = new JTextField();
        JTextField weight1 = new JTextField();
        JTextField weight2 = new JTextField();
        JTextField weight3 = new JTextField();
        JTextField weight4 = new JTextField();
        Object[] message = {
                "Reps for set 1:", rep1,
                "Weight for set 1:", weight1,
                "Reps for set 2:", rep2,
                "Weight for set 2:", weight2,
                "Reps for set 3:", rep3,
                "Weight for set 3:", weight3,
                "Reps for set 4:", rep4,
                "Weight for set 4:", weight4,
        };
        int option = JOptionPane.showConfirmDialog(null, message, "Enter all your values",
                JOptionPane.OK_CANCEL_OPTION);

        ArrayList<Long> reps = new ArrayList<Long>();
        ArrayList<Long> weight = new ArrayList<Long>();
        try {
            reps.add(Long.parseLong(rep1.getText()));
            weight.add(Long.parseLong(weight1.getText()));
            reps.add(Long.parseLong(rep2.getText()));
            weight.add(Long.parseLong(weight2.getText()));
            reps.add(Long.parseLong(rep3.getText()));
            weight.add(Long.parseLong(weight3.getText()));
            reps.add(Long.parseLong(rep4.getText()));
            weight.add(Long.parseLong(weight4.getText()));

        } catch (NullPointerException e) {
            System.out.println();
        }
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
        String strDate = formatter.format(date);
        routine.addExercise(new Exercise(storedName, numSets, reps, weight, strDate));
        routineModel.addElement(new Exercise(storedName, numSets, reps, weight).getName());


    }

    private void statsMenu() {
        clearGui();

        //view stats button
        viewStatsB = new JButton("View stats");
        viewStatsB.setFont(new Font("open sans", Font.PLAIN, 20));
        viewStatsB.setBackground(new Color(96, 108, 58));
        viewStatsB.setForeground(new Color(255, 255, 255));
        Dimension size = viewStatsB.getPreferredSize();
        viewStatsB.setVisible(true);
        viewStatsB.setBounds(180, 200, size.width, size.height);
        viewStatsB.addActionListener(this);
        panel.add(viewStatsB);

        //view graph button
        viewGraphB = new JButton("View Graph");
        viewGraphB.setFont(new Font("open sans", Font.PLAIN, 20));
        viewGraphB.setBounds(360, 200, viewGraphB.getPreferredSize().width, viewGraphB.getPreferredSize().height);
        viewGraphB.setBackground(new Color(96, 108, 58));
        viewGraphB.setForeground(new Color(255, 255, 255));
        viewGraphB.setVisible(true);
        viewGraphB.addActionListener(this);
        panel.add(viewGraphB);

        //quit button
        quitB = new JButton("Quit");
        quitB.setFont(new Font("open sans", Font.PLAIN, 15));
        quitB.setBackground(new Color(40, 54, 24));
        quitB.setForeground(new Color(255, 255, 255));
        Dimension quitSize = quitB.getPreferredSize();
        quitB.setVisible(true);
        quitB.setBounds(300, 300, quitSize.width, quitSize.height);
        quitB.addActionListener(this);
        panel.add(quitB);
    }

    // EFFECTS: Clears the GUI from all components
    private void clearGui() {
        addExerciseB.setVisible(false);
        addNewExerciseB.setVisible(false);
        list.setVisible(false);
        enterExercise.setVisible(false);
        exerciseName.setVisible(false);
        numSetsLabel.setVisible(false);
        numSetsText.setVisible(false);
        doneB.setVisible(false);
        orLabel.setVisible(false);
        routineList.setVisible(false);
        removeB.setVisible(false);
        routineLabel.setVisible(false);
        pastExLabel.setVisible(false);
    }

    // EFFECTS: Action listener for all buttons
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == addExerciseB) { // if button is clicked
            storedName = String.valueOf(list.getSelectedValue());
            getData();

        } else if (e.getSource() == addNewExerciseB) {
            storedName = exerciseName.getText();
            getData();

        } else if (e.getSource() == doneB) {
            gettingUserInput = false;
            statsMenu();

        } else if (e.getSource() == viewStatsB) {
            displayStats();
        } else if (e.getSource() == quitB) {
            save();
        } else if (e.getSource() == removeB) {
            removeExercise();
        } else if (e.getSource() == viewGraphB) {
            selectGraph();
        }
    }

    // EFFECTS: Removes selected item from the current routine
    private void removeExercise() {
        String toRemove = String.valueOf(routineList.getSelectedValue());
        routine.removeExercise(toRemove);
        routineModel.removeElement(routineList.getSelectedValue());
    }

    private void displayStats() {
        ArrayList x = new ArrayList();
        analyzer = new Analyzer();
        statsModel = new DefaultListModel();

        Hashtable stats = new Hashtable();
        try {
            stats = analyzer.compareData(routine, "data/data.json");
        } catch (IOException e) {
            e.printStackTrace();
        }
        Set<String> keys = stats.keySet();
        for (String exName : keys) {
            statsModel.addElement(exName + ": " + stats.get(exName) + "%");
            x.add(exName + ": " + stats.get(exName) + "%");
        }

        statsList = new JList(statsModel);
        statsList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        statsList.setSelectedIndex(0);
        statsList.addListSelectionListener(this);
        statsList.setVisibleRowCount(5);
        statsList.setBounds(WIDTH / 2, HEIGHT / 2, 400, 400);
        statsList.setFont(new Font("open sans", Font.PLAIN, 17));
        JScrollPane statsListScrollPane = new JScrollPane(list);
        add(statsListScrollPane, BorderLayout.CENTER);
        add(statsList);
        statsList.setVisible(true);
        statsListScrollPane.setVisible(true);
        panel.add(statsListScrollPane);
        panel.add(statsList);
        panel.add(statsListScrollPane);
        improvementLabel = new JLabel("Percent Improvement");
        improvementLabel.setBounds(400, (HEIGHT / 2) + 40, 100, 20);
        improvementLabel.setFont(new Font("open sans", Font.PLAIN, 18));
        improvementLabel.setVisible(true);
        panel.add(improvementLabel);
        JOptionPane.showConfirmDialog(null, statsList, "Save", JOptionPane.PLAIN_MESSAGE);
    }

    private void graph(String graphName) {
        reader = new JsonReader("data/data.json");
        DefaultCategoryDataset dataSet = new DefaultCategoryDataset();
        HashMap volAndDate = new HashMap();

        try {
            volAndDate = reader.readVolAndDate(graphName);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Set<String> dates = volAndDate.keySet();
        Object[] dateArray = dates.toArray();
        Arrays.sort(dateArray);
        for (int i = 0; i < dateArray.length; i++) {
            dataSet.addValue((Double) volAndDate.get(dateArray[i]), "Volume", (Comparable) dateArray[i]);
        }
        StandardChartTheme theme = (StandardChartTheme) org.jfree.chart.StandardChartTheme.createJFreeTheme();
        theme.setTitlePaint(Color.decode("#4572a7"));
        theme.setExtraLargeFont(new Font("open sans", Font.PLAIN, 16)); //title
        theme.setLargeFont(new Font("open sans", Font.BOLD, 15)); //axis-title
        theme.setRegularFont(new Font("open sans", Font.PLAIN, 11));
        theme.setRangeGridlinePaint(Color.decode("#C0C0C0"));
        theme.setPlotBackgroundPaint(Color.white);
        theme.setChartBackgroundPaint(Color.white);
        theme.setGridBandPaint(Color.red);
        theme.setAxisLabelPaint(Color.decode("#666666"));
        chart = ChartFactory.createLineChart("Your " + graphName + " progress", "Date", "Volume", dataSet);
        theme.apply(chart);
        ChartFrame chartFrame = new ChartFrame("Progress Graph", chart);
        chartFrame.setVisible(true);
        chartFrame.setSize(500, 500);

    }

    // EFFECTS: Creates popup letting the user choose which graph they want
    private void selectGraph() {

        ArrayList<Exercise> exerciseNames = routine.getAllExercises();
        Object[] selectionValues = new Object[exerciseNames.size()];
        JDialog.setDefaultLookAndFeelDecorated(true);
        for (int i = 0; i < exerciseNames.size(); i++) {
            selectionValues[i] = exerciseNames.get(i).getName();
        }

        String initialSelection = null;
        Object selection = JOptionPane.showInputDialog(null, "Which graph would you like to view?",
                "Exercise Selection", JOptionPane.QUESTION_MESSAGE, null, selectionValues, null);
        graph(String.valueOf(selection));
    }

    // EFFECTS: Saves data entered by the user
    private void save() {
        String message = "Save data?";
        int answer = JOptionPane.showConfirmDialog(null, message, "Save", JOptionPane.YES_NO_OPTION);
        if (answer == JOptionPane.YES_OPTION) {
            try {
                writer.write(routine);
            } catch (IOException e) {
                e.printStackTrace();
            }
            JOptionPane.showMessageDialog(null, "Saved!");
        } else {
            JOptionPane.showMessageDialog(null, "Have a good one!");
        }
    }

    // EFFECTS: Gets name of past exercise selected by user
    @Override
    public void valueChanged(ListSelectionEvent e) {
        name = String.valueOf(list.getSelectedValue());
    }
}

