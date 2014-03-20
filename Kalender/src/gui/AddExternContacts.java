package gui;
 
import java.awt.Color;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
 
public class AddExternContacts extends JFrame {
 
    private JPanel contentPane;
    private JTextArea txtContacts;
    private ArrayList<String> mailList;
 
    public AddExternContacts() {
        this.setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 326, 268);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);
        initGUI();
    }
         
    public void initGUI() {
        Border border = BorderFactory.createLineBorder(Color.BLACK);
        txtContacts = new JTextArea();
        txtContacts.setBounds(34, 60, 243, 130);
        txtContacts.setEditable(true);
        txtContacts.setBorder(border);
        contentPane.add(txtContacts);
        txtContacts.setColumns(10);
         
        JButton btnAddContacts = new JButton("Legg til kontakter");
        btnAddContacts.setBounds(34, 202, 161, 25);
        btnAddContacts.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                String s[] = txtContacts.getText().split("\\r?\\n");
                mailList = new ArrayList<String>(Arrays.asList(s)) ;
                dispose();
            }
        });
        contentPane.add(btnAddContacts);
         
        JButton btnReturn = new JButton("Tilbake");
        btnReturn.setBounds(214, 202, 63, 25);
        btnReturn.addActionListener(new ActionListener() {
             
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                 
            }
        });
        contentPane.add(btnReturn);
         
         
        JLabel lblHeader = new JLabel("Skriv inn eposten til deltagere:");
        lblHeader.setFont(new Font("Dialog", Font.BOLD, 14));
        lblHeader.setBounds(34, 12, 260, 36);
        contentPane.add(lblHeader);
         
    }
     
    public ArrayList<String> getMailList() {
        return mailList;
    }
}