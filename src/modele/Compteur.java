/**
 * Classe Compteur
 *
 * Cette classe représente un compteur avec un identifiant, un libellé, une direction,
 * une latitude et une longitude.
 * 
 * @author A.Bellec R.Desse G.Roger E.Rousseau
 */
package modele;



public class Compteur{
	private int id;
	private String libelle;
	private String sens;
	private double latitude;
	private double longitude;
	private int idQuartier;
	
	/**
     * Constructeur de la classe Compteur.
     *
     * @param id l'identifiant du compteur
     * @param nomCompteur le nom du compteur avec le libellé et la direction
     * @param latitude la latitude du compteur
     * @param longitude la longitude du compteur
     */
	public Compteur(int id, String libelle,String sens, double latitude, double longitude, int idQuartier ){
		this.id = id;
		this.latitude = latitude;
		this.longitude = longitude;
		if(libelle == null || libelle.length() == 0 ){
			this.libelle = "Default";
		}else{
			this.libelle = libelle;
		}

		if(sens == null || sens.length() == 0){
			this.sens = "Default";
		}
		else{
			this.sens = sens;
		}
		this.idQuartier = idQuartier;
	}
	
	/**
     * Retourne l'identifiant du compteur.
     *
     * @return l'identifiant du compteur
     */
	public int getId(){
		return this.id;
	}
	
	/**
     * Modifie l'identifiant du compteur.
     *
     * @param id le nouvel identifiant du compteur
     */
	public void setId(int id){
		this.id = id;
	}
	
	/**
     * Retourne la latitude du compteur.
     *
     * @return la latitude du compteur
     */
	public double getLatitude(){
		return this.latitude;
	}
	
	/**
     * Modifie la latitude du compteur.
     *
     * @param latitude la nouvelle latitude du compteur
     */
	public void setLatitude(double latitude){
		this.latitude = latitude;
	}
	
	/**
     * Retourne la longitude du compteur.
     *
     * @return la longitude du compteur
     */
	public double getLongitude(){
		return this.longitude;
	}
	
	/**
     * Modifie la longitude du compteur.
     *
     * @param longitude la nouvelle longitude du compteur
     */
	public void setLongitude(double longitude){
		this.longitude = longitude;
	}
	
	
	/**
     * Retourne le libelle du compteur.
     *
     * @return le libelle du compteur
     */
	public String getLibelle(){
		return this.libelle;
	}
	
	/**
     * Modifie le libelle du compteur
     *
     * @param libelle la nouvelle chaîne contenant le libellé 
     */
	public void setLibelle(String libelle){
		this.libelle = libelle;
	}
	
	/**
     * Retourne le sens du compteur.
     *
     * @return le sens du compteur
     */
	public String getSens(){
		return this.sens;
	}

	/**
     * Modifie le sens du compteur
     *
     * @param sens nouvelle chaîne contenant le libellé 
     */
	public void setSens(String sens){
		this.sens = sens;
	}

	/**
     * Retourne l'id du Quartier
     *
     * @return ll'id du Quartier
     */
	public int getIdQuartier(){
		return this.idQuartier;
	}
	
	/**
     * Retourne une représentation sous forme de chaîne de caractères du compteur.
     *
     * @return la représentation du compteur sous forme de chaîne de caractères
     */
	public String toString(){		
		return this.id+" "+this.libelle+" vers "+this.sens+" : "+this.latitude+"N "+this.longitude+"W"+" "+this.idQuartier;
	}
}
