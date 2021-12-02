package hu.hazazs.tetris;

import java.util.Arrays;

public final class Level {

	public final static int WIDTH = 10;
	public final static int HEIGHT = 15;
	private final String[][] level = new String[HEIGHT][WIDTH];

	Level() {
		for (int row = 0; row < HEIGHT; row++) {
			for (int column = 0; column < WIDTH; column++) {
				level[row][column] = "  ";
			}
		}
		level[0][0] = "██";
		level[0][WIDTH - 1] = "██";
		level[HEIGHT - 1][0] = "██";
		level[HEIGHT - 1][WIDTH - 1] = "██";
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		for (int row = 0; row < HEIGHT; row++) {
			for (int column = 0; column < WIDTH; column++) {
				builder.append(level[row][column]);
			}
			builder.append("\n");
		}
		return builder.toString();
	}

	public String toString(Block block) {
		String[][] copy = Arrays.stream(level).map(String[]::clone).toArray(String[][]::new);
		for (MiniBlock miniBlock : block.getMiniBlocks()) {
			copy[block.getRow() + miniBlock.getRowOffset()][block.getColumn() + miniBlock.getColumnOffset()] = "██";
		}
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