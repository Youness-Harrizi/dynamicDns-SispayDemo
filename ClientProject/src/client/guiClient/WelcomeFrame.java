package client.guiClient;

import javax.swing.*;
import java.awt.*;

public class WelcomeFrame extends JFrame {
    private JLabel text1;
    private JLabel text2;
    private JLabel text3;
    public static Font font=new Font("Calibri",Font.ITALIC,24);

    public WelcomeFrame(Boolean successful){
        super("Welcome client ") ; // Window title
        this.setSize(600,200);
        this.setLocationRelativeTo(null);
        this.setLayout(new BorderLayout());

        setBackground(Color.BLUE);
        text1=new JLabel("                                            Welcome Client ....");
        text1.setFont(font);

        text2=new JLabel("                                    The communication with the server was successful ....");
        text3=new JLabel("                                  Unfortunately something went wrong in the communication ....\n please retry another time ....");
        if(successful){
            this.add(text1,BorderLayout.NORTH);
            this.add(text2,BorderLayout.CENTER);
        }
        else{
            this.add(text1,BorderLayout.NORTH);
            this.add(text3,BorderLayout.CENTER);
        }
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE) ; // Explicit !

       // pack() ;            // Components sizes and positions

    }
}
