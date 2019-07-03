package de.sprax2013.skindb.backend.constructors;

import java.sql.Timestamp;

import de.sprax2013.skindb.backend.utils.DatabaseUtils;

public class Skin {
	private final int id;

	private final String mojangURL;

	private final String cleanHash;

	private boolean hasOverlay, hasSteveArms;

	private final Integer duplicateOf;

	private final Timestamp knownSince;

	public Skin(String mojangURL, String cleanHash, boolean hasOverlay, boolean hasSteveArms, Integer duplicateOf) {
		this(-1, mojangURL, cleanHash, hasOverlay, hasSteveArms, duplicateOf, null);
	}

	public Skin(int id, String mojangURL, String cleanHash, boolean hasOverlay, boolean hasSteveArms,
			Integer duplicateOf, Timestamp knownSince) {
		this.id = id;

		this.mojangURL = mojangURL;

		this.cleanHash = cleanHash;

		this.hasOverlay = hasOverlay;
		this.hasSteveArms = hasSteveArms;

		this.duplicateOf = duplicateOf;

		this.knownSince = knownSince;
	}

	public boolean hasID() {
		return this.id != -1;
	}

	public int getID() {
		return this.id;
	}

	public String getMojangURL() {
		return this.mojangURL;
	}

	public String getCleanHash() {
		return this.cleanHash;
	}

	public void hasOverlay(boolean hasOverlay) {
		this.hasOverlay = hasOverlay;
	}

	public boolean hasOverlay() {
		return this.hasOverlay;
	}

	public void hasSteveArms(boolean hasSteveArms) {
		this.hasSteveArms = hasSteveArms;
	}

	public boolean hasSteveArms() {
		return this.hasSteveArms;
	}

	public boolean isDuplicate() {
		return this.duplicateOf != null && this.duplicateOf >= 0;
	}

	public Integer getDuplicateOf() {
		return this.duplicateOf;
	}

	public Timestamp getKnownSince() {
		return this.knownSince;
	}

	public void save() {
		DatabaseUtils.setSkin(this);
	}
}