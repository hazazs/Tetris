package hu.hazazs.tetris;

public final class Level {
	
	private final static int WIDTH = 10;
	private final static int HEIGHT = 15;
	private final String[][] level = new String[HEIGHT][WIDTH];
	
	public Level() {
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

}