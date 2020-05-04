package it.polimi.ingsw.Controller.MiniController;

import it.polimi.ingsw.Client.PlaySpace;

public interface MiniController {
    public boolean checkPos(String input, PlaySpace playSpace, StringBuilder stringBuilder);

    public String getMessage(String input);
}
