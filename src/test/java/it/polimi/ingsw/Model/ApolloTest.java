package it.polimi.ingsw.Model;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ApolloTest {

    @Test
    void deactivateIfNeededTestStandard(){
        List<Player> pList = createPlayer(new Apollo(), new Athena());
        Model model = new Model(pList);

        model.getGameState().setCurrentPlayer(pList.get(0));

        Position pos1 = new Position(1,1);
        Position pos2 = new Position(3,2);

        model.getBoard().placeConstructor(model.getBoard().getTile(pos1), pList.get(0).getAllConstructors().get(0));
        model.getBoard().placeConstructor(model.getBoard().getTile(pos2), pList.get(0).getAllConstructors().get(1));

        Apollo ap = (Apollo)pList.get(0).getGod();
        ap.deactivateIfNeeded(model);

        //Useless, but you never know...
        assertEquals(2, howManyConstructorRemain(model.getBoard()), "Should remain only 2 constructors");

        for(Constructor c : pList.get(0).getAllConstructors()){
            assertTrue(c.getCanMove(), "The constructor should be active");
        }
    }

    @Test
    void deactivateOnlyOneWithOnlyBuilding(){
        List<Player> pList = createPlayer(new Apollo(), new Athena());
        Model model = new Model(pList);

        model.getGameState().setCurrentPlayer(pList.get(0));

        Position pos1 = new Position(0,0);
        Position pos2 = new Position(3,2);

        model.getBoard().placeConstructor(model.getBoard().getTile(pos1), pList.get(0).getAllConstructors().get(0));
        model.getBoard().placeConstructor(model.getBoard().getTile(pos2), pList.get(0).getAllConstructors().get(1));

        setMaxLevel(model.getBoard(), new Position(0,1));
        setMaxLevel(model.getBoard(), new Position(1,1));
        setMaxLevel(model.getBoard(), new Position(1,0));

        Apollo ap = (Apollo)pList.get(0).getGod();
        ap.deactivateIfNeeded(model);

        //Useless, but you never know...
        assertEquals(2, howManyConstructorRemain(model.getBoard()), "Should remain only 2 constructors");

        assertFalse(pList.get(0).getAllConstructors().get(0).getCanMove(), "The constructor should be inactive");
        assertTrue(pList.get(0).getAllConstructors().get(1).getCanMove(), "The constructor should be active");

    }

    @Test
    void OneWithOtherConstructor(){
        List<Player> pList = createPlayer(new Apollo(), new Athena());
        Model model = new Model(pList);

        model.getGameState().setCurrentPlayer(pList.get(0));

        Position pos1 = new Position(0,0);
        Position pos2 = new Position(3,3);

        model.getBoard().placeConstructor(model.getBoard().getTile(pos1), pList.get(0).getAllConstructors().get(0));
        model.getBoard().placeConstructor(model.getBoard().getTile(pos2), pList.get(0).getAllConstructors().get(1));

        setMaxLevel(model.getBoard(), new Position(0,1));
        setMaxLevel(model.getBoard(), new Position(1,1));
        setMaxLevel(model.getBoard(), new Position(1,0));

        for(int i=2; i<5; i++){
            for(int j=2; j<5; j++){
                if(!(i == 4 && j == 3) && !(i == 4 && j == 4) && !(i == 3 && j == 3)){
                    setMaxLevel(model.getBoard(), new Position(i,j));
                }
            }
        }
        model.getBoard().placeConstructor(model.getBoard().getTile(new Position(4,3)), pList.get(1).getAllConstructors().get(0));
        model.getBoard().placeConstructor(model.getBoard().getTile(new Position(4,4)), pList.get(1).getAllConstructors().get(1));

        Apollo ap = (Apollo)pList.get(0).getGod();
        ap.deactivateIfNeeded(model);

        assertFalse(pList.get(0).getAllConstructors().get(0).getCanMove(), "The constructor should be inactive");
        assertTrue(pList.get(0).getAllConstructors().get(1).getCanMove(), "The constructor should be active");

    }

    @Test
    void blockedWithOwnConstructor(){
        List<Player> pList = createPlayer(new Apollo(), new Athena());
        Model model = new Model(pList);

        model.getGameState().setCurrentPlayer(pList.get(0));

        Position pos1 = new Position(0,0);
        Position pos2 = new Position(1,1);

        model.getBoard().placeConstructor(model.getBoard().getTile(pos1), pList.get(0).getAllConstructors().get(0));
        model.getBoard().placeConstructor(model.getBoard().getTile(pos2), pList.get(0).getAllConstructors().get(1));

        setMaxLevel(model.getBoard(), new Position(0,1));
        setMaxLevel(model.getBoard(), new Position(1,0));

        Apollo ap = (Apollo)pList.get(0).getGod();
        ap.deactivateIfNeeded(model);

        assertFalse(pList.get(0).getAllConstructors().get(0).getCanMove(), "The constructor should be inactive");

    }

    @Test
    void occupiedAllAround(){
        List<Player> pList = createPlayer(new Apollo(), new Athena());
        Model model = new Model(pList);

        model.getGameState().setCurrentPlayer(pList.get(0));

        Position pos1 = new Position(0,0);
        Position pos2 = new Position(1,1);

        model.getBoard().placeConstructor(model.getBoard().getTile(pos1), pList.get(0).getAllConstructors().get(0));
        model.getBoard().placeConstructor(model.getBoard().getTile(pos2), pList.get(0).getAllConstructors().get(1));

        model.getBoard().placeConstructor(model.getBoard().getTile(new Position(1,0)), pList.get(1).getAllConstructors().get(0));
        model.getBoard().placeConstructor(model.getBoard().getTile(new Position(0,1)), pList.get(1).getAllConstructors().get(1));

        Apollo ap = (Apollo)pList.get(0).getGod();
        ap.deactivateIfNeeded(model);

        assertTrue(pList.get(0).getAllConstructors().get(0).getCanMove(), "The constructor should be active");
    }

    @Test
    void getMoveAddListStandard(){
        List<Player> pList = createPlayer(new Apollo(), new Athena());
        Model model = new Model(pList);

        model.getGameState().setCurrentPlayer(pList.get(0));
        model.setCurrentConstructor(pList.get(0).getAllConstructors().get(0));

        Position pos1 = new Position(1,1);

        model.getBoard().placeConstructor(model.getBoard().getTile(pos1), pList.get(0).getAllConstructors().get(0));

        Apollo ap = (Apollo)pList.get(0).getGod();
        List<Position> list = ap.getMoveAddList(model);

        assertEquals(0, list.size(), "Should be empty");
    }

    @Test
    void getMoveAddWithSomething(){
        List<Player> pList = createPlayer(new Apollo(), new Athena());
        Model model = new Model(pList);

        model.getGameState().setCurrentPlayer(pList.get(0));
        model.setCurrentConstructor(pList.get(0).getAllConstructors().get(0));

        Position pos1 = new Position(0,0);
        model.getBoard().placeConstructor(model.getBoard().getTile(pos1), pList.get(0).getAllConstructors().get(0));

        setMaxLevel(model.getBoard(), new Position(1,1));

        model.getBoard().placeConstructor(model.getBoard().getTile(new Position(1,0)), pList.get(1).getAllConstructors().get(0));
        model.getBoard().placeConstructor(model.getBoard().getTile(new Position(0,1)), pList.get(1).getAllConstructors().get(1));

        Apollo ap = (Apollo)pList.get(0).getGod();
        List<Position> list = ap.getMoveAddList(model);

        assertEquals(2, list.size(), "Should contain 2 elements");
        assertEquals(list.get(0).getRow(), 0, "Should be 0");
        assertEquals(list.get(0).getCol(), 1, "Should be 1");
        assertEquals(list.get(1).getRow(), 1, "Should be 1");
        assertEquals(list.get(1).getCol(), 0, "Should be 0");
    }

    @Test
    void getMoveAddOtherConstructorSamePlayer(){
        List<Player> pList = createPlayer(new Apollo(), new Athena());
        Model model = new Model(pList);

        model.getGameState().setCurrentPlayer(pList.get(0));
        model.setCurrentConstructor(pList.get(0).getAllConstructors().get(0));

        Position pos1 = new Position(0,0);
        Position pos2 = new Position(1,1);

        model.getBoard().placeConstructor(model.getBoard().getTile(pos1), pList.get(0).getAllConstructors().get(0));
        model.getBoard().placeConstructor(model.getBoard().getTile(pos2), pList.get(0).getAllConstructors().get(1));

        model.getBoard().placeConstructor(model.getBoard().getTile(new Position(1,0)), pList.get(1).getAllConstructors().get(0));
        model.getBoard().placeConstructor(model.getBoard().getTile(new Position(0,1)), pList.get(1).getAllConstructors().get(1));

        Apollo ap = (Apollo)pList.get(0).getGod();
        List<Position> list = ap.getMoveAddList(model);

        assertEquals(2, list.size(), "Should contain 2 elements");
        assertEquals(list.get(0).getRow(), 0, "Should be 0");
        assertEquals(list.get(0).getCol(), 1, "Should be 1");
        assertEquals(list.get(1).getRow(), 1, "Should be 1");
        assertEquals(list.get(1).getCol(), 0, "Should be 0");
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