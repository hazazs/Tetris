package hu.hazazs.tetris;

import java.util.Arrays;
import hu.hazazs.tetris.blocks.Block;
import hu.hazazs.tetris.blocks.MiniBlock;

final class TetrisGame implements Runnable {

	private final MainWindow mainWindow;
	private final Level level;
	private int score;
	private Block block;
	private Block nextBlock;
	private boolean drop;
	private boolean pause;

	TetrisGame(MainWindow mainWindow) {
		this.mainWindow = mainWindow;
		level = new Level();
		nextBlock = Block.random();
		mainWindow.setTetrisGame(this);
	}

	@Override
	public void run() {
		while (true) {
			if (!isValid(block = nextBlock)) {
				draw();
				mainWindow.getNextBlockTextArea().setText("");
				break;
			}
			nextBlock = Block.random();
			mainWindow.getNextBlockTextArea().setText(createStringFrom(nextBlock.toDrawBuffer()));
			drop = false;
			while (true) {
				draw();
				sleep(millisecondsByScore());
				if (!canMoveDown()) {
					break;
				}
				moveDown();
			}
			buildBlockIntoLevel();
			scoreBy(level.checkFullRows());
		}
		mainWindow.getControlButton().setText("RESTART");
		gameOverAnimation();
		gameOverScreen();
	}

	private void draw() {
		mainWindow.getGameArea().setText(createStringFrom(drawBlockIntoLevel()));
	}

	private String createStringFrom(String[][] drawBuffer) {
		StringBuilder builder = new StringBuilder();
		for (int row = 0; row < drawBuffer.length; row++) {
			for (int column = 0; column < drawBuffer[0].length; column++) {
				builder.append(MiniBlock.BLOCK.equals(drawBuffer[row][column]) ? MiniBlock.BLOCK : "  ");
			}
			builder.append("\n");
		}
		return builder.toString();
	}

	private String[][] drawBlockIntoLevel() {
		String[][] copy = Arrays.stream(level.getLevel()).map(String[]::clone).toArray(String[][]::new);
		return buildBlockInto(copy);
	}

	private void buildBlockIntoLevel() {
		buildBlockInto(level.getLevel());
	}

	private String[][] buildBlockInto(String[][] level) {
		for (MiniBlock miniBlock : block.getMiniBlocks()) {
			int row = block.getRow() + miniBlock.getRowOffset();
			int column = block.getColumn() + miniBlock.getColumnOffset();
			if (row >= 0) {
				level[row][column] = MiniBlock.BLOCK;
			}
		}
		return level;
	}

	private void sleep(long milliseconds) {
		try {
			Thread.sleep(milliseconds);
		} catch (InterruptedException exception) {
			exception.printStackTrace();
		}
	}

	private long millisecondsByScore() {
		if (score < 150) {
			return 550L;
		} else if (score < 300) {
			return 450L;
		} else if (score < 450) {
			return 350L;
		} else if (score < 600) {
			return 250L;
		}
		return 150L;
	}

	private void scoreBy(int fullRowCounter) {
		switch (fullRowCounter) {
			case 0:
				return;
			case 1:
				score += 4;
				break;
			case 2:
				score += 10;
				break;
			case 3:
				score += 30;
				break;
			case 4:
				score += 120;
				break;
		}
		mainWindow.getScoreTextField().setText(String.valueOf(score));
	}

	void rotate() {
		if (!drop && canRotate()) {
			block.rotate();
			draw();
		}
	}

	private boolean canRotate() {
		return isValid(block.copy().rotate());
	}

	void moveLeft() {
		if (!drop && canMoveLeft()) {
			block.moveLeft();
			draw();
		}
	}

	private boolean canMoveLeft() {
		return isValid(block.copy().moveLeft());
	}

	void moveDown() {
		if (!pause && canMoveDown()) {
			block.moveDown();
			draw();
		}
	}

	private boolean canMoveDown() {
		return isValid(block.copy().moveDown());
	}

	void moveRight() {
		if (!drop && canMoveRight()) {
			block.moveRight();
			draw();
		}
	}

	private boolean canMoveRight() {
		return isValid(block.copy().moveRight());
	}

	private boolean isValid(Block block) {
		for (MiniBlock miniBlock : block.getMiniBlocks()) {
			int row = block.getRow() + miniBlock.getRowOffset();
			int column = block.getColumn() + miniBlock.getColumnOffset();
			if (column < 0 || column > Level.WIDTH - 1 || row > Level.HEIGHT - 1
					|| (row >= 0 && MiniBlock.BLOCK.equals(level.getLevel()[row][column]))) {
				return false;
			}
		}
		return true;
	}

	private void gameOverAnimation() {
		mainWindow.getControlButton().setEnabled(false);
		for (int row = Level.HEIGHT - 1; row >= 0; row--) {
			for (int column = 0; column < Level.WIDTH; column++) {
				level.getLevel()[row][column] = MiniBlock.BLOCK;
				draw();
			}
			sleep(100L);
		}
		block.getMiniBlocks().clear();
		for (int row = 0; row < Level.HEIGHT; row++) {
			for (int column = 0; column < Level.WIDTH; column++) {
				level.getLevel()[row][column] = "  ";
				draw();
			}
			sleep(100L);
		}
		mainWindow.getControlButton().setEnabled(true);
	}

	private void gameOverScreen() {
		mainWindow.getLeftPanel().removeAll();
		mainWindow.getFinalScore().setText(String.format("SCORE: %d", score));
		mainWindow.getLeftPanel().add(mainWindow.getFinalScore());
		mainWindow.getLeftPanel().add(mainWindow.getGameOverLabel());
		mainWindow.getLeftPanel().repaint();
	}

	void drop() {
		drop = true;
		while (canMoveDown()) {
			moveDown();
		}
	}

	void pauseAndResume() {
		pause = !pause;
	}

}