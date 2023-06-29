import java.sql.*;
import java.util.ArrayList;

import modele.*;

public class MySQLAccess {
    private static final String URL = "jdbc:mysql://localhost:3306/sae_velo";
    private static final String USERNAME = "admin";
    private static final String PASSWORD = "mdp_admin";

    /**
     * Récupère la liste des quartiers à partir de la table Quartier de la base de données.
     *
     * @return Une liste d'objets Quartier contenant les informations des quartiers.
     */
    public ArrayList<Quartier> implementQuartier(){
        ArrayList<Quartier> listeQuartier = new ArrayList<Quartier>();
        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD)) {
            //System.out.println("connection reussie ! ");

            String query = "SELECT * FROM Quartier";
            ResultSet results;

            Statement stmt = connection.createStatement();

	        results = stmt.executeQuery(query);

            ResultSetMetaData resultMeta = results.getMetaData();
            int nbColumns = resultMeta.getColumnCount();

             while (results.next()) {
                for (int i = 1; i <= nbColumns; i=i+3) {
                    if (i > 1) System.out.print(",  ");
                    //recuperation des informations du Quartier 
                    String columnValue = results.getString(i)+","+results.getString(i+1)+","+results.getString(i+2);
                    //System.out.println(columnValue);
                    String[] tmp = columnValue.split(",");
                    //System.out.println(tmp.length +" "+ tmp[0]);
                    Quartier q1 = new Quartier(Integer.parseInt(tmp[0]),tmp[1],Double.parseDouble(tmp[2]));
                    listeQuartier.add(q1);
                    System.out.println(q1.toString());
                    //System.out.println(columnContent);
                    
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listeQuartier;
    }

     /**
     * Met à jour les informations d'un quartier dans la table Quartier de la base de données.
     *
     * @param q Le quartier à mettre à jour.
     */
    public void updateQuartier(Quartier q) {
        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD)) {
            PreparedStatement commandsql = connection.prepareStatement("UPDATE Quartier SET nomQuartier = ?, longueurPisteVelo = ? WHERE idQuartier = ?");
            commandsql.setString(1, q.getNom());
            commandsql.setDouble(2, q.getLgPisteCyclable());
            commandsql.setInt(3, q.getId());
            commandsql.executeUpdate();
            //this.implementQuartier();
        } catch (SQLException e) {
            System.out.println("Erreur : " + e.getMessage());
            System.out.println("updateQuartier : erreur lors de la mise a jour des données");
        }
    }

    /**
     * Supprime un quartier de la table Quartier de la base de données.
     *
     * @param q Le quartier à supprimer.
     */
    public void deleteQuartier(Quartier q) {
        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD)) {
            //System.out.println("Connexion réussie !");
            PreparedStatement commandsql = connection.prepareStatement("DELETE FROM Quartier WHERE idQuartier = ?");
            commandsql.setInt(1, q.getId());
            commandsql.executeUpdate();
            //this.implementQuartier();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("deleteQuartier : Erreur lors de la suppression du quartier.");
        }
    }

    /**
    * Insère un objet Quartier dans la base de données.
    *
    * @param q l'objet Quartier à insérer
    */
    public void insertQuartier(Quartier q) {
        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD)) {
            //System.out.println("Connexion réussie !");
            PreparedStatement commandsql = connection.prepareStatement("INSERT INTO Quartier  VALUES (?,?,?)");
            commandsql.setInt(1, q.getId());
            commandsql.setString(2, q.getNom());
            commandsql.setDouble(3, q.getLgPisteCyclable());
            commandsql.executeUpdate();
            //this.implementQuartier();
        } catch (SQLException e) {
            System.out.println("Erreur : " + e.getMessage());
            System.out.println("insertQuartier : erreur lors de l'insertion");
        }
    }

    /**
    * Exécute un test sur les opérations de manipulation de la classe Quartier.
    */
    public static void testQuartier(){
        MySQLAccess access = new MySQLAccess();
        ArrayList<Quartier> listeQuartier = new ArrayList<Quartier>();
        listeQuartier = access.implementQuartier();
        Quartier q = new Quartier(50, "test", 0);
        access.insertQuartier(q);
        q.setNom("testUpdate");
        access.updateQuartier(q);
        access.deleteQuartier(q);
    }




    /**
     * Récupère la liste des Jour à partir de la table DateInfo de la base de données.
     *
     * @return Une liste d'objets Jour contenant les informations des jours.
     */
    public ArrayList<Jour> implementJour(){
        ArrayList<Jour> listeJour = new ArrayList<Jour>();
        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD)) {
            //System.out.println("connection reussie ! ");

            String query = "SELECT * FROM DateInfo";
            ResultSet results;

            Statement stmt = connection.createStatement();

	        results = stmt.executeQuery(query);

            ResultSetMetaData resultMeta = results.getMetaData();
            int nbColumns = resultMeta.getColumnCount();

             while (results.next()) {
                for (int i = 1; i <= nbColumns; i=i+4) {
                    if (i > 1) System.out.print(",  ");
                    //recuperation des informations du Quartier 
                    String columnValue = results.getString(i)+","+results.getString(i+1)+","+results.getString(i+2)+","+results.getString(i+3);
                    //System.out.println(columnValue);
                    String[] tmp = columnValue.split(",");
                    //System.out.println(tmp.length +" "+ tmp[0]);
                    Jour j1 = new Jour(tmp[0],Double.parseDouble(tmp[1]),tmp[2],tmp[3]);
                    listeJour.add(j1);
                    //System.out.println(j1.toString());
                    //System.out.println(columnContent);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("ImplementJour : erreur lors de l'implementation de la table DateInfo");
        }
        return listeJour;
    }

    /**
     * Met à jour les informations d'un Jour dans la table DateInfo de la base de données.
     *
     * @param j Le jour à mettre à jour.
     */
    public void updateJour(Jour j) {
        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD)) {
            PreparedStatement commandsql = connection.prepareStatement("UPDATE DateInfo SET tempMoy = ?, jour = ?, vacances = ? WHERE laDate= ?");
            commandsql.setDouble(1, j.getTemperatureMoyenne());
            commandsql.setString(2, j.getJour());
            commandsql.setString(3, j.getVacances());
            commandsql.setString(4, j.getDate());
            commandsql.executeUpdate();
            //this.implementJour();
        } catch (SQLException e) {
            System.out.println("updateJour : Erreur : " + e.getMessage());
        }
    }

    /**
     * Supprime un jour de la table DatInfo de la base de données.
     *
     * @param j Le jour à supprimer.
     */
    public void deleteJour(Jour j) {
        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD)) {
            //System.out.println("Connexion réussie !");
            PreparedStatement commandsql = connection.prepareStatement("DELETE FROM DateInfo WHERE laDate = ?");
            commandsql.setString(1, j.getDate());
            commandsql.executeUpdate();
            //this.implementJour();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("deleteJour : erreur lors de la suppression");
        }
    }

    /**
    * Insère un objet Jour dans la base de données.
    *
    * @param j l'objet Jour à insérer
    */
    public void insertJour(Jour j) {
        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD)) {
            //System.out.println("Connexion réussie !");
            PreparedStatement commandsql = connection.prepareStatement("INSERT INTO DateInfo VALUES (?,?,?,?)");
            commandsql.setString(1, j.getDate());
            commandsql.setDouble(2, j.getTemperatureMoyenne());
            commandsql.setString(3, j.getJour());
            commandsql.setString(4,j.getVacances());
            commandsql.executeUpdate();
            //this.implementJour();
        } catch (SQLException e) {
            System.out.println("Erreur : " + e.getMessage());
            System.out.println("insertJour : erreur lors de l'insertion");
        }
    }

    /**
    * Exécute un test sur les opérations de manipulation de la classe Jour.
    */
    public static void testJour(){
        MySQLAccess access = new MySQLAccess();
        ArrayList<Jour> listeJour = new ArrayList<Jour>();
        listeJour = access.implementJour();
        
        Jour j  = new Jour("3000-01-01",0,"Lundi",null);
        access.insertJour(j);
        
        j.setTemperatureMoyenne(15.5);
        access.updateJour(j);
        access.deleteJour(j);
        
    }


    /**
     * Récupère la liste des Compteurs à partir de la table Compteur de la base de données.
     *
     * @return Une liste d'objets Compteur contenant les informations des compteurs.
     */
    public ArrayList<Compteur> implementCompteur(){
        ArrayList<Compteur> listeCompteur = new ArrayList<Compteur>();
        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD)) {
            //System.out.println("connection reussie ! ");

            String query = "SELECT * FROM Compteur";
            ResultSet results;

            Statement stmt = connection.createStatement();

	        results = stmt.executeQuery(query);

            ResultSetMetaData resultMeta = results.getMetaData();
            int nbColumns = resultMeta.getColumnCount();

             while (results.next()) {
                for (int i = 1; i <= nbColumns; i=i+6) {
                    if (i > 1) System.out.print(",  ");
                    //recuperation des informations du Compteur
                    String columnValue = results.getString(i)+","+results.getString(i+1)+","+results.getString(i+2)+","+results.getString(i+3)+","+results.getString(i+4)+","+results.getString(i+5);
                    //System.out.println(columnValue);
                    String[] tmp = columnValue.split(",");
                    //System.out.println(tmp.length +" "+ tmp[0]);
                    int idQuartierTmp = 0;

                    if (!tmp[5].isEmpty() && !tmp[5].equals("null")) {
                        idQuartierTmp = Integer.parseInt(tmp[5]);
                    }
                    Compteur c1 = new Compteur (Integer.parseInt(tmp[0]),tmp[1],tmp[2],Double.parseDouble(tmp[3]),Double.parseDouble(tmp[4]),idQuartierTmp);
                    listeCompteur.add(c1);
                    //System.out.println(c1.toString());
                    //System.out.println(columnContent);
                    
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("implementCompteur: erreur lors de l'implementation de la table Compteur");
        }
        return listeCompteur;
    }

    /**
     * Met à jour les informations d'un compteur dans la table Compteur de la base de données.
     *
     * @param c Le compteur à mettre à jour.
     */
    public void updateCompteur(Compteur c) {
        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD)) {
            PreparedStatement commandsql = connection.prepareStatement("UPDATE Compteur SET nomCompteur = ?, sens = ?, coord_X = ?, coord_Y = ?, leQuartier = ? WHERE idCompteur= ?");
            commandsql.setString(1,c.getLibelle());
            commandsql.setString(2,c.getSens());
            commandsql.setDouble(3,c.getLatitude());
            commandsql.setDouble(4,c.getLongitude());
            commandsql.setInt(5, c.getIdQuartier());
            commandsql.setInt(6, c.getId());
            commandsql.executeUpdate();
            //this.implementCompteur();
        } catch (SQLException e) {
            System.out.println("updateCompteur : Erreur : " + e.getMessage());
        }
    }

    /**
     * Supprime un compteur de la table Compteur de la base de données.
     *
     * @param c Le compteur à supprimer.
     */
    public void deleteCompteur(Compteur c) {
        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD)) {
            //System.out.println("Connexion réussie !");
            PreparedStatement commandsql = connection.prepareStatement("DELETE FROM Compteur WHERE idCompteur = ?");
            commandsql.setInt(1, c.getId());
            commandsql.executeUpdate();
            //this.implementCompteur();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("deleteCompteur : erreur lors de la suppression");
        }
    }

    /**
    * Insère un objet Compteur dans la base de données.
    *
    * @param c l'objet compteur à insérer
    */
    public void insertCompteur(Compteur c) {
        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD)) {
            //System.out.println("Connexion réussie !");
            PreparedStatement commandsql = connection.prepareStatement("INSERT INTO Compteur VALUES (?,?,?,?,?,?)");
            commandsql.setInt(1,c.getId());
            commandsql.setString(2, c.getLibelle());
            commandsql.setString(3, c.getSens());
            commandsql.setDouble(4,c.getLatitude());
            commandsql.setDouble(5, c.getLongitude());
            commandsql.setInt(6,c.getIdQuartier());

            
            commandsql.executeUpdate();
            //this.implementCompteur();
        } catch (SQLException e) {
            System.out.println("Erreur : " + e.getMessage());
            System.out.println("insertCompteur : erreur lors de l'insertion");
        }
    }

    /**
    * Exécute un test sur les opérations de manipulation de la classe Compteur.
    */
    public static void testCompteur(){
        MySQLAccess access = new MySQLAccess();
        ArrayList<Compteur> listeCompteur = new ArrayList<Compteur>();
        listeCompteur = access.implementCompteur();
        
        Compteur c = new Compteur(0,"test","Sud", 0, 0, 0);
        access.insertCompteur(c);
        
        c.setLibelle("testApresChangement");
        access.updateCompteur(c);
        
        access.deleteCompteur(c);
        
    }

    /**
     * Récupère la liste des Rapports à partir de la table Comptage de la base de données.
     *
     * @return Une liste d'objets Rapport contenant les informations des rapports.
     */
    public ArrayList<Rapport> implementRapport() {
        ArrayList<Rapport> listeRapport = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD)) {
            //System.out.println("Connexion réussie !");

            String query = "SELECT * FROM Comptage";
            ResultSet results;
            Statement stmt = connection.createStatement();
            results = stmt.executeQuery(query);

            ResultSetMetaData resultMeta = results.getMetaData();
            int nbColumns = resultMeta.getColumnCount();

            while (results.next()) {
                int leCompteur = results.getInt(1);
                String leJour = results.getString(2);
                String presenceAnomalie = results.getString(3);

                int[] valeursHoraire = new int[24];
                for (int i = 4; i < nbColumns; i++) {
                    valeursHoraire[i - 4] = results.getInt(i);
                }

                Rapport rapport = new Rapport(leCompteur, leJour, valeursHoraire[0], valeursHoraire[1], valeursHoraire[2], valeursHoraire[3], 
                                            valeursHoraire[4], valeursHoraire[5], valeursHoraire[6], valeursHoraire[7], valeursHoraire[8], 
                                            valeursHoraire[9], valeursHoraire[10], valeursHoraire[11], valeursHoraire[12], valeursHoraire[13], 
                                            valeursHoraire[14], valeursHoraire[15], valeursHoraire[16], valeursHoraire[17], valeursHoraire[18], 
                                            valeursHoraire[19], valeursHoraire[20], valeursHoraire[21], valeursHoraire[22], valeursHoraire[23], 
                                            presenceAnomalie);
                
                listeRapport.add(rapport);
                //System.out.println(rapport.toString());
            }

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("implementRapport: Erreur lors de l'implémentation de la table Comptage");
        }

        return listeRapport;
    }

    /**
     * Met à jour les informations d'un rapport dans la table Comptage de la base de données.
     *
     * @param r Le rapport à mettre à jour.
     */
    public void updateRapport(Rapport rapport) {
        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD)) {
            PreparedStatement commandsql = connection.prepareStatement("UPDATE Comptage  SET h00 = ?, h01 = ?, h02 = ?, h03 = ?, h04 = ?, h05 = ?, h06 = ?, h07 = ?, h08 = ?, h09 = ?, h10 = ?, h11 = ?, h12 = ?, h13 = ?, h14 = ?, h15 = ?, h16 = ?, h17 = ?, h18 = ?, h19 = ?, h20 = ?, h21 = ?, h22 = ?, h23 = ?, presenceAnomalie = ? WHERE leCompteur = ? AND dateComptage = ?");
            
            for (int i = 1; i <= 24; i++) {
                commandsql.setInt(i, rapport.getPassage(i-1));
            }

            commandsql.setString(25, rapport.getPresenceAnomalie());
            commandsql.setInt(26, rapport.getCompteur());
            commandsql.setString(27, rapport.getJour());
            commandsql.executeUpdate();
            //this.implementRapport();
        } catch (SQLException e) {
            System.out.println("updateRapport: Erreur : " + e.getMessage());
        }
    }

    /**
     * Supprime un rapport de la table Comptage de la base de données.
     *
     * @param r Le rapport à supprimer.
     */
    public void deleteRapport(Rapport r) {
        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD)) {
            //System.out.println("Connexion réussie !");
            PreparedStatement commandsql = connection.prepareStatement("DELETE FROM Comptage  WHERE leCompteur = ? AND dateComptage = ?");
            commandsql.setInt(1, r.getCompteur());
            commandsql.setString(2, r.getJour());
            commandsql.executeUpdate();
            //this.implementRapport();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("deleteCompteur : erreur lors de la suppression");
        }
    }

    /**
    * Insère un objet Rapport dans la base de données.
    *
    * @param r l'objet Rapport à insérer
    */
    public void insertRapport(Rapport rapport) {
    try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD)) {
        PreparedStatement commandsql = connection.prepareStatement("INSERT INTO Comptage VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
        commandsql.setInt(1, rapport.getCompteur());
        commandsql.setString(2, rapport.getJour());
        for (int i = 0; i < 24; i++) {
            commandsql.setInt(i + 3, rapport.getPassage(i));
        }
        commandsql.setString(27, rapport.getPresenceAnomalie());

        commandsql.executeUpdate();
        //this.implementRapport();
    } catch (SQLException e) {
        System.out.println("insertRapport: Erreur : " + e.getMessage());
    }
}


    /**
    * Exécute un test sur les opérations de manipulation de la classe Rapport.
    */
    public static void testRapport(){
        MySQLAccess access = new MySQLAccess();
        ArrayList<Rapport> listeRapport = new ArrayList<Rapport>();
        //listeRapport = access.implementRapport();
        
        Rapport r = new Rapport(89, "2020-09-09", 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, null);
        access.insertRapport(r);
        
        r.setPassage(0,100);
        access.updateRapport(r);
        
        access.deleteRapport(r);
        
    }
    public static void main(String[] args) {
       //testQuartier();
       //testJour();
       //testCompteur();
        testRapport();

    }
}

