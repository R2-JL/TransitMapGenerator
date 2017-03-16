package Base;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import GTFS.GTFSReader;

public class DataLoadController {
	private GUIs.TransitMapGenerator mainWindow;
	private String filepath;
	public GTFS.GTFSSystem gtfsSystem;
	public SystemModel system;
	private GUIs.LinePickerPanel linePickerPanel;
	private GUIs.TripPickerPanel tripPickerPanel;
	private int lineIndex = 0;
	private ArrayList<GTFS.GTFSLine> lines;
	
	public DataLoadController(GUIs.TransitMapGenerator window){
		mainWindow = window;
	}
	
	public SystemModel doDataLoad(){
		chooseFilepath();
		readGTFS();
		userChooseLines();
		return system;
	}
	
	private void chooseFilepath(){
		//TODO implement properly
		filepath = "C:\\GTFS\\WMATA";
	}
	
	private void readGTFS(){
		GTFSReader read = new GTFSReader(filepath);
		try {
			read.readStops();
			read.readLines();
			read.readTrips();
			read.readStopCalls();
			
			gtfsSystem = read.getSystem();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//set up panels here
		linePickerPanel = new GUIs.LinePickerPanel(this, gtfsSystem);
		linePickerPanel.setVisible(false);
		mainWindow.add(linePickerPanel);

		
	}
	
	private void userChooseLines(){
		//mainWindow.removeAll();
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
		System.out.println("SystemModel generated");
		System.out.println("");
	}
}
