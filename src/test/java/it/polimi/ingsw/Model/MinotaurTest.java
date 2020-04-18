package it.polimi.ingsw.Model;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MinotaurTest {
    @Test
    void calculatePushedPosTopLeft(){
        Minotaur minotaur = new Minotaur();

        Position p = minotaur.calculatePushedPos(new Position(1,1), new Position(0,0));
        assertEquals("-1,-1", p.toString());

        p = minotaur.calculatePushedPos(new Position(0,1), new Position(0,0));
        assertEquals("0,-1", p.toString());

        p = minotaur.calculatePushedPos(new Position(1,0), new Position(0,0));
        assertEquals("-1,0", p.toString());
    }

    @Test
    void calculatePushedPosTopRight(){
        Minotaur minotaur = new Minotaur();

        Position p = minotaur.calculatePushedPos(new Position(1,3), new Position(0,4));
        assertEquals("-1,5", p.toString());

        p = minotaur.calculatePushedPos(new Position(0,3), new Position(0,4));
        assertEquals("0,5", p.toString());

        p = minotaur.calculatePushedPos(new Position(1,4), new Position(0,4));
        assertEquals("-1,4", p.toString());
    }

    @Test
    void calculatePushedPosBottomLeft(){
        Minotaur minotaur = new Minotaur();

        Position p = minotaur.calculatePushedPos(new Position(3,1), new Position(4,0));
        assertEquals("5,-1", p.toString(), "Should be an illegal position");

        p = minotaur.calculatePushedPos(new Position(3,0), new Position(4,0));
        assertEquals("5,0", p.toString(), "Should be an illegal position");

        p = minotaur.calculatePushedPos(new Position(4,1), new Position(4,0));
        assertEquals("4,-1", p.toString(), "Should be an illegal position");
    }

    @Test
    void calculatePushedPosBottomRight(){
        Minotaur minotaur = new Minotaur();

        Position p = minotaur.calculatePushedPos(new Position(3,3), new Position(4,4));
        assertEquals("5,5", p.toString());

        p = minotaur.calculatePushedPos(new Position(4,3), new Position(4,4));
        assertEquals("4,5", p.toString());

        p = minotaur.calculatePushedPos(new Position(3,4), new Position(4,4));
        assertEquals("5,4", p.toString());
    }

    @Test
    void checkSomeImpossiblePos(){
        Minotaur minotaur = new Minotaur();

        assertTrue(minotaur.isGood(new Position(0,0)), "Should be a good position");
        assertTrue(minotaur.isGood(new Position(0,4)), "Should be a good position");
        assertTrue(minotaur.isGood(new Position(4,4)), "Should be a good position");
        assertTrue(minotaur.isGood(new Position(4,0)), "Should be a good position");
        assertTrue(minotaur.isGood(new Position(1,3)), "Should be a good position");

        assertFalse(minotaur.isGood(new Position(-1,0)), "Should be a bad position");
        assertFalse(minotaur.isGood(new Position(0,-1)), "Should be a bad position");
        assertFalse(minotaur.isGood(new Position(5,0)), "Should be a bad position");
        assertFalse(minotaur.isGood(new Position(0,5)), "Should be a bad position");
        assertFalse(minotaur.isGood(new Position(-5,5)), "Should be a bad position");

    }

    @Test
    void getAddEmpty(){
        List<Player> pList = createPlayer(new Minotaur(), new Athena());
        Model model = new Model(pList);
        model.setCurrentConstructor(pList.get(0).getAllConstructors().get(0));
        Minotaur minotaur = (Minotaur)model.getCurrentGod();

        model.performMove(new Position(1,1));

        assertNull(minotaur.getMoveAddList(model), "Empty because no other constructor are on the board");
    }

    @Test
    void getAddEmptyWithConstructors(){
         List<Player> pList = createPlayer(new Minotaur(), new Athena());
         Model model = new Model(pList);
         model.setCurrentConstructor(pList.get(0).getAllConstructors().get(0));
         Minotaur minotaur = (Minotaur)model.getCurrentGod();

         model.performMove(new Position(1,1));
         model.getBoard().placeConstructor(model.getBoard().getTile(new Position(1,2)), pList.get(0).getAllConstructors().get(1));

         assertNull(minotaur.getMoveAddList(model), "Empty because no other enemy' constructor are on the board");
     }

    @Test
    void getAddEmptyWithEnemyButTooHigh(){
         List<Player> pList = createPlayer(new Minotaur(), new Athena());
         Model model = new Model(pList);
         model.setCurrentConstructor(pList.get(0).getAllConstructors().get(0));
         Minotaur minotaur = (Minotaur)model.getCurrentGod();

         model.performMove(new Position(1,1));
         setLevel(model.getBoard(), new Position(1,2), 2);
         model.getBoard().placeConstructor(model.getBoard().getTile(new Position(1,2)), pList.get(1).getAllConstructors().get(0));

         assertNull(minotaur.getMoveAddList(model), "There's an enemy, but he's too high");
     }

    @Test
    void getAddOk(){
        List<Player> pList = createPlayer(new Minotaur(), new Athena());
        Model model = new Model(pList);
        Minotaur minotaur = (Minotaur)model.getCurrentGod();

        model.setCurrentConstructor(pList.get(0).getAllConstructors().get(0));
        model.performMove(new Position(1,1));

        model.setCurrentConstructor(pList.get(1).getAllConstructors().get(0));
        model.performMove(new Position(1,2));

        model.setCurrentConstructor(pList.get(0).getAllConstructors().get(0));

        assertEquals("1,2", minotaur.getMoveAddList(model).get(0).toString(), "There's one in 1,2");
    }

    @Test
    void getAddOkDouble(){
        List<Player> pList = createPlayer(new Minotaur(), new Athena());
        Model model = new Model(pList);
        Minotaur minotaur = (Minotaur)model.getCurrentGod();

        model.setCurrentConstructor(pList.get(0).getAllConstructors().get(0));
        model.performMove(new Position(1,1));

        model.setCurrentConstructor(pList.get(1).getAllConstructors().get(0));
        model.performMove(new Position(1,2));

        model.setCurrentConstructor(pList.get(1).getAllConstructors().get(1));
        model.performMove(new Position(2,1));

        model.setCurrentConstructor(pList.get(0).getAllConstructors().get(0));

        assertEquals(2, minotaur.getMoveAddList(model).size(), "Should be both the 2 position");
    }

    @Test
    void getAddBlockedByDome(){
        List<Player> pList = createPlayer(new Minotaur(), new Athena());
        Model model = new Model(pList);
        Minotaur minotaur = (Minotaur)model.getCurrentGod();

        model.setCurrentConstructor(pList.get(0).getAllConstructors().get(0));
        model.performMove(new Position(3,3));

        model.setCurrentConstructor(pList.get(1).getAllConstructors().get(0));
        model.performMove(new Position(2,3));

        model.setCurrentConstructor(pList.get(0).getAllConstructors().get(0));

        setLevel(model.getBoard(), new Position(1,3), 4);

        assertNull(minotaur.getMoveAddList(model), "There's an enemy, but he's can't be pushed due to a dome");
    }

    @Test
    void getAddBlockedByOutOfBound(){
        List<Player> pList = createPlayer(new Minotaur(), new Athena());
        Model model = new Model(pList);
        Minotaur minotaur = (Minotaur)model.getCurrentGod();

        model.setCurrentConstructor(pList.get(0).getAllConstructors().get(0));
        model.performMove(new Position(1,1));

        model.setCurrentConstructor(pList.get(1).getAllConstructors().get(0));
        model.performMove(new Position(0,1));

        model.setCurrentConstructor(pList.get(0).getAllConstructors().get(0));

        assertNull(minotaur.getMoveAddList(model), "There's an enemy, but he's can't be pushed out of bounds");
    }

    @Test
    void changeActiveNobodyAround(){
        List<Player> pList = createPlayer(new Minotaur(), new Athena());
        Model model = new Model(pList);
        Minotaur minotaur = (Minotaur)model.getCurrentGod();

        model.setCurrentConstructor(pList.get(0).getAllConstructors().get(0));
        model.performMove(new Position(0,0));

        model.setCurrentConstructor(pList.get(0).getAllConstructors().get(1));
        model.performMove(new Position(2,2));

        minotaur.changeActiveConstructors(model);

        assertTrue(pList.get(0).getAllConstructors().get(0).getCanMove(), "Should be active");
        assertTrue(pList.get(0).getAllConstructors().get(1).getCanMove(), "Should be active");

    }

    @Test
    void changeActiveOneBlocked(){
        List<Player> pList = createPlayer(new Minotaur(), new Athena());
        Model model = new Model(pList);
        Minotaur minotaur = (Minotaur)model.getCurrentGod();

        model.setCurrentConstructor(pList.get(0).getAllConstructors().get(0));
        model.performMove(new Position(0,0));

        model.setCurrentConstructor(pList.get(0).getAllConstructors().get(1));
        model.performMove(new Position(0,1));

        setLevel(model.getBoard(), new Position(1,0), 4);
        setLevel(model.getBoard(), new Position(1,1), 4);

        minotaur.changeActiveConstructors(model);

        assertFalse(pList.get(0).getAllConstructors().get(0).getCanMove(), "Should be inactive");
        assertTrue(pList.get(0).getAllConstructors().get(1).getCanMove(), "Should be active");
    }

    @Test
    void changeActiveNoBlocked(){
        List<Player> pList = createPlayer(new Minotaur(), new Athena());
        Model model = new Model(pList);
        Minotaur minotaur = (Minotaur)model.getCurrentGod();

        model.setCurrentConstructor(pList.get(0).getAllConstructors().get(0));
        model.performMove(new Position(0,0));

        model.setCurrentConstructor(pList.get(0).getAllConstructors().get(1));
        model.performMove(new Position(0,1));

        model.setCurrentConstructor(pList.get(1).getAllConstructors().get(0));
        model.performMove(new Position(1,0));

        setLevel(model.getBoard(), new Position(1,1), 4);

        model.setCurrentConstructor(pList.get(0).getAllConstructors().get(0));

        minotaur.changeActiveConstructors(model);

        assertTrue(pList.get(0).getAllConstructors().get(0).getCanMove(), "Should be active (it can push)");
        assertTrue(pList.get(0).getAllConstructors().get(1).getCanMove(), "Should be active");
    }

    @Test
    void changeActiveCouldPushButCant(){
        List<Player> pList = createPlayer(new Minotaur(), new Athena());
        Model model = new Model(pList);
        Minotaur minotaur = (Minotaur)model.getCurrentGod();

        model.setCurrentConstructor(pList.get(0).getAllConstructors().get(0));
        model.performMove(new Position(0,0));

        model.setCurrentConstructor(pList.get(0).getAllConstructors().get(1));
        model.performMove(new Position(0,1));

        model.setCurrentConstructor(pList.get(1).getAllConstructors().get(0));
        model.performMove(new Position(1,0));

        setLevel(model.getBoard(), new Position(1,1), 4);
        setLevel(model.getBoard(), new Position(2,0), 4);

        model.setCurrentConstructor(pList.get(0).getAllConstructors().get(0));

        minotaur.changeActiveConstructors(model);

        assertFalse(pList.get(0).getAllConstructors().get(0).getCanMove(), "Should be inactive (there's a constructor to push, but the arrival position is blocked)");
        assertTrue(pList.get(0).getAllConstructors().get(1).getCanMove(), "Should be active");
    }

    @Test
    void changeActivePushMoreThan2Level(){
        List<Player> pList = createPlayer(new Minotaur(), new Athena());
        Model model = new Model(pList);
        Minotaur minotaur = (Minotaur)model.getCurrentGod();

        model.setCurrentConstructor(pList.get(0).getAllConstructors().get(0));
        model.performMove(new Position(0,0));

        model.setCurrentConstructor(pList.get(0).getAllConstructors().get(1));
        model.performMove(new Position(0,1));

        model.setCurrentConstructor(pList.get(1).getAllConstructors().get(0));
        model.performMove(new Position(1,0));

        setLevel(model.getBoard(), new Position(1,1), 4);
        setLevel(model.getBoard(), new Position(2,0), 3);

        model.setCurrentConstructor(pList.get(0).getAllConstructors().get(0));

        minotaur.changeActiveConstructors(model);

        assertTrue(pList.get(0).getAllConstructors().get(0).getCanMove(), "Should be active (there's a constructor to push)");
        assertTrue(pList.get(0).getAllConstructors().get(1).getCanMove(), "Should be active");
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

    private void setLevel(Board board, Position pos, int level){
        int start = board.getTile(pos).getConstructionLevel();

        for(int i = 0; i < level-start; i++){
            board.getTile(pos).increaseConstructionLevel();
        }
    }
}