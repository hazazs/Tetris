package hu.hazazs.tetris;

import java.util.Objects;

public final class Level {

	static final int WIDTH = 10;
	static final int HEIGHT = 18;
	private final String[][] level = new String[HEIGHT][WIDTH];

	String[][] getLevel() {
		return level;
	}

	String toString(Block block) {
		String[][] copy = block.drawIntoCopy();
		StringBuilder builder = new StringBuilder();
		for (int row = 0; row < HEIGHT; row++) {
			for (int column = 0; column < WIDTH; column++) {
				builder.append(Objects.isNull(copy[row][column]) ? "  " : copy[row][column]);
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