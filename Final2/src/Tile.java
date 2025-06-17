public class Tile {

	private final String name;

	Tile(String name) {
		this.name = name;
	}


	void landOn(Participant p, GameController ctrl) {


		if (name.toLowerCase().contains("chance")) {
			Card card = Card.drawChance();
			System.out.println("Chance: " + card.text);
			card.applyEffect(p, ctrl); 
			return;
		}

		
		if (name.toLowerCase().contains("community") && name.toLowerCase().contains("chest")) {
			Card card = Card.drawChest();
			System.out.println("Community Chest: " + card.text);
			card.applyEffect(p, ctrl);
			return;
		}

	
		Property prop = ctrl.getMapManager().getProperty(p.position);
		if (prop != null) {
			ctrl.getBuyingManager().handleSpace(p, ctrl.getPlayers(), ctrl.getMapManager());
			return;
		}


		if (name.equalsIgnoreCase("Go")) {
			p.money += 200;
			System.out.println(p.getName() + " collects $200 for landing on GO.");
		} else if (name.equalsIgnoreCase("Income Tax")) {
			
			p.money -= 200;
			ctrl.addToPot(200);
			System.out.println(p.getName() + " pays $200 Income Tax.");
		} else if (name.equalsIgnoreCase("Luxury Tax")) {
			p.money -= 100;
			ctrl.addToPot(100);
			System.out.println(p.getName() + " pays $100 Luxury Tax.");
		} else if (name.equalsIgnoreCase("Free Parking")) {
			int pot = ctrl.collectPot();
			p.money += pot;
			System.out.println(p.getName() + " lands on Free Parking and collects $" + pot + "!");
		} else if (name.toLowerCase().contains("jail")) {
			if (name.equalsIgnoreCase("Go To Jail")) {
				p.position = 10; 
				p.inJail = true;
				p.jailTurns = 0; 
				System.out.println(p.getName() + " goes directly to Jail!");
			} else { 
				System.out.println(p.getName() + " is just visiting Jail.");
			}
		}
		
	}
}