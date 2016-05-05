package coordonee;

public class Coordonee {
	private int x;
	private int y;
	
	public Coordonee(int x, int y){
		this.x = x;
		this.y = y;
	}
	public Coordonee aDroite(Coordonee coord){
		return new Coordonee(coord.getX(), coord.getY()+1);
	}
	public Coordonee aGauche(Coordonee coord){
		return new Coordonee(coord.getX(), coord.getY()-1);
	}
	public Coordonee enHaut(Coordonee coord){
		return new Coordonee(coord.getX()-1, coord.getY());
	}
	public Coordonee enBas(Coordonee coord){
		return new Coordonee(coord.getX()+1, coord.getY());
	}
	public Coordonee hautDroit (Coordonee coord){
		return new Coordonee(coord.getX()-1, coord.getY()+1);
	}
	public Coordonee hautGauche (Coordonee coord){
		return new Coordonee(coord.getX()-1, coord.getY()-1);
	}
	public Coordonee basDroit (Coordonee coord){
		return new Coordonee(coord.getX()+1, coord.getY()+1);
	}
	public Coordonee basGauche (Coordonee coord){
		return new Coordonee(coord.getX()+1, coord.getY()-1);
	}
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	public String toString(){
		return "ligne : " + x + " - colonne : " + y;
	}
	public boolean equals(Coordonee coord){
		if (this.getX() == coord.getX() && this.getY() == coord.getY()){
			return true;
		}else return false;
	}
}
