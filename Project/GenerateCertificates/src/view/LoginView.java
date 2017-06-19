package view;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import bcrypt.BCrypt;
import dbconn.DbConn;

public class LoginView extends JFrame {

	private static LoginView instance;
	private JTextField userText;
	private JPasswordField passwordText;
	private JLabel error, errorLabel ;
	private static JPanel panel;
	private JButton loginButton;
	private DbConn database;


	public static LoginView getInstance() {
		if (instance == null) {
			instance = new LoginView();
			instance.init();

		}
		return instance;
	}

	private void init() {
		
		database = new DbConn();
		database.OpenConnection();
		
	//	MainWindow.getInstance(1).setVisible(true);
		
		
		panel = new JPanel();

		setSize(480, 180);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		setTitle("Login form");
		
		panel.setLayout(null);

		error = new JLabel("");
		error.setForeground(Color.RED);
		error.setBounds(140, 85, 160, 25);
		panel.add(error);

		JLabel userLabel = new JLabel("Username");
		userLabel.setBounds(60, 30, 80, 25);
		panel.add(userLabel);

		userText = new JTextField(20);
		userText.setBounds(140, 30, 190, 25);
		userText.getDocument().addDocumentListener(new DocumentListener() {

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
				errorLabel.setText("");
				if (!(userText.getText().equals("")) && !(String.valueOf(passwordText.getPassword()).equals("")) ) {
					loginButton.setEnabled(true);
				} else {
					loginButton.setEnabled(false);
				}

			}
		});

		panel.add(userText);

		JLabel passwordLabel = new JLabel("Password");
		passwordLabel.setBounds(60, 60, 80, 25);
		

		panel.add(passwordLabel);
		
		errorLabel = new JLabel("");
		errorLabel.setForeground(Color.RED);
		errorLabel.setBounds(340, 60, 190, 25);
		errorLabel.setFont(new Font("Serif", Font.BOLD, 20));
		
		panel.add(errorLabel);
		
		passwordText = new JPasswordField(20);
		passwordText.setBounds(140, 60, 190, 25);
		passwordText.getDocument().addDocumentListener(new DocumentListener() {

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
				errorLabel.setText("");
				if (!(userText.getText().equals("")) && !(String.valueOf(passwordText.getPassword()).equals("")) ) {
					loginButton.setEnabled(true);
				} else {
					loginButton.setEnabled(false);
				}

			}
		});

		panel.add(passwordText);
		loginButton = new JButton("Login");

		loginButton.setBounds(140, 115, 80, 35);
		loginButton.setEnabled(false);

		loginButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(getDatabase().login(userText.getText(), String.valueOf(passwordText.getPassword())) == 1) {
					MainWindow.getInstance(1).setVisible(true);
					LoginView.getInstance().setVisible(false);
				}else if(getDatabase().login(userText.getText(),String.valueOf(passwordText.getPassword())) == 3) {
					MainWindow.getInstance(3).setVisible(true);
					LoginView.getInstance().setVisible(false);
				}
				else {
					errorLabel.setText("Unsuccessful!");
				}
		
			}
		});

		panel.add(loginButton);

		JButton loginAsGuestButton = new JButton("Continue as guest");

		loginAsGuestButton.setBounds(240, 115, 150, 35);

		loginAsGuestButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				MainWindow.getInstance(2).setVisible(true);
				LoginView.getInstance().setVisible(false);
			
			}
		});

		panel.add(loginAsGuestButton);

		add(panel);
	}
	public DbConn getDatabase() {
		return database;
	}

	public JTextField getUserText() {
		return userText;
	}

	public void setUserText(JTextField userText) {
		this.userText = userText;
	}

	public JPasswordField getPasswordText() {
		return passwordText;
	}

	public void setPasswordText(JPasswordField passwordText) {
		this.passwordText = passwordText;
	}
	
	

	
}
