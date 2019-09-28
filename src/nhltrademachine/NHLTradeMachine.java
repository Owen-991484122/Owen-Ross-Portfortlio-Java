/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nhltrademachine;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

/**
 *
 * @author oicr1
 */
public class NHLTradeMachine extends Application {

    // Declaring the global variables that are instances of the Team class 
    private static Team team1;
    private static Team team2;

    //This will have all of the javafx code and will display the main stage
    @Override
    public void start(Stage primaryStage) {

        /* these buttons when clicked will bring up a window with all the team 
        logos and the user will choose one */
        Button btnTeam1 = new Button("Select Team");
        Button btnTeam2 = new Button("Select Team");

        /* These  buttons when clicked will display a window with all the players 
        on the team the user has chose, where the user will choose a player to 
        be added to the trade*/
        Button addPlayerTeam1 = new Button("Add Player");
        Button addPlayerTeam2 = new Button("Add Player");

        // This is the label that will be at the top of the page 
        Label lblTitle = new Label("Hockey Trading Simulator");
        // This will set the font size to 30
        lblTitle.setFont(Font.font(30));

        // This will be the first pane of the scene and will have the title in it
        StackPane title = new StackPane(lblTitle);

        // These vboxes will display the contents of the team the user selected
        VBox team1Pane = new VBox(btnTeam1);
        VBox team2Pane = new VBox(btnTeam2);

        // Added padding to the above vboxes to make it look more presentable
        team1Pane.setPadding(new Insets(10));
        team2Pane.setPadding(new Insets(10));

        // This hbox will have both of the team panes in it
        HBox teamsPane = new HBox(team1Pane, team2Pane);
        teamsPane.setAlignment(Pos.CENTER);

        // These vboxes will display the players the user added to the trade 
        VBox clickedPlayersTeam1 = new VBox();
        VBox clickedPlayersTeam2 = new VBox();

        // setting the padding for the clicked players panes
        clickedPlayersTeam1.setPadding(new Insets(3));
        clickedPlayersTeam2.setPadding(new Insets(3));

        // This is the trade button that when pressed will trade the players
        Button btnTrade = new Button("Trade!");

        // This hbox will have the trade button in it
        HBox tradePane = new HBox(btnTrade);
        tradePane.setAlignment(Pos.CENTER);

        // this hbox will display the players that the users added to the trade
        HBox clickedPlayers = new HBox(clickedPlayersTeam1, clickedPlayersTeam2);
        clickedPlayers.setAlignment(Pos.CENTER);

        /* When the select team button for team 1 is clicked everything in this 
        setOnAction method will be executed */
        btnTeam1.setOnAction((ActionEvent event) -> {
            teamWindow(team1Pane, addPlayerTeam1);
        });

        /* When the select team button for team 2 is clicked everything in this 
        setOnAction method will be executed */
        btnTeam2.setOnAction((ActionEvent event) -> {
            teamWindow(team2Pane, addPlayerTeam2);
        });

        /* when the add player button for team 1 is clicked everything in this 
        setOnAction method will be executed */
        addPlayerTeam1.setOnAction((ActionEvent event) -> {
            clickedPlayer(team1, clickedPlayersTeam1);
        });

        /* when the add player button for team 2 is clicked everything in this 
        setOnAction method will be executed */
        addPlayerTeam2.setOnAction((ActionEvent event) -> {
            clickedPlayer(team2, clickedPlayersTeam2);
        });

        /* when the trade button is clicked every player added to the clicked 
         player pane will be compared based on their rating */
        btnTrade.setOnAction((ActionEvent event) -> {
            trade(team1, team2, clickedPlayersTeam1, clickedPlayersTeam2);
        });

        /* Every pane declared before this is added to this pane so everything 
         will be displayed vertically */
        VBox mainPane = new VBox(title, teamsPane, tradePane, clickedPlayers);

        // Declaring the scene that will have the vbox above in it as the node 
        Scene scene = new Scene(mainPane, 500, 800);

        // This will add the title and scene to the stage, it will also show the stage
        primaryStage.setTitle("Hockey Trading Simulator");
        primaryStage.setScene(scene);
        primaryStage.show();

    }

    /*
    * This method will display a window were the user will select a team 
    * then the team's name,logo and an add player button to the vbox that is
    * passed into it
     */
    public static void teamWindow(VBox teamPane, Button addPlayer) {

        // Initilizing the stage variable
        Stage stage = new Stage();

        // Declaring a label variable that will be at the top of the window
        Label lbl = new Label("Please Select a Team");
        lbl.setFont(Font.font(30));
        StackPane header = new StackPane(lbl);

        // Initilizing a flowpane variable that will have all the team logos in it
        FlowPane teamPage = new FlowPane();

        // Creating an arraylist that will hold all of the team logos
        ArrayList<ImageView> teams = new ArrayList<>();

        // This for loop will add al of the team logos to the teams array
        for (int i = 0; i < 31; i++) {
            teams.add(new ImageView("TeamLogos/team" + (i + 1) + ".png"));
        }

        // This will add all of the team logos to the flowpane
        teamPage.getChildren().addAll(teams);

        // This will add the title and logo to one vbox
        VBox selectTeamPane = new VBox(header, teamPage);

        // This will add the selectTeamPane to the scene
        Scene pane = new Scene(selectTeamPane, 950, 1000);
        stage.setScene(pane);
        stage.setTitle("Select a Team");
        stage.show();

        // This will make all the logos clickable 
        for (int i = 0; i < 31; i++) {
            teams.get(i).setOnMouseClicked((MouseEvent event) -> {
                ImageView clickedTeam = (ImageView) event.getSource();

                // declaring the variables that will be altered in the if statements
                int teamNumber = 0;
                String name = "";

                /* These if statements will add the team logo to the teamPane, 
                and assign the name and teamNumber variable a value based on the 
                team logo that was clicked */
                if (clickedTeam == teams.get(0)) {
                    teamNumber = 12;
                    teamPane.getChildren().add(teams.get(0));
                    name = "Carolina Hurricanes";
                } else if (clickedTeam == teams.get(1)) {
                    teamNumber = 29;
                    teamPane.getChildren().add(teams.get(1));
                    name = "Columbus Blue Jackets";
                } else if (clickedTeam == teams.get(2)) {
                    teamNumber = 1;
                    teamPane.getChildren().add(teams.get(2));
                    name = "New Jersey Devils";
                } else if (clickedTeam == teams.get(3)) {
                    teamNumber = 2;
                    teamPane.getChildren().add(teams.get(3));
                    name = "New York Islanders";
                } else if (clickedTeam == teams.get(4)) {
                    teamNumber = 3;
                    teamPane.getChildren().add(teams.get(4));
                    name = "New York Rangers";
                } else if (clickedTeam == teams.get(5)) {
                    teamNumber = 4;
                    teamPane.getChildren().add(teams.get(5));
                    name = "Phildelphia Flyers";
                } else if (clickedTeam == teams.get(6)) {
                    teamNumber = 5;
                    teamPane.getChildren().add(teams.get(6));
                    name = "Pittsburgh Penguins";
                } else if (clickedTeam == teams.get(7)) {
                    teamNumber = 15;
                    teamPane.getChildren().add(teams.get(7));
                    name = "Washignton Capitals";
                } else if (clickedTeam == teams.get(8)) {
                    teamNumber = 6;
                    teamPane.getChildren().add(teams.get(8));
                    name = "Boston Bruins";
                } else if (clickedTeam == teams.get(9)) {
                    teamNumber = 7;
                    teamPane.getChildren().add(teams.get(9));
                    name = "Buffalo Bills";
                } else if (clickedTeam == teams.get(10)) {
                    teamNumber = 17;
                    teamPane.getChildren().add(teams.get(10));
                    name = "Detroit Red Wings";
                } else if (clickedTeam == teams.get(11)) {
                    teamNumber = 13;
                    teamPane.getChildren().add(teams.get(11));
                    name = "Flordia Panthers";
                } else if (clickedTeam == teams.get(12)) {
                    teamNumber = 8;
                    teamPane.getChildren().add(teams.get(12));
                    name = "Montreal Canadians";
                } else if (clickedTeam == teams.get(13)) {
                    teamNumber = 9;
                    teamPane.getChildren().add(teams.get(13));
                    name = "Ottawa Senators";
                } else if (clickedTeam == teams.get(14)) {
                    teamNumber = 14;
                    teamPane.getChildren().add(teams.get(14));
                    name = "Tampa Bay Lighting";
                } else if (clickedTeam == teams.get(15)) {
                    teamNumber = 10;
                    teamPane.getChildren().add(teams.get(15));
                    name = "Toronto Maple Leafs";
                } else if (clickedTeam == teams.get(16)) {
                    teamNumber = 16;
                    teamPane.getChildren().add(teams.get(16));
                    name = "Chicago BlackHawks";
                } else if (clickedTeam == teams.get(17)) {
                    teamNumber = 21;
                    teamPane.getChildren().add(teams.get(17));
                    name = "Colerado Avalance";
                } else if (clickedTeam == teams.get(18)) {
                    teamNumber = 25;
                    teamPane.getChildren().add(teams.get(18));
                    name = "Dallas Stars";
                } else if (clickedTeam == teams.get(19)) {
                    teamNumber = 30;
                    teamPane.getChildren().add(teams.get(19));
                    name = "Minnasota Wild";
                } else if (clickedTeam == teams.get(20)) {
                    teamNumber = 18;
                    teamPane.getChildren().add(teams.get(20));
                    name = "Nashville Predators";
                } else if (clickedTeam == teams.get(21)) {
                    teamNumber = 19;
                    teamPane.getChildren().add(teams.get(21));
                    name = "St.Louis Blues";
                } else if (clickedTeam == teams.get(22)) {
                    teamNumber = 52;
                    teamPane.getChildren().add(teams.get(22));
                    name = "Winnipeg Jets";
                } else if (clickedTeam == teams.get(23)) {
                    teamNumber = 24;
                    teamPane.getChildren().add(teams.get(23));
                    name = "Aniehiem Ducks";
                } else if (clickedTeam == teams.get(24)) {
                    teamNumber = 53;
                    teamPane.getChildren().add(teams.get(24));
                    name = "Arizona Cyotes";
                } else if (clickedTeam == teams.get(25)) {
                    teamNumber = 20;
                    teamPane.getChildren().add(teams.get(25));
                    name = "Calgary Flames";
                } else if (clickedTeam == teams.get(26)) {
                    teamNumber = 22;
                    teamPane.getChildren().add(teams.get(26));
                    name = "Edmonton Oilers";
                } else if (clickedTeam == teams.get(27)) {
                    teamNumber = 26;
                    teamPane.getChildren().add(teams.get(27));
                    name = "LA Kings";
                } else if (clickedTeam == teams.get(28)) {
                    teamNumber = 28;
                    teamPane.getChildren().add(teams.get(28));
                    name = "San Jose Sharks";
                } else if (clickedTeam == teams.get(29)) {
                    teamNumber = 23;
                    teamPane.getChildren().add(teams.get(29));
                    name = "Vancouver Canucks";
                } else if (clickedTeam == teams.get(30)) {
                    teamNumber = 54;
                    teamPane.getChildren().add(teams.get(30));
                    name = "Las Vegas Golden Knights";

                }

                teamPane.setAlignment(Pos.CENTER);

                // This will remove the select team button from the teamPane
                teamPane.getChildren().remove(0);
                // This will close the window
                stage.close();

                try {
                    /* if there is no in the team1 variable then everything in 
                    this if block will be executed */
                    if (team1 == null) {
                        /* This is adding all of the information of the first 
                        team the user selected in the team1 variable */
                        team1 = new Team(name, teamNumber, loadRoster(teamNumber));
                        /* This is creating and adding a label of the team name 
                        stored in the team1 variable to the teamPane */
                        Label lbl2 = new Label(team1.getName());
                        // This will set the font size to 20
                        lbl2.setFont(Font.font(20));
                        teamPane.getChildren().add(lbl2);
                        /* if there is no data in the team2 variable then everything in 
                    this if block will be executed */
                    } else if (team2 == null) {
                        /* This is adding all of the information of the second 
                        team the user selected in the team2 variable */
                        team2 = new Team(name, teamNumber, loadRoster(teamNumber));
                        /* This is creating and adding a label of the team name 
                        stored in the team2 variable to the teamPane */
                        Label lbl2 = new Label(team2.getName());
                        // This will set the font size to 20
                        lbl2.setFont(Font.font(20));
                        teamPane.getChildren().add(lbl2);
                    }
                    /* If a parse exception is caught then an alert window with an error message will pop up */
                } catch (ParseException | MalformedURLException ex) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);

                    alert.setTitle("Error");
                    alert.setContentText(ex.toString());
                    alert.setHeaderText("An error has occuried");
                }
            });
        }
        // This is adding the a add player button to the teamPane
        teamPane.getChildren().add(addPlayer);
    }

    /*
    * This method will return a player arraylist with all of the players of the
    * team that the user chose
     */
    public static ArrayList<Player> loadRoster(int teamNumber)
            throws ParseException, MalformedURLException {

        // Declaring an arraylist that will store all of the player on a team 
        ArrayList<Player> playersArray = new ArrayList<>();

        try {

            // This will call to the rest api from the internet
            URL url = new URL("https://statsapi.web.nhl.com/api/v1/teams/" + teamNumber + "/roster");
            URLConnection connection = url.openConnection();

            // This variable will hold the status of the connection
            HttpURLConnection httpConnection = (HttpURLConnection) connection;
            int response = httpConnection.getResponseCode();

            // This will make sure that there was a succesful connection to the rest api page
            if (response == HttpURLConnection.HTTP_OK) {

                // This will store all of the input from the rest api
                InputStream is = httpConnection.getInputStream();

                StringBuilder builder = new StringBuilder();

                Scanner input = new Scanner(is);
                // This loop will keep going until the file has no new lines of text
                while (input.hasNextLine()) {
                    /* This code will store each line of the file into a string 
                    variable then will add it to the string builder */
                    String lineIn = input.nextLine();
                    builder.append(lineIn).append("\n");
                }

                // Declaring a json parser variables
                JSONParser parser = new JSONParser();

                JSONObject obj = (JSONObject) parser.parse(builder.toString());

                // This will store a json array of all the players on the team to this variable
                JSONArray roster = (JSONArray) obj.get("roster");

                for (int i = 0; i < roster.size(); i++) {

                    // This stores the player objct in this roster
                    JSONObject player = (JSONObject) roster.get(i);

                    JSONObject jobjPerson = (JSONObject) player.get("person");

                    // This will get the name of the player and store it in a variable
                    String playerName = (String) jobjPerson.get("fullName");

                    // This will store the ID of a player in a variable
                    long id = (long) jobjPerson.get("id");

                    JSONObject jobjPosition = (JSONObject) player.get("position");

                    // This will store the player's position in a string variable
                    String playerPosition = (String) jobjPosition.get("name");

                    // This will store the player's position type in a string variable
                    String playerPositionType = (String) jobjPosition.get("type");

                    // This will add all the information about a specfic player into the players array
                    playersArray.add(i, new Player(playerName, playerPosition, 
                            rating(id, playerPositionType), id, playerPositionType));
                }
            }
            // If a exception is caught an error window will pop up
        } catch (IOException | org.json.simple.parser.ParseException ex) {
            Alert alert = new Alert(Alert.AlertType.ERROR);

            alert.setTitle("Error");
            alert.setContentText(ex.toString());
            alert.setHeaderText("An error has occuried");
        }
        // This will return the players array
        return playersArray;
    }

    /*
    * This will get the stats of a player and depending on their stats and
    * position it will return a rating out of 5  
     */
    public static double rating(long id, String position) throws org.json.simple.parser.ParseException {

        double rating = 0;

        try {

            // This will call to the rest api from the internet
            URL url = new URL("https://statsapi.web.nhl.com/api/v1/people/" + id + "/stats"
                    + "?stats=statsSingleSeason&season=20182019");
            URLConnection connection = url.openConnection();

            // This variable will hold the status of the connection
            HttpURLConnection httpConnection = (HttpURLConnection) connection;
            int response = httpConnection.getResponseCode();

            // This will make sure that there was a succesful connection to the rest api page
            if (response == HttpURLConnection.HTTP_OK) {

                // This will store all of the input from the rest api
                InputStream is = httpConnection.getInputStream();

                StringBuilder builder = new StringBuilder();

                Scanner input = new Scanner(is);
                // This loop will keep going until the file has no new lines of text
                while (input.hasNextLine()) {
                    /* This code will store each line of the file into a string 
                    variable then will add it to the string builder */
                    String lineIn = input.nextLine();
                    builder.append(lineIn).append("\n");
                }

                JSONParser parser = new JSONParser();

                JSONObject obj = (JSONObject) parser.parse(builder.toString());
                // If the player is a forward then everything in this if block will be executed
                if (position.equals("Forward")) {

                    // This is getting the json object stats
                    JSONArray stats = (JSONArray) obj.get("stats");

                    // This is getting the first element of the stats object
                    JSONObject obj2 = (JSONObject) stats.get(0);

                    // This is getting a json array called splits
                    JSONArray splits = (JSONArray) obj2.get("splits");

                    // If the splits array is empty then it will return a rating of 0.5
                    if (splits.size() == 0) {
                        rating = 0.5;
                        return rating;
                    }

                    // This is getting the first element of the splits array
                    JSONObject obj3 = (JSONObject) splits.get(0);

                    // This will get the stat object
                    JSONObject objStat = (JSONObject) obj3.get("stat");

                    // These will get the amnout of goals, assits and points a player has
                    long goals = (long) objStat.get("goals");
                    long assists = (long) objStat.get("assists");
                    long points = (long) objStat.get("points");

                    /* Depending on the assists and goals or points a player has 
                    they will get a rating depending on those factors */
                    if (goals >= 40 && assists >= 74 || points >= 114) {

                        rating = 5;
                    } else if (goals >= 35 && goals < 40 && assists > 65 && assists < 74
                            || points >= 90 && points < 114) {

                        rating = 4.5;
                    } else if (goals >= 30 && goals < 35 && assists > 60 && assists < 65
                            || points >= 80 && points < 90) {

                        rating = 4;
                    } else if (goals >= 25 && goals < 30 && assists > 50 && assists < 60
                            || points >= 70 && points < 80) {

                        rating = 3.5;
                    } else if (goals >= 20 && goals < 25 && assists > 40 && assists < 50
                            || points >= 60 && points < 70) {

                        rating = 3;
                    } else if (goals >= 15 && goals < 20 && assists > 30 && assists < 40
                            || points >= 40 && points < 60) {

                        rating = 2.5;
                    } else if (goals >= 10 && goals < 15 && assists > 20 && assists < 30
                            || points >= 30 && points < 40) {

                        rating = 2;
                    } else if (goals >= 5 && goals < 10 && assists > 10 && assists < 20
                            || points >= 20 && points < 30) {

                        rating = 1.5;
                    } else if (goals < 5 && goals > 1 && assists > 1 && assists < 10
                            || points >= 10 && points < 20) {

                        rating = 1;
                    } else {

                        rating = 0.5;
                    }

                    // If the player is a defenseman everything in this if block will be executed
                } else if (position.equals("Defenseman")) {

                    // This is getting the json object stats
                    JSONArray stats = (JSONArray) obj.get("stats");

                    // This is getting the first element of the stats object
                    JSONObject obj2 = (JSONObject) stats.get(0);

                    // This is getting a json array called splits
                    JSONArray splits = (JSONArray) obj2.get("splits");

                    // If the splits array is empty then it will return a rating of 0.5
                    if (splits.size() == 0) {
                        rating = 0.5;
                        return rating;
                    }

                    // This is getting the first element of the splits array
                    JSONObject obj3 = (JSONObject) splits.get(0);

                    // This will get the stat object
                    JSONObject objStat = (JSONObject) obj3.get("stat");

                    // These will get the amnout of points and the plus/minus a player has
                    long points = (long) objStat.get("points");
                    long plusMinus = (long) objStat.get("plusMinus");

                    /* Depending on the points and plus/minus a player has 
                    they will get a rating depending on those factors */
                    if (points >= 75 || plusMinus >= 39) {
                        rating = 5.0;
                    } else if (points >= 60 && points < 75 || plusMinus >= 34 && plusMinus < 39) {
                        rating = 4.5;
                    } else if (points >= 55 && points < 60 || plusMinus >= 29 && plusMinus < 34) {
                        rating = 4.0;
                    } else if (points >= 50 && points < 55 || plusMinus >= 24 && plusMinus < 29) {
                        rating = 3.5;
                    } else if (points >= 45 && points < 50 || plusMinus >= 19 && plusMinus < 24) {
                        rating = 3.0;
                    } else if (points >= 35 && points < 40 || plusMinus >= 14 && plusMinus < 19) {
                        rating = 2.5;
                    } else if (points >= 30 && points < 35 || plusMinus >= 9 && plusMinus < 14) {
                        rating = 2.0;
                    } else if (points >= 25 && points < 30 || plusMinus >= 4 && plusMinus < 9) {
                        rating = 1.5;
                    } else if (points >= 20 && points < 25 || plusMinus == 0) {
                        rating = 1.0;
                    } else {
                        rating = 0.5;
                    }

                    // If the player is a goalie everything in this if block will be executed  
                } else if (position.equals("Goalie")) {

                    // This is getting the json array stats
                    JSONArray stats = (JSONArray) obj.get("stats");

                    // This is getting the first element of the stats object
                    JSONObject obj2 = (JSONObject) stats.get(0);

                    // This is getting a json array called splits
                    JSONArray splits = (JSONArray) obj2.get("splits");

                    // If the splits array is empty then it will return a rating of 0.5
                    if (splits.size() == 0) {
                        rating = 0.5;
                        return rating;
                    }

                    // This is getting the first element of the splits array
                    JSONObject obj3 = (JSONObject) splits.get(0);

                    // This will get the stat object
                    JSONObject objStat = (JSONObject) obj3.get("stat");

                    // This will get the save percentage of the goalie
                    double savePct = (double) objStat.get("savePercentage");

                    /* Depending on the save percentage a player has 
                    they will get a rating out of 5 */
                    if (savePct >= 0.920) {
                        rating = 5.0;
                    } else if (savePct >= 0.915 && savePct < 0.920) {
                        rating = 4.5;
                    } else if (savePct >= 0.910 && savePct < 0.915) {
                        rating = 4.0;
                    } else if (savePct >= 0.905 && savePct < 0.910) {
                        rating = 3.5;
                    } else if (savePct >= 0.900 && savePct < 0.905) {
                        rating = 3.0;
                    } else if (savePct >= 0.890 && savePct < 0.900) {
                        rating = 2.5;
                    } else if (savePct >= 0.880 && savePct < 0.890) {
                        rating = 2.0;
                    } else if (savePct >= 0.870 && savePct < 0.880) {
                        rating = 1.5;
                    } else if (savePct >= 0.860 && savePct < 0.870) {
                        rating = 1.0;
                    } else if (savePct < 0.860) {
                        rating = 0.5;
                    }
                }

                // If you are unable to connect to the rest api an error window will pop up
            } else {

                Alert alert = new Alert(Alert.AlertType.ERROR);

                alert.setTitle("Error");
                alert.setContentText("Response" + response);
                alert.setHeaderText("An error has occuried");

                Optional<ButtonType> result = alert.showAndWait();
            }
            /* If a malformed URL exception is caught an error window with an 
            error message will pop up */
        } catch (MalformedURLException ex) {
            Alert alert = new Alert(Alert.AlertType.ERROR);

            alert.setTitle("Error");
            alert.setContentText(ex.toString());
            alert.setHeaderText("An error has occuried");

            Optional<ButtonType> result = alert.showAndWait();
            /* If a IO exception is caught an error window with an 
            error message will pop up */
        } catch (IOException ex) {
            Alert alert = new Alert(Alert.AlertType.ERROR);

            alert.setTitle("Error");
            alert.setContentText(ex.toString());
            alert.setHeaderText("An error has occuried");

            Optional<ButtonType> result = alert.showAndWait();
        }
        // The rating that was determinded will be returned
        return rating;
    }

    /*
    * This method will display all the players on the team as labels, the user
    * will select one of the players and then that player will be displayed
    * in the clicked players pane
     */
    public static void clickedPlayer(Team team, VBox teamPane) {

        ObservableList<String> positions = FXCollections.observableArrayList(
                "All", "Forward", "Defenseman", "Goalie");

        ComboBox playerPositions = new ComboBox(positions);

        Label selectPosition = new Label("Select position");

        HBox pickPosition = new HBox(selectPosition, playerPositions);

        // Declaring a stage variable
        Stage stage = new Stage();
        // Declaring an array that will hold all the labels with the player's information
        ArrayList<Label> lblArray = new ArrayList<>();

        // Declaring a label variable that will be the header of the page
        Label lblHeader = new Label("Please Select a Player");
        // This will set the font size to 20
        lblHeader.setFont(Font.font(20));

        FlowPane playerPane = new FlowPane();
        playerPane.setPadding(new Insets(5));
        
        /* This loop will go through every player on the selected team and add 
        a label with the player's information on it */
        for (int i = 0; i < team.getPlayers().size(); i++) {

            Label lbl = new Label(team.getPlayers().get(i).toString());
            // Setting the padding between the labels
            lbl.setPadding(new Insets(3));

            lblArray.add(i, lbl);
        }

        /* This will check to see if a player was already selected, if he was 
        then he will be removed form the label array */
        if (teamPane.getChildren().size() > 0) {
            label:
            for (int i = 0; i < teamPane.getChildren().size(); i++) {
                for (int j = 0; j < lblArray.size(); j++) {
                    Label lbl = (Label) teamPane.getChildren().get(i);
                    if (lbl.getText().equals(lblArray.get(j).getText())) {

                        lblArray.remove(j);
                        break label;
                    }
                }
            }
        }

        // This will add all the player labels to the player pane
        playerPane.getChildren().addAll(lblArray);

        VBox vbox = new VBox(pickPosition, lblHeader, playerPane);

        // Declaring the scene variable that will show all of the players
        Scene scene = new Scene(vbox);
        stage.setScene(scene);
        stage.setTitle("Please select a player");
        stage.show();

        playerPositions.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
               
                    //ComboBox cb = (ComboBox)event.getSource();
                    String position = (String)playerPositions.getSelectionModel().getSelectedItem();
                
                    playerPane.getChildren().removeAll(lblArray);
                    
                    lblArray.clear();

                    for (int i = 0; i < team.getPlayers().size(); i++) {

                        Label lbl = new Label(team.getPlayers().get(i).toString());
                        // Setting the padding between the labels
                        lbl.setPadding(new Insets(3));

                       String playerPosition = team.getPlayers().get(i).getPositionType();
                        
                        if(playerPosition.equals(position)) {
                        lblArray.add(lbl);
                        } else if(position.equals("All")) {
                        lblArray.add(lbl);   
                        }
                    }

                    /* This will check to see if a player was already selected, if he was 
        then he will be removed form the label array */
                    if (teamPane.getChildren().size() > 0) {
                        label:
                        for (int i = 0; i < teamPane.getChildren().size(); i++) {
                            for (int j = 0; j < lblArray.size(); j++) {
                                Label lbl = (Label) teamPane.getChildren().get(i);
                                if (lbl.getText().equals(lblArray.get(j).getText())) {

                                    lblArray.remove(j);
                                    break label;
                                }
                            }
                        }
                    } 
                    
                    // This will add all the player labels to the player pane
                    playerPane.getChildren().addAll(lblArray);
                    
                     // This will make all of the player labels clickable
        for (int i = 0; i < lblArray.size(); i++) {

            lblArray.get(i).setOnMouseClicked((MouseEvent event2) -> {

                // This will add the clicked label to the team pane
                teamPane.getChildren().add((Label) event2.getSource());
                // This will close the window
                stage.close();

            });
        }     
            } 
        });
        
         

        // This will make all of the player labels clickable
        for (int i = 0; i < lblArray.size(); i++) {

            lblArray.get(i).setOnMouseClicked((MouseEvent event) -> {

                // This will add the clicked label to the team pane
                teamPane.getChildren().add((Label) event.getSource());
                // This will close the window
                stage.close();

            });
        }

    }

    /*
    * This method will compare the players that the user selected to trade, if 
    * if the total ratings of the players on each team match then the players
    * the user selected will be added to the team they were traded to, if
    * the ratings don't match then an error window will pop up and the selected
    * players are cleared from the pane
     */
    public static void trade(Team team1, Team team2, VBox clickedPlayers1, VBox clickedPlayers2) {

        // Declaring the total team rating variables
        double team1Rating = 0;
        double team2Rating = 0;

        // Declaring the array lists the will store that players involed in the trade
        ArrayList<Player> team1Players = new ArrayList<>();
        ArrayList<Player> team2Players = new ArrayList<>();

        /* This for loop will find the players that were added to the clicked 
        players pane, then it will add it's rating to the total rating vriable, 
        then it will add the selected player to the team1Players array */
        for (int i = 0; i < clickedPlayers1.getChildren().size(); i++) {
            Label lbl = (Label) clickedPlayers1.getChildren().get(i);
            for (int j = team1.getPlayers().size() - 1; i <= j; j--) {
                if (team1.getPlayers().get(j).toString().equals(lbl.getText())) {

                    team1Players.add(team1.getPlayers().get(j));

                    team1Rating += team1.getPlayers().get(j).getRating();

                    team1.getPlayers().remove(j);

                }
            }
        }

        /* This for loop will find the players that were added to the clicked 
        players pane, then it will add it's rating to the total rating vriable, 
        then it will add the selected player to the team2Players array */
        for (int i = 0; i < clickedPlayers2.getChildren().size(); i++) {
            Label lbl = (Label) clickedPlayers2.getChildren().get(i);
            for (int j = team2.getPlayers().size() - 1; i <= j; j--) {
                if (team2.getPlayers().get(j).toString().equals(lbl.getText())) {

                    team2Players.add(team2.getPlayers().get(j));

                    team2Rating += team2.getPlayers().get(j).getRating();

                    team2.getPlayers().remove(j);

                }
            }
        }

        // If the total team ratings don't match then an error window will pop up
        if (team1Rating > team2Rating || team1Rating < team2Rating) {
            Alert alert = new Alert(Alert.AlertType.ERROR);

            alert.setTitle("Error");
            alert.setContentText("The ratings of the players don't match");
            alert.setHeaderText("Trade Rejected");

            Optional<ButtonType> result = alert.showAndWait();

            clickedPlayers1.getChildren().clear();
            clickedPlayers2.getChildren().clear();

            /* if the total team ratings do match then an information window 
            will pop up and the playters involed in the trade will switch teams */
        } else if (team1Rating == team2Rating) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);

            alert.setTitle("Trade Accepted");
            alert.setContentText("The ratings match, the trade is accepted");
            alert.setHeaderText("Trade Accepted");

            Optional<ButtonType> result = alert.showAndWait();

            team1.getPlayers().addAll(team2Players);
            team2.getPlayers().addAll(team1Players);
        }

    }

    public static void main(String[] args) {
        // This calls the launch method
        launch(args);
    }

}
