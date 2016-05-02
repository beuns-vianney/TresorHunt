package parcelle;
/**
 * Roche est la classe représentant une roche sur le plateau de jeu
 * @author Siocbo
 *
 */
public class Roche extends Parcelle{
	/**
	 * Type de Parcelle
	 */
	private String type;
	private boolean revealed = false;
	/**
	 * Booléen vérifiant si la clé est présente ou non sur cette parcelle
	 */
	private boolean key = false;
	/**
	 * Booléen vérifiant si le trésor est présent ou non sur cette parcelle
	 */
	private boolean chest= false;
	/**
	 * Constructeur de roche
	 * initialise son type
	 */
	public Roche(){
		this.type = "roche";
	}
	/**
	 * Constructeur de roche
	 * initialise son type et la présence d'une clé ou d'un coffre
	 * @param c k pour mettre une clé ; c pour mettre un coffre.
	 */
	//k pour mettre une clé ; c pour mettre un coffre.
	public Roche(char c){
		this.type = "roche";
		if(c == 'k'){
			this.key = true;
		}else if(c == 'c'){
			this.chest = true;
		}
	}
	
	/**
	 * @return le type de la parcelle
	 */
	public String getType(){
		return this.type;
	}
	public void setRevealed(boolean revealed) {
		this.revealed = revealed;
	}
	public boolean isRevealed() {
		return revealed;
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
		if(this.revealed){
			return "C";
		}else{
			return "R";
		}
	}
}
