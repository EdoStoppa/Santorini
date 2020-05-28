package it.polimi.ingsw.Controller.MiniController;

import it.polimi.ingsw.Client.PlaySpace;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.RepetitionInfo;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ServerMoveMiniControllerTest {
    ServerMoveMiniController serverMoveMiniController = new ServerMoveMiniController();

    @RepeatedTest(7)
    void checkPosTest(RepetitionInfo repetitionInfo) {
        String input;
        PlaySpace playSpace = new PlaySpace();
        playSpace.setConstructorMatrix(createConstructorMatrix());
        if (repetitionInfo.getCurrentRepetition() == 1) {
            input = "3";
            assertFalse(serverMoveMiniController.checkPos(input, null, null));
        } else if (repetitionInfo.getCurrentRepetition() == 2) {
            input = "-1,2";
            assertFalse(serverMoveMiniController.checkPos(input, playSpace, null));
        } else if (repetitionInfo.getCurrentRepetition() == 3) {
            input = "5,2";
            assertFalse(serverMoveMiniController.checkPos(input, playSpace, null));
        } else if (repetitionInfo.getCurrentRepetition() == 4) {
            input = "1,-1";
            assertFalse(serverMoveMiniController.checkPos(input, playSpace, null));
        } else if (repetitionInfo.getCurrentRepetition() == 5) {
            input = "1,5";
            assertFalse(serverMoveMiniController.checkPos(input, playSpace, null));
        } else if (repetitionInfo.getCurrentRepetition() == 6) {
            input = "1,2";
            assertFalse(serverMoveMiniController.checkPos(input, playSpace, null));
        } else if (repetitionInfo.getCurrentRepetition() == 7) {
            input = "1,3";
            assertTrue(serverMoveMiniController.checkPos(input, playSpace, null));
        }
    }

    @Test
    void getMessageTest()   {
        String input = "1,2";
        assertEquals("1,2", serverMoveMiniController.getMessage(input));
    }


    private int[][] createConstructorMatrix() {
        int[][] m = new int[5][5];
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                if ((i == 1 && j == 2) || (i == 4 && j == 1)) {
                    m[i][j] = 1;
                } else {
                    m[i][j] = 0;
                }
            }
        }
        return m;
    }
}

