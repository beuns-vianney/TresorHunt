package parcelle;

import coordonee.Coordonee;
import personnage.Explorateur;

/**
 * Parcelle est la classe représentant une parcelle de terrain.
 * Elle peut adopter plusieurs formes (mer, sable, roche, etc)
 * @author Siocbo
 *
 */
public class Parcelle {
	private Coordonee cord;

	public Coordonee getCord() {
		return cord;
	}
/*	public boolean deplacer(String type, Coordonee coord, Coordonee dest){
		return deplacer(type, coord, dest);
	} */
	public void setCord(Coordonee cord) {
		this.cord = cord;
	}
	public void setRevealed(boolean revealed){
		this.setRevealed(revealed);
	}
	public boolean isRevealed(){
		return this.isRevealed();
	}
	public void souleverRoche(Roche r){
		this.souleverRoche(r);
	}
	public void voler(Explorateur e){
		this.voler(e);
	}
	/**
	 * 
	 * @return le type de la parcelle
	 */
	public String getType(){
		return this.getType();
	}
	public void setType(String type){
		this.setType(type);
	}
	/**
	 * 
	 * @return la présence ou non de la clé
	 */
	public boolean getKey(){
		return this.getKey();
	}
	/**
	 * 
	 * @return la présence ou non d'un trésor
	 */
	public boolean getChest(){
		return this.getChest();
	}
	/**
	 * 
	 * @param lol si la clé est toujours là ou non
	 */
	public void setKey(boolean lol){
		this.setKey(lol);
	}
	/**
	 * 
	 * @param lol si le trésor est toujours là ou non
	 */
	public void setChest(boolean lol){
		this.setChest(lol);
	}
}
