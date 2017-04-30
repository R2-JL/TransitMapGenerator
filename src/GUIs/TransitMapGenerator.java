package GUIs;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class TransitMapGenerator extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7286020409255004553L;
	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		if(args.length > 0){
			Renderer.OVERLAP_LINES = args[0].toUpperCase().contains("O");
			Renderer.DRAW_STN_LABELS = !(args[0].toUpperCase().contains("S"));
			Renderer.DRAW_LINE_LABELS = !(args[0].toUpperCase().contains("L"));
		}
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					
					TransitMapGenerator frame = new TransitMapGenerator("Transit Map Generator");
					frame.setVisible(true);
					Base.Controller dlc = new Base.Controller(frame);
					dlc.doDataLoad();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public TransitMapGenerator(String title) {
		super(title);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setExtendedState(java.awt.Frame.MAXIMIZED_BOTH);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
	}

}
