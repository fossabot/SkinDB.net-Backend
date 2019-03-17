package de.sprax2013.skindb.backend.constructors;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.UUID;

import de.sprax2013.advanced_dev_utils.misc.UUIDUtils;

public class PendingData {
	private final String data;

	public PendingData(String data) {
		this.data = data;
	}

	public URL getAsURL() {
		try {
			return new URL(data);
		} catch (MalformedURLException ex) {
			ex.printStackTrace();
		}

		return null;
	}

	public UUID getAsUUID() {
		try {
			return UUID.fromString(UUIDUtils.addDashesToUUID(data));
		} catch (IllegalArgumentException ex) {
			ex.printStackTrace();
		}

		return null;
	}

	public boolean isURL() {
		try {
			new URL(data).toString();

			return true;
		} catch (@SuppressWarnings("unused") MalformedURLException ignore) {
		}

		return false;
	}

	public boolean isUUID() {
		try {
			UUID.fromString(UUIDUtils.addDashesToUUID(data));

			return true;
		} catch (@SuppressWarnings("unused") IllegalArgumentException ignore) {
		}

		return false;
	}

	@Override
	public String toString() {
		return this.data;
	}
}