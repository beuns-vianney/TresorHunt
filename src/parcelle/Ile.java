package parcelle;

import java.awt.event.InputEvent;
import java.awt.event.MouseEvent;
import java.util.Random;

import javax.swing.JOptionPane;


import coordonee.Coordonee;
import personnage.*;
import plateau.Plateau;
/**
 * Ile est la classe représentant le plateau de jeu, composé d'un tableau de Parcelles.
 * @author Siocbo
 *
 */

public class Ile {
	private	String[] img = new String[]{"images/rocher.png", "images/mer.png",
			"images/1.navire.png","images/2.navire.png","images/1.explorateur.png", "images/coffre.png", "images/1.piegeur.png", "images./sable.png"};
	private Parcelle[][] plateau;
	private int size;
	private int[][] jeu;
	private Plateau grille;
	private static Navire navire1;
	private static Navire navire2;
	public static boolean fin = false;
	public static boolean tour1 = true;
	public static boolean tour2 = false;
	/**
	 * 
	 * @param idx	index de ligne
	 * @param idy	index de colonne
	 * @return La parcelle ciblée du plateau de jeu
	 */
	public Parcelle getParcelle(Coordonee coord){
		if(coord.getX() >= 0 || coord.getX() <= this.size -1 || coord.getY() >= 0 || coord.getY() <= this.size -1){
			return plateau[coord.getX()][coord.getY()];
		}else return new Parcelle();
	}
	/**
	 *  Méthode générale pour déplacer un personnage ou lui faire éxecuter une action
	 */
	public void deplacerPerso(){
		grille.setJeu(jeu);
		InputEvent e = grille.waitEvent();
		Coordonee coord = new Coordonee(grille.getX((MouseEvent) e), grille.getY((MouseEvent) e));
		String type = getParcelle(coord).getType();
		boolean ok = false;
		boolean bateau = false;
		Personnage perso = null;
		Navire navire = null;
//------------SI BATEAU------------------------------------------------------
		if(type.equals("navire") && (Ile.tour1 && getParcelle(coord) == navire1) || (Ile.tour2 && getParcelle(coord) == navire2)){
			grille.setHighlight(coord.getX(), coord.getY());
			navire = (Navire) getParcelle(coord);
			String[] choix = new String[navire.getBateau().size()+1];
			for(int i = 0; i<navire.getBateau().size(); i++){
				choix[i] = navire.getBateau().get(i).toString();
			}
			choix[choix.length-1] = "Quitter";
			int rep = JOptionPane.showOptionDialog(null, "Quel personnage voulez-vous sortir du bateau ?", 
					"Navire", JOptionPane.NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, 
					choix, choix.length-1);
			if(rep != choix.length-1){
				perso = navire.getBateau().get(rep);
				ok = true;
				bateau = true;
			}
		}else
//------------------------------------SI PERSONNAGE-----------------------------------------------
		if(type.equals("explorateur") || type.equals("voleur")){
			grille.setHighlight(coord.getX(), coord.getY());
			perso = (Personnage) getParcelle(coord);
			ok = true;
		}
		if(Ile.tour1 && ! navire1.getEquipe().contains(perso)){
			ok = false;
		}else if(Ile.tour2 && ! navire2.getEquipe().contains(perso)){
			ok = false;
		}
		if(ok && ! perso.getaJoue()){
			InputEvent dest = grille.waitEvent();
			Coordonee destination = new Coordonee(grille.getX((MouseEvent) dest), grille.getY((MouseEvent) dest));
			

			if(perso.deplacer(getParcelle(destination).getType(), coord, destination)){
				if(perso.getType().equals("explorateur")){
				
					if(getParcelle(destination).getType().equals("roche")){
						Roche cible = (Roche) getParcelle(destination);
						perso.souleverRoche((Roche)cible);
						perso.retirerEnergie(5);
				
					}else if(getParcelle(destination).getType().equals("navire")){
						Navire cible = (Navire) getParcelle(destination);
						if(cible.estDansLEquipe(perso)){
							cible.addPersoBateau(perso);	
							if(coord.getX() > 0 && coord.getX() < this.size-1 && coord.getY() > 0 && coord.getY() < this.size-1){
								this.plateau[coord.getX()][coord.getY()] = new Sable(); 
							}else if( ! bateau){
								this.plateau[coord.getX()][coord.getY()] = new Mer(); 
							}
						}else {
							JOptionPane.showMessageDialog(null, "Ce n'est pas votre bateau !");
						}
					
					}else{
						plateau[destination.getX()][destination.getY()] = perso;
						perso.setCoord(destination);
						perso.retirerEnergie(1);
						if(bateau){
							navire.rmPersoB(perso);
						}
						if(coord.getX() > 0 && coord.getX() < this.size-1 && coord.getY() > 0 && coord.getY() < this.size-1){
							this.plateau[coord.getX()][coord.getY()] = new Sable(); 
						}else if(! bateau){
							this.plateau[coord.getX()][coord.getY()] = new Mer(); 
						}
					}
			
				}else if(perso.getType().equals("voleur")){
					
					if(getParcelle(destination).getType().equals("explorateur") || getParcelle(destination).getType().equals("voleur")){
						if (! perso.memeEquipe((Personnage) getParcelle(destination))){
							Personnage cible = (Personnage) getParcelle(destination);
							perso.voler(cible);
							perso.retirerEnergie(10);
						}else JOptionPane.showMessageDialog(null, "Ce personnage est dans votre équipe, ne tentez pas de le dérober !");
					
					}else if(getParcelle(destination).getType().equals("navire")){
						Navire cible = (Navire) getParcelle(destination);
						if(cible.estDansLEquipe(perso)){
							cible.addPersoBateau(perso);	
							if(coord.getX() > 0 && coord.getX() < this.size-1 && coord.getY() > 0 && coord.getY() < this.size-1){
								this.plateau[coord.getX()][coord.getY()] = new Sable(); 
							}else if (!bateau){
								this.plateau[coord.getX()][coord.getY()] = new Mer(); 
							}
						}else {
							JOptionPane.showMessageDialog(null, "Ce n'est pas votre bateau !");
						}
					
					}else{
						plateau[destination.getX()][destination.getY()] = perso;
						perso.setCoord(destination);
						perso.retirerEnergie(1);
						if(bateau){
							navire.rmPersoB(perso);
						}
						if(coord.getX() > 0 && coord.getX() < this.size-1 && coord.getY() > 0 && coord.getY() < this.size-1){
							this.plateau[coord.getX()][coord.getY()] = new Sable(); 
						}else if(! bateau){
							this.plateau[coord.getX()][coord.getY()] = new Mer(); 
						}
					}
				}
				perso.setaJoue(true);
			}
		}
		grille.clearHighlight();
	}
	
	public static Navire getNavire1() {
		return navire1;
	}

	public static Navire getNavire2() {
		return navire2;
	}
	public void remplirEnergie(){
		navire1.refillEnergie();
		navire2.refillEnergie();
	}
	/**
	 * 
	 *  @return la taille du plateau (longeur ou largeur)
	 */
	public int getSize(){
		return this.size;
	}
	/**
	 * Place un Explorateur quelconque sur le terrain
	 * @param x 	indice de ligne
	 * @param y		indice de colonne
	 */
	public void placerExplo(int x, int y){
		plateau[x][y] = new Explorateur(new Coordonee(x, y));
		this.plateau[x][y].setCord(new Coordonee(x, y));
	}
	
	/**
	 * Constructeur de Ile
	 * Génère les éléments du plateau
	 * @param taille	taille du tableau
	 * @param rocRate   taux de roches
	 */
	public Ile(int taille, int rocRate){
		this.grille = new Plateau(img, this.size);
		this.size = taille;
		Random r = new Random();
		this.plateau = new Parcelle[taille][taille];
		this.jeu = new int[this.size][this.size];
		for(int i = 0; i<taille; i++){
			for(int j=0; j<taille; j++){
				if(i ==0 || j==0 || i == taille-1 || j==taille-1){
					plateau[i][j] = new Mer();
				}else {
					plateau[i][j] = new Sable();
				}				
			}
		}
		int n1 = r.nextInt(taille-2) + 1;
		int n2 = r.nextInt(taille-2) + 1;
		plateau[n1][0] = new Navire();
		plateau[n2][taille-1] = new Navire();
		int rate = (int)((taille*taille)*(double)rocRate/100);
		boolean done = false;
		for(int y =0; y<rate; y++){
			while(!done){
				int cor1 = r.nextInt(taille-2)+1;
				int cor2 = r.nextInt(taille-2)+1;
				if(!( plateau[cor1][cor2].getType().equals("roche") || 
						(plateau[cor1+1][cor2].getType().equals("roche") && plateau[cor1][cor2+1].getType().equals("roche") && plateau[cor1-1][cor2].getType().equals("roche") && plateau[cor1][cor2-1].getType().equals("roche")))){
					if(y == 1){
						plateau[cor1][cor2] = new Roche('k');
						done = true;
						}else if (y == 2){
							plateau[cor1][cor2] = new Roche('c');
							done = true;
						}else{
							plateau[cor1][cor2] = new Roche();
							done = true;
						}
					} 
				}
				done = false;
			}
		for(int i = 0; i<size; i++){
			for(int j = 0; j<size; j++){
				if(plateau[i][j].getType().equals("navire")){
					if(j == 0){
						navire1 = (Navire) plateau[i][j];
					}else{
						navire2 = (Navire) plateau[i][j];	
					}
				}
			}
		}
	}
	
	public String toString() {
		
		String ligne = "";
		
		for(int i=0; i<(plateau.length*2)+1; i++) {
			for(int j=0; j<plateau[1].length; j++) {
				
				if(i%2==0){
					ligne=ligne+"+---";
				}
				else {
					if(plateau[i/2][j].toString().length() < 2){
					ligne=ligne+"| " + plateau[i/2][j].toString() + " ";
					}else{
						ligne=ligne+"| " + plateau[i/2][j].toString();
					}
				}
				if(j==plateau.length-1){
					if(i%2==0) {
				
					ligne=ligne+'+'+"\n";
						}
					else{
					ligne=ligne+"|"+"\n";
					}
				}
			}
		   			
		}
		return ligne;
	}
	public void Menu(){
		int rep = JOptionPane.showOptionDialog(null, "Que voulez-vous faire ?", 
				"Menu contextuel (PROVISOIRE)", JOptionPane.NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, 
				new String[]{"Placer un explorateur", "Placer un voleur", "Poursuivre le déroulement normal du jeu"}, 2);
		if(rep >= 0 && rep <= 1){
			int y = JOptionPane.showOptionDialog(null, "Dans quelle équipe voulez-vous placer votre personnage ?", 
					"Menu contextuel (PROVISOIRE)", JOptionPane.NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, 
					new String[]{"Equipe une (gauche)", "Equipe deux (droite)"}, 1);
			int j = JOptionPane.showOptionDialog(null, "Voulez-vous qu'il détienne un objet ?", 
					"Attributs", JOptionPane.NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, 
					new String[]{"Le Trésor", "La clée", "Rien"}, 2);
		int va;
		int xa;
		try{
			String v = JOptionPane.showInputDialog(null, "Saisissez sur quelle ligne vous voulez placer l'explorateur");
			va = Integer.parseInt(v);
			if(va >= this.size){
				System.out.println("Emplacement non valide, placement sur la ligne 0.");
				va = 0;
			}
		}catch(java.lang.NumberFormatException e){
			System.out.println("Emplacement non valide, placement sur la ligne 0.");
			va = 0;
		}
		try{
			String x = JOptionPane.showInputDialog(null, "Ainsi que la colone.");
			xa = Integer.parseInt(x);
			if(xa >= this.size){
				System.out.println("Emplacement non valide, placement sur la colonne 0.");
				xa = 0;
			}
		}catch(java.lang.NumberFormatException e){
			System.out.println("Emplacement non valide, placement sur la colonne 0.");
			xa = 0;
		}
			if(y == 0 && rep == 0){
				if(j == 0){
					Personnage p = new Explorateur('c', new Coordonee(va, xa));
					navire1.addPersoEquipe(p);
					plateau[va][xa] = p;
				}else if (j == 1){
					Personnage p = new Explorateur('k', new Coordonee(va, xa));
					navire1.addPersoEquipe(p);
					plateau[va][xa] = p;
				}else{
					Personnage p = new Explorateur(new Coordonee(va, xa));
					navire1.addPersoEquipe(p);
					plateau[va][xa] = p;
				}
			}else if(y == 1 && rep == 0){
				if(j == 0){
					Personnage p = new Explorateur('c', new Coordonee(va, xa));
					navire2.addPersoEquipe(p);
					plateau[va][xa] = p;
				}else if (j == 1){
					Personnage p = new Explorateur('k', new Coordonee(va, xa));
					navire2.addPersoEquipe(p);
					plateau[va][xa] = p;
				}else{
					Personnage p = new Explorateur(new Coordonee(va, xa));
					navire2.addPersoEquipe(p);
					plateau[va][xa] = p;
				}
			}else if(y == 0 && rep == 1){
				if(j == 0){
					Personnage p = new Voleur('c', new Coordonee(va, xa));
					navire1.addPersoEquipe(p);
					plateau[va][xa] = p;
				}else if (j == 1){
					Personnage p = new Voleur('k', new Coordonee(va, xa));
					navire1.addPersoEquipe(p);
					plateau[va][xa] = p;
				}else{
					Personnage p = new Voleur(new Coordonee(va, xa));
					navire1.addPersoEquipe(p);
					plateau[va][xa] = p;
				}
			}else if(y == 1 && rep == 1){
				if(j == 0){
					Personnage p = new Voleur('c', new Coordonee(va, xa));
					navire2.addPersoEquipe(p);
					plateau[va][xa] = p;
				}else if (j == 1){
					Personnage p = new Voleur('k', new Coordonee(va, xa));
					navire2.addPersoEquipe(p);
					plateau[va][xa] = p;
				}else{
					Personnage p = new Voleur(new Coordonee(va, xa));
					navire2.addPersoEquipe(p);
					plateau[va][xa] = p;
				}
			}
		}
	}
	/**
	 * Affichage graphique du plateau avec Plateau
	 */
	public void display(){
		for(int i = 0; i<this.size;i++){
			for(int j = 0; j<this.size; j++){
				if(plateau[i][j].getType().equals("explorateur")){
					jeu[j][i] = 5;
				}else if(plateau[i][j].getType().equals("voleur")){
					jeu[j][i] = 7;
				}else if(plateau[i][j].getType().equals("roche")){
					if(plateau[i][j].isRevealed() && plateau[i][j].getChest()){
						jeu[j][i] = 6;
					}else {
						jeu[j][i] = 1;
					}
				}else if(plateau[i][j].getType().equals("mer")){
					jeu[j][i] = 2;
				}else if(plateau[i][j].getType().equals("navire") && j == 0){
					jeu[j][i] = 3;
				}else if(plateau[i][j].getType().equals("navire") && j == this.size-1){
					jeu[j][i] = 4;
				}else{
					jeu[j][i] = 8;
				}
			}
		}
		grille.setJeu(jeu);
		grille.affichage();
	}
			
}
		
		
		
	
	
	
	


