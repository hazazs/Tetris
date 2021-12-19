package hu.hazazs.tetris;

public final class TetrisGame implements Runnable {

	// törölni a csalásokat
	// score-t kiemelni ide a tetrisGamebe példányváltozónak?
	// tetszőleges méretű lehessen a pálya (szélesség: 47,6 * WIDTH / magasság: 39,2 * HEIGHT)
	// az ebben az osztályban levő new Block BlockTypeját randomizálni
	// tükrözött Z és L elem
	// GAME OVER felirat + indulóképernyő

	// valahogy a isBlockedFromTheLeft-Right hasReachedTheBottom metódusokat egybegyúrni, kiemelni valami absztrakciót (interface + execute())

	private final MainWindow mainWindow;
	private final Level level;
	private int score;
	private Block block;
	private boolean drop;
	private boolean pause;

	TetrisGame(MainWindow mainWindow) {
		this.mainWindow = mainWindow;
		level = new Level();
		mainWindow.setTetrisGame(this);
	}

	@Override
	public void run() {
		while (true) {
			block = new Block(level.getLevel(), BlockType.SQUARE);
			if (block.hasReachedTheBottom()) {
				break;
			}
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

	private void scoreBy(int fullRowCounter) {
		switch (fullRowCounter) {
			case 1:
				score += 10;
				break;
			case 2:
				score += 30;
				break;
			case 3:
				score += 45;
				break;
			case 4:
				score += 60;
				break;
		}
		mainWindow.getScoreTextField().setText(String.valueOf(score));
	}

}