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
				|| type.equals("piegeur")
				|| type.equals("mer")
				|| type.equals("guerrier")) return true;
		return false;
		} return false;
	}
	public void voler(Personnage e){
		java.util.Random r = new java.util.Random();
		boolean reussi = false;
		if(e.getKey()){
			if(r.nextInt(75) <= 75){
				this.setKey(true);
				e.setKey(false);
				JOptionPane.showMessageDialog(null, "Vous venez de dérober la clé.");			
			}
			reussi = true;
		}else if(e.getChest()){
			if(r.nextInt(75) <= 75){				
				this.setChest(true);
				e.setChest(false);
				JOptionPane.showMessageDialog(null, "Vous venez de dérober le trésor.");
			}
			reussi = true;
		}
		if(reussi){
			JOptionPane.showMessageDialog(null, "Vous n'avez pas réussi à voler ce personnage.");
		}else{				
			JOptionPane.showMessageDialog(null, "Ce personnage n'avait aucun objet sur lui.");
		}
	}
	public String toString(){
		return "v"+ super.getId();
	}
}
