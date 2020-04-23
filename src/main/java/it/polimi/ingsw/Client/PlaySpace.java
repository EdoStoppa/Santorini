package it.polimi.ingsw.Client;

import it.polimi.ingsw.Model.Position;

import java.util.List;

public class PlaySpace {
    private int[][] buildingMatrix;
    private int[][] constructorMatrix;

    private boolean checkDome;
    private boolean checkIfSure;
    private List<Position> tileToShow;
    private List<Position> tileToCheck;

    public PlaySpace(){
        this.checkDome = false;
        this.checkIfSure = false;
        this.tileToShow = null;
        this.tileToCheck = null;

        this.buildingMatrix = new int[5][5];
        this.constructorMatrix = new int[5][5];
    }

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

    public boolean isCheckDome() {
        return checkDome;
    }

    public void setCheckDome(boolean checkDome) {
        this.checkDome = checkDome;
    }

    public boolean isCheckIfSure() {
        return checkIfSure;
    }

    public void setCheckIfSure(boolean checkIfSure) {
        this.checkIfSure = checkIfSure;
    }

    public List<Position> getTileToShow() {
        return tileToShow;
    }

    public void setTileToShow(List<Position> tileToShow) {
        this.tileToShow = tileToShow;
    }

    public List<Position> getTileToCheck() {
        return tileToCheck;
    }

    public void setTileToCheck(List<Position> tileToCheck) {
        this.tileToCheck = tileToCheck;
    }

    public void reset(){
        this.checkDome = false;
        this.checkIfSure = false;
        this.tileToShow = null;
        this.tileToCheck = null;
    }
}
