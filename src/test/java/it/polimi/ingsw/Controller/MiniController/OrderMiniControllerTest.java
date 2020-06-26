package it.polimi.ingsw.Controller.MiniController;

import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.RepetitionInfo;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class OrderMiniControllerTest {
    OrderMiniController orderMiniController = new OrderMiniController(createPlayerList());

    @RepeatedTest(2)
    void checkPosTest(RepetitionInfo repetitionInfo)    {
        String input;
        if(repetitionInfo.getCurrentRepetition() == 1)  {
            input = "uno";
            assertTrue(orderMiniController.checkPos(input, null, null));
        }
        else if(repetitionInfo.getCurrentRepetition() == 2) {
            input = "tre";
            assertFalse(orderMiniController.checkPos(input, null, null));
        }
    }

    @Test
    void getMessageTest()   {
        String input = "uno";
        assertEquals("uno", orderMiniController.getMessage(input));
    }

    @RepeatedTest(2)
    void checkPosGuiTest(RepetitionInfo repetitionInfo)    {
        String input;
        if(repetitionInfo.getCurrentRepetition() == 1)  {
            input = "uno";
            assertTrue(orderMiniController.checkPosGui(input, null, null));
        }
        else if(repetitionInfo.getCurrentRepetition() == 2) {
            input = "tre";
            assertFalse(orderMiniController.checkPosGui(input, null, null));
        }
    }

    @Test
    void getMessageGuiTest()   {
        String input = "uno";
        assertEquals("uno", orderMiniController.getMessageGui(input));
    }

    private List<String> createPlayerList() {
        List<String> pList = new ArrayList<>();
        pList.add("uno");
        pList.add("due");

        return pList;
    }
}