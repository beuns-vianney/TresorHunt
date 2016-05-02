package personnage;

import coordonee.Coordonee;
import javax.swing.JOptionPane;
import parcelle.Roche;

public class Explorateur extends Personnage{
	
	public Explorateur(){
		super("explorateur");
	}
	public Explorateur(Coordonee coord){
		super("explorateur", coord);
	}
	
	public Explorateur(char c, Coordonee coord){
		super("explorateur", c, coord);
		
	}
	public boolean deplacer(String type, Coordonee coord, Coordonee dest){
		if(dest.equals(coord.aDroite(coord)) || dest.equals(coord.aGauche(coord)) || dest.equals(coord.enBas(coord))|| dest.equals(coord.enHaut(coord))){
			if(type.equals("roche")) return true;
			else if(type.equals("sable")) return true;
			else if(type.equals("navire")) return true;
			else if(type.equals("mer"))return true;
			else return false;
		}else return false;
	}
	public void souleverRoche(Roche r){
		if(r.getKey()){
			r.setKey(false);
			this.setKey(true);
			JOptionPane.showMessageDialog(null,"l'explorateur vient de ramasser la clé.");
		}else if(r.getChest()){
			if(this.getKey()){
				r.setChest(false);
				this.setChest(true);
				r.setRevealed(true);
				this.setKey(false);
				JOptionPane.showMessageDialog(null,"l'explorateur vient de ramasser le trésor.");
			}else{
				r.setRevealed(true);
				JOptionPane.showMessageDialog(null,"l'explorateur vient de trouver le coffre, mais il lui manque la clé.");
			}
		}else{
			JOptionPane.showMessageDialog(null,"Vous ne trouvez rien sous ce rocher.");
		}
	}
	public String toString(){
		return "e"+ super.getId();
	}
	
}
