import java.util.*;

public class CardLinkedList {

	private static class CardNode {
		Card card;
		CardNode next;

		CardNode(Card c) {
			card = c;
		}
	}

	private CardNode head;

	CardLinkedList() {
		head = null;
	}

	void insert(Card c) {
		CardNode n = new CardNode(c);
		if (head == null) {
			head = n;
		} else {
			CardNode t = head;
			while (t.next != null) {
				t = t.next;
			}
			t.next = n;
		}
	}

	Card draw() {
		if (head == null) {
			return null;
		}
		CardNode top = head;
		head = head.next;
		insert(top.card);
		return top.card;
	}

	void shuffle() {
		int count = 0;
		CardNode t = head;
		while (t != null) {
			count++;
			t = t.next;
		}

		if (count <= 1)
			return;

		Card[] arr = new Card[count];
		t = head;
		int i = 0;
		while (t != null) {
			arr[i] = t.card;
			t = t.next;
			i++;
		}

		Random rnd = new Random();
		for (int k = count - 1; k > 0; k--) {
			int j = rnd.nextInt(k + 1);
			Card tmp = arr[k];
			arr[k] = arr[j];
			arr[j] = tmp;
		}

		head = null;
		for (int i1 = 0; i1 < arr.length; i1++) {
			insert(arr[i1]);
		}
	}
}