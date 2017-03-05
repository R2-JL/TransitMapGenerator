package GUIs;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Canvas;
import java.awt.Color;

public class HelloGraphics extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					HelloGraphics frame = new HelloGraphics();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	private void draw(Graphics g){
		Graphics2D g2 = (Graphics2D) g;
		
		g2.setPaint(Color.RED);
		
		int w = getWidth();
		int h = getHeight();
		
		g2.drawLine(100, 100, w-100, h-100);
	}
	
	@Override
	public void paint(Graphics g){
		super.paint(g);
		draw(g);
	}

	/**
	 * Create the frame.
	 */
	public HelloGraphics() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		

	}

}
