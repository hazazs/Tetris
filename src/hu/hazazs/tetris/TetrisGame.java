package hu.hazazs.tetris;

public final class TetrisGame implements Runnable {

	// megfordítani a T blokkot
	// létrehozni egy L blokkot
	// törölni a négy sarokban levő csalást
	// az ebben az osztályban levő new Block BlockTypeját randomizálni
	// startra induljon

	private final MainWindow mainWindow;
	private final Level level;
	private final Block block;

	TetrisGame(MainWindow mainWindow) {
		this.mainWindow = mainWindow;
		this.level = new Level();
		this.block = new Block(BlockType.LONG);
		mainWindow.setTetrisGame(this);
	}

	@Override
	public void run() {
		while (block.isDrawableInto(level.getLevel())) {
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
		block.moveLeft();
	}
	
	void moveBlockToTheRight() {
		block.moveRight();
	}

}