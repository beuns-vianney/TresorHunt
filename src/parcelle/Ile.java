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
			"images/2.navire.png","images/1.navire.png","images/1.explorateur.png",
			"images/coffre.jpg", "images/1.voleur.png", "images./sable.png", 
			"images/2.explorateur.png", "images/brouillardMer.png", "images/brouillardSable.png",
			"images/2.voleur.png", "images/explorateurmer.png", "images/voleur1mer.png", "images/voleurmer.png",
			"images/1.explorateurMer.png", "images/cle.jpg", "images/1.piegeur.png", "images/1.piegeurMer.png", "images/2.piegeur.png",
			"images/2.piegeurMer.png", "images/1.guerrier.png", "images/1.guerrierMer.png", "images/2.guerrier.png",
			"images/2.guerrierMer.png"};
	private static Parcelle[][] plateau;
	private static int size;
	private static int[][] jeu;
	private static Plateau grille1;
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
		if(coord.getX() >= 0 || coord.getX() <= size -1 || coord.getY() >= 0 || coord.getY() <= size -1){
			return plateau[coord.getX()][coord.getY()];
		}else return new Parcelle();
	}
	/**
	 *  Méthode générale pour déplacer un personnage ou lui faire éxecuter une action
	 */
	public void deplacerPerso(){
		grille1.setJeu(jeu);
		InputEvent e = grille1.waitEvent();
		Coordonee coord = new Coordonee(grille1.getX((MouseEvent) e), grille1.getY((MouseEvent) e));
		String type = "";
		if(! (coord.getX() >= size) && ! (coord.getY() >= size)){
			type = getParcelle(coord).getType();
		}
		boolean ok = false;
		boolean bateau = false;
		Personnage perso = null;
		Navire navire = null;
//------------SI BATEAU------------------------------------------------------
		if(type.equals("navire") && (Ile.tour1 && getParcelle(coord) == navire1) || (Ile.tour2 && getParcelle(coord) == navire2)){
			grille1.setHighlight(coord.getX(), coord.getY());
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
				this.Inventaire(perso);
				ok = true;
				bateau = true;
			}
		}else
//------------------------------------SI PERSONNAGE-----------------------------------------------
		if(type.equals("explorateur") || type.equals("voleur") || type.equals("piegeur") || type.equals("guerrier")){
			grille1.setHighlight(coord.getX(), coord.getY());
			perso = (Personnage) getParcelle(coord);
			this.Inventaire(perso);
			ok = true;
		}
		if(Ile.tour1 && ! navire1.estDansLEquipe(perso)){
			ok = false;
		}else if(Ile.tour2 && ! navire2.estDansLEquipe(perso)){
			ok = false;
		}
		if(ok && ! perso.getaJoue()){
			InputEvent dest = grille1.waitEvent();
			Coordonee destination = new Coordonee(grille1.getX((MouseEvent) dest), grille1.getY((MouseEvent) dest));
			

			if(perso.deplacer(getParcelle(destination).getType(), coord, destination)){
				//---------------EXPLORATEUR--------------------------------------------------
				if(perso.getType().equals("explorateur")){
				
					if(getParcelle(destination).getType().equals("roche")){
						Roche cible = (Roche) getParcelle(destination);
						if(cible.isPiege()){
							perso.retirerEnergie(35);
							this.mort(perso, coord, bateau);
							cible.resetPiege();
						}
						perso.souleverRoche((Roche)cible);
						perso.retirerEnergie(5);
						this.mort(perso, coord, bateau);
				
					}else if(getParcelle(destination).getType().equals("navire")){
						Navire cible = (Navire) getParcelle(destination);
						if(cible.estDansLEquipe(perso)){
							cible.addPersoBateau(perso);	
							if(coord.getX() > 0 && coord.getX() < size-1 && coord.getY() > 0 && coord.getY() < size-1){
								plateau[coord.getX()][coord.getY()] = new Sable(); 
							}else if( ! bateau){
								plateau[coord.getX()][coord.getY()] = new Mer(); 
							}
						}else {
							JOptionPane.showMessageDialog(null, "Ce n'est pas votre bateau !");
						}
					}else if(getParcelle(destination).getType().equals("explorateur") || getParcelle(destination).getType().equals("voleur")
							|| getParcelle(destination).getType().equals("piegeur") || getParcelle(destination).getType().equals("guerrier")){
						Personnage cible = (Personnage) getParcelle(destination);
						if(perso.memeEquipe(cible)){
							perso.Donner(cible);
							perso.retirerEnergie(1);
							this.mort(perso, coord, bateau);
						}else{
							JOptionPane.showMessageDialog(null, "Ce joueur ne fait pas partit de votre équipe !");
						}
					
					}else{
						if(plateau[destination.getX()][destination.getY()].isPiege()){
							perso.retirerEnergie(35);
							this.mort(perso, coord, bateau);
							plateau[destination.getX()][destination.getY()].resetPiege();
						}
						if(plateau[destination.getX()][destination.getY()].getKey()){
							plateau[destination.getX()][destination.getY()] = perso;
							perso.setKey(true);
						}else if(plateau[destination.getX()][destination.getY()].getChest()){
							plateau[destination.getX()][destination.getY()] = perso;
							perso.setChest(true);
						}else plateau[destination.getX()][destination.getY()] = perso;
						perso.setCoord(destination);
						perso.retirerEnergie(1);
						this.mort(perso, destination, bateau);
						if(getNavire1().estDansLEquipe(perso)){
							getNavire1().updateBrouillard(destination.getX(), destination.getY(), size);
						}else getNavire2().updateBrouillard(destination.getX(), destination.getY(), size);
						if(bateau){
							navire.rmPersoB(perso);
						}
						if(coord.getX() > 0 && coord.getX() < size-1 && coord.getY() > 0 && coord.getY() < size-1){
							plateau[coord.getX()][coord.getY()] = new Sable(); 
						}else if(! bateau){
							plateau[coord.getX()][coord.getY()] = new Mer(); 
						}
					}
			//-----------------------VOLEUR------------------------------------------------
				}else if(perso.getType().equals("voleur")){
					
					if(getParcelle(destination).getType().equals("explorateur") || getParcelle(destination).getType().equals("voleur")||
							getParcelle(destination).getType().equals("piegeur") || getParcelle(destination).getType().equals("guerrier")){
						if (! perso.memeEquipe((Personnage) getParcelle(destination))){
							Personnage cible = (Personnage) getParcelle(destination);
							perso.voler(cible);
							perso.retirerEnergie(10);
							this.mort(perso, coord, bateau);
						}else{
							Personnage cible = (Personnage) getParcelle(destination);
							perso.Donner(cible);
							perso.retirerEnergie(1);
							this.mort(perso, coord, bateau);
						}
					
					}else if(getParcelle(destination).getType().equals("navire")){
						Navire cible = (Navire) getParcelle(destination);
						if(cible.estDansLEquipe(perso)){
							cible.addPersoBateau(perso);	
							if(coord.getX() > 0 && coord.getX() < size-1 && coord.getY() > 0 && coord.getY() < size-1){
								plateau[coord.getX()][coord.getY()] = new Sable(); 
							}else if (!bateau){
								plateau[coord.getX()][coord.getY()] = new Mer(); 
							}
						}else {
							JOptionPane.showMessageDialog(null, "Ce n'est pas votre bateau !");
						}
					
					}else{
						if(plateau[destination.getX()][destination.getY()].isPiege()){
							perso.retirerEnergie(35);
							this.mort(perso, coord, bateau);
							plateau[destination.getX()][destination.getY()].resetPiege();
						}
						plateau[destination.getX()][destination.getY()] = perso;
						perso.setCoord(destination);
						perso.retirerEnergie(1);
						this.mort(perso, destination, bateau);
						if(getNavire1().estDansLEquipe(perso)){
							getNavire1().updateBrouillard(destination.getX(), destination.getY(), size);
						}else getNavire2().updateBrouillard(destination.getX(), destination.getY(), size);
						if(bateau){
							navire.rmPersoB(perso);
						}
						if(coord.getX() > 0 && coord.getX() < size-1 && coord.getY() > 0 && coord.getY() < size-1){
							plateau[coord.getX()][coord.getY()] = new Sable(); 
						}else if(! bateau){
							plateau[coord.getX()][coord.getY()] = new Mer(); 
						}
					}
			//--------------------PIEGEUR--------------------------------------------------------
				}else if(perso.getType().equals("piegeur")){
					if(getParcelle(destination).getType().equals("roche") && ! getParcelle(destination).isPiege()){
						Parcelle cible =  getParcelle(destination);
						perso.placerPiege(cible);
						perso.retirerEnergie(15);
						this.mort(perso, coord, bateau);
						grille1.println("Un piège a été placé.");
				
					}else if(getParcelle(destination).getType().equals("navire")){
						Navire cible = (Navire) getParcelle(destination);
						if(cible.estDansLEquipe(perso)){
							cible.addPersoBateau(perso);	
							if(coord.getX() > 0 && coord.getX() < size-1 && coord.getY() > 0 && coord.getY() < size-1){
								plateau[coord.getX()][coord.getY()] = new Sable(); 
							}else if( ! bateau){
								plateau[coord.getX()][coord.getY()] = new Mer(); 
							}
						}else {
							JOptionPane.showMessageDialog(null, "Ce n'est pas votre bateau !");
						}
					}else if(getParcelle(destination).getType().equals("explorateur") || getParcelle(destination).getType().equals("voleur") || 
							getParcelle(destination).getType().equals("piegeur") || getParcelle(destination).getType().equals("guerrier")){
						Personnage cible = (Personnage) getParcelle(destination);
						if(perso.memeEquipe(cible)){
							perso.Donner(cible);
							perso.retirerEnergie(1);
							this.mort(perso, coord, bateau);
						}else{
							JOptionPane.showMessageDialog(null, "Ce joueur ne fait pas partit de votre équipe !");
						}
					
					}else{
						int r;
						if(getParcelle(destination).isPiege()){
							r = 1;
						}else r = JOptionPane.showOptionDialog(null, "Que voulez-vous faire ?", 
								"Action du piegeur", JOptionPane.NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, 
								new String[]{"Placer un piège", "Se déplacer"}, 1);
						if(r == 0){
							Parcelle cible =  getParcelle(destination);
							perso.placerPiege(cible);
							perso.retirerEnergie(15);
							this.mort(perso, coord, bateau);
							grille1.println("Un piège a été placé.");
						}else{
							if(plateau[destination.getX()][destination.getY()].isPiege()){
								perso.retirerEnergie(35);
								plateau[destination.getX()][destination.getY()].resetPiege();
							}
							if(plateau[destination.getX()][destination.getY()].getKey()){
								plateau[destination.getX()][destination.getY()] = perso;
								perso.setKey(true);
							}else if(plateau[destination.getX()][destination.getY()].getChest()){
								plateau[destination.getX()][destination.getY()] = perso;
								perso.setChest(true);
							}else plateau[destination.getX()][destination.getY()] = perso;
							perso.setCoord(destination);
							perso.retirerEnergie(1);
							this.mort(perso, destination, bateau);
							if(getNavire1().estDansLEquipe(perso)){
								getNavire1().updateBrouillard(destination.getX(), destination.getY(), size);
							}else getNavire2().updateBrouillard(destination.getX(), destination.getY(), size);
							if(bateau){
								navire.rmPersoB(perso);
							}
							if(coord.getX() > 0 && coord.getX() < size-1 && coord.getY() > 0 && coord.getY() < size-1){
								plateau[coord.getX()][coord.getY()] = new Sable(); 
							}else if(! bateau){
								plateau[coord.getX()][coord.getY()] = new Mer(); 
							}
						}
					}
					//----------------------GUERRIER-------------------------------------
				}else if(perso.getType().equals("guerrier")){
					if(getParcelle(destination).getType().equals("explorateur") || getParcelle(destination).getType().equals("voleur")||
							getParcelle(destination).getType().equals("piegeur") || getParcelle(destination).getType().equals("guerrier")){
						if (! perso.memeEquipe((Personnage) getParcelle(destination))){
							Personnage cible = (Personnage) getParcelle(destination);
							perso.attaque(cible);
							perso.retirerEnergie(20);
							grille1.println("Le guerrier inflige 25 points de dégâts.");
							this.mort(perso, coord, bateau);
							this.mort(cible, destination, bateau);
						}else{
							Personnage cible = (Personnage) getParcelle(destination);
							perso.Donner(cible);
							perso.retirerEnergie(1);
							this.mort(perso, coord, bateau);
						}
					
					}else if(getParcelle(destination).getType().equals("navire")){
						Navire cible = (Navire) getParcelle(destination);
						if(cible.estDansLEquipe(perso)){
							cible.addPersoBateau(perso);	
							if(coord.getX() > 0 && coord.getX() < size-1 && coord.getY() > 0 && coord.getY() < size-1){
								plateau[coord.getX()][coord.getY()] = new Sable(); 
							}else if (!bateau){
								plateau[coord.getX()][coord.getY()] = new Mer(); 
							}
						}else {
							JOptionPane.showMessageDialog(null, "Ce n'est pas votre bateau !");
						}
					
					}else{
						if(plateau[destination.getX()][destination.getY()].isPiege()){
							perso.retirerEnergie(35);
							plateau[destination.getX()][destination.getY()].resetPiege();
							this.mort(perso, coord, bateau);
						}
						plateau[destination.getX()][destination.getY()] = perso;
						perso.setCoord(destination);
						perso.retirerEnergie(1);
						this.mort(perso, destination, bateau);
						if(getNavire1().estDansLEquipe(perso)){
							getNavire1().updateBrouillard(destination.getX(), destination.getY(), size);
						}else getNavire2().updateBrouillard(destination.getX(), destination.getY(), size);
						if(bateau){
							navire.rmPersoB(perso);
						}
						if(coord.getX() > 0 && coord.getX() < size-1 && coord.getY() > 0 && coord.getY() < size-1){
							plateau[coord.getX()][coord.getY()] = new Sable(); 
						}else if(! bateau){
							plateau[coord.getX()][coord.getY()] = new Mer(); 
						}
				}
				perso.setaJoue(true);
			}
		}
		grille1.clearHighlight();
		}
	}
	private void mort(Personnage perso, Coordonee lol, boolean bateau){
		if(perso.estMort()){
			grille1.println("Un de vos personnages est mort...");
			if(lol.getX() > 0 && lol.getX() < size-1 && lol.getY() > 0 && lol.getY() < size-1){
				plateau[lol.getX()][lol.getY()] = new Sable(); 
			}else if(! bateau){
				plateau[lol.getX()][lol.getY()] = new Mer(); 
			}
			if(perso.getChest()){
				getParcelle(lol).setChest(true);
			}else if(perso.getKey()){
				getParcelle(lol).setKey(true);
			}
		}
	}
	public static Navire getNavire1() {
		return navire1;
	}

	public static Navire getNavire2() {
		return navire2;
	}
	public static void remplirEnergie(){
		navire1.refillEnergie();
		navire2.refillEnergie();
	}
	/**
	 * 
	 *  @return la taille du plateau (longeur ou largeur)
	 */
	public int getSize(){
		return size;
	}
	/**
	 * Place un Explorateur quelconque sur le terrain
	 * @param x 	indice de ligne
	 * @param y		indice de colonne
	 */
	public void placerExplo(int x, int y){
		plateau[x][y] = new Explorateur(new Coordonee(x, y));
		plateau[x][y].setCord(new Coordonee(x, y));
	}
	
	/**
	 * Constructeur de Ile
	 * Génère les éléments du plateau
	 * @param taille	taille du tableau
	 * @param rocRate   taux de roches
	 */
	public Ile(int taille, int rocRate){
		grille1 = new Plateau(img, size, true);
		size = taille;
		Random r = new Random();
		plateau = new Parcelle[taille][taille];
		jeu = new int[size][size];
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
		navire1 = (Navire) plateau[n1][0];
		Ile.getNavire1().initPlateau(n1, 0, taille);
		plateau[n2][taille-1] = new Navire();
		navire2 = (Navire) plateau[n2][taille-1];
		Ile.getNavire2().initPlateau(n2, taille-1, taille);
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
				new String[]{"Placer un explorateur", "Placer un voleur", "Placer un piégeur", "Placer un guerrier",
						"Poursuivre le déroulement normal du jeu"}, 4);
		if(rep >= 0 && rep <= 3){
			int y = JOptionPane.showOptionDialog(null, "Dans quelle équipe voulez-vous placer votre personnage ?", 
					"Menu contextuel (PROVISOIRE)", JOptionPane.NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, 
					new String[]{"Equipe une (gauche)", "Equipe deux (droite)"}, 1);
			int j = JOptionPane.showOptionDialog(null, "Voulez-vous qu'il détienne un objet ?", 
					"Attributs", JOptionPane.NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, 
					new String[]{"Le Trésor", "La clée", "Rien"}, 2);
		int va;
		int xa;
		try{
			String v = JOptionPane.showInputDialog(null, "Saisissez sur quelle ligne vous voulez placer le personnage");
			va = Integer.parseInt(v);
			if(va >= size){
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
			if(xa >= size){
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
			}else if(y == 0 && rep == 2){
				if(j == 0){
					Personnage p = new Piegeur('c', new Coordonee(va, xa));
					navire1.addPersoEquipe(p);
					plateau[va][xa] = p;
				}else if (j == 1){
					Personnage p = new Piegeur('k', new Coordonee(va, xa));
					navire1.addPersoEquipe(p);
					plateau[va][xa] = p;
				}else{
					Personnage p = new Piegeur(new Coordonee(va, xa));
					navire1.addPersoEquipe(p);
					plateau[va][xa] = p;
				}
			}else if(y == 1 && rep == 2){
				if(j == 0){
					Personnage p = new Piegeur('c', new Coordonee(va, xa));
					navire2.addPersoEquipe(p);
					plateau[va][xa] = p;
				}else if (j == 1){
					Personnage p = new Piegeur('k', new Coordonee(va, xa));
					navire2.addPersoEquipe(p);
					plateau[va][xa] = p;
				}else{
					Personnage p = new Piegeur(new Coordonee(va, xa));
					navire2.addPersoEquipe(p);
					plateau[va][xa] = p;
				}
			}else if(y == 0 && rep == 3){
				if(j == 0){
					Personnage p = new Guerrier('c', new Coordonee(va, xa));
					navire1.addPersoEquipe(p);
					plateau[va][xa] = p;
				}else if (j == 1){
					Personnage p = new Guerrier('k', new Coordonee(va, xa));
					navire1.addPersoEquipe(p);
					plateau[va][xa] = p;
				}else{
					Personnage p = new Guerrier(new Coordonee(va, xa));
					navire1.addPersoEquipe(p);
					plateau[va][xa] = p;
				}
			}else if(y == 1 && rep == 3){
				if(j == 0){
					Personnage p = new Guerrier('c', new Coordonee(va, xa));
					navire2.addPersoEquipe(p);
					plateau[va][xa] = p;
				}else if (j == 1){
					Personnage p = new Guerrier('k', new Coordonee(va, xa));
					navire2.addPersoEquipe(p);
					plateau[va][xa] = p;
				}else{
					Personnage p = new Guerrier(new Coordonee(va, xa));
					navire2.addPersoEquipe(p);
					plateau[va][xa] = p;
				}
			}
		}
	}
	public void Inventaire(Personnage p){
		if((Ile.tour1 && navire1.estDansLEquipe(p)) || (Ile.tour2 && navire2.estDansLEquipe(p))){
			grille1.println("---------------"+p+"----------------");
			grille1.println("Energie : " + p.getEnergie() + "/100");
			if(p.getChest()){
				grille1.println("Ce personnage a le coffre sur lui.");
			}
			if(p.getKey()){
				grille1.println("Ce personnage a la clé sur lui.");
			}
			grille1.println("-------------------------------------");
			grille1.println("");
		}
	}
	/**
	 * Affichage graphique du plateau avec Plateau
	 */
	public static void display(){
		grille1.PlateauRename();
		if(Ile.tour1){
			for(int i = 0; i<size;i++){
				for(int j = 0; j<size; j++){
					if(! Ile.getNavire1().getVisible(i, j)){
						if(i == 0 || j == 0 || i == size-1 || j == size-1)
							jeu[j][i] = 10;
						else jeu[j][i] = 11;
					}else 
					if(plateau[i][j].getType().equals("explorateur") && navire1.estDansLEquipe((Personnage) plateau[i][j])){
						if(i == 0 || j == 0 || i == size-1 || j == size-1){
							jeu[j][i] = 16;
						}else{
							jeu[j][i] = 5;
						}
					}else if(plateau[i][j].getType().equals("explorateur") && navire2.estDansLEquipe((Personnage) plateau[i][j])){
						if(i == 0 || j == 0 || i == size-1 || j == size-1){
							jeu[j][i] = 13;
						}else{
							jeu[j][i] = 9;
						}
					}else if(plateau[i][j].getType().equals("piegeur") && navire1.estDansLEquipe((Personnage) plateau[i][j])){
						if(i == 0 || j == 0 || i == size-1 || j == size-1){
							jeu[j][i] = 19;
						}else{
							jeu[j][i] = 18;
						}
					}else if(plateau[i][j].getType().equals("piegeur") && navire2.estDansLEquipe((Personnage) plateau[i][j])){
						if(i == 0 || j == 0 || i == size-1 || j == size-1){
							jeu[j][i] = 21;
						}else{
							jeu[j][i] = 20;
						}
					}else if(plateau[i][j].getType().equals("guerrier") && navire1.estDansLEquipe((Personnage) plateau[i][j])){
						if(i == 0 || j == 0 || i == size-1 || j == size-1){
							jeu[j][i] = 23;
						}else{
							jeu[j][i] = 22;
						}
					}else if(plateau[i][j].getType().equals("guerrier") && navire2.estDansLEquipe((Personnage) plateau[i][j])){
						if(i == 0 || j == 0 || i == size-1 || j == size-1){
							jeu[j][i] = 25;
						}else{
							jeu[j][i] = 24;
						}	
					}else if(plateau[i][j].getType().equals("voleur") && navire1.estDansLEquipe((Personnage) plateau[i][j])){
						if(i == 0 || j == 0 || i == size-1 || j == size-1){
							jeu[j][i] = 14;
						}else{
							jeu[j][i] = 7;
						}
					}else if(plateau[i][j].getType().equals("voleur") && navire2.estDansLEquipe((Personnage) plateau[i][j])){
						if(i == 0 || j == 0 || i == size-1 || j == size-1){
							jeu[j][i] = 15;
						}else{
							jeu[j][i] = 12;
						}	
					}else if(plateau[i][j].getType().equals("roche")){
						if(plateau[i][j].isRevealed() && plateau[i][j].getChest()){
							jeu[j][i] = 6;
						}else if(plateau[i][j].isPiege() && plateau[i][j].getEquipe1()){
							//À remplacer par le sprite du piège sur le rocher.
							jeu[j][i] = 5;
						}else{
							jeu[j][i] = 1;
						}
					}else if(plateau[i][j].getType().equals("mer")){
						jeu[j][i] = 2;
					}else if(plateau[i][j].getType().equals("navire") && j == 0){
						jeu[j][i] = 3;
					}else if(plateau[i][j].getType().equals("navire") && j == size-1){
						jeu[j][i] = 4;
					}else if(plateau[i][j].getChest()){
						jeu[j][i] = 6;
					}else if(plateau[i][j].getKey()){
						jeu[j][i] = 17;
					}else if(plateau[i][j].isPiege() && plateau[i][j].getEquipe1()){
						//À remplacer par le sprite du piège.
						jeu[j][i] = 5;
					}else{
						jeu[j][i] = 8;
					}
				}
			}
			grille1.setJeu(jeu);
			grille1.affichage();
		}else{
			for(int i = 0; i<size;i++){
				for(int j = 0; j<size; j++){
					if(! Ile.getNavire2().getVisible(i, j)){
						if(i == 0 || j == 0 || i == size-1 || j == size-1)
							jeu[j][i] = 10;
						else jeu[j][i] = 11;
					}else 
						if(plateau[i][j].getType().equals("explorateur") && navire1.estDansLEquipe((Personnage) plateau[i][j])){
							if(i == 0 || j == 0 || i == size-1 || j == size-1){
								jeu[j][i] = 5;
							}else{
								jeu[j][i] = 5;
							}
						}else if(plateau[i][j].getType().equals("explorateur") && navire2.estDansLEquipe((Personnage) plateau[i][j])){
							if(i == 0 || j == 0 || i == size-1 || j == size-1){
								jeu[j][i] = 13;
							}else{
								jeu[j][i] = 9;
							}
					}else if(plateau[i][j].getType().equals("voleur") && navire1.estDansLEquipe((Personnage) plateau[i][j])){
						if(i == 0 || j == 0 || i == size-1 || j == size-1){
							jeu[j][i] = 14;
						}else{
							jeu[j][i] = 7;
						}
					}else if(plateau[i][j].getType().equals("voleur") && navire2.estDansLEquipe((Personnage) plateau[i][j])){
						if(i == 0 || j == 0 || i == size-1 || j == size-1){
							jeu[j][i] = 15;
						}else{
							jeu[j][i] = 12;
						}
					}else if(plateau[i][j].getType().equals("piegeur") && navire1.estDansLEquipe((Personnage) plateau[i][j])){
						if(i == 0 || j == 0 || i == size-1 || j == size-1){
							jeu[j][i] = 19;
						}else{
							jeu[j][i] = 18;
						}
					}else if(plateau[i][j].getType().equals("piegeur") && navire2.estDansLEquipe((Personnage) plateau[i][j])){
						if(i == 0 || j == 0 || i == size-1 || j == size-1){
							jeu[j][i] = 21;
						}else{
							jeu[j][i] = 20;
						}
					}else if(plateau[i][j].getType().equals("guerrier") && navire1.estDansLEquipe((Personnage) plateau[i][j])){
						if(i == 0 || j == 0 || i == size-1 || j == size-1){
							jeu[j][i] = 23;
						}else{
							jeu[j][i] = 22;
						}
					}else if(plateau[i][j].getType().equals("guerrier") && navire2.estDansLEquipe((Personnage) plateau[i][j])){
						if(i == 0 || j == 0 || i == size-1 || j == size-1){
							jeu[j][i] = 25;
						}else{
							jeu[j][i] = 24;
						}	
					}else if(plateau[i][j].getType().equals("roche")){
						if(plateau[i][j].isRevealed() && plateau[i][j].getChest()){
							jeu[j][i] = 6;
						}else if(plateau[i][j].isPiege() && ! plateau[i][j].getEquipe1()){
							//À remplacer par le sprite du piège sur le rocher.
							jeu[j][i] = 5;
						}else {
							jeu[j][i] = 1;
						}
					}else if(plateau[i][j].getType().equals("mer")){
						jeu[j][i] = 2;
					}else if(plateau[i][j].getType().equals("navire") && j == 0){
						jeu[j][i] = 3;
					}else if(plateau[i][j].getType().equals("navire") && j == size-1){
						jeu[j][i] = 4;
					}else if(plateau[i][j].getChest()){
						jeu[j][i] = 6;
					}else if(plateau[i][j].getKey()){
						jeu[j][i] = 17;
					}else if(plateau[i][j].isPiege() && ! plateau[i][j].getEquipe1()){
						//À remplacer par le sprite du piège.
						jeu[j][i] = 5;
					}else
						jeu[j][i] = 8;
					}
				}
			}
			grille1.setJeu(jeu);
			grille1.affichage();
		}
	public static void displayTest(){
		if(Ile.tour1){
			for(int i = 0; i<size;i++){
				for(int j = 0; j<size; j++){
					if(plateau[i][j].getType().equals("explorateur") && navire1.estDansLEquipe((Personnage) plateau[i][j])){
						if(i == 0 || j == 0 || i == size-1 || j == size-1){
							jeu[j][i] = 16;
						}else{
							jeu[j][i] = 5;
						}
					}else if(plateau[i][j].getType().equals("explorateur") && navire2.estDansLEquipe((Personnage) plateau[i][j])){
						if(i == 0 || j == 0 || i == size-1 || j == size-1){
							jeu[j][i] = 13;
						}else{
							jeu[j][i] = 9;
						}
					}else if(plateau[i][j].getType().equals("piegeur") && navire1.estDansLEquipe((Personnage) plateau[i][j])){
						if(i == 0 || j == 0 || i == size-1 || j == size-1){
							jeu[j][i] = 19;
						}else{
							jeu[j][i] = 18;
						}
					}else if(plateau[i][j].getType().equals("piegeur") && navire2.estDansLEquipe((Personnage) plateau[i][j])){
						if(i == 0 || j == 0 || i == size-1 || j == size-1){
							jeu[j][i] = 21;
						}else{
							jeu[j][i] = 20;
						}
					}else if(plateau[i][j].getType().equals("guerrier") && navire1.estDansLEquipe((Personnage) plateau[i][j])){
						if(i == 0 || j == 0 || i == size-1 || j == size-1){
							jeu[j][i] = 23;
						}else{
							jeu[j][i] = 22;
						}
					}else if(plateau[i][j].getType().equals("guerrier") && navire2.estDansLEquipe((Personnage) plateau[i][j])){
						if(i == 0 || j == 0 || i == size-1 || j == size-1){
							jeu[j][i] = 25;
						}else{
							jeu[j][i] = 24;
						}	
					}else if(plateau[i][j].getType().equals("voleur") && navire1.estDansLEquipe((Personnage) plateau[i][j])){
						if(i == 0 || j == 0 || i == size-1 || j == size-1){
							jeu[j][i] = 14;
						}else{
							jeu[j][i] = 7;
						}
					}else if(plateau[i][j].getType().equals("voleur") && navire2.estDansLEquipe((Personnage) plateau[i][j])){
						if(i == 0 || j == 0 || i == size-1 || j == size-1){
							jeu[j][i] = 15;
						}else{
							jeu[j][i] = 12;
						}	
					}else if(plateau[i][j].getType().equals("roche")){
						if(plateau[i][j].isRevealed() && plateau[i][j].getChest()){
							jeu[j][i] = 6;
						}else if(plateau[i][j].isPiege() && plateau[i][j].getEquipe1()){
							//À remplacer par le sprite du piège sur le rocher.
							jeu[j][i] = 5;
						}else{
							jeu[j][i] = 1;
						}
					}else if(plateau[i][j].getType().equals("mer")){
						jeu[j][i] = 2;
					}else if(plateau[i][j].getType().equals("navire") && j == 0){
						jeu[j][i] = 3;
					}else if(plateau[i][j].getType().equals("navire") && j == size-1){
						jeu[j][i] = 4;
					}else if(plateau[i][j].getChest()){
						jeu[j][i] = 6;
					}else if(plateau[i][j].getKey()){
						jeu[j][i] = 17;
					}else if(plateau[i][j].isPiege() && plateau[i][j].getEquipe1()){
						//À remplacer par le sprite du piège.
						jeu[j][i] = 5;
					}else{
						jeu[j][i] = 8;
					}
				}
			}
			grille1.setJeu(jeu);
			grille1.affichage();
		}else{
			for(int i = 0; i<size;i++){
				for(int j = 0; j<size; j++){ 
						if(plateau[i][j].getType().equals("explorateur") && navire1.estDansLEquipe((Personnage) plateau[i][j])){
							if(i == 0 || j == 0 || i == size-1 || j == size-1){
								jeu[j][i] = 5;
							}else{
								jeu[j][i] = 5;
							}
						}else if(plateau[i][j].getType().equals("explorateur") && navire2.estDansLEquipe((Personnage) plateau[i][j])){
							if(i == 0 || j == 0 || i == size-1 || j == size-1){
								jeu[j][i] = 13;
							}else{
								jeu[j][i] = 9;
							}
					}else if(plateau[i][j].getType().equals("voleur") && navire1.estDansLEquipe((Personnage) plateau[i][j])){
						if(i == 0 || j == 0 || i == size-1 || j == size-1){
							jeu[j][i] = 14;
						}else{
							jeu[j][i] = 7;
						}
					}else if(plateau[i][j].getType().equals("voleur") && navire2.estDansLEquipe((Personnage) plateau[i][j])){
						if(i == 0 || j == 0 || i == size-1 || j == size-1){
							jeu[j][i] = 15;
						}else{
							jeu[j][i] = 12;
						}
					}else if(plateau[i][j].getType().equals("piegeur") && navire1.estDansLEquipe((Personnage) plateau[i][j])){
						if(i == 0 || j == 0 || i == size-1 || j == size-1){
							jeu[j][i] = 19;
						}else{
							jeu[j][i] = 18;
						}
					}else if(plateau[i][j].getType().equals("piegeur") && navire2.estDansLEquipe((Personnage) plateau[i][j])){
						if(i == 0 || j == 0 || i == size-1 || j == size-1){
							jeu[j][i] = 21;
						}else{
							jeu[j][i] = 20;
						}
					}else if(plateau[i][j].getType().equals("guerrier") && navire1.estDansLEquipe((Personnage) plateau[i][j])){
						if(i == 0 || j == 0 || i == size-1 || j == size-1){
							jeu[j][i] = 23;
						}else{
							jeu[j][i] = 22;
						}
					}else if(plateau[i][j].getType().equals("guerrier") && navire2.estDansLEquipe((Personnage) plateau[i][j])){
						if(i == 0 || j == 0 || i == size-1 || j == size-1){
							jeu[j][i] = 25;
						}else{
							jeu[j][i] = 24;
						}	
					}else if(plateau[i][j].getType().equals("roche")){
						if(plateau[i][j].isRevealed() && plateau[i][j].getChest()){
							jeu[j][i] = 6;
						}else if(plateau[i][j].isPiege() && ! plateau[i][j].getEquipe1()){
							//À remplacer par le sprite du piège sur le rocher.
							jeu[j][i] = 5;
						}else {
							jeu[j][i] = 1;
						}
					}else if(plateau[i][j].getType().equals("mer")){
						jeu[j][i] = 2;
					}else if(plateau[i][j].getType().equals("navire") && j == 0){
						jeu[j][i] = 3;
					}else if(plateau[i][j].getType().equals("navire") && j == size-1){
						jeu[j][i] = 4;
					}else if(plateau[i][j].getChest()){
						jeu[j][i] = 6;
					}else if(plateau[i][j].getKey()){
						jeu[j][i] = 17;
					}else if(plateau[i][j].isPiege() && ! plateau[i][j].getEquipe1()){
						//À remplacer par le sprite du piège.
						jeu[j][i] = 5;
					}else
						jeu[j][i] = 8;
					}
				}
			}
			grille1.setJeu(jeu);
			grille1.affichage();
	}
}	

		
		
		
	
	
	
	


