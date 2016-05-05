package plateau;
/**
 * superPlateau est la classe permettant l'affichage graphique du plateau de jeu
 * @author Siocbo
 *
 */

public class superPlateau {
	private Plateau p;
	/**
	 * Constructeur de superPlateau
	 * 
	 * @param gifs un tableau contenant les adresses relatives des images que l'on veut voir dans le plateau
	 * @param taille la taille du plateau désiré
	 */
	public superPlateau(String[] gifs, int taille){
		this.p = new Plateau(gifs, taille);
	}
	/**
	 * affiche le plateau sous forme graphique
	 */
	public void affichage() {
		p.affichage();
	}
	/**
	 * Récupère le plateau à un instant t
	 * @return l'état du jeu sous forme de tableau d'entiers
	 */
	public int[][] getJeu() {
		return p.getJeu();
	}
	/**
	 * Update le plateau à un instant t
	 * @param jeu tableau d'entiers que l'on veut afficher
	 */
	public void setJeu(int[][] jeu) {
		p.setJeu(jeu);
	}

}
