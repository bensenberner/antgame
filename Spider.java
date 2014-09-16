import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import javax.imageio.ImageIO;

public class Spider{
	private double ID;
	private double spiCalCount;
	private double x;
	private double y;
	private BufferedImage img = new BufferedImage(10, 10, 2);

	public Spider(double inX, double inY, double identity) {
		this.x = inX;
		this.y = inY;
		this.ID = identity;
		this.spiCalCount = 0;
	}
	//basic getters and setters and Point class emulators
	public double getX() {
		return this.x;
	}

	public double getY() {
		return this.y;
	}

	public double getID() {
		return this.ID;
	}

	public void setSpiCalCount(double food) {
		this.spiCalCount += food;
	}

	public double getSpiCalCount() {
		return this.spiCalCount;
	}

	public double distanceToAnt(Ant ant) {
		double dx = ant.getX() - x;
		double dy = ant.getY() - y;
		return Math.sqrt(dx*dx + dy*dy);
	}

	public void translate(double dx, double dy) {
		x += dx;
		y += dy;
	}
	//adds to an ants' "spiderCounter" which keeps track of how many spiders are eating it
	public void feeding(Ant ant) {
		if (this.distanceToAnt(ant) < 0.01) {
			ant.spiderCounter();
		}
	}
	//kills ants
	public void eatAnt(Ant ant) {
		ant.toxify();
	}
	//finds the closest ant
	public Ant getClosestAnt(ArrayList<Ant> ants) {
		Ant sucrose = new Ant(0, 0, 0);
		double antDist = 1000;
		for (int i = 0; i < ants.size(); i++) {
			if (this.distanceToAnt(ants.get(i)) < antDist) {
				antDist = this.distanceToAnt(ants.get(i));
				sucrose = ants.get(i);	
			}
		}
		return sucrose;
	}
	//moves towards that ant (or any ant)
	public void moveSpider(Ant ant) {
		double antDistX = (ant.getX() - this.getX());
		double antDistY = (ant.getY() - this.getY());
		double antDistZSquare = ((antDistX * antDistX) + (antDistY * antDistY));
		double antDistZ = Math.sqrt(antDistZSquare);
		double newX = (2 + (this.getSpiCalCount() / 7)) * (antDistX/antDistZ);
		double newY = (2 + (this.getSpiCalCount() / 7)) * (antDistY/antDistZ);
		if (this.distanceToAnt(ant) < (2 + (this.getSpiCalCount() / 7))) {
			this.translate(antDistX, antDistY);
		}
		else {
			this.translate(newX, newY);
		}
		//	System.out.println("Spider " + this.ID + " is at: " + this.toString());
		//	System.out.println("Spider " + this.ID + " ate this much: " + this.getSpiCalCount());
	}
	//rewrite the directory to this file
	public void draw(Graphics g) {
		Graphics2D g2d = (Graphics2D)g;
		try {
			img = ImageIO.read(new File ("/Users/benjaminlerner/Documents/Columbia/COMS1004/FINAL/spider.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		g2d.drawImage(img, ((int) this.x) - 7, ((int) this.y) - 1, null);
	}
	//use this ONLY if the original draw function isn't working
	/*	
	public void draw(Graphics g) {
		Color c = new Color(0, 0, 0);
		g.setColor(c);
		g.fillRect((int) (this.x), (int) (this.y), 10, 10);
	}
	 */

	public String toString() {
		return "(" + x + ", " + y + ")";
	}
}
