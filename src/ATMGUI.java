import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ATMGUI extends JFrame implements ActionListener {
    private JPanel pnlLogin = new JPanel();
    private JPanel pnlChoice = new JPanel();
    private JLabel lbAccountNumber = new JLabel("Account Number",SwingConstants.CENTER);
    private JTextField tfAccountNumber = new JTextField(10);
    private JLabel lbPIN = new JLabel("PIN",SwingConstants.CENTER);
    private JPasswordField pfPIN = new JPasswordField(10);
    private JLabel lbLogin = new JLabel("Login");
    private JLabel lbExit = new JLabel("Exit");
    private JButton bt1 = new JButton();
    private JButton bt2 = new JButton();
    private JButton bt3 = new JButton();
    private JButton bt4 = new JButton();
    private JButton bt5 = new JButton();
    private JButton bt6 = new JButton();
    private JButton bt7 = new JButton();
    private JButton bt8 = new JButton();

    public ATMGUI() {
        super("ATM");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(null);
        this.add(bt1);
        this.add(bt2);
        this.add(bt3);
        this.add(bt4);
        this.add(bt5);
        this.add(bt6);
        this.add(bt7);
        this.add(bt8);
        this.setSize(500, 320);
        this.bt1.setBounds(0,120,30,30);
        this.bt2.setBounds(470,120,30,30);
        this.bt3.setBounds(0,170,30,30);
        this.bt4.setBounds(470,170,30,30);
        this.bt5.setBounds(0,220,30,30);
        this.bt6.setBounds(470,220,30,30);
        this.bt7.setBounds(0,270,30,30);
        this.bt8.setBounds(470,270,30,30);
        this.pnlLogin.setBounds(50,10,400,200);
        this.pnlLogin.setBackground(Color.blue);
        this.pnlLogin.add(lbAccountNumber);
        this.pnlLogin.add(tfAccountNumber);
        this.pnlLogin.add(lbPIN);
        this.pnlLogin.add(pfPIN);
        this.lbAccountNumber.setBounds(150,110,100,50);
        this.tfAccountNumber.setBounds(250,110,100,50);
        this.lbPIN.setBounds(150,160,100,50);
        this.pfPIN.setBounds(250,160,100,50);
        this.pnlChoice.setBounds(50,210,400,100);
        this.pnlChoice.setBackground(Color.blue);
        this.pnlChoice.add(lbLogin);
        this.lbLogin.setBounds(100,2701,00,50);
        this.pnlChoice.add(lbExit);
        this.lbExit.setBounds(200,270,100,50);
        this.lbAccountNumber.setForeground(Color.WHITE);
        this.lbAccountNumber.setFont(new Font("Times New Roman", Font.BOLD, 24));
        this.lbPIN.setForeground(Color.WHITE);
        this.lbPIN.setFont(new Font("Times New Roman", Font.BOLD, 24));
        this.lbLogin.setForeground(Color.WHITE);
        this.lbLogin.setFont(new Font("Times New Roman", Font.BOLD, 18));
        this.lbExit.setForeground(Color.WHITE);
        this.lbExit.setFont(new Font("Times New Roman", Font.BOLD, 18));
        this.add(pnlLogin);
        this.add(pnlChoice);
        this.setVisible(true);
        this.setLocationRelativeTo(null);
        bt7.addActionListener(this);
        bt8.addActionListener(this);
    }
    //Login
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == bt8) {
            JOptionPane.showMessageDialog(this, "Thank you for your use");
            System.exit(0);
        } else {
            int AccountNumber = Integer.parseInt(tfAccountNumber.getText());
            int PIN = Integer.parseInt(new String(pfPIN.getPassword()));

            // Perform authentication and validate user
            BankDatabase bankDatabase = new BankDatabase();
            boolean isAuthenticated = bankDatabase.authenticateUser(AccountNumber, PIN);

         
            if (isAuthenticated) {
                JOptionPane.showMessageDialog(this, "Login successful");
                this.dispose();
                // Call the next frame or perform further actions
            } else {
                JLabel lbError = new JLabel("Invalid account number or PIN. Please try again.");
                lbError.setBackground(Color.red);
                lbError.setForeground(Color.WHITE);
                lbError.setBounds(50,10,400,30);
                lbError.setFont(new Font("Times New Roman", Font.BOLD, 24));
                add(lbError);
            }
        }
    }


    public static void main(String[] args) {
        new ATMGUI();
    }
}
    