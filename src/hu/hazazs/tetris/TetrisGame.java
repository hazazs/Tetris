package hu.hazazs.tetris;

public class TetrisGame implements Runnable {
	
	private MainWindow mainWindow;
	
	public TetrisGame(MainWindow mainWindow) {
		this.mainWindow = mainWindow;
	}

	@Override
	public void run() {
		System.out.println("Hello%");
	}

}