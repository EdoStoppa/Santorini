package it.polimi.ingsw.Controller.MiniController;

import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.RepetitionInfo;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PickGodMiniControllerTest {
    PickGodMiniController pickGodMiniController2 = new PickGodMiniController(2, 2);
    PickGodMiniController pickGodMiniController3 = new PickGodMiniController(3, 3);

    @RepeatedTest(14)
    void checKPosTest(RepetitionInfo repetitionInfo)    {
        String input;
        if(repetitionInfo.getCurrentRepetition() == 1)  {
            input = "1";
            assertFalse(pickGodMiniController2.checkPos(input, null, new StringBuilder()));
        }
        else if(repetitionInfo.getCurrentRepetition() == 2) {
            input = "123";
            assertFalse(pickGodMiniController2.checkPos(input, null, new StringBuilder()));
        }
        else if(repetitionInfo.getCurrentRepetition() == 3) {
            input = "1,1";
            assertFalse(pickGodMiniController2.checkPos(input, null, new StringBuilder()));
        }
        else if(repetitionInfo.getCurrentRepetition() == 4) {
            input = "1,a";
            assertFalse(pickGodMiniController2.checkPos(input, null, new StringBuilder()));
        }
        else if(repetitionInfo.getCurrentRepetition() == 5) {
            input = "-1,2";
            assertFalse(pickGodMiniController2.checkPos(input, null, new StringBuilder()));
        }
        else if(repetitionInfo.getCurrentRepetition() == 6) {
            input = "1,3";
            assertFalse(pickGodMiniController2.checkPos(input, null, new StringBuilder()));
        }
        else if(repetitionInfo.getCurrentRepetition() == 7) {
            input = "0,1";
            assertTrue(pickGodMiniController2.checkPos(input, null, null));
        }
        else if(repetitionInfo.getCurrentRepetition() == 8) {
            input = "5";
            assertFalse(pickGodMiniController3.checkPos(input, null, new StringBuilder()));
        }
        else if(repetitionInfo.getCurrentRepetition() == 9) {
            input = "1,1,2";
            assertFalse(pickGodMiniController3.checkPos(input, null, new StringBuilder()));
        }
        else if(repetitionInfo.getCurrentRepetition() == 10)    {
            input = "12345";
            assertFalse(pickGodMiniController3.checkPos(input, null, new StringBuilder()));
        }
        else if(repetitionInfo.getCurrentRepetition() == 11)    {
            input = "1,2,c";
            assertFalse(pickGodMiniController3.checkPos(input, null, new StringBuilder()));
        }
        else if(repetitionInfo.getCurrentRepetition() == 12)    {
            input ="-1,2,0";
            assertFalse(pickGodMiniController3.checkPos(input, null, new StringBuilder()));
        }
        else if(repetitionInfo.getCurrentRepetition() == 13)    {
            input = "1,2,4";
            assertFalse(pickGodMiniController3.checkPos(input, null, new StringBuilder()));
        }
        else if(repetitionInfo.getCurrentRepetition() == 14)    {
            input = "0,1,2";
            assertTrue(pickGodMiniController3.checkPos(input, null, new StringBuilder()));
        }
    }

    @Test
    void getMessageTest()   {
        String input = "0,1,2";
        assertEquals("0,1,2", pickGodMiniController3.getMessage(input));
    }

    @RepeatedTest(14)
    void checKPosGuiTest(RepetitionInfo repetitionInfo)    {
        String input;
        if(repetitionInfo.getCurrentRepetition() == 1)  {
            input = "1";
            assertFalse(pickGodMiniController2.checkPosGui(input, null, new StringBuilder()));
        }
        else if(repetitionInfo.getCurrentRepetition() == 2) {
            input = "123";
            assertFalse(pickGodMiniController2.checkPosGui(input, null, new StringBuilder()));
        }
        else if(repetitionInfo.getCurrentRepetition() == 3) {
            input = "1,1";
            assertFalse(pickGodMiniController2.checkPosGui(input, null, new StringBuilder()));
        }
        else if(repetitionInfo.getCurrentRepetition() == 4) {
            input = "1,a";
            assertFalse(pickGodMiniController2.checkPosGui(input, null, new StringBuilder()));
        }
        else if(repetitionInfo.getCurrentRepetition() == 5) {
            input = "-1,2";
            assertFalse(pickGodMiniController2.checkPosGui(input, null, new StringBuilder()));
        }
        else if(repetitionInfo.getCurrentRepetition() == 6) {
            input = "1,3";
            assertFalse(pickGodMiniController2.checkPosGui(input, null, new StringBuilder()));
        }
        else if(repetitionInfo.getCurrentRepetition() == 7) {
            input = "0,1";
            assertTrue(pickGodMiniController2.checkPosGui(input, null, null));
        }
        else if(repetitionInfo.getCurrentRepetition() == 8) {
            input = "5";
            assertFalse(pickGodMiniController3.checkPosGui(input, null, new StringBuilder()));
        }
        else if(repetitionInfo.getCurrentRepetition() == 9) {
            input = "1,1,2";
            assertFalse(pickGodMiniController3.checkPosGui(input, null, new StringBuilder()));
        }
        else if(repetitionInfo.getCurrentRepetition() == 10)    {
            input = "12345";
            assertFalse(pickGodMiniController3.checkPosGui(input, null, new StringBuilder()));
        }
        else if(repetitionInfo.getCurrentRepetition() == 11)    {
            input = "1,2,c";
            assertFalse(pickGodMiniController3.checkPosGui(input, null, new StringBuilder()));
        }
        else if(repetitionInfo.getCurrentRepetition() == 12)    {
            input ="-1,2,0";
            assertFalse(pickGodMiniController3.checkPosGui(input, null, new StringBuilder()));
        }
        else if(repetitionInfo.getCurrentRepetition() == 13)    {
            input = "1,2,4";
            assertFalse(pickGodMiniController3.checkPosGui(input, null, new StringBuilder()));
        }
        else if(repetitionInfo.getCurrentRepetition() == 14)    {
            input = "0,1,2";
            assertTrue(pickGodMiniController3.checkPosGui(input, null, new StringBuilder()));
        }
    }

    @Test
    void getMessageGuiTest()   {
        String input = "0,1,2";
        assertEquals("0,1,2", pickGodMiniController3.getMessageGui(input));
    }
}