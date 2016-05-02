package parcelle;
/**
 * Mer est la classe représentant une parcelle d'eau
 * @author Siocbo
 *
 */
public class Mer extends Parcelle{
	/**
	 * Type de Parcelle
	 */
	private String type;
	/**
	 * Booléen vérifiant si la clé est présente ou non sur cette parcelle
	 */
	private boolean key = false;
	/**
	 * Booléen vérifiant si le trésor est présent ou non sur cette parcelle
	 */
	private boolean chest= false;
	
	/**
	 * Constructeur de Mer
	 * initialise le type
	 */
	public Mer(){
		this.type = "mer";
	}
	/**
	 * @return le type de la parcelle
	 */
	public String getType(){
		return this.type;
	}
	/**
	 * @return la présence de la clé ou non
	 */
	public boolean getKey(){
		return this.key;
	}
	/**
	 * @return la présence du trésor ou non
	 */
	public boolean getChest(){
		return this.chest;
	}
	/**
	 * @return l'affichage texte pour ce type de parcelle
	 */
	public String toString(){
		return "M";
	}
	/**
	 * @param key si la clé est toujours là ou non
	 */
	public void setKey(boolean key) {
		this.key = key;
	}
	/**
	 * @param chest si le trésor est toujours là ou non
	 */
	public void setChest(boolean chest) {
		this.chest = chest;
	}
}
