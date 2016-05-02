package parcelle;

import java.util.ArrayList;

import javax.swing.JOptionPane;

import personnage.Explorateur;
import personnage.Personnage;
import personnage.Voleur;

/**
 * Navire est la classe représentant un navire d'une équipe
 * @author Siocbo
 *
 */
public class Navire extends Parcelle {
	private static int indice;
	private final int ID;
	private ArrayList<Personnage> equipe = new ArrayList<Personnage>();
	private ArrayList<Personnage> bateau;
	/**
	 * Type de Parcelle
	 */
	private String type;
	/**
	 * Constructeur de navire
	 * initialise le type
	 */
	public Navire(){
		this.type = "navire";
		Navire.indice++;
		this.ID = indice;
		this.creerEquipe();
	}
	public void creerEquipe(){
		while(this.equipe.size() < 10){
			int rep = JOptionPane.showOptionDialog(null, "Quel personnage voulez-vous ajouter à votre équipe ?", 
					"Equipe"+getID(), JOptionPane.NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, 
					new String[]{"Explorateur", "Voleur", "Quitter"}, 2);
			if(rep == 0){
				equipe.add(new Explorateur());
			}else if(rep == 1){
				equipe.add(new Voleur());
			}else if (rep == 2){
				if(equipe.size() == 0){
					JOptionPane.showMessageDialog(null, "Vous devez avoir au moins un personnage dans votre équipe !");
				}else{
					break;
				}
			}
		}
		this.bateau = new ArrayList<Personnage>(this.equipe);
		for(Personnage p : equipe){
			p.setNavire(this);
		}
	}
	/**
	 * Teste l'appartenance d'un personnage à une équipe.
	 * @param p   Le personnage testé
	 * @return		retourne vrai si le personnage testé est dans l'équipe
	 */
	public boolean estDansLEquipe(Personnage p){
		if(equipe.contains(p)){
			return true;
		}else return false;
	}
	
	public boolean equals(Navire n){
		for(int i = 0; i< this.equipe.size(); i++){
			if(this.equipe.get(i) != n.equipe.get(i)){
				return false;
			}
		}
		return true;
	}
	public void refillEnergie(){
		for(Personnage p : bateau){
			p.ajouterEnergie(10);
		}
	}
	
	public void resetTurns(){
		for(Personnage p : this.equipe){
			p.setaJoue(false);
		}
	}
/**
 * @return le type de la parcelle
 */
	public String getType() {
		return type;
	}
/**
 * @param type le nom voulu pour ce navire
 */
	public void setType(String type) {
		this.type = type;
	}
	public ArrayList<Personnage> getEquipe() {
		return equipe;
	}
	public ArrayList<Personnage> getBateau() {
		return bateau;
	}
	public void addPersoEquipe(Personnage p){
		equipe.add(p);
	}
	public void addPersoBateau(Personnage p){
		bateau.add(p);
		if(p.getChest()){
			Ile.fin = true;
			System.out.println("L'équipe " + this.getID() + " a gagné !!");
		}
	}
	public void rmPersoB(Personnage p){
		bateau.remove(p);
	}
	/**
	 * @return l'affichage texte de cette parcelle
	 */
	public String toString(){
		return "N";
	}
	public int getID() {
		return ID;
	}
}
