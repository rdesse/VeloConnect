/**
 * Classe Rapport
 *
 * Cette classe représente un rapport contenant les informations sur un compteur à un jour donné.
 * Le rapport comprend le compteur, le jour, le nombre de passages par heure, et la présence d'une anomalie.
 * 
 * @author A.Bellec R.Desse G.Roger E.Rousseau
 */
package modele;

public class Rapport{
	private int leCompteur;
	private String leJour;
	private int[] nbVeloH;
	private String presenceAnomalie;
	
	private static final String[] ANOMALIE = {"Faible", "Forte"};
	
	/**
     * Constructeur de la classe Rapport.
     *
     * @param leCompteur le compteur associé au rapport
     * @param leJour le jour associé au rapport
     * @param presenceAnomalie la présence d'une anomalie ("Faible" ou "Forte")
     */
	public Rapport(int leCompteur, String leJour, String presenceAnomalie){
		this.leCompteur = leCompteur;
		if(leJour == null || leJour.length() ==0){
			System.err.println("leJour null");
			this.leJour = "Default";
		}else{
			this.leJour = leJour;
		}
		
		addPresenceAnomalie(presenceAnomalie);
		
		this.nbVeloH = new int[24];
	}




	
	/**
	 * Constructeur de la classe Rapport.
	 * @param leCompteur le compteur associé au rapport
	 * @param leJour le jour associé au rapport
	 * @param h00 heure 0 
	 * @param h01 heure 1
	 * @param h02 heure 2
	 * @param h03 heure 3
	 * @param h04 heure 4
	 * @param h05 heure 5
	 * @param h06 heure 6
	 * @param h07 heure 7
	 * @param h08 heure 8
	 * @param h09 heure 9
	 * @param h10 heure 10
	 * @param h11 heure 11
	 * @param h12 heure 12
	 * @param h13 heure 13
	 * @param h14 heure 14
	 * @param h15 heure 15
	 * @param h16 heure 16
	 * @param h17 heure 17
	 * @param h18 heure 18
	 * @param h19 heure 19
	 * @param h20 heure 20
	 * @param h21 heure 21
	 * @param h22 heure 22
	 * @param h23 heure 23
	 * @param presenceAnomalie la présence d'une anomalie ("Faible" ou "Forte")
	 */
	public Rapport(int leCompteur, String leJour,int h00,int h01,int h02, int h03, int h04, int h05, int h06, int h07, int h08, int h09, int h10, int h11, int h12, int h13, int h14, int h15, int h16, int h17, int h18, int h19, int h20, int h21, int h22, int h23,String presenceAnomalie){
		this.leCompteur = leCompteur;
		if(leJour == null || leJour.length() ==0){
			System.err.println("leJour null");
			this.leJour = "Default";
		}else{
			this.leJour = leJour;
		}
		
		addPresenceAnomalie(presenceAnomalie);
		
		this.nbVeloH = new int[]{h00, h01, h02, h03, h04, h05, h06, h07, h08, h09, h10, h11, h12, h13, h14, h15, h16, h17, h18, h19, h20, h21, h22, h23};
	}
	
	/**
     * Retourne le compteur associé au rapport.
     *
     * @return le compteur associé au rapport
     */
	public int getCompteur(){
		return this.leCompteur;
	}
	
	/**
     * Modifie le compteur associé au rapport.
     *
     * @param leCompteur le nouveau compteur associé au rapport
     */
	public void setCompteur(int leCompteur){
		this.leCompteur = leCompteur;
		
	}
	
	/**
     * Retourne le jour associé au rapport.
     *
     * @return le jour associé au rapport
     */
	public String getJour(){
		return this.leJour;
	}
	
	/**
     * Modifie le jour associé au rapport.
     *
     * @param leJour le nouveau jour associé au rapport
     */
	public void setJour(String leJour){
		if(leJour == null || leJour.length() ==0){
			System.out.println("setJour : pas de changement : jour null ou vide");
		}else{
			this.leJour = leJour;
		}
	}
	
	/**
     * Retourne la présence d'une anomalie dans le rapport.
     *
     * @return la présence d'une anomalie ("Faible" ou "Forte")
     */
	public String getPresenceAnomalie(){
		return this.presenceAnomalie;
	}
	
	/**
     * Modifie la présence d'une anomalie dans le rapport.
     *
     * @param presenceAnomalie la nouvelle présence d'une anomalie ("Faible" ou "Forte")
     */
	public void setPresenceAnomalie(String presenceAnomalie){
		addPresenceAnomalie(presenceAnomalie);
	}
	
	/**
     * Ajoute la présence d'une anomalie dans le rapport.
     * Si la valeur n'est pas "Faible" ou "Forte", la présence d'anomalie est considérée comme nulle.
     *
     * @param presenceAnomalie la présence d'une anomalie ("Faible" ou "Forte")
     */
	public void addPresenceAnomalie(String presenceAnomalie){
		if(presenceAnomalie != null){
			boolean test = false;
			for(int i=0; i<ANOMALIE.length; i++){
				if(presenceAnomalie.equals(ANOMALIE[i])){
					this.presenceAnomalie = presenceAnomalie;
					test = true;
				}
			}
			if(!test){
				this.presenceAnomalie = null;
			}
		}else{
			this.presenceAnomalie = null;
		}
	}
	
	/**
     * Retourne le nombre de passages à l'heure donnée.
     *
     * @param index l'heure (entre 0 et 23)
     * @return le nombre de passages à l'heure donnée
     */
	public int getPassage(int index){
		return this.nbVeloH[index];
	}
	
	/**
     * Modifie le nombre de passages à l'heure donnée.
     *
     * @param index l'heure (entre 0 et 23)
     * @param valeur le nombre de passages à l'heure donnée
     */
	public void setPassage(int index, int valeur){
		this.nbVeloH[index]=valeur;
	}
	
	/**
     * Calcule la moyenne des passages par heure.
     *
     * @return la moyenne des passages par heure
     */
	public double moyennePassageParHeure(){
		int sum = 0;
		
		for(int i=0; i<nbVeloH.length; i++){
			sum += nbVeloH[i];
		}
		
		return sum/nbVeloH.length;
	}
	
	/**
     * Calcule le nombre total de passages sur la journée.
     *
     * @return le nombre total de passages sur la journée
     */
	public int totalPassage(){
		int sum = 0;
		
		for(int i=0; i<nbVeloH.length; i++){
			sum += nbVeloH[i];
		}
		
		return sum;
	}
	
	/**
     * Retourne une représentation sous forme de chaîne de caractères du rapport.
     *
     * @return la représentation du rapport sous forme de chaîne de caractères
     */
	public String toString(){
		String ret = "Compteur : "+this.leCompteur+"  Jour : "+this.leJour+"\n";
		
		for(int i=0; i<nbVeloH.length; i++){
			ret += i+"h : "+nbVeloH[i]+"\n";
		}
		ret = ret +"Probabilité de présence d'anomalie : "+ this.presenceAnomalie;
		
		return ret;
	}
}
