package GUIs;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Stroke;
import java.awt.geom.Line2D;
import Base.SystemModel;
import Base.Node;
import Base.Line;

import javax.swing.JPanel;

public class BasicRenderer extends JPanel {
	
	private SystemModel sys;
	
	public static int STN_BLOB_RADIUS = 5;
	public static float LINE_STROKE = (float)2.5;
	public static float STN_BLOB_STROKE = (float) 2.5;
	public static Color BACKGROUND_COLOR = Color.WHITE;
	public static Color TXT_COLOR = Color.BLACK;
	public static int NODE_BORDER_LEFT = 20;
	public static int NODE_BORDER_UP = 40;
	public static int NODE_BORDER_DOWN = 20;
	public static int NODE_BORDER_RIGHT = 150;
	
	private double minX, maxX, minY, maxY;
	
	/**
	 * Create the panel.
	 */
	
	public BasicRenderer(SystemModel s) {
		sys = s;
	}
	
	@Override
	public void paint(Graphics G){
		super.paint(G);
		Graphics2D g = (Graphics2D) G;
		
		Rectangle r = getBounds();
		minX = r.getMinX() + NODE_BORDER_LEFT;
		maxX = r.getMaxX() - NODE_BORDER_RIGHT;
		minY = r.getMinY() + NODE_BORDER_UP;
		maxY = r.getMaxY() - NODE_BORDER_DOWN;
		
//		Rectangle r = getBounds();
//		System.out.println("x: " + r.getMinX() + ", " + r.getMaxX() + ". y: " + r.getMinY() + ", " + r.getMaxY() + ".");
//		
//		g.draw(new Line2D.Double(r.getMinX(), r.getMinY(), r.getMaxX(), r.getMaxY()));
		
		g.setStroke(new BasicStroke((float)2.5));
		//Draw lines
		for(Line l : sys.lines){
			g.setColor(l.color);
			for(int i = 0; i < (l.nodes.size() - 1); i++){
				g.drawLine(getNodeX(l.nodes.get(i)), getNodeY(l.nodes.get(i)), getNodeX(l.nodes.get(i + 1)), getNodeY(l.nodes.get(i + 1)));
			}
		}
		
		//Draw stations
		
		for(Node n : sys.nodes){
			if(n instanceof Base.Station){
				g.setColor(g.getBackground());
				g.fillOval(getNodeX(n) - (STN_BLOB_RADIUS), getNodeY(n) - STN_BLOB_RADIUS, 2 * STN_BLOB_RADIUS, 2 * STN_BLOB_RADIUS);
				g.setColor(TXT_COLOR);
				g.drawOval(getNodeX(n) - (STN_BLOB_RADIUS), getNodeY(n) - STN_BLOB_RADIUS, 2 * STN_BLOB_RADIUS, 2 *STN_BLOB_RADIUS);
			}
		}
		
		//Draw station labels
		g.setColor(TXT_COLOR);
		for(Node n: sys.nodes){
			if(n instanceof Base.Station){
				g.drawString(((Base.Station)n).name, getNodeX(n) + STN_BLOB_RADIUS, getNodeY(n) - STN_BLOB_RADIUS);
			}
		}
	}
	
	private int getNodeX(Node n){
		double relativeX = n.x / SystemModel.X_SIZE;
		relativeX *= (maxX - minX);
		return (int) relativeX + NODE_BORDER_LEFT;
	}
	
	private int getNodeY(Node n){
		double relativeY = n.y / SystemModel.Y_SIZE;
		relativeY = 1.0 - relativeY;
		relativeY *= (maxY - minY);
		return (int) relativeY + NODE_BORDER_UP;
	}
}
