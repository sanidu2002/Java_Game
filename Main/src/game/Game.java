package game;
import javax.swing.*;
import java.awt.*;
import java.util.Objects;
import java.util.Random;

public class Game {

    JFrame frame = new JFrame();
    JLabel textLabel = new JLabel();
    JLabel timerLabel = new JLabel();
    JPanel textPanel = new JPanel();

    JButton[] board = new JButton[9];
    JPanel boardPanel = new JPanel();

    ImageIcon animeIcon;
    ImageIcon hammerIcon;

    JButton currentAnimeTile;

    Random random = new Random();

    Timer setAnimeTimer;
    Timer hideAnimeTimer;
    Timer gameTimer;
    
    int timeLeft = 60;
    boolean gameActive = true;



    public Game() {
        //JFrame

        frame.setSize(600, 650);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("My Game");
        frame.setLayout(new BorderLayout());

        //JLabel
        textLabel.setText("Score: 00");
        textLabel.setFont(new Font("Arial", Font.PLAIN, 50));
        
        timerLabel.setText("Time: 60s");
        timerLabel.setFont(new Font("Arial", Font.PLAIN, 40));
        timerLabel.setForeground(Color.RED);
        
        textPanel.setLayout(new FlowLayout());
        textPanel.add(textLabel);
        textPanel.add(timerLabel);
        frame.add(textPanel, BorderLayout.NORTH);

        //Image Icon
        Image animeImg = new ImageIcon(Objects.requireNonNull(getClass().getResource("./animal.png"))).getImage();
        animeIcon = new ImageIcon(animeImg.getScaledInstance(150, 150,java.awt.Image.SCALE_SMOOTH));

        Image hammerIMG = new ImageIcon(Objects.requireNonNull(getClass().getResource("./hammer.png"))).getImage();
        hammerIcon = new ImageIcon(hammerIMG.getScaledInstance(150, 150, java.awt.Image.SCALE_SMOOTH));

        //button
        boardPanel.setLayout(new GridLayout(3, 3));
        frame.add(boardPanel, BorderLayout.CENTER);

        for (int i = 0; i < 9; i++) {
            JButton title = new JButton();
            board[i] = title;
            boardPanel.add(title);
            title.setFocusPainted(false);
            title.setIcon(hammerIcon);
            
            title.addActionListener(e -> {
                if (gameActive && title.getIcon() == animeIcon) {
                    title.setIcon(hammerIcon);
                    int score = Integer.parseInt(textLabel.getText().replaceAll("[^0-9]", ""));
                    textLabel.setText("Score: " + (score + 10));
                }
            });




        }

        //timer - Show animal at random position
        setAnimeTimer = new Timer(1000, event -> {
            if (gameActive) {
                // Hide previous animal if any
                if(currentAnimeTile != null){
                    currentAnimeTile.setIcon(hammerIcon);
                }
                
                // Show animal at random position
                int randomIndex = random.nextInt(9);
                currentAnimeTile = board[randomIndex];
                currentAnimeTile.setIcon(animeIcon);
            }
        });
        
        //timer - Hide animal after 800ms
        hideAnimeTimer = new Timer(800, event -> {
            if(currentAnimeTile != null && gameActive){
                currentAnimeTile.setIcon(hammerIcon);
            }
        });
        
        //timer - 60 second countdown timer
        gameTimer = new Timer(1000, event -> {
            timeLeft--;
            timerLabel.setText("Time: " + timeLeft + "s");
            
            if (timeLeft <= 0) {
                gameActive = false;
                setAnimeTimer.stop();
                hideAnimeTimer.stop();
                gameTimer.stop();
                
                // Hide the current animal if any
                if(currentAnimeTile != null){
                    currentAnimeTile.setIcon(hammerIcon);
                }
                
                // Get final score
                int finalScore = Integer.parseInt(textLabel.getText().replaceAll("[^0-9]", ""));
                
                // Show game over dialog
                JDialog gameOverDialog = new JDialog(frame, "Game Over!", true);
                gameOverDialog.setSize(300, 200);
                gameOverDialog.setLocationRelativeTo(frame);
                gameOverDialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
                
                JPanel dialogPanel = new JPanel(new BorderLayout());
                dialogPanel.setBackground(Color.WHITE);
                
                JLabel scoreLabel = new JLabel("Final Score: " + finalScore);
                scoreLabel.setFont(new Font("Arial", Font.BOLD, 30));
                scoreLabel.setHorizontalAlignment(JLabel.CENTER);
                
                JButton restartButton = new JButton("Restart Game");
                restartButton.setFont(new Font("Arial", Font.PLAIN, 18));
                restartButton.addActionListener(restartEvent -> {
                    gameOverDialog.dispose();
                    frame.dispose();
                    new Game();
                });
                
                dialogPanel.add(scoreLabel, BorderLayout.CENTER);
                dialogPanel.add(restartButton, BorderLayout.SOUTH);
                gameOverDialog.add(dialogPanel);
                gameOverDialog.setVisible(true);
            }
        });
        
        hideAnimeTimer.start();
        setAnimeTimer.start();
        gameTimer.start();

        frame.setVisible(true);
    }
    
    public static void main(String[] args) {
        new Game();
    }
}
