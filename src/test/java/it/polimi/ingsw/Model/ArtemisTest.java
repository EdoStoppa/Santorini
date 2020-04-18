package it.polimi.ingsw.Model;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ArtemisTest {
    @Test
    void canGoForAnother(){
        List<Player> pList = createPlayer(new Artemis(), new Athena());
        Model model = new Model(pList);
        model.setCurrentConstructor(pList.get(0).getAllConstructors().get(0));

        Artemis artemis = (Artemis)model.getCurrentGod();

        model.performMove(new Position(1,1));

        List<Position> delList = new ArrayList<>();
        delList.add(model.getCurrentConstructor().getPrevPos());

        assertFalse(artemis.cantDoAnother(model, delList), "Should be able to do other moves");
    }

    @Test
    void cantGoForAnother(){
        List<Player> pList = createPlayer(new Artemis(), new Athena());
        Model model = new Model(pList);
        model.setCurrentConstructor(pList.get(0).getAllConstructors().get(0));

        Artemis artemis = (Artemis)model.getCurrentGod();

        model.performMove(new Position(1,1));
        model.performMove(new Position(1,2));
        for(int i = 0; i < 3; i++){
            for(int j = 1; j < 4; j++){
                if(!(i == 1 && j == 1) && !(i == 1 && j == 2)){
                    setMaxLevel(model.getBoard(), new Position(i,j));
                }
            }
        }

        List<Position> delList = new ArrayList<>();
        delList.add(model.getCurrentConstructor().getPrevPos());

        assertTrue(artemis.cantDoAnother(model, delList), "Should not be able to do other moves");

    }

    @Test
    void cantGoForAnotherNoDome(){
        List<Player> pList = createPlayer(new Artemis(), new Athena());
        Model model = new Model(pList);
        model.setCurrentConstructor(pList.get(0).getAllConstructors().get(0));

        Artemis artemis = (Artemis)model.getCurrentGod();

        model.performMove(new Position(1,1));
        model.performMove(new Position(1,2));
        for(int i = 0; i < 3; i++){
            for(int j = 1; j < 4; j++){
                if(!(i == 1 && j == 1) && !(i == 1 && j == 2)){
                    setThirdLevel(model.getBoard(), new Position(i,j));
                }
            }
        }

        List<Position> delList = new ArrayList<>();
        delList.add(model.getCurrentConstructor().getPrevPos());

        assertTrue(artemis.cantDoAnother(model, delList), "Should not be able to do other moves");
    }

    @Test
    void onlyOnePossibleMove(){
        List<Player> pList = createPlayer(new Artemis(), new Athena());
        Model model = new Model(pList);
        model.setCurrentConstructor(pList.get(0).getAllConstructors().get(0));

        Artemis artemis = (Artemis)model.getCurrentGod();

        model.performMove(new Position(1,1));
        model.performMove(new Position(1,2));
        for(int i = 0; i < 3; i++){
            for(int j = 1; j < 4; j++){
                if(i != 1){
                    setMaxLevel(model.getBoard(), new Position(i,j));
                }
            }
        }

        List<Position> delList = new ArrayList<>();
        delList.add(model.getCurrentConstructor().getPrevPos());

        assertFalse(artemis.cantDoAnother(model, delList), "Should be able to do other moves (1 moves accepted and 1 is prevPos)");
    }

    private List<Player> createPlayer(God p1God, God p2God){
        Player p1 = new Player("uno", "1/01/2000", 1);
        Player p2 = new Player("due", "1/01/2000", 2);

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
}