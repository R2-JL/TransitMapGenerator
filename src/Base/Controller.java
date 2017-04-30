package Base;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import GTFS.GTFSReader;

public class Controller {
	private GUIs.TransitMapGenerator mainWindow;
	private File gtfsFeed;
	public GTFS.GTFSSystem gtfsSystem;
	public SystemModel system;
	private GUIs.LinePickerPanel linePickerPanel;
	private GUIs.TripPickerPanel tripPickerPanel;
	private int lineIndex = 0;
	private ArrayList<GTFS.GTFSLine> lines;
	
	public Controller(GUIs.TransitMapGenerator window){
		mainWindow = window;
	}
	
	public SystemModel doDataLoad(){
		chooseFilepath();
		readGTFS();
		userChooseLines();
		return system;
	}
	
	private void chooseFilepath(){
		JFileChooser chooser = new JFileChooser();
		FileNameExtensionFilter filter = new FileNameExtensionFilter("ZIP archives", "zip");
		chooser.setFileFilter(filter);
		int returnVal = chooser.showOpenDialog(null);
		if(returnVal == JFileChooser.APPROVE_OPTION){
			gtfsFeed = chooser.getSelectedFile();
		} else {
			System.exit(1);
		}
	}
	
	private void readGTFS(){
		GTFSReader read;
		try {
			read = new GTFSReader(gtfsFeed);
			read.readStops();
			read.readLines();
			read.readTrips();
			read.readStopCalls();
			
			gtfsSystem = read.getSystem();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//set up panels here
		linePickerPanel = new GUIs.LinePickerPanel(this, gtfsSystem);
		linePickerPanel.setVisible(false);
		mainWindow.add(linePickerPanel);

		
	}
	
	private void userChooseLines(){
		linePickerPanel.setVisible(true);
		mainWindow.repaint();
	}
	
	public void returnFromLinePickerPanel(){
		linePickerPanel.setVisible(false);
		mainWindow.remove(linePickerPanel);
		tripPickerPanel = new GUIs.TripPickerPanel(this, gtfsSystem);
		tripPickerPanel.setVisible(false);
		mainWindow.add(tripPickerPanel);
		
		lines = gtfsSystem.getLines(true);
		userChooseTrips(lines.get(lineIndex));
	}
	
	private void userChooseTrips(GTFS.GTFSLine line){
		tripPickerPanel.setLine(line);
		tripPickerPanel.setVisible(true);
	}
	
	public void returnFromTripPickerPanel(){
		lineIndex++;
		if(lineIndex >= lines.size()){
			tripPickerPanel.setVisible(false);
			generateSystemModel();
		} else {
			userChooseTrips(lines.get(lineIndex));
		}
	}
	
	private void generateSystemModel(){
		SystemModel sys = new SystemModel(gtfsSystem);
		
		//this may move later, but for now:
		//render map
		
		mainWindow.remove(tripPickerPanel);
		mainWindow.add(new GUIs.Renderer(sys));
	}
}
