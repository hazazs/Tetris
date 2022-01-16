package hu.hazazs.tetris;

import java.awt.Color;
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
import java.io.File;
import java.io.IOException;
import java.util.concurrent.Executors;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;

final class MainWindow {

	private final JFrame frame = new JFrame();
	private final JPanel leftPanel = new JPanel();
	private final JTextArea gameArea = new JTextArea();
	private final JLabel finalScore = new JLabel();
	private final JLabel gameOverLabel = new JLabel(getIconFrom("src/game_over.jpg"));
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

	JPanel getLeftPanel() {
		return leftPanel;
	}

	JTextArea getGameArea() {
		return gameArea;
	}

	JLabel getFinalScore() {
		return finalScore;
	}

	JLabel getGameOverLabel() {
		return gameOverLabel;
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
		frame.getContentPane().setLayout(null);

		leftPanel.setBounds(0, 0, 340, 660);
		leftPanel.setLayout(null);
		frame.getContentPane().add(leftPanel);

		JLabel tetrisLabel = new JLabel(getIconFrom("src/tetris.jpg"));
		tetrisLabel.setBounds(0, 0, 340, 660);
		leftPanel.add(tetrisLabel);

		gameArea.setBounds(0, 0, 340, 660);
		gameArea.setEditable(false);
		gameArea.setFont(new Font("Consolas", Font.BOLD, 31));
		gameArea.setHighlighter(null);
		gameArea.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent keyEvent) {
				if ("PAUSE".equals(controlButton.getText())) {
					switch (keyEvent.getKeyCode()) {
						case KeyEvent.VK_UP:
							tetrisGame.rotate();
							break;
						case KeyEvent.VK_LEFT:
							tetrisGame.moveLeft();
							break;
						case KeyEvent.VK_DOWN:
							tetrisGame.moveDown();
							break;
						case KeyEvent.VK_RIGHT:
							tetrisGame.moveRight();
							break;
						case ' ':
							tetrisGame.drop();
							break;
					}
				}
			}
		});

		finalScore.setBounds(0, 220, 340, 30);
		finalScore.setHorizontalAlignment(SwingConstants.CENTER);
		finalScore.setFont(new Font("Stencil", Font.PLAIN, 30));
		finalScore.setForeground(Color.RED);

		gameOverLabel.setBounds(0, 0, 340, 660);

		GridBagLayout gbl_rightPanel = new GridBagLayout();
		gbl_rightPanel.columnWidths = new int[] { 131 };
		gbl_rightPanel.rowHeights = new int[] { 60, 15, 30, 30, 100, 0 };
		gbl_rightPanel.columnWeights = new double[] { 0.0 };
		gbl_rightPanel.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 1.0 };

		JPanel rightPanel = new JPanel(gbl_rightPanel);
		rightPanel.setBounds(340, 0, 131, 660);
		frame.getContentPane().add(rightPanel);

		controlButton.setFocusable(false);
		controlButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				switch (controlButton.getText()) {
					case "START":
						leftPanel.add(gameArea);
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
						leftPanel.add(gameArea);
						startGame();
						scoreTextField.setText("0");
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
		rightPanel.add(controlButton, gbc_controlButton);

		JLabel scoreLabel = new JLabel("Score:");
		GridBagConstraints gbc_scoreLabel = new GridBagConstraints();
		gbc_scoreLabel.anchor = GridBagConstraints.SOUTHWEST;
		gbc_scoreLabel.insets = new Insets(0, 5, 0, 0);
		gbc_scoreLabel.gridx = 0;
		gbc_scoreLabel.gridy = 1;
		rightPanel.add(scoreLabel, gbc_scoreLabel);

		scoreTextField.setEditable(false);
		scoreTextField.setHighlighter(null);
		GridBagConstraints gbc_scoreTextField = new GridBagConstraints();
		gbc_scoreTextField.anchor = GridBagConstraints.NORTH;
		gbc_scoreTextField.fill = GridBagConstraints.HORIZONTAL;
		gbc_scoreTextField.insets = new Insets(5, 5, 0, 5);
		gbc_scoreTextField.gridx = 0;
		gbc_scoreTextField.gridy = 2;
		rightPanel.add(scoreTextField, gbc_scoreTextField);

		JLabel nextBlockLabel = new JLabel("Next block:");
		GridBagConstraints gbc_nextBlockLabel = new GridBagConstraints();
		gbc_nextBlockLabel.anchor = GridBagConstraints.SOUTHWEST;
		gbc_nextBlockLabel.insets = new Insets(0, 5, 0, 0);
		gbc_nextBlockLabel.gridx = 0;
		gbc_nextBlockLabel.gridy = 3;
		rightPanel.add(nextBlockLabel, gbc_nextBlockLabel);

		nextBlockTextArea.setEditable(false);
		nextBlockTextArea.setFont(new Font("Consolas", Font.BOLD, 28));
		nextBlockTextArea.setHighlighter(null);
		nextBlockTextArea.setBackground(UIManager.getColor("Button.background"));
		GridBagConstraints gbc_nextBlockTextArea = new GridBagConstraints();
		gbc_nextBlockTextArea.fill = GridBagConstraints.BOTH;
		gbc_nextBlockTextArea.insets = new Insets(5, 5, 0, 5);
		gbc_nextBlockTextArea.gridx = 0;
		gbc_nextBlockTextArea.gridy = 4;
		rightPanel.add(nextBlockTextArea, gbc_nextBlockTextArea);
	}

	private ImageIcon getIconFrom(String path) {
		ImageIcon icon = null;
		try {
			icon = new ImageIcon(ImageIO.read(new File(path)));
		} catch (IOException exception) {
			exception.printStackTrace();
		}
		return icon;
	}

	private void startGame() {
		Executors.newSingleThreadExecutor().execute(new TetrisGame(this));
	}

}