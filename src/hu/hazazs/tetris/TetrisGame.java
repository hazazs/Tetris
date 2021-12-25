package hu.hazazs.tetris;

import java.util.Random;

public final class TetrisGame implements Runnable {

	// forgatás logika (felfelé gombra forgat)
	// ami kilóg esetlegesen forgatáskor (csak felül (alul nem?)) az szimplán ne látszódjon (balról és jobbról ha kilógna forgatás után akkor nem engedjük forgatni)
	// SPACE-re azonnal lemegy, lefelé csak begyorsít (és azonnal reagál, nincs 1 másodperces delay)
	// gyorsuljon a pontok növekedésével
	// GAME OVER felirat + indulóképernyő
	// különböző színű blokkok

	// miért alacsonyabbak az első sorok mind a gameAreaban, mind a nextBlockTextAreaban? (vagy a többi hosszabb ?)
	// miért lesz darabos a nextBlock kijelöléskor? (eleve nem is lehetne kijelölni)
	// valahogy a isBlockedFromTheLeft-Right hasReachedTheBottom metódusokat egybegyúrni, kiemelni valami absztrakciót (interface + execute())

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
		nextBlock = getRandomBlock();
		mainWindow.setTetrisGame(this);
	}

	@Override
	public void run() {
		while (true) {
			block = nextBlock;
			if (block.hasReachedTheBottom()) {
				draw();
				mainWindow.getNextBlockTextArea().setText("");
				break;
			}
			nextBlock = getRandomBlock();
			mainWindow.getNextBlockTextArea().setText(createStringFrom(nextBlock.toDrawBuffer()));
			do {
				draw();
				sleep();
				if (!pause && !block.hasReachedTheBottom()) {
					move();
				}
			} while (!block.hasReachedTheBottom());
			block.drawIntoLevel();
			scoreBy(level.checkFullRows());
			drop = false;
		}
		mainWindow.getGameArea().setEnabled(false);
		mainWindow.getControlButton().setText("RESTART");
		mainWindow.getGameArea().requestFocusInWindow();
	}

	private Block getRandomBlock() {
		BlockType randomBlockType = BlockType.values()[new Random().nextInt(BlockType.values().length)];
		return new Block(level.getLevel(), randomBlockType);
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

	private void draw() {
		mainWindow.getGameArea().setText(createStringFrom(level.draw(block)));
	}

	private void sleep() {
		try {
			Thread.sleep(drop ? 100 : 1_000L);
		} catch (InterruptedException exception) {
			exception.printStackTrace();
		}
	}

	private void move() {
		block.moveDown();
	}

	private void scoreBy(int fullRowCounter) {
		switch (fullRowCounter) {
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

	void moveBlockToTheLeft() {
		if (!drop && !pause) {
			block.moveLeft();
			draw();
		}
	}

	void moveBlockToTheRight() {
		if (!drop && !pause) {
			block.moveRight();
			draw();
		}
	}

	void dropBlock() {
		if (!pause) {
			drop = true;
		}
	}

	void pauseAndResume() {
		pause = !pause;
	}

}