package it.polimi.ingsw.Client.GraphicElements;

import it.polimi.ingsw.ClientGuiApp;
import it.polimi.ingsw.Message.ServerMessage.ChosenGodMessage;
import it.polimi.ingsw.Message.ServerMessage.PickGodMessage;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.util.ArrayList;

public class SceneBuilder {
    static Image apoll= new Image("file:Apollo.png");
    static Image artemi= new Image("file:Artemis.png");
    static Image athen= new Image("file:Athena.png");
    static Image atla= new Image("file:Atlas.png");
    static Image demete= new Image("file:Demeter.png");
    static Image hephaestu= new Image("file:Hephaestus.png");
    static Image minotau= new Image("file:Minotaur.png");
    static Image pa=new Image("file:Pan.png");
    static Image prometheu= new Image("file:Prometheus.png");


    public Parent ChooseName(){
        VBox layout=new VBox(40);
        Label textName= new Label("enter your name");
        TextField name= new TextField();
        Button go= new Button("go");
        go.setOnAction(e->{
            ClientGuiApp.getClient().asyncWriteToSocketGUI(name.getText());
            Scene sceneWait= new Scene(waitScene(),800,710);
            ClientGuiApp.getPrimaryStage().setScene(sceneWait);
        });
        name.setMaxWidth(150);
        layout.setAlignment(Pos.CENTER);
        layout.getChildren().addAll(textName,name,go);
        return layout;
    }

    public static void PickGod(PickGodMessage message){
        BorderPane layout=new BorderPane();
        HBox firstLine= new HBox();
        HBox secondLine=new HBox();
        HBox thirdLine=new HBox();
        HBox fourLine= new HBox();
        CheckBox apollo=new CheckBox();
        CheckBox artemis=new CheckBox();
        CheckBox athena= new CheckBox();
        CheckBox atlas= new CheckBox();
        CheckBox demeter= new CheckBox();
        CheckBox hephaestus= new CheckBox();
        CheckBox minotaur=new CheckBox();
        CheckBox pan= new CheckBox();
        CheckBox prometheus= new CheckBox();
        ImageView IWApollo= new ImageView(apoll);
        setGodImage(IWApollo);
        ImageView IWArtemis= new ImageView(artemi);
        setGodImage(IWArtemis);
        ImageView IWAthena= new ImageView(athen);
        setGodImage(IWAthena);
        ImageView IWAtlas= new ImageView(atla);
        setGodImage(IWAtlas);
        ImageView IWDemeter = new ImageView(demete);
        setGodImage(IWDemeter);
        ImageView IWHephaestus = new ImageView(hephaestu);
        setGodImage(IWHephaestus);
        ImageView IWMinotaur= new ImageView(minotau);
        setGodImage(IWMinotaur);
        ImageView IWPan= new ImageView(pa);
        setGodImage(IWPan);
        ImageView IWPrometheus= new ImageView(prometheu);
        setGodImage(IWPrometheus);
        Text ApolloDesctripion =new Text(message.GetGod(0).getGodPower());
        ApolloDesctripion.setWrappingWidth(130);
        Text ArtemisDesctripion =new Text(message.GetGod(1).getGodPower());
        ArtemisDesctripion.setWrappingWidth(130);
        Text AthenaDesctripion =new Text(message.GetGod(2).getGodPower());
        AthenaDesctripion.setWrappingWidth(130);
        Text AtlasDesctripion =new Text(message.GetGod(3).getGodPower());
        AtlasDesctripion.setWrappingWidth(130);
        Text DemeterDesctripion =new Text(message.GetGod(4).getGodPower());
        DemeterDesctripion.setWrappingWidth(130);
        Text HephaestusDesctripion =new Text(message.GetGod(5).getGodPower());
        HephaestusDesctripion.setWrappingWidth(130);
        Text MinotaurDesctripion =new Text(message.GetGod(6).getGodPower());
        MinotaurDesctripion.setWrappingWidth(130);
        Text PanDesctripion =new Text(message.GetGod(7).getGodPower());
        PanDesctripion.setWrappingWidth(130);
        Text PrometheusDesctripion =new Text(message.GetGod(8).getGodPower());
        PrometheusDesctripion.setWrappingWidth(130);
        firstLine.getChildren().addAll(apollo,IWApollo,ApolloDesctripion,artemis,IWArtemis,ArtemisDesctripion,athena,IWAthena,AthenaDesctripion);
        secondLine.getChildren().addAll(atlas,IWAtlas,AtlasDesctripion,demeter,IWDemeter,DemeterDesctripion,hephaestus,IWHephaestus,HephaestusDesctripion);
        thirdLine.getChildren().addAll(minotaur,IWMinotaur,MinotaurDesctripion,pan,IWPan,PanDesctripion,prometheus,IWPrometheus,PrometheusDesctripion);
        VBox griglia=new VBox(30);
        Button chosenGods=new Button("enter");
        chosenGods.setOnAction(e->handleOptions(message.getNumPlayer(),apollo,artemis,athena,atlas,demeter,hephaestus,minotaur,pan,prometheus));
        fourLine.getChildren().add(chosenGods);
        fourLine.setAlignment(Pos.CENTER);
        griglia.getChildren().addAll(firstLine,secondLine,thirdLine,fourLine);
        layout.setCenter(griglia);
        Scene scene3= new Scene(layout,850,710);
        ClientGuiApp.getPrimaryStage().setScene(scene3);
    }

    private static void handleOptions(int numPlayer, CheckBox apollo, CheckBox artemis, CheckBox athena, CheckBox atlas, CheckBox demeter, CheckBox hephaestus, CheckBox minotaur, CheckBox pan, CheckBox prometheus) {
        ArrayList<Integer>chosenGod = new ArrayList<>();
    if (apollo.isSelected()){
      chosenGod.add(0);
    }
    if(artemis.isSelected()){
        chosenGod.add(1);
    }
    if(athena.isSelected()){
        chosenGod.add(2);
    }
    if(atlas.isSelected()){
        chosenGod.add(3);
    }
    if(demeter.isSelected()){
        chosenGod.add(4);
    }
    if(hephaestus.isSelected()){
        chosenGod.add(5);
    }
    if(minotaur.isSelected()){
        chosenGod.add(6);
    }
    if(pan.isSelected()){
        chosenGod.add(7);
    }
    if(prometheus.isSelected()){
        chosenGod.add(8);;
    }
    if (chosenGod.size()==numPlayer){
        String message=null;
        for(int i=0;i<numPlayer;i++){
            message=message+chosenGod.get(i);
            if (i!=numPlayer-1){
                message=message+",";
            }
        }
        ClientGuiApp.getClient().asyncWriteToSocketGUI(message);
        Scene scene=new Scene(waitScene(),810,700);
        ClientGuiApp.getPrimaryStage().setScene(scene);
    }else{
        AlertBox.displayError("select a correct number of God");
    }
    }

    public static void chooseGod(ChosenGodMessage message){

    }



    private static void setGodImage(ImageView IWGod){
        IWGod.setFitHeight(190);
        IWGod.setFitWidth(133.33);
    }

    private static Parent waitScene(){
        Label label= new Label("wait");
        VBox layout=new VBox(10);

        layout.setAlignment(Pos.CENTER);
        layout.getChildren().add(label);
        return layout;

    }
}
