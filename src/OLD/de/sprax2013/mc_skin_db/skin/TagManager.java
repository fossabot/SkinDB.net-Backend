package OLD.de.sprax2013.mc_skin_db.skin;

import java.util.ArrayList;
import java.util.List;

import de.sprax2013.skindb.back_end.utils.RemoteDatabaseUtils;

public class TagManager {
	private static List<Tag> tags;

	public static List<Tag> getTags() {
		if (tags == null) {
			refreshCache();
		}

		return tags;
	}

	public static void refreshCache() {
		tags = RemoteDatabaseUtils.getTags();
	}

	public static Tag getTag(int tagID) {
		for (Tag tag : getTags()) {
			if (tag.getID() == tagID) {
				return tag;
			}
		}

		return null;
	}

	public static List<Tag> getTags(String searchQuery) {
		return getTags(searchQuery, true);
	}

	public static List<Tag> getTags(String searchQuery, boolean includeContains) {
		List<Tag> result = new ArrayList<>();

		for (Tag tag : getTags()) {
			if (tag.getIdentifier().equalsIgnoreCase(searchQuery) || tag.isSynonym(searchQuery)) {
				result.add(tag);
			} else if (includeContains && tag.getIdentifier().toLowerCase().contains(searchQuery.toLowerCase())) {
				result.add(tag);
			} else {
				for (Integer tagID : tag.getSimilar()) {
					Tag similarTag = getTag(tagID);

					if (similarTag != null) {
						if (tag.getIdentifier().equalsIgnoreCase(searchQuery) || tag.isSynonym(searchQuery)) {
							result.add(similarTag);
						} else if (includeContains && (tag.getIdentifier().toLowerCase().contains(searchQuery)
								|| tag.getSynonyms().stream().anyMatch(t -> {
									return t.toLowerCase().contains(searchQuery.toLowerCase());
								}))) {
							result.add(similarTag);
						}
					}
				}
			}
		}

		return result;
	}

	public static Tag getTagOrCreate(String searchQuery) {
		List<Tag> searchResult = getTags(searchQuery, false);

		if (searchResult.isEmpty()) {
			RemoteDatabaseUtils.setTag(new Tag(searchQuery));

			refreshCache();

			return getTags(searchQuery).get(0);
		}

		return searchResult.get(0);
	}
}