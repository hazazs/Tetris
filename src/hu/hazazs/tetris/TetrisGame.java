package hu.hazazs.tetris;

public final class TetrisGame implements Runnable {

	// megfordítani a T blokkot
	// létrehozni egy L blokkot
	// törölni a négy sarokban levő csalást
	// az ebben az osztályban levő new Block BlockTypeját randomizálni
	// startra induljon
	// valahogy a blockLeft blockRight reachedBottom metódusokat egybegyúrni, kiemelni valami absztrakciót

	private final MainWindow mainWindow;
	private final Level level;
	private final Block block;

	TetrisGame(MainWindow mainWindow) {
		this.mainWindow = mainWindow;
		this.level = new Level();
		this.block = new Block(BlockType.Z);
		mainWindow.setTetrisGame(this);
	}

	@Override
	public void run() {
		while (!block.hasReachedTheBottom(level.getLevel())) {
			draw();
			sleep();
			drop();
		}
	}

	private void draw() {
		mainWindow.getGameArea().setText(level.toString(block));
	}

	private void sleep() {
		try {
			Thread.sleep(1_000L);
		} catch (InterruptedException exception) {
			exception.printStackTrace();
		}
	}

	private void drop() {
		block.moveDown();
	}

	void moveBlockToTheLeft() {
		if (!block.isBlockedFromTheLeft(level.getLevel())) {
			block.moveLeft();
			draw();
		}
	}

	void moveBlockToTheRight() {
		if (!block.isBlockedFromTheRight(level.getLevel())) {
			block.moveRight();
			draw();
		}
	}

}