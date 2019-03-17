package de.sprax2013.skindb.backend.constructors;

import de.sprax2013.skindb.backend.utils.DatabaseUtils;

public class Skin {
	private final int id;

	private final String mojangURL;

	private final String hash;

	private boolean has4pxArms;

	private final Integer duplicateOf;

	public Skin(int id, String mojangURL, String hash, boolean has4pxArms) {
		this(id, mojangURL, hash, has4pxArms, -1);
	}

	public Skin(String mojangURL, String hash, boolean has4pxArms, Integer duplicateOf) {
		this(-1, mojangURL, hash, has4pxArms, duplicateOf);
	}

	public Skin(int id, String mojangURL, String hash, boolean has4pxArms, Integer duplicateOf) {
		this.id = id;

		this.mojangURL = mojangURL;

		this.hash = hash;

		this.has4pxArms = has4pxArms;

		this.duplicateOf = duplicateOf;
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

	public String getHash() {
		return this.hash;
	}

	public void has4pxArms(boolean has4pxArms) {
		this.has4pxArms = has4pxArms;
	}

	public boolean has4pxArms() {
		return this.has4pxArms;
	}

	public boolean isDuplicate() {
		return this.duplicateOf != null;
	}

	public Integer getDuplicateOf() {
		return this.duplicateOf;
	}

	public void save() {
		DatabaseUtils.setSkin(this);
	}
}