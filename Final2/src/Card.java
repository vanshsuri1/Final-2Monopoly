public class Card {

	/* ---------- descriptive fields ---------- */
	public String text; // full message shown to the player
	public String type; // "Chance" or "Community Chest"

	/* ---------- raw text (16 per deck) ---------- */
	private static final String[] CHANCE_TEXTS = { "Advance to Boardwalk.", "Advance to Go (Collect $200).",
			"Advance to Illinois Avenue. If you pass Go, collect $200.",
			"Advance to St. Charles Place. If you pass Go, collect $200.",
			"Advance to the nearest Railroad. If unowned, you may buy it. If owned, pay double rent.",
			"Advance to the nearest Railroad. If unowned, you may buy it. If owned, pay double rent.",
			"Advance token to nearest Utility. If unowned, you may buy it. If owned, throw dice and pay 10× dice roll.",
			"Bank pays you dividend of $50.", "Get Out of Jail Free.", "Go Back 3 Spaces.",
			"Go to Jail. Go directly to Jail, do not pass Go, do not collect $200.",
			"Make general repairs on all your property. For each house pay $25. For each hotel pay $100.",
			"Speeding fine $15.", "Take a trip to Reading Railroad. If you pass Go, collect $200.",
			"You have been elected Chairman of the Board. Pay each player $50.",
			"Your building loan matures. Collect $150." };

	private static final String[] CHEST_TEXTS = { "Advance to Go (Collect $200)",
			"Bank error in your favor. Collect $200", "Doctor’s fee. Pay $50", "From sale of stock you get $50",
			"Get Out of Jail Free", "Go to Jail. Go directly to jail, do not pass Go, do not collect $200",
			"Holiday fund matures. Receive $100", "Income tax refund. Collect $20",
			"It is your birthday. Collect $10 from every player", "Life insurance matures. Collect $100",
			"Pay hospital fees of $100", "Pay school fees of $50", "Receive $25 consultancy fee",
			"You are assessed for street repair. $40 per house. $115 per hotel",
			"You have won second prize in a beauty contest. Collect $10", "You inherit $100" };

	/* ---------- the two decks ---------- */
	private static final CardLinkedList chanceDeck = new CardLinkedList();
	private static final CardLinkedList chestDeck = new CardLinkedList();

	/* ---------- static block builds + shuffles once ---------- */
	static {
		int i = 0;
		while (i < CHANCE_TEXTS.length) {
			chanceDeck.insert(new Card(CHANCE_TEXTS[i], "Chance"));
			i = i + 1;
		}
		i = 0;
		while (i < CHEST_TEXTS.length) {
			chestDeck.insert(new Card(CHEST_TEXTS[i], "Community Chest"));
			i = i + 1;
		}
		chanceDeck.shuffle();
		chestDeck.shuffle();
	}

	/* ---------- private constructor ---------- */
	private Card(String txt, String tp) {
		text = txt;
		type = tp;
	}

	/* ---------- draw helpers ---------- */
	public static Card drawChance() {
		return chanceDeck.draw();
	}

	public static Card drawChest() {
		return chestDeck.draw();
	}

	/*
	 * ========================================================== applyEffect
	 * ==========================================================
	 */
	public void applyEffect(Participant player, GameController ctrl) {
		// Handle Chance card effects here...
	}
}