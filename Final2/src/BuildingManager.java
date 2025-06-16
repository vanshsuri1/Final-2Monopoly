import java.util.ArrayList;

public class BuildingManager {

    // Define the field to track if the next railroad rent is doubled
    private boolean doubleRentNextRR = false;

    /* ===========================================================
       PUBLIC ENTRY – attempt to build on a specific property
     =========================================================== */
    public boolean build(Participant p, Property target, MapManager board) {
        int cost = canBuildHouse(p, target, board);
        if (cost == 0) return false; // build not allowed

        if (p.money < cost) {
            System.out.println("Not enough cash.");
            return false;
        }

        boolean ok = target.buildHouse(); // does 4 → hotel itself
        if (!ok) {
            System.out.println("Cannot build further.");
            return false;
        }

        p.money -= cost;
        System.out.println("Built on " + target.getName() + ".   New rent $" + target.getRent());
        return true;
    }

    /* ===========================================================
       Determine if a house/hotel MAY be built.
       Returns the build cost or 0 when illegal.
     =========================================================== */
    public int canBuildHouse(Participant p, Property target, MapManager board) {

        // Can't build on non-property spaces (railroad / utility)
        if (!"property".equals(target.getType())) return 0;

        // Can't build on mortgaged properties
        if (target.isMortgaged()) return 0;

        // Ensure player owns the full color set
        Property[] set = colourSetOwned(target, p);
        if (set == null) return 0; // not full set

        /* even-build rule: The difference between the max and min house count should not exceed 1 */
        int min = 5, max = -1;
        for (Property pr : set) {
            int houseCount = pr.getHouseCount();
            min = Math.min(min, houseCount);
            max = Math.max(max, houseCount);
        }
        if (max - min > 1) return 0; // uneven number of houses across color set

        // Can't build if there is already a hotel
        if (target.hasHotel()) return 0;

        // Return the cost to build a house
        return target.getHouseCost();
    }

    /* ---------- Colour-set helpers ---------- */
    private Property[] colourSetOwned(Property sample, Participant owner) {

        String key = colourKey(sample);
        int needed = (key.equals("Med") || key.equals("Bal") || key.equals("Par")) ? 2 : 3; // Brown & Dark-Blue = 2

        ArrayList<Property> list = new ArrayList<>();
        for (int loc = 0; loc < 40; loc++) {
            Property pr = owner.core.getOwnedProperties().searchByLocation(loc);
            if (pr != null && colourKey(pr).equals(key)) {
                list.add(pr);
            }
        }

        return list.size() == needed ? list.toArray(new Property[0]) : null;
    }

    private String colourKey(Property p) {
        return p.getName().length() < 3 ? p.getName() : p.getName().substring(0, 3);
    }

    /* ===========================================================
       Helper method to augment the rent price based on number of houses built.
     =========================================================== */
    public int calculateRent(Property property) {
        int baseRent = property.getBaseRent();

        // If property has a hotel, augment the rent significantly
        if (property.hasHotel()) {
            return baseRent * 5; // Hotel rent increases significantly
        } else {
            int houseCount = property.getHouseCount();
            if (houseCount > 0) {
                return baseRent + (houseCount * 20); // Rent increases with each house
            }
        }

        // If no houses or hotels, return the base rent
        return baseRent;
    }

    /* ===========================================================
       Handle Double Rent for the next Railroad (Triggered by Chance card)
     =========================================================== */
    public void setDoubleRentForNextRR(boolean value) {
        doubleRentNextRR = value;
    }

    public boolean isDoubleRentForNextRR() {
        return doubleRentNextRR;
    }

    /* ===========================================================
       Handles the rent payment logic for Railroads with a "Double Rent" rule
     =========================================================== */
    public int getRailroadRent(Participant p, Property rr) {
        int rent = rr.getRent(); // Regular rent for the railroad

        // If double rent is triggered, we double the regular rent
        if (doubleRentNextRR) {
            rent = rent * 2;
            System.out.println("Double rent applied for the next Railroad! Rent is now: $" + rent);
            doubleRentNextRR = false; // Reset the double rent flag after use
        }

        return rent;
    }
}