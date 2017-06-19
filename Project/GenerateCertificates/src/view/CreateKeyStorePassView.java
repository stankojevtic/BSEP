package view;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class CreateKeyStorePassView extends JFrame {

	private JPasswordField jpf1, jpf2;
	private JPanel panel;
	private String filePath;
	private JButton jb;

	public CreateKeyStorePassView(String string) {
		this.filePath = string;

		panel = new JPanel();

		setSize(400, 150);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		setTitle("Enter KeyStore password");

		panel.setLayout(null);

		JLabel lb1 = new JLabel("Password:");
		lb1.setBounds(60, 30, 80, 25);
		panel.add(lb1);

		jpf1 = new JPasswordField(20);
		jpf1.setBounds(180, 30, 190, 25);
		jpf1.getDocument().addDocumentListener(new DocumentListener() {

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

				if (String.valueOf(jpf1.getPassword()).equals("")) {
					jb.setEnabled(false);
				} else if (!String.valueOf(jpf2.getPassword()).equals("")
						&& !String.valueOf(jpf1.getPassword()).equals("")
						&& passwordsEqual(String.valueOf(jpf1.getPassword()),
								String.valueOf(jpf2.getPassword()))) {
					jb.setEnabled(true);
				}

			}
		});
		panel.add(jpf1);

		JLabel lb2 = new JLabel("Repeat password:");
		lb2.setBounds(60, 60, 110, 25);
		panel.add(lb2);

		jpf2 = new JPasswordField(20);
		jpf2.setBounds(180, 60, 190, 25);
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

				if (String.valueOf(jpf2.getPassword()).equals("")) {
					jb.setEnabled(false);
				} else if (!String.valueOf(jpf2.getPassword()).equals("")
						&& !String.valueOf(jpf1.getPassword()).equals("")
						&& passwordsEqual(String.valueOf(jpf1.getPassword()),
								String.valueOf(jpf2.getPassword()))) {
					jb.setEnabled(true);
				}

			}
		});
		panel.add(jpf2);

		jb = new JButton("Submit");
		jb.setBounds(120, 90, 100, 25);
		jb.setEnabled(false);
		jb.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				/*
				 * a password must be eight characters including one uppercase
				 * letter, one special character and alphanumeric characters.
				 */
				if (!passwordAlgorithm(String.valueOf(jpf1.getPassword()))) {
					JOptionPane.showMessageDialog(null,
							"<html>Password must be at least 7<br>characters "
							+ "including one uppercase <br>letter and"
							+ " one number.</html>");
				} else {

					MainWindow.getInstance().getKeyStoreWriter()
							.saveKeyStore(filePath, jpf1.getPassword());
					MainWindow.getInstance().getKeyStoreWriter()
							.setFileName(filePath);

					OutputStream output = null;

					try {

						output = new FileOutputStream("config.properties");

						Path p = Paths.get(filePath);
						String fileName = p.getFileName().toString();

						String stringValueOf = String.valueOf(jpf1
								.getPassword());

						MainWindow.getInstance().getProp()
								.setProperty(fileName, stringValueOf);

						MainWindow.getInstance().getProp().store(output, null);

					} catch (IOException io) {
						io.printStackTrace();
					} finally {
						if (output != null) {
							try {
								output.close();
							} catch (IOException e1) {
								e1.printStackTrace();
							}
						}

					}

					setVisible(false);

				}
			}
		});

		panel.add(jb);



		add(panel);

	}

	private boolean passwordsEqual(String pass1, String pass2) {
		boolean valid = false;

		if (pass1.equals(pass2))
			valid = true;

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
