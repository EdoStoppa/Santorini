package it.polimi.ingsw.Controller.MiniController;

import it.polimi.ingsw.Client.PlaySpace;
import it.polimi.ingsw.Model.Position;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.RepetitionInfo;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CanEndMiniControllerTest {
    CanEndMiniController canEndMiniController = new CanEndMiniController();

    @RepeatedTest(8)
    void checkPosTest(RepetitionInfo repetitionInfo)    {
        String input;
        if(repetitionInfo.getCurrentRepetition() == 1)  {
            input = "3"; //length < 3;
            assertFalse(canEndMiniController.checkPos(input, null, null));
        }
        else if(repetitionInfo.getCurrentRepetition() == 2) {
            input = "end";
            assertTrue(canEndMiniController.checkPos(input, null, null));
        }
        else if(repetitionInfo.getCurrentRepetition() == 3) {
            input = "-1,2";
            assertFalse(canEndMiniController.checkPos(input, null, null));
        }
        else if(repetitionInfo.getCurrentRepetition() == 4) {
            input = "5,2";
            assertFalse(canEndMiniController.checkPos(input, null, null));
        }
        else if(repetitionInfo.getCurrentRepetition() == 5) {
            input = "2,-1";
            assertFalse(canEndMiniController.checkPos(input, null, null));
        }
        else if(repetitionInfo.getCurrentRepetition() == 6) {
            input = "2,5";
            assertFalse(canEndMiniController.checkPos(input, null, null));
        }
        else if(repetitionInfo.getCurrentRepetition() == 7) {
            input = "2,2";
            PlaySpace playSpace = new PlaySpace();
            List<Position> tileToShow = new ArrayList<>();
            tileToShow.add(new Position(2, 2));
            playSpace.setTileToShow(tileToShow);

            assertTrue(canEndMiniController.checkPos(input, playSpace, null));
        }
        else if(repetitionInfo.getCurrentRepetition() == 8) {
            //controllare eccezione.
        }
    }

    @RepeatedTest(2)
    void getMessage(RepetitionInfo repetitionInfo)  {
        String input;
        if(repetitionInfo.getCurrentRepetition() == 1)  {
            input = "end";
            assertEquals("end -1,-1", canEndMiniController.getMessage(input), "The string should be the same");
        }
        else if(repetitionInfo.getCurrentRepetition() == 2)  {
            input = "3,4";
            assertEquals("standard 3,4", canEndMiniController.getMessage(input));
        }
    }

    @RepeatedTest(2)
    void getMessageGuiTest(RepetitionInfo repetitionInfo)   {
        String input;
        if(repetitionInfo.getCurrentRepetition() == 1)  {
            input = "end";
            assertEquals("end -1,-1", canEndMiniController.getMessageGui(input));
        }

        else if(repetitionInfo.getCurrentRepetition() == 2) {
            input = "3,4";
            assertEquals("standard 3,4", canEndMiniController.getMessageGui(input));
        }
    }

    @RepeatedTest(8)
    void checkPosGuiTest(RepetitionInfo repetitionInfo)    {
        String input;
        if(repetitionInfo.getCurrentRepetition() == 1)  {
            input = "3"; //length < 3;
            assertFalse(canEndMiniController.checkPosGui(input, null, null));
        }
        else if(repetitionInfo.getCurrentRepetition() == 2) {
            input = "end";
            assertTrue(canEndMiniController.checkPosGui(input, null, null));
        }
        else if(repetitionInfo.getCurrentRepetition() == 3) {
            input = "-1,2";
            assertFalse(canEndMiniController.checkPosGui(input, null, null));
        }
        else if(repetitionInfo.getCurrentRepetition() == 4) {
            input = "5,2";
            assertFalse(canEndMiniController.checkPosGui(input, null, null));
        }
        else if(repetitionInfo.getCurrentRepetition() == 5) {
            input = "2,-1";
            assertFalse(canEndMiniController.checkPosGui(input, null, null));
        }
        else if(repetitionInfo.getCurrentRepetition() == 6) {
            input = "2,5";
            assertFalse(canEndMiniController.checkPosGui(input, null, null));
        }
        else if(repetitionInfo.getCurrentRepetition() == 7) {
            input = "2,2";
            PlaySpace playSpace = new PlaySpace();
            List<Position> tileToShow = new ArrayList<>();
            tileToShow.add(new Position(2, 2));
            playSpace.setTileToShow(tileToShow);

            assertTrue(canEndMiniController.checkPosGui(input, playSpace, null));
        }
        else if(repetitionInfo.getCurrentRepetition() == 8) {
            //controllare eccezione.
        }
    }
}