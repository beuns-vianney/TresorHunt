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
		int rep = JOptionPane.showOptionDialog(null, "Quel mode de jeu choisissez-vous ?", 
				"Menu", JOptionPane.NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, 
				new String[]{"Le mode TEST", "Le jeu normal"}, 1);
		if(rep == 0){
			Ile.displayTest();
			while(! Ile.fin){
				jeu.Menu();
				Ile.displayTest();
				jeu.deplacerPerso();
				Ile.displayTest();
			}
		}else while(! Ile.fin){
			jeu.deplacerPerso();
			Ile.display();
		}
	}
}
