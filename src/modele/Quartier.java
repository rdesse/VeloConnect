/**
 * Classe Quartier
 *
 * Cette classe représente un quartier avec ses informations telles que l'identifiant, le nom,
 * la longueur de la piste cyclable et les compteurs présents dans le quartier.
 * 
 * @author A.Bellec R.Desse G.Roger E.Rousseau
 */
package modele;

import java.io.*;
import java.util.*;

public class Quartier{
	private int id;
	private String nom;
	private double lgPisteCyclable;
	
	
	/**
     * Constructeur de la classe Quartier.
     *
     * @param id l'identifiant du quartier
     * @param nom le nom du quartier
     * @param lgPisteCyclable la longueur de la piste cyclable dans le quartier
     */
	public Quartier(int id, String nom, double lgPisteCyclable){
		this.id = id;
		if(nom != null){
			this.nom = nom;
		}else{
			System.err.println("nom null");
			this.nom = "Default";
		}
		
		this.lgPisteCyclable = lgPisteCyclable;
		
	}
	
	/**
     * Retourne l'identifiant du quartier.
     *
     * @return l'identifiant du quartier
     */
	public int getId(){
		return this.id;
	}
	
	/**
     * Modifie l'identifiant du quartier.
     *
     * @param id le nouvel identifiant du quartier
     */
	public void setId(int id){
		this.id = id;
	}
	
	/**
     * Retourne le nom du quartier.
     *
     * @return le nom du quartier
     */
	public String getNom(){
		return this.nom;
	}
	
	/**
     * Modifie le nom du quartier.
     * Si le nom est nul, le nom par défaut est utilisé.
     *
     * @param nom le nouveau nom du quartier
     */
	public void setNom(String nom){
		if(nom != null){
			this.nom = nom;
		}else{
			System.err.println("nom null");
			this.nom = "Default";
		}
	}
	
	/**
     * Retourne la longueur de la piste cyclable dans le quartier.
     *
     * @return la longueur de la piste cyclable
     */
	public double getLgPisteCyclable(){
		return this.lgPisteCyclable;
	}
	
	/**
     * Modifie la longueur de la piste cyclable dans le quartier.
     *
     * @param lgPisteCyclable la nouvelle longueur de la piste cyclable
     */
	public void setLgPisteCyclable(double lgPisteCyclable){
		this.lgPisteCyclable = lgPisteCyclable;
	}
	
	
	
	/**
     * Vérifie si la piste cyclable dans le quartier est plus longue que la valeur spécifiée.
     *
     * @param longPiste la valeur à comparer avec la longueur de la piste cyclable
     * @return true si la piste cyclable est plus longue, false sinon
     */
	public boolean pistePlusLongue(double longPiste){
		boolean ret = true;
		if(longPiste > this.lgPisteCyclable){
			ret = false;
		}
		return ret;
	}
	
	/**
     * Retourne une représentation sous forme de chaîne de caractères du quartier.
     *
     * @return la représentation du quartier en tant que chaîne de caractères
     */
	public String toString(){		
		return this.id+" "+this.nom+" : "+this.lgPisteCyclable+"m";
	}
}
