package it.polimi.ingsw.Message.TileToShowMessages;

import it.polimi.ingsw.Client.GraphicElements.Board.BoardScene;
import it.polimi.ingsw.Client.PlaySpace;
import it.polimi.ingsw.Controller.MiniController.MiniController;
import it.polimi.ingsw.Message.GameMessage;
import it.polimi.ingsw.Model.Position;
import it.polimi.ingsw.Model.PossiblePhases;

import java.io.Serializable;
import java.util.List;

public abstract class TileToShowMessage extends GameMessage implements Serializable{
    private static final long serialVersionUID = 1L;
    private final List<Position> tileToShow;
    protected MiniController miniController;

    public TileToShowMessage(String player, PossiblePhases phase, List<Position> list) {
        super(player, phase);
        this.tileToShow = list;
    }

    @Override
    public void autoSetMessage(boolean isMyTurn, boolean isCLI) {
        String text;

        if(isMyTurn){
            text = getPhase().toString(true);
        } else {
            text = getIdPlayer() + getPhase().toString(false);
        }

        if(!isCLI){
            setPhase(getPhase());
        }

        setMessage(text);
    }

    public List<Position> getTileToShow() {
        return this.tileToShow;
    }

    public MiniController getMiniController(){
        return this.miniController;
    }

    public void updatePlaySpace(PlaySpace playSpace)    {
        playSpace.setTileToShow(getTileToShow());
    }

    public void setPhase(PossiblePhases phase){
        switch (phase){
            case CHOOSE_CONSTRUCTOR,MOVE,SPECIAL_CHOOSE_CONSTRUCTOR,SPECIAL_MOVE -> {
                BoardScene.setMove(true);
                BoardScene.setBuild(false);
                if (phase==PossiblePhases.SPECIAL_CHOOSE_CONSTRUCTOR){
                    BoardScene.setSpecialChooseConstructor(true);
                }
                if (phase==PossiblePhases.SPECIAL_MOVE)
                    BoardScene.setSpecial(true);

            }
            case BUILD,SPECIAL_BUILD -> {
                BoardScene.setMove(false);
                BoardScene.setBuild(true);
                BoardScene.setSpecial(PossiblePhases.SPECIAL_BUILD == phase);
            }
        }
    }


    @Override
    public void updateGUI(PlaySpace playSpace) {
        playSpace.tileToShowGUI(getTileToShow());
    }
}
