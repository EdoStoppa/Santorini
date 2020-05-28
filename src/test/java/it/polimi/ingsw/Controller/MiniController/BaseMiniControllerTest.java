package it.polimi.ingsw.Controller.MiniController;

import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.RepetitionInfo;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BaseMiniControllerTest {
    BaseMiniController baseMiniController = new BaseMiniController();

    @RepeatedTest(2)
    void checkPosTest(RepetitionInfo repetitionInfo)    {
        String input;
        if(repetitionInfo.getCurrentRepetition() == 1)  {
            input = "3,4"; //length >= 1
            assertTrue(baseMiniController.checkPos(input, null, null));
        }
        else    {
            input = "";
            assertFalse(baseMiniController.checkPos(input, null, null));
        }
    }

    @Test
    void getMessageTest()   {
        String input = "3,4";
        assertEquals("3,4", baseMiniController.getMessage(input), "The string should be the same");
    }
}