package OLD.de.sprax2013.mc_skin_db.skin;

import java.util.HashSet;
import java.util.Set;

import de.sprax2013.skindb.back_end.utils.RemoteDatabaseUtils;

public class Tag {
	private final int id;
	private final String identifier;
	private Set<String> synonyms = new HashSet<>();
	private Set<Integer> similar = new HashSet<>();

	public Tag(String identifier) {
		this.id = -1;
		this.identifier = identifier;
	}

	public Tag(int id, String identifier, Set<String> synonyms, Set<Integer> similar) {
		this.id = id;
		this.identifier = identifier;

		this.synonyms = synonyms == null ? new HashSet<>() : synonyms;
		this.similar = similar == null ? new HashSet<>() : similar;
	}

	public int getID() {
		return this.id;
	}

	public String getIdentifier() {
		return this.identifier;
	}

	public Set<String> getSynonyms() {
		return this.synonyms;
	}

	public boolean isSynonym(String synonym) {
		for (String s : this.synonyms) {
			if (s.equalsIgnoreCase(synonym)) {
				return true;
			}
		}

		return false;
	}

	public void addSynonym(String synonym) {
		this.synonyms.add(synonym);

		save();
	}

	public Set<Integer> getSimilar() {
		return this.similar;
	}

	public boolean isSimilar(int tagID) {
		return this.similar.contains(tagID);
	}

	public void addSimilar(Tag tag) {
		this.similar.add(tag.getID());

		save();
	}

	private void save() {
		RemoteDatabaseUtils.setTag(this);
	}
}