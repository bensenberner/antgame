import java.awt.*;

public class Sugar{
	private double x;
	private double y;
	private double calories;
	private boolean eaten;
	private int antsEating;
	private boolean isPoisonous;

	public Sugar(double inX, double inY, double inCal, boolean poison) {
		this.x = inX;
		this.y = inY;
		calories = inCal;
		eaten = false;
		antsEating = 0;
		isPoisonous = poison;
	}
	//basic getters/setters/counters
	public double getX() {
		return this.x;
	}

	public double getY() {
		return this.y;
	}

	public double getCalories() {
		return this.calories;
	}

	public boolean eatenYet() {
		return this.eaten;
	}

	public double getEaters() {
		return this.antsEating;
	}

	public void antCounter() {
		++antsEating;
	}

	public void eaten() {
		this.eaten = true;
	}

	public boolean findPoison() {
		return this.isPoisonous;
	}

	public void draw(Graphics g) {
		Color c = new Color(115, 83, 255);
		if (this.isPoisonous) {
			c = new Color(0, 255, 0);
		}
		g.setColor(c);
		g.fillRect(((int) (this.x)) - 3, ((int) (this.y)) - 3, 10, 10);
	}

	public String toString() {
		return "(" + x + ", " + y + ")";
	}
}
