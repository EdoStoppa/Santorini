package it.polimi.ingsw.Model;

import it.polimi.ingsw.Message.GameMessage;
import it.polimi.ingsw.Message.SpecialActionMessage;
import it.polimi.ingsw.Message.TileToShowMessage;
import it.polimi.ingsw.Observer.Observer;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PrometheusTest {
    private class Receiver implements Observer<GameMessage> {
        private GameMessage message;

        public GameMessage getMessage(){
            return message;
        }

        @Override
        public void update(GameMessage message) {
            this.message = message;
        }
    }

    @Test
    void getWrongPosStandard(){
        List<Player> pList = createPlayer(new Prometheus(), new Athena());
        Model model = new Model(pList);
        Prometheus prometheus = (Prometheus) model.getCurrentGod();

        model.setCurrentConstructor(pList.get(0).getAllConstructors().get(0));
        model.performMove(new Position(1,1));

        assertNull(prometheus.getWrongPos(model), "Should be null because nothing's around constructor");
    }

    @Test
    void getWrongEmptyBecauseCanGoUp(){
        List<Player> pList = createPlayer(new Prometheus(), new Athena());
        Model model = new Model(pList);
        Prometheus prometheus = (Prometheus) model.getCurrentGod();

        model.setCurrentConstructor(pList.get(0).getAllConstructors().get(0));
        model.performMove(new Position(1,1));
        setLevel(model.getBoard(), new Position(1,2), 1);
        setLevel(model.getBoard(), new Position(2,2), 1);
        setLevel(model.getBoard(), new Position(2,1), 1);

        assertNull(prometheus.getWrongPos(model), "Should be null because Prometheus can go up");
    }

    @Test
    void getWrongNotEmptyCantGoUp(){
        List<Player> pList = createPlayer(new Prometheus(), new Athena());
        Model model = new Model(pList);
        Prometheus prometheus = (Prometheus) model.getCurrentGod();

        model.setCurrentConstructor(pList.get(0).getAllConstructors().get(0));
        model.performMove(new Position(1,1));
        setLevel(model.getBoard(), new Position(1,2), 1);
        setLevel(model.getBoard(), new Position(2,2), 1);
        setLevel(model.getBoard(), new Position(2,1), 1);

        prometheus.setCanGoUp(false);

        assertEquals(3, prometheus.getWrongPos(model).size(), "All 3 positions because can't go up");
    }

    @Test
    void getWrongEmptyBecauseConstrIsElevated(){
        List<Player> pList = createPlayer(new Prometheus(), new Athena());
        Model model = new Model(pList);
        Prometheus prometheus = (Prometheus) model.getCurrentGod();

        model.setCurrentConstructor(pList.get(0).getAllConstructors().get(0));
        setLevel(model.getBoard(), new Position(1,1), 2);
        model.performMove(new Position(1,1));

        assertNull(prometheus.getWrongPos(model), "Should be null because constructor is above everything else");
    }

    @Test
    void sameOrDownFull(){
        List<Player> pList = createPlayer(new Prometheus(), new Athena());
        Model model = new Model(pList);
        Prometheus prometheus = (Prometheus) model.getCurrentGod();

        model.setCurrentConstructor(pList.get(0).getAllConstructors().get(0));
        model.performMove(new Position(1,1));

        assertEquals(8, prometheus.sameOrDownLevel(model, pList.get(0).getAllConstructors().get(0)).size(), "Every position should be in the list");
    }

    @Test
    void sameOrDownElevated(){
        List<Player> pList = createPlayer(new Prometheus(), new Athena());
        Model model = new Model(pList);
        Prometheus prometheus = (Prometheus) model.getCurrentGod();

        model.setCurrentConstructor(pList.get(0).getAllConstructors().get(0));
        setLevel(model.getBoard(), new Position(1,1), 1);
        model.performMove(new Position(1,1));

        assertEquals(8, prometheus.sameOrDownLevel(model, pList.get(0).getAllConstructors().get(0)).size(), "Every position should be in the list");
    }

    @Test
    void sameOrDownEmpty(){
        List<Player> pList = createPlayer(new Prometheus(), new Athena());
        Model model = new Model(pList);
        Prometheus prometheus = (Prometheus) model.getCurrentGod();

        model.setCurrentConstructor(pList.get(0).getAllConstructors().get(0));
        for(int i = 0; i < 3; i++){
            for(int j = 0; j < 3; j++){
                if(!(i == 1 && j == 1)){
                    setLevel(model.getBoard(), new Position(i,j), 1);
                }
            }
        }
        model.performMove(new Position(1,1));

        assertEquals(0, prometheus.sameOrDownLevel(model, pList.get(0).getAllConstructors().get(0)).size(), "Should be empty");
    }

    @Test
    void sameOrDownEmptyWithDome(){
        List<Player> pList = createPlayer(new Prometheus(), new Athena());
        Model model = new Model(pList);
        Prometheus prometheus = (Prometheus) model.getCurrentGod();

        model.setCurrentConstructor(pList.get(0).getAllConstructors().get(0));
        for(int i = 0; i < 3; i++){
            for(int j = 0; j < 3; j++){
                if(!(i == 1 && j == 1)){
                    model.getBoard().getTile(new Position(i,j)).setDome(true);
                }
            }
        }
        model.performMove(new Position(1,1));

        assertEquals(0, prometheus.sameOrDownLevel(model, pList.get(0).getAllConstructors().get(0)).size(), "Should be empty (same level but with dome)");
    }

    @Test
    void create(){
        List<Player> pList = createPlayer(new Prometheus(), new Athena());
        Model model = new Model(pList);
        Receiver r = new Receiver();
        model.addObserver(r);
        Prometheus prometheus = (Prometheus) model.getCurrentGod();

        model.setCurrentConstructor(pList.get(0).getAllConstructors().get(0));
        model.performMove(new Position(1,1));

        model.setCurrentConstructor(pList.get(0).getAllConstructors().get(1));
        model.performMove(new Position(2,2));

        prometheus.createPossibleConstructorPos(model);

        assertEquals(2, model.getTileToShow().size(), "Should be both of the constructors' positions");

        assertTrue(r.getMessage() instanceof TileToShowMessage, "This should be a normal message");
    }

    @Test
    void createOneAndOne(){
        List<Player> pList = createPlayer(new Prometheus(), new Athena());
        Model model = new Model(pList);
        Receiver r = new Receiver();
        model.addObserver(r);
        Prometheus prometheus = (Prometheus) model.getCurrentGod();

        for(int i = 0; i < 3; i++){
            for(int j = 0; j < 3; j++){
                if(!(i == 1 && j == 1)){
                    setLevel(model.getBoard(), new Position(i,j), 1);
                }
            }
        }

        model.setCurrentConstructor(pList.get(0).getAllConstructors().get(0));
        model.performMove(new Position(1,1));

        model.setCurrentConstructor(pList.get(0).getAllConstructors().get(1));
        model.performMove(new Position(3,3));

        prometheus.createPossibleConstructorPos(model);

        assertEquals(2, model.getTileToShow().size(), "Should be both of the constructors' positions");

        assertTrue(r.getMessage() instanceof SpecialActionMessage, "This should be a special action message");

        SpecialActionMessage message = (SpecialActionMessage)r.getMessage();
        assertEquals(1, message.getSpecialTile().size(), "Should be only one tile (1,1)");
    }

    @Test
    void oneIsFreeAndCanBuild(){
        List<Player> pList = createPlayer(new Prometheus(), new Athena());
        Model model = new Model(pList);
        Receiver r = new Receiver();
        model.addObserver(r);
        Prometheus prometheus = (Prometheus) model.getCurrentGod();

        for(int i = 0; i < 3; i++){
            for(int j = 0; j < 3; j++){
                if(!(i == 1 && j == 1) && !(i == 1 && j == 2)){
                    setLevel(model.getBoard(), new Position(i,j), 1);
                }
            }
        }

        model.setCurrentConstructor(pList.get(0).getAllConstructors().get(0));
        model.performMove(new Position(1,1));

        model.setCurrentConstructor(pList.get(0).getAllConstructors().get(1));
        model.performMove(new Position(3,3));

        prometheus.createPossibleConstructorPos(model);

        assertEquals(2, model.getTileToShow().size(), "Should be both of the constructors' positions");

        assertTrue(r.getMessage() instanceof TileToShowMessage, "This should be a normal message");
    }

    @Test
    void oneFreeButNoBuild(){
        List<Player> pList = createPlayer(new Prometheus(), new Athena());
        Model model = new Model(pList);
        Receiver r = new Receiver();
        model.addObserver(r);
        Prometheus prometheus = (Prometheus) model.getCurrentGod();

        for(int i = 0; i < 3; i++){
            for(int j = 0; j < 3; j++){
                if(!(i == 1 && j == 1) && !(i == 1 && j == 2)){
                    setLevel(model.getBoard(), new Position(i,j), 4);
                }
            }
        }

        model.setCurrentConstructor(pList.get(0).getAllConstructors().get(0));
        model.performMove(new Position(1,1));

        model.setCurrentConstructor(pList.get(0).getAllConstructors().get(1));
        model.performMove(new Position(3,3));

        prometheus.createPossibleConstructorPos(model);

        assertEquals(2, model.getTileToShow().size(), "Should be both of the constructors' positions");

        assertTrue(r.getMessage() instanceof SpecialActionMessage, "This should be a special message");
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