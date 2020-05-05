package it.polimi.ingsw.Controller.MiniController;

import it.polimi.ingsw.Client.PlaySpace;

public class BaseMiniController implements MiniController{
    @Override
    public boolean checkPos(String input, PlaySpace playSpace, StringBuilder stringBuilder) {
        return true;
    }

    @Override
    public String getMessage(String input) {
        return input;
    }
}
