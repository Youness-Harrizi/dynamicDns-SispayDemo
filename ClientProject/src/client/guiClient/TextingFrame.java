package client.guiClient;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TextingFrame extends JFrame {
    private JButton submit;
    private JTextField field;
    private JLabel label;
    private String text="";
    public final Font font=new Font("Algerian",Font.BOLD,15);

    public String getText() {
        return text;
    }

    public TextingFrame(){
        super("SEND MESSAGES TO THE SERVER ");
        this.label=new JLabel("send Messages to The server...      Press quit to exit ");
        label.setFont(font);
        setSize(800,400);
        setLocation(new Point(300,200));
        // no layout
        setLayout(null);
        setResizable(true);
        // setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        initComponent();
        initEvent();


    }
    private void initComponent(){
      field=new JTextField();
      field.setBounds(200,100,300,40);
      submit=new JButton("submit");
      submit.setBounds(280,200, 140,50);
      label.setBounds(150,10,500,100);
      add(submit);
      add(field);
      add(label);

    }
    private void initEvent(){
        submit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                text=field.getText();
                //System.out.println("Input has changed");
            }
        });

    }


    // Test Test
    public static void main(String[] args) {
        TextingFrame textingFrame=new TextingFrame();
        textingFrame.setVisible(true);
    }
}
