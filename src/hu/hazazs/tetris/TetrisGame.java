package hu.hazazs.tetris;

public final class TetrisGame implements Runnable {

	// törölni a csalásokat
	// tetszőleges méretű lehessen a pálya (szélesség: 47,6 * WIDTH / magasság: 39,2 * HEIGHT)
	// az ebben az osztályban levő new Block BlockTypeját randomizálni
	// tükrözött Z és L elem
	// startra induljon, ha gameover, akkor restart
	// a jobbra balra le gomb HATÁSÁT nem tudja lekövetni az 1 másodperces várakozás alatt (meg kellene valahogy szakadni a while ciklusnak)
		// [5][3] gyors balra jobbra kombináció után simán megyünk tovább
		// egy elemre gyorsan balra jobbra mozogva majdnem 1 másodperces késéssel lesz disabled a pálya
		// lefelé gombnál van egy majdnem egy másodperces delay elindulás előtt

	// valahogy a második drawt kiszedni (leérkezéskor egy másodperces delay lesz, ha kiszedem)
	// valahogy a blockLeft blockRight reachedBottom metódusokat egybegyúrni, kiemelni valami absztrakciót

	private final MainWindow mainWindow;
	private final Level level;
	private final Block block;
	private boolean drop;

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
			if (!block.hasReachedTheBottom()) {
				move();
			}
		}
		drop = false;
		draw();
		mainWindow.getGameArea().setEnabled(false);
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
		if (!drop) {
			block.moveLeft();
			draw();
		}
	}

	void moveBlockToTheRight() {
		if (!drop) {
			block.moveRight();
			draw();
		}
	}

	void dropBlock() {
		drop = true;
	}

}