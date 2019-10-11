package gof.flyweight;

import java.util.Arrays;

public class Map {
    private final Tile[] tiles;

    public Map(final int size) {
        tiles = new Tile[size * size];
    }

    public void clear() {
        Arrays.fill(tiles, createGrassTile());
    }

    public void addSomeRivers(double p) {
        for (int i = 0; i < tiles.length; i++) {
            if (Math.random() < p) {
                tiles[i] = createRiverTile();
            }
        }
    }

    public void addSomeHills(double p) {
        for (int i = 0; i < tiles.length; i++) {
            if (Math.random() < p) {
                tiles[i] = createHillTile();
            }
        }
    }

    private static Tile createGrassTile() {
        return new Tile("grass.png", 1.0, false);
    }

    private static Tile createRiverTile() {
        return new Tile("river.png", 0.0, true);
    }

    private static Tile createHillTile() {
        return new Tile("hill.png", 0.5, false);
    }

    // ...
}
