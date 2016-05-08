package personnage;

import coordonee.Coordonee;
import parcelle.Ile;
import parcelle.Parcelle;

public class Piegeur extends Personnage{
	public Piegeur(){
		super("piegeur");
	}
	public Piegeur(char c, Coordonee coord){
		super("piegeur", c, coord);
	}
	public Piegeur(Coordonee coord){
		super("piegeur", coord);
	}
	@Override
	public boolean deplacer(String type, Coordonee coord, Coordonee dest) {
		if(dest.equals(coord.aDroite(coord)) || dest.equals(coord.aGauche(coord)) || dest.equals(coord.enBas(coord))|| dest.equals(coord.enHaut(coord)) || dest.equals(coord.basDroit(coord))
				|| dest.equals(coord.basGauche(coord)) || dest.equals(coord.hautDroit(coord)) || dest.equals(coord.hautGauche(coord))){
		if(type.equals("sable") 
				|| type.equals("navire") 
				|| type.equals("explorateur") 
				|| type.equals("voleur")
				|| type.equals("piegeur")
				|| type.equals("roche")
				|| type.equals("mer")
				|| type.equals("guerrier")) return true;
		return false;
		} return false;
	}
	
	public void placerPiege(Parcelle p){
		p.setPiege(true);
		if(Ile.getNavire1().estDansLEquipe(this)){
			p.setEquipe1(true);
		}else if(Ile.getNavire2().estDansLEquipe(this)){
			p.setEquipe1(false);
		}
	}
	
	public String toString(){
		return "p"+ super.getId();
	}
}
