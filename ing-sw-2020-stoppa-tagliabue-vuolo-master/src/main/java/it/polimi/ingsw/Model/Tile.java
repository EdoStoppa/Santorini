package it.polimi.ingsw.Model;

public class Tile {

    private final Position position;
    private boolean occupied;
    private int constructionLevel;
    private boolean dome;
    private Constructor actualConstuctor;

    public Tile(Position pos)   {
        this.position = pos;
        this.occupied = false;
        this.constructionLevel = 0;
        this.dome = 0;
        this.actualConstuctor = null;
    }

    public Position getPos()    {
        return this.position;
    }

    public void setOccupied(boolean b)    {
        this.occupied = b;
    }

    public boolean getOccupied()    {
        return this.occupied;
    }

    public void setConstructionLevel() {
        if(getConstructionLevel() < 3)  {
            this.constructionLevel = this.constructionLevel + 1;
        }
        else if(getConstructionLevel() = 3)   {
            setDome(true);
        }
    }

    public int getConstructionLevel()   {
        return this.constructionLevel;
    }

    public void setDome(boolean b)  {
        this.dome = b;
    }

    public boolean getDome()    {
        return this.dome;
    }
}
