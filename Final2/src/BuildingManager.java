public class BuildingManager {

	
	public boolean build(Participant p, Property target, MapManager board) {
		int cost = canBuildHouse(p, target, board);
		if (cost == 0)
			return false; // build not allowed

		if (p.money < cost) {
			System.out.println("Not enough cash.");
			return false;
		}

		boolean ok = target.buildHouse(); 
		if (!ok) {
			System.out.println("Cannot build further.");
			return false;
		}

		p.money -= cost;
		System.out.println("Built on " + target.getName() + ". New rent $" + target.getRent());
		return true;
	}

	
	public int canBuildHouse(Participant p, Property target, MapManager board) {
		if (!"property".equals(target.getType()))
			return 0; 
		if (target.isMortgaged())
			return 0;

		Property[] set = colourSetOwned(target, p);
		if (set == null)
			return 0; 

		/* even-build rule */
		int min = 5, max = -1;
		for (int i = 0; i < set.length; i++) {
			Property pr = set[i];
			int h = pr.getRentLevelsIndex();
			if (h < min)
				min = h;
			if (h > max)
				max = h;
		}

		if (target.getRentLevelsIndex() > min)
			return 0; 

		if (target.hasHotel())
			return 0;

		return target.getHouseCost();
	}

	
	private Property[] colourSetOwned(Property sample, Participant owner) {
		String key = colourKey(sample);
		int needed = 3; // Default to 3 for most colors

		if ("Med".equals(key) || "Bal".equals(key) || "Par".equals(key)) {
			needed = 2; // brown & dark-blue = 2
		}

		Property[] list = new Property[needed];
		int count = 0;
		for (int loc = 0; loc < 40; loc++) {
			Property pr = owner.core.getOwnedProperties().searchByLocation(loc);
			if (pr != null && colourKey(pr).equals(key)) {
				list[count++] = pr;
				if (count == needed)
					break;
			}
		}

		if (count == needed)
			return list;
		else
			return null;
	}

	private String colourKey(Property p) {
		String name = p.getName();
		if (name.length() < 3) {
			return name;
		}
		return name.substring(0, 3);
	}


	public int calculateRent(Property property) {
		int baseRent = property.getBaseRent();

		
		int rent = baseRent;
		if (property.hasHotel()) {
			rent = baseRent * 5; 
		} else {
			int houseCount = property.getHouseCount();
			if (houseCount > 0) {
				rent += houseCount * 20; 
			}
		}

		return rent;
	}
}