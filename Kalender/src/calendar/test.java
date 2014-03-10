package calendar;


import java.awt.Component;
import java.awt.Rectangle;
import java.awt.geom.Ellipse2D;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.xml.crypto.Data;

import org.joda.time.DateTime;
import org.joda.time.DateTimeConstants;
import org.joda.time.LocalDate;

public class test extends JPanel {
	
	public test() {
		
	}
	
	public static void main(String[] args) throws SQLException {
		System.out.println(BCrypt.hashpw("asdf123", BCrypt.gensalt(12)));
		
	}

}
