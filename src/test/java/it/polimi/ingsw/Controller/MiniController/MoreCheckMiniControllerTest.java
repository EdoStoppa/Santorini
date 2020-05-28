package it.polimi.ingsw.Controller.MiniController;

import it.polimi.ingsw.Client.PlaySpace;
import it.polimi.ingsw.Model.Position;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.RepetitionInfo;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MoreCheckMiniControllerTest {
    MoreCheckMiniController moreCheckMiniController = new MoreCheckMiniController();

    @RepeatedTest(6)
    void checkPosTest(RepetitionInfo repetitionInfo)    {
        String input;
        if(repetitionInfo.getCurrentRepetition() == 1)  {
            input = "-1,2";
            assertFalse(moreCheckMiniController.checkPos(input, null, null));
        }
        else if(repetitionInfo.getCurrentRepetition() == 2) {
            input = "5,2";
            assertFalse(moreCheckMiniController.checkPos(input, null, null));
        }
        else if(repetitionInfo.getCurrentRepetition() == 3) {
            input = "2,-1";
            assertFalse(moreCheckMiniController.checkPos(input, null, null));
        }
        else if(repetitionInfo.getCurrentRepetition() == 4) {
            input = "2,5";
            assertFalse(moreCheckMiniController.checkPos(input, null, null));
        }
        else if(repetitionInfo.getCurrentRepetition() == 5) {
            input = "2,2";
            PlaySpace playSpace = new PlaySpace();
            List<Position> tileToShow = new ArrayList<>();
            List<Position> tileToCheck = new ArrayList<>();
            tileToShow.add(new Position(2,2));
            tileToCheck.add(new Position(3,4));
            playSpace.setTileToShow(tileToShow);
            playSpace.setTileToCheck(tileToCheck);

            assertTrue(moreCheckMiniController.checkPos(input, playSpace, null));
        }
        else if(repetitionInfo.getCurrentRepetition() == 6) {
            input = "2,2";
            PlaySpace playSpace = new PlaySpace();
            assertFalse(moreCheckMiniController.checkPos(input, playSpace, null));
        }
    }

    @RepeatedTest(2)
    void getMessageTest(RepetitionInfo repetitionInfo)  {
        PlaySpace playSpace = new PlaySpace();
        String input;
        List<Position> toCheck = new ArrayList<>();
        if(repetitionInfo.getCurrentRepetition() == 1)  {
            input = "3,4";
            toCheck.add(new Position(3, 4));
            moreCheckMiniController.setToCheck(toCheck);
            assertEquals("skipPhase 3,4", moreCheckMiniController.getMessage(input));
        }
        else if(repetitionInfo.getCurrentRepetition() == 2) {
            input = "3,4";
            toCheck.add(new Position(2,2));
            moreCheckMiniController.setToCheck(toCheck);
            assertEquals("standard 3,4", moreCheckMiniController.getMessage(input));
        }
    }

    @RepeatedTest(2)
    void moreCheckTest(RepetitionInfo repetitionInfo)   {
        List<Position> toCheck = new ArrayList<>();
        toCheck.add(new Position(3,4));
        toCheck.add(new Position(2,2));
        if(repetitionInfo.getCurrentRepetition() == 1)  {
            assertTrue(moreCheckMiniController.moreCheck(new Position(3, 4), toCheck));
        }
        else if(repetitionInfo.getCurrentRepetition() == 2) {
            assertFalse(moreCheckMiniController.moreCheck(new Position(1, 2), toCheck));
        }
    }
}