package bank.management.system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class BalanceEnquiry extends JFrame implements ActionListener {

    JButton back;
    String pinnumber;

    BalanceEnquiry(String pinnumber) {
        this.pinnumber = pinnumber;
        setLayout(null);

        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icons/atm.jpg"));
        Image i2 = i1.getImage().getScaledInstance(900, 900, Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel image = new JLabel(i3);
        image.setBounds(0, 0, 900, 900);
        add(image);

        back = new JButton("Back");
        back.setBounds(355, 520, 150, 30);
        back.addActionListener(this);
        image.add(back);

        int balance = 0;

        try {
            Conn c = new Conn();
            ResultSet rs = c.s.executeQuery("SELECT * FROM bank WHERE pin = '" + pinnumber + "'");

            while (rs.next()) {
                String type = rs.getString("type");
                int amt = Integer.parseInt(rs.getString("amount"));

                if (type.equalsIgnoreCase("Deposit")) {
                    balance += amt;
                } else {
                    balance -= amt;
                }
            }

        } catch (Exception e) {
            System.out.println(e);
        }

        JLabel text = new JLabel("Your Current Account Balance is Rs " + balance);
        text.setForeground(Color.WHITE);
        text.setFont(new Font("System", Font.BOLD, 16));
        text.setBounds(170, 300, 400, 30);
        image.add(text);

        setSize(900, 900);
        setLocation(300, 0);
        setUndecorated(true);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        setVisible(false);
        new Transactions(pinnumber).setVisible(true);
    }

    public static void main(String args[]) {
        new BalanceEnquiry("");
    }
}
