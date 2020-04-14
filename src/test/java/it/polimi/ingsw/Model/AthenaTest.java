package it.polimi.ingsw.Model;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AthenaTest {

    @Test
    void shouldActivatePowerSameHeight(){
        Player p1 = new Player("uno", "01/01/2000", 1);
        p1.setGod(new Athena());

        List<Player> list = new ArrayList<Player>();
        list.add(p1);

        Model model = new Model(list);
        model.setCurrentConstructor(p1.getAllConstructors().get(0));

        model.performMove(new Position(1,1));
        model.performMove(new Position(1,2));

        Athena athena = (Athena)p1.getGod();
        boolean b = athena.shouldActivatePower(model);

        assertFalse(b, "Should not be activated");

        model.performMove(new Position(1,4));

        boolean b3 = athena.shouldActivatePower(model);
        assertFalse(b3, "Should not be activated");
    }

    @Test
    void shouldActivatePowerGoingUp(){
        Player p1 = new Player("uno", "01/01/2000", 1);
        p1.setGod(new Athena());

        List<Player> list = new ArrayList<Player>();
        list.add(p1);

        Model model = new Model(list);
        model.setCurrentConstructor(p1.getAllConstructors().get(0));

        model.performMove(new Position(1,1));
        model.getBoard().getTile(new Position(1,2)).increaseConstructionLevel();
        model.performMove(new Position(1,2));

        Athena athena = (Athena)p1.getGod();
        boolean result = athena.shouldActivatePower(model);

        assertTrue(result, "Should be activated");
    }

    @Test
    void shouldActivePowerGoingDown(){
        Player p1 = new Player("uno", "01/01/2000", 1);
        p1.setGod(new Athena());

        List<Player> list = new ArrayList<Player>();
        list.add(p1);

        Model model = new Model(list);
        model.setCurrentConstructor(p1.getAllConstructors().get(0));

        model.getBoard().getTile(new Position(1,1)).increaseConstructionLevel();
        model.performMove(new Position(1,1));
        model.performMove(new Position(1,2));

        Athena athena = (Athena)p1.getGod();
        boolean result = athena.shouldActivatePower(model);

        assertFalse(result, "Should not be activated");
    }

}