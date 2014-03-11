package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JPanel;

public class EventPanel extends JPanel implements ActionListener{
	
	ArrayList<JButton> btns = new ArrayList<JButton>();
	
	public EventPanel() {
		setOpaque(true);
		JButton btn = new JButton("hei");
		btn.addActionListener(this);
		add(btn);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		System.out.println("hei");
		
	}

}
