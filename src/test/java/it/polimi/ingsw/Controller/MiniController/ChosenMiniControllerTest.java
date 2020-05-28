package it.polimi.ingsw.Controller.MiniController;

import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.RepetitionInfo;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ChosenMiniControllerTest {
    ChosenMiniController chosenMiniController2 = new ChosenMiniController(2);
    ChosenMiniController chosenMiniController3 = new ChosenMiniController(3);

    @RepeatedTest(5)
    void checkPosTest(RepetitionInfo repetitionInfo)    {
        String input;
        if(repetitionInfo.getCurrentRepetition() == 1)  {
            input = "11";
            assertFalse(chosenMiniController2.checkPos(input, null, null));
            assertFalse(chosenMiniController3.checkPos(input, null, null));
        }
        else if(repetitionInfo.getCurrentRepetition() == 2) {
            input = "2";
            assertFalse(chosenMiniController2.checkPos(input, null, new StringBuilder()));
            assertTrue(chosenMiniController3.checkPos(input, null, new StringBuilder()));
        }
        else if(repetitionInfo.getCurrentRepetition() == 3) {
            input = "3";
            assertFalse(chosenMiniController2.checkPos(input, null, new StringBuilder()));
            assertFalse(chosenMiniController3.checkPos(input, null, new StringBuilder()));
        }
        else if(repetitionInfo.getCurrentRepetition() == 4) {
            input = "1";
            assertTrue(chosenMiniController2.checkPos(input, null, null));
            assertTrue(chosenMiniController3.checkPos(input, null, null));
        }
        else if(repetitionInfo.getCurrentRepetition() == 5) {
            input = "a";
            assertFalse(chosenMiniController2.checkPos(input, null, null));
            assertFalse(chosenMiniController3.checkPos(input, null, null));
        }
    }

    @Test
    void getMessageTest()   {
        String input = "3,4";
        assertEquals("3,4", chosenMiniController2.getMessage(input));
        assertEquals("3,4", chosenMiniController3.getMessage(input));
    }
}