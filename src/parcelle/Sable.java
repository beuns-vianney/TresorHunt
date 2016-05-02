package parcelle;
/**
 * Sable est la classe représentant du sable dans une parcelle
 * @author Siocbo
 *
 */
public class Sable extends Parcelle{
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
	 * Constructeur de Sable
	 * initialise le type
	 */
	public Sable(){
		this.type = "sable";
	}
	/**
	 * Constructeur de sable
	 * initialise son type et la présence d'une clé ou d'un trésor
	 * @param c k pour mettre une clé ; c pour mettre un trésor.
	 */
	public Sable(char c){
		this.type="sable";
		if(c=='k'){
			this.key=true;
		}else if(c=='c'){
			this.chest = true;
		}
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
	 * @param key si la clé est toujours là ou non
	 */
	public void setKey(boolean lol){
		this.key = lol;
	}
	/**
	 * @param key si le trésor est toujours là ou non
	 */
	public void setChest(boolean lol){
		this.chest = lol;
	}
	/**
	 * @return l'affichage texte de ce type de parcelle
	 */
	public String toString(){
		return " ";
	}
}
