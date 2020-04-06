package it.polimi.ingsw.Model;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

class TileTest {
    Tile t = new Tile(new Position(3,4));

    @Nested
    class increaseConstructionLevel {

        @RepeatedTest(5)
        void increaseConstructionLevelTest(RepetitionInfo repetitionInfo)    {
            for(int i = 0; i < repetitionInfo.getCurrentRepetition(); i++)  {
                if(i < 4)   {
                    t.increaseConstructionLevel();
                }
                else if (i == 4)    {
                    break;
                }
            }
            if(repetitionInfo.getCurrentRepetition() == 1)  {
                assertAll(
                        ()  -> assertEquals(1, t.getConstructionLevel(), "Should increase by one"),
                        ()  -> assertFalse(t.getDome(), "The dome should not exists")
                );
            }
            else if(repetitionInfo.getCurrentRepetition() == 2) {
                assertAll(
                        ()  ->  assertEquals(2, t.getConstructionLevel(), "Should increase by one"),
                        ()  ->  assertFalse(t.getDome(), "The dome should not exists")
                );
            }
            else if(repetitionInfo.getCurrentRepetition() == 3) {
                assertAll(
                        ()  ->  assertEquals(3, t.getConstructionLevel(), "Should increase by one"),
                        ()  ->  assertFalse(t.getDome(), "The dome should not exists")
                );
            }
            else if(repetitionInfo.getCurrentRepetition() == 4) {
                assertAll(
                        ()  ->  assertEquals(3, t.getConstructionLevel(), "Should not increase"),
                        ()  ->  assertTrue(t.getDome(), "The dome should exists")
                );
            }
            else if(repetitionInfo.getCurrentRepetition() == 5) {
                assertThrows(IllegalArgumentException.class, () -> t.increaseConstructionLevel());
            }
        }

        @Test
        @DisplayName("With a dome")
        void increaseConstructionLevelTest()    {
            t.setDome(true);
            assertThrows(IllegalArgumentException.class, () -> t.increaseConstructionLevel());
        }
    }


}

