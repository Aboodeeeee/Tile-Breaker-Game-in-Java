package Project;

import java.awt.event.KeyListener;
import java.util.*;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.ActionListener;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Font;

import javax.swing.Timer;
import java.awt.Graphics2D;




public class gameplay extends JPanel implements KeyListener, ActionListener{
    private boolean play = false;
    private int score = 0;

    private int totalBricks = 21;

    private Timer timer;
    private int delay = 4;

    private int playerX = 310;

    private int ballposX = 120;
    private int ballposY = 350;
    private int ballXdir = -1;
    private int ballYdir = -2;
    private mapGenerator map;

    public gameplay(){
        map = new mapGenerator(3,7);
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        timer =new Timer(delay, this);
        timer.start();
    }

    public void paint(Graphics g){
        // background
        g.setColor(Color.BLACK);
        g.fillRect(1, 1, 692, 592);

        // drawing bricks
        map.draw((Graphics2D)g);

        // borders
        g.setColor(Color.blue);
        g.fillRect(0, 0, 3, 592);
        g.fillRect(0, 0, 692, 3);
        g.fillRect(691, 0, 3, 592);

        // score
        g.setColor(Color.blue);
        g.setFont(new Font("TimesRoman", Font.BOLD, 25));
        g.drawString("" + score, 590, 30);
        // the padle
        g.setColor(Color.white);
        g.fillRect(playerX, 550, 100, 8);

        // the ball
        g.setColor(Color.blue);
        g.fillOval(ballposX, ballposY, 20, 20);

        // if competed 
        if(totalBricks <= 0){
            play = false;
            ballXdir = 0;
            ballYdir = 0;

            g.setColor(Color.RED);
            g.setFont(new Font("TimesRoman", Font.BOLD, 30));
            g.drawString("You Won!", 280, 300);
            
            g.setFont(new Font("TimesRoman", Font.BOLD, 20));
            g.drawString("- Press Enter to Restart -", 230, 330);
        }
        // if ball goes out of bounds 
        if(ballposY > 570){
            play = false;
            ballXdir = 0;
            ballYdir = 0;
            g.setColor(Color.RED);
            g.setFont(new Font("TimesRoman", Font.BOLD, 30));
            g.drawString("Game Over!", 260, 300);
            
            g.setFont(new Font("TimesRoman", Font.BOLD, 30));
            g.drawString("Score: "+ score, 275, 330);
            
            g.setFont(new Font("TimesRoman", Font.BOLD, 20));
            g.drawString("- Press Enter to Restart -", 230, 360);
        }

        g.dispose();
    }


    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode()==KeyEvent.VK_LEFT){
            if(playerX >= 600){
                playerX = 600;
            }
            else{
                moveRight();
            }
        }
        if(e.getKeyCode()==KeyEvent.VK_RIGHT){
            if(playerX < 10){
                playerX = 10;
            }
            else{
                moveLeft();
            }
        }
        if(e.getKeyCode() == KeyEvent.VK_ENTER){
            if(!play){
                play = true;
                ballXdir = -1;
                ballYdir = -2;
                ballposX = 120;
                ballposY = 350;
                playerX = 310;
                score = 0;
                totalBricks = 21;
                map = new mapGenerator(3,7);

                repaint();
            }
        }
    }

    public void moveLeft(){
        play = true;
        playerX -= 20;
    }
    public void moveRight(){
        play=true;
        playerX += 20;
    }

    @Override
    public void keyReleased(KeyEvent e) {}
    @Override
    public void actionPerformed(ActionEvent e) {
        timer.start();

        if(play){

            if(new Rectangle(ballposX, ballposY, 20, 20).intersects(new Rectangle(playerX, 550, 100, 8))){
                ballYdir = -ballYdir;
            }
            // to gothru all the bricks ...so we create loop
            A: for(int i = 0; i < map.map.length; i++){
                for(int j = 0; j < map.map[0].length; j++){
                    if(map.map[i][j] == 1){
                        if(map.map[i][j] > 0){
                            // for instersectionfirst we gotta detect the pos of ball and brick 
                            int brickX = j*map.brickWidth + 80;
                            int brickY = i*map.brickHeight + 50;
                            int brickWidth = map.brickWidth;
                            int brickHeight = map.brickHeight;
                            // rectangle to detect brick
                            Rectangle rect = new Rectangle(brickX, brickY, brickWidth, brickHeight);
                            // rectangle to detect ball 
                            Rectangle ballRect = new Rectangle(ballposX, ballposY, 20 ,20);

                            Rectangle brickRect = rect;
                            // checing if ball intersects brick 
                            if(ballRect.intersects(brickRect)) {
                                map.setBrickValue(0, i, j);
                                totalBricks--;
                                score+=5;
                                if(ballposX + 19 <= brickRect.x || ballposX + 1 >= brickRect.x + brickRect.width){
                                     ballXdir = -ballXdir;
                                }
                                else{
                                     ballYdir = -ballYdir;
                                }

                                break A;
                            }
                        }
                    }
                }
            }

            ballposX += ballXdir;
            ballposY += ballYdir;

            if(ballposX <0){
                ballXdir = -ballXdir;
            }
            if(ballposY <0){
                ballYdir = -ballYdir;
            }
            if(ballposX > 670){
                ballXdir = -ballXdir;
            }
        }

        repaint();
    }
}