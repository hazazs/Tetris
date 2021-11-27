package hu.hazazs.tetris;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class MainWindow {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainWindow window = new MainWindow();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public MainWindow() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 600);
		frame.setResizable(false);
		frame.setIconImage(Toolkit.getDefaultToolkit().getImage("src/ico.png"));
		frame.setTitle("Tetris 1.0");
		//frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//frame.getContentPane().setLayout(new BorderLayout(0, 0));
		
		GridBagLayout gbl_sidePanel = new GridBagLayout();
		gbl_sidePanel.columnWidths = new int[] {0};
		gbl_sidePanel.rowHeights = new int[] {60, 15, 30, 30, 100, 0};
		gbl_sidePanel.columnWeights = new double[]{0.0};
		gbl_sidePanel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 1.0};
		JPanel sidePanel = new JPanel(gbl_sidePanel);
		frame.getContentPane().add(sidePanel, BorderLayout.EAST);
		
		JButton startButton = new JButton("START");
		startButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		GridBagConstraints gbc_startButton = new GridBagConstraints();
		gbc_startButton.anchor = GridBagConstraints.NORTH;
		gbc_startButton.insets = new Insets(10, 0, 0, 0);
		gbc_startButton.gridx = 0;
		gbc_startButton.gridy = 0;
		sidePanel.add(startButton, gbc_startButton);
		
		JLabel scoreLabel = new JLabel("Score:");
		GridBagConstraints gbc_scoreLabel = new GridBagConstraints();
		gbc_scoreLabel.anchor = GridBagConstraints.WEST;
		gbc_scoreLabel.insets = new Insets(0, 5, 0, 0);
		gbc_scoreLabel.gridx = 0;
		gbc_scoreLabel.gridy = 1;
		sidePanel.add(scoreLabel, gbc_scoreLabel);
		
		JTextField scoreTextField = new JTextField("0", 10);
		scoreTextField.setEditable(false);
		GridBagConstraints gbc_scoreTextField = new GridBagConstraints();
		gbc_scoreTextField.fill = GridBagConstraints.HORIZONTAL;
		gbc_scoreTextField.insets = new Insets(0, 5, 0, 5);
		gbc_scoreTextField.gridx = 0;
		gbc_scoreTextField.gridy = 2;
		sidePanel.add(scoreTextField, gbc_scoreTextField);
		
		JLabel nextBlockLabel = new JLabel("Next block:");
		GridBagConstraints gbc_nextBlockLabel = new GridBagConstraints();
		gbc_nextBlockLabel.anchor = GridBagConstraints.SOUTHWEST;
		gbc_nextBlockLabel.insets = new Insets(0, 5, 0, 0);
		gbc_nextBlockLabel.gridx = 0;
		gbc_nextBlockLabel.gridy = 3;
		sidePanel.add(nextBlockLabel, gbc_nextBlockLabel);
		
		JTextArea nextBlockTextArea = new JTextArea();
		nextBlockTextArea.setEditable(false);
		GridBagConstraints gbc_nextBlockTextArea = new GridBagConstraints();
		gbc_nextBlockTextArea.fill = GridBagConstraints.BOTH;
		gbc_nextBlockTextArea.insets = new Insets(5, 5, 0, 5);
		gbc_nextBlockTextArea.gridx = 0;
		gbc_nextBlockTextArea.gridy = 4;
		sidePanel.add(nextBlockTextArea, gbc_nextBlockTextArea);
		
		JTextArea mainWindow = new JTextArea();
		mainWindow.setEditable(false);
		frame.getContentPane().add(mainWindow, BorderLayout.CENTER);
	}
	
}