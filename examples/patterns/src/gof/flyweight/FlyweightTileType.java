package gof.flyweight;

public class FlyweightTileType {
    private String imageName;
    private double moveSpeed;
    private boolean isWater;

    public FlyweightTileType(String imageName, double moveSpeed, boolean isWater) {
        this.imageName = imageName;
        this.moveSpeed = moveSpeed;
        this.isWater = isWater;
    }

    // ...
}
