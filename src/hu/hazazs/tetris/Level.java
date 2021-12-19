package hu.hazazs.tetris;

public final class Level {

	static final int WIDTH = 10;
	static final int HEIGHT = 15;
	private final String[][] level = new String[HEIGHT][WIDTH];

	Level() {
		for (int row = 0; row < HEIGHT; row++) {
			for (int column = 0; column < WIDTH; column++) {
				level[row][column] = "  ";
			}
		}
		level[0][0] = MiniBlock.BLOCK;
		level[0][WIDTH - 1] = MiniBlock.BLOCK;
		level[HEIGHT - 1][0] = MiniBlock.BLOCK;
		level[HEIGHT - 1][WIDTH - 1] = MiniBlock.BLOCK;

		level[5][1] = MiniBlock.BLOCK;
		level[5][3] = MiniBlock.BLOCK;
		level[6][3] = MiniBlock.BLOCK;
		level[7][3] = MiniBlock.BLOCK;
		level[8][3] = MiniBlock.BLOCK;
		level[9][3] = MiniBlock.BLOCK;
		level[10][7] = MiniBlock.BLOCK;
		
		level[14][0] = MiniBlock.BLOCK;
		level[14][1] = MiniBlock.BLOCK;
		level[14][2] = MiniBlock.BLOCK;
		level[14][3] = MiniBlock.BLOCK;
		level[14][7] = MiniBlock.BLOCK;
		level[14][8] = MiniBlock.BLOCK;
		level[14][9] = MiniBlock.BLOCK;
		level[13][2] = MiniBlock.BLOCK;
		level[13][3] = MiniBlock.BLOCK;
		level[12][3] = MiniBlock.BLOCK;
	}

	String[][] getLevel() {
		return level;
	}

	String toString(Block block) {
		String[][] copy = block.drawIntoCopy();
		StringBuilder builder = new StringBuilder();
		for (int row = 0; row < HEIGHT; row++) {
			for (int column = 0; column < WIDTH; column++) {
				builder.append(copy[row][column]);
			}
			builder.append("\n");
		}
		return builder.toString();
	}

	int checkFullRows() {
		int fullRowCounter = 0;
		for (int row = 0; row < Level.HEIGHT; row++) {
			if (isFullRow(row)) {
				fullRowCounter++;
				deleteAndDropByOneUntil(row);
			}
		}
		return fullRowCounter;
	}

	private boolean isFullRow(int row) {
		for (int column = 0; column < Level.WIDTH; column++) {
			if (!MiniBlock.BLOCK.equals(level[row][column])) {
				return false;
			}
		}
		return true;
	}

	private void deleteAndDropByOneUntil(int startRow) {
		for (int row = startRow; row >= 0; row--) {
			for (int column = 0; column < Level.WIDTH; column++) {
				level[row][column] = row == 0 ? "  " : level[row - 1][column];
			}
		}
	}

}