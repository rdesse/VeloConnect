import javafx.application.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.text.*;
import javafx.scene.layout.*;
import javafx.stage.*;
import javafx.geometry.*;
import javafx.scene.image.*;
import javafx.scene.text.*;
import javax.imageio.*;
import java.io.*;
import java.awt.image.*;
import javafx.embed.swing.*;
import javafx.scene.web.*;
import javafx.event.*;
import javafx.scene.input.*;
import java.nio.charset.*;
import javafx.concurrent.*;
import javafx.scene.shape.*;
import javafx.scene.paint.*;
import java.sql.*;
import java.util.*;
import java.time.*;
import java.time.format.*;
import modele.*;
import javafx.scene.chart.*;

public class MainApp extends Application {

	private Stage primaryStage;
	private HBox header;
	private boolean connected = false;
	private Font fontGros = Font.font("Arial", 18);
	private Font fontMoyen = Font.font("Arial", 15);
	private Font fontPetit = Font.font("Arial", 12);
	private Font fontTitre = Font.font("Arial", FontWeight.BOLD, 42);
	private GridPane bdd;
	private SqlRequete requete;
	private MySQLAccess requeteBdd;
	private int pointChoisi;
	private ArrayList<Integer> listePoint;
	
    public static void main(String[] args) {
        launch(args);
    }
    
    @Override
    public void start(Stage primaryStage) {
		this.requete = new SqlRequete();
		this.requeteBdd = new MySQLAccess();
		this.bdd = setAfficherBdd();
		ArrayList<String> ret = new ArrayList<String>();
		ret = this.requete.selectAll();
		updateBdd(ret);
        this.primaryStage = primaryStage;
        showMainPage();
    }
    
    public void changeScene(Scene scene) {
		primaryStage.setMinWidth(1024);
        primaryStage.setMinHeight(768);
        primaryStage.setMaxWidth(1024);
        primaryStage.setMaxHeight(768);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
    public void setHeader(){
		Button accueil = new Button("Accueil");
        accueil.setPrefWidth(200);
        accueil.setPrefHeight(75);
        accueil.setAlignment(Pos.CENTER);
        accueil.setFont(fontGros);
        
        Button carteVille = new Button("Carte de la ville");
        carteVille.setPrefWidth(200);
        carteVille.setPrefHeight(75);
        carteVille.setAlignment(Pos.CENTER);
        carteVille.setFont(fontGros);

        Button bdd = new Button("Base de données");
        bdd.setPrefWidth(200);
        bdd.setPrefHeight(75);
        bdd.setFont(fontGros);

        Button connexion = new Button("Connexion");
        if(connected==true){
			connexion.setText("Déconnexion");
		}
        connexion.setPrefWidth(200);
        connexion.setPrefHeight(75);
        connexion.setAlignment(Pos.CENTER);
        connexion.setFont(fontGros);
        
        connexion.setOnAction(event -> {
			if(connexion.getText().equals("Connexion")){
				showConnexionPage();
				connexion.setText("Déconnexion");
			}else{
				connexion.setText("Connexion");
				connected=false;
			}
            
        });
        
        //Lien des pages
        accueil.setOnAction(event -> showMainPage());
        carteVille.setOnAction(event -> showCartePage());
        bdd.setOnAction(event -> showBddPage());
        
        header = new HBox(50);
        header.setPadding(new Insets(15));
        header.setAlignment(Pos.TOP_CENTER);
        header.getChildren().addAll(accueil, carteVille, bdd, connexion);
        HBox.setHgrow(header, javafx.scene.layout.Priority.ALWAYS);
        
        //Couleur
		accueil.setStyle("-fx-text-fill: #f9f7f0; -fx-background-color: #072a40;");
        carteVille.setStyle("-fx-text-fill: #f9f7f0; -fx-background-color: #072a40;");
        bdd.setStyle("-fx-text-fill: #f9f7f0; -fx-background-color: #072a40; -fx-focus-color: transparent;");
        connexion.setStyle("-fx-text-fill: #f9f7f0; -fx-background-color: #072a40;");
        header.setStyle("-fx-background-color: #18b7be;");
        
        header.setMinWidth(1024);
        header.setMaxWidth(1024);
	}
    
    public GridPane setAfficherBdd(){
		GridPane afficherBdd = new GridPane();
        afficherBdd.setPadding(new Insets(15));
        afficherBdd.setHgap(5);
        afficherBdd.setVgap(5);
        for (int row = 0; row < 6; row++) {
            for (int col = 0; col < 4; col++) {
                Rectangle rectangle = new Rectangle(175, 50, Color.web("#18b7be"));
                Label label = new Label("null");
                label.setAlignment(Pos.CENTER);
                GridPane.setHalignment(label, javafx.geometry.HPos.CENTER);
                GridPane.setValignment(label, javafx.geometry.VPos.CENTER);
                label.setFont(fontMoyen);
                label.setTextFill(Color.web("#072a40")); 
                afficherBdd.add(rectangle, col, row);
                afficherBdd.add(label, col, row);
            }
        }
        Label labelToUpdate = (Label) afficherBdd.getChildren().get(1);
        labelToUpdate.setText("Id");
        labelToUpdate.setFont(Font.font("Arial", FontWeight.BOLD, 18));
        Label labelToUpdate2 = (Label) afficherBdd.getChildren().get(3);
        labelToUpdate2.setText("Libelle");
        labelToUpdate2.setFont(Font.font("Arial", FontWeight.BOLD, 18));
        Label labelToUpdate3 = (Label) afficherBdd.getChildren().get(5);
        labelToUpdate3.setText("Quartier");
        labelToUpdate3.setFont(Font.font("Arial", FontWeight.BOLD, 18));
        Label labelToUpdate4 = (Label) afficherBdd.getChildren().get(7);
        labelToUpdate4.setText("Total");
        labelToUpdate4.setFont(Font.font("Arial", FontWeight.BOLD, 18));
        
        Rectangle rectangleToColor = (Rectangle) afficherBdd.getChildren().get(0);
        rectangleToColor.setFill(Color.web("#178ca4"));
        Rectangle rectangleToColor2 = (Rectangle) afficherBdd.getChildren().get(2);
        rectangleToColor2.setFill(Color.web("#178ca4"));
        Rectangle rectangleToColor3 = (Rectangle) afficherBdd.getChildren().get(4);
        rectangleToColor3.setFill(Color.web("#178ca4"));
        Rectangle rectangleToColor4 = (Rectangle) afficherBdd.getChildren().get(6);
        rectangleToColor4.setFill(Color.web("#178ca4"));
        
        
        return afficherBdd;
	}

    public void updateBdd(ArrayList<String> reponse){
		for(int k=9; k<48; k+=2){
			Label labelToUpdate = (Label) this.bdd.getChildren().get(k);
			labelToUpdate.setText("null");
		}
		
		
		int i = 9;
		Iterator<String> iterator = reponse.iterator();
		
		while(i<47 && iterator.hasNext()){
			int j=0;
			String line = iterator.next();
			String[] tmp = line.split(",");
			while(j<4){
				Label labelToUpdate = (Label) this.bdd.getChildren().get(i);
				String texte = tmp[j];
				if(texte.length()>20){
					texte = texte.substring(0, Math.min(texte.length(), 18))+"...";
				}
				labelToUpdate.setText(texte);
				i+=2;
				j++;
			}
		}
	}
    
    //Accueil
    public void showMainPage() {
        primaryStage.setTitle("VéloConnect - Nantes");
        
        setHeader();
        Pane layout = new Pane();
        
        //Ajout des boutons de coté
        Button cartePiste = new Button("Carte des pistes");
        cartePiste.setPrefWidth(200);
        cartePiste.setPrefHeight(50);
        cartePiste.setAlignment(Pos.CENTER);
        cartePiste.setFont(fontMoyen);
        
        Button donnees = new Button("Données et projection");
        donnees.setPrefWidth(200);
        donnees.setPrefHeight(50);
        donnees.setAlignment(Pos.CENTER);
        donnees.setFont(fontMoyen);
        
        Button graphes = new Button("Graphes");
        graphes.setPrefWidth(200);
        graphes.setPrefHeight(50);
        graphes.setAlignment(Pos.CENTER);
        graphes.setFont(fontMoyen);
        
        //Couleur
		cartePiste.setStyle("-fx-text-fill: #072a40; -fx-background-color: #178ca4; -fx-background-radius: 30px;");
        donnees.setStyle("-fx-text-fill: #072a40; -fx-background-color: #178ca4; -fx-background-radius: 30px;");
        graphes.setStyle("-fx-text-fill: #072a40; -fx-background-color: #178ca4; -fx-background-radius: 30px;");
        
        //Lien des pages
        cartePiste.setOnAction(event -> showCartePistesPage());
        donnees.setOnAction(event -> showDonneesPage());
        graphes.setOnAction(event -> showGraphesPage());
        
        VBox menu = new VBox(10);
        menu.setPadding(new Insets(15));
        menu.setAlignment(Pos.TOP_CENTER);
        menu.getChildren().addAll(cartePiste, donnees, graphes);
        VBox.setVgrow(menu, javafx.scene.layout.Priority.ALWAYS);
        
        this.header.setLayoutX(0);
		this.header.setLayoutY(0);
		menu.setLayoutY(105);
        
        //Background
        try {
			BufferedImage bufferedImage = ImageIO.read(new File("../data/background.jpg"));
			Image image = SwingFXUtils.toFXImage(bufferedImage, null);
			ImageView imageView = new ImageView(image);
			imageView.setFitWidth(layout.getWidth());
			imageView.setFitHeight(layout.getHeight());
			imageView.setY(24);
			layout.getChildren().addAll(imageView, this.header, menu);
        } catch (IOException e) {
			e.printStackTrace();
		}
        
        //Logo
        try {
			BufferedImage bufferedImage = ImageIO.read(new File("../data/LogoTitreBas.png"));
			Image image = SwingFXUtils.toFXImage(bufferedImage, null);
			ImageView imageView = new ImageView(image);
			imageView.setFitWidth(layout.getWidth());
			imageView.setFitHeight(layout.getHeight());
			imageView.setFitWidth(500);
			imageView.setPreserveRatio(true);
			imageView.setX(262);
			imageView.setY(140);
			layout.getChildren().addAll(imageView);
        } catch (IOException e) {
			e.printStackTrace();
		}
        
        Scene scene = new Scene(layout, 1024, 768);
        changeScene(scene);
    }
    
    //Base de donnée
    public void showBddPage() {
		setHeader();
        Pane layout = new Pane();
        
        this.bdd.setLayoutX(140);
        this.bdd.setLayoutY(240);
        
        Button ajoutFiltre = new Button("Ajouter les filtres");
        ajoutFiltre.setPrefWidth(200);
        ajoutFiltre.setPrefHeight(50);
        ajoutFiltre.setAlignment(Pos.CENTER);
        ajoutFiltre.setLayoutX(15);
        ajoutFiltre.setLayoutY(120);
        ajoutFiltre.setStyle("-fx-text-fill: #072a40; -fx-background-color: #178ca4; -fx-background-radius: 30px;");
        ajoutFiltre.setOnAction(event -> showBddFiltrePage());
        ajoutFiltre.setFont(fontMoyen);
        
        //Background
        try {
			BufferedImage bufferedImage = ImageIO.read(new File("../data/background.jpg"));
			Image image = SwingFXUtils.toFXImage(bufferedImage, null);
			ImageView imageView = new ImageView(image);
			imageView.setFitWidth(layout.getWidth());
			imageView.setFitHeight(layout.getHeight());
			imageView.setY(24);
			layout.getChildren().addAll(imageView, this.header, this.bdd, ajoutFiltre);
        } catch (IOException e) {
			e.printStackTrace();
		}
		
		//Modifier les données
        if(connected == true){
			Button modifierDonnee = new Button("Modifier les données");
			modifierDonnee.setPrefWidth(200);
			modifierDonnee.setPrefHeight(50);
			modifierDonnee.setAlignment(Pos.CENTER);
			modifierDonnee.setLayoutX(809);
			modifierDonnee.setLayoutY(120);
			modifierDonnee.setStyle("-fx-text-fill: #072a40; -fx-background-color: #178ca4; -fx-background-radius: 30px;");
			modifierDonnee.setOnAction(event -> showBddModifierPage());
			modifierDonnee.setFont(fontMoyen);
			layout.getChildren().addAll(modifierDonnee);
		}
		
        Scene scene = new Scene(layout, 1024, 768);
		changeScene(scene);
	}
	
	 public void showBddFiltrePage() {
		setHeader();
        Pane layout = new Pane();
        
        this.bdd.setLayoutX(254);
        this.bdd.setLayoutY(240);
        
        TextField compteur = new TextField();
		compteur.setPromptText("Compteur");
		compteur.setPrefWidth(200); 
		compteur.setStyle("-fx-background-color: #f9f7f0;");
        
        ComboBox<String> quartier = new ComboBox<>();
        quartier.getItems().addAll(
                "null",
                "Centre Ville",
                "Bellevue - Chantenay - Sainte Anne",
                "Dervallières - Zola",
                "Hauts Pavés - Saint Félix",
                "Malakoff - Saint-Donatien",
                "Ile de Nantes",
                "Breil - Barberie",
                "Nantes Nord",
                "Nantes Erdre",
                "Doulon - Bottière",
                "Nantes Sud",
                "Trentemoult",
                "Hôtel de Ville",
                "Château de Rezé",
                "Pont Rousseau",
                "La Houssais",
                "Blordière",
                "Ragon"
        );
		quartier.setPromptText("Quartier");
		quartier.setPrefWidth(200); 
		quartier.setStyle("-fx-background-color: #f9f7f0; -fx-control-inner-background: #f9f7f0;");
        
        ComboBox<String> periode = new ComboBox<>();
        periode.getItems().addAll(
                "Lundi",
                "Mardi",
                "Mercredi",
                "Jeudi",
                "Vendredi",
                "Samedi",
                "Dimanche"
        );
		periode.setPromptText("Periode");
		periode.setPrefWidth(200); 
		periode.setStyle("-fx-background-color: #f9f7f0; -fx-control-inner-background: #f9f7f0;");
        
        ComboBox<String> vacances = new ComboBox<>();
        vacances.getItems().addAll(
                "Ete",
                "Toussaint",
                "Noel",
                "Hiver",
                "Printemps",
                "Ascension"
        );
		vacances.setPromptText("Vacances");
		vacances.setPrefWidth(200); 
		vacances.setStyle("-fx-background-color: #f9f7f0; -fx-control-inner-background: #f9f7f0;");
        
        DatePicker date = new DatePicker();
		date.setPromptText("Date");
		date.setPrefWidth(200); 
		date.setStyle("-fx-control-inner-background: #f9f7f0;");
		
		TextField heure = new TextField();
		heure.setPromptText("Heure");
		heure.setPrefWidth(200); 
		heure.setStyle("-fx-background-color: #f9f7f0;");
		
        Button valider = new Button("Valider");
		valider.setPrefWidth(150);
        valider.setPrefHeight(50);
		valider.setStyle("-fx-text-fill: #f9f7f0; -fx-background-color: #072a40; -fx-background-radius: 30px;");
        valider.setFont(fontMoyen);
        //Liaison avec la base de donnée
        valider.setOnAction(event -> {
			String compteurValue;
			try{compteurValue = compteur.getText();
				try {
					Double.parseDouble(compteurValue);
				} catch (NumberFormatException e) {
					compteurValue = null;
				}
			}catch(Exception e){
				compteurValue = null;
			}
			String quartierValue;
			try{quartierValue = quartier.getValue();}
			catch(Exception e){
				quartierValue = null;
			}
			String periodeValue;
			try{periodeValue = periode.getValue();}
			catch(Exception e){
				periodeValue = null;
			}
			String vacancesValue;
			try{vacancesValue = vacances.getValue();}
			catch(Exception e){
				vacancesValue = null;
			}
			String dateValue;
			try{dateValue = date.getValue().toString();}
			catch(Exception e){
				dateValue = null;
			}
			String heureValue;
			try{heureValue = heure.getText();
				try {
					Double.parseDouble(heureValue);
				} catch (NumberFormatException e) {
					heureValue = null;
				}
				}
			catch(Exception e){
				heureValue = null;
			}
			ArrayList<String> reponse;
			
			if (compteurValue != null) {
				if (periodeValue != null) {
					if (vacancesValue != null) {
						if (heureValue != null && Integer.parseInt(heureValue.trim())<24 && Integer.parseInt(heureValue.trim())>=0) {
							reponse = this.requete.selectCompteurPeriodeVacancesHeure(Integer.parseInt(heureValue.trim()), periodeValue, vacancesValue, Integer.parseInt(compteurValue.trim()));
						} else {
							reponse = this.requete.selectCompteurPeriodeVacances(periodeValue, vacancesValue, Integer.parseInt(compteurValue.trim()));
						}
					} else if (heureValue != null && Integer.parseInt(heureValue.trim())<24 && Integer.parseInt(heureValue.trim())>=0) {
						reponse = this.requete.selectCompteurPeriodeHeure(Integer.parseInt(heureValue.trim()), periodeValue, Integer.parseInt(compteurValue.trim()));
					} else {
						reponse = this.requete.selectCompteurPeriode(periodeValue, Integer.parseInt(compteurValue.trim()));
					}
				} else if (vacancesValue != null) {
					if (heureValue != null && Integer.parseInt(heureValue.trim())<24 && Integer.parseInt(heureValue.trim())>=0) {
						reponse = this.requete.selectCompteurVacancesHeure(Integer.parseInt(heureValue.trim()), vacancesValue, Integer.parseInt(compteurValue.trim()));
					} else {
						reponse = this.requete.selectCompteurVacances(vacancesValue, Integer.parseInt(compteurValue.trim()));
					}
				} else if (dateValue != null) {
					if (heureValue != null && Integer.parseInt(heureValue.trim())<24 && Integer.parseInt(heureValue.trim())>=0) {
						reponse = this.requete.selectCompteurDateHeure(Integer.parseInt(heureValue.trim()), Integer.parseInt(compteurValue.trim()), dateValue);
					} else {
						reponse = this.requete.selectCompteurDate(Integer.parseInt(compteurValue.trim()), dateValue);
					}
				} else if (heureValue != null && Integer.parseInt(heureValue.trim())<24 && Integer.parseInt(heureValue.trim())>=0) {
					reponse = this.requete.selectCompteurHeure(Integer.parseInt(heureValue.trim()), Integer.parseInt(compteurValue.trim()));
				} else {
					reponse = this.requete.selectCompteur(Integer.parseInt(compteurValue.trim()));
				}
			} else if (quartierValue != null){
				if (periodeValue != null) {
					if (vacancesValue != null) {
						if (heureValue != null && Integer.parseInt(heureValue.trim())<24 && Integer.parseInt(heureValue.trim())>=0) {
							reponse = this.requete.selectQuartierPeriodeVacancesHeure(Integer.parseInt(heureValue.trim()), periodeValue, vacancesValue, quartierValue);
						} else {
							reponse = this.requete.selectQuartierPeriodeVacances(periodeValue, vacancesValue, quartierValue);
						}
					} else if (heureValue != null && Integer.parseInt(heureValue.trim())<24 && Integer.parseInt(heureValue.trim())>=0) {
						reponse = this.requete.selectQuartierPeriodeHeure(Integer.parseInt(heureValue.trim()), periodeValue, quartierValue);
					} else {
						reponse = this.requete.selectQuartierPeriode(periodeValue, quartierValue);
					}
				} else if (vacancesValue != null) {
					if (heureValue != null && Integer.parseInt(heureValue.trim())<24 && Integer.parseInt(heureValue.trim())>=0) {
						reponse = this.requete.selectQuartierVacancesHeure(Integer.parseInt(heureValue.trim()), vacancesValue, quartierValue);
					} else {
						reponse = this.requete.selectQuartierVacances(vacancesValue, quartierValue);
					}
				} else if (dateValue != null) {
					if (heureValue != null && Integer.parseInt(heureValue.trim())<24 && Integer.parseInt(heureValue.trim())>=0) {
						reponse = this.requete.selectQuartierDateHeure(Integer.parseInt(heureValue.trim()), quartierValue, dateValue);
					} else {
						reponse = this.requete.selectQuartierDate(quartierValue, dateValue);
					}
				} else if (heureValue != null && Integer.parseInt(heureValue.trim())<24 && Integer.parseInt(heureValue.trim())>=0) {
					reponse = this.requete.selectQuartierHeure(Integer.parseInt(heureValue.trim()), quartierValue);
				} else {
					reponse = this.requete.selectQuartier(quartierValue);
				}
			}else if(periodeValue != null){
				if(vacancesValue != null){
					if(heureValue != null && Integer.parseInt(heureValue.trim())<24 && Integer.parseInt(heureValue.trim())>=0){
						reponse = this.requete.selectPeriodeVacancesHeure(Integer.parseInt(heureValue.trim()), periodeValue, vacancesValue);	
					}else{
						reponse = this.requete.selectPeriodeVacances(periodeValue, vacancesValue);
					}
				}else if(heureValue != null && Integer.parseInt(heureValue.trim())<24 && Integer.parseInt(heureValue.trim())>=0){
					reponse = this.requete.selectPeriodeHeure(Integer.parseInt(heureValue.trim()), periodeValue);
				}else{
					reponse = this.requete.selectPeriode(periodeValue);	
				}
			}else if(vacancesValue != null){
				if(heureValue != null && Integer.parseInt(heureValue.trim())<24 && Integer.parseInt(heureValue.trim())>=0){
					reponse = this.requete.selectVacancesHeure(Integer.parseInt(heureValue.trim()), vacancesValue);
				}else{
					reponse = this.requete.selectVacances(vacancesValue);	
				}
			}else if(dateValue != null){
				if(heureValue != null && Integer.parseInt(heureValue.trim())<24 && Integer.parseInt(heureValue.trim())>=0){
					reponse = this.requete.selectDateHeure(Integer.parseInt(heureValue.trim()), dateValue);
				}else{
					reponse = this.requete.selectDate(dateValue);	
				}
			}else if(heureValue != null && Integer.parseInt(heureValue.trim())<24 && Integer.parseInt(heureValue.trim())>=0){
				reponse = this.requete.selectHeure(Integer.parseInt(heureValue.trim()));
			}else{
				reponse = this.requete.selectAll();
			}
			
			updateBdd(reponse);
			showBddFiltreePage();
        });
        
        VBox filtre = new VBox(10);
        filtre.setPadding(new Insets(15));
        filtre.setAlignment(Pos.TOP_CENTER);
        filtre.getChildren().addAll(compteur, quartier, periode, vacances, date, heure, valider);
        VBox.setVgrow(filtre, javafx.scene.layout.Priority.ALWAYS);
        filtre.setStyle("-fx-background-color: #178ca4;");
		filtre.setLayoutY(105);
        filtre.setPrefHeight(1000);
        
        //Background
        try {
			BufferedImage bufferedImage = ImageIO.read(new File("../data/background.jpg"));
			Image image = SwingFXUtils.toFXImage(bufferedImage, null);
			ImageView imageView = new ImageView(image);
			imageView.setFitWidth(layout.getWidth());
			imageView.setFitHeight(layout.getHeight());
			imageView.setY(24);
			layout.getChildren().addAll(imageView, this.header, this.bdd, filtre);
        } catch (IOException e) {
			e.printStackTrace();
		}
		
        Scene scene = new Scene(layout, 1024, 768);
		changeScene(scene);
	}
	
	public void showBddFiltreePage() {
		setHeader();
        Pane layout = new Pane();
        
        this.bdd.setLayoutX(140);
        this.bdd.setLayoutY(240);
        
        Button retirerFiltre = new Button("Réinitialiser les filtres");
        retirerFiltre.setPrefWidth(200);
        retirerFiltre.setPrefHeight(50);
        retirerFiltre.setAlignment(Pos.CENTER);
        retirerFiltre.setLayoutX(809);
        retirerFiltre.setLayoutY(668);
        retirerFiltre.setStyle("-fx-text-fill: #072a40; -fx-background-color: #178ca4; -fx-background-radius: 30px;");
        retirerFiltre.setOnAction(event -> {
			ArrayList<String> reponse = this.requete.selectAll();
			updateBdd(reponse);
			showBddPage();
        });
        retirerFiltre.setFont(fontMoyen);
        
        //Background
        try {
			BufferedImage bufferedImage = ImageIO.read(new File("../data/background.jpg"));
			Image image = SwingFXUtils.toFXImage(bufferedImage, null);
			ImageView imageView = new ImageView(image);
			imageView.setFitWidth(layout.getWidth());
			imageView.setFitHeight(layout.getHeight());
			imageView.setY(24);
			layout.getChildren().addAll(imageView, this.header, this.bdd, retirerFiltre);
        } catch (IOException e) {
			e.printStackTrace();
		}
		
        Scene scene = new Scene(layout, 1024, 768);
		changeScene(scene);
	}
	
	public void showBddModifierPage() {
		setHeader();
        Pane layout = new Pane();
        
        ComboBox<String> tables = new ComboBox<>();
        tables.getItems().addAll(
                "Quartier",
                "Compteur",
                "DateInfo"
        );
		tables.setPromptText("Table à modifier");
		tables.setPrefWidth(500);
		tables.setPrefHeight(50);
		tables.setLayoutX(262);
		tables.setLayoutY(329);
		tables.setStyle("-fx-background-color: #f9f7f0; -fx-control-inner-background: #f9f7f0; -fx-border-color: #072a40; -fx-border-width: 2px;");
        
        //Menu
        Button ajouterDonnees = new Button("Ajouter des données");
        ajouterDonnees.setPrefWidth(200);
        ajouterDonnees.setPrefHeight(50);
        ajouterDonnees.setAlignment(Pos.CENTER);
        ajouterDonnees.setStyle("-fx-text-fill: #072a40; -fx-background-color: #178ca4; -fx-background-radius: 30px;");
        ajouterDonnees.setOnAction(event -> {
			String tableValue;
			try{tableValue = tables.getValue();}
			catch(Exception e){
				tableValue = null;
			}
			
			if(tableValue == "Quartier"){
				showBddAjouterDonneeQuartierPage();
			}else if(tableValue == "Compteur"){
				showBddAjouterDonneeCompteurPage();
			}else if(tableValue == "DateInfo"){
				showBddAjouterDonneeDateInfoPage();
			}		
			});
        ajouterDonnees.setFont(fontMoyen);
        
        Button modifierDonnees = new Button("Modifier des données");
        modifierDonnees.setPrefWidth(200);
        modifierDonnees.setPrefHeight(50);
        modifierDonnees.setAlignment(Pos.CENTER);
        modifierDonnees.setStyle("-fx-text-fill: #072a40; -fx-background-color: #178ca4; -fx-background-radius: 30px;");
        modifierDonnees.setOnAction(event -> {
			String tableValue;
			try{tableValue = tables.getValue();}
			catch(Exception e){
				tableValue = null;
			}
			
			if(tableValue == "Quartier"){
				showBddModifierDonneeQuartierPage();
			}else if(tableValue == "Compteur"){
				showBddModifierDonneeCompteurPage();
			}else if(tableValue == "DateInfo"){
				showBddModifierDonneeDateInfoPage();
			}		
			});
        modifierDonnees.setFont(fontMoyen);
        
        Button supprimerDonnees = new Button("Supprimer des données");
        supprimerDonnees.setPrefWidth(200);
        supprimerDonnees.setPrefHeight(50);
        supprimerDonnees.setAlignment(Pos.CENTER);
        supprimerDonnees.setStyle("-fx-text-fill: #072a40; -fx-background-color: #178ca4; -fx-background-radius: 30px;");
        supprimerDonnees.setOnAction(event -> {
			String tableValue;
			try{tableValue = tables.getValue();}
			catch(Exception e){
				tableValue = null;
			}
			
			if(tableValue == "Quartier"){
				showBddSupprimerDonneeQuartierPage();
			}else if(tableValue == "Compteur"){
				showBddSupprimerDonneeCompteurPage();
			}else if(tableValue == "DateInfo"){
				showBddSupprimerDonneeDateInfoPage();
			}		
		});
        supprimerDonnees.setFont(fontMoyen);
        
        HBox menu = new HBox(150);
        menu.setAlignment(Pos.TOP_CENTER);
        menu.getChildren().addAll(ajouterDonnees, modifierDonnees, supprimerDonnees);
        HBox.setHgrow(menu, javafx.scene.layout.Priority.ALWAYS);
        menu.setLayoutY(429);
        menu.setMinWidth(1024);
        menu.setMaxWidth(1024);
        
        //Background
        try {
			BufferedImage bufferedImage = ImageIO.read(new File("../data/background.jpg"));
			Image image = SwingFXUtils.toFXImage(bufferedImage, null);
			ImageView imageView = new ImageView(image);
			imageView.setFitWidth(layout.getWidth());
			imageView.setFitHeight(layout.getHeight());
			imageView.setY(24);
			layout.getChildren().addAll(imageView, this.header, tables, menu);
        } catch (IOException e) {
			e.printStackTrace();
		}
		
        Scene scene = new Scene(layout, 1024, 768);
		changeScene(scene);
	}
	
	public void showBddAjouterDonneeQuartierPage() {
		setHeader();
        Pane layout = new Pane();
        
        TextField id = new TextField();
		id.setPromptText("Id du quartier");
		id.setPrefWidth(200); 
		id.setStyle("-fx-background-color: #f9f7f0; -fx-border-color: #072a40; -fx-border-width: 2px;");
		
		TextField nom = new TextField();
		nom.setPromptText("Nom du quartier");
		nom.setPrefWidth(200); 
		nom.setStyle("-fx-background-color: #f9f7f0; -fx-border-color: #072a40; -fx-border-width: 2px;");
		
		TextField lg = new TextField();
		lg.setPromptText("Longueur des pistes cyclables");
		lg.setPrefWidth(200); 
		lg.setStyle("-fx-background-color: #f9f7f0; -fx-border-color: #072a40; -fx-border-width: 2px;");
        
        HBox data = new HBox(50);
        data.setAlignment(Pos.TOP_CENTER);
        data.getChildren().addAll(id, nom, lg);
        HBox.setHgrow(data, javafx.scene.layout.Priority.ALWAYS);
        data.setLayoutY(329);
        data.setMinWidth(1024);
        data.setMaxWidth(1024);
        
        //Valider
        Button valider = new Button("Valider");
		valider.setPrefWidth(150);
        valider.setPrefHeight(50);
		valider.setStyle("-fx-text-fill: #f9f7f0; -fx-background-color: #072a40; -fx-background-radius: 30px;");
        valider.setFont(fontMoyen);
        valider.setLayoutX(429);
        valider.setLayoutY(429);
        //Liaison avec la base de donnée
        valider.setOnAction(event -> {
			boolean error = false;
			String idValue = null;
			String nomValue = null;
			String lgValue = null;
			try{
				idValue = id.getText();
				nomValue = nom.getText();
				lgValue = lg.getText();
				try {
					int nombre = Integer.parseInt(idValue);
				} catch (NumberFormatException e) {
					error = true;
				}
				try {
					double nombre2 = Double.parseDouble(lgValue);
				} catch (NumberFormatException e) {
					error = true;
				}
			}catch(Exception e){
				error = true;
			}
			
			if(!error){
				this.requeteBdd.insertQuartier(new Quartier(Integer.parseInt(idValue.trim()),nomValue,Double.parseDouble(lgValue.trim())));
				showBddModifierPage();
			}
		});
        
        //Retour
        Button retour = new Button();
        retour.setPrefWidth(20);
        retour.setPrefHeight(10);
        retour.setAlignment(Pos.CENTER);
		retour.setOnAction(event -> showBddModifierPage());
		retour.setStyle("-fx-background-color: transparent;");
        try {
			BufferedImage bufferedImage = ImageIO.read(new File("../data/retour.png"));
			Image image = SwingFXUtils.toFXImage(bufferedImage, null);
			ImageView imageView = new ImageView(image);
			imageView.setFitWidth(layout.getWidth());
			imageView.setFitHeight(layout.getHeight());
			imageView.setFitWidth(50);
			imageView.setPreserveRatio(true);
			retour.setGraphic(imageView);
        } catch (IOException e) {
			e.printStackTrace();
		}
		retour.setLayoutY(105);

        //Background
        try {
			BufferedImage bufferedImage = ImageIO.read(new File("../data/background.jpg"));
			Image image = SwingFXUtils.toFXImage(bufferedImage, null);
			ImageView imageView = new ImageView(image);
			imageView.setFitWidth(layout.getWidth());
			imageView.setFitHeight(layout.getHeight());
			imageView.setY(24);
			layout.getChildren().addAll(imageView, this.header, data, valider, retour);
        } catch (IOException e) {
			e.printStackTrace();
		}
		
        Scene scene = new Scene(layout, 1024, 768);
		changeScene(scene);
	}
	
	public void showBddAjouterDonneeCompteurPage() {
		setHeader();
        Pane layout = new Pane();
        
        TextField id = new TextField();
		id.setPromptText("Id du compteur");
		id.setPrefWidth(200); 
		id.setStyle("-fx-background-color: #f9f7f0; -fx-border-color: #072a40; -fx-border-width: 2px;");
		
		TextField nom = new TextField();
		nom.setPromptText("Nom du compteur");
		nom.setPrefWidth(200); 
		nom.setStyle("-fx-background-color: #f9f7f0; -fx-border-color: #072a40; -fx-border-width: 2px;");
		
		TextField sens = new TextField();
		sens.setPromptText("Sens du compteur");
		sens.setPrefWidth(200); 
		sens.setStyle("-fx-background-color: #f9f7f0; -fx-border-color: #072a40; -fx-border-width: 2px;");
        
        TextField latitude = new TextField();
		latitude.setPromptText("Latitude");
		latitude.setPrefWidth(200); 
		latitude.setStyle("-fx-background-color: #f9f7f0; -fx-border-color: #072a40; -fx-border-width: 2px;");
        
        TextField longitude = new TextField();
		longitude.setPromptText("Longitude");
		longitude.setPrefWidth(200); 
		longitude.setStyle("-fx-background-color: #f9f7f0; -fx-border-color: #072a40; -fx-border-width: 2px;");
        
        TextField quartier = new TextField();
		quartier.setPromptText("Id du quartier");
		quartier.setPrefWidth(200); 
		quartier.setStyle("-fx-background-color: #f9f7f0; -fx-border-color: #072a40; -fx-border-width: 2px;");
        
        HBox data = new HBox(15);
        data.setPadding(new Insets(15));
        data.setAlignment(Pos.TOP_CENTER);
        data.getChildren().addAll(id, nom, sens, latitude, longitude, quartier);
        HBox.setHgrow(data, javafx.scene.layout.Priority.ALWAYS);
        data.setLayoutY(329);
        data.setMinWidth(1024);
        data.setMaxWidth(1024);
        
        //Valider
        Button valider = new Button("Valider");
		valider.setPrefWidth(150);
        valider.setPrefHeight(50);
		valider.setStyle("-fx-text-fill: #f9f7f0; -fx-background-color: #072a40; -fx-background-radius: 30px;");
        valider.setFont(fontMoyen);
        valider.setLayoutX(429);
        valider.setLayoutY(429);
        //Liaison avec la base de donnée
        valider.setOnAction(event -> {
			boolean error = false;
			String idValue = null;
			String nomValue = null;
			String sensValue = null;
			String latitudeValue = null;
			String longitudeValue = null;
			String quartierValue = null;
			try{
				idValue = id.getText();
				nomValue = nom.getText();
				sensValue = sens.getText();
				latitudeValue = latitude.getText();
				longitudeValue = longitude.getText();
				quartierValue = quartier.getText();
				try {
					int nombre = Integer.parseInt(idValue);
				} catch (NumberFormatException e) {
					error = true;
				}
				try {
					double nombre2 = Double.parseDouble(latitudeValue);
				} catch (NumberFormatException e) {
					error = true;
				}
				try {
					double nombre3 = Double.parseDouble(longitudeValue);
				} catch (NumberFormatException e) {
					error = true;
				}
				try {
					int nombre4 = Integer.parseInt(quartierValue);
				} catch (NumberFormatException e) {
					error = true;
				}
			}catch(Exception e){
				error = true;
			}
			
			if(!error){
				this.requeteBdd.insertCompteur(new Compteur(Integer.parseInt(idValue.trim()),nomValue,sensValue,Double.parseDouble(latitudeValue.trim()),Double.parseDouble(longitudeValue.trim()),Integer.parseInt(quartierValue.trim())));
				showBddModifierPage();
			}
		});
        
        //Retour
        Button retour = new Button();
        retour.setPrefWidth(20);
        retour.setPrefHeight(10);
        retour.setAlignment(Pos.CENTER);
		retour.setOnAction(event -> showBddModifierPage());
		retour.setStyle("-fx-background-color: transparent;");
        try {
			BufferedImage bufferedImage = ImageIO.read(new File("../data/retour.png"));
			Image image = SwingFXUtils.toFXImage(bufferedImage, null);
			ImageView imageView = new ImageView(image);
			imageView.setFitWidth(layout.getWidth());
			imageView.setFitHeight(layout.getHeight());
			imageView.setFitWidth(50);
			imageView.setPreserveRatio(true);
			retour.setGraphic(imageView);
        } catch (IOException e) {
			e.printStackTrace();
		}
		retour.setLayoutY(105);

        //Background
        try {
			BufferedImage bufferedImage = ImageIO.read(new File("../data/background.jpg"));
			Image image = SwingFXUtils.toFXImage(bufferedImage, null);
			ImageView imageView = new ImageView(image);
			imageView.setFitWidth(layout.getWidth());
			imageView.setFitHeight(layout.getHeight());
			imageView.setY(24);
			layout.getChildren().addAll(imageView, this.header, data, valider, retour);
        } catch (IOException e) {
			e.printStackTrace();
		}
		
        Scene scene = new Scene(layout, 1024, 768);
		changeScene(scene);
	}
	
	public void showBddAjouterDonneeDateInfoPage() {
		setHeader();
        Pane layout = new Pane();
        
        DatePicker date = new DatePicker();
		date.setPromptText("Date");
		date.setPrefWidth(200); 
		date.setStyle("-fx-control-inner-background: #f9f7f0; -fx-border-color: #072a40; -fx-border-width: 2px;");
		
        TextField temp = new TextField();
		temp.setPromptText("Temperature moyenne");
		temp.setPrefWidth(200); 
		temp.setStyle("-fx-background-color: #f9f7f0; -fx-border-color: #072a40; -fx-border-width: 2px;");
		
		ComboBox<String> jour = new ComboBox<>();
        jour.getItems().addAll(
                "Lundi",
                "Mardi",
                "Mercredi",
                "Jeudi",
                "Vendredi",
                "Samedi",
                "Dimanche"
        );
		jour.setPromptText("Jour");
		jour.setPrefWidth(200); 
		jour.setStyle("-fx-background-color: #f9f7f0; -fx-control-inner-background: #f9f7f0;  -fx-border-color: #072a40; -fx-border-width: 2px;");
        
        ComboBox<String> vacances = new ComboBox<>();
        vacances.getItems().addAll(
                "Ete",
                "Toussaint",
                "Noel",
                "Hiver",
                "Printemps",
                "Ascension"
        );
		vacances.setPromptText("Vacances");
		vacances.setPrefWidth(200); 
		vacances.setStyle("-fx-background-color: #f9f7f0; -fx-control-inner-background: #f9f7f0;  -fx-border-color: #072a40; -fx-border-width: 2px;");      
		
        HBox data = new HBox(40);
        data.setAlignment(Pos.TOP_CENTER);
        data.getChildren().addAll(date, temp, jour, vacances);
        HBox.setHgrow(data, javafx.scene.layout.Priority.ALWAYS);
        data.setLayoutY(329);
        data.setMinWidth(1024);
        data.setMaxWidth(1024);
        
        //Valider
        Button valider = new Button("Valider");
		valider.setPrefWidth(150);
        valider.setPrefHeight(50);
		valider.setStyle("-fx-text-fill: #f9f7f0; -fx-background-color: #072a40; -fx-background-radius: 30px;");
        valider.setFont(fontMoyen);
        valider.setLayoutX(429);
        valider.setLayoutY(429);
        //Liaison avec la base de donnée
        valider.setOnAction(event -> {
			boolean error = false;
			String dateValue = null;
			String tempValue = null;
			String jourValue = null;
			String vacancesValue = null;
			try{
				dateValue = date.getValue().toString();
				tempValue = temp.getText();
				jourValue = jour.getValue();
				vacancesValue = vacances.getValue();
				try {
					double nombre = Double.parseDouble(tempValue);
				} catch (NumberFormatException e) {
					error = true;
				}
			}catch(Exception e){
				error = true;
			}
			
			if(!error){
				this.requeteBdd.insertJour(new Jour(dateValue,Double.parseDouble(tempValue.trim()), jourValue, vacancesValue));
				showBddModifierPage();
			}
		});
        
        //Retour
        Button retour = new Button();
        retour.setPrefWidth(20);
        retour.setPrefHeight(10);
        retour.setAlignment(Pos.CENTER);
		retour.setOnAction(event -> showBddModifierPage());
		retour.setStyle("-fx-background-color: transparent;");
        try {
			BufferedImage bufferedImage = ImageIO.read(new File("../data/retour.png"));
			Image image = SwingFXUtils.toFXImage(bufferedImage, null);
			ImageView imageView = new ImageView(image);
			imageView.setFitWidth(layout.getWidth());
			imageView.setFitHeight(layout.getHeight());
			imageView.setFitWidth(50);
			imageView.setPreserveRatio(true);
			retour.setGraphic(imageView);
        } catch (IOException e) {
			e.printStackTrace();
		}
		retour.setLayoutY(105);

        //Background
        try {
			BufferedImage bufferedImage = ImageIO.read(new File("../data/background.jpg"));
			Image image = SwingFXUtils.toFXImage(bufferedImage, null);
			ImageView imageView = new ImageView(image);
			imageView.setFitWidth(layout.getWidth());
			imageView.setFitHeight(layout.getHeight());
			imageView.setY(24);
			layout.getChildren().addAll(imageView, this.header, data, valider, retour);
        } catch (IOException e) {
			e.printStackTrace();
		}
		
        Scene scene = new Scene(layout, 1024, 768);
		changeScene(scene);
	}
	
	public void showBddModifierDonneeQuartierPage() {
		setHeader();
        Pane layout = new Pane();
        
        TextField id = new TextField();
		id.setPromptText("Id du quartier");
		id.setPrefWidth(200); 
		id.setStyle("-fx-background-color: #f9f7f0; -fx-border-color: #072a40; -fx-border-width: 2px;");
		
		TextField nom = new TextField();
		nom.setPromptText("Nom du quartier");
		nom.setPrefWidth(200); 
		nom.setStyle("-fx-background-color: #f9f7f0; -fx-border-color: #072a40; -fx-border-width: 2px;");
		
		TextField lg = new TextField();
		lg.setPromptText("Longueur des pistes cyclables");
		lg.setPrefWidth(200); 
		lg.setStyle("-fx-background-color: #f9f7f0; -fx-border-color: #072a40; -fx-border-width: 2px;");
        
        HBox data = new HBox(50);
        data.setAlignment(Pos.TOP_CENTER);
        data.getChildren().addAll(id, nom, lg);
        HBox.setHgrow(data, javafx.scene.layout.Priority.ALWAYS);
        data.setLayoutY(329);
        data.setMinWidth(1024);
        data.setMaxWidth(1024);
        
        //Valider
        Button valider = new Button("Valider");
		valider.setPrefWidth(150);
        valider.setPrefHeight(50);
		valider.setStyle("-fx-text-fill: #f9f7f0; -fx-background-color: #072a40; -fx-background-radius: 30px;");
        valider.setFont(fontMoyen);
        valider.setLayoutX(429);
        valider.setLayoutY(429);
        //Liaison avec la base de donnée
        valider.setOnAction(event -> {
			boolean error = false;
			String idValue = null;
			String nomValue = null;
			String lgValue = null;
			try{
				idValue = id.getText();
				nomValue = nom.getText();
				lgValue = lg.getText();
				try {
					int nombre = Integer.parseInt(idValue);
				} catch (NumberFormatException e) {
					error = true;
				}
				try {
					double nombre2 = Double.parseDouble(lgValue);
				} catch (NumberFormatException e) {
					error = true;
				}
			}catch(Exception e){
				error = true;
			}
			
			if(!error){
				this.requeteBdd.updateQuartier(new Quartier(Integer.parseInt(idValue.trim()),nomValue,Double.parseDouble(lgValue.trim())));
				showBddModifierPage();
			}
		});
        
        //Retour
        Button retour = new Button();
        retour.setPrefWidth(20);
        retour.setPrefHeight(10);
        retour.setAlignment(Pos.CENTER);
		retour.setOnAction(event -> showBddModifierPage());
		retour.setStyle("-fx-background-color: transparent;");
        try {
			BufferedImage bufferedImage = ImageIO.read(new File("../data/retour.png"));
			Image image = SwingFXUtils.toFXImage(bufferedImage, null);
			ImageView imageView = new ImageView(image);
			imageView.setFitWidth(layout.getWidth());
			imageView.setFitHeight(layout.getHeight());
			imageView.setFitWidth(50);
			imageView.setPreserveRatio(true);
			retour.setGraphic(imageView);
        } catch (IOException e) {
			e.printStackTrace();
		}
		retour.setLayoutY(105);
        
        //Background
        try {
			BufferedImage bufferedImage = ImageIO.read(new File("../data/background.jpg"));
			Image image = SwingFXUtils.toFXImage(bufferedImage, null);
			ImageView imageView = new ImageView(image);
			imageView.setFitWidth(layout.getWidth());
			imageView.setFitHeight(layout.getHeight());
			imageView.setY(24);
			layout.getChildren().addAll(imageView, this.header, data, valider, retour);
        } catch (IOException e) {
			e.printStackTrace();
		}
		
        Scene scene = new Scene(layout, 1024, 768);
		changeScene(scene);
	}
	
	public void showBddModifierDonneeCompteurPage() {
		setHeader();
        Pane layout = new Pane();
        
        TextField id = new TextField();
		id.setPromptText("Id du compteur");
		id.setPrefWidth(200); 
		id.setStyle("-fx-background-color: #f9f7f0; -fx-border-color: #072a40; -fx-border-width: 2px;");
		
		TextField nom = new TextField();
		nom.setPromptText("Nom du compteur");
		nom.setPrefWidth(200); 
		nom.setStyle("-fx-background-color: #f9f7f0; -fx-border-color: #072a40; -fx-border-width: 2px;");
		
		TextField sens = new TextField();
		sens.setPromptText("Sens du compteur");
		sens.setPrefWidth(200); 
		sens.setStyle("-fx-background-color: #f9f7f0; -fx-border-color: #072a40; -fx-border-width: 2px;");
        
        TextField latitude = new TextField();
		latitude.setPromptText("Latitude");
		latitude.setPrefWidth(200); 
		latitude.setStyle("-fx-background-color: #f9f7f0; -fx-border-color: #072a40; -fx-border-width: 2px;");
        
        TextField longitude = new TextField();
		longitude.setPromptText("Longitude");
		longitude.setPrefWidth(200); 
		longitude.setStyle("-fx-background-color: #f9f7f0; -fx-border-color: #072a40; -fx-border-width: 2px;");
        
        TextField quartier = new TextField();
		quartier.setPromptText("Id du quartier");
		quartier.setPrefWidth(200); 
		quartier.setStyle("-fx-background-color: #f9f7f0; -fx-border-color: #072a40; -fx-border-width: 2px;");
        
        
        HBox data = new HBox(15);
        data.setPadding(new Insets(15));
        data.setAlignment(Pos.TOP_CENTER);
        data.getChildren().addAll(id, nom, sens, latitude, longitude, quartier);
        HBox.setHgrow(data, javafx.scene.layout.Priority.ALWAYS);
        data.setLayoutY(329);
        data.setMinWidth(1024);
        data.setMaxWidth(1024);
        
        //Valider
        Button valider = new Button("Valider");
		valider.setPrefWidth(150);
        valider.setPrefHeight(50);
		valider.setStyle("-fx-text-fill: #f9f7f0; -fx-background-color: #072a40; -fx-background-radius: 30px;");
        valider.setFont(fontMoyen);
        valider.setLayoutX(429);
        valider.setLayoutY(429);
        //Liaison avec la base de donnée
        valider.setOnAction(event -> {
			boolean error = false;
			String idValue = null;
			String nomValue = null;
			String sensValue = null;
			String latitudeValue = null;
			String longitudeValue = null;
			String quartierValue = null;
			try{
				idValue = id.getText();
				nomValue = nom.getText();
				sensValue = sens.getText();
				latitudeValue = latitude.getText();
				longitudeValue = longitude.getText();
				quartierValue = quartier.getText();
				try {
					int nombre = Integer.parseInt(idValue);
				} catch (NumberFormatException e) {
					error = true;
				}
				try {
					double nombre2 = Double.parseDouble(latitudeValue);
				} catch (NumberFormatException e) {
					error = true;
				}
				try {
					double nombre3 = Double.parseDouble(longitudeValue);
				} catch (NumberFormatException e) {
					error = true;
				}
				try {
					int nombre4 = Integer.parseInt(quartierValue);
				} catch (NumberFormatException e) {
					error = true;
				}
			}catch(Exception e){
				error = true;
			}
			
			if(!error){
				this.requeteBdd.updateCompteur(new Compteur(Integer.parseInt(idValue.trim()),nomValue,sensValue,Double.parseDouble(latitudeValue.trim()),Double.parseDouble(longitudeValue.trim()),Integer.parseInt(quartierValue.trim())));
				showBddModifierPage();
			}
		});
        
        //Retour
        Button retour = new Button();
        retour.setPrefWidth(20);
        retour.setPrefHeight(10);
        retour.setAlignment(Pos.CENTER);
		retour.setOnAction(event -> showBddModifierPage());
		retour.setStyle("-fx-background-color: transparent;");
        try {
			BufferedImage bufferedImage = ImageIO.read(new File("../data/retour.png"));
			Image image = SwingFXUtils.toFXImage(bufferedImage, null);
			ImageView imageView = new ImageView(image);
			imageView.setFitWidth(layout.getWidth());
			imageView.setFitHeight(layout.getHeight());
			imageView.setFitWidth(50);
			imageView.setPreserveRatio(true);
			retour.setGraphic(imageView);
        } catch (IOException e) {
			e.printStackTrace();
		}
		retour.setLayoutY(105);
        
        //Background
        try {
			BufferedImage bufferedImage = ImageIO.read(new File("../data/background.jpg"));
			Image image = SwingFXUtils.toFXImage(bufferedImage, null);
			ImageView imageView = new ImageView(image);
			imageView.setFitWidth(layout.getWidth());
			imageView.setFitHeight(layout.getHeight());
			imageView.setY(24);
			layout.getChildren().addAll(imageView, this.header, data, valider, retour);
        } catch (IOException e) {
			e.printStackTrace();
		}
		
        Scene scene = new Scene(layout, 1024, 768);
		changeScene(scene);
	}
	
	public void showBddModifierDonneeDateInfoPage() {
		setHeader();
        Pane layout = new Pane();
        
        DatePicker date = new DatePicker();
		date.setPromptText("Date");
		date.setPrefWidth(200); 
		date.setStyle("-fx-control-inner-background: #f9f7f0; -fx-border-color: #072a40; -fx-border-width: 2px;");
		
        TextField temp = new TextField();
		temp.setPromptText("Temperature moyenne");
		temp.setPrefWidth(200); 
		temp.setStyle("-fx-background-color: #f9f7f0; -fx-border-color: #072a40; -fx-border-width: 2px;");
		
		ComboBox<String> jour = new ComboBox<>();
        jour.getItems().addAll(
                "Lundi",
                "Mardi",
                "Mercredi",
                "Jeudi",
                "Vendredi",
                "Samedi",
                "Dimanche"
        );
		jour.setPromptText("Jour");
		jour.setPrefWidth(200); 
		jour.setStyle("-fx-background-color: #f9f7f0; -fx-control-inner-background: #f9f7f0;  -fx-border-color: #072a40; -fx-border-width: 2px;");
        
        ComboBox<String> vacances = new ComboBox<>();
        vacances.getItems().addAll(
                "Ete",
                "Toussaint",
                "Noel",
                "Hiver",
                "Printemps",
                "Ascension"
        );
		vacances.setPromptText("Vacances");
		vacances.setPrefWidth(200); 
		vacances.setStyle("-fx-background-color: #f9f7f0; -fx-control-inner-background: #f9f7f0;  -fx-border-color: #072a40; -fx-border-width: 2px;");      
		
        HBox data = new HBox(40);
        data.setAlignment(Pos.TOP_CENTER);
        data.getChildren().addAll(date, temp, jour, vacances);
        HBox.setHgrow(data, javafx.scene.layout.Priority.ALWAYS);
        data.setLayoutY(329);
        data.setMinWidth(1024);
        data.setMaxWidth(1024);
        
        //Valider
        Button valider = new Button("Valider");
		valider.setPrefWidth(150);
        valider.setPrefHeight(50);
		valider.setStyle("-fx-text-fill: #f9f7f0; -fx-background-color: #072a40; -fx-background-radius: 30px;");
        valider.setFont(fontMoyen);
        valider.setLayoutX(429);
        valider.setLayoutY(429);
        //Liaison avec la base de donnée
        valider.setOnAction(event -> {
			boolean error = false;
			String dateValue = null;
			String tempValue = null;
			String jourValue = null;
			String vacancesValue = null;
			try{
				dateValue = date.getValue().toString();
				tempValue = temp.getText();
				jourValue = jour.getValue();
				vacancesValue = vacances.getValue();
				try {
					double nombre = Double.parseDouble(tempValue);
				} catch (NumberFormatException e) {
					error = true;
				}
			}catch(Exception e){
				error = true;
			}
			
			if(!error){
				this.requeteBdd.updateJour(new Jour(dateValue,Double.parseDouble(tempValue.trim()), jourValue, vacancesValue));
				showBddModifierPage();
			}
		});
        
        //Retour
        Button retour = new Button();
        retour.setPrefWidth(20);
        retour.setPrefHeight(10);
        retour.setAlignment(Pos.CENTER);
		retour.setOnAction(event -> showBddModifierPage());
		retour.setStyle("-fx-background-color: transparent;");
        try {
			BufferedImage bufferedImage = ImageIO.read(new File("../data/retour.png"));
			Image image = SwingFXUtils.toFXImage(bufferedImage, null);
			ImageView imageView = new ImageView(image);
			imageView.setFitWidth(layout.getWidth());
			imageView.setFitHeight(layout.getHeight());
			imageView.setFitWidth(50);
			imageView.setPreserveRatio(true);
			retour.setGraphic(imageView);
        } catch (IOException e) {
			e.printStackTrace();
		}
		retour.setLayoutY(105);
        
        //Background
        try {
			BufferedImage bufferedImage = ImageIO.read(new File("../data/background.jpg"));
			Image image = SwingFXUtils.toFXImage(bufferedImage, null);
			ImageView imageView = new ImageView(image);
			imageView.setFitWidth(layout.getWidth());
			imageView.setFitHeight(layout.getHeight());
			imageView.setY(24);
			layout.getChildren().addAll(imageView, this.header, data, valider, retour);
        } catch (IOException e) {
			e.printStackTrace();
		}
		
        Scene scene = new Scene(layout, 1024, 768);
		changeScene(scene);
	}
	
	public void showBddSupprimerDonneeQuartierPage() {
		setHeader();
        Pane layout = new Pane();
        
        TextField id = new TextField();
		id.setPromptText("Id du quartier");
		id.setPrefWidth(200); 
		id.setStyle("-fx-background-color: #f9f7f0; -fx-border-color: #072a40; -fx-border-width: 2px;");
        
        HBox data = new HBox(50);
        data.setAlignment(Pos.TOP_CENTER);
        data.getChildren().addAll(id);
        HBox.setHgrow(data, javafx.scene.layout.Priority.ALWAYS);
        data.setLayoutY(329);
        data.setMinWidth(1024);
        data.setMaxWidth(1024);
        
        //Valider
        Button valider = new Button("Valider");
		valider.setPrefWidth(150);
        valider.setPrefHeight(50);
		valider.setStyle("-fx-text-fill: #f9f7f0; -fx-background-color: #072a40; -fx-background-radius: 30px;");
        valider.setFont(fontMoyen);
        valider.setLayoutX(429);
        valider.setLayoutY(429);
        //Liaison avec la base de donnée
        valider.setOnAction(event -> {
			boolean error = false;
			String idValue = null;
			try{
				idValue = id.getText();
				try {
					int nombre = Integer.parseInt(idValue);
				} catch (NumberFormatException e) {
					error = true;
				}
			}catch(Exception e){
				error = true;
			}
			
			if(!error){
				this.requeteBdd.deleteQuartier(new Quartier(Integer.parseInt(idValue.trim()),"test",1));
				showBddModifierPage();
			}
		});
        
		//Retour
        Button retour = new Button();
        retour.setPrefWidth(20);
        retour.setPrefHeight(10);
        retour.setAlignment(Pos.CENTER);
		retour.setOnAction(event -> showBddModifierPage());
		retour.setStyle("-fx-background-color: transparent;");
        try {
			BufferedImage bufferedImage = ImageIO.read(new File("../data/retour.png"));
			Image image = SwingFXUtils.toFXImage(bufferedImage, null);
			ImageView imageView = new ImageView(image);
			imageView.setFitWidth(layout.getWidth());
			imageView.setFitHeight(layout.getHeight());
			imageView.setFitWidth(50);
			imageView.setPreserveRatio(true);
			retour.setGraphic(imageView);
        } catch (IOException e) {
			e.printStackTrace();
		}
		retour.setLayoutY(105);
        
        //Background
        try {
			BufferedImage bufferedImage = ImageIO.read(new File("../data/background.jpg"));
			Image image = SwingFXUtils.toFXImage(bufferedImage, null);
			ImageView imageView = new ImageView(image);
			imageView.setFitWidth(layout.getWidth());
			imageView.setFitHeight(layout.getHeight());
			imageView.setY(24);
			layout.getChildren().addAll(imageView, this.header, data, valider, retour);
        } catch (IOException e) {
			e.printStackTrace();
		}
		
        Scene scene = new Scene(layout, 1024, 768);
		changeScene(scene);
	}
	
	public void showBddSupprimerDonneeCompteurPage() {
		setHeader();
        Pane layout = new Pane();
       
        TextField id = new TextField();
		id.setPromptText("Id du compteur");
		id.setPrefWidth(200); 
		id.setStyle("-fx-background-color: #f9f7f0; -fx-border-color: #072a40; -fx-border-width: 2px;");
		
        HBox data = new HBox(50);
        data.setAlignment(Pos.TOP_CENTER);
        data.getChildren().addAll(id);
        HBox.setHgrow(data, javafx.scene.layout.Priority.ALWAYS);
        data.setLayoutY(329);
        data.setMinWidth(1024);
        data.setMaxWidth(1024);
        
        //Valider
        Button valider = new Button("Valider");
		valider.setPrefWidth(150);
        valider.setPrefHeight(50);
		valider.setStyle("-fx-text-fill: #f9f7f0; -fx-background-color: #072a40; -fx-background-radius: 30px;");
        valider.setFont(fontMoyen);
        valider.setLayoutX(429);
        valider.setLayoutY(429);
        //Liaison avec la base de donnée
        valider.setOnAction(event -> {
			boolean error = false;
			String idValue = null;
			try{
				idValue = id.getText();
				try {
					int nombre = Integer.parseInt(idValue);
				} catch (NumberFormatException e) {
					error = true;
				}
			}catch(Exception e){
				error = true;
			}
			
			if(!error){
				this.requeteBdd.deleteCompteur(new Compteur(Integer.parseInt(idValue.trim()),"test","test",0,0,1));
				showBddModifierPage();
			}
		});
        
		//Retour
        Button retour = new Button();
        retour.setPrefWidth(20);
        retour.setPrefHeight(10);
        retour.setAlignment(Pos.CENTER);
		retour.setOnAction(event -> showBddModifierPage());
		retour.setStyle("-fx-background-color: transparent;");
        try {
			BufferedImage bufferedImage = ImageIO.read(new File("../data/retour.png"));
			Image image = SwingFXUtils.toFXImage(bufferedImage, null);
			ImageView imageView = new ImageView(image);
			imageView.setFitWidth(layout.getWidth());
			imageView.setFitHeight(layout.getHeight());
			imageView.setFitWidth(50);
			imageView.setPreserveRatio(true);
			retour.setGraphic(imageView);
        } catch (IOException e) {
			e.printStackTrace();
		}
		retour.setLayoutY(105);
        
        //Background
        try {
			BufferedImage bufferedImage = ImageIO.read(new File("../data/background.jpg"));
			Image image = SwingFXUtils.toFXImage(bufferedImage, null);
			ImageView imageView = new ImageView(image);
			imageView.setFitWidth(layout.getWidth());
			imageView.setFitHeight(layout.getHeight());
			imageView.setY(24);
			layout.getChildren().addAll(imageView, this.header, data, valider, retour);
        } catch (IOException e) {
			e.printStackTrace();
		}
		
        Scene scene = new Scene(layout, 1024, 768);
		changeScene(scene);
	}
	
	public void showBddSupprimerDonneeDateInfoPage() {
		setHeader();
        Pane layout = new Pane();
        
        DatePicker date = new DatePicker();
		date.setPromptText("Date");
		date.setPrefWidth(200); 
		date.setStyle("-fx-control-inner-background: #f9f7f0; -fx-border-color: #072a40; -fx-border-width: 2px;");
		
        HBox data = new HBox(50);
        data.setAlignment(Pos.TOP_CENTER);
        data.getChildren().addAll(date);
        HBox.setHgrow(data, javafx.scene.layout.Priority.ALWAYS);
        data.setLayoutY(329);
        data.setMinWidth(1024);
        data.setMaxWidth(1024);
        
        //Valider
        Button valider = new Button("Valider");
		valider.setPrefWidth(150);
        valider.setPrefHeight(50);
		valider.setStyle("-fx-text-fill: #f9f7f0; -fx-background-color: #072a40; -fx-background-radius: 30px;");
        valider.setFont(fontMoyen);
        valider.setLayoutX(429);
        valider.setLayoutY(429);
        //Liaison avec la base de donnée
        valider.setOnAction(event -> {
			boolean error = false;
			String dateValue = null;
			try{
				dateValue = date.getValue().toString();
			}catch(Exception e){
				error = true;
			}
			
			if(!error){
				this.requeteBdd.deleteJour(new Jour(dateValue,0, "Lundi", "Ete"));
				showBddModifierPage();
			}
		});
        
		//Retour
        Button retour = new Button();
        retour.setPrefWidth(20);
        retour.setPrefHeight(10);
        retour.setAlignment(Pos.CENTER);
		retour.setOnAction(event -> showBddModifierPage());
		retour.setStyle("-fx-background-color: transparent;");
        try {
			BufferedImage bufferedImage = ImageIO.read(new File("../data/retour.png"));
			Image image = SwingFXUtils.toFXImage(bufferedImage, null);
			ImageView imageView = new ImageView(image);
			imageView.setFitWidth(layout.getWidth());
			imageView.setFitHeight(layout.getHeight());
			imageView.setFitWidth(50);
			imageView.setPreserveRatio(true);
			retour.setGraphic(imageView);
        } catch (IOException e) {
			e.printStackTrace();
		}
		retour.setLayoutY(105);
        
        //Background
        try {
			BufferedImage bufferedImage = ImageIO.read(new File("../data/background.jpg"));
			Image image = SwingFXUtils.toFXImage(bufferedImage, null);
			ImageView imageView = new ImageView(image);
			imageView.setFitWidth(layout.getWidth());
			imageView.setFitHeight(layout.getHeight());
			imageView.setY(24);
			layout.getChildren().addAll(imageView, this.header, data, valider, retour);
        } catch (IOException e) {
			e.printStackTrace();
		}
		
        Scene scene = new Scene(layout, 1024, 768);
		changeScene(scene);
	}
	
	//Carte de la ville
	public void showCartePage() {
		setHeader();
        Pane layout = new Pane();
        
        Button rechercherPoint = new Button("Rechercher un point");
        rechercherPoint.setPrefWidth(200);
        rechercherPoint.setPrefHeight(50);
        rechercherPoint.setAlignment(Pos.CENTER);
        rechercherPoint.setLayoutX(809);
        rechercherPoint.setLayoutY(120);
        rechercherPoint.setStyle("-fx-text-fill: #072a40; -fx-background-color: #178ca4; -fx-background-radius: 30px;");
        rechercherPoint.setOnAction(event -> showCartePointPage());
        rechercherPoint.setFont(fontMoyen);
        
        Button ajoutFiltre = new Button("Ajouter les filtres");
        ajoutFiltre.setPrefWidth(200);
        ajoutFiltre.setPrefHeight(50);
        ajoutFiltre.setAlignment(Pos.CENTER);
        ajoutFiltre.setLayoutX(15);
        ajoutFiltre.setLayoutY(120);
        ajoutFiltre.setStyle("-fx-text-fill: #072a40; -fx-background-color: #178ca4; -fx-background-radius: 30px;");
        ajoutFiltre.setOnAction(event -> showCarteFiltrePage());
        ajoutFiltre.setFont(fontMoyen);
        
       //Carte
        WebView webView = new WebView();
        
        String htmlContent = "<html><head><style>"
                + "#map{position: fixed; top: 0; right: 0; bottom: 0; left: 0; height: 100%; width: 100%;}</style>"
                + "<script src=\"https://unpkg.com/leaflet@1.7.1/dist/leaflet.js\"></script>"
                + "<link rel=\"stylesheet\" href=\"https://unpkg.com/leaflet@1.7.1/dist/leaflet.css\" />"
                + "</head><body>"
                + "<div id=\"map\"></div>"
                + "<script>"
                + "var map = L.map('map').setView([47.218371, -1.553621], 13);"
                + "L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {"
                + "  maxZoom: 19,"
                + "  attribution: 'Carte de base &copy; <a href=\"https://www.openstreetmap.org/\">OpenStreetMap</a> contributeurs'"
                + "}).addTo(map);";
                
        //Ajout des points sur la carte
        ArrayList<Compteur> lesCompteurs = this.requeteBdd.implementCompteur();
        for(Compteur compteur : lesCompteurs){
			htmlContent += "L.marker(["+compteur.getLatitude()+", "+compteur.getLongitude()+"]).addTo(map).bindPopup('"+compteur.getId()+"');";
		}
  
		htmlContent += "</script></body></html>";
        
        webView.getEngine().loadContent(htmlContent);
        webView.setPrefWidth(1024);
		webView.setPrefHeight(768);
        layout.getChildren().addAll(webView, this.header, rechercherPoint, ajoutFiltre);
        
        Scene scene = new Scene(layout, 1024, 768);
		changeScene(scene);
	}
	
	public void showCarteFiltrePage() {
		setHeader();
        Pane layout = new Pane();
        
        //Filtre
        ComboBox<String> quartier = new ComboBox<>();
        quartier.getItems().addAll(
				"null",
                "Centre Ville",
                "Bellevue - Chantenay - Sainte Anne",
                "Dervallières - Zola",
                "Hauts Pavés - Saint Félix",
                "Malakoff - Saint-Donatien",
                "Ile de Nantes",
                "Breil - Barberie",
                "Nantes Nord",
                "Nantes Erdre",
                "Doulon - Bottière",
                "Nantes Sud",
                "Trentemoult",
                "Hôtel de Ville",
                "Château de Rezé",
                "Pont Rousseau",
                "La Houssais",
                "Blordière",
                "Ragon"
        );
		quartier.setPromptText("Quartier");
		quartier.setPrefWidth(200); 
		quartier.setStyle("-fx-background-color: #f9f7f0; -fx-control-inner-background: #f9f7f0;");
        
        DatePicker date = new DatePicker();
		date.setPromptText("Date");
		date.setPrefWidth(200); 
		date.setStyle("-fx-control-inner-background: #f9f7f0;");
        
        ComboBox<String> information = new ComboBox<>();
        information.getItems().addAll(
                "Maximum",
                "Minimum"
        );
		information.setPromptText("Information souhaitée");
		information.setPrefWidth(200); 
		information.setStyle("-fx-background-color: #f9f7f0; -fx-control-inner-background: #f9f7f0;");
        
        Button valider = new Button("Valider");
		valider.setPrefWidth(150);
        valider.setPrefHeight(50);
		valider.setOnAction(event -> {
			String quartierValue;
			try{quartierValue = quartier.getValue();}
			catch(Exception e){
				quartierValue = null;
			}
			String dateValue;
			try{dateValue = date.getValue().toString();}
			catch(Exception e){
				dateValue = null;
			}
			String infoValue;
			try{infoValue = information.getValue();}
			catch(Exception e){
				infoValue = null;
			}
			this.listePoint = new ArrayList<Integer>();
			ArrayList<String> reponse;
			
			if(infoValue==null){
				if(quartierValue != null){
					if(dateValue != null){
						reponse = this.requete.carteQuartierDate(dateValue, quartierValue);
					}else{
						reponse = this.requete.carteQuartier(quartierValue);
					}
				}else if(dateValue != null){
					reponse = this.requete.carteDate(dateValue);
				}else{
					reponse = this.requete.carteAll();
				}
			}else if(infoValue=="Maximum"){
				if(quartierValue != null){
					if(dateValue != null){
						reponse = this.requete.carteQuartierDateMax(dateValue, quartierValue);
					}else{
						reponse = this.requete.carteQuartierMax(quartierValue);
					}
				}else if(dateValue != null){
					reponse = this.requete.carteDateMax(dateValue);
				}else{
					reponse = this.requete.carteMax();
				}
			}else{
				if(quartierValue != null){
					if(dateValue != null){
						reponse = this.requete.carteQuartierDateMin(dateValue, quartierValue);
					}else{
						reponse = this.requete.carteQuartierMin(quartierValue);
					}
				}else if(dateValue != null){
					reponse = this.requete.carteDateMin(dateValue);
				}else{
					reponse = this.requete.carteMin();
				}
			}
			
			for(String elem : reponse){
				this.listePoint.add(Integer.parseInt(elem));
			}
			
			showCarteFiltreePage();
			});
		valider.setStyle("-fx-text-fill: #f9f7f0; -fx-background-color: #072a40; -fx-background-radius: 30px;");
        valider.setFont(fontMoyen);
        
        VBox filtre = new VBox(10);
        filtre.setPadding(new Insets(15));
        filtre.setAlignment(Pos.TOP_CENTER);
        filtre.getChildren().addAll(quartier, date, information, valider);
        VBox.setVgrow(filtre, javafx.scene.layout.Priority.ALWAYS);
        filtre.setStyle("-fx-background-color: #178ca4;");
		filtre.setLayoutY(105);
        filtre.setPrefHeight(1000);
        
        //Carte
        WebView webView = new WebView();
        
        String htmlContent = "<html><head><style>"
                + "#map{position: fixed; top: 0; right: 0; bottom: 0; left: 0; height: 100%; width: 100%;}</style>"
                + "<script src=\"https://unpkg.com/leaflet@1.7.1/dist/leaflet.js\"></script>"
                + "<link rel=\"stylesheet\" href=\"https://unpkg.com/leaflet@1.7.1/dist/leaflet.css\" />"
                + "</head><body>"
                + "<div id=\"map\"></div>"
                + "<script>"
                + "var map = L.map('map').setView([47.218371, -1.553621], 13);"
                + "L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {"
                + "  maxZoom: 19,"
                + "  attribution: 'Carte de base &copy; <a href=\"https://www.openstreetmap.org/\">OpenStreetMap</a> contributeurs'"
                + "}).addTo(map);";
                
        //Ajout des points sur la carte
        ArrayList<Compteur> lesCompteurs = this.requeteBdd.implementCompteur();
        for(Compteur compteur : lesCompteurs){
			htmlContent += "L.marker(["+compteur.getLatitude()+", "+compteur.getLongitude()+"]).addTo(map).bindPopup('"+compteur.getId()+"');";
		}
  
		htmlContent += "</script></body></html>";
        
        webView.getEngine().loadContent(htmlContent);
        webView.setPrefWidth(794);
		webView.setPrefHeight(768);
		webView.setLayoutX(230);
		
		Button rechercherPoint = new Button("Rechercher un point");
        rechercherPoint.setPrefWidth(200);
        rechercherPoint.setPrefHeight(50);
        rechercherPoint.setAlignment(Pos.CENTER);
        rechercherPoint.setLayoutX(809);
        rechercherPoint.setLayoutY(120);
        rechercherPoint.setStyle("-fx-text-fill: #072a40; -fx-background-color: #178ca4; -fx-background-radius: 30px;");
        rechercherPoint.setOnAction(event -> showCartePointPage());
        rechercherPoint.setFont(fontMoyen);
		
        layout.getChildren().addAll(webView, this.header, filtre, rechercherPoint);
        
        Scene scene = new Scene(layout, 1024, 768);
		changeScene(scene);
	}
	
	public void showCarteFiltreePage() {
		setHeader();
        Pane layout = new Pane();
        
        Button rechercherPoint = new Button("Rechercher un point");
        rechercherPoint.setPrefWidth(200);
        rechercherPoint.setPrefHeight(50);
        rechercherPoint.setAlignment(Pos.CENTER);
        rechercherPoint.setLayoutX(809);
        rechercherPoint.setLayoutY(120);
        rechercherPoint.setStyle("-fx-text-fill: #072a40; -fx-background-color: #178ca4; -fx-background-radius: 30px;");
        rechercherPoint.setOnAction(event -> showCartePointPage());
        rechercherPoint.setFont(fontMoyen);
        
        Button retirerFiltre = new Button("Réinitialiser les filtres");
        retirerFiltre.setPrefWidth(200);
        retirerFiltre.setPrefHeight(50);
        retirerFiltre.setAlignment(Pos.CENTER);
        retirerFiltre.setLayoutX(809);
        retirerFiltre.setLayoutY(668);
        retirerFiltre.setStyle("-fx-text-fill: #072a40; -fx-background-color: #178ca4; -fx-background-radius: 30px;");
        retirerFiltre.setOnAction(event -> showCartePage());
        retirerFiltre.setFont(fontMoyen);
        
        //Carte
        WebView webView = new WebView();
        
        String htmlContent = "<html><head><style>"
                + "#map{position: fixed; top: 0; right: 0; bottom: 0; left: 0; height: 100%; width: 100%;}</style>"
                + "<script src=\"https://unpkg.com/leaflet@1.7.1/dist/leaflet.js\"></script>"
                + "<link rel=\"stylesheet\" href=\"https://unpkg.com/leaflet@1.7.1/dist/leaflet.css\" />"
                + "</head><body>"
                + "<div id=\"map\"></div>"
                + "<script>"
                + "var map = L.map('map').setView([47.218371, -1.553621], 13);"
                + "L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {"
                + "  maxZoom: 19,"
                + "  attribution: 'Carte de base &copy; <a href=\"https://www.openstreetmap.org/\">OpenStreetMap</a> contributeurs'"
                + "}).addTo(map);";
                
        //Ajout des points sur la carte
        ArrayList<Compteur> lesCompteurs = this.requeteBdd.implementCompteur();
        for(Compteur compteur : lesCompteurs){
			for(int point : listePoint){
				if(point == compteur.getId()){
					htmlContent += "L.marker(["+compteur.getLatitude()+", "+compteur.getLongitude()+"]).addTo(map).bindPopup('"+compteur.getId()+"');";
				}
			}
		}
  
		htmlContent += "</script></body></html>";
        
        webView.getEngine().loadContent(htmlContent);
        
        webView.setPrefWidth(1024);
		webView.setPrefHeight(768);
        layout.getChildren().addAll(webView, this.header, rechercherPoint, retirerFiltre);
        
        Scene scene = new Scene(layout, 1024, 768);
		changeScene(scene);
	}
	
	public void showCartePointPage() {
		setHeader();
        Pane layout = new Pane();
        
        TextField compteur = new TextField();
		compteur.setPromptText("Compteur");
		compteur.setPrefWidth(200); 
		compteur.setStyle("-fx-background-color: #f9f7f0;");
        
        Button valider = new Button("Valider");
		valider.setPrefWidth(150);
        valider.setPrefHeight(50);
		valider.setOnAction(event -> {
			String compteurValue = compteur.getText();
			this.pointChoisi = Integer.parseInt(compteurValue.trim());
			showCartePointResultatPage();
			});
		valider.setStyle("-fx-text-fill: #f9f7f0; -fx-background-color: #072a40; -fx-background-radius: 30px;");
        valider.setFont(fontMoyen);
        
        VBox filtre = new VBox(10);
        filtre.setPadding(new Insets(15));
        filtre.setAlignment(Pos.TOP_CENTER);
        filtre.getChildren().addAll(compteur, valider);
        VBox.setVgrow(filtre, javafx.scene.layout.Priority.ALWAYS);
        filtre.setStyle("-fx-background-color: #178ca4;");
		filtre.setLayoutY(105);
        filtre.setPrefHeight(1000);
        
        //Carte
        WebView webView = new WebView();
        String htmlContent = "<html><head><style>"
                + "#map{position: fixed; top: 0; right: 0; bottom: 0; left: 0; height: 100%; width: 100%;}</style>"
                + "<script src=\"https://unpkg.com/leaflet@1.7.1/dist/leaflet.js\"></script>"
                + "<link rel=\"stylesheet\" href=\"https://unpkg.com/leaflet@1.7.1/dist/leaflet.css\" />"
                + "</head><body>"
                + "<div id=\"map\"></div>"
                + "<script>"
                + "var map = L.map('map').setView([47.218371, -1.553621], 13);"
                + "L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {"
                + "  maxZoom: 19,"
                + "  attribution: 'Carte de base &copy; <a href=\"https://www.openstreetmap.org/\">OpenStreetMap</a> contributeurs'"
                + "}).addTo(map);";
                
        //Ajout des points sur la carte
        ArrayList<Compteur> lesCompteurs = this.requeteBdd.implementCompteur();
        for(Compteur compteurs : lesCompteurs){
			htmlContent += "L.marker(["+compteurs.getLatitude()+", "+compteurs.getLongitude()+"]).addTo(map).bindPopup('"+compteurs.getId()+"');";
		}
  
		htmlContent += "</script></body></html>";
        
        webView.getEngine().loadContent(htmlContent);
        webView.setPrefWidth(794);
		webView.setPrefHeight(768);
		webView.setLayoutX(230);
        layout.getChildren().addAll(webView, this.header, filtre);
        
        Scene scene = new Scene(layout, 1024, 768);
		changeScene(scene);
	}
	
	public void showCartePointResultatPage() {
		setHeader();
        Pane layout = new Pane();
        
        //Retour
        Button retour = new Button();
        retour.setPrefWidth(20);
        retour.setPrefHeight(10);
        retour.setAlignment(Pos.CENTER);
		retour.setOnAction(event -> showCartePage());
		retour.setStyle("-fx-background-color: transparent;");
        try {
			BufferedImage bufferedImage = ImageIO.read(new File("../data/retour.png"));
			Image image = SwingFXUtils.toFXImage(bufferedImage, null);
			ImageView imageView = new ImageView(image);
			imageView.setFitWidth(layout.getWidth());
			imageView.setFitHeight(layout.getHeight());
			imageView.setFitWidth(50);
			imageView.setPreserveRatio(true);
			retour.setGraphic(imageView);
        } catch (IOException e) {
			e.printStackTrace();
		}
		retour.setLayoutX(230);
		retour.setLayoutY(105);
        
        //Info points
        String reponse = this.requete.point(this.pointChoisi).get(0);  
        String[] tmp = reponse.split(",");
        Text nom = new Text(tmp[0]);
        nom.setStyle("-fx-fill: #f9f7f0;");
        nom.setFont(Font.font("Arial", FontWeight.BOLD, 15));
        Text latitude = new Text("Latitude : "+tmp[1]);
        latitude.setStyle("-fx-fill: #072a40;");
        latitude.setFont(fontMoyen);
        Text longitude = new Text("Longitude : "+tmp[2]);
        longitude.setStyle("-fx-fill: #072a40;");
        longitude.setFont(fontMoyen);
        Text quartier = new Text("Quartier : "+tmp[3]);
        quartier.setStyle("-fx-fill: #072a40;");
        quartier.setFont(fontMoyen);
        Text total = new Text("Total : "+tmp[4]);
        total.setStyle("-fx-fill: #072a40;");
        total.setFont(fontMoyen);
        Text minimum = new Text("Minimum : "+tmp[5]);
        minimum.setStyle("-fx-fill: #072a40;");
        minimum.setFont(fontMoyen);
        Text maximum = new Text("Maximum : "+tmp[6]);
        maximum.setStyle("-fx-fill: #072a40;");
        maximum.setFont(fontMoyen);
        
        //Graphe
        ArrayList<String> reponseGraph = this.requete.graphique(this.pointChoisi);
        
        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();
        BarChart<String, Number> barChart = new BarChart<>(xAxis, yAxis);
        XYChart.Series<String, Number> series1 = new XYChart.Series<>();
        series1.setName("Nombre de passages");
        series1.getData().add(new XYChart.Data<>("Février 2022", Integer.parseInt(reponseGraph.get(11))));
        series1.getData().add(new XYChart.Data<>("Mars 2022", Integer.parseInt(reponseGraph.get(10))));
        series1.getData().add(new XYChart.Data<>("Avril 2022", Integer.parseInt(reponseGraph.get(9))));
        series1.getData().add(new XYChart.Data<>("Mai 2022", Integer.parseInt(reponseGraph.get(8))));
        series1.getData().add(new XYChart.Data<>("Juin 2022", Integer.parseInt(reponseGraph.get(7))));
        series1.getData().add(new XYChart.Data<>("Juillet 2022", Integer.parseInt(reponseGraph.get(6))));
        series1.getData().add(new XYChart.Data<>("Août 2022", Integer.parseInt(reponseGraph.get(5))));
        series1.getData().add(new XYChart.Data<>("Septembre 2022", Integer.parseInt(reponseGraph.get(4))));
        series1.getData().add(new XYChart.Data<>("Octobre 2022", Integer.parseInt(reponseGraph.get(3))));
        series1.getData().add(new XYChart.Data<>("Novembre 2022", Integer.parseInt(reponseGraph.get(2))));
        series1.getData().add(new XYChart.Data<>("Décembre 2022", Integer.parseInt(reponseGraph.get(1))));
        series1.getData().add(new XYChart.Data<>("Janvier 2023", Integer.parseInt(reponseGraph.get(0))));
        barChart.getData().add(series1);
        String barColor = "#178ca4";
        String barStyle = "-fx-bar-fill: " + barColor + ";";
        for (XYChart.Data<String, Number> data : series1.getData()) {
            Node barNode = data.getNode();
            barNode.setStyle(barStyle);
        }
        barChart.setPrefWidth(200);
        barChart.setPrefHeight(400);
        
        VBox resultat = new VBox(10);
        resultat.setPadding(new Insets(15));
        resultat.setAlignment(Pos.TOP_CENTER);
        VBox.setVgrow(resultat, javafx.scene.layout.Priority.ALWAYS);
        resultat.getChildren().addAll(nom, latitude, longitude, quartier, total, barChart, maximum, minimum);
        resultat.setStyle("-fx-background-color: #178ca4;");
        resultat.setPrefWidth(230);
		resultat.setLayoutY(105);
        resultat.setPrefHeight(1000);
        
        //Carte
        WebView webView = new WebView();
        String htmlContent = "<html><head><style>"
                + "#map{position: fixed; top: 0; right: 0; bottom: 0; left: 0; height: 100%; width: 100%;}</style>"
                + "<script src=\"https://unpkg.com/leaflet@1.7.1/dist/leaflet.js\"></script>"
                + "<link rel=\"stylesheet\" href=\"https://unpkg.com/leaflet@1.7.1/dist/leaflet.css\" />"
                + "</head><body>"
                + "<div id=\"map\"></div>"
                + "<script>"
                + "var map = L.map('map').setView([47.218371, -1.553621], 13);"
                + "L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {"
                + "  maxZoom: 19,"
                + "  attribution: 'Carte de base &copy; <a href=\"https://www.openstreetmap.org/\">OpenStreetMap</a> contributeurs'"
                + "}).addTo(map);";
                
        //Ajout des points sur la carte
        ArrayList<Compteur> lesCompteurs = this.requeteBdd.implementCompteur();
        for(Compteur compteur : lesCompteurs){
			if(compteur.getId()==this.pointChoisi){
				htmlContent += "L.marker(["+compteur.getLatitude()+", "+compteur.getLongitude()+"]).addTo(map).bindPopup('"+compteur.getId()+"');";
			}
		}
  
		htmlContent += "</script></body></html>";
        
        webView.getEngine().loadContent(htmlContent);
        webView.setPrefWidth(794);
		webView.setPrefHeight(768);
		webView.setLayoutX(230);
        layout.getChildren().addAll(webView, this.header, retour, resultat);
        
        Scene scene = new Scene(layout, 1024, 768);
		changeScene(scene);
	}
	
	//Carte des pistes
	public void showCartePistesPage() {
		setHeader();
        Pane layout = new Pane();
        
        Text titre = new Text("Carte des pistes");
        titre.setX(320);
		titre.setY(182);
		titre.setStyle("-fx-fill: #072a40;");
		titre.setFont(fontTitre);
        
        //Retour
        Button retour = new Button();
        retour.setPrefWidth(20);
        retour.setPrefHeight(10);
        retour.setAlignment(Pos.CENTER);
		retour.setOnAction(event -> showMainPage());
		retour.setStyle("-fx-background-color: transparent;");
        try {
			BufferedImage bufferedImage = ImageIO.read(new File("../data/retour.png"));
			Image image = SwingFXUtils.toFXImage(bufferedImage, null);
			ImageView imageView = new ImageView(image);
			imageView.setFitWidth(layout.getWidth());
			imageView.setFitHeight(layout.getHeight());
			imageView.setFitWidth(50);
			imageView.setPreserveRatio(true);
			retour.setGraphic(imageView);
        } catch (IOException e) {
			e.printStackTrace();
		}
		retour.setLayoutY(105);
		
        //Background
        try {
			BufferedImage bufferedImage = ImageIO.read(new File("../data/background.jpg"));
			Image image = SwingFXUtils.toFXImage(bufferedImage, null);
			ImageView imageView = new ImageView(image);
			imageView.setFitWidth(layout.getWidth());
			imageView.setFitHeight(layout.getHeight());
			imageView.setY(24);
			layout.getChildren().addAll(imageView, this.header, titre, retour);
        } catch (IOException e) {
			e.printStackTrace();
		}
		
		//Carte des pistes
        try {
			BufferedImage bufferedImage = ImageIO.read(new File("../data/cartePiste.png"));
			Image image = SwingFXUtils.toFXImage(bufferedImage, null);
			ImageView imageView = new ImageView(image);
			imageView.setFitWidth(400);
			imageView.setPreserveRatio(true);
			imageView.setX(312);
			imageView.setY(218.5);
			layout.getChildren().addAll(imageView);
        } catch (IOException e) {
			e.printStackTrace();
		}
		
        Scene scene = new Scene(layout, 1024, 768);
		changeScene(scene);
	}
	
	//Données
	public void showDonneesPage() {
		setHeader();
        Pane layout = new Pane();
        
        Text titre = new Text("Données et projection");
        titre.setX(250);
		titre.setY(182);
		titre.setStyle("-fx-fill: #072a40;");
		titre.setFont(fontTitre);
        
        //Retour
        Button retour = new Button();
        retour.setPrefWidth(20);
        retour.setPrefHeight(10);
        retour.setAlignment(Pos.CENTER);
		retour.setOnAction(event -> showMainPage());
		retour.setStyle("-fx-background-color: transparent;");
        try {
			BufferedImage bufferedImage = ImageIO.read(new File("../data/retour.png"));
			Image image = SwingFXUtils.toFXImage(bufferedImage, null);
			ImageView imageView = new ImageView(image);
			imageView.setFitWidth(layout.getWidth());
			imageView.setFitHeight(layout.getHeight());
			imageView.setFitWidth(50);
			imageView.setPreserveRatio(true);
			retour.setGraphic(imageView);
        } catch (IOException e) {
			e.printStackTrace();
		}
		retour.setLayoutY(105);
        
        //Background
        try {
			BufferedImage bufferedImage = ImageIO.read(new File("../data/background.jpg"));
			Image image = SwingFXUtils.toFXImage(bufferedImage, null);
			ImageView imageView = new ImageView(image);
			imageView.setFitWidth(layout.getWidth());
			imageView.setFitHeight(layout.getHeight());
			imageView.setY(24);
			layout.getChildren().addAll(imageView, this.header, titre, retour);
        } catch (IOException e) {
			e.printStackTrace();
		}
		
		//Donnees
		try {
			BufferedImage bufferedImage = ImageIO.read(new File("../data/projection.jpg"));
			Image image = SwingFXUtils.toFXImage(bufferedImage, null);
			ImageView imageView = new ImageView(image);
			imageView.setFitWidth(layout.getWidth());
			imageView.setFitHeight(layout.getHeight());
			imageView.setFitWidth(800);
			imageView.setPreserveRatio(true);
			imageView.setX(112);
			imageView.setY(219.5);
			layout.getChildren().addAll(imageView);
        } catch (IOException e) {
			e.printStackTrace();
		}
		
        Scene scene = new Scene(layout, 1024, 768);
		changeScene(scene);
	}
	
	//Graphes
	public void showGraphesPage() {
		setHeader();
        Pane layout = new Pane();
        
        Text titre = new Text("Graphes");
        titre.setX(413.5);
		titre.setY(182);
		titre.setStyle("-fx-fill: #072a40;");
		titre.setFont(fontTitre);
		
		//Retour
        Button retour = new Button();
        retour.setPrefWidth(20);
        retour.setPrefHeight(10);
        retour.setAlignment(Pos.CENTER);
		retour.setOnAction(event -> showMainPage());
		retour.setStyle("-fx-background-color: transparent;");
        try {
			BufferedImage bufferedImage = ImageIO.read(new File("../data/retour.png"));
			Image image = SwingFXUtils.toFXImage(bufferedImage, null);
			ImageView imageView = new ImageView(image);
			imageView.setFitWidth(layout.getWidth());
			imageView.setFitHeight(layout.getHeight());
			imageView.setFitWidth(50);
			imageView.setPreserveRatio(true);
			retour.setGraphic(imageView);
        } catch (IOException e) {
			e.printStackTrace();
		}
		retour.setLayoutY(105);
        
        //Background
        try {
			BufferedImage bufferedImage = ImageIO.read(new File("../data/background.jpg"));
			Image image = SwingFXUtils.toFXImage(bufferedImage, null);
			ImageView imageView = new ImageView(image);
			imageView.setFitWidth(layout.getWidth());
			imageView.setFitHeight(layout.getHeight());
			imageView.setY(24);
			layout.getChildren().addAll(imageView, this.header, titre, retour);
        } catch (IOException e) {
			e.printStackTrace();
		}
		
		//Images
        try {
			BufferedImage bufferedImage1 = ImageIO.read(new File("../data/graphe1.jpg"));
			Image image1 = SwingFXUtils.toFXImage(bufferedImage1, null);
			ImageView imageView1 = new ImageView(image1);
			imageView1.setFitHeight(153);
			imageView1.setPreserveRatio(true);
			imageView1.setX(97);
			imageView1.setY(212);
			
			BufferedImage bufferedImage2 = ImageIO.read(new File("../data/graphe2.jpg"));
			Image image2 = SwingFXUtils.toFXImage(bufferedImage2, null);
			ImageView imageView2 = new ImageView(image2);
			imageView2.setFitHeight(153);
			imageView2.setPreserveRatio(true);
			imageView2.setX(97);
			imageView2.setY(380);
			
			BufferedImage bufferedImage3 = ImageIO.read(new File("../data/graphe4.jpeg"));
			Image image3 = SwingFXUtils.toFXImage(bufferedImage3, null);
			ImageView imageView3 = new ImageView(image3);
			imageView3.setFitHeight(153);
			imageView3.setPreserveRatio(true);
			imageView3.setX(97);
			imageView3.setY(548);
			
			BufferedImage bufferedImage4 = ImageIO.read(new File("../data/graphe3.jpg"));
			Image image4 = SwingFXUtils.toFXImage(bufferedImage4, null);
			ImageView imageView4 = new ImageView(image4);
			imageView4.setFitHeight(489);
			imageView4.setPreserveRatio(true);
			imageView4.setX(306);
			imageView4.setY(212);
			
			//Changement d'image
			EventHandler<MouseEvent> imageClickHandler1 = event -> {
				Image clickedImage = imageView1.getImage();
				imageView1.setImage(imageView4.getImage());
				imageView4.setImage(clickedImage);
			};
			imageView1.setOnMouseClicked(imageClickHandler1);
			
			EventHandler<MouseEvent> imageClickHandler2 = event -> {
				Image clickedImage = imageView2.getImage();
				imageView2.setImage(imageView4.getImage());
				imageView4.setImage(clickedImage);
			};
			imageView2.setOnMouseClicked(imageClickHandler2);
			
			EventHandler<MouseEvent> imageClickHandler3 = event -> {
				Image clickedImage = imageView3.getImage();
				imageView3.setImage(imageView4.getImage());
				imageView4.setImage(clickedImage);
			};
			imageView3.setOnMouseClicked(imageClickHandler3);
			
			layout.getChildren().addAll(imageView1, imageView2, imageView3, imageView4);
        } catch (IOException e) {
			e.printStackTrace();
		}
		
        Scene scene = new Scene(layout, 1024, 768);
		changeScene(scene);
	}
	
	//Connexion
	public void showConnexionPage() {
		setHeader();
        Pane layout = new Pane();
        
        Text titre = new Text("Connexion");
        titre.setX(512);
		titre.setY(130);
		titre.setStyle("-fx-fill: #f9f7f0;");
		titre.setFont(fontTitre);
		
		TextField mail = new TextField();
		mail.setPromptText("Adresse mail");
		mail.setPrefWidth(200); 
		mail.setStyle("-fx-background-color: #f9f7f0;");
		
		PasswordField mdp = new PasswordField();
		mdp.setPromptText("Mot de passe");
		mdp.setPrefWidth(200); 
		mdp.setStyle("-fx-background-color: #f9f7f0;");
		
		Button valider = new Button("Valider");
		valider.setPrefWidth(150);
        valider.setPrefHeight(50);
		valider.setOnAction(event -> {
			String email = mail.getText();
			String password = mdp.getText();

			if (email.endsWith("@nantes.fr") && password.equals("please20")) {
				connected = true;
				showMainPage();
			} else {
				showConnexionPage();
			}
        });
		valider.setStyle("-fx-text-fill: #f9f7f0; -fx-background-color: #072a40; -fx-background-radius: 30px;");
		valider.setFont(fontMoyen);
		
		VBox fieldBox = new VBox(20);
		fieldBox.setAlignment(Pos.BOTTOM_CENTER);
		fieldBox.setPadding(new Insets(15));
		fieldBox.getChildren().addAll(mail, mdp);
		
		VBox formulaire = new VBox(100);
		formulaire.setPadding(new Insets(15));
        formulaire.setAlignment(Pos.CENTER);
        formulaire.getChildren().addAll(titre, fieldBox, valider);
        VBox.setVgrow(formulaire, javafx.scene.layout.Priority.ALWAYS);
        formulaire.setStyle("-fx-background-color: #178ca4; -fx-background-radius: 30px;");
        formulaire.setLayoutX(312);
		formulaire.setLayoutY(155);
		formulaire.setPrefWidth(400);
        formulaire.setPrefHeight(533);
        
        //Background
        try {
			BufferedImage bufferedImage = ImageIO.read(new File("../data/background.jpg"));
			Image image = SwingFXUtils.toFXImage(bufferedImage, null);
			ImageView imageView = new ImageView(image);
			imageView.setFitWidth(layout.getWidth());
			imageView.setFitHeight(layout.getHeight());
			imageView.setY(24);
			layout.getChildren().addAll(imageView, this.header, formulaire);
        } catch (IOException e) {
			e.printStackTrace();
		}
		
        Scene scene = new Scene(layout, 1024, 768);
		changeScene(scene);
	}
}
