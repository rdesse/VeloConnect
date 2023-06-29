import java.sql.*;
import java.util.ArrayList;

import modele.*;

//PROBLEME heure plus petite que 10

public class SqlRequete {
    private static final String URL = "jdbc:mysql://localhost:3306/sae_velo";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "root";
    
    //REQUETES BDD
    public ArrayList<String> selectAll(){
		ArrayList<String> ret = new ArrayList<String>();
		try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD)) {
            String query = "SELECT * FROM affichage";
            
            PreparedStatement stmt = connection.prepareStatement(query);

	        ResultSet results = stmt.executeQuery();

            ResultSetMetaData resultMeta = results.getMetaData();
            int nbColumns = resultMeta.getColumnCount();

             while (results.next()) {
                StringBuilder columnValue = new StringBuilder();
				for (int i = 1; i <= nbColumns; i++) {
					if (i > 1) {
						columnValue.append(",");
					}
					columnValue.append(results.getString(i));
				}
				ret.add(columnValue.toString());
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ret;
	}
    
    public ArrayList<String> selectCompteur(int idCompteur){
		ArrayList<String> ret = new ArrayList<String>();
		try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD)) {
            String query = "SELECT * FROM affichage WHERE id = ?";
            
            PreparedStatement stmt = connection.prepareStatement(query);
			stmt.setInt(1, idCompteur);

	        ResultSet results = stmt.executeQuery();

            ResultSetMetaData resultMeta = results.getMetaData();
            int nbColumns = resultMeta.getColumnCount();

             while (results.next()) {
                StringBuilder columnValue = new StringBuilder();
				for (int i = 1; i <= nbColumns; i++) {
					if (i > 1) {
						columnValue.append(",");
					}
					columnValue.append(results.getString(i));
				}
				ret.add(columnValue.toString());
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ret;
	}
    
    public ArrayList<String> selectCompteurPeriode(String jour, int idCompteur){
		ArrayList<String> ret = new ArrayList<String>();
		try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD)) {
            String query = "SELECT idCompteur AS id, CONCAT(nomCompteur, 'vers ', sens) AS libelle, nomQuartier AS quartier, SUM(h00+h01+h02+h03+h04+h05+h06+h07+h08+h09+h10+h11+h12+h13+h14+h15+h16+h17+h18+h19+h20+h21+h22+h23) AS totalPassage FROM Comptage JOIN Compteur ON Comptage.leCompteur = Compteur.idCompteur JOIN Quartier ON Quartier.idQuartier = Compteur.leQuartier JOIN DateInfo ON DateInfo.laDate = Comptage.dateComptage WHERE UPPER(Jour) = ? AND idCompteur = ? GROUP BY idCompteur, nomCompteur, nomQuartier";
            
            PreparedStatement stmt = connection.prepareStatement(query);
			stmt.setString(1, jour.toUpperCase());
			stmt.setInt(2, idCompteur);

	        ResultSet results = stmt.executeQuery();

            ResultSetMetaData resultMeta = results.getMetaData();
            int nbColumns = resultMeta.getColumnCount();

             while (results.next()) {
                StringBuilder columnValue = new StringBuilder();
				for (int i = 1; i <= nbColumns; i++) {
					if (i > 1) {
						columnValue.append(",");
					}
					columnValue.append(results.getString(i));
				}
				ret.add(columnValue.toString());
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ret;
	}
	
	public ArrayList<String> selectCompteurVacances(String vacances, int idCompteur){
		ArrayList<String> ret = new ArrayList<String>();
		try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD)) {
            String query = "SELECT idCompteur AS id, CONCAT(nomCompteur, 'vers ', sens) AS libelle, nomQuartier AS quartier, SUM(h00+h01+h02+h03+h04+h05+h06+h07+h08+h09+h10+h11+h12+h13+h14+h15+h16+h17+h18+h19+h20+h21+h22+h23) AS totalPassage FROM Comptage JOIN Compteur ON Comptage.leCompteur = Compteur.idCompteur JOIN Quartier ON Quartier.idQuartier = Compteur.leQuartier JOIN DateInfo ON DateInfo.laDate = Comptage.dateComptage WHERE UPPER(vacances) = ? AND idCompteur = ? GROUP BY idCompteur, nomCompteur, nomQuartier";
            
            PreparedStatement stmt = connection.prepareStatement(query);
			stmt.setString(1, vacances.toUpperCase());
			stmt.setInt(2, idCompteur);

	        ResultSet results = stmt.executeQuery();

            ResultSetMetaData resultMeta = results.getMetaData();
            int nbColumns = resultMeta.getColumnCount();

             while (results.next()) {
                StringBuilder columnValue = new StringBuilder();
				for (int i = 1; i <= nbColumns; i++) {
					if (i > 1) {
						columnValue.append(",");
					}
					columnValue.append(results.getString(i));
				}
				ret.add(columnValue.toString());
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ret;
	}
	
	public ArrayList<String> selectCompteurDate(int idCompteur, String date){
		ArrayList<String> ret = new ArrayList<String>();
		try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD)) {
            String query = "SELECT idCompteur AS id, CONCAT(nomCompteur, 'vers ', sens) AS libelle, nomQuartier AS quartier, SUM(h00+h01+h02+h03+h04+h05+h06+h07+h08+h09+h10+h11+h12+h13+h14+h15+h16+h17+h18+h19+h20+h21+h22+h23) AS totalPassage FROM Comptage JOIN Compteur ON Comptage.leCompteur = Compteur.idCompteur JOIN Quartier ON Quartier.idQuartier = Compteur.leQuartier WHERE idCompteur = ? AND dateComptage = ?";
            
            PreparedStatement stmt = connection.prepareStatement(query);
			stmt.setInt(1, idCompteur);
			stmt.setString(2, date);

	        ResultSet results = stmt.executeQuery();

            ResultSetMetaData resultMeta = results.getMetaData();
            int nbColumns = resultMeta.getColumnCount();

             while (results.next()) {
                StringBuilder columnValue = new StringBuilder();
				for (int i = 1; i <= nbColumns; i++) {
					if (i > 1) {
						columnValue.append(",");
					}
					columnValue.append(results.getString(i));
				}
				ret.add(columnValue.toString());
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ret;
	}
	
	public ArrayList<String> selectCompteurHeure(int heure, int idCompteur){
		ArrayList<String> ret = new ArrayList<String>();
		try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD)) {
            String query;
            if(heure<10){
				query = "SELECT idCompteur AS id, CONCAT(nomCompteur, 'vers ', sens) AS libelle, nomQuartier AS quartier, SUM(h0?) AS totalPassage FROM Comptage JOIN Compteur ON Comptage.leCompteur = Compteur.idCompteur JOIN Quartier ON Quartier.idQuartier = Compteur.leQuartier WHERE idCompteur = ?";
			}else{
				query = "SELECT idCompteur AS id, CONCAT(nomCompteur, 'vers ', sens) AS libelle, nomQuartier AS quartier, SUM(h?) AS totalPassage FROM Comptage JOIN Compteur ON Comptage.leCompteur = Compteur.idCompteur JOIN Quartier ON Quartier.idQuartier = Compteur.leQuartier WHERE idCompteur = ?";
			}
            
            PreparedStatement stmt = connection.prepareStatement(query);
			stmt.setInt(1, heure);
			stmt.setInt(2, idCompteur);

	        ResultSet results = stmt.executeQuery();

            ResultSetMetaData resultMeta = results.getMetaData();
            int nbColumns = resultMeta.getColumnCount();

             while (results.next()) {
                StringBuilder columnValue = new StringBuilder();
				for (int i = 1; i <= nbColumns; i++) {
					if (i > 1) {
						columnValue.append(",");
					}
					columnValue.append(results.getString(i));
				}
				ret.add(columnValue.toString());
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ret;
	}
    
    public ArrayList<String> selectCompteurPeriodeVacances(String jour, String vacances, int idCompteur){
		ArrayList<String> ret = new ArrayList<String>();
		try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD)) {
            String query = "SELECT idCompteur AS id, CONCAT(nomCompteur, 'vers ', sens) AS libelle, nomQuartier AS quartier, SUM(h00+h01+h02+h03+h04+h05+h06+h07+h08+h09+h10+h11+h12+h13+h14+h15+h16+h17+h18+h19+h20+h21+h22+h23) AS totalPassage FROM Comptage JOIN Compteur ON Comptage.leCompteur = Compteur.idCompteur JOIN Quartier ON Quartier.idQuartier = Compteur.leQuartier JOIN DateInfo ON DateInfo.laDate = Comptage.dateComptage WHERE UPPER(Jour) = ? AND UPPER(vacances) = ? AND idCompteur = ? GROUP BY idCompteur, nomCompteur, nomQuartier";
            
            PreparedStatement stmt = connection.prepareStatement(query);
			stmt.setString(1, jour.toUpperCase());
			stmt.setString(2, vacances.toUpperCase());
			stmt.setInt(3, idCompteur);

	        ResultSet results = stmt.executeQuery();

            ResultSetMetaData resultMeta = results.getMetaData();
            int nbColumns = resultMeta.getColumnCount();

             while (results.next()) {
                StringBuilder columnValue = new StringBuilder();
				for (int i = 1; i <= nbColumns; i++) {
					if (i > 1) {
						columnValue.append(",");
					}
					columnValue.append(results.getString(i));
				}
				ret.add(columnValue.toString());
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ret;
	}
    
    public ArrayList<String> selectCompteurPeriodeHeure(int heure, String jour, int idCompteur){
		ArrayList<String> ret = new ArrayList<String>();
		try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD)) {
            String query;
            if(heure<10){
				query = "SELECT idCompteur AS id, CONCAT(nomCompteur, 'vers ', sens) AS libelle, nomQuartier AS quartier, SUM(h0?) AS totalPassage FROM Comptage JOIN Compteur ON Comptage.leCompteur = Compteur.idCompteur JOIN Quartier ON Quartier.idQuartier = Compteur.leQuartier JOIN DateInfo ON DateInfo.laDate = Comptage.dateComptage WHERE UPPER(Jour) = ? AND idCompteur = ? GROUP BY idCompteur, nomCompteur, nomQuartier";
			}else{
				query = "SELECT idCompteur AS id, CONCAT(nomCompteur, 'vers ', sens) AS libelle, nomQuartier AS quartier, SUM(h?) AS totalPassage FROM Comptage JOIN Compteur ON Comptage.leCompteur = Compteur.idCompteur JOIN Quartier ON Quartier.idQuartier = Compteur.leQuartier JOIN DateInfo ON DateInfo.laDate = Comptage.dateComptage WHERE UPPER(Jour) = ? AND idCompteur = ? GROUP BY idCompteur, nomCompteur, nomQuartier";
			}
            
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setInt(1, heure);
			stmt.setString(2, jour.toUpperCase());
			stmt.setInt(3, idCompteur);

	        ResultSet results = stmt.executeQuery();

            ResultSetMetaData resultMeta = results.getMetaData();
            int nbColumns = resultMeta.getColumnCount();

             while (results.next()) {
                StringBuilder columnValue = new StringBuilder();
				for (int i = 1; i <= nbColumns; i++) {
					if (i > 1) {
						columnValue.append(",");
					}
					columnValue.append(results.getString(i));
				}
				ret.add(columnValue.toString());
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ret;
	}
	
	public ArrayList<String> selectCompteurVacancesHeure(int heure, String vacances, int idCompteur){
		ArrayList<String> ret = new ArrayList<String>();
		try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD)) {
            String query;
            if(heure<10){
				query = "SELECT idCompteur AS id, CONCAT(nomCompteur, 'vers ', sens) AS libelle, nomQuartier AS quartier, SUM(h0?) AS totalPassage FROM Comptage JOIN Compteur ON Comptage.leCompteur = Compteur.idCompteur JOIN Quartier ON Quartier.idQuartier = Compteur.leQuartier JOIN DateInfo ON DateInfo.laDate = Comptage.dateComptage WHERE UPPER(vacances) = ? AND idCompteur = ? GROUP BY idCompteur, nomCompteur, nomQuartier";
			}else{
				query = "SELECT idCompteur AS id, CONCAT(nomCompteur, 'vers ', sens) AS libelle, nomQuartier AS quartier, SUM(h?) AS totalPassage FROM Comptage JOIN Compteur ON Comptage.leCompteur = Compteur.idCompteur JOIN Quartier ON Quartier.idQuartier = Compteur.leQuartier JOIN DateInfo ON DateInfo.laDate = Comptage.dateComptage WHERE UPPER(vacances) = ? AND idCompteur = ? GROUP BY idCompteur, nomCompteur, nomQuartier";
			}
            
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setInt(1, heure);
			stmt.setString(2, vacances.toUpperCase());
			stmt.setInt(3, idCompteur);

	        ResultSet results = stmt.executeQuery();

            ResultSetMetaData resultMeta = results.getMetaData();
            int nbColumns = resultMeta.getColumnCount();

             while (results.next()) {
                StringBuilder columnValue = new StringBuilder();
				for (int i = 1; i <= nbColumns; i++) {
					if (i > 1) {
						columnValue.append(",");
					}
					columnValue.append(results.getString(i));
				}
				ret.add(columnValue.toString());
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ret;
	}
	
	public ArrayList<String> selectCompteurDateHeure(int heure, int idCompteur, String date){
		ArrayList<String> ret = new ArrayList<String>();
		try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD)) {
            String query;
            if(heure<10){
				query = "SELECT idCompteur AS id, CONCAT(nomCompteur, 'vers ', sens) AS libelle, nomQuartier AS quartier, SUM(h0?) AS totalPassage FROM Comptage JOIN Compteur ON Comptage.leCompteur = Compteur.idCompteur JOIN Quartier ON Quartier.idQuartier = Compteur.leQuartier WHERE idCompteur = ? AND dateComptage = ?";
			}else{
				query = "SELECT idCompteur AS id, CONCAT(nomCompteur, 'vers ', sens) AS libelle, nomQuartier AS quartier, SUM(h?) AS totalPassage FROM Comptage JOIN Compteur ON Comptage.leCompteur = Compteur.idCompteur JOIN Quartier ON Quartier.idQuartier = Compteur.leQuartier WHERE idCompteur = ? AND dateComptage = ?";
			}
            
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setInt(1, heure);
			stmt.setInt(2, idCompteur);
			stmt.setString(3, date);

	        ResultSet results = stmt.executeQuery();

            ResultSetMetaData resultMeta = results.getMetaData();
            int nbColumns = resultMeta.getColumnCount();

             while (results.next()) {
                StringBuilder columnValue = new StringBuilder();
				for (int i = 1; i <= nbColumns; i++) {
					if (i > 1) {
						columnValue.append(",");
					}
					columnValue.append(results.getString(i));
				}
				ret.add(columnValue.toString());
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ret;
	}
    
    public ArrayList<String> selectCompteurPeriodeVacancesHeure(int heure, String jour, String vacances, int idCompteur){
		ArrayList<String> ret = new ArrayList<String>();
		try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD)) {
            String query;
            if(heure<10){
				query = "SELECT idCompteur AS id, CONCAT(nomCompteur, 'vers ', sens) AS libelle, nomQuartier AS quartier, SUM(h0?) AS totalPassage FROM Comptage JOIN Compteur ON Comptage.leCompteur = Compteur.idCompteur JOIN Quartier ON Quartier.idQuartier = Compteur.leQuartier JOIN DateInfo ON DateInfo.laDate = Comptage.dateComptage WHERE UPPER(Jour) = ? AND UPPER(vacances) = ? AND idCompteur = ? GROUP BY idCompteur, nomCompteur, nomQuartier";
			}else{
				query = "SELECT idCompteur AS id, CONCAT(nomCompteur, 'vers ', sens) AS libelle, nomQuartier AS quartier, SUM(h?) AS totalPassage FROM Comptage JOIN Compteur ON Comptage.leCompteur = Compteur.idCompteur JOIN Quartier ON Quartier.idQuartier = Compteur.leQuartier JOIN DateInfo ON DateInfo.laDate = Comptage.dateComptage WHERE UPPER(Jour) = ? AND UPPER(vacances) = ? AND idCompteur = ? GROUP BY idCompteur, nomCompteur, nomQuartier";
			}
            
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setInt(1, heure);
			stmt.setString(2, jour.toUpperCase());
			stmt.setString(3, vacances.toUpperCase());
			stmt.setInt(4, idCompteur);

	        ResultSet results = stmt.executeQuery();

            ResultSetMetaData resultMeta = results.getMetaData();
            int nbColumns = resultMeta.getColumnCount();

             while (results.next()) {
                StringBuilder columnValue = new StringBuilder();
				for (int i = 1; i <= nbColumns; i++) {
					if (i > 1) {
						columnValue.append(",");
					}
					columnValue.append(results.getString(i));
				}
				ret.add(columnValue.toString());
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ret;
	}
    
    public ArrayList<String> selectQuartier(String nomQuartier){
		ArrayList<String> ret = new ArrayList<String>();
		try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD)) {
            String query = "SELECT * FROM affichage WHERE quartier = ?";
            
            PreparedStatement stmt = connection.prepareStatement(query);
			stmt.setString(1, nomQuartier);

	        ResultSet results = stmt.executeQuery();

            ResultSetMetaData resultMeta = results.getMetaData();
            int nbColumns = resultMeta.getColumnCount();

             while (results.next()) {
                StringBuilder columnValue = new StringBuilder();
				for (int i = 1; i <= nbColumns; i++) {
					if (i > 1) {
						columnValue.append(",");
					}
					columnValue.append(results.getString(i));
				}
				ret.add(columnValue.toString());
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ret;
	}
	
	public ArrayList<String> selectQuartierPeriode(String periode, String nomQuartier){
		ArrayList<String> ret = new ArrayList<String>();
		try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD)) {
            String query = "SELECT idCompteur AS id, CONCAT(nomCompteur, 'vers ', sens) AS libelle, nomQuartier AS quartier, SUM(h00+h01+h02+h03+h04+h05+h06+h07+h08+h09+h10+h11+h12+h13+h14+h15+h16+h17+h18+h19+h20+h21+h22+h23) AS totalPassage FROM Comptage JOIN Compteur ON Comptage.leCompteur = Compteur.idCompteur JOIN Quartier ON Quartier.idQuartier = Compteur.leQuartier JOIN DateInfo ON DateInfo.laDate = Comptage.dateComptage WHERE UPPER(Jour) = ? AND nomQuartier = ? GROUP BY idCompteur, nomCompteur, nomQuartier";
            
            PreparedStatement stmt = connection.prepareStatement(query);
			stmt.setString(1, periode.toUpperCase());
			stmt.setString(2, nomQuartier);

	        ResultSet results = stmt.executeQuery();

            ResultSetMetaData resultMeta = results.getMetaData();
            int nbColumns = resultMeta.getColumnCount();

             while (results.next()) {
                StringBuilder columnValue = new StringBuilder();
				for (int i = 1; i <= nbColumns; i++) {
					if (i > 1) {
						columnValue.append(",");
					}
					columnValue.append(results.getString(i));
				}
				ret.add(columnValue.toString());
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ret;
	}
	
	public ArrayList<String> selectQuartierVacances(String vacances, String nomQuartier){
		ArrayList<String> ret = new ArrayList<String>();
		try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD)) {
            String query = "SELECT idCompteur AS id, CONCAT(nomCompteur, 'vers ', sens) AS libelle, nomQuartier AS quartier, SUM(h00+h01+h02+h03+h04+h05+h06+h07+h08+h09+h10+h11+h12+h13+h14+h15+h16+h17+h18+h19+h20+h21+h22+h23) AS totalPassage FROM Comptage JOIN Compteur ON Comptage.leCompteur = Compteur.idCompteur JOIN Quartier ON Quartier.idQuartier = Compteur.leQuartier JOIN DateInfo ON DateInfo.laDate = Comptage.dateComptage WHERE UPPER(vacances) = ? AND nomQuartier = ? GROUP BY idCompteur, nomCompteur, nomQuartier";
            
            PreparedStatement stmt = connection.prepareStatement(query);
			stmt.setString(1, vacances.toUpperCase());
			stmt.setString(2, nomQuartier);

	        ResultSet results = stmt.executeQuery();

            ResultSetMetaData resultMeta = results.getMetaData();
            int nbColumns = resultMeta.getColumnCount();

             while (results.next()) {
                StringBuilder columnValue = new StringBuilder();
				for (int i = 1; i <= nbColumns; i++) {
					if (i > 1) {
						columnValue.append(",");
					}
					columnValue.append(results.getString(i));
				}
				ret.add(columnValue.toString());
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ret;
	}
	
	public ArrayList<String> selectQuartierDate(String nomQuartier, String date){
		ArrayList<String> ret = new ArrayList<String>();
		try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD)) {
            String query = "SELECT idCompteur AS id, CONCAT(nomCompteur, 'vers ', sens) AS libelle, nomQuartier AS quartier, SUM(h00+h01+h02+h03+h04+h05+h06+h07+h08+h09+h10+h11+h12+h13+h14+h15+h16+h17+h18+h19+h20+h21+h22+h23) AS totalPassage FROM Comptage JOIN Compteur ON Comptage.leCompteur = Compteur.idCompteur JOIN Quartier ON Quartier.idQuartier = Compteur.leQuartier WHERE nomQuartier = ? AND dateComptage = ? GROUP BY idCompteur, nomCompteur, nomQuartier";
            
            PreparedStatement stmt = connection.prepareStatement(query);
			stmt.setString(1, nomQuartier);
			stmt.setString(2, date);

	        ResultSet results = stmt.executeQuery();

            ResultSetMetaData resultMeta = results.getMetaData();
            int nbColumns = resultMeta.getColumnCount();

             while (results.next()) {
                StringBuilder columnValue = new StringBuilder();
				for (int i = 1; i <= nbColumns; i++) {
					if (i > 1) {
						columnValue.append(",");
					}
					columnValue.append(results.getString(i));
				}
				ret.add(columnValue.toString());
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ret;
	}
	
	public ArrayList<String> selectQuartierHeure(int heure, String nomQuartier){
		ArrayList<String> ret = new ArrayList<String>();
		try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD)) {
            String query;
            if(heure<10){
				query = "SELECT idCompteur AS id, CONCAT(nomCompteur, 'vers ', sens) AS libelle, nomQuartier AS quartier, SUM(h0?) AS totalPassage FROM Comptage JOIN Compteur ON Comptage.leCompteur = Compteur.idCompteur JOIN Quartier ON Quartier.idQuartier = Compteur.leQuartier WHERE nomQuartier = ? GROUP BY idCompteur, nomCompteur, nomQuartier";
			}else{
				query = "SELECT idCompteur AS id, CONCAT(nomCompteur, 'vers ', sens) AS libelle, nomQuartier AS quartier, SUM(h?) AS totalPassage FROM Comptage JOIN Compteur ON Comptage.leCompteur = Compteur.idCompteur JOIN Quartier ON Quartier.idQuartier = Compteur.leQuartier WHERE nomQuartier = ? GROUP BY idCompteur, nomCompteur, nomQuartier";
			}
            
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setInt(1, heure);
			stmt.setString(2, nomQuartier);

	        ResultSet results = stmt.executeQuery();

            ResultSetMetaData resultMeta = results.getMetaData();
            int nbColumns = resultMeta.getColumnCount();

             while (results.next()) {
                StringBuilder columnValue = new StringBuilder();
				for (int i = 1; i <= nbColumns; i++) {
					if (i > 1) {
						columnValue.append(",");
					}
					columnValue.append(results.getString(i));
				}
				ret.add(columnValue.toString());
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ret;
	}
	
	public ArrayList<String> selectQuartierPeriodeVacances(String periode, String vacances, String nomQuartier){
		ArrayList<String> ret = new ArrayList<String>();
		try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD)) {
            String query = "SELECT idCompteur AS id, CONCAT(nomCompteur, 'vers ', sens) AS libelle, nomQuartier AS quartier, SUM(h00+h01+h02+h03+h04+h05+h06+h07+h08+h09+h10+h11+h12+h13+h14+h15+h16+h17+h18+h19+h20+h21+h22+h23) AS totalPassage FROM Comptage JOIN Compteur ON Comptage.leCompteur = Compteur.idCompteur JOIN Quartier ON Quartier.idQuartier = Compteur.leQuartier JOIN DateInfo ON DateInfo.laDate = Comptage.dateComptage WHERE UPPER(Jour) = ? AND UPPER(vacances) = ? AND nomQuartier = ? GROUP BY idCompteur, nomCompteur, nomQuartier";
            
            PreparedStatement stmt = connection.prepareStatement(query);
			stmt.setString(1, periode.toUpperCase());
			stmt.setString(2, vacances.toUpperCase());
			stmt.setString(3, nomQuartier);

	        ResultSet results = stmt.executeQuery();

            ResultSetMetaData resultMeta = results.getMetaData();
            int nbColumns = resultMeta.getColumnCount();

             while (results.next()) {
                StringBuilder columnValue = new StringBuilder();
				for (int i = 1; i <= nbColumns; i++) {
					if (i > 1) {
						columnValue.append(",");
					}
					columnValue.append(results.getString(i));
				}
				ret.add(columnValue.toString());
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ret;
	}
	
	public ArrayList<String> selectQuartierPeriodeHeure(int heure, String periode, String nomQuartier){
		ArrayList<String> ret = new ArrayList<String>();
		try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD)) {
            String query;
            if(heure<10){
				query = "SELECT idCompteur AS id, CONCAT(nomCompteur, 'vers ', sens) AS libelle, nomQuartier AS quartier, SUM(h0?) AS totalPassage FROM Comptage JOIN Compteur ON Comptage.leCompteur = Compteur.idCompteur JOIN Quartier ON Quartier.idQuartier = Compteur.leQuartier JOIN DateInfo ON DateInfo.laDate = Comptage.dateComptage WHERE UPPER(Jour) = ? AND nomQuartier = ? GROUP BY idCompteur, nomCompteur, nomQuartier";
			}else{
				query = "SELECT idCompteur AS id, CONCAT(nomCompteur, 'vers ', sens) AS libelle, nomQuartier AS quartier, SUM(h?) AS totalPassage FROM Comptage JOIN Compteur ON Comptage.leCompteur = Compteur.idCompteur JOIN Quartier ON Quartier.idQuartier = Compteur.leQuartier JOIN DateInfo ON DateInfo.laDate = Comptage.dateComptage WHERE UPPER(Jour) = ? AND nomQuartier = ? GROUP BY idCompteur, nomCompteur, nomQuartier";
			}
            
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setInt(1, heure);
			stmt.setString(2, periode.toUpperCase());
			stmt.setString(3, nomQuartier);

	        ResultSet results = stmt.executeQuery();

            ResultSetMetaData resultMeta = results.getMetaData();
            int nbColumns = resultMeta.getColumnCount();

             while (results.next()) {
                StringBuilder columnValue = new StringBuilder();
				for (int i = 1; i <= nbColumns; i++) {
					if (i > 1) {
						columnValue.append(",");
					}
					columnValue.append(results.getString(i));
				}
				ret.add(columnValue.toString());
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ret;
	}
	
	
	public ArrayList<String> selectQuartierVacancesHeure(int heure, String vacances, String nomQuartier){
		ArrayList<String> ret = new ArrayList<String>();
		try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD)) {
            String query;
            if(heure<10){
				query = "SELECT idCompteur AS id, CONCAT(nomCompteur, 'vers ', sens) AS libelle, nomQuartier AS quartier, SUM(h0?) AS totalPassage FROM Comptage JOIN Compteur ON Comptage.leCompteur = Compteur.idCompteur JOIN Quartier ON Quartier.idQuartier = Compteur.leQuartier JOIN DateInfo ON DateInfo.laDate = Comptage.dateComptage WHERE UPPER(vacances) = ? AND nomQuartier = ? GROUP BY idCompteur, nomCompteur, nomQuartier";
			}else{
				query = "SELECT idCompteur AS id, CONCAT(nomCompteur, 'vers ', sens) AS libelle, nomQuartier AS quartier, SUM(h?) AS totalPassage FROM Comptage JOIN Compteur ON Comptage.leCompteur = Compteur.idCompteur JOIN Quartier ON Quartier.idQuartier = Compteur.leQuartier JOIN DateInfo ON DateInfo.laDate = Comptage.dateComptage WHERE UPPER(vacances) = ? AND nomQuartier = ? GROUP BY idCompteur, nomCompteur, nomQuartier";
			}
            
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setInt(1, heure);
			stmt.setString(2, vacances.toUpperCase());
			stmt.setString(3, nomQuartier);

	        ResultSet results = stmt.executeQuery();

            ResultSetMetaData resultMeta = results.getMetaData();
            int nbColumns = resultMeta.getColumnCount();

             while (results.next()) {
                StringBuilder columnValue = new StringBuilder();
				for (int i = 1; i <= nbColumns; i++) {
					if (i > 1) {
						columnValue.append(",");
					}
					columnValue.append(results.getString(i));
				}
				ret.add(columnValue.toString());
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ret;
	}
	
	public ArrayList<String> selectQuartierDateHeure(int heure, String nomQuartier, String date){
		ArrayList<String> ret = new ArrayList<String>();
		try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD)) {
            String query;
            if(heure<10){
				query = "SELECT idCompteur AS id, CONCAT(nomCompteur, 'vers ', sens) AS libelle, nomQuartier AS quartier, SUM(h0?) AS totalPassage FROM Comptage JOIN Compteur ON Comptage.leCompteur = Compteur.idCompteur JOIN Quartier ON Quartier.idQuartier = Compteur.leQuartier WHERE nomQuartier = ? AND dateComptage = ? GROUP BY idCompteur, nomCompteur, nomQuartier";
			}else{
				query = "SELECT idCompteur AS id, CONCAT(nomCompteur, 'vers ', sens) AS libelle, nomQuartier AS quartier, SUM(h?) AS totalPassage FROM Comptage JOIN Compteur ON Comptage.leCompteur = Compteur.idCompteur JOIN Quartier ON Quartier.idQuartier = Compteur.leQuartier WHERE nomQuartier = ? AND dateComptage = ? GROUP BY idCompteur, nomCompteur, nomQuartier";
			}
            
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setInt(1, heure);
			stmt.setString(2, nomQuartier);
			stmt.setString(3, date);

	        ResultSet results = stmt.executeQuery();

            ResultSetMetaData resultMeta = results.getMetaData();
            int nbColumns = resultMeta.getColumnCount();

             while (results.next()) {
                StringBuilder columnValue = new StringBuilder();
				for (int i = 1; i <= nbColumns; i++) {
					if (i > 1) {
						columnValue.append(",");
					}
					columnValue.append(results.getString(i));
				}
				ret.add(columnValue.toString());
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ret;
	}
	
	public ArrayList<String> selectQuartierPeriodeVacancesHeure(int heure, String periode, String vacances, String nomQuartier){
		ArrayList<String> ret = new ArrayList<String>();
		try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD)) {
            String query;
            if(heure<10){
				query = "SELECT idCompteur AS id, CONCAT(nomCompteur, 'vers ', sens) AS libelle, nomQuartier AS quartier, SUM(h0?) AS totalPassage FROM Comptage JOIN Compteur ON Comptage.leCompteur = Compteur.idCompteur JOIN Quartier ON Quartier.idQuartier = Compteur.leQuartier JOIN DateInfo ON DateInfo.laDate = Comptage.dateComptage WHERE UPPER(Jour) = ? AND UPPER(vacances) = ? AND nomQuartier = ? GROUP BY idCompteur, nomCompteur, nomQuartier";
			}else{
				query = "SELECT idCompteur AS id, CONCAT(nomCompteur, 'vers ', sens) AS libelle, nomQuartier AS quartier, SUM(h?) AS totalPassage FROM Comptage JOIN Compteur ON Comptage.leCompteur = Compteur.idCompteur JOIN Quartier ON Quartier.idQuartier = Compteur.leQuartier JOIN DateInfo ON DateInfo.laDate = Comptage.dateComptage WHERE UPPER(Jour) = ? AND UPPER(vacances) = ? AND nomQuartier = ? GROUP BY idCompteur, nomCompteur, nomQuartier";
			}
            
            PreparedStatement stmt = connection.prepareStatement(query);
			stmt.setInt(1, heure);
			stmt.setString(2, periode.toUpperCase());
			stmt.setString(3, vacances.toUpperCase());
			stmt.setString(4, nomQuartier);

	        ResultSet results = stmt.executeQuery();

            ResultSetMetaData resultMeta = results.getMetaData();
            int nbColumns = resultMeta.getColumnCount();

             while (results.next()) {
                StringBuilder columnValue = new StringBuilder();
				for (int i = 1; i <= nbColumns; i++) {
					if (i > 1) {
						columnValue.append(",");
					}
					columnValue.append(results.getString(i));
				}
				ret.add(columnValue.toString());
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ret;
	}
	
	public ArrayList<String> selectPeriode(String periode){
		ArrayList<String> ret = new ArrayList<String>();
		try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD)) {
            String query = "SELECT idCompteur AS id, CONCAT(nomCompteur, 'vers ', sens) AS libelle, nomQuartier AS quartier, SUM(h00+h01+h02+h03+h04+h05+h06+h07+h08+h09+h10+h11+h12+h13+h14+h15+h16+h17+h18+h19+h20+h21+h22+h23) AS totalPassage FROM Comptage JOIN Compteur ON Comptage.leCompteur = Compteur.idCompteur JOIN Quartier ON Quartier.idQuartier = Compteur.leQuartier JOIN DateInfo ON DateInfo.laDate = Comptage.dateComptage WHERE UPPER(jour) = ? GROUP BY idCompteur, nomCompteur, nomQuartier";
            
            PreparedStatement stmt = connection.prepareStatement(query);
			stmt.setString(1, periode.toUpperCase());

	        ResultSet results = stmt.executeQuery();

            ResultSetMetaData resultMeta = results.getMetaData();
            int nbColumns = resultMeta.getColumnCount();

             while (results.next()) {
                StringBuilder columnValue = new StringBuilder();
				for (int i = 1; i <= nbColumns; i++) {
					if (i > 1) {
						columnValue.append(",");
					}
					columnValue.append(results.getString(i));
				}
				ret.add(columnValue.toString());
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ret;
	}
	
	public ArrayList<String> selectPeriodeVacances(String periode, String vacances){
		ArrayList<String> ret = new ArrayList<String>();
		try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD)) {
            String query = "SELECT idCompteur AS id, CONCAT(nomCompteur, 'vers ', sens) AS libelle, nomQuartier AS quartier, SUM(h00+h01+h02+h03+h04+h05+h06+h07+h08+h09+h10+h11+h12+h13+h14+h15+h16+h17+h18+h19+h20+h21+h22+h23) AS totalPassage FROM Comptage JOIN Compteur ON Comptage.leCompteur = Compteur.idCompteur JOIN Quartier ON Quartier.idQuartier = Compteur.leQuartier JOIN DateInfo ON DateInfo.laDate = Comptage.dateComptage WHERE UPPER(jour) = ? AND UPPER(vacances) = ? GROUP BY idCompteur, nomCompteur, nomQuartier";
            
            PreparedStatement stmt = connection.prepareStatement(query);
			stmt.setString(1, periode.toUpperCase());
			stmt.setString(2, vacances.toUpperCase());

	        ResultSet results = stmt.executeQuery();

            ResultSetMetaData resultMeta = results.getMetaData();
            int nbColumns = resultMeta.getColumnCount();

             while (results.next()) {
                StringBuilder columnValue = new StringBuilder();
				for (int i = 1; i <= nbColumns; i++) {
					if (i > 1) {
						columnValue.append(",");
					}
					columnValue.append(results.getString(i));
				}
				ret.add(columnValue.toString());
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ret;
	}
	
	public ArrayList<String> selectPeriodeHeure(int heure, String periode){
		ArrayList<String> ret = new ArrayList<String>();
		try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD)) {
            String query;
            if(heure<10){
				query = "SELECT idCompteur AS id, CONCAT(nomCompteur, 'vers ', sens) AS libelle, nomQuartier AS quartier, SUM(h0?) AS totalPassage FROM Comptage JOIN Compteur ON Comptage.leCompteur = Compteur.idCompteur JOIN Quartier ON Quartier.idQuartier = Compteur.leQuartier JOIN DateInfo ON DateInfo.laDate = Comptage.dateComptage WHERE UPPER(jour) = ? GROUP BY idCompteur, nomCompteur, nomQuartier";
			}else{
				query = "SELECT idCompteur AS id, CONCAT(nomCompteur, 'vers ', sens) AS libelle, nomQuartier AS quartier, SUM(h?) AS totalPassage FROM Comptage JOIN Compteur ON Comptage.leCompteur = Compteur.idCompteur JOIN Quartier ON Quartier.idQuartier = Compteur.leQuartier JOIN DateInfo ON DateInfo.laDate = Comptage.dateComptage WHERE UPPER(jour) = ? GROUP BY idCompteur, nomCompteur, nomQuartier";
			}
            
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setInt(1, heure);
			stmt.setString(2, periode.toUpperCase());

	        ResultSet results = stmt.executeQuery();

            ResultSetMetaData resultMeta = results.getMetaData();
            int nbColumns = resultMeta.getColumnCount();

             while (results.next()) {
                StringBuilder columnValue = new StringBuilder();
				for (int i = 1; i <= nbColumns; i++) {
					if (i > 1) {
						columnValue.append(",");
					}
					columnValue.append(results.getString(i));
				}
				ret.add(columnValue.toString());
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ret;
	}
	
	public ArrayList<String> selectPeriodeVacancesHeure(int heure, String periode, String vacances){
		ArrayList<String> ret = new ArrayList<String>();
		try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD)) {
            String query;
            if(heure<10){
				query = "SELECT idCompteur AS id, CONCAT(nomCompteur, 'vers ', sens) AS libelle, nomQuartier AS quartier, SUM(h0?) AS totalPassage FROM Comptage JOIN Compteur ON Comptage.leCompteur = Compteur.idCompteur JOIN Quartier ON Quartier.idQuartier = Compteur.leQuartier JOIN DateInfo ON DateInfo.laDate = Comptage.dateComptage WHERE UPPER(jour) = ? AND UPPER(vacances) = ? GROUP BY idCompteur, nomCompteur, nomQuartier";
			}else{
				query = "SELECT idCompteur AS id, CONCAT(nomCompteur, 'vers ', sens) AS libelle, nomQuartier AS quartier, SUM(h?) AS totalPassage FROM Comptage JOIN Compteur ON Comptage.leCompteur = Compteur.idCompteur JOIN Quartier ON Quartier.idQuartier = Compteur.leQuartier JOIN DateInfo ON DateInfo.laDate = Comptage.dateComptage WHERE UPPER(jour) = ? AND UPPER(vacances) = ? GROUP BY idCompteur, nomCompteur, nomQuartier";
			}
            
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setInt(1, heure);
			stmt.setString(2, periode.toUpperCase());
			stmt.setString(3, vacances.toUpperCase());

	        ResultSet results = stmt.executeQuery();

            ResultSetMetaData resultMeta = results.getMetaData();
            int nbColumns = resultMeta.getColumnCount();

             while (results.next()) {
                StringBuilder columnValue = new StringBuilder();
				for (int i = 1; i <= nbColumns; i++) {
					if (i > 1) {
						columnValue.append(",");
					}
					columnValue.append(results.getString(i));
				}
				ret.add(columnValue.toString());
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ret;
	}
	
	public ArrayList<String> selectVacances(String vacances){
		ArrayList<String> ret = new ArrayList<String>();
		try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD)) {
            String query = "SELECT idCompteur AS id, CONCAT(nomCompteur, 'vers ', sens) AS libelle, nomQuartier AS quartier, SUM(h00+h01+h02+h03+h04+h05+h06+h07+h08+h09+h10+h11+h12+h13+h14+h15+h16+h17+h18+h19+h20+h21+h22+h23) AS totalPassage FROM Comptage JOIN Compteur ON Comptage.leCompteur = Compteur.idCompteur JOIN Quartier ON Quartier.idQuartier = Compteur.leQuartier JOIN DateInfo ON DateInfo.laDate = Comptage.dateComptage WHERE UPPER(vacances)= ?  GROUP BY idCompteur, nomCompteur, nomQuartier";
            
            PreparedStatement stmt = connection.prepareStatement(query);
			stmt.setString(1, vacances.toUpperCase());

	        ResultSet results = stmt.executeQuery();

            ResultSetMetaData resultMeta = results.getMetaData();
            int nbColumns = resultMeta.getColumnCount();

             while (results.next()) {
                StringBuilder columnValue = new StringBuilder();
				for (int i = 1; i <= nbColumns; i++) {
					if (i > 1) {
						columnValue.append(",");
					}
					columnValue.append(results.getString(i));
				}
				ret.add(columnValue.toString());
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ret;
	}
	
	public ArrayList<String> selectVacancesHeure(int heure, String vacances){
		ArrayList<String> ret = new ArrayList<String>();
		try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD)) {
            String query;
            if(heure<10){
				query = "SELECT idCompteur AS id, CONCAT(nomCompteur, 'vers ', sens) AS libelle, nomQuartier AS quartier, SUM(h0?) AS totalPassage FROM Comptage JOIN Compteur ON Comptage.leCompteur = Compteur.idCompteur JOIN Quartier ON Quartier.idQuartier = Compteur.leQuartier JOIN DateInfo ON DateInfo.laDate = Comptage.dateComptage WHERE UPPER(vacances) = ?  GROUP BY idCompteur, nomCompteur, nomQuartier";
			}else{
				query = "SELECT idCompteur AS id, CONCAT(nomCompteur, 'vers ', sens) AS libelle, nomQuartier AS quartier, SUM(h?) AS totalPassage FROM Comptage JOIN Compteur ON Comptage.leCompteur = Compteur.idCompteur JOIN Quartier ON Quartier.idQuartier = Compteur.leQuartier JOIN DateInfo ON DateInfo.laDate = Comptage.dateComptage WHERE UPPER(vacances) = ?  GROUP BY idCompteur, nomCompteur, nomQuartier";
			}
            
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setInt(1, heure);
			stmt.setString(2, vacances.toUpperCase());

	        ResultSet results = stmt.executeQuery();

            ResultSetMetaData resultMeta = results.getMetaData();
            int nbColumns = resultMeta.getColumnCount();

             while (results.next()) {
                StringBuilder columnValue = new StringBuilder();
				for (int i = 1; i <= nbColumns; i++) {
					if (i > 1) {
						columnValue.append(",");
					}
					columnValue.append(results.getString(i));
				}
				ret.add(columnValue.toString());
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ret;
	}
	
	public ArrayList<String> selectDate(String date){
		ArrayList<String> ret = new ArrayList<String>();
		try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD)) {
            String query = "SELECT idCompteur AS id, CONCAT(nomCompteur, 'vers ', sens) AS libelle, nomQuartier AS quartier, SUM(h00+h01+h02+h03+h04+h05+h06+h07+h08+h09+h10+h11+h12+h13+h14+h15+h16+h17+h18+h19+h20+h21+h22+h23) AS totalPassage FROM Comptage JOIN Compteur ON Comptage.leCompteur = Compteur.idCompteur JOIN Quartier ON Quartier.idQuartier = Compteur.leQuartier WHERE dateComptage = ? GROUP BY idCompteur, nomCompteur, nomQuartier";
            
            PreparedStatement stmt = connection.prepareStatement(query);
			stmt.setString(1, date);

	        ResultSet results = stmt.executeQuery();

            ResultSetMetaData resultMeta = results.getMetaData();
            int nbColumns = resultMeta.getColumnCount();

             while (results.next()) {
                StringBuilder columnValue = new StringBuilder();
				for (int i = 1; i <= nbColumns; i++) {
					if (i > 1) {
						columnValue.append(",");
					}
					columnValue.append(results.getString(i));
				}
				ret.add(columnValue.toString());
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ret;
	}
	
	public ArrayList<String> selectDateHeure(int heure, String date){
		ArrayList<String> ret = new ArrayList<String>();
		try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD)) {
            String query;
            if(heure<10){
				query = "SELECT idCompteur AS id, CONCAT(nomCompteur, 'vers ', sens) AS libelle, nomQuartier AS quartier, SUM(h0?) AS totalPassage FROM Comptage JOIN Compteur ON Comptage.leCompteur = Compteur.idCompteur JOIN Quartier ON Quartier.idQuartier = Compteur.leQuartier WHERE dateComptage = ? GROUP BY idCompteur, nomCompteur, nomQuartier";
			}else{
				query = "SELECT idCompteur AS id, CONCAT(nomCompteur, 'vers ', sens) AS libelle, nomQuartier AS quartier, SUM(h?) AS totalPassage FROM Comptage JOIN Compteur ON Comptage.leCompteur = Compteur.idCompteur JOIN Quartier ON Quartier.idQuartier = Compteur.leQuartier WHERE dateComptage = ? GROUP BY idCompteur, nomCompteur, nomQuartier";
			}
            
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setInt(1, heure);
			stmt.setString(2, date);

	        ResultSet results = stmt.executeQuery();

            ResultSetMetaData resultMeta = results.getMetaData();
            int nbColumns = resultMeta.getColumnCount();

             while (results.next()) {
                StringBuilder columnValue = new StringBuilder();
				for (int i = 1; i <= nbColumns; i++) {
					if (i > 1) {
						columnValue.append(",");
					}
					columnValue.append(results.getString(i));
				}
				ret.add(columnValue.toString());
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ret;
	}
	
	public ArrayList<String> selectHeure(int heure){
		ArrayList<String> ret = new ArrayList<String>();
		try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD)) {
            String query;
            if(heure<10){
				query = "SELECT idCompteur AS id, CONCAT(nomCompteur, 'vers ', sens) AS libelle, nomQuartier AS quartier, SUM(h0?) AS totalPassage FROM Comptage JOIN Compteur ON Comptage.leCompteur = Compteur.idCompteur JOIN Quartier ON Quartier.idQuartier = Compteur.leQuartier GROUP BY idCompteur, nomCompteur, nomQuartier";
			}else{
				query = "SELECT idCompteur AS id, CONCAT(nomCompteur, 'vers ', sens) AS libelle, nomQuartier AS quartier, SUM(h?) AS totalPassage FROM Comptage JOIN Compteur ON Comptage.leCompteur = Compteur.idCompteur JOIN Quartier ON Quartier.idQuartier = Compteur.leQuartier GROUP BY idCompteur, nomCompteur, nomQuartier";
			}
            
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setInt(1, heure);

	        ResultSet results = stmt.executeQuery();

            ResultSetMetaData resultMeta = results.getMetaData();
            int nbColumns = resultMeta.getColumnCount();

             while (results.next()) {
                StringBuilder columnValue = new StringBuilder();
				for (int i = 1; i <= nbColumns; i++) {
					if (i > 1) {
						columnValue.append(",");
					}
					columnValue.append(results.getString(i));
				}
				ret.add(columnValue.toString());
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ret;
	}
	
	//REQUETE CARTE
	public ArrayList<String> carteQuartier(String nomQuartier){
		ArrayList<String> ret = new ArrayList<String>();
		try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD)) {
            String query = "SELECT idCompteur FROM Compteur JOIN Quartier ON Quartier.idQuartier = Compteur.leQuartier WHERE nomQuartier = ?";
            
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, nomQuartier);

	        ResultSet results = stmt.executeQuery();

            ResultSetMetaData resultMeta = results.getMetaData();
            int nbColumns = resultMeta.getColumnCount();

             while (results.next()) {
                StringBuilder columnValue = new StringBuilder();
				for (int i = 1; i <= nbColumns; i++) {
					if (i > 1) {
						columnValue.append(",");
					}
					columnValue.append(results.getString(i));
					
				}
				ret.add(columnValue.toString());
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ret;
	}
	
	public ArrayList<String> carteQuartierMax(String nomQuartier){
		ArrayList<String> ret = new ArrayList<String>();
		try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD)) {
            String query = "SELECT leCompteur FROM Comptage JOIN Compteur ON Comptage.leCompteur = Compteur.idCompteur JOIN Quartier ON Quartier.idQuartier = Compteur.leQuartier WHERE nomQuartier = ? GROUP BY leCompteur HAVING SUM(h00+h01+h02+h03+h04+h05+h06+h07+h08+h09+h10+h11+h12+h13+h14+h15+h16+h17+h18+h19+h20+h21+h22+h23) = (SELECT MAX(total) FROM (SELECT leCompteur, SUM(h00+h01+h02+h03+h04+h05+h06+h07+h08+h09+h10+h11+h12+h13+h14+h15+h16+h17+h18+h19+h20+h21+h22+h23) AS total FROM Comptage JOIN Compteur ON Comptage.leCompteur = Compteur.idCompteur JOIN Quartier ON Quartier.idQuartier = Compteur.leQuartier WHERE nomQuartier = ? GROUP BY leCompteur) AS test)";
            
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, nomQuartier);
            stmt.setString(2, nomQuartier);

	        ResultSet results = stmt.executeQuery();

            ResultSetMetaData resultMeta = results.getMetaData();
            int nbColumns = resultMeta.getColumnCount();

             while (results.next()) {
                StringBuilder columnValue = new StringBuilder();
				for (int i = 1; i <= nbColumns; i++) {
					if (i > 1) {
						columnValue.append(",");
					}
					columnValue.append(results.getString(i));
					
				}
				ret.add(columnValue.toString());
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ret;
	}
	
	public ArrayList<String> carteQuartierMin(String nomQuartier){
		ArrayList<String> ret = new ArrayList<String>();
		try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD)) {
            String query = "SELECT leCompteur FROM Comptage JOIN Compteur ON Comptage.leCompteur = Compteur.idCompteur JOIN Quartier ON Quartier.idQuartier = Compteur.leQuartier WHERE nomQuartier = ? GROUP BY leCompteur HAVING SUM(h00+h01+h02+h03+h04+h05+h06+h07+h08+h09+h10+h11+h12+h13+h14+h15+h16+h17+h18+h19+h20+h21+h22+h23) = (SELECT MIN(total) FROM (SELECT leCompteur, SUM(h00+h01+h02+h03+h04+h05+h06+h07+h08+h09+h10+h11+h12+h13+h14+h15+h16+h17+h18+h19+h20+h21+h22+h23) AS total FROM Comptage JOIN Compteur ON Comptage.leCompteur = Compteur.idCompteur JOIN Quartier ON Quartier.idQuartier = Compteur.leQuartier WHERE nomQuartier = ? GROUP BY leCompteur) AS test)";
            
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, nomQuartier);
            stmt.setString(2, nomQuartier);

	        ResultSet results = stmt.executeQuery();

            ResultSetMetaData resultMeta = results.getMetaData();
            int nbColumns = resultMeta.getColumnCount();

             while (results.next()) {
                StringBuilder columnValue = new StringBuilder();
				for (int i = 1; i <= nbColumns; i++) {
					if (i > 1) {
						columnValue.append(",");
					}
					columnValue.append(results.getString(i));
					
				}
				ret.add(columnValue.toString());
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ret;
	}
	
	public ArrayList<String> carteDateMax(String date){
		ArrayList<String> ret = new ArrayList<String>();
		try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD)) {
            String query = "SELECT leCompteur FROM Comptage JOIN DateInfo ON Comptage.dateComptage = DateInfo.laDate WHERE dateComptage = ? GROUP BY leCompteur HAVING SUM(h00+h01+h02+h03+h04+h05+h06+h07+h08+h09+h10+h11+h12+h13+h14+h15+h16+h17+h18+h19+h20+h21+h22+h23) = (SELECT MAX(total) FROM (SELECT leCompteur, SUM(h00+h01+h02+h03+h04+h05+h06+h07+h08+h09+h10+h11+h12+h13+h14+h15+h16+h17+h18+h19+h20+h21+h22+h23) AS total FROM Comptage JOIN DateInfo ON Comptage.dateComptage = DateInfo.laDate WHERE dateComptage = ? GROUP BY leCompteur) AS test)";
            
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, date);
            stmt.setString(2, date);

	        ResultSet results = stmt.executeQuery();

            ResultSetMetaData resultMeta = results.getMetaData();
            int nbColumns = resultMeta.getColumnCount();

             while (results.next()) {
                StringBuilder columnValue = new StringBuilder();
				for (int i = 1; i <= nbColumns; i++) {
					if (i > 1) {
						columnValue.append(",");
					}
					columnValue.append(results.getString(i));
					
				}
				ret.add(columnValue.toString());
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ret;
	}
	
	public ArrayList<String> carteDateMin(String date){
		ArrayList<String> ret = new ArrayList<String>();
		try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD)) {
            String query = "SELECT leCompteur FROM Comptage JOIN DateInfo ON Comptage.dateComptage = DateInfo.laDate WHERE dateComptage = ?  GROUP BY leCompteur HAVING SUM(h00+h01+h02+h03+h04+h05+h06+h07+h08+h09+h10+h11+h12+h13+h14+h15+h16+h17+h18+h19+h20+h21+h22+h23) = (SELECT MIN(total) FROM (SELECT leCompteur, SUM(h00+h01+h02+h03+h04+h05+h06+h07+h08+h09+h10+h11+h12+h13+h14+h15+h16+h17+h18+h19+h20+h21+h22+h23) AS total FROM Comptage JOIN DateInfo ON Comptage.dateComptage = DateInfo.laDate WHERE dateComptage = ? GROUP BY leCompteur) AS test)";
            
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, date);
            stmt.setString(2, date);

	        ResultSet results = stmt.executeQuery();

            ResultSetMetaData resultMeta = results.getMetaData();
            int nbColumns = resultMeta.getColumnCount();

             while (results.next()) {
                StringBuilder columnValue = new StringBuilder();
				for (int i = 1; i <= nbColumns; i++) {
					if (i > 1) {
						columnValue.append(",");
					}
					columnValue.append(results.getString(i));
					
				}
				ret.add(columnValue.toString());
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ret;
	}
	
	public ArrayList<String> carteMax(){
		ArrayList<String> ret = new ArrayList<String>();
		try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD)) {
            String query = "SELECT leCompteur FROM Comptage GROUP BY leCompteur HAVING SUM(h00+h01+h02+h03+h04+h05+h06+h07+h08+h09+h10+h11+h12+h13+h14+h15+h16+h17+h18+h19+h20+h21+h22+h23) = (SELECT MAX(total) FROM (SELECT leCompteur, SUM(h00+h01+h02+h03+h04+h05+h06+h07+h08+h09+h10+h11+h12+h13+h14+h15+h16+h17+h18+h19+h20+h21+h22+h23) AS total FROM Comptage GROUP BY leCompteur) AS test)";
            
            PreparedStatement stmt = connection.prepareStatement(query);

	        ResultSet results = stmt.executeQuery();

            ResultSetMetaData resultMeta = results.getMetaData();
            int nbColumns = resultMeta.getColumnCount();

             while (results.next()) {
                StringBuilder columnValue = new StringBuilder();
				for (int i = 1; i <= nbColumns; i++) {
					if (i > 1) {
						columnValue.append(",");
					}
					columnValue.append(results.getString(i));
					
				}
				ret.add(columnValue.toString());
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ret;
	}
	
	public ArrayList<String> carteMin(){
		ArrayList<String> ret = new ArrayList<String>();
		try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD)) {
            String query = "SELECT leCompteur FROM Comptage GROUP BY leCompteur HAVING SUM(h00+h01+h02+h03+h04+h05+h06+h07+h08+h09+h10+h11+h12+h13+h14+h15+h16+h17+h18+h19+h20+h21+h22+h23) = (SELECT MIN(total) FROM (SELECT leCompteur, SUM(h00+h01+h02+h03+h04+h05+h06+h07+h08+h09+h10+h11+h12+h13+h14+h15+h16+h17+h18+h19+h20+h21+h22+h23) AS total FROM Comptage GROUP BY leCompteur) AS test)";
            
            PreparedStatement stmt = connection.prepareStatement(query);

	        ResultSet results = stmt.executeQuery();

            ResultSetMetaData resultMeta = results.getMetaData();
            int nbColumns = resultMeta.getColumnCount();

             while (results.next()) {
                StringBuilder columnValue = new StringBuilder();
				for (int i = 1; i <= nbColumns; i++) {
					if (i > 1) {
						columnValue.append(",");
					}
					columnValue.append(results.getString(i));
					
				}
				ret.add(columnValue.toString());
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ret;
	}
	
	public ArrayList<String> carteQuartierDate(String date, String nomQuartier){
		ArrayList<String> ret = new ArrayList<String>();
		try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD)) {
            String query = "SELECT leCompteur FROM Comptage JOIN DateInfo ON Comptage.dateComptage = DateInfo.laDate JOIN Compteur ON Comptage.leCompteur = Compteur.idCompteur JOIN Quartier ON Quartier.idQuartier = Compteur.leQuartier WHERE dateComptage = ? AND nomQuartier = ? GROUP BY leCompteur";
            
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, date);
            stmt.setString(2, nomQuartier);
            
	        ResultSet results = stmt.executeQuery();

            ResultSetMetaData resultMeta = results.getMetaData();
            int nbColumns = resultMeta.getColumnCount();

             while (results.next()) {
                StringBuilder columnValue = new StringBuilder();
				for (int i = 1; i <= nbColumns; i++) {
					if (i > 1) {
						columnValue.append(",");
					}
					columnValue.append(results.getString(i));
					
				}
				ret.add(columnValue.toString());
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ret;
	}
	
	public ArrayList<String> carteQuartierDateMax(String date, String nomQuartier){
		ArrayList<String> ret = new ArrayList<String>();
		try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD)) {
            String query = "SELECT leCompteur FROM Comptage JOIN DateInfo ON Comptage.dateComptage = DateInfo.laDate JOIN Compteur ON Comptage.leCompteur = Compteur.idCompteur JOIN Quartier ON Quartier.idQuartier = Compteur.leQuartier WHERE dateComptage = ? AND nomQuartier = ? GROUP BY leCompteur HAVING SUM(h00+h01+h02+h03+h04+h05+h06+h07+h08+h09+h10+h11+h12+h13+h14+h15+h16+h17+h18+h19+h20+h21+h22+h23) = (SELECT MAX(total) FROM (SELECT leCompteur, SUM(h00+h01+h02+h03+h04+h05+h06+h07+h08+h09+h10+h11+h12+h13+h14+h15+h16+h17+h18+h19+h20+h21+h22+h23) AS total FROM Comptage JOIN DateInfo ON Comptage.dateComptage = DateInfo.laDate JOIN Compteur ON Comptage.leCompteur = Compteur.idCompteur JOIN Quartier ON Quartier.idQuartier = Compteur.leQuartier WHERE dateComptage = ? AND nomQuartier = ? GROUP BY leCompteur) AS test)";
            
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, date);
            stmt.setString(2, nomQuartier);
            stmt.setString(3, date);
            stmt.setString(4, nomQuartier);
            
	        ResultSet results = stmt.executeQuery();

            ResultSetMetaData resultMeta = results.getMetaData();
            int nbColumns = resultMeta.getColumnCount();

             while (results.next()) {
                StringBuilder columnValue = new StringBuilder();
				for (int i = 1; i <= nbColumns; i++) {
					if (i > 1) {
						columnValue.append(",");
					}
					columnValue.append(results.getString(i));
					
				}
				ret.add(columnValue.toString());
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ret;
	}
	
	public ArrayList<String> carteQuartierDateMin(String date, String nomQuartier){
		ArrayList<String> ret = new ArrayList<String>();
		try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD)) {
            String query = "SELECT leCompteur FROM Comptage JOIN DateInfo ON Comptage.dateComptage = DateInfo.laDate JOIN Compteur ON Comptage.leCompteur = Compteur.idCompteur JOIN Quartier ON Quartier.idQuartier = Compteur.leQuartier WHERE dateComptage = ? AND nomQuartier = ? GROUP BY leCompteur HAVING SUM(h00+h01+h02+h03+h04+h05+h06+h07+h08+h09+h10+h11+h12+h13+h14+h15+h16+h17+h18+h19+h20+h21+h22+h23) = (SELECT MIN(total) FROM (SELECT leCompteur, SUM(h00+h01+h02+h03+h04+h05+h06+h07+h08+h09+h10+h11+h12+h13+h14+h15+h16+h17+h18+h19+h20+h21+h22+h23) AS total FROM Comptage JOIN DateInfo ON Comptage.dateComptage = DateInfo.laDate JOIN Compteur ON Comptage.leCompteur = Compteur.idCompteur JOIN Quartier ON Quartier.idQuartier = Compteur.leQuartier WHERE dateComptage = ? AND nomQuartier = ? GROUP BY leCompteur) AS test)";
            
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, date);
            stmt.setString(2, nomQuartier);
            stmt.setString(3, date);
            stmt.setString(4, nomQuartier);
            
	        ResultSet results = stmt.executeQuery();

            ResultSetMetaData resultMeta = results.getMetaData();
            int nbColumns = resultMeta.getColumnCount();

             while (results.next()) {
                StringBuilder columnValue = new StringBuilder();
				for (int i = 1; i <= nbColumns; i++) {
					if (i > 1) {
						columnValue.append(",");
					}
					columnValue.append(results.getString(i));
					
				}
				ret.add(columnValue.toString());
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ret;
	}
	
	public ArrayList<String> carteDate(String date){
		ArrayList<String> ret = new ArrayList<String>();
		try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD)) {
            String query = "SELECT leCompteur FROM Comptage JOIN DateInfo ON Comptage.dateComptage = DateInfo.laDate WHERE dateComptage = ? GROUP BY leCompteur";
            
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, date);
            
	        ResultSet results = stmt.executeQuery();

            ResultSetMetaData resultMeta = results.getMetaData();
            int nbColumns = resultMeta.getColumnCount();

             while (results.next()) {
                StringBuilder columnValue = new StringBuilder();
				for (int i = 1; i <= nbColumns; i++) {
					if (i > 1) {
						columnValue.append(",");
					}
					columnValue.append(results.getString(i));
					
				}
				ret.add(columnValue.toString());
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ret;
	}
	
	public ArrayList<String> carteAll(){
		ArrayList<String> ret = new ArrayList<String>();
		try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD)) {
            String query = "SELECT leCompteur FROM Comptage GROUP BY leCompteur";
            
            PreparedStatement stmt = connection.prepareStatement(query);
            
	        ResultSet results = stmt.executeQuery();

            ResultSetMetaData resultMeta = results.getMetaData();
            int nbColumns = resultMeta.getColumnCount();

             while (results.next()) {
                StringBuilder columnValue = new StringBuilder();
				for (int i = 1; i <= nbColumns; i++) {
					if (i > 1) {
						columnValue.append(",");
					}
					columnValue.append(results.getString(i));
					
				}
				ret.add(columnValue.toString());
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ret;
	}
	
	//REQUETE POINT
	public ArrayList<String> point(int idCompteur){
		ArrayList<String> ret = new ArrayList<String>();
		try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD)) {
            String query = "SELECT CONCAT(nomCompteur, 'vers ', sens), coord_X, coord_Y, nomQuartier, SUM(h00+h01+h02+h03+h04+h05+h06+h07+h08+h09+h10+h11+h12+h13+h14+h15+h16+h17+h18+h19+h20+h21+h22+h23) AS total, min_jour, max_jour FROM Comptage JOIN Compteur ON Comptage.leCompteur = Compteur.idCompteur JOIN Quartier ON Quartier.idQuartier = Compteur.leQuartier JOIN vue_max_min ON vue_max_min.leCompteur = Compteur.idCompteur WHERE idCompteur = ?";
            
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setInt(1, idCompteur);
            
	        ResultSet results = stmt.executeQuery();

            ResultSetMetaData resultMeta = results.getMetaData();
            int nbColumns = resultMeta.getColumnCount();

             while (results.next()) {
                StringBuilder columnValue = new StringBuilder();
				for (int i = 1; i <= nbColumns; i++) {
					if (i > 1) {
						columnValue.append(",");
					}
					columnValue.append(results.getString(i));
					
				}
				ret.add(columnValue.toString());
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ret;
	}
	
	//REQUETE GRAPHIQUE
	public ArrayList<String> graphique(int idCompteur){
		ArrayList<String> ret = new ArrayList<String>();
		try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD)) {
            String query = "SELECT * FROM (SELECT SUM(h00+h01+h02+h03+h04+h05+h06+h07+h08+h09+h10+h11+h12+h13+h14+h15+h16+h17+h18+h19+h20+h21+h22+h23) AS total_mois FROM Comptage WHERE leCompteur = ? GROUP BY EXTRACT(YEAR FROM dateComptage), EXTRACT(MONTH FROM dateComptage) ORDER BY EXTRACT(YEAR FROM dateComptage) DESC, EXTRACT(MONTH FROM dateComptage) DESC) AS test LIMIT 12";
            
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setInt(1, idCompteur);
            
	        ResultSet results = stmt.executeQuery();

            ResultSetMetaData resultMeta = results.getMetaData();
            int nbColumns = resultMeta.getColumnCount();

             while (results.next()) {
                StringBuilder columnValue = new StringBuilder();
				for (int i = 1; i <= nbColumns; i++) {
					if (i > 1) {
						columnValue.append(",");
					}
					columnValue.append(results.getString(i));
					
				}
				ret.add(columnValue.toString());
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ret;
	}
}
