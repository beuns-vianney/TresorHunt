package personnage;

import javax.swing.JOptionPane;

import coordonee.Coordonee;

public class Voleur extends Personnage{
	
	
	public Voleur(){
		super("voleur");
	}
	public Voleur(char c, Coordonee coord){
		super("voleur", c, coord);
	}
	public Voleur(Coordonee coord){
		super("voleur", coord);
	}

	@Override
	public boolean deplacer(String type, Coordonee coord, Coordonee dest) {
		if(dest.equals(coord.aDroite(coord)) || dest.equals(coord.aGauche(coord)) || dest.equals(coord.enBas(coord))|| dest.equals(coord.enHaut(coord)) || dest.equals(coord.basDroit(coord))
				|| dest.equals(coord.basGauche(coord)) || dest.equals(coord.hautDroit(coord)) || dest.equals(coord.hautGauche(coord))){
		if(type.equals("sable") 
				|| type.equals("navire") 
				|| type.equals("explorateur") 
				|| type.equals("voleur") 
				|| type.equals("mer")) return true;
		return false;
		} return false;
	}
	public void voler(Personnage e){
		if(e.getKey()){
			this.setKey(true);
			e.setKey(false);
			JOptionPane.showMessageDialog(null, "Vous venez de d�rober la cl�.");
		}else if(e.getChest()){
			this.setChest(true);
			e.setChest(false);
			JOptionPane.showMessageDialog(null, "Vous venez de d�rober le tr�sor.");
		}else {
			JOptionPane.showMessageDialog(null, "Cet explorateur n'avait aucun objet sur lui.");
		}
	}
	public String toString(){
		return "v"+ super.getId();
	}
}
