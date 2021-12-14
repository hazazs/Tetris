package hu.hazazs.tetris;

public final class TetrisGame implements Runnable {

	// törölni a csalásokat
	// az ebben az osztályban levő new Block BlockTypeját randomizálni
	// startra induljon, ha gameover, akkor restart
	// a jobbra balra mozgatás HATÁSÁT nem tudja lekövetni az 1 másodperces várakozás alatt (meg kellene valahogy szakadni a while ciklusnak)
	// [5][3] gyors balra jobbra kombináció után simán megyünk tovább
	// [5][3] gyors balra után továbbesünk egyet
	// [10][7] gyors jobbra után szimplán átesünk az elemen

	// valahogy a második drawt kiszedni
	// valahogy a blockLeft blockRight reachedBottom metódusokat egybegyúrni, kiemelni valami absztrakciót

	private final MainWindow mainWindow;
	private final Level level;
	private final Block block;

	TetrisGame(MainWindow mainWindow) {
		this.mainWindow = mainWindow;
		level = new Level();
		block = new Block(level.getLevel(), BlockType.Z);
		mainWindow.setTetrisGame(this);
	}

	@Override
	public void run() {
		while (!block.hasReachedTheBottom()) {
			draw();
			sleep();
			drop();
		}
		draw();
		mainWindow.getGameArea().setEnabled(false);
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
		draw();
	}

	void moveBlockToTheRight() {
		block.moveRight();
		draw();
	}

}