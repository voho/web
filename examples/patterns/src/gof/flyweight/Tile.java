package gof.flyweight;

public class Tile {
    private final String imageName;
    private final double moveSpeed;
    private final boolean isWater;

    public Tile(final String imageName, final double moveSpeed, final boolean isWater) {
        this.imageName = imageName;
        this.moveSpeed = moveSpeed;
        this.isWater = isWater;
    }

    // ...
}
