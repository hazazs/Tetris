package hu.hazazs.tetris;

import java.util.Arrays;

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
		//level[6][3] = MiniBlock.BLOCK;
		level[7][3] = MiniBlock.BLOCK;
		level[8][3] = MiniBlock.BLOCK;
		level[9][3] = MiniBlock.BLOCK;
		level[10][7] = MiniBlock.BLOCK;
	}

	String[][] getLevel() {
		return level;
	}

	String toString(Block block) {
		String[][] copy = Arrays.stream(level).map(String[]::clone).toArray(String[][]::new);
		block.drawItselfInto(copy);
		StringBuilder builder = new StringBuilder();
		for (int row = 0; row < HEIGHT; row++) {
			for (int column = 0; column < WIDTH; column++) {
				builder.append(copy[row][column]);
			}
			builder.append("\n");
		}
		return builder.toString();
	}

}