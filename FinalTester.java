import java.awt.*;
import java.util.ArrayList;

import javax.swing.*;

@SuppressWarnings("serial")
public class FinalTester extends JPanel{
	public static int Sug;
	public static int Ant;
	public static int Spi;
	public static int Delay;

	public static ArrayList<Sugar> sugars = new ArrayList<Sugar>(); {
		for (int j = 0; j < Sug; ++j) {
			double hunna = (Math.random() * 1000);
			double newHun = (Math.random() * 600);
			double fiddy = (Math.random() * 4 + 1);
			boolean toxin = getRandomBoolean();
			sugars.add(new Sugar(hunna, newHun, fiddy, toxin));
		}
	}

	public static ArrayList<Ant> ants = new ArrayList<Ant>(); {
		for (int i = 0; i < Ant; ++i) {
			double hunna = (Math.random() * 1000);
			double newHun = (Math.random() * 600);
			ants.add(new Ant(hunna, newHun, i));
		}
	}

	public static ArrayList<Spider> spiders = new ArrayList<Spider>(); {
		for (int k = 0; k < Spi; ++k) {
			double hunna = (Math.random() * 1000);
			double newHun = (Math.random() * 600);
			spiders.add(new Spider(hunna, newHun, k));
		}
	}

	public void paintComponent(Graphics g) {
		for (int i = 0; i < sugars.size(); i++) {
			sugars.get(i).draw(g);
		}

		for (int i = 0; i < ants.size(); i++) {
			ants.get(i).draw(g);
		}

		for (int i = 0; i < spiders.size(); i++) {
			spiders.get(i).draw(g);
		}
	}

	private static JFrame frame;

	public static void main(String[] args) {
		if (args.length > 4) {
			System.err.println("You can't have more than four parameters!");
			System.exit(0);
		}
		if (args.length < 4) {
			System.err.println("You can't have less than four parameters!");
			System.exit(0);
		}
		for (int i = 0; i < args.length; ++i) {
			try {
				Integer.parseInt(args[i]);
			} catch (NumberFormatException nfe) {
				System.err.println("Please only enter integers.");
				System.exit(0);
			}
		}
		for (int j = 0; j < args.length; ++j) {
			if (!(Integer.parseInt(args[j]) > 0)) {
				System.err.println("Please enter only positive integers.");
				System.exit(0);
			}
		}

		Sug = Integer.parseInt(args[0]);
		Ant = Integer.parseInt(args[1]);
		Spi = Integer.parseInt(args[2]);
		Delay = Integer.parseInt(args[3]);

		frame = new JFrame();
		frame.add(new FinalTester(), BorderLayout.CENTER);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(1200, 750);
		frame.validate();
		frame.setVisible(true);
		while (ants.size() > 0) {
			try {
				Thread.sleep(Delay);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			antHunt();
		}
	}

	public static void antHunt() {
		for (int a = 0; a < spiders.size(); a++) {
			Ant closeAnt = (spiders.get(a).getClosestAnt(ants));
			spiders.get(a).moveSpider(closeAnt);
		}
		//counts how many spiders are feeding on each ant
		for (int b = 0; b < spiders.size(); b++) {
			Ant closeAnt = (spiders.get(b).getClosestAnt(ants));
			spiders.get(b).feeding(closeAnt);
		}
		//poisons ants if they are too close to the spiders, marks eaten ants as "poisoned"
		for (int c = 0; c < spiders.size(); c++) {
			Ant closeAnt = (spiders.get(c).getClosestAnt(ants));
			if (spiders.get(c).distanceToAnt(closeAnt) < 0.01) {
				spiders.get(c).eatAnt(closeAnt);
				spiders.get(c).setSpiCalCount((closeAnt.getCalCount())/(closeAnt.getPredators()));
			}
		}
		//clears away any spider-poisoned ants
		killAnts(ants);
		frame.repaint();
		if (sugars.size() > 0) {
			for (int i = 0; i < ants.size(); i++) {
				Sugar closeSug = (ants.get(i).getClosestSugar(sugars));
				ants.get(i).moveAnt(closeSug);
			}
			for (int j = 0; j < ants.size(); j++) {
				Sugar closeSug = (ants.get(j).getClosestSugar(sugars));
				ants.get(j).feeding(closeSug);
			}
			for (int k = 0; k < ants.size(); k++) {
				Sugar closeSug = (ants.get(k).getClosestSugar(sugars));
				if (ants.get(k).distanceToSugar(closeSug) < 0.01) {
					ants.get(k).eatSugar(closeSug);
					ants.get(k).setCalCount((closeSug.getCalories())/(closeSug.getEaters()));
				}
			}
		}
		else {
			for (int i = 0; i < ants.size(); i++) {
				Spider spyder = (ants.get(i).averageSpider(spiders));
				ants.get(i).runAway(spyder);
			}
		}
		killAnts(ants);
		removeSugar(sugars);
		frame.repaint();
	}

	public static void removeSugar(ArrayList<Sugar> sugars) {
		int i = 0;
		while (i < sugars.size()) {
			if (!(sugars.get(i).eatenYet())) {
				++i;
			}
			else {
				sugars.remove(i);
			}
		}
	}

	public static void killAnts(ArrayList<Ant> ants) {
		int i = 0;
		while (i < ants.size()) {
			if (!(ants.get(i).wasPoisoned())) {
				++i;
			}
			else {
				ants.remove(i);
			}
		}
	}

	public static boolean getRandomBoolean() {
		return Math.random() + 0.35 < 0.5;
	}
}
