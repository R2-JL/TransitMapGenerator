package GUIs;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JList;
import javax.swing.JOptionPane;

import java.awt.BorderLayout;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import javax.swing.JFormattedTextField;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;
import com.jgoodies.forms.layout.FormSpecs;
import javax.swing.ListSelectionModel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.ListSelectionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class TripPickerPanel extends JPanel {
	private JTextField colorBox;
	private GTFS.GTFSSystem sys;
	private GTFS.GTFSLine line;
	private JList<GTFS.GTFSTrip> tripList;
	private JList<GTFS.GTFSStop> stopList;
	private JLabel lblStationsServed;
	private JLabel lblSelectTripFor;
	private Base.DataLoadController ctrl;
	

	/**
	 * Create the panel.
	 */
	public TripPickerPanel(Base.DataLoadController c, GTFS.GTFSSystem s) {
		ctrl = c;
		sys = s;
		setLayout(new FormLayout(new ColumnSpec[] {
				FormSpecs.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"),
				FormSpecs.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"),
				FormSpecs.RELATED_GAP_COLSPEC,},
			new RowSpec[] {
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				RowSpec.decode("default:grow"),
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,}));
		
		lblSelectTripFor = new JLabel("Select trip for line: ");
		add(lblSelectTripFor, "2, 2, 3, 1");
		
		JScrollPane scrollPane = new JScrollPane();
		add(scrollPane, "2, 4, 1, 7, fill, fill");
		
		tripList = new JList<GTFS.GTFSTrip>();
		tripList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tripList.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent arg0) {
				//change model of stopList to reflect current trip
				if(tripList.isSelectionEmpty())
					return;
				GTFS.GTFSTrip selectedTrip = tripList.getSelectedValue();
				
				DefaultListModel<GTFS.GTFSStop> stopModel = new DefaultListModel<GTFS.GTFSStop>();
				for(GTFS.GTFSStopCall s : selectedTrip.stops){
					stopModel.addElement(sys.stops.get(s.stopId));
				}
				stopList.setModel(stopModel);
			}
		});
		scrollPane.setViewportView(tripList);
		
		JLabel lblLineColor = new JLabel("Line Color");
		add(lblLineColor, "4, 4");
		
		colorBox = new JTextField();
		add(colorBox, "4, 6, fill, default");
		colorBox.setColumns(10);
		
		lblStationsServed = new JLabel("Stations Served:");
		add(lblStationsServed, "4, 8");
		
		JScrollPane scrollPane_1 = new JScrollPane();
		add(scrollPane_1, "4, 10, fill, fill");
		
		stopList = new JList<GTFS.GTFSStop>();
		stopList.setEnabled(false);
		scrollPane_1.setViewportView(stopList);
		
		JButton btnNext = new JButton("Next");
		btnNext.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(tripList.isSelectionEmpty()){
					JOptionPane.showMessageDialog(null, "Please select a trip to represent the line.", "Error", JOptionPane.WARNING_MESSAGE);
					return;
				}
				line.representativeTrip = tripList.getSelectedValue();
				line.color = colorBox.getText();
				ctrl.returnFromTripPickerPanel();
			}
		});
		add(btnNext, "4, 12");

	}
	
	public void setLine(GTFS.GTFSLine l){
		line = l;
		tripList.clearSelection();
		
		lblSelectTripFor.setText("Select trip for line " + l.toString());
		
		//build list model for tripList
		DefaultListModel<GTFS.GTFSTrip> mod = new DefaultListModel<GTFS.GTFSTrip>();
		ArrayList<GTFS.GTFSTrip> lst = sys.getTripsForLine(line.id);
		for(GTFS.GTFSTrip t : lst){
			mod.addElement(t);
		}
		
		tripList.setModel(mod);
		colorBox.setText(line.color);
	}

}
