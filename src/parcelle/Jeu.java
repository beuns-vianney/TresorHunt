package parcelle;

import javax.swing.JOptionPane;

public class Jeu {
	public static void main(String[] args) {
		int ta;
		int ra;
		try{
			String t = JOptionPane.showInputDialog(null, "saisissez la taille de l'Ile.");
			ta = Integer.parseInt(t);
		}catch(java.lang.NumberFormatException e){
			System.out.println("Valeur par défaut choisie : 10");
			ta = 10;
		}
		try{
			String r = JOptionPane.showInputDialog(null, "saisissez le taux de roches.");
			ra = Integer.parseInt(r);
		}catch (java.lang.NumberFormatException e1){
			System.out.println("Valeur par défaut choisie : 10");
			ra = 10;
		}
		Ile jeu = new Ile(ta, ra);
		Ile.display();
	//	String v = JOptionPane.showInputDialog(null, "Saisissez sur quelle ligne vous voulez placer l'explorateur");
	//	String x = JOptionPane.showInputDialog(null, "Ainsi que la colone.");
	//	int va = Integer.parseInt(v);
	//	int xa = Integer.parseInt(x);		
	//	jeu.placerExplo(va, xa);
	//	jeu.display();
		while(! Ile.fin){
			// jeu.Menu();
			jeu.deplacerPerso();
			Ile.display();
			jeu.remplirEnergie();
		}
	}
}
