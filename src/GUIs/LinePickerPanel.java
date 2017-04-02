package GUIs;

import javax.swing.JPanel;
import javax.swing.JList;
import javax.swing.JOptionPane;

import java.awt.BorderLayout;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

public class LinePickerPanel extends JPanel {

	/**
	 * Create the panel.
	 */
	private GTFS.GTFSSystem sys;
	private Base.DataLoadController ctrl;
	
	public LinePickerPanel(Base.DataLoadController c, GTFS.GTFSSystem s) {
		sys = s;
		ctrl = c;
		setLayout(new BorderLayout(0, 0));
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		add(scrollPane, BorderLayout.CENTER);
		JList<GTFS.GTFSLine> list = new JList<GTFS.GTFSLine>(new Vector<GTFS.GTFSLine>(sys.getLines()));
		scrollPane.setViewportView(list);
		
		JButton btnNewButton = new JButton("Next");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(list.isSelectionEmpty()){
					JOptionPane.showMessageDialog(null, "Please select at least one line to include on the map.", "Error", JOptionPane.WARNING_MESSAGE);
					return;
				}
				for(int i = 0; i < list.getModel().getSize(); i++){
					if(list.getSelectionModel().isSelectedIndex(i)){
						list.getModel().getElementAt(i).usingInMap = true;
					}
				}
				
				ctrl.returnFromLinePickerPanel();
			}
		});
		add(btnNewButton, BorderLayout.SOUTH);
		
		JLabel lblSelectLinesTo = new JLabel("Select Lines to Include on Map:");
		add(lblSelectLinesTo, BorderLayout.NORTH);
		


	}

}
