package plateau;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

/**
 * La classe GraphicPane permet d'afficher un plateau de Jeu carré
 * sur lequel sont disposés des images représentant les éléments du jeu
 * Les images sont toutes de même taille et carrées.
 * @author M2103-Team
 */
class GraphicPane extends JPanel {
	private static final String ERR_NOT_INIT_MSG = "jeu non initialisé" ;
	private static final long serialVersionUID = 2L;
	private int nbImages;
	private int nbLig ; // taille (en nombre de lignes). par défaut: paramètre taille du constructeur.
	private int nbCol ; // taille (en nombre de colonnes). par défaut: paramètre taille du constructeur.
	private ImageIcon[] images;
	private int dimImage;
	private int[][] jeu;
	private boolean[][] highlight = null ;
	/**
	 * Construit un plateau de jeu vide de dimension taille x taille.
	 * Initialement, les cellules sont vides. Le constructeur demande la fourniture
	 * d'un catalogue d'images gif ou png.
	 * @param gif tableau 1D des chemins des fichiers des différentes images affichées.
	 * @param taille dimension (en nombre de cellules) d'un côté du plateau.
	 */
	public GraphicPane(String[] gif,int taille){
		jeu = null ;
		// TODO Rendre la spécification de taille optionnelle (la calculer à partir du tableau d'entier)
		// TODO Affichage d'un message d'erreur si fichier non trouvé.
		// TODO Vérifier l'uniformité de taille des images	
		nbLig = taille ;	
		nbCol = taille ;
		dimImage = 15 ; // Taille par défaut d'une case.
		if (gif!=null){
			nbImages=gif.length;
			images=new ImageIcon[nbImages];	
			for (int i=0;i<nbImages;i++){
				java.net.URL imageURL = Plateau.class.getResource(imagesPath[i]);
				   if (imageURL != null) {
				      images[i] = new ImageIcon(imageURL);
				   } else { // Traitement  si image non trouv�e
				      System.err.println("Image : '" + imagesPath[i]+ "' non trouv�e") ;
				      images[i] = notFound ;
				   }
			}
			dimImage=images[0].getIconHeight()+2;
			setGraphicSize() ;
			this.setBackground(Color.LIGHT_GRAY);
		} 
	}
	public Dimension getGraphicSize() {
		return new Dimension(nbCol * dimImage, nbLig * dimImage) ;
	}
	private void setGraphicSize() {
		this.setPreferredSize(getGraphicSize());
	}
	private void showText(Graphics g, String msg) {
		Dimension dimPage = this.getPreferredSize() ;
		Rectangle dimText = g.getFontMetrics().getStringBounds(ERR_NOT_INIT_MSG, g).getBounds() ;
		// Calcule la position du message pour qu'il soit centré.
		int x0 = (dimPage.width - dimText.width) / 2 ;
		int y0 = (dimPage.height - dimText.height) / 2 ;
		
		g.setColor(Color.BLACK) ;
		g.drawString(ERR_NOT_INIT_MSG, x0, y0) ;
	}
	/**
	 * Dessine la surbrillance autour de la cellule placée en w/l (coord. graphiques).
	 * La cellule fait dimImage x dimImage.
	 * @param w la position en largeur.
	 * @param h la position en hauteur.
	 */
	private void highlight(Graphics g, int w,int h) {
		g.setColor(Color.YELLOW);
		g.drawRect(w-1, h-1, dimImage-2, dimImage-2);
		g.setPaintMode();
	}
	/**
	 * Méthode d'affichage du composant (utilisée par Swing. Ne pas appeler directement).
	 */
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		if (jeu != null) {
			Dimension size=this.getSize();
			int w=2,h=1,lig=0,col=0;
			g.setColor( Color.white );
			while ((h<size.height) && (lig < nbLig)) {
				while ((w<size.width) && (col < nbCol)) {
					if (jeu[col][lig]!=0) {
						g.drawImage(images[jeu[col][lig]-1].getImage(),w,h,null);
						if (highlight[lig][col]) {
							highlight(g, w, h) ;
						}
					}
					else
						g.drawRect(w-1, h-1, dimImage-2, dimImage-2);
					w+=dimImage;
					col++;
				}
				lig++;
				col=0;
				w=2;
				h+=dimImage;
			}
		} else {
			this.showText(g, ERR_NOT_INIT_MSG) ;
		}
	}
	public void clearHighlight() {
		highlight = new boolean[nbLig][nbCol] ;
		for (int c = 0 ; c < nbCol ; c++) {
			for (int l = 0 ; l < nbLig ; l++) {
				highlight[l][c] = false ;
			}
		}
	}
	public void setHighlight(int x, int y) {
		if (highlight != null) {
			highlight[x][y] = true ;
		}
	}
	public void resetHighlight(int x, int y) {
		if (highlight != null) {
			highlight[x][y] = false ;
		}
	}
	public boolean isHighlight(int x, int y) {
		return highlight[x][y] ;
	}
	/**
	 * Méthode permettant de placer les éléments sur le plateau. 
	 * Le tableau doit être de même taille que la dimension déclarée via le constructeur.
	 * @param jeu tableau 2D représentant le plateau  
	 * la valeur numérique d'une cellule désigne l'image correspondante
	 * dans le tableau des chemins (décalé de un, 0 désigne une case vide)
	 */
	public void setJeu(int[][] jeu){
		// Calcule nbLig et nbCol en fonction de la taille réelle du tableau d'entier
		if (jeu != null) {
			this.jeu=jeu;	
			nbCol = jeu.length ;
			nbLig = jeu[0].length ;
			setGraphicSize() ;
			clearHighlight() ;
		}
	}
	public int getX(MouseEvent event) {
		return ((event.getY() - 2) / dimImage) ;
	}
	public int getY(MouseEvent event) {
		return ((event.getX() - 1) / dimImage) ;
	}
	/**
	 * Retourne le tableau d'entiers représentant le plateau
	 * @return le tableau d'entiers
	 */
	public int[][] getJeu(){return jeu;}

}

