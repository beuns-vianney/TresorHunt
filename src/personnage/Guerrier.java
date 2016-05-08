package personnage;

import coordonee.Coordonee;

public class Guerrier extends Personnage{
	public Guerrier(){
		super("guerrier");
	}
	public Guerrier(Coordonee coord){
		super("guerrier", coord);
	}
	
	public Guerrier(char c, Coordonee coord){
		super("guerrier", c, coord);
		
	}
	public boolean deplacer(String type, Coordonee coord, Coordonee dest){
		if(dest.equals(coord.aDroite(coord)) || dest.equals(coord.aGauche(coord)) || dest.equals(coord.enBas(coord))|| dest.equals(coord.enHaut(coord))){
			if(	type.equals("sable") ||
					type.equals("navire") ||
					type.equals("mer") || 
					type.equals("voleur") ||
					type.equals("piegeur") ||
					type.equals("explorateur") ||
					type.equals("guerrier")) return true;
			return false;
		} return false;
	}
	
	public void attaque(Personnage p){
		p.retirerEnergie(25);
	}
	
	public String toString(){
		return "g" + this.getId();
	}
}
