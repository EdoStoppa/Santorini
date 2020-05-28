package it.polimi.ingsw.Controller.MiniController;

import it.polimi.ingsw.Client.PlaySpace;
import it.polimi.ingsw.Model.Position;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.RepetitionInfo;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class StandardMiniControllerTest {
    StandardMiniController standardMiniController = new StandardMiniController();

    @RepeatedTest(7)
    void checkPosTest(RepetitionInfo repetitionInfo)    {
        String input;
        if(repetitionInfo.getCurrentRepetition() == 1)  {
            input ="3";
            assertFalse(standardMiniController.checkPos(input, null, null));
        }
        else if(repetitionInfo.getCurrentRepetition() == 2) {
            input = "5,2";
            assertFalse(standardMiniController.checkPos(input, null, null));
        }
        else if(repetitionInfo.getCurrentRepetition() == 3) {
            input = "1,5";
            assertFalse(standardMiniController.checkPos(input, null, null));
        }
        else if(repetitionInfo.getCurrentRepetition() == 4) {
            PlaySpace playSpace = new PlaySpace();
            List<Position> tileToShow = new ArrayList<>();
            tileToShow.add(new Position(1,2));
            tileToShow.add(new Position(3,4));
            playSpace.setTileToShow(tileToShow);
            input = "1,2";
            assertTrue(standardMiniController.checkPos(input, playSpace, null));
        }
    }

    @Test
    void getMessageTest()   {
        String input = "1,2";
        assertEquals("standard 1,2", standardMiniController.getMessage(input));
    }
}