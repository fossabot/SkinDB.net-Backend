package de.sprax2013.skindb.backend.constructors;

public enum PendingStatus {
	UNKNOWN_ERROR(0), SUCCESS(1), DUPLICATE(2), INVALID_DATA(3), WRONG_DIMENSIONS(4), NON_WHITELISTED_URL(5);

	private final int id;

	private PendingStatus(int id) {
		this.id = id;
	}

	public int getID() {
		return this.id;
	}

	public static PendingStatus getByID(int id) {
		for (PendingStatus pS : values()) {
			if (pS.id == id) {
				return pS;
			}
		}

		return null;
	}
}