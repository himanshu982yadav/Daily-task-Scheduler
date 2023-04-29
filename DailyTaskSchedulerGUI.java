import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

public class DailyTaskSchedulerGUI implements ActionListener {

    private JFrame frame;
    private JPanel mainPanel;
    private JPanel inputPanel;
    private JTextField taskNameField;
    private JTextField taskTimeField;
    private JButton addButton;
    private JButton removeButton;
    private JList<String> taskList;
    private List<String> tasks;

    public DailyTaskSchedulerGUI() {
        // Set the look and feel to the system default
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ex) {
            // Ignore
        }

        // Create the main window
        frame = new JFrame("Daily Task Scheduler");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 500);
        frame.setLocationRelativeTo(null);

        // Create the main panel and set its layout
        mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        frame.getContentPane().add(mainPanel);

        // Create the input panel and its components
        inputPanel = new JPanel();
        inputPanel.setLayout(new BoxLayout(inputPanel, BoxLayout.X_AXIS));
        inputPanel.setAlignmentX(JPanel.LEFT_ALIGNMENT);
        inputPanel.setBorder(BorderFactory.createTitledBorder("Add New Task"));

        addButton = new JButton("Add");
        addButton.addActionListener(this);
        removeButton = new JButton("Remove");
        removeButton.addActionListener(this);

        taskNameField = new JTextField(9);
        taskTimeField = new JTextField(5);

        JPanel inputPanel = new JPanel();
        inputPanel.add(new JLabel("Task Name:"));
        inputPanel.add(taskNameField);
        inputPanel.add(new JLabel("Task Time (HH:mm):"));
        inputPanel.add(taskTimeField);
        inputPanel.add(Box.createHorizontalStrut(20));
        inputPanel.add(addButton);
        inputPanel.add(Box.createHorizontalStrut(10));
        inputPanel.add(removeButton);

        JLabel clockLabel = new JLabel(new ImageIcon("clock.png"));
        inputPanel.add(clockLabel);

        // Add the input panel to the main panel
        mainPanel.add(inputPanel);
        mainPanel.add(Box.createVerticalStrut(20));

        // Create the task list panel and its components
        JPanel taskListPanel = new JPanel(new BorderLayout());
        taskListPanel.setBorder(BorderFactory.createTitledBorder("Task List"));
        tasks = new ArrayList<String>();
        taskList = new JList<String>();
        taskList.setFont(new Font("Arial", Font.PLAIN, 16));
        taskList.setBackground(new Color(248, 248, 248));
        JScrollPane scrollPane = new JScrollPane(taskList);
        taskListPanel.add(scrollPane);

        // Add the task list panel to the main panel
        mainPanel.add(taskListPanel);

        // Set the frame to be visible
        // Set the frame to be visible
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        // Run the GUI code on the event dispatching thread
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new DailyTaskSchedulerGUI();
            }
        });
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == addButton) {
            String taskName = taskNameField.getText().trim();
            String taskTime = taskTimeField.getText().trim();

            // Validate the task name and time fields
            if (taskName.isEmpty() || taskTime.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Please enter a task name and time.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                LocalTime.parse(taskTime);
            } catch (DateTimeParseException ex) {
                JOptionPane.showMessageDialog(frame, "Please enter a valid time in the format HH:mm.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Add the task to the list and update the display
            tasks.add(taskName + " at " + taskTime);
            taskList.setListData(tasks.toArray(new String[tasks.size()]));

            // Clear the input fields
            taskNameField.setText("");
            taskTimeField.setText("");

        } else if (e.getSource() == removeButton) {
            // Remove the selected task from the list and update the display
            int selectedIndex = taskList.getSelectedIndex();
            if (selectedIndex != -1) {
                tasks.remove(selectedIndex);
                taskList.setListData(tasks.toArray(new String[tasks.size()]));
            }
        }
    }
}