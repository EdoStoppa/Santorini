package it.polimi.ingsw.Client;

public class PlaySpace {
    private int[][] buildingMatrix;
    private int[][] constructorMatrix;

    public int[][] getBuildingMatrix() {
        return buildingMatrix;
    }

    public int[][] getConstructorMatrix() {
        return constructorMatrix;
    }

    public void setBuildingMatrix(int[][] buildingMatrix) {
        this.buildingMatrix = buildingMatrix;
    }

    public void setConstructorMatrix(int[][] constructorMatrix) {
        this.constructorMatrix = constructorMatrix;
    }
}
