package com.Aditya;

/*****************************************************************************************************
 * @author  Aditya Tripuraneni
 * @version 1.00, 4 Febuary 2022
 * Description:  This is intended as a review of the concepts learned in the grade 11 course.  There
 *     will be loops, if structures, data types, and some object-oriented concepts.  This game is
 *     not written with objects interacting with objects, but takes a less object-oriented approach
 *     to focus on the basic concepts especially two-dimensional arrays.
 *
 *****************************************************************************************************/

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;


/************************************************************************
 * The PacMan class
 * This class contains all code to run the Pacman game.
 * Once class is instantiated, game begins with call to init method.
 *************************************************************************/
class PacMan extends JFrame implements KeyListener {
    static final long serialVersionUID = 2;
    BufferedImage buffer;


    /*
        Pacman map
     */
    char[][] map =
                   {{'X' ,  'X' ,  'X' ,  'X' ,  'X' ,  'X' ,  'X' ,  'X' ,  'X' ,  'X' ,  'X' ,  'X' ,  'X' ,  'X' ,  'X'},
                    {'X' ,  'P' ,  '*' ,  '*' ,  '*' ,  '*' ,  'X' ,  '*' ,  '*' ,  '*' ,  '*' ,  '*' ,  '*' ,  'S' ,  'X'},
                    {'X' ,  '*' ,  'X' ,  '*' ,  'X' ,  'X' ,  'X' ,  'X' ,  '*' ,  '*' ,  '*' ,  '*' ,  '*' ,  '*' ,  'X' },
                    {'X' ,  '*' ,  'X' ,  '*' ,  '*' ,  '*' ,  'X' ,  '*' ,  '*' ,  '*' ,  '*' ,  '*' ,  '*' ,  '*' ,  'X' },
                    {'X' ,  '*' ,  'X' ,  '*' ,  '*' ,  '*' ,  'X' ,  'S' ,  '*' ,  '*' ,  '*' ,  '*' ,  'X' ,  'X' ,  'X' },
                    {'X' ,  '*' ,  '*' ,  '*' ,  '*' ,  '*' ,  '*' ,  '*' ,  '*' ,  'X' ,  '*' ,  '*' ,  '*' ,  '*' ,  'X' },
                    {'X' ,  '*' ,  '*' ,  '*' ,  '*' ,  '*' ,  '*' ,  '*' ,  '*' ,  'X' ,  '*' ,  '*' ,  '*' ,  '*' ,  'X' },
                    {'X' ,  'X' ,  'X' ,  '*' ,  '*' ,  '*' ,  '*' ,  '*' ,  '*' ,  'X' ,  '*' ,  '*' ,  '*' ,  '*' ,  'X' },
                    {'X' ,  '*' ,  'X' ,  '*' ,  'X' ,  'X' ,  '-' ,  '-' ,  'X' ,  'X' ,  '*' ,  '*' ,  '*' ,  '*' ,  'X' },
                    {'X' ,  '*' ,  'X' ,  '*' ,  'X' ,  '1' ,  '2' ,  '3' ,  '4' ,  'X' ,  '*' ,  '*' ,  'X' ,  '*' ,  'X' },
                    {' ' ,  '*' ,  'X' ,  '*' ,  'X' ,  'X' ,  'X' ,  'X' ,  'X' ,  'X' ,  '*' ,  '*' ,  '*' ,  '*' ,  ' ' },
                    {'X' ,  '*' ,  '*' ,  '*' ,  '*' ,  '*' ,  '*' ,  '*' ,  '*' ,  '*' ,  '*' ,  '*' ,  'X' ,  '*' ,  'X' },
                    {'X' ,  '*' ,  'X' ,  '*' ,  '*' ,  '*' ,  '*' ,  'X' ,  '*' ,  '*' ,  '*' ,  '*' ,  'X' ,  '*' ,  'X' },
                    {'X' ,  '*' ,  '*' ,  '*' ,  '*' ,  '*' ,  '*' ,  'X' ,  '*' ,  'X' ,  '*' ,  '*' ,  'X' ,  '*' ,  'X' },
                    {'X' ,  'X' ,  '*' ,  'X' ,  'X' ,  '*' ,  'X' ,  'X' ,  '*' ,  'X' ,  'X' ,  '*' ,  'X' ,  'X' ,  'X' },
                    {'X' ,  '*' ,  '*' ,  '*' ,  '*' ,  '*' ,  '*' ,  '*' ,  '*' ,  '*' ,  '*' ,  '*' ,  '*' ,  '*' ,  'X' },
                    {'X' ,  '*' ,  'X' ,  '*' ,  '*' ,  '*' ,  '*' ,  'X' ,  '*' ,  '*' ,  '*' ,  '*' ,  'X' ,  '*' ,  'X' },
                    {'X' ,  'X' ,  'X' ,  'X' ,  'X' ,  '*' ,  'X' ,  'X' ,  '*' ,  'X' ,  'X' ,  '*' ,  'X' ,  'X' ,  'X' },
                    {'X' ,  '*' ,  '*' ,  '*' ,  '*' ,  '*' ,  '*' ,  'X' ,  '*' ,  '*' ,  '*' ,  '*' ,  '*' ,  '*' ,  'X' },
                    {'X' ,  'S' ,  'X' ,  '*' ,  '*' ,  '*' ,  '*' ,  'X' ,  '*' ,  '*' ,  '*' ,  '*' ,  'X' ,  'S' ,  'X' },
                    {'X' ,  'X' ,  'X' ,  'X' ,  'X' ,  'X' ,  'X' ,  'X' ,  'X' ,  'X' ,  'X' ,  'X' ,  'X' ,  'X' ,  'X' }};
    /*
        Values needed for pacman
        Contains lives points, direction and shape
     */
    char pacmanShape = 'R';
    char direction = ' ';
    int pacX = 1;
    int pacY = 1;
    int lives = 3;
    int points = 0;

    /*
        Ghost positions
     */
    int ghostOneX = 9;
    int ghostOneY = 5;

    /*
        Time tracker
    */
    long time ;
    long gameStart = System.currentTimeMillis();

    /*
        States of game
    */
    boolean escape = false;
    boolean run = true;

    Random randNum = new Random();

    /*
        Board size
     */
    final int WIDTH = map[0].length * 30, HEIGHT = map.length * 30 + 40;
    JPanel boardPanel;
    JFrame frame;

    /***********************************************
     * PacmMan Constructor <BR>
     *     Allows to PacMan object to be created
     ***********************************************/
    public PacMan()
    {
        super("Pacman game"  );
        buffer = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
        boardPanel = new JPanel();
        boardPanel.setDoubleBuffered(true);
        boardPanel.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        boardPanel.setMinimumSize(new Dimension(WIDTH, HEIGHT));

        JPanel pane = (JPanel) getContentPane();

        addKeyListener(this);
        pane.add(boardPanel);

        pane.setDoubleBuffered(true);
        setSize(WIDTH + 40, HEIGHT + 40);
        pack();

        setLocationRelativeTo(null);
        setVisible(true);
    }


    /****************************************************************
     * METHOD: drawBoard() <BR>
     *     Read data from array and display contents to screen
     *****************************************************************/
    public void drawBoard()
    {
        Graphics2D b = buffer.createGraphics();
        BufferedImage boardImage = null;
        b.setColor(Color.black);
        b.fillRect(0, 25, WIDTH, HEIGHT);

        for (int row = 0; row < map.length; row++) {
            for (int column = 0; column < map[row].length; column++)
            {
                if (map[row][column] == 'X')
                {
                    b.setColor(new Color(173, 216, 230));
                    b.fillRect((column) * 30, (row) * 30, 30, 30);
                }
                else if (map[row][column] == '*') { //point
                    b.setColor(new Color(50, 205, 50));
                    b.fillOval((column) * 30 + 9, (row) * 30 + 9, 10, 10);
                }
                else if (map[row][column] == '-')
                {
                    b.setColor(new Color(255, 215, 0));
                    b.setStroke(new BasicStroke(6));
                    b.drawLine(column * 30 + 3, row * 30 + 12, column * 30 + 27, row * 30 + 12);
                }
                else if (map[row][column] == 'S')
                    addPowerUp(b, map[row][column], column * 30, row * 30);
                else if (map[row][column] >= '1' && map[row][column] <= '6')
                {
                    addGhost(b, map[row][column] , column * 30 , row* 30);
                    if (map[row][column] == '1')
                    {
                        ghostOneX = row; ghostOneY = column;
                    }
                }
                else if (map[row][column] == 'P')
                {
                    pacX = row; pacY = column; int pacSize = 24;
                    b.setColor(Color.yellow);
                    if (System.currentTimeMillis() - time <= 5000)
                    {
                        b.setColor(Color.CYAN);
                        pacSize = 30;
                    }
                    if (pacmanShape == 'O')
                        b.fillArc((column) * 30 + 3, (row) * 30 + 3, pacSize, pacSize, 0, 365);
                    else if (pacmanShape == 'R')
                        b.fillArc((column) * 30 + 3, (row) * 30 + 3, pacSize, pacSize, 45, 270);
                    else if (pacmanShape == 'L')
                        b.fillArc((column) * 30 + 3, (row) * 30 + 3, pacSize, pacSize, 180+45, 270);
                    else if (pacmanShape == 'U')
                        b.fillArc((column) * 30 + 3, (row) * 30 + 3, pacSize, pacSize, 90+45, 270);
                    else if (pacmanShape == 'D')
                        b.fillArc((column) * 30 + 3, (row) * 30 + 3, pacSize, pacSize, 270+45, 270);
                }
            }
        }
        b.setColor(new Color(247, 112, 21));
        b.setFont(new Font("Monospaced", Font.ROMAN_BASELINE , 30));
        b.drawString("SCORE: " + points, 10, HEIGHT - 10);

        b.setColor(new Color(244, 13, 24));
        b.setFont(new Font("Monospaced", Font.BOLD, 30));
        b.drawString("LIVES: " + lives, WIDTH- 200, HEIGHT - 10);


        b.drawImage(boardImage, 0, 15, this);
        b.dispose();
        drawScreen();
    }


    /*******************************************************
     * METHOD: addGhost() <Br>
     *     Draws ghost onto screen
     * @Params: Graphics2D b <Br>
     *     Takes object of Graphics2D
     * @Param: char ghost <Br>
     *     Takes in symbol of ghost
     * @Param: int x <Br>
     *     Takes column of where ghost is located
     * @Param: int y <Br>
     *     Takes row of where ghost is located
     *******************************************************/
    public void addGhost(Graphics2D b, char ghost, int x, int y){
        BufferedImage img = null;
        Image newImg;
        try{
            if (ghost == '1')
                img = ImageIO.read(new File("images/blueGhostResized.png"));
            else if (ghost == '2')
                img =  ImageIO.read(new File("images/yellowGhostResized.png"));
            else if (ghost == '3')
                img =  ImageIO.read(new File("images/pinky.png"));
            else if (ghost == '4')
                img =  ImageIO.read(new File("images/redGhostResized.png"));

            newImg = img.getScaledInstance(30, 30, Image.SCALE_DEFAULT);
            b.drawImage(newImg, x+3, y,this);
        }
        catch (Exception e){
            e.printStackTrace();
            System.out.println("Run time error when adding ghost picture.. " + ghost + " not found or read properly");
            System.out.println("Make sure images are in same folder as java");
            System.exit(0);
        }
    }


    /*******************************************************
     * METHOD: playSFX() <BR>
     *     Plays desired SFX by specifying path
     * @Params: String pathway <BR>
     *     pathway will take the pathway of the .wav file
     *******************************************************/
    public void playSFX(String pathway){
        try {
            File audioFile = new File (pathway);
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);
            Clip clip = AudioSystem.getClip();
            clip.open(audioStream);
            clip.start();
        }
        catch (Exception e){
            e.printStackTrace();
            System.out.println("Run time error when adding audio file " + pathway);
            System.out.println("Make sure audio file is in .wav format and path is specified properly.");
        }
    }


    /*******************************************************
     * METHOD: addPowerUp() <BR>
     *     Draws the power up to screen
     * @Params: b <Br>
     *     Takes Graphics2D object
     * @Params: symbol <Br>
     *     Takes symbol of power-up on map
     * @Params: symbolX <Br>
     *     Takes column
     * @Params: symbolY <Br>
     *     Takes row
     *******************************************************/
    public void addPowerUp(Graphics2D b, char symbol, int symbolX, int symbolY){
        BufferedImage img = null;
        Image newImg;
        try{
            if (symbol == 'S'){
                img = ImageIO.read(new File("images/powerFruit.jpg"));
            }
            newImg = img.getScaledInstance(30, 30, Image.SCALE_DEFAULT);
            b.drawImage(newImg, symbolX+3, symbolY,this);
        }
        catch (Exception e){
            e.printStackTrace();
            System.out.println("Run time error when adding symbol picture.. " + symbol + " not found or read properly");
            System.out.println("Make sure images are in same folder as java");
            System.exit(0);
        }
    }


    /*******************************************************
     * METHOD: drawScreen() <BR>
     *     Place picture from (memory) onto screen
     *******************************************************/
    public void drawScreen()
    {
        Graphics2D g = (Graphics2D) this.getGraphics();
        g.drawImage(buffer, 6, 30, this);
        Toolkit.getDefaultToolkit().sync();
        g.dispose();
    }


    /*******************************************************
     * METHOD: move() <BR>
     *     Moves pacman through keyboard input,
     *     detects collision with ghost and power-ups
     *******************************************************/
    public void move()
    {
        int row = 0, column = 0;

        if (pacmanShape == 'O')
            pacmanShape = direction;
        else
            pacmanShape = 'O';

        if (direction == 'R'){
            row = pacX;
            column = pacY + 1;
        }
        else if (direction == 'L')
        {
          row = pacX;
          column = pacY -1;
        }
        else if (direction == 'D'){
            row = pacX + 1;
            column = pacY;
        }
        else if (direction == 'U')
        {
          row = pacX - 1;
          column = pacY;
        }

        if (map[row][column] != 'X' && map[row][column] != '-')
        {
            if (map[row][column] == '*'){
                points++;
                playSFX("SFX/pacman_chomp.wav");
            }
            if (map[row][column] == 'S'){
                 time = System.currentTimeMillis();
                 playSFX("SFX/pacman_eatfruit.wav");
            }
            if (map[row][column] >= '1' && map[row][column] <= '6')
            {
                if (System.currentTimeMillis() - time <= 5000)
                {
                    points += 10;
                    playSFX("SFX/pacman_eatghost.wav");
                }
                else{
                    row = 1;
                    column = 1;
                    lives --;
                    playSFX("SFX/smb_gameover.wav");
                }
            }
            if (lives < 0)
            {
                points = 0;
                playSFX("SFX/smb_gameover.wav");
                run = false;
            }

            if (row == 10 && column == 0)
                column = 14;
            else if (row == 10 && column == 14)
                column = 0;
            map[pacX][pacY] = ' ';
            map[row][column] = 'P';
        }
    }


    /**********************************
     * METHOD: init() <BR>
     *     Runs the game
     ***********************************/
    public void init()
    {
        Graphics2D b = buffer.createGraphics();
        drawBoard();
        b.dispose();
        drawScreen();
        gameLoop();
    }


    /**********************************
     * METHOD: moveBlueGhost() <BR>
     *     Moves blue ghost randomly
     ***********************************/
    public void moveBlueGhost() {
        int row = ghostOneX, column = ghostOneY;

        if (System.currentTimeMillis() - gameStart >= 5000 && !escape)
        {
            char blueTemp = map[row - 2][column]; //holding onto the dot
            map[ghostOneX - 2][ghostOneY] = '1';
            map[ghostOneX][ghostOneY] = blueTemp;
            escape = true;
        }

        int direction = randNum.nextInt(4);

        if (escape)
        {
            if (direction == 0 && map[row][column + 1] != 'X') {
                char hold = map[row][column + 1];
                if (hold != 'P')
                {
                    map[row][column + 1] = '1';
                    map[ghostOneX][ghostOneY] = hold;
                }
            }
            else if (direction == 1 && map[row][column - 1] != 'X')
            {
                char hold = map[row][column - 1];
                map[row][column - 1] = '1';
                map[row][column] = hold;
            }
            else if (direction == 2 && map[row - 1][column] != 'X')
            {
                char hold = map[row - 1][column];
                map[row - 1][column] = '1';
                map[row][column] = hold;

            }
            else if (direction == 3 && map[row + 1][column] != 'X' && map[row + 1][column] != '-')
            {
                char hold = map[row + 1][column];
                map[row + 1][column] = '1';
                map[row][column] = hold;
            }

        }
    }


    /*************************************************************
     * METHOD: detectWinner() <BR>
     *     Detects when player wins game giving them option to play again or quit
     * ************************************************************/
    public void detectWinnerOrLoser(){
        int dotCount = 0;
        for (int row = 0; row < map.length; row ++){
            for (int col = 0; col < map[0].length; col ++){
                if (map[row][col] == '*')
                    dotCount++;
            }
        }

        if (dotCount <= 0)
        {
            frame = new JFrame();
            JOptionPane.showMessageDialog(frame, "Congratulations You Won!");
            int answer = JOptionPane.showConfirmDialog(frame, "Would you like to play again?", "WINNER", JOptionPane.YES_NO_OPTION);
            if (answer == 0)
            {
                playSFX("SFX/winnerSFX.wav");
                resetLevel();
            }
            else
                System.exit(0);
        }
        if (lives <= 0)
        {
            frame = new JFrame();
            JOptionPane.showMessageDialog(frame, "Your kinda bad at this game lol");
            int response = JOptionPane.showConfirmDialog(frame, "I suggest you quit cuz ure bad", ":(", JOptionPane.YES_NO_OPTION);
            if (response == 1)
            {
                playSFX("SFX/smb_gameover.wav");
                resetLevel();
            }
            else
                System.exit(0);
        }
    }


    /*************************************************************
     * METHOD: checkCageForDots() <BR>
     *     Removes any dots from cage when ghost moves
     * ************************************************************/
    public void checkCageForDots(){
        if (map[9][5] == '*'){
            map[9][5] = ' ';
        }
        else if (map[9][8] == '*'){
            map[9][8] = ' ';
        }
    }


    /**********************************************************************************************
     * METHOD: resetLevel() <BR>
     *     Resets the board, points, ghosts, direction, time, lives, pacman shape & escape state
     * ********************************************************************************************/
    public void resetLevel()
    {
        points = 0;
        lives = 3;
        direction = ' ';
        pacmanShape = 'R';
        escape = false;
        gameStart= System.currentTimeMillis();

        map = new char[][]{{'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X'},
                {'X', 'P', '*', '*', '*', '*', 'X', '*', '*', '*', '*', '*', '*', 'S', 'X'},
                {'X', '*', 'X', '*', 'X', 'X', 'X', 'X', '*', '*', '*', '*', '*', '*', 'X'},
                {'X', '*', 'X', '*', '*', '*', 'X', '*', '*', '*', '*', '*', '*', '*', 'X'},
                {'X', '*', 'X', '*', '*', '*', 'X', 'S', '*', '*', '*', '*', 'X', 'X', 'X'},
                {'X', '*', '*', '*', '*', '*', '*', '*', '*', 'X', '*', '*', '*', '*', 'X'},
                {'X', '*', '*', '*', '*', '*', '*', '*', '*', 'X', '*', '*', '*', '*', 'X'},
                {'X', 'X', 'X', '*', '*', '*', '*', '*', '*', 'X', '*', '*', '*', '*', 'X'},
                {'X', '*', 'X', '*', 'X', 'X', '-', '-', 'X', 'X', '*', '*', '*', '*', 'X'},
                {'X', '*', 'X', '*', 'X', '1', '2', '3', '4', 'X', '*', '*', 'X', '*', 'X'},
                {'X', '*', 'X', '*', 'X', 'X', 'X', 'X', 'X', 'X', '*', '*', '*', '*', 'X'},
                {'X', '*', '*', '*', '*', '*', '*', '*', '*', '*', '*', '*', 'X', '*', 'X'},
                {'X', '*', 'X', '*', '*', '*', '*', 'X', '*', '*', '*', '*', 'X', '*', 'X'},
                {'X', '*', '*', '*', '*', '*', '*', 'X', '*', 'X', '*', '*', 'X', '*', 'X'},
                {'X', 'X', '*', 'X', 'X', '*', 'X', 'X', '*', 'X', 'X', '*', 'X', 'X', 'X'},
                {'X', '*', '*', '*', '*', '*', '*', '*', '*', '*', '*', '*', '*', '*', 'X'},
                {'X', '*', 'X', '*', '*', '*', '*', 'X', '*', '*', '*', '*', 'X', '*', 'X'},
                {'X', 'X', 'X', 'X', 'X', '*', 'X', 'X', '*', 'X', 'X', '*', 'X', 'X', 'X'},
                {'X', '*', '*', '*', '*', '*', '*', 'X', '*', '*', '*', '*', '*', '*', 'X'},
                {'X', 'S', 'X', '*', '*', '*', '*', 'X', '*', '*', '*', '*', 'X', 'S', 'X'},
                {'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X'}};
    }


    /************************************************************************
     * METHOD: gameloop() <Br>
     *     Allows for game to run smoothly and update every frame
     *     Contains all of in game methods in order for game to run properly
     * ***********************************************************************/
    public void gameLoop()
    {
        while (run)
        {
            try {
                move();
                moveBlueGhost();
                detectWinnerOrLoser();
                checkCageForDots();
                drawBoard();
                if (System.currentTimeMillis() - time <= 5000)
                    Thread.sleep(100);
                else
                    Thread.sleep(500);
                }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    /********************************************
     * KEY LISTENER EVENTS
     ********************************************/


    /***********************************************
     * keyTyped Method <BR>
     *     Detects when key is typed
     * @Params: KeyEvent keyEvent
     ***********************************************/
    @Override
    public void keyTyped(KeyEvent keyEvent) {
    }


    /***********************************************
     * keyPressed Method <BR>
     *     Detects when key is pressed
     * @Params: KeyEvent keyEvent
     ***********************************************/
    @Override
    public void keyPressed(KeyEvent keyEvent) {
        int key = keyEvent.getKeyCode();
        if (key == KeyEvent.VK_RIGHT)
            direction = 'R';
        if (key == KeyEvent.VK_LEFT)
            direction = 'L';
        if (key == KeyEvent.VK_UP)
            direction = 'U';
        if (key == KeyEvent.VK_DOWN)
            direction = 'D';
    }


    /***********************************************
     * keyReleased Method <BR>
     *     Detects when key is released
     * @Params: KeyEvent keyEvent
     ***********************************************/
    @Override
    public void keyReleased(KeyEvent keyEvent) {
    }
}



/***********************************************************************************
 * The AdityaPacman class
 * This class allows Pacman to be instantied and the game can run within this class
 * Contains main method
 ***********************************************************************************/
public class AdityaPacman
{
    /***********************************************************
     * main method <BR>
     *     Instantiate Pacman class and calls init method
     * @Params: String[] args
     ***********************************************************/
    public static void main(String[] args){
        PacMan gameBoard = new PacMan();
        gameBoard.setLocationRelativeTo(null);
        gameBoard.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gameBoard.init();
    }
}
