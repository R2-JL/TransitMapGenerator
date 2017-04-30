package GUIs;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Stroke;
import java.awt.geom.Line2D;
import Base.SystemModel;
import Base.Node;
import Base.Line;
import Base.Segment;

import javax.swing.JPanel;

public class Renderer extends JPanel {
	
	private SystemModel sys;
	
	public static int STN_BLOB_RADIUS = 6;
	public static float LINE_STROKE = (float)3;
	public static float STN_BLOB_STROKE = (float) 3;
	public static Color BACKGROUND_COLOR = Color.WHITE;
	public static Color TXT_COLOR = Color.BLACK;
	public static int NODE_BORDER_LEFT = 20;
	public static int NODE_BORDER_UP = 20;
	public static int NODE_BORDER_DOWN = 40;
	public static int NODE_BORDER_RIGHT = 200;
	public static int FONT_SIZE = 12;
	public static float LINE_OFFSET = (float) 3;
	
	public static boolean OVERLAP_LINES = false;
	public static boolean DRAW_STN_LABELS = true;
	public static boolean DRAW_LINE_LABELS = true;
	
	
	
	private double minX, maxX, minY, maxY;
	
	/**
	 * Create the panel.
	 */
	
	public Renderer(SystemModel s) {
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
		if(OVERLAP_LINES){
			for(Line l : sys.lines){
				g.setColor(l.color);
				for(int i = 0; i < (l.nodes.size() - 1); i++){
					drawLine(g, getNodeX(l.nodes.get(i)), getNodeY(l.nodes.get(i)), getNodeX(l.nodes.get(i + 1)), getNodeY(l.nodes.get(i + 1)));
				}
			}
		} else {
		
		//Draw segments
		
			for(Segment s : sys.segments){
				//g.drawLine(getNodeX(s.start), getNodeY(s.start), getNodeX(s.end), getNodeY(s.end));
				//calculate offsets
				double dX = getNodeX(s.end) - getNodeX(s.start);
				double dY = getNodeY(s.end) - getNodeY(s.start);
				
				double xOffset = dY / Math.sqrt((dX * dX) + (dY * dY));
				double yOffset = (0 - dX) / Math.sqrt((dX * dX) + (dY * dY));
				
				xOffset *= LINE_OFFSET;
				yOffset *= LINE_OFFSET;
				
				for(int i = 0; i < s.lines.size(); i++){
					Line l = s.lines.get(i);
					double offsetMultiplier = (double) i - ((double)(s.lines.size() - 1) / 2);
					double x1 = getNodeX(s.start) + (xOffset * offsetMultiplier);
					double y1 = getNodeY(s.start) + (yOffset * offsetMultiplier);
					double x2 = getNodeX(s.end) + (xOffset * offsetMultiplier);
					double y2 = getNodeY(s.end) + (yOffset * offsetMultiplier);
					g.setColor(l.color);
					drawLine(g, x1, y1, x2, y2);
				}
			}
		}
		
		//Draw stations
		
		for(Node n : sys.nodes){
			if(n instanceof Base.Station){
				g.setColor(g.getBackground());
				g.fillOval((int) getNodeX(n) - (STN_BLOB_RADIUS), (int) getNodeY(n) - STN_BLOB_RADIUS, 2 * STN_BLOB_RADIUS, 2 * STN_BLOB_RADIUS);
				g.setColor(TXT_COLOR);
				g.drawOval((int) getNodeX(n) - (STN_BLOB_RADIUS), (int) getNodeY(n) - STN_BLOB_RADIUS, 2 * STN_BLOB_RADIUS, 2 *STN_BLOB_RADIUS);
			}
		}
		
		//Draw station labels
		g.setColor(TXT_COLOR);
		for(Node n: sys.nodes){
			if(n instanceof Base.Station){
				if(DRAW_STN_LABELS){
					g.setColor(TXT_COLOR);
					g.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, FONT_SIZE));
					g.drawString(((Base.Station)n).name, (int) getNodeX(n) + STN_BLOB_RADIUS + 2, (int) getNodeY(n) - STN_BLOB_RADIUS);
				}
			}
				
			//Draw line labels
			if(DRAW_LINE_LABELS){
				for(int i = 0; i < n.terminatingLines.size(); i++){
					g.setColor(n.terminatingLines.get(i).color);
					g.setFont(new Font(Font.SANS_SERIF ,Font.BOLD, FONT_SIZE));
					g.drawString(n.terminatingLines.get(i).toString(), (int) getNodeX(n) + STN_BLOB_RADIUS + 2, (int) getNodeY(n) - STN_BLOB_RADIUS + ((FONT_SIZE + 2) * (i + 1)));
				}
			}
		}
	}
	
	private double getNodeX(Node n){
		double relativeX = n.x / SystemModel.X_SIZE;
		relativeX *= (maxX - minX);
		return relativeX + NODE_BORDER_LEFT;
	}
	
	private double getNodeY(Node n){
		double relativeY = n.y / SystemModel.Y_SIZE;
		relativeY = 1.0 - relativeY;
		relativeY *= (maxY - minY);
		return relativeY + NODE_BORDER_UP;
	}
	
	private void drawLine(Graphics2D g, double x1, double y1, double x2, double y2){
		g.draw(new Line2D.Double(x1, y1, x2, y2));
	}
}
