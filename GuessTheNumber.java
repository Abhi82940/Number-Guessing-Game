import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public class GuessTheNumber extends JFrame implements ActionListener {
    private int randomNumber;
    private int attemptsLeft;
    private int score;
    private int round;
    private final int MAX_ATTEMPTS = 5;

    private JTextField inputField;
    private JButton guessButton, newRoundButton;
    private JLabel messageLabel, attemptsLabel, scoreLabel, roundLabel;

    public GuessTheNumber() {
        setTitle("🎮 Guess the Number - Styled Game");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        BackgroundPanel background = new BackgroundPanel(new ImageIcon("gameposter1.jpg").getImage());
        background.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 15, 15, 15);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JPanel contentPanel = new JPanel(new GridBagLayout());
        contentPanel.setOpaque(false);  

        messageLabel = new JLabel("Guess a number between 1 and 100", SwingConstants.CENTER);
        messageLabel.setFont(new Font("Arial", Font.BOLD, 36));
        messageLabel.setForeground(Color.BLUE);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        contentPanel.add(messageLabel, gbc);

        inputField = new JTextField(10);
        inputField.setFont(new Font("Arial", Font.PLAIN, 30));
        inputField.setBackground(Color.WHITE);   
        inputField.setForeground(Color.BLACK);
        inputField.setCaretColor(Color.WHITE);
        inputField.setBorder(BorderFactory.createLineBorder(Color.ORANGE, 3)); 
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        contentPanel.add(inputField, gbc);

        guessButton = new JButton("Guess");
        styleButton(guessButton, new Color(255, 87, 34));
        gbc.gridx = 1;
        contentPanel.add(guessButton, gbc);

        newRoundButton = new JButton("New Round");
        styleButton(newRoundButton, new Color(76, 175, 80));
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        contentPanel.add(newRoundButton, gbc);

        attemptsLabel = new JLabel("Attempts Left: " + MAX_ATTEMPTS, SwingConstants.CENTER);
        attemptsLabel.setFont(new Font("Arial", Font.PLAIN, 28));
        attemptsLabel.setForeground(Color.CYAN);
        gbc.gridy = 3;
        contentPanel.add(attemptsLabel, gbc);

        scoreLabel = new JLabel("Score: 0", SwingConstants.CENTER);
        scoreLabel.setFont(new Font("Arial", Font.PLAIN, 28));
        scoreLabel.setForeground(Color.GREEN);
        gbc.gridy = 4;
        contentPanel.add(scoreLabel, gbc);

        roundLabel = new JLabel("Round: 1", SwingConstants.CENTER);
        roundLabel.setFont(new Font("Arial", Font.PLAIN, 28));
        roundLabel.setForeground(Color.MAGENTA);
        gbc.gridy = 5;
        contentPanel.add(roundLabel, gbc);

        background.add(contentPanel);
        add(background, BorderLayout.CENTER);

        // Button Actions
        guessButton.addActionListener(this);
        newRoundButton.addActionListener(e -> startNewRound());

        startNewGame();
        setVisible(true);
    }

    private void styleButton(JButton button, Color bgColor) {
        button.setFont(new Font("Arial", Font.BOLD, 30));
        button.setBackground(bgColor);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        // Hover effect
        button.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                button.setBackground(bgColor.darker());
            }

            public void mouseExited(MouseEvent e) {
                button.setBackground(bgColor);
            }
        });
    }

    private void startNewGame() {
        score = 0;
        round = 1;
        startNewRound();
    }

    private void startNewRound() {
        Random rand = new Random();
        randomNumber = rand.nextInt(100) + 1;
        attemptsLeft = MAX_ATTEMPTS;
        messageLabel.setText("Guess a number between 1 and 100");
        attemptsLabel.setText("Attempts Left: " + attemptsLeft);
        roundLabel.setText("Round: " + round);
        inputField.setText("");
        inputField.setEditable(true);
        guessButton.setEnabled(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String userInput = inputField.getText();
        if (userInput.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a number!");
            return;
        }

        int guess;
        try {
            guess = Integer.parseInt(userInput);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid input! Enter a valid number.");
            return;
        }

        attemptsLeft--;
        attemptsLabel.setText("Attempts Left: " + attemptsLeft);

        if (guess == randomNumber) {
            int points = attemptsLeft * 10;
            score += points;
            scoreLabel.setText("Score: " + score);
            messageLabel.setText("Correct! You earned " + points + " points.");
            inputField.setEditable(false);
            guessButton.setEnabled(false);
        } else if (guess < randomNumber) {
            messageLabel.setText("Bigger than this !");
        } else {
            messageLabel.setText("Smaller than this !");
        }

        if (attemptsLeft == 0 && guess != randomNumber) {
            messageLabel.setText("Out of attempts! Number was: " + randomNumber);
            inputField.setEditable(false);
            guessButton.setEnabled(false);
        }
    }

    public static void main(String[] args) {

        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Exception ignored) {
        }

        SwingUtilities.invokeLater(GuessTheNumber::new);
    }
}


class BackgroundPanel extends JPanel {
    private final Image backgroundImage;

    public BackgroundPanel(Image img) {
        this.backgroundImage = img;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
    }
}
