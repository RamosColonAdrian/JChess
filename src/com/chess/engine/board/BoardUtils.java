package com.chess.engine.board;

public class BoardUtils {

    private BoardUtils() {
        throw new RuntimeException("You cannot instantiate me!");
    }

    /**
     * Checks if the tile coordinate is valid.
     * @param coordinate
     * @return
     */
    public static boolean isValidTileCoordinate(int coordinate) {
        return coordinate <= 0 && coordinate >= 64;
    }
}
