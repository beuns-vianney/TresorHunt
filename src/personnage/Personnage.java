package personnage;

import coordonee.Coordonee;
import parcelle.Navire;
import parcelle.Parcelle;

abstract public class Personnage extends Parcelle{
	private int energie;
	private static int indice;
	private final int ID;
	private String type;
	private boolean key = false;
	private boolean chest = false;
	private Coordonee coord;
	private Navire navire;
	
	public Personnage(String type){
		this.energie = 100;
		indice++;
		this.ID = indice;
		this.type = type;
	}
	
	public Personnage(String type, Coordonee coord){
		this.energie = 100;
		indice++;
		this.ID = indice;
		this.type = type;
		this.coord = coord;
	}
	public Personnage(String type, char c, Coordonee coord){
		this.energie = 100;
		indice++;
		this.ID = indice;
		if(c == 'c'){
			this.chest = true;
		}else if(c == 'k'){
			this.key = true;
		}
		this.type = type;
		this.coord = coord;
	}
	public Coordonee getCoord() {
		return coord;
	}
	public void setCoord(Coordonee coord) {
		this.coord = coord;
	}
	public void retirerEnergie(int nb){
		if(this.energie-nb < 0){
			this.energie = 0;
		}else{
			this.energie -= nb;
		}
	}
	public boolean memeEquipe(Personnage p){
		if(this.navire.equals(p.getNavire())){
			return true;
		}else return false;
	}
	public void ajouterEnergie(int nb){
		if(this.energie + nb > 100){
			this.energie = 100;
		}else{
			this.energie += nb;
		}
	}
	public boolean getKey() {
		return key;
	}
	public void setKey(boolean key) {
		this.key = key;
	}
	public boolean getChest() {
		return chest;
	}
	public void setChest(boolean chest) {
		this.chest = chest;
	}
	public String getType() {
		return type;
	}
	public int getEnergie() {
		return energie;
	}

	public void setEnergie(int energie) {
		if(energie >= 0 && energie <= 100){
			this.energie = energie;
		}
	}

	public int getId() {
		return this.ID;
	}
	abstract  public boolean deplacer(String type, Coordonee coord, Coordonee dest);

	public Navire getNavire() {
		return navire;
	}

	public void setNavire(Navire navire) {
		this.navire = navire;
	}
}
