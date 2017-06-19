package view;

import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import controller.PokupiSaForme;

public class Forma extends JFrame {

	final static String DATE_FORMAT = "dd-MM-yyyy";
	private JLabel lb1, lb2, lb3, lb4, lb5, lb6, lb7, lb8, lb9;
	private JTextField jtf1, jtf2, jtf3, jtf4, jtf5, jtf6, jtf7, jtf8;
	private JPasswordField jpf1, jpf2;
	private JPanel panel;
	private String s;
	private JLabel errorMsg = new JLabel("*Required");
	private JLabel errorMsg2 = new JLabel("*Required");
	private JLabel errorMsg3 = new JLabel("*Required");
	private JLabel errorMsg1 = new JLabel("*Required");
	private JButton submitButton = new JButton("Submit");

	public Forma(String s) {

		panel = new JPanel();
		panel.setLayout(new GridLayout(2, 10));

		setSize(550, 400);
		setLocationRelativeTo(null);
		setTitle("Certificate details");
		// setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		panel.setLayout(null);

		JLabel lb1 = new JLabel("Name:");
		lb1.setBounds(40, 30, 80, 25);
		panel.add(lb1);

		jtf1 = new JTextField(20);
		jtf1.setBounds(140, 30, 190, 25);
		panel.add(jtf1);

		JLabel lb2 = new JLabel("Surname:");
		lb2.setBounds(40, 60, 80, 25);
		panel.add(lb2);

		jtf2 = new JTextField(20);
		jtf2.setBounds(140, 60, 190, 25);
		panel.add(jtf2);

		JLabel lb3 = new JLabel("E-mail:");
		lb3.setBounds(40, 90, 80, 25);
		panel.add(lb3);

		jtf3 = new JTextField(20);
		jtf3.setBounds(140, 90, 190, 25);
		panel.add(jtf3);

		JLabel lb4 = new JLabel("Company:");
		lb4.setBounds(40, 120, 80, 25);
		panel.add(lb4);

		jtf4 = new JTextField(20);
		jtf4.setBounds(140, 120, 190, 25);
		panel.add(jtf4);

		JLabel lb5 = new JLabel("Validity(days):");
		lb5.setBounds(40, 150, 80, 25);
		panel.add(lb5);

		jtf5 = new JTextField(20);
		jtf5.setBounds(140, 150, 190, 25);
		jtf5.setText("");
		jtf5.getDocument().addDocumentListener(new DocumentListener() {

			@Override
			public void removeUpdate(DocumentEvent e) {
				// TODO Auto-generated method stub
				validateInput();
			}

			@Override
			public void insertUpdate(DocumentEvent e) {
				// TODO Auto-generated method stub
				validateInput();
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
				// TODO Auto-generated method stub
				validateInput();
			}

			private void validateInput() {
				// TODO Auto-generated method stub

				if (!isDateValid(jtf5.getText())) {
					submitButton.setEnabled(false);
				} else if (!String.valueOf(jpf1.getPassword()).equals("")
						&& !String.valueOf(jpf2.getPassword()).equals("")
						&& !jtf6.getText().equals("")
						&& isDateValid(jtf5.getText())
						&& passwordsEqual(String.valueOf(jpf1.getPassword()),
								String.valueOf(jpf2.getPassword()))) {
					submitButton.setEnabled(true);
				}

			}
		});

		panel.add(jtf5);

		JLabel lb6 = new JLabel("Alias:");
		lb6.setBounds(40, 180, 80, 25);
		panel.add(lb6);

		errorMsg2.setBounds(330, 180, 80, 25);
		panel.add(errorMsg2);

		errorMsg3.setBounds(330, 150, 80, 25);
		panel.add(errorMsg3);

		jtf6 = new JTextField(20);
		jtf6.setBounds(140, 180, 190, 25);
		jtf6.setText("");
		jtf6.getDocument().addDocumentListener(new DocumentListener() {

			@Override
			public void removeUpdate(DocumentEvent e) {
				// TODO Auto-generated method stub
				validateInput();
			}

			@Override
			public void insertUpdate(DocumentEvent e) {
				// TODO Auto-generated method stub
				validateInput();
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
				// TODO Auto-generated method stub
				validateInput();
			}

			private void validateInput() {
				// TODO Auto-generated method stub

				if (jtf6.getText().equals("")) {
					submitButton.setEnabled(false);
				} else if (!String.valueOf(jpf1.getPassword()).equals("")
						&& !String.valueOf(jpf2.getPassword()).equals("")
						&& !jtf6.getText().equals("")
						&& isDateValid(jtf5.getText())
						&& passwordsEqual(String.valueOf(jpf1.getPassword()),
								String.valueOf(jpf2.getPassword()))) {
					submitButton.setEnabled(true);
				}

			}
		});
		panel.add(jtf6);

		JLabel lb7 = new JLabel("Password:");
		lb7.setBounds(40, 210, 80, 25);
		panel.add(lb7);

		errorMsg.setBounds(330, 210, 80, 25);
		panel.add(errorMsg);

		errorMsg1.setBounds(330, 240, 80, 25);
		panel.add(errorMsg1);
		jpf1 = new JPasswordField(20);
		jpf1.setBounds(140, 210, 190, 25);
		jtf1.getDocument().addDocumentListener(new DocumentListener() {

			@Override
			public void removeUpdate(DocumentEvent e) {
				// TODO Auto-generated method stub
				validateInput();
			}

			@Override
			public void insertUpdate(DocumentEvent e) {
				// TODO Auto-generated method stub
				validateInput();
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
				// TODO Auto-generated method stub
				validateInput();
			}

			private void validateInput() {
				// TODO Auto-generated method stub

				if (String.valueOf(jpf1.getPassword()).equals("")
						|| (!String.valueOf(jpf1.getPassword()).equals("")
								&& !String.valueOf(jpf2.getPassword()).equals(
										"") && passwordsEqual(
									String.valueOf(jpf1.getPassword()),
									String.valueOf(jpf2.getPassword())))) {
					submitButton.setEnabled(false);
				} else if (!String.valueOf(jpf1.getPassword()).equals("")
						&& !String.valueOf(jpf2.getPassword()).equals("")
						&& !jtf6.getText().equals("")
						&& isDateValid(jtf5.getText())
						&& passwordsEqual(String.valueOf(jpf1.getPassword()),
								String.valueOf(jpf2.getPassword()))) {
					submitButton.setEnabled(true);
				}

			}
		});
		panel.add(jpf1);

		JLabel lb8 = new JLabel("Repeat password:");
		lb8.setBounds(40, 240, 110, 25);
		panel.add(lb8);

		jpf2 = new JPasswordField(20);
		jpf2.setBounds(140, 240, 190, 25);

		jpf2.getDocument().addDocumentListener(new DocumentListener() {

			@Override
			public void removeUpdate(DocumentEvent e) {
				// TODO Auto-generated method stub
				validateInput();
			}

			@Override
			public void insertUpdate(DocumentEvent e) {
				// TODO Auto-generated method stub
				validateInput();
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
				// TODO Auto-generated method stub
				validateInput();
			}

			private void validateInput() {
				// TODO Auto-generated method stub

				if (String.valueOf(jpf2.getPassword()).equals("")
						|| (!String.valueOf(jpf1.getPassword()).equals("")
								&& !String.valueOf(jpf2.getPassword()).equals(
										"") && !passwordsEqual(
									String.valueOf(jpf1.getPassword()),
									String.valueOf(jpf2.getPassword())))) {
					submitButton.setEnabled(false);
				} else if (!String.valueOf(jpf1.getPassword()).equals("")
						&& !String.valueOf(jpf2.getPassword()).equals("")
						&& !jtf6.getText().equals("")
						&& isDateValid(jtf5.getText())
						&& passwordsEqual(String.valueOf(jpf1.getPassword()),
								String.valueOf(jpf2.getPassword()))) {
					submitButton.setEnabled(true);

				}
			}
		});
		panel.add(jpf2);

		JLabel lb10 = new JLabel("Issuer alias:");
		lb10.setBounds(40, 270, 80, 25);
		panel.add(lb10);

		jtf8 = new JTextField(20);
		jtf8.setBounds(140, 270, 190, 25);
		panel.add(jtf8);

		this.s = s;

		submitButton.setBounds(140, 300, 100, 25);

		/* passwordAlgorithm(String.valueOf(jpf1.getPassword()) */
		submitButton.addActionListener(new PokupiSaForme(this, jtf1, jtf2,
				jtf3, jtf4, jtf5, jtf6, jpf1, jpf2, jtf8, s));

		/*
		 * submitButton.addActionListener(new ActionListener() {
		 * 
		 * @Override public void actionPerformed(ActionEvent e) { // TODO
		 * Auto-generated method stub
		 * if(passwordAlgorithm(String.valueOf(jpf1.getPassword()))){
		 * submitButton.addActionListener(new PokupiSaForme(this, jtf1, jtf2,
		 * jtf3, jtf4, jtf5, jtf6, jtf7, jpf1, jtf8, s)); } } })
		 */
		submitButton.setEnabled(false);

		add(submitButton);

		add(panel);

	}

	private boolean passwordsEqual(String pass1, String pass2) {
		boolean valid = false;

		if (pass1.equals(pass2))
			valid = true;

		return valid;

	}

	public static boolean isDateValid(String date) {
		if (date.equals("")) {
			return false;
		}
		boolean valid = false;
		boolean noDigit = false;
		for (int i = 0; i < date.length(); i++) {
			if (!Character.isDigit(date.charAt(i))) {
				noDigit = true;
			}
		}
		if (!noDigit) {
			valid = true;
		}
		return valid;
	}

	public static boolean passwordAlgorithm(String s) {
		boolean upper = false;
		boolean number = false;
		boolean length = false;

		if (s.length() >= 7) {
			length = true;
		}
		for (int i = 0; i < s.length(); i++) {
			if (Character.isUpperCase(s.charAt(i))) {
				upper = true;
			}
		}
		for (int j = 0; j < s.length(); j++) {
			if (Character.isDigit(s.charAt(j))) {
				number = true;
			}
		}

		if (upper && number && length) {
			return true;
		}

		return false;

	}

}