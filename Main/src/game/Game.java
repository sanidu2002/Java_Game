package game;
import javax.swing.*;
import java.awt.*;
import java.util.Objects;
import java.util.Random;

public class Game {

    JFrame frame = new JFrame();
    JLabel textLabel = new JLabel();
    JPanel textPanel = new JPanel();

    JButton[] board = new JButton[9];
    JPanel boardPanel = new JPanel();

    ImageIcon animeIcon;
    ImageIcon hammerIcon;

    JButton currentAnimeTile;

    Random random = new Random();

    Timer setAnimeTimer;
    Timer hideAnimeTimer;




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
        textLabel.setFont(new Font("Arial", Font.PLAIN,50));
        textPanel.add(textLabel);
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
                if (title.getIcon() == animeIcon) {
                    title.setIcon(hammerIcon);
                    int score = Integer.parseInt(textLabel.getText().replaceAll("[^0-9]", ""));
                    textLabel.setText("Score: " + (score + 10));
                }
            });




        }

        //timer - Show animal at random position
        setAnimeTimer = new Timer(1000, event -> {
            // Hide previous animal if any
            if(currentAnimeTile != null){
                currentAnimeTile.setIcon(hammerIcon);
            }
            
            // Show animal at random position
            int randomIndex = random.nextInt(9);
            currentAnimeTile = board[randomIndex];
            currentAnimeTile.setIcon(animeIcon);
        });
        
        //timer - Hide animal after 800ms
        hideAnimeTimer = new Timer(800, event -> {
            if(currentAnimeTile != null){
                currentAnimeTile.setIcon(hammerIcon);
            }
        });
        hideAnimeTimer.start();
        setAnimeTimer.start();

        frame.setVisible(true);
    }
    
    public static void main(String[] args) {
        new Game();
    }
}
