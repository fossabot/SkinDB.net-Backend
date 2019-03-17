package de.sprax2013.skindb.backend.constructors;

import de.sprax2013.skindb.backend.utils.DatabaseUtils;

public class Pending {
	private final int id;

	private final PendingData data;
	private Integer skinID;
	private final String userAgent;

	private PendingStatus status;

	public Pending(int id, String data, Integer skinID, String userAgent, PendingStatus status) {
		this.id = id;

		this.data = new PendingData(data);
		this.skinID = skinID;
		this.userAgent = userAgent;

		this.status = status;
	}

	public int getID() {
		return this.id;
	}

	public PendingData getData() {
		return this.data;
	}

	public void setSkinID(Integer skinID) {
		this.skinID = skinID;
	}

	public boolean hasSkinID() {
		return this.skinID != null;
	}

	public int getSkinID() {
		return this.skinID;
	}

	public boolean hasUserAgent() {
		return this.userAgent != null;
	}

	public String getUserAgent() {
		return this.userAgent;
	}

	public void setStatus(PendingStatus status) {
		this.status = status;
	}

	public boolean hasStatus() {
		return this.status != null;
	}

	public PendingStatus getStatus() {
		return this.status;
	}

	public void save() {
		DatabaseUtils.setPending(this);
	}
}