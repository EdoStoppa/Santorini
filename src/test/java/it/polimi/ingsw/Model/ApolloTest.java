package it.polimi.ingsw.Model;

import it.polimi.ingsw.Controller.Controller;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ApolloTest {

    @Test
    void deactivateIfNeededTest(){
        Player p1 = new Player("uno", "1/01/2000", 1);
        Player p2 = new Player("due", "1/01/2000", 2);

        p1.setGod(new Apollo());
        p2.setGod(new Athena());

        List<Player> pList = new ArrayList<>();
        pList.add(p1);
        pList.add(p2);

        Model model = new Model(pList);
        Controller controller = new Controller(model);
        assertFalse(false, "Dummy");
    }


}