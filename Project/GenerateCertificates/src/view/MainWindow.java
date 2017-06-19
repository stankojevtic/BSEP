package view;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.Security;
import java.util.ArrayList;
import java.util.Properties;

import javax.swing.AbstractButton;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JToolBar;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;

import keystore_reader_writer.KeyStoreWriter;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

import controller.ApproveCertificates;
import controller.FindCertificate;
import controller.ImportCertificate;
import controller.NewKeyPair;
import controller.NewKeyStore;
import controller.OcspRevoke;
import controller.OpenKeyStore;
import controller.RemoveCertificate;
import controller.SaveAsKeyStore;
import controller.SaveKeyStore;

public class MainWindow extends JFrame {

	// WebService handleri

	// email - sta ste tacno rekli gde treba da cuvam sifru keyStora?

	// provera CA prilikom signed certificata
	// getPrivateKey
	// Uzimanje passworda da ne bi unosili
	//

	private static MainWindow instance = null;
	private JToolBar toolBar = new JToolBar();
	public JButton btn0, btn1, btn2, btn3, btn5;
	private JMenuBar menuBar = new JMenuBar();
	public JMenuItem ocspG, ocspA, ocspL;
	private JMenuBar menuBarLice = new JMenuBar();
	private JMenuBar menuBar1 = new JMenuBar();
	private JMenu file, tools, optionsGuest;
	private JTable jtAdmin, jtLice, jtGuest;
	public JMenuItem newKeyPair, newKeyPairPL, takeCertificate,
			takeCertificate1, removeCertificate, newItem, openItem, saveItem,
			saveAsItem, deleteItem, logoutItem1, logoutItem2,
			approveCertificate, openItemLice, openItemGuest, newKeyPairPL2;

	private KeyStoreWriter keyStoreWriter;
	public DefaultTableModel defaultTableModelAdmin, defaultTableModelLice,
			defaultTableModelGuest;
	private JPanel panel1, panel2, panel3, panel4, panel5, panel6, mainPanel;

	private Properties prop;
	private JMenu optionsLice;
	private JMenu optionsAdmin;
	public JMenuItem importCert;
	public JMenuItem importCertG;

	public static MainWindow getInstance(int rola) {
		if (instance == null) {
			instance = new MainWindow();
			instance.init(rola);

		} else {
			instance = new MainWindow();
			instance.init(rola);

		}
		return instance;
	}

	public static MainWindow getInstance() {
		/*
		 * if (instance == null) { instance = new MainWindow();
		 * instance.init(rola); }
		 */
		return instance;
	}

	public MainWindow() {

		/*
		 * Security.addProvider(new BouncyCastleProvider()); setSize(600, 400);
		 * setLocationRelativeTo(null);
		 * setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //
		 * setResizable(false); keyStoreWriter = new KeyStoreWriter();
		 * 
		 * menuPopulate(); toolbarPopulate(); tableInitialize();
		 * 
		 * database = new DbConn(); database.OpenConnection();
		 */

	}

	private void init(int rola) {
		Security.addProvider(new BouncyCastleProvider());
		setSize(600, 400);
		setLocationRelativeTo(null);
		setTitle("Generate keys");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		ImageIcon img = new ImageIcon("images/keys.png");
		setIconImage(img.getImage());

		/*
		 * this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		 * this.addWindowListener(new WindowAdapter() { public void
		 * windowClosing(WindowEvent e) { String ObjButtons[] = { "Yes", "No",
		 * "Cancel" }; int PromptResult = JOptionPane.showOptionDialog(null,
		 * "Would you like to save KeyStore before exit?", "Exit",
		 * JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null,
		 * ObjButtons, ObjButtons[2]); if (PromptResult ==
		 * JOptionPane.YES_OPTION) { char[] array1 = { 'a' }; if
		 * (MainWindow.getInstance().getKeyStoreWriter() .getFileName() == null)
		 * { JFileChooser jfc = new JFileChooser();
		 * 
		 * if (jfc.showSaveDialog(MainWindow.getInstance()) ==
		 * JFileChooser.APPROVE_OPTION) {
		 * 
		 * MainWindow .getInstance() .getKeyStoreWriter() .saveKeyStore(
		 * jfc.getSelectedFile().toString(), array1); MainWindow .getInstance()
		 * .getKeyStoreWriter() .setFileName( jfc.getSelectedFile().toString());
		 * 
		 * MainWindow .getInstance() .getKeyStoreWriter() .saveKeyStore(
		 * MainWindow.getInstance() .getKeyStoreWriter() .getFileName(),
		 * array1);
		 * 
		 * } } else { MainWindow .getInstance() .getKeyStoreWriter()
		 * .saveKeyStore( MainWindow.getInstance().getKeyStoreWriter()
		 * .getFileName(), array1); } System.exit(0); } else if (PromptResult ==
		 * JOptionPane.NO_OPTION) { System.exit(0); }
		 * 
		 * } });
		 */

		// setResizable(false);
		keyStoreWriter = new KeyStoreWriter();
		// initPropertyFile();
		prop = new Properties();

		InputStream input = null;

		try {

			input = new FileInputStream("config.properties");

			// load a properties file
			prop.load(input);
		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		panel1 = new JPanel();
		panel2 = new JPanel();
		panel3 = new JPanel();
		panel4 = new JPanel();
		panel5 = new JPanel();
		panel6 = new JPanel();

		panel3.setLayout(new BorderLayout());
		panel4.setLayout(new BorderLayout());
		panel2.setLayout(new BorderLayout());
		panel1.setLayout(new BorderLayout());
		panel5.setLayout(new BorderLayout());
		panel6.setLayout(new BorderLayout());

		menuPopulate();
		toolbarPopulate();
		tableInitialize();

		panel1.add(panel2, BorderLayout.NORTH);

		panel3.add(panel4, BorderLayout.NORTH);

		panel5.add(panel6, BorderLayout.NORTH);

		// add(panel1);

		/*
		 * mainPanel = new JPanel(new CardLayout());
		 * 
		 * mainPanel.add(panel1, "Guest"); mainPanel.add(panel3, "Admin");
		 */

		// add(panel1);

		if (rola == 1) {
			add(panel1);

			MainWindow.getInstance().repaint();
			MainWindow.getInstance().invalidate();
			MainWindow.getInstance().revalidate();
			/*
			 * char[] pass = { 'a' };
			 * MainWindow.getInstance().getKeyStoreWriter()
			 * .loadKeyStore("C:\\Users\\Jevtic\\Desktop\\a\\a1", pass);
			 * MainWindow.getInstance().getKeyStoreWriter()
			 * .setFileName("C:\\Users\\Jevtic\\Desktop\\a\\a1");
			 */
		} else if (rola == 2) {
			add(panel3);
		} else if (rola == 3) {
			add(panel5);
			MainWindow.getInstance().repaint();
			MainWindow.getInstance().invalidate();
			MainWindow.getInstance().revalidate();
		}

		/*
		 * database = new DbConn(); database.OpenConnection();
		 */
	}

	private void tableInitialize() {
		// TODO Auto-generated method stub

		Object columnNames[] = { "", "Alias" };
		defaultTableModelAdmin = new DefaultTableModel(columnNames, 0) {
			@Override
			public boolean isCellEditable(int row, int column) {
				// TODO Auto-generated method stub
				return false;
			}
		};

		jtAdmin = new JTable(defaultTableModelAdmin) {
			public Class getColumnClass(int column) {
				return getValueAt(0, column).getClass();
			}

		};
		jtAdmin.getColumnModel().getColumn(0).setMaxWidth(16);
		jtAdmin.getColumnModel().getColumn(0).setMinWidth(16);

		jtAdmin.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {

				int r = jtAdmin.rowAtPoint(e.getPoint());
				if (r >= 0 && r < jtAdmin.getRowCount()) {
					jtAdmin.setRowSelectionInterval(r, r);

				} else {
					jtAdmin.clearSelection();
				}

				int rowindex = jtAdmin.getSelectedRow();
				if (rowindex < 0)
					return;
				if (e.isPopupTrigger() && e.getComponent() instanceof JTable) {
					PopUpMenu popup = new PopUpMenu();
					popup.show(e.getComponent(), e.getX(), e.getY());

				}

			}
		});

		jtAdmin.setFont(new Font("Arial", Font.BOLD, 14));

		Object columnNamesLice[] = { "", "Alias" };
		defaultTableModelLice = new DefaultTableModel(columnNamesLice, 0) {
			@Override
			public boolean isCellEditable(int row, int column) {
				// TODO Auto-generated method stub
				return false;
			}
		};

		jtLice = new JTable(defaultTableModelLice) {
			public Class getColumnClass(int column) {
				return getValueAt(0, column).getClass();
			}
		};
		jtLice.getColumnModel().getColumn(0).setMaxWidth(16);
		jtLice.getColumnModel().getColumn(0).setMinWidth(16);

		/*
		 * jtLice.addMouseListener(new MouseAdapter() {
		 * 
		 * @Override public void mouseReleased(MouseEvent e) {
		 * 
		 * int r = jtLice.rowAtPoint(e.getPoint()); if (r >= 0 && r <
		 * jtLice.getRowCount()) { jtLice.setRowSelectionInterval(r, r);
		 * 
		 * } else { jtLice.clearSelection(); }
		 * 
		 * int rowindex = jtLice.getSelectedRow(); if (rowindex < 0) return; if
		 * (e.isPopupTrigger() && e.getComponent() instanceof JTable) {
		 * PopUpMenuLice popup = new PopUpMenuLice();
		 * popup.show(e.getComponent(), e.getX(), e.getY());
		 * 
		 * }
		 * 
		 * } });
		 */

		jtLice.setFont(new Font("Arial", Font.BOLD, 14));

		Object columnNamesGuest[] = { "", "Alias" };
		defaultTableModelGuest = new DefaultTableModel(columnNamesGuest, 0) {
			@Override
			public boolean isCellEditable(int row, int column) {
				// TODO Auto-generated method stub
				return false;
			}
		};

		jtGuest = new JTable(defaultTableModelGuest) {
			public Class getColumnClass(int column) {
				return getValueAt(0, column).getClass();
			}
		};
		jtGuest.getColumnModel().getColumn(0).setMaxWidth(16);
		jtGuest.getColumnModel().getColumn(0).setMinWidth(16);

		jtGuest.setFont(new Font("Arial", Font.BOLD, 14));

		panel1.add(new JScrollPane(jtAdmin));

		panel3.add(new JScrollPane(jtGuest));

		panel5.add(new JScrollPane(jtLice));

	}

	public void tablePopulate(ArrayList<String> listaStringova) {

		ArrayList<String> listaStringovaApproved = new ArrayList<String>();

		for (int i = 0; i < listaStringova.size(); i++) {
			if (LoginView.getInstance().getDatabase()
					.isApproved(listaStringova.get(i)) == 1) {
				listaStringovaApproved.add(listaStringova.get(i));
			}

		}

		defaultTableModelAdmin.setRowCount(0);

		if (listaStringovaApproved != null) {
			for (int i = 0; i < listaStringovaApproved.size(); i++) {
				ImageIcon icon = new ImageIcon("images/keys.png");
				defaultTableModelAdmin.addRow(new Object[] { icon,
						listaStringovaApproved.get(i) });

			}
		}
		defaultTableModelAdmin.fireTableDataChanged();
		jtAdmin.setModel(defaultTableModelAdmin);
		if (listaStringova != null) {
			jtAdmin.getSelectionModel().setSelectionInterval(0, 0);
		}

		defaultTableModelLice.setRowCount(0);

		if (listaStringovaApproved != null) {
			for (int i = 0; i < listaStringovaApproved.size(); i++) {
				ImageIcon icon = new ImageIcon("images/keys.png");
				defaultTableModelLice.addRow(new Object[] { icon,
						listaStringovaApproved.get(i) });

			}
		}
		defaultTableModelLice.fireTableDataChanged();
		jtLice.setModel(defaultTableModelLice);
		if (listaStringova != null) {
			jtLice.getSelectionModel().setSelectionInterval(0, 0);
		}

		// /
		defaultTableModelGuest.setRowCount(0);

		if (listaStringovaApproved != null) {
			for (int i = 0; i < listaStringovaApproved.size(); i++) {
				ImageIcon icon = new ImageIcon("images/keys.png");
				defaultTableModelGuest.addRow(new Object[] { icon,
						listaStringovaApproved.get(i) });

			}
		}
		defaultTableModelGuest.fireTableDataChanged();
		jtGuest.setModel(defaultTableModelGuest);
		if (listaStringova != null) {
			jtGuest.getSelectionModel().setSelectionInterval(0, 0);
		}

	}

	private void toolbarPopulate() {

		btn0 = new JButton();
		btn0.setIcon(new ImageIcon("images/new.png"));
		btn0.setEnabled(true);
		btn0.setToolTipText("Create new keystore");
		btn0.addActionListener(new NewKeyStore());
		toolBar.add(btn0);

		btn5 = new JButton();
		btn5.setIcon(new ImageIcon("images/open.png"));
		btn5.setEnabled(true);
		btn5.setToolTipText("Open existing keystore");
		btn5.addActionListener(new OpenKeyStore());
		toolBar.add(btn5);

		toolBar.addSeparator();
		;

		btn1 = new JButton();
		btn1.setIcon(new ImageIcon("images/key.png"));
		btn1.setEnabled(false);
		btn1.setToolTipText("Generate certificate");
		btn1.addActionListener(new NewKeyPair(""));

		toolBar.add(btn1);

		btn2 = new JButton();
		btn2.addActionListener(new FindCertificate());
		btn2.setIcon(new ImageIcon("images/find.png"));
		btn2.setToolTipText("Find certificate");
		btn2.setEnabled(false);
		toolBar.add(btn2);

		btn3 = new JButton();
		btn3.addActionListener(new RemoveCertificate());
		btn3.setIcon(new ImageIcon("images/removeKey.png"));
		btn3.setToolTipText("Remove certificate");
		btn3.setEnabled(false);
		toolBar.add(btn3);

		toolBar.setFloatable(false);
		/* this.add(menuBar,BorderLayout.NORTH); */

		// panel4.add(toolBar, BorderLayout.CENTER);

		panel2.add(toolBar, BorderLayout.CENTER);

	}

	private void initPropertyFile() {
		// TODO Auto-generated method stub
		prop = new Properties();
		OutputStream output = null;

		try {

			output = new FileOutputStream("config.properties");

			// set the properties value
			/*
			 * prop.setProperty("database", "localhost");
			 * prop.setProperty("dbuser", "mkyong");
			 * prop.setProperty("dbpassword", "password");
			 */

			// save properties to project root folder
			prop.store(output, null);

		} catch (IOException io) {
			io.printStackTrace();
		} finally {
			if (output != null) {
				try {
					output.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

		}
	}

	private void menuPopulate() {

		// //////////////////Fizicko lice menu
		JMenu lice = new JMenu("File");
		JMenu toolsLice = new JMenu("Tools");

		optionsLice = new JMenu("Look and feel");
		optionsLice.setIcon(new ImageIcon("images/look.png"));

		JMenuItem nimbusLFLice = new JMenuItem("Nimbus");
		nimbusLFLice.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				try {
					UIManager
							.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
					MainWindow.getInstance().repaint();
					MainWindow.getInstance().invalidate();
					MainWindow.getInstance().revalidate();
				} catch (Exception e1) {
				}

				SwingUtilities.updateComponentTreeUI(MainWindow.getInstance());
			}
		});

		JMenuItem nimbus1LFLice = new JMenuItem("Cross");
		nimbus1LFLice.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				try {
					UIManager.setLookAndFeel(UIManager
							.getCrossPlatformLookAndFeelClassName());
					MainWindow.getInstance().repaint();
					MainWindow.getInstance().invalidate();
					MainWindow.getInstance().revalidate();
				} catch (Exception e1) {
				}
				SwingUtilities.updateComponentTreeUI(MainWindow.getInstance());
			}
		});

		JMenuItem nimbus2LFLice = new JMenuItem("System");
		nimbus2LFLice.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				try {
					UIManager.setLookAndFeel(UIManager
							.getSystemLookAndFeelClassName());
					MainWindow.getInstance().repaint();
					MainWindow.getInstance().invalidate();
					MainWindow.getInstance().revalidate();
				} catch (Exception e1) {
				}
				SwingUtilities.updateComponentTreeUI(MainWindow.getInstance());
			}
		});
		optionsLice.add(nimbusLFLice);
		optionsLice.add(nimbus1LFLice);
		optionsLice.add(nimbus2LFLice);

		toolsLice.add(optionsLice);

		newKeyPairPL2 = new JMenuItem("Generate key (Pravna lica)");
		newKeyPairPL2.setEnabled(false);
		newKeyPairPL2.setIcon(new ImageIcon("images/key.png"));
		newKeyPairPL2.addActionListener(new NewKeyPair("Final"));
		toolsLice.add(newKeyPairPL2);

		toolsLice.addSeparator();

		importCert = new JMenuItem("Find certificate by serial number");
		importCert.setEnabled(false);
		importCert.setIcon(new ImageIcon("images/find.png"));
		importCert.addActionListener(new FindCertificate());
		toolsLice.add(importCert);

		ocspG = new JMenuItem("Is revoked (OCSP)");
		ocspG.setEnabled(false);
		ocspG.setIcon(new ImageIcon("images/revoke.png"));
		ocspG.addActionListener(new OcspRevoke());
		toolsLice.add(ocspG);

		openItemLice = new JMenuItem("Open KeyStore");
		openItemLice.setIcon(new ImageIcon("images/open.png"));
		openItemLice.addActionListener(new OpenKeyStore());
		lice.add(openItemLice);

		JMenuItem logoutItem3 = new JMenuItem("Log Out");
		logoutItem3.setIcon(new ImageIcon("images/logout.png"));
		logoutItem3.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				MainWindow.getInstance().setVisible(false);
				LoginView.getInstance().setVisible(true);
				LoginView.getInstance().getUserText().setText("");
				LoginView.getInstance().getPasswordText().setText("");
			}
		});
		lice.add(logoutItem3);

		menuBarLice.add(lice);
		menuBarLice.add(toolsLice);
		// /////////////////////////////

		// Guest menu

		JMenu menuGuest = new JMenu("File");
		JMenu toolsGuest = new JMenu("Tools");

		openItemGuest = new JMenuItem("Open KeyStore");
		openItemGuest.setIcon(new ImageIcon("images/open.png"));
		openItemGuest.addActionListener(new OpenKeyStore());
		openItemGuest.setEnabled(true);
		menuGuest.add(openItemGuest);

		optionsGuest = new JMenu("Look and feel");
		optionsGuest.setIcon(new ImageIcon("images/look.png"));

		JMenuItem nimbusLFGuest = new JMenuItem("Nimbus");
		nimbusLFGuest.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				try {
					UIManager
							.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
					MainWindow.getInstance().repaint();
					MainWindow.getInstance().invalidate();
					MainWindow.getInstance().revalidate();
				} catch (Exception e1) {
				}

				SwingUtilities.updateComponentTreeUI(MainWindow.getInstance());
			}
		});

		JMenuItem nimbus1LFGuest = new JMenuItem("Cross");
		nimbus1LFGuest.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				try {
					UIManager.setLookAndFeel(UIManager
							.getCrossPlatformLookAndFeelClassName());
					MainWindow.getInstance().repaint();
					MainWindow.getInstance().invalidate();
					MainWindow.getInstance().revalidate();
				} catch (Exception e1) {
				}
				SwingUtilities.updateComponentTreeUI(MainWindow.getInstance());
			}
		});

		JMenuItem nimbus2LFGuest = new JMenuItem("System");
		nimbus2LFGuest.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				try {
					UIManager.setLookAndFeel(UIManager
							.getSystemLookAndFeelClassName());
					MainWindow.getInstance().repaint();
					MainWindow.getInstance().invalidate();
					MainWindow.getInstance().revalidate();
				} catch (Exception e1) {
				}
				SwingUtilities.updateComponentTreeUI(MainWindow.getInstance());
			}
		});
		optionsGuest.add(nimbusLFGuest);
		optionsGuest.add(nimbus1LFGuest);
		optionsGuest.add(nimbus2LFGuest);

		toolsGuest.add(optionsGuest);

		takeCertificate1 = new JMenuItem("Find certificate by serial number");
		takeCertificate1.addActionListener(new FindCertificate());
		takeCertificate1.setIcon(new ImageIcon("images/find.png"));
		takeCertificate1.setEnabled(false);
		toolsGuest.add(takeCertificate1);
		
		ocspL = new JMenuItem("Is revoked (OCSP)");
		ocspL.setEnabled(false);
		ocspL.setIcon(new ImageIcon("images/revoke.png"));
		ocspL.addActionListener(new OcspRevoke());
		toolsGuest.add(ocspL);
		
		

		logoutItem2 = new JMenuItem("Log Out");
		logoutItem2.setIcon(new ImageIcon("images/logout.png"));
		logoutItem2.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				MainWindow.getInstance().setVisible(false);
				LoginView.getInstance().setVisible(true);
				LoginView.getInstance().getUserText().setText("");
				LoginView.getInstance().getPasswordText().setText("");
			}
		});
		menuGuest.add(logoutItem2);

		menuBar1.add(menuGuest);
		menuBar1.add(toolsGuest);
		// ////////////////////////////

		// admin menu

		file = new JMenu("File");
		newItem = new JMenuItem("New KeyStore");
		newItem.setIcon(new ImageIcon("images/new.png"));
		newItem.addActionListener(new NewKeyStore());

		openItem = new JMenuItem("Open KeyStore");
		openItem.setIcon(new ImageIcon("images/open.png"));

		saveItem = new JMenuItem("Save KeyStore");
		saveItem.setIcon(new ImageIcon("images/save.png"));
		saveItem.setEnabled(false);
		saveItem.addActionListener(new SaveKeyStore());
		saveAsItem = new JMenuItem("Save KeyStore As");
		saveAsItem.setIcon(new ImageIcon("images/saveAs.png"));
		saveAsItem.setEnabled(false);
		saveAsItem.addActionListener(new SaveAsKeyStore());
		openItem.addActionListener(new OpenKeyStore());

		deleteItem = new JMenuItem("Close");
		deleteItem.setIcon(new ImageIcon("images/delete.png"));
		deleteItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				System.exit(0);
			}
		});

		JMenuItem importItem = new JMenuItem("Import trusted cert");
		importItem.addActionListener(new ImportCertificate());

		logoutItem1 = new JMenuItem("Log Out");
		logoutItem1.setIcon(new ImageIcon("images/logout.png"));
		logoutItem1.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				MainWindow.getInstance().setVisible(false);
				LoginView.getInstance().setVisible(true);
				LoginView.getInstance().getUserText().setText("");
				LoginView.getInstance().getPasswordText().setText("");
			}
		});

		file.add(newItem);
		file.add(openItem);
		file.addSeparator();
		file.add(saveItem);
		file.add(saveAsItem);
		file.addSeparator();
		file.add(deleteItem);
		file.addSeparator();
		file.add(importItem);
		file.addSeparator();
		file.add(logoutItem1);

		tools = new JMenu("Tools");
		newKeyPair = new JMenuItem("Generate key");
		newKeyPair.setIcon(new ImageIcon("images/key.png"));
		newKeyPair.setEnabled(false);
		newKeyPair.addActionListener(new NewKeyPair(""));

		tools.add(newKeyPair);

		tools.addSeparator();

		takeCertificate = new JMenuItem("Find certificate by serial number");
		takeCertificate.addActionListener(new FindCertificate());
		takeCertificate.setIcon(new ImageIcon("images/find.png"));
		takeCertificate.setEnabled(false);
		tools.add(takeCertificate);
		
		ocspA = new JMenuItem("Is revoked (OCSP)");
		ocspA.setEnabled(false);
		ocspA.setIcon(new ImageIcon("images/revoke.png"));
		ocspA.addActionListener(new OcspRevoke());
		tools.add(ocspA);

		tools.addSeparator();

		removeCertificate = new JMenuItem("Remove certificate by serial number");
		removeCertificate.addActionListener(new RemoveCertificate());
		removeCertificate.setIcon(new ImageIcon("images/removeKey.png"));
		removeCertificate.setEnabled(false);
		tools.add(removeCertificate);

		tools.addSeparator();

		approveCertificate = new JMenuItem("Certificates to approve");
		approveCertificate.addActionListener(new ApproveCertificates());
		approveCertificate.setIcon(new ImageIcon("images/keys1.png"));
		approveCertificate.setEnabled(false);
		tools.add(approveCertificate);

		optionsAdmin = new JMenu("Look and feel");
		optionsAdmin.setIcon(new ImageIcon("images/look.png"));

		JMenuItem nimbusLAdmin = new JMenuItem("Nimbus");
		nimbusLAdmin.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				try {
					UIManager
							.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
					MainWindow.getInstance().repaint();
					MainWindow.getInstance().invalidate();
					MainWindow.getInstance().revalidate();
				} catch (Exception e1) {
				}

				SwingUtilities.updateComponentTreeUI(MainWindow.getInstance());
			}
		});

		JMenuItem nimbus1LAdmin = new JMenuItem("Cross");
		nimbus1LAdmin.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				try {
					UIManager.setLookAndFeel(UIManager
							.getCrossPlatformLookAndFeelClassName());
					MainWindow.getInstance().repaint();
					MainWindow.getInstance().invalidate();
					MainWindow.getInstance().revalidate();
				} catch (Exception e1) {
				}
				SwingUtilities.updateComponentTreeUI(MainWindow.getInstance());
			}
		});

		JMenuItem nimbus2LAdmin = new JMenuItem("System");
		nimbus2LAdmin.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				try {
					UIManager.setLookAndFeel(UIManager
							.getSystemLookAndFeelClassName());
					MainWindow.getInstance().repaint();
					MainWindow.getInstance().invalidate();
					MainWindow.getInstance().revalidate();
				} catch (Exception e1) {
				}
				SwingUtilities.updateComponentTreeUI(MainWindow.getInstance());
			}
		});
		optionsAdmin.add(nimbusLAdmin);
		optionsAdmin.add(nimbus1LAdmin);
		optionsAdmin.add(nimbus2LAdmin);

		tools.add(optionsAdmin);

		menuBar.add(file);
		menuBar.add(tools);

		panel4.add(menuBar1, BorderLayout.NORTH);

		panel2.add(menuBar, BorderLayout.NORTH);

		panel6.add(menuBarLice, BorderLayout.NORTH);
	}

	public Properties getProp() {
		return prop;
	}

	public void setProp(Properties prop) {
		this.prop = prop;
	}

	public JTable getJt() {
		return jtAdmin;
	}

	public KeyStoreWriter getKeyStoreWriter() {
		return keyStoreWriter;
	}

	public void setKeyStoreWriter(KeyStoreWriter keyStoreWriter) {
		this.keyStoreWriter = keyStoreWriter;
	}
}
