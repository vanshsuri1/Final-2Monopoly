public class BuildingManager {

    /* ===========================================================
     * PUBLIC ENTRY – attempt to build on a specific property
     =========================================================== */
    public boolean build(Participant p, Property target, MapManager board) {
        int cost = canBuildHouse(p, target, board);
        if (cost == 0) return false;  // build not allowed

        if (p.money < cost) {
            System.out.println("Not enough cash.");
            return false;
        }

        boolean ok = target.buildHouse();  // does 4 → hotel itself
        if (!ok) {
            System.out.println("Cannot build further.");
            return false;
        }

        p.money -= cost;
        System.out.println("Built on " + target.getName() + ". New rent $" + target.getRent());
        return true;
    }

    /* ===========================================================
     * Determine if a house/hotel MAY be built.
     * Returns the build cost or 0 when illegal.
     =========================================================== */
    public int canBuildHouse(Participant p, Property target, MapManager board) {
        if (!"property".equals(target.getType())) return 0;  // RR / utility
        if (target.isMortgaged()) return 0;

        Property[] set = colourSetOwned(target, p);
        if (set == null) return 0;  // not full set

        /* even-build rule */
        int min = 5, max = -1;
        for (Property pr : set) {
            int h = pr.getHouseCount();
            min = Math.min(min, h);
            max = Math.max(max, h);
        }
        if (target.getHouseCount() > min) return 0;  // uneven

        if (target.hasHotel()) return 0;

        return target.getHouseCost();
    }

    /* ---------- colour-set helpers ---------- */
    private Property[] colourSetOwned(Property sample, Participant owner) {
        String key = colourKey(sample);
        int needed = ("Med".equals(key) || "Bal".equals(key) ||
                      "Par".equals(key)) ? 2 : 3;  // brown & dark-blue = 2

        Property[] list = new Property[needed];
        int count = 0;
        for (int loc = 0; loc < 40; loc++) {
            Property pr = owner.core.getOwnedProperties().searchByLocation(loc);
            if (pr != null && colourKey(pr).equals(key)) {
                list[count++] = pr;
                if (count == needed) break;
            }
        }
        return count == needed ? list : null;
    }

    private String colourKey(Property p) {
        return p.getName().length() < 3 ? p.getName() : p.getName().substring(0, 3);
    }
}