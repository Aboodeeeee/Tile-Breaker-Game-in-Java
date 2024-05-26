package Project;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

public class UserHome extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    UserHome frame = new UserHome();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public UserHome() {

    }

    /**
     * Create the frame.
     */
    public UserHome(String userSes) {

    	// creates frame by creating obj of jframe
        JFrame obj = new JFrame();
        // creating object of gameplay
        gameplay gamePlay = new gameplay();

        // setting propreties of JFrame
        obj.setBounds(10,10,710,610);
        obj.setTitle("Tile Breaker Game");
        obj.setResizable(false);
        obj.setVisible(true);
        obj.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        obj.add(gamePlay);
}}
