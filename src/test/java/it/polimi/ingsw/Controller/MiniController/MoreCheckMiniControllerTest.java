package it.polimi.ingsw.Controller.MiniController;

import it.polimi.ingsw.Client.PlaySpace;
import it.polimi.ingsw.Message.PosMessage;
import it.polimi.ingsw.Model.Position;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.RepetitionInfo;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MoreCheckMiniControllerTest {
    PlaySpace playSpace = new PlaySpace();

    @BeforeEach
    void init() {
        List<Position> list = new ArrayList<>();
        list.add(new Position(1,1));
        list.add(new Position(2,2));
        list.add(new Position(3,3));
        playSpace.setTileToShow(list);
    }

    @RepeatedTest(5)
    void checkPosTest(RepetitionInfo repetitionInfo)    {
        if(repetitionInfo.getCurrentRepetition() == 1)  {
            String input = "2";
            MoreCheckMiniController moreCheckMiniController = new MoreCheckMiniController(null);
            assertFalse(moreCheckMiniController.checkPos(input, playSpace, new StringBuilder()));
        }

        else if(repetitionInfo.getCurrentRepetition() == 2) {
            String input = "2,5";
            MoreCheckMiniController moreCheckMiniController = new MoreCheckMiniController(null);
            assertFalse(moreCheckMiniController.checkPos(input, playSpace, new StringBuilder()));
        }

        else if(repetitionInfo.getCurrentRepetition() == 3) {
            String input = "2,3";
            MoreCheckMiniController moreCheckMiniController = new MoreCheckMiniController(null);
            assertFalse(moreCheckMiniController.checkPos(input, playSpace, new StringBuilder()));
        }

        else if (repetitionInfo.getCurrentRepetition() == 4)    {
            String input = "2,2";
            List<Position> list = new ArrayList<>();
            list.add(new Position(2,3));
            MoreCheckMiniController moreCheckMiniController = new MoreCheckMiniController(list);
            assertTrue(moreCheckMiniController.checkPos(input, playSpace, new StringBuilder()));
        }

        else if(repetitionInfo.getCurrentRepetition() == 5) {
            String input = "2,2";
            MoreCheckMiniController moreCheckMiniController = new MoreCheckMiniController(null);
            assertTrue(moreCheckMiniController.checkPos(input, playSpace, new StringBuilder()));
        }
    }

    @RepeatedTest(2)
    void getMessageTest(RepetitionInfo repetitionInfo)  {
        List<Position> list = new ArrayList<>();
        list.add(new Position(1,1));
        list.add(new Position(2,2));
        list.add(new Position(3,3));
        MoreCheckMiniController moreCheckMiniController = new MoreCheckMiniController(list);

        if(repetitionInfo.getCurrentRepetition() == 1)  {
            String input = "2,3";
            assertEquals("standard " + input, moreCheckMiniController.getMessage(input));
        }

        else if(repetitionInfo.getCurrentRepetition() == 2) {
            String input = "2,2";
            assertEquals("skipPhase " + input, moreCheckMiniController.getMessage(input));
        }
    }

    @RepeatedTest(4)
    void checkPosGuiTest(RepetitionInfo repetitionInfo) {
        MoreCheckMiniController moreCheckMiniController = new MoreCheckMiniController(null);

        if(repetitionInfo.getCurrentRepetition() == 1)  {
            String input = "2";
            assertFalse(moreCheckMiniController.checkPosGui(input, playSpace, new StringBuilder()));
        }

        else if(repetitionInfo.getCurrentRepetition() == 2) {
            String input = "2,5";
            assertFalse(moreCheckMiniController.checkPosGui(input, playSpace, new StringBuilder()));
        }

        else if(repetitionInfo.getCurrentRepetition() == 3) {
            String input = "2,3";
            assertFalse(moreCheckMiniController.checkPosGui(input, playSpace, new StringBuilder()));
        }

        else if (repetitionInfo.getCurrentRepetition() == 4)    {
            String input = "2,2";
            assertTrue(moreCheckMiniController.checkPosGui(input, playSpace, new StringBuilder()));
        }
    }

    @RepeatedTest(2)
    void getMessageGuiTest(RepetitionInfo repetitionInfo)  {
        List<Position> list = new ArrayList<>();
        list.add(new Position(1,1));
        list.add(new Position(2,2));
        list.add(new Position(3,3));
        MoreCheckMiniController moreCheckMiniController = new MoreCheckMiniController(list);

        if(repetitionInfo.getCurrentRepetition() == 1)  {
            String input = "2,3";
            assertEquals("standard " + input, moreCheckMiniController.getMessageGui(input));
        }

        else if(repetitionInfo.getCurrentRepetition() == 2) {
            String input = "2,2";
            assertEquals("skipPhase " + input, moreCheckMiniController.getMessageGui(input));
        }
    }

    @RepeatedTest(4)
    void moreCheckTest(RepetitionInfo repetitionInfo)   {
        MoreCheckMiniController moreCheckMiniController = new MoreCheckMiniController(null);
        List<Position> list = new ArrayList<>();
        list.add(new Position(1,1));
        list.add(new Position(2,2));
        list.add(new Position(3,3));

        if(repetitionInfo.getCurrentRepetition() == 1)  {
            assertFalse(moreCheckMiniController.moreCheck(new Position(1,2), null));
        }

        else if(repetitionInfo.getCurrentRepetition() == 2) {
            Position position = new Position(1,2);
            assertFalse(moreCheckMiniController.moreCheck(position, list));
        }

        else if(repetitionInfo.getCurrentRepetition() == 3) {
            Position position = new Position(3,3);
            assertTrue(moreCheckMiniController.moreCheck(position, list));
        }
    }
}