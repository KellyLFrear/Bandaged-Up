import javax.swing.*;
import java.awt.*;

public class GUI {

    private JFrame frame;
    private JPanel mainPanel;
    private CardLayout cardLayout;


    public GUI() {

        frame = new JFrame();

        // CardLayout allows switching between panels
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        // Create different views(pages)
        mainPanel.add(createHomePage(), "Home");
        mainPanel.add(createLoginPage(), "Login");
        mainPanel.add(createRegisterPage(), "Register");

        frame.add(mainPanel);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setTitle("My GUI");
        frame.pack();
        frame.setVisible(true);
    }

    /**
     * Creates the Home Page with Login and Register buttons.
     */
    private JPanel createHomePage() {
        // General home-page panel
        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                ImageIcon icon = new ImageIcon("background.jpg");
                g.drawImage(icon.getImage(), 0, 0, getWidth(), getHeight(), this);
            }
        };
        panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 10, 30));
        panel.setLayout(new GridLayout(0, 1));

        // panel for header text and image
        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new FlowLayout(FlowLayout.LEFT));

        JLabel headerLabel = new JLabel("Welcome to Bandaged-Up!", SwingConstants.CENTER);
        headerLabel.setFont(new Font("SansSerif", Font.BOLD, 24));
        headerLabel.setForeground(Color.BLUE);

        // Load image (ensure 'smiley_bandage.jpeg' is in the project folder or provide the full path)
        ImageIcon icon = new ImageIcon("smiley_bandage_2.jpeg"); // Provide relative or absolute path
        JLabel imageLabel = new JLabel(icon); // Add image to JLabel


        headerPanel.add(headerLabel);
        headerPanel.add(imageLabel);

        JButton loginButton = new JButton("Login");
        JButton registerButton = new JButton("Register");

        loginButton.addActionListener(e -> cardLayout.show(mainPanel, "Login"));
        registerButton.addActionListener(e -> cardLayout.show(mainPanel, "Register"));

        panel.add(headerPanel, BorderLayout.NORTH);
        panel.add(loginButton);
        panel.add(registerButton);

        return panel;
    }

    /**
     * Creates the Login Page.
     */
    private JPanel createLoginPage() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(6, 1));

        JLabel label = new JLabel("Login Page", SwingConstants.CENTER);
        JButton backButton = new JButton("Back");

        backButton.addActionListener(e -> cardLayout.show(mainPanel, "Home"));

        panel.add(label);

        panel.add(new JLabel("Enter your Username"));
        panel.add(new JTextField());
        panel.add(new JLabel("Enter your Password"));
        panel.add(new JPasswordField());
        panel.add(backButton);

        return panel;
    }
    /**
     * Creates the Register Page.
     */
    private JPanel createRegisterPage() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(0, 1));

        JLabel label = new JLabel("Register Page", SwingConstants.CENTER);
        JButton backButton = new JButton("Back");

        backButton.addActionListener(e -> cardLayout.show(mainPanel, "Home"));

        panel.add(label);

        panel.add(new JLabel("Enter First Name"));
        panel.add(new JTextField());
        panel.add(new JLabel("Enter Last Name"));
        panel.add(new JTextField());
        panel.add(new JLabel("Enter Username"));
        panel.add(new JTextField());
        panel.add(new JLabel("Enter your Password"));
        panel.add(new JPasswordField());

        panel.add(backButton);

        return panel;
    }

}
