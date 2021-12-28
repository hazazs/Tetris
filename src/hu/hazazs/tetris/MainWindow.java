package hu.hazazs.tetris;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.concurrent.Executors;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.UIManager;

public final class MainWindow {

	private final JFrame frame = new JFrame();
	private final JTextArea gameArea = new JTextArea();
	private final JButton controlButton = new JButton("START");
	private final JTextField scoreTextField = new JTextField("0");
	private final JTextArea nextBlockTextArea = new JTextArea();
	private TetrisGame tetrisGame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					MainWindow mainWindow = new MainWindow();
					mainWindow.frame.setVisible(true);
				} catch (Exception exception) {
					exception.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	private MainWindow() {
		initialize();
	}

	JTextArea getGameArea() {
		return gameArea;
	}

	JButton getControlButton() {
		return controlButton;
	}

	JTextField getScoreTextField() {
		return scoreTextField;
	}

	JTextArea getNextBlockTextArea() {
		return nextBlockTextArea;
	}

	void setTetrisGame(TetrisGame tetrisGame) {
		this.tetrisGame = tetrisGame;
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame.setBounds(100, 100, 487, 699);
		frame.setResizable(false);
		frame.setIconImage(Toolkit.getDefaultToolkit().getImage("src/ico.png"));
		frame.setTitle("Tetris 1.0");

		gameArea.setEditable(false);
		gameArea.setFont(new Font("Consolas", Font.BOLD, 31));
		gameArea.setHighlighter(null);
		gameArea.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent keyEvent) {
				if (!"START".equals(controlButton.getText())) {
					switch (keyEvent.getKeyCode()) {
						case KeyEvent.VK_UP:
							tetrisGame.rotate();
							break;
						case KeyEvent.VK_LEFT:
							tetrisGame.moveBlockToTheLeft();
							break;
						case KeyEvent.VK_RIGHT:
							tetrisGame.moveBlockToTheRight();
							break;
						case KeyEvent.VK_DOWN:
							tetrisGame.dropBlock();
					}
				}
			}
		});
		frame.getContentPane().add(gameArea, BorderLayout.CENTER);

		GridBagLayout gbl_sidePanel = new GridBagLayout();
		gbl_sidePanel.columnWidths = new int[] { 131 };
		gbl_sidePanel.rowHeights = new int[] { 60, 15, 30, 30, 100, 0 };
		gbl_sidePanel.columnWeights = new double[] { 0.0 };
		gbl_sidePanel.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 1.0 };
		JPanel sidePanel = new JPanel(gbl_sidePanel);
		frame.getContentPane().add(sidePanel, BorderLayout.EAST);

		controlButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				switch (controlButton.getText()) {
					case "START":
						startGame();
						controlButton.setText("PAUSE");
						break;
					case "PAUSE":
						tetrisGame.pauseAndResume();
						controlButton.setText("RESUME");
						break;
					case "RESUME":
						tetrisGame.pauseAndResume();
						controlButton.setText("PAUSE");
						break;
					case "RESTART":
						startGame();
						gameArea.setEnabled(true);
						controlButton.setText("PAUSE");
						break;
				}
				gameArea.requestFocusInWindow();
			}
		});
		GridBagConstraints gbc_controlButton = new GridBagConstraints();
		gbc_controlButton.anchor = GridBagConstraints.NORTH;
		gbc_controlButton.insets = new Insets(10, 0, 0, 0);
		gbc_controlButton.gridx = 0;
		gbc_controlButton.gridy = 0;
		sidePanel.add(controlButton, gbc_controlButton);

		JLabel scoreLabel = new JLabel("Score:");
		GridBagConstraints gbc_scoreLabel = new GridBagConstraints();
		gbc_scoreLabel.anchor = GridBagConstraints.SOUTHWEST;
		gbc_scoreLabel.insets = new Insets(0, 5, 0, 0);
		gbc_scoreLabel.gridx = 0;
		gbc_scoreLabel.gridy = 1;
		sidePanel.add(scoreLabel, gbc_scoreLabel);

		scoreTextField.setEditable(false);
		GridBagConstraints gbc_scoreTextField = new GridBagConstraints();
		gbc_scoreTextField.anchor = GridBagConstraints.NORTH;
		gbc_scoreTextField.fill = GridBagConstraints.HORIZONTAL;
		gbc_scoreTextField.insets = new Insets(5, 5, 0, 5);
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

		nextBlockTextArea.setEditable(false);
		nextBlockTextArea.setFont(new Font("Consolas", Font.BOLD, 28));
		nextBlockTextArea.setHighlighter(null);
		nextBlockTextArea.setBackground(UIManager.getColor("Button.background"));
		GridBagConstraints gbc_nextBlockTextArea = new GridBagConstraints();
		gbc_nextBlockTextArea.fill = GridBagConstraints.BOTH;
		gbc_nextBlockTextArea.insets = new Insets(5, 5, 0, 5);
		gbc_nextBlockTextArea.gridx = 0;
		gbc_nextBlockTextArea.gridy = 4;
		sidePanel.add(nextBlockTextArea, gbc_nextBlockTextArea);
	}

	private void startGame() {
		Executors.newSingleThreadExecutor().execute(new TetrisGame(this));
	}

}