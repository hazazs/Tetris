package hu.hazazs.tetris;

public final class TetrisGame implements Runnable {

	// megfordítani a T blokkot
	// létrehozni egy L blokkot
	// törölni a négy sarokban levő csalást
	// két toStringet összemosni (a sima toString lehet nem is kell)
	// az ebben az osztályban levő new Block BlockTypeját randomizálni
	// while drawItselfInto (és ez is booleant adna vissza) amiben lenne egy isDrawable metódus

	private final MainWindow mainWindow;
	private final Level level;
	private final Block block;

	TetrisGame(MainWindow mainWindow) {
		this.mainWindow = mainWindow;
		this.level = new Level();
		this.block = new Block(BlockType.LONG);
	}

	@Override
	public void run() {
		while (block.isValid()) {
			draw();
			sleep();
			move();
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

	private void move() {
		block.moveDown();
	}

}