package view;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Enumeration;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class CertificatesToApproveView extends JFrame {

	private static CertificatesToApproveView instance = null;
	private DefaultTableModel defaultTableModel;
	private JTable jt;
	private Container panel1;

	public static CertificatesToApproveView getInstance() {

		if (instance == null) {
			instance = new CertificatesToApproveView();
		}
		return instance;
	}

	public CertificatesToApproveView() {
		setSize(600, 400);
		setLocationRelativeTo(null);
		setTitle("Certificates to approve");
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		panel1 = new JPanel();
		tableInitialize();

		

		tablePopulate();

		panel1.add(new JScrollPane(jt));

		add(panel1, BorderLayout.NORTH);
	}

	// TODO Auto-generated method stub
	public void tablePopulate() {
		
		Enumeration<String> enum123 = MainWindow.getInstance()
				.getKeyStoreWriter().getAlias();

		ArrayList<String> listaStringova = new ArrayList<String>();
		while (enum123.hasMoreElements()) {
			String show = enum123.nextElement();
			listaStringova.add(show);
		}
		
		ArrayList<String> listaStringovaApproved = new ArrayList<String>();

		for (int i = 0; i < listaStringova.size(); i++) {
			if (LoginView.getInstance().getDatabase()
					.isApproved(listaStringova.get(i)) == 0) {
				listaStringovaApproved.add(listaStringova.get(i));
			}

		}

		defaultTableModel.setRowCount(0);

		if (listaStringovaApproved != null) {
			for (int i = 0; i < listaStringovaApproved.size(); i++) {
				System.out.println("eve ga: " + listaStringovaApproved.get(i));
				ImageIcon icon = new ImageIcon("images/keys.png");
				defaultTableModel.addRow(new Object[] { icon,
						listaStringovaApproved.get(i) });

			}
		}
		defaultTableModel.fireTableDataChanged();
		jt.setModel(defaultTableModel);
		if (listaStringova != null) {
			jt.getSelectionModel().setSelectionInterval(0, 0);
		}

	}

	private void tableInitialize() {
		// TODO Auto-generated method stub
		Object columnNames[] = { "", "Alias" };
		defaultTableModel = new DefaultTableModel(columnNames, 0) {
			@Override
			public boolean isCellEditable(int row, int column) {
				// TODO Auto-generated method stub
				return false;
			}
		};

		jt = new JTable(defaultTableModel) {
			public Class getColumnClass(int column) {
				return getValueAt(0, column).getClass();
			}

		};
		jt.getColumnModel().getColumn(0).setMaxWidth(16);
		jt.getColumnModel().getColumn(0).setMinWidth(16);

		jt.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {

				int r = jt.rowAtPoint(e.getPoint());
				if (r >= 0 && r < jt.getRowCount()) {
					jt.setRowSelectionInterval(r, r);

				} else {
					jt.clearSelection();
				}

				int rowindex = jt.getSelectedRow();
				if (rowindex < 0)
					return;
				if (e.isPopupTrigger() && e.getComponent() instanceof JTable) {
					PopUpMenuLice popup = new PopUpMenuLice();
					popup.show(e.getComponent(), e.getX(), e.getY());

				}

			}
		});

		jt.setFont(new Font("Arial", Font.BOLD, 14));

	}

	public JTable getJt() {
		return jt;
	}

}
