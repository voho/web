package gof.flyweight;

public class FlyweightTileType {
    private final String imageName;
    private final double moveSpeed;
    private final boolean isWater;

    public FlyweightTileType(final String imageName, final double moveSpeed, final boolean isWater) {
        this.imageName = imageName;
        this.moveSpeed = moveSpeed;
        this.isWater = isWater;
    }

    // ...
}
