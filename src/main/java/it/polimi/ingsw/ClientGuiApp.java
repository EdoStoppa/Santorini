package it.polimi.ingsw;



import it.polimi.ingsw.Client.ClientGUI;
import it.polimi.ingsw.Client.EventHandler;
import it.polimi.ingsw.Message.ServerMessage.PickGodMessage;
import it.polimi.ingsw.Message.ServerMessage.ServerMessage;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.Serializable;


public class ClientGuiApp extends Application implements EventHandler, Serializable {
    private static final long serialVersionUID = 1L;
    private static ClientGUI  client;
    private static Stage primaryStage;
    private int phase=0;

    public void setPhase(int phase) {
        this.phase = phase;
        System.out.println(phase);
    }

    @Override
    public void init() throws Exception {
        client=new ClientGUI("127.0.0.1", 12345,this);
        client.run();
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        //fistLayout
        this.primaryStage=primaryStage;
        VBox layout1= new VBox(50);
        Label welcome= new Label("welcome to santorini\n 2 or 3 player mode?");
        HBox Buttons= new HBox(150);
        Button twoPlayer= new Button("2 player");
        Button treePlayer=new Button("3 player");
        twoPlayer.setOnAction(e->{
            client.asyncWriteToSocketGUI("2");
            Scene scene=new Scene(ChooseName(),800,710);
            this.primaryStage.setScene(scene);
        });
        treePlayer.setOnAction(e->{
            client.asyncWriteToSocketGUI("3");
            Scene scene=new Scene(ChooseName(),800,710);
            primaryStage.setScene(scene);
        });
        Buttons.getChildren().addAll(twoPlayer,treePlayer);
        Buttons.setAlignment(Pos.CENTER);
        layout1.setAlignment(Pos.CENTER);
        layout1.getChildren().addAll(welcome,Buttons);
        Scene scene1= new Scene(layout1,800,710);
        primaryStage.setScene(scene1);
        primaryStage.show();
    }


    private Parent ChooseName(){
        VBox layout=new VBox(40);
        Label textName= new Label("enter your name");
        TextField name= new TextField();
        Button go= new Button("go");
        go.setOnAction(e->{
            client.asyncWriteToSocketGUI(name.getText());
            Scene sceneWait= new Scene(waitScene(),800,710);
            primaryStage.setScene(sceneWait);
        });
        name.setMaxWidth(150);
        layout.setAlignment(Pos.CENTER);
        layout.getChildren().addAll(textName,name,go);
        return layout;
    }
        public static void ChooseGod(PickGodMessage message){
        BorderPane layout=new BorderPane();
        HBox firstLine= new HBox(10);
        HBox secondLine=new HBox(10);
        HBox thirdLine=new HBox(10);
        HBox fourLine= new HBox(10);
        CheckBox apollo=new CheckBox();
        CheckBox artemis=new CheckBox();
        CheckBox athena= new CheckBox();
        CheckBox atlas= new CheckBox();
        CheckBox demeter= new CheckBox();
        CheckBox hephaestus= new CheckBox();
        CheckBox minotaur=new CheckBox();
        CheckBox pan= new CheckBox();
        CheckBox prometheus= new CheckBox();
        Image apoll= new Image("file:Apollo.png");
        Image artemi= new Image("file:Artemis.png");
        Image athen= new Image("file:Athena.png");
        Image atla= new Image("file:Atlas.png");
        Image demete= new Image("file:Demeter.png");
        Image hephaestu= new Image("file:Hephaestus.png");
        Image minotau= new Image("file:Minotaur.png");
        Image pa=new Image("file:Pan.png");
        Image prometheu= new Image("file:Prometheus.png");
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
        Label ApolloDesctripion =new Label(message.GetGod(0).getGodPower());
        Label ArtemisDesctripion =new Label(message.GetGod(1).getGodPower());
        Label AthenaDesctripion =new Label(message.GetGod(2).getGodPower());
        Label AtlasDesctripion =new Label(message.GetGod(3).getGodPower());
        Label DemeterDesctripion =new Label(message.GetGod(4).getGodPower());
        Label HepheastusDesctripion =new Label(message.GetGod(5).getGodPower());
        Label MinotausDesctripion =new Label(message.GetGod(6).getGodPower());
        Label PanDesctripion =new Label(message.GetGod(7).getGodPower());
        Label PrometheusDesctripion =new Label(message.GetGod(8).getGodPower());
        firstLine.getChildren().addAll(apollo,IWApollo,ApolloDesctripion,artemis,IWArtemis,ArtemisDesctripion,athena,IWAthena,AthenaDesctripion);
        secondLine.getChildren().addAll(atlas,IWAtlas,demeter,IWDemeter,hephaestus,IWHephaestus);
        thirdLine.getChildren().addAll(minotaur,IWMinotaur,pan,IWPan,prometheus,IWPrometheus);
        VBox griglia=new VBox(50);
        Button chosenGods=new Button("enter");
        fourLine.getChildren().add(chosenGods);
        fourLine.setAlignment(Pos.CENTER);
        griglia.getChildren().addAll(firstLine,secondLine,thirdLine,fourLine);
        Pane left= new Pane();
        left.setPrefSize(50,720);
        layout.setCenter(griglia);
        layout.setLeft(left);
        Scene scene3= new Scene(layout,800,710);
        primaryStage.setScene(scene3);
    }

    private static void setGodImage(ImageView IWGod){
        IWGod.setFitHeight(170.66);
        IWGod.setFitWidth(110);
    }

    private Parent waitScene(){
        Label label= new Label("wait");
        VBox layout=new VBox(10);

        layout.setAlignment(Pos.CENTER);
        layout.getChildren().add(label);
        return layout;

    }


    @Override
    public void update(ServerMessage message) {
        Platform.runLater(()->{
            System.out.println("sssss");
            message.buildScene();
        });

    }

}
