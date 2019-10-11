package gof.flyweight;

public class Tile {
    private String imageName;
    private double moveSpeed;
    private boolean isWater;

    public Tile(String imageName, double moveSpeed, boolean isWater) {
        this.imageName = imageName;
        this.moveSpeed = moveSpeed;
        this.isWater = isWater;
    }

    // ...
}
