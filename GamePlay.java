import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class GamePlay extends JPanel implements KeyListener, ActionListener {
    private boolean play = false; //game is on or not
    private int score = 0;
    private int totalBricks = 21;
    private Timer timer;
    private int delay = 8; //delay after losing the game
    private int playerX = 310; //platform X pos
    private int ballPosX = 120; //ball X pos
    private int ballPosY = 350; //ball Y pos
    private int ballXdir = -1;
    private int ballYdir = -2;
    private GenerateMap map;
    public GamePlay(){
        map = new GenerateMap(3,7); //brick map with 3 rows and 7 columns
        setFocusTraversalKeysEnabled(false);
        setFocusable(true);
        //focus traversal keys like TAB, SHIFT+TAB etc. can't be used to move to the next component
        addKeyListener(this); //added a key listener
        timer = new Timer(delay, this);
        timer.start();
    }
    //initialise the ball, the platform, bricks
    public void paint(Graphics g){
        //background
        g.setColor(Color.black);
        g.fillRect(1,1,692,592);

        //bricks
        map.makeBricks((Graphics2D) g); //typecasted the Graphics object to Graphics2d

        //border
        g.setColor(Color.yellow);
        g.fillRect(0,0,3,592);
        g.fillRect(0,0,692,3);
        g.fillRect(0,557,692,3);
        g.fillRect(680,0,3,592);


        //Score
        g.setColor(Color.white);
        g.setFont(new Font("serif",Font.BOLD,25));
        g.drawString(""+score, 590,30);

        //Platform
        g.setColor(Color.yellow);
        g.fillRect(playerX,550, 100,8);

        //Ball
        g.setColor(Color.green);
        g.fillOval(ballPosX,ballPosY,20,20);

        //ball touches the floor
        if(ballPosY > 570){
            play = false; //game is over
            ballXdir = -1;
            ballYdir = -2;
            g.setColor(Color.RED);
            g.setFont(new Font("serif",Font.BOLD,30));
            g.drawString("GAME OVER, SCORE = "+score,190,300);
            g.drawString("Press Enter to Restart",190,340);
        }

        //if all bricks are over
        if(totalBricks==0){
            play = false; //game is over
            ballXdir = -1;
            ballYdir = -2;
            g.setColor(Color.RED);
            g.setFont(new Font("serif",Font.BOLD,30));
            g.drawString("YOU WON, SCORE = "+score,190,300);
            g.drawString("Press Enter to Restart",190,340);
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_RIGHT){
            if(playerX >= 600)
                playerX = 600;
            else
                moveRight(); //platform should move right
        }
        if(e.getKeyCode() == KeyEvent.VK_LEFT){
            if(playerX <=10)
                playerX = 10;
            else
                moveLeft(); //platform should move left
        }
        if(e.getKeyCode() == KeyEvent.VK_ENTER){ //Restart the game
            if(!play){ //not playing
                ballPosX = 120;
                ballPosY = 350;
                ballXdir = -1;
                ballYdir = -2;
                score = 0;
                playerX = 310;
                totalBricks = 21;
                map = new GenerateMap(3,7);
                repaint(); //an inbuilt function which calls the paint function (you can write paint)
            }
        }
    }

    private void moveRight() {
        play = true;
        playerX += 20; //increase the position by 20
    }
    private void moveLeft() {
        play = true;
        playerX -= 20; //decrease the position by 20
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        timer.start();
        if(play){
            if(new Rectangle(ballPosX,ballPosY,20,20).intersects(new Rectangle(playerX,550,100,8))){
                //cheking for collision of ball with platform
                ballYdir = -ballYdir;
                ballXdir = ballXdir;
            }

            loops:
            for(int i=0;i<map.map.length;i++){
                for(int j=0;j<map.map[0].length;j++){
                    if(map.map[i][j] > 0){
                        int brickX = j * map.brickWidth + 80;
                        int brickY = i * map.brickHeight  + 50;
                        int brickWidth = map.brickWidth;
                        int brickHeight = map.brickHeight;
                        Rectangle brickRect = new Rectangle(brickX,brickY,brickWidth,brickHeight);
                        Rectangle ballRect = new Rectangle(ballPosX,ballPosY,20,20);
                        if(ballRect.intersects(brickRect)){ // collision of the ball with the brick
                            map.setValue(i,j,0);
                            totalBricks--;
                            score += 5;
                            //if ball hits brick horizontally from left or right side
                            if(ballPosX + 19 <= brickRect.x || ballPosX + 1 >= brickRect.x + brickWidth){
                                ballXdir = -ballXdir; //reverse the X direction of the ball
                            }
                            else
                                ballYdir = -ballYdir;
                            break loops; //come out of both the loops
                        }
                    }
                }
            }
            ballPosX += ballXdir;
            ballPosY += ballYdir;
            if(ballPosX < 0) //ball goes out of bounds on left
                ballXdir = -ballXdir;
            if(ballPosY < 0) //ball goes above the roof
                ballYdir = -ballYdir;
            if(ballPosX > 670) //ball goes out of bounds on right
                ballXdir = -ballXdir;
            repaint();
        }
    }
}
