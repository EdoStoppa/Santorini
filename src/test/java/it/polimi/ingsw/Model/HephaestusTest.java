package it.polimi.ingsw.Model;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class HephaestusTest {

    @Test
    void canDo(){
        List<Player> pList = createPlayer(new Hephaestus(), new Athena());
        Model model = new Model(pList);
        model.setCurrentConstructor(pList.get(0).getAllConstructors().get(0));

        Hephaestus hephaestus = (Hephaestus) model.getCurrentGod();

        model.performMove(new Position(1,1));
        model.performBuild(new Position(1,2));

        assertFalse(hephaestus.cantDoAnother(model, hephaestus.getLastBuildPos(model).clone()), "Being only level one should be able to build one more");
    }

    @Test
    void cantDoThirdLevel(){
        List<Player> pList = createPlayer(new Hephaestus(), new Athena());
        Model model = new Model(pList);
        model.setCurrentConstructor(pList.get(0).getAllConstructors().get(0));

        Hephaestus hephaestus = (Hephaestus) model.getCurrentGod();

        model.performMove(new Position(1,1));
        model.performBuild(new Position(1,2));
        model.performBuild(new Position(1,2));
        model.performBuild(new Position(1,2));

        assertTrue(hephaestus.cantDoAnother(model, hephaestus.getLastBuildPos(model).clone()), "Being third level shouldn't be able to build");
    }

    @Test
    void cantDoDome(){
        List<Player> pList = createPlayer(new Hephaestus(), new Athena());
        Model model = new Model(pList);
        model.setCurrentConstructor(pList.get(0).getAllConstructors().get(0));

        Hephaestus hephaestus = (Hephaestus) model.getCurrentGod();

        model.performMove(new Position(1,1));
        model.performBuild(new Position(1,2));
        model.performBuild(new Position(1,2));
        model.performBuild(new Position(1,2));
        model.performBuild(new Position(1,2));

        assertTrue(hephaestus.cantDoAnother(model, hephaestus.getLastBuildPos(model).clone()), "Being third level shouldn't be able to build");
    }

    private List<Player> createPlayer(God p1God, God p2God){
        Player p1 = new Player("uno", 1);
        Player p2 = new Player("due", 2);

        p1.setGod(p1God);
        p2.setGod(p2God);

        List<Player> pList = new ArrayList<>();
        pList.add(p1);
        pList.add(p2);

        return pList;
    }

    private void setMaxLevel(Board board, Position pos){
        int start = board.getTile(pos).getConstructionLevel();

        for(int i = 0; i < 4-start; i++){
            board.getTile(pos).increaseConstructionLevel();
        }
    }

    private void setThirdLevel(Board board, Position pos){
        int start = board.getTile(pos).getConstructionLevel();

        for(int i = 0; i < 3-start; i++){
            board.getTile(pos).increaseConstructionLevel();
        }
    }

    private int howManyConstructorRemain(Board board){
        int count = 0;
        for(int i = 0; i < 5; i++){
            for(int j = 0; j < 5; j++){
                if(board.getTile(new Position(i,j)).getOccupied()){
                    count += 1;
                }
            }
        }

        return count;
    }
}