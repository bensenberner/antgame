import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;


public class Ant{
	private double ID;
	private double x;
	private double y;
	private double calCount;
	private boolean poisoned;
	private int spidersEating;
	private BufferedImage img = new BufferedImage(10, 10, 2);

	public Ant(double inX, double inY, double identity) {
		this.x = inX;
		this.y = inY;
		this.ID = identity;
		this.calCount = 0;
		this.poisoned = false;
		this.spidersEating = 0;
	}
	//basic getters for instance variables
	public double getX() {
		return this.x;
	}

	public double getY() {
		return this.y;
	}

	public double getID() {
		return this.ID;
	}

	public double getCalCount() {
		return this.calCount;
	}

	public double getPredators() {
		return this.spidersEating;
	}

	public boolean wasPoisoned() {
		return this.poisoned;
	}
	//emulates the distance method from the Point class
	public double distanceToSugar(Sugar sweet) {
		double dx = sweet.getX() - x;
		double dy = sweet.getY() - y;
		return Math.sqrt(dx*dx + dy*dy);
	}

	public double distanceToSpider(Spider spi) {
		double dx = spi.getX() - x;
		double dy = spi.getY() - y;
		return Math.sqrt(dx*dx + dy*dy);
	}

	public void translate(double dx, double dy) {
		x += dx;
		y += dy;
	}
	//basic setters
	public void setCalCount(double food) {
		this.calCount += food;
	}

	public void toxify() {
		this.poisoned = true;
	}
	//counts how many ants are feeding on a sugar (multiple ants invoke it on a single sugar)
	public void feeding(Sugar aSugar) {
		if (this.distanceToSugar(aSugar) < 0.01) {
			aSugar.antCounter();
		}
	}
	//counts how many spiders are feeding on a given ant
	public void spiderCounter() {
		++this.spidersEating;
	}
	//marks a sugar as "eaten," poisons the ant if the sugar is poisonous
	public void eatSugar(Sugar aSug) {
		aSug.eaten();
		if (aSug.findPoison()) {
			this.toxify();
		}
	}
	//finds the closest sugar to a given ant out of all the sugars
	public Sugar getClosestSugar(ArrayList<Sugar> sugars) {
		Sugar sucrose = new Sugar(0, 0, 0, false);
		double sugarDist = 1000;
		for (int i = 0; i < sugars.size(); i++) {
			if (this.distanceToSugar(sugars.get(i)) < sugarDist) {
				sugarDist = this.distanceToSugar(sugars.get(i));
				sucrose = sugars.get(i);	
			}
		}
		return sucrose;
	}

	//moves an ant to a sugar (the closest is usually the parameter)
	public void moveAnt(Sugar aSugar) {
		double sugarDistX = (aSugar.getX() - this.getX());
		double sugarDistY = (aSugar.getY() - this.getY());
		double sugarDistZSquare = ((sugarDistX * sugarDistX) + (sugarDistY * sugarDistY));
		double sugarDistZ = Math.sqrt(sugarDistZSquare);
		double newX = (sugarDistX/sugarDistZ);
		double newY = (sugarDistY/sugarDistZ);
		if (this.distanceToSugar(aSugar) < 1) {
			this.translate(sugarDistX, sugarDistY);
		}
		else {
			this.translate(newX, newY);
		}
		//System.out.println("Ant " + this.ID + " is at: " + this.toString());
		//System.out.println("Ant " + this.ID + " ate this much: " + this.getCalCount());
	}
	//finds an "average spider" from the average of all the spiders' coordinates
	public Spider averageSpider(ArrayList<Spider> spiders) {
		Spider spy = new Spider(0, 0, 0);
		double spyX = 0;
		double spyY = 0;
		for (int i = 0; i < spiders.size(); ++i) {
			spyX += spiders.get(i).getX();
			spyY += spiders.get(i).getY();
		}
		spyX = (spyX / spiders.size());
		spyY = (spyY / spiders.size());
		spy.translate(spyX, spyY);
		return spy;
	}
	//moves one unit away from this "average spider"
	public void runAway(Spider aSpider) {
		double spiderDistX = (aSpider.getX() - this.getX());
		double spiderDistY = (aSpider.getY() - this.getY());
		double spiderDistZSquare = ((spiderDistX * spiderDistX) + (spiderDistY * spiderDistY));
		double spiderDistZ = Math.sqrt(spiderDistZSquare);
		double newX = -(spiderDistX/spiderDistZ);
		double newY = -(spiderDistY/spiderDistZ);
		this.translate(newX, newY);
		//System.out.println("Ant " + this.ID + " is at: " + this.toString());
		//System.out.println("Ant " + this.ID + " ate this much: " + this.getCalCount());
	}
	//rewrite the directory to this file
	public void draw(Graphics g) {
		Graphics2D g2d = (Graphics2D)g;
		try {
			img = ImageIO.read(new File ("/Users/benjaminlerner/Documents/Columbia/COMS1004/FINAL/ant.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		g2d.drawImage(img, (int) this.x - 7, (int) this.y - 1, null);
	}
	//use this ONLY if the actual draw method is not working
	/*
	public void draw(Graphics g) {
		Color c = new Color(72, 14, 14);
		g.setColor(c);
		g.fillRect((int) (this.x), (int) (this.y), 7, 7);
	}
	 */
	public String toString() {
		return "(" + x + ", " + y + ")";
	}
}
