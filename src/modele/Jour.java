/**
 * Classe Jour
 *
 * Cette classe représente un jour avec ses informations telles que la date, la température moyenne,
 * le jour de la semaine et les vacances.
 * 
 * Les jours de la semaine sont représentés par les constantes : Lundi, Mardi, Mercredi, Jeudi,
 * Vendredi, Samedi, Dimanche.
 *
 * Les vacances sont représentées par les constantes : Noel, Ascension, Hiver, Ete, Toussaint, Printemps.
 * 
 * @author A.Bellec R.Desse G.Roger E.Rousseau
 */
package modele;

public class Jour{
	private String date;
	private double temperatureMoyenne;
	private String jour;
	private String vacances;
	
	
	private static final String[] JOURS = {"Lundi", "Mardi", "Mercredi", "Jeudi", "Vendredi", "Samedi", "Dimanche"};
	private static final String[] NOMVACANCES = {"Noel", "Ascension", "Hiver", "Ete", "Toussaint", "Printemps"};
	
	/**
     * Constructeur de la classe Jour.
     *
     * @param date la date du jour
     * @param temperatureMoyenne la température moyenne du jour
     * @param jour le jour de la semaine
     * @param vacances les vacances (null si pas de vacances)
     */
	public Jour(String date, double temperatureMoyenne, String jour, String vacances){
		if(date != null){
			this.date = date;
		}else{
			System.err.println("date null");
			this.date = "Default";
		}
		
		this.temperatureMoyenne = temperatureMoyenne;
		
		if(jour != null){
			addJour(jour);
		}else{
			System.err.println("jour null");
			this.jour = "Default";
		}
		
		addVacances(vacances);
	}
	
	/**
     * Retourne la date du jour.
     *
     * @return la date du jour
     */
	public String getDate(){
		return this.date;
	}
	
	/**
     * Modifie la date du jour.
     *
     * @param date la nouvelle date du jour
     */
	public void setDate(String date){
		if(date != null){
			this.date = date;
		}else{
			System.err.println("date null");
			this.date = "Default";
		}
	}
	
	/**
     * Retourne la température moyenne du jour.
     *
     * @return la température moyenne du jour
     */
	public double getTemperatureMoyenne(){
		return this.temperatureMoyenne;
	}
	
	/**
     * Modifie la température moyenne du jour.
     *
     * @param temperatureMoyenne la nouvelle température moyenne du jour
     */
	public void setTemperatureMoyenne(double temperatureMoyenne){
		this.temperatureMoyenne = temperatureMoyenne;
	}
	
	/**
     * Retourne le jour de la semaine.
     *
     * @return le jour de la semaine
     */
	public String getJour(){
		return this.jour;
	}
	
	/**
     * Modifie le jour de la semaine.
     * Si le jour est nul, le jour par défaut est utilisé.
     *
     * @param jour le nouveau jour de la semaine
     */
	public void setJour(String jour){
		if(jour != null){
			addJour(jour);
		}else{
			System.err.println("jour null");
			this.jour = "Default";
		}
	}
	
	/**
     * Ajoute la présence le jour de la semaine en vérifiant qu'il respecte les contraintes.
     *
     * @param jour Jour de la semaine
     */
	public void addJour(String jour){
		if(jour != null){
			for(int i=0; i<JOURS.length; i++){
				if(jour.equals(JOURS[i])){
					this.jour = jour;
				}
			}
		}else{
			System.err.println("Jour de la semaine inexistante");
			this.jour = "Lundi";
		}
	}
	
	/**
     * Retourne les vacances.
     *
     * @return les vacances (null si pas de vacances)
     */
	public String getVacances(){
		return this.vacances;
	}
	
	/**
     * Modifie les vacances.
     *
     * @param vacances les nouvelles vacances (null si pas de vacances)
     */
	public void setVacances(String vacances){
		addVacances(vacances);
	}
	
	/**
     * Ajoute les vacances si la valeur est correcte.
     * Si la valeur n'est pas dans la liste NOMVACANCES, vacances est considérée comme nulle.
     *
     * @param vacances les vacances du jour
     */
	public void addVacances(String vacances){
		if(vacances != null){
			boolean test = false;
			for(int i=0; i<NOMVACANCES.length; i++){
				if(vacances.equals(NOMVACANCES[i])){
					this.vacances = vacances;
					test = true;
				}
			}
			if(!test){
				this.vacances = null;
			}
		}else{
			this.vacances = null;
		}
	}
	
	/**
     * Teste si la date est en weekend
     *
     * @return true si la date est pendant un weekend false sinon
     */
	public boolean enWeekend(){
		boolean ret = false;
		
		if(this.jour=="Samedi" || this.jour=="Dimanche"){
			ret = true;
		}
		
		return ret;
	}
	
	/**
     * Teste si la date est en vacances
     *
     * @return true si la date est pendant les vacances false sinon
     */
	public boolean enVacances(){
		boolean ret = true;
		
		if(this.vacances==null){
			ret = false;
		}
		
		return ret;
	}
	
	/**
     * Retourne une représentation sous forme de chaîne de caractères du jour.
     *
     * @return la représentation du jour en tant que chaîne de caractères
     */
	public String toString(){
		return this.date+" "+this.jour+" "+this.vacances+" "+this.temperatureMoyenne+"°C moyens";
	}
}
