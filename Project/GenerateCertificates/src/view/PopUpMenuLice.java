package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Enumeration;

import javax.swing.ImageIcon;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

public class PopUpMenuLice extends JPopupMenu {

	private JMenuItem approve, decline;

	public PopUpMenuLice() {

		approve = new JMenuItem("Approve");
		approve.setIcon(new ImageIcon("images/approve.png"));
		approve.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				String alias = (String) CertificatesToApproveView
						.getInstance()
						.getJt()
						.getValueAt(
								CertificatesToApproveView.getInstance().getJt()
										.getSelectedRow(), 1);
				
				LoginView.getInstance().getDatabase().adminApprove(alias);
				
				CertificatesToApproveView.getInstance().tablePopulate();
				
				CertificatesToApproveView.getInstance().repaint();
				CertificatesToApproveView.getInstance().invalidate();
				CertificatesToApproveView.getInstance().revalidate();
				
				Enumeration<String> enum123 = MainWindow.getInstance()
						.getKeyStoreWriter().getAlias();

				ArrayList<String> listaStringova = new ArrayList<String>();
				while (enum123.hasMoreElements()) {
					String show = enum123.nextElement();
					listaStringova.add(show);
				}
				
				MainWindow.getInstance().tablePopulate(listaStringova);
				
				MainWindow.getInstance().repaint();
				MainWindow.getInstance().invalidate();
				MainWindow.getInstance().revalidate();
			}
		});
		add(approve);
		
		decline = new JMenuItem("Decline");
		decline.setIcon(new ImageIcon("images/removeKey.png"));
		decline.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				String alias = (String) CertificatesToApproveView
						.getInstance()
						.getJt()
						.getValueAt(
								CertificatesToApproveView.getInstance().getJt()
										.getSelectedRow(), 1);
				
				MainWindow.getInstance().getKeyStoreWriter().delete(alias);
				
				LoginView.getInstance().getDatabase().adminDecline(alias);
				
				CertificatesToApproveView.getInstance().tablePopulate();
				
				CertificatesToApproveView.getInstance().repaint();
				CertificatesToApproveView.getInstance().invalidate();
				CertificatesToApproveView.getInstance().revalidate();
				
				Enumeration<String> enum123 = MainWindow.getInstance()
						.getKeyStoreWriter().getAlias();

				ArrayList<String> listaStringova = new ArrayList<String>();
				while (enum123.hasMoreElements()) {
					String show = enum123.nextElement();
					listaStringova.add(show);
				}
				
				MainWindow.getInstance().tablePopulate(listaStringova);
				
				MainWindow.getInstance().repaint();
				MainWindow.getInstance().invalidate();
				MainWindow.getInstance().revalidate();
			}
		});
		add(decline);
	}
}
