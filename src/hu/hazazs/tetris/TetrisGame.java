package hu.hazazs.tetris;

import java.util.Random;

public final class TetrisGame implements Runnable {

	// block toStringen még alakítani
	// game over esetén az utolsó még kirajzolódik felülírva a pályát (és következő elem is generálódik - nem biztos, hogy ez kell)
	// első sortól induljanak, ami kilóg esetlegesen forgatáskor az szimplán ne látszódjon (oldalra elvileg nem tud kilógni forgatáskor) (balról milyen behúzással jelennek meg?)

	// törölni a csalásokat
	// GAME OVER felirat + indulóképernyő
	// forgatás logika (felfelé gombra forgat)
	// gyorsuljon a pontok növekedésével
	// különböző színű blokkok
	// SPACE-re azonnal lemegy, lefelé csak begyorsít (és azonnal reagál)

	// miért alacsonyabbak az első sorok mind a gameAreaban, mind a nextBlockTextAreaban? (vagy a többi hosszabb ?)
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
		nextBlock = new Block(level.getLevel(), getRandomType());
		mainWindow.setTetrisGame(this);
	}

	@Override
	public void run() {
		while (true) {
			block = nextBlock;
			if (block.hasReachedTheBottom()) {
				break;
			}
			nextBlock = new Block(level.getLevel(), getRandomType());
			mainWindow.getNextBlockTextArea().setText(nextBlock.toString());
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

	private BlockType getRandomType() {
		return BlockType.values()[new Random().nextInt(BlockType.values().length)];
	}

	private void draw() {
		mainWindow.getGameArea().setText(level.toString(block));
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