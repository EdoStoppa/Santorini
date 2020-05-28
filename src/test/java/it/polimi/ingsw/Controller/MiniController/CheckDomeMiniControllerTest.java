package it.polimi.ingsw.Controller.MiniController;

import it.polimi.ingsw.Client.PlaySpace;
import it.polimi.ingsw.Model.Position;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.RepetitionInfo;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CheckDomeMiniControllerTest {
    CheckDomeMiniController checkDomeMiniController = new CheckDomeMiniController();

    @RepeatedTest(7)
    void checkPosTest(RepetitionInfo repetitionInfo)    {
        String input;
        if(repetitionInfo.getCurrentRepetition() == 1)  {
            input = "1";
            assertFalse(checkDomeMiniController.checkPos(input, null, null));
        }
        else if(repetitionInfo.getCurrentRepetition() == 2) {
            input = "-1,2";
            assertFalse(checkDomeMiniController.checkPos(input, null, null));
        }
        else if(repetitionInfo.getCurrentRepetition() == 3) {
            input = "5,2";
            assertFalse(checkDomeMiniController.checkPos(input, null, null));
        }
        else if(repetitionInfo.getCurrentRepetition() == 4) {
            input = "2,-1";
            assertFalse(checkDomeMiniController.checkPos(input, null, null));
        }
        else if(repetitionInfo.getCurrentRepetition() == 5) {
            input = "2,5";
            assertFalse(checkDomeMiniController.checkPos(input, null, null));
        }
        else if(repetitionInfo.getCurrentRepetition() == 6) {
            input = "2,2";
            PlaySpace playSpace = new PlaySpace();
            List<Position> tileToShow = new ArrayList<>();
            tileToShow.add(new Position(2,2));
            playSpace.setTileToShow(tileToShow);

            assertTrue(checkDomeMiniController.checkPos(input, playSpace, null));
        }
        else if(repetitionInfo.getCurrentRepetition() == 7) {
            //controlla eccezione
        }
    }
}