package it.polimi.ingsw.Controller.MiniController;

import it.polimi.ingsw.Client.PlaySpace;

import java.io.Serializable;

public class BaseMiniController implements MiniController, Serializable {
    private static final long serialVersionUID = 1L;
    @Override
    public boolean checkPos(String input, PlaySpace playSpace, StringBuilder stringBuilder) {
        return (input.length() >= 1);
    }

    @Override
    public String getMessage(String input) {
        return input;
    }

    @Override
    public String getMessageGui(String message) {
         return getMessage(message);
    }

    @Override
    public boolean checkPosGui(String input, PlaySpace playSpace, StringBuilder stringBuilder) {
        return checkPos(input,playSpace,stringBuilder);
    }
}
