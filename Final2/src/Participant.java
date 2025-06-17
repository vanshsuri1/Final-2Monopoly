public class Participant {

	public Player core;
	public int money;
	public int position;
	public boolean bankrupt;
	public boolean inJail;
	public boolean hasGetOutOfJailCard;
	public int jailTurns;
	public int doublesStreak;
	public boolean doubleRentNextRR;

	public Participant(String name) {
		core = new Player(name);
		money = 1500;
		position = 0;
		bankrupt = false;
		inJail = false;
		hasGetOutOfJailCard = false;
		jailTurns = 0;
		doublesStreak = 0;
		doubleRentNextRR = false;
	}

	public String getName() {
		return core.getName();
	}

	public void move(int steps) {
		int oldPos = position;
		position = (position + steps) % 40;

		if (!inJail && position < oldPos) {
			money += 200;
			System.out.println(getName() + " collects $200 for passing GO.");
		}
	}

	public int getNetWorth() {
		int total = money;
		int loc = 0;
		while (loc < 40) {
			Property pr = core.getOwnedProperties().searchByLocation(loc);
			if (pr != null) {
				total += pr.getPrice();
			}
			loc++;
		}
		return total;
	}

	public void buy(Property p) {
		core.buyProperty(p);
	}

	public boolean tryLeaveJail(int die1, int die2) {
		jailTurns++;
		if (die1 == die2) {
			inJail = false;
			jailTurns = 0;
			System.out.println(getName() + " rolled doubles and leaves Jail.");
			return true;
		}

		if (jailTurns == 3) {
			System.out.println(getName() + " served 3 turns and pays $50.");
			if (money >= 50) {
				money -= 50;
			} else {
				bankrupt = true;
			}
			inJail = false;
			jailTurns = 0;
			return true;
		}

		return false;
	}
}