package hu.hazazs.tetris;

public final class TetrisGame implements Runnable {

	private final MainWindow mainWindow;
	private final Level level;
	private Block block;

	public TetrisGame(MainWindow mainWindow) {
		this.mainWindow = mainWindow;
		this.level = new Level();
	}

	// ██
	// azt hiszem nagy nehezen sikerült
	@Override
	public void run() {
		mainWindow.getGameArea().setText(level.toString());
	}

}