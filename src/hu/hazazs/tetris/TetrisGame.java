package hu.hazazs.tetris;

public final class TetrisGame implements Runnable {
	
	// MiniBlock.BLOCK static final String
	// megfordítani a T blokkot
	// létrehozni egy L blokkot
	// törölni a négy sarokban levő csalást

	private final MainWindow mainWindow;
	private final Level level;
	private final Block block;

	TetrisGame(MainWindow mainWindow) {
		this.mainWindow = mainWindow;
		this.level = new Level();
		this.block = new Block(BlockType.Z);
	}

	@Override
	public void run() {
		mainWindow.getGameArea().setText(level.toString());
	}

}