package OLD.de.sprax2013.mc_skin_db.skin;

import java.util.HashSet;
import java.util.Set;

import OLD.de.sprax2013.mc_skin_db.util.RemoteDatabaseUtils;

public class Skin {
	private final int id;

	private final String url, fileHash;

	private Set<Integer> tags;

	private Integer duplicate;

	public Skin(String url, String fileHash, Integer duplicate) {
		this.id = -1;

		this.url = url;
		this.fileHash = fileHash;

		this.tags = new HashSet<>();

		this.duplicate = duplicate;
	}

	public Skin(int id, String url, String fileHash, Set<Integer> tags, Integer duplicate) {
		this.id = id;

		this.url = url;
		this.fileHash = fileHash;

		this.tags = tags == null ? new HashSet<>() : tags;

		this.duplicate = duplicate;
	}

	public int getID() {
		return this.id;
	}

	public String getURL() {
		return this.url;
	}

	public String getFileHash() {
		return this.fileHash;
	}

	public Set<Integer> getTags() {
		return this.tags;
	}

	public void addTag(int tagID) {
		this.tags.add(tagID);

		save();
	}

	public void removeTag(int tagID) {
		this.tags.remove(tagID);

		save();
	}

	public boolean hasTag(int tagID) {
		return this.tags.contains(tagID);
	}

	public Integer getDuplicate() {
		return this.duplicate;
	}

	public boolean hasDuplicate() {
		return this.duplicate != null;
	}

	private void save() {
		RemoteDatabaseUtils.setSkin(this);
	}
}