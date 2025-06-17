public class Property {

    private int location = -1;
    private String name;
    private String type;
    private int price;
    private int baseRent;
    private int houseCost;
    private int[] rentLevels;
    private int mortgageValue;
    private boolean mortgaged = false;

    private int houseCount = 0;
    private boolean hasHotel = false;

    public Property(int location, String name, String type, int price, int rent) {
        this.location = location;
        this.name = name;
        this.type = type;
        this.price = price;
        this.baseRent = rent;
        this.houseCost = 50; // Default house cost (can change as per game rules)
        this.rentLevels = new int[1];
        this.rentLevels[0] = rent;
        this.mortgageValue = price / 2;
    }

    public Property(String name, int price, int houseCost, int[] rentTable, int mortgage, String type) {
        this.name = name;
        this.price = price;
        this.houseCost = houseCost;
        this.type = type;
        this.mortgageValue = mortgage;

        this.rentLevels = new int[rentTable.length];
        for (int i = 0; i < rentTable.length; i++) {
            this.rentLevels[i] = rentTable[i];
        }

        this.baseRent = rentTable[0];  // Base rent is the first value in the rent table
    }

    public int getLocation() {
        return location;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public int getPrice() {
        return price;
    }

    public int getBaseRent() {
        return baseRent;
    }

    public int getHouseCost() {
        return houseCost;
    }

    public int getMortgageValue() {
        return mortgageValue;
    }

    public boolean isMortgaged() {
        return mortgaged;
    }

    public void setMortgaged(boolean value) {
        mortgaged = value;
    }

    public boolean buildHouse() {
        if (!"property".equals(type)) return false;  // Cannot build on railroad or utility
        if (hasHotel) return false;  // Already has hotel
        if (houseCount < 4) {
            houseCount++;  // Increment the number of houses
            return true;
        }
        houseCount = 4;
        hasHotel = true;  // Convert 4 houses to hotel
        return true;
    }

    public int getRent() {
        int index = 0;

        if (hasHotel) {
            index = rentLevels.length - 1;  // Last rent is for hotel
        } else {
            if (houseCount < rentLevels.length) {
                index = houseCount;
            } else {
                index = rentLevels.length - 1;  // Return the highest rent if more houses are built than allowed
            }
        }
        return rentLevels[index];
    }

    public int getHouseCount() {
        return houseCount;
    }

    public boolean hasHotel() {
        return hasHotel;
    }

    public void print() {
        System.out.print("[" + name + "]  $" + price);
        System.out.print("  Rent $" + getRent());
        System.out.print("  (" + type + ")");
        if (houseCount > 0) {
            System.out.print("  Houses=" + houseCount);
        }
        if (hasHotel) {
            System.out.print("  [HOTEL]");
        }
        if (mortgaged) {
            System.out.print("  [MORTGAGED]");
        }
        System.out.println();
    }
}