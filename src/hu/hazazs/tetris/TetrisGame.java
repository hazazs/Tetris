package hu.hazazs.tetris;

public final class TetrisGame implements Runnable {

	private final MainWindow mainWindow;
	private final Level level;
	private final Block block;

	TetrisGame(MainWindow mainWindow) {
		this.mainWindow = mainWindow;
		this.level = new Level();
		this.block = new Block();
	}

	// ██
	@Override
	public void run() {
		mainWindow.getGameArea().setText(level.toString());
	}

}