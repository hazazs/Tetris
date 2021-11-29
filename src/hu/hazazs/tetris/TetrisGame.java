package hu.hazazs.tetris;

public final class TetrisGame implements Runnable {
	
	private MainWindow mainWindow;
	
	public TetrisGame(MainWindow mainWindow) {
		this.mainWindow = mainWindow;
	}

	// █
	@Override
	public void run() {
		String buffer = "";
		for (int i = 0; i < 5; i++) {
			mainWindow.getGameArea().setText(buffer);
			sleep(2);
		}
	}

	private void sleep(int seconds) {
		try {
			Thread.sleep(seconds * 1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}