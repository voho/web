package gof.flyweight;

public class FlyweightMap {
    private static final FlyweightTileType GRASS = new FlyweightTileType("grass.png", 1.0, false);
    private static final FlyweightTileType RIVER = new FlyweightTileType("river.png", 0.0, true);
    private static final FlyweightTileType HILL = new FlyweightTileType("hill.png", 0.5, false);

    private final FlyweightTile[] tiles;

    public FlyweightMap(final int size) {
        tiles = new FlyweightTile[size * size];
    }

    public void clear() {
        for (int i = 0; i < tiles.length; i++) {
            tiles[i] = new FlyweightTile(GRASS);
        }
    }

    public void addSomeRivers(double p) {
        for (int i = 0; i < tiles.length; i++) {
            if (Math.random() < p) {
                tiles[i] = new FlyweightTile(RIVER);
            }
        }
    }

    public void addSomeHills(double p) {
        for (int i = 0; i < tiles.length; i++) {
            if (Math.random() < p) {
                tiles[i] = new FlyweightTile(HILL);
            }
        }
    }

    // ...
}
