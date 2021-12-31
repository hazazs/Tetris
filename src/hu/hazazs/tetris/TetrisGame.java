package hu.hazazs.tetris;

import java.util.Arrays;
import hu.hazazs.tetris.blocks.Block;
import hu.hazazs.tetris.blocks.MiniBlock;

final class TetrisGame implements Runnable {

	// Block osztály absztraktá tétele (randomizálás statikus factory metódussal)
	// közös Z és Z_MIRRORED forgási logika

	// SPACE-re azonnal lemegy, lefelé csak begyorsít (és azonnal reagál, nincs 1 másodperces delay) (moveDown-ba esetleg feltételként pluszba a !hasReachedBottom hogy többszöri lenyomásra ne menjen bele a pályába)
	// gyorsuljon a pontok növekedésével (kezdő sebesség)
	// indulóképernyő + GAME OVER felirat (a gameover felirat esetén már lehet nem kell visszaadni a focust a gameareanak) + score
	// különböző színű blokkok (1.2)
	// fantommozgás mindenhol? + isValid() metódus az isBlock() helyett (a fantom után a drawBlock és buildBlock metódust egybe lehet gyúrni) (buildBlocknál már nem fog kelleni a row - 1)
	// CTRL+SHIFT O,F,S mindenhol
	// mit lehet még összegyúrni?

	// miért alacsonyabbak az első sorok mind a gameAreaban, mind a nextBlockTextAreaban? (vagy a többi hosszabb ?  - a fontméret ezt indokolná)
	// miért lesz darabos a nextBlock kijelöléskor? (eleve nem is lehetne kijelölni)
	// valahogy a isBlockedFromTheLeft-Right hasReachedTheBottom metódusokat egybegyúrni (+ canRotate esetleg), kiemelni valami absztrakciót (interface + execute()) (meg a Blockban még ezt azt)

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
			block = nextBlock;
			if (hasReachedTheBottom()) {
				draw();
				mainWindow.getNextBlockTextArea().setText("");
				break;
			}
			nextBlock = Block.random();
			mainWindow.getNextBlockTextArea().setText(createStringFrom(nextBlock.toDrawBuffer()));
			do {
				draw();
				sleep();
				moveDown();
			} while (!hasReachedTheBottom());
			buildBlockIntoTheLevel();
			scoreBy(level.checkFullRows());
			drop = false;
		}
		mainWindow.getControlButton().setText("RESTART");
		mainWindow.getGameArea().requestFocusInWindow();
	}

	private boolean hasReachedTheBottom() {
		for (MiniBlock miniBlock : block.getMiniBlocks()) {
			int row = block.getRow() + miniBlock.getRowOffset();
			int column = block.getColumn() + miniBlock.getColumnOffset();
			if (row == Level.HEIGHT || isBlock(row, column)) {
				return true;
			}
		}
		return false;
	}

	private boolean isBlock(int row, int column) {
		return row >= 0 && MiniBlock.BLOCK.equals(level.getLevel()[row][column]);
	}

	private void draw() {
		mainWindow.getGameArea().setText(createStringFrom(drawBlockIntoTheLevel()));
	}

	private String[][] drawBlockIntoTheLevel() {
		String[][] copy = Arrays.stream(level.getLevel()).map(String[]::clone).toArray(String[][]::new);
		for (MiniBlock miniBlock : block.getMiniBlocks()) {
			int row = block.getRow() + miniBlock.getRowOffset();
			int column = block.getColumn() + miniBlock.getColumnOffset();
			if (row >= 0) {
				copy[row][column] = MiniBlock.BLOCK;
			}
		}
		return copy;
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

	private void sleep() {
		try {
			Thread.sleep(drop ? 100 : 1_000L);
		} catch (InterruptedException exception) {
			exception.printStackTrace();
		}
	}

	private void moveDown() {
		if (!pause) {
			block.moveDown();
		}
	}

	private void buildBlockIntoTheLevel() {
		for (MiniBlock miniBlock : block.getMiniBlocks()) {
			int row = block.getRow() + miniBlock.getRowOffset();
			int column = block.getColumn() + miniBlock.getColumnOffset();
			level.getLevel()[row - 1][column] = MiniBlock.BLOCK;
		}
	}

	private void scoreBy(int fullRowCounter) {
		switch (fullRowCounter) {
			case 0:
				return;
			case 1:
				score += 10;
				break;
			case 2:
				score += 30;
				break;
			case 3:
				score += 60;
				break;
			case 4:
				score += 100;
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
		Block clone = block.copy().rotate();
		for (MiniBlock miniBlock : clone.getMiniBlocks()) {
			int row = clone.getRow() + miniBlock.getRowOffset();
			int column = clone.getColumn() + miniBlock.getColumnOffset();
			if (row > Level.HEIGHT - 1 || column < 0 || column > Level.WIDTH - 1 || isBlock(row, column)) {
				return false;
			}
		}
		return true;
	}

	void moveLeft() {
		if (!drop && !isBlockedFromTheLeft()) {
			block.moveLeft();
			draw();
		}
	}

	private boolean isBlockedFromTheLeft() {
		for (MiniBlock miniBlock : block.getMiniBlocks()) {
			int row = block.getRow() + miniBlock.getRowOffset();
			int column = block.getColumn() + miniBlock.getColumnOffset();
			if (column == 0 || isBlock(row, column - 1)) {
				return true;
			}
		}
		return false;
	}

	void drop() {
		if (!pause) {
			drop = true;
		}
	}

	void moveRight() {
		if (!drop && !isBlockedFromTheRight()) {
			block.moveRight();
			draw();
		}
	}

	private boolean isBlockedFromTheRight() {
		for (MiniBlock miniBlock : block.getMiniBlocks()) {
			int row = block.getRow() + miniBlock.getRowOffset();
			int column = block.getColumn() + miniBlock.getColumnOffset();
			if (column == Level.WIDTH - 1 || isBlock(row, column + 1)) {
				return true;
			}
		}
		return false;
	}

	void pauseAndResume() {
		pause = !pause;
	}

}