package de.sprax2013.skindb.backend.skins;

import java.sql.Timestamp;

public class Skin {
    private final long ID;

    private final String hash, skinURL, textureValue, textureSignature;

    private final boolean hasOverlay, isAlex;

    private final long duplicateOf;

    private final Timestamp added;

    public Skin(long ID, String hash, String skinURL, String textureValue, String textureSignature,
                boolean hasOverlay, boolean isAlex, long duplicateOf, Timestamp added) {
        this.ID = ID;

        this.hash = hash;
        this.skinURL = skinURL;
        this.textureValue = textureValue;
        this.textureSignature = textureSignature;

        this.hasOverlay = hasOverlay;
        this.isAlex = isAlex;
        this.duplicateOf = duplicateOf;

        this.added = added;
    }

    public long getID() {
        return ID;
    }

    @SuppressWarnings("unused")
    public String getHash() {
        return hash;
    }

    public String getSkinURL() {
        return skinURL;
    }

    @SuppressWarnings("unused")
    public String getTextureValue() {
        return textureValue;
    }

    @SuppressWarnings("unused")
    public String getTextureSignature() {
        return textureSignature;
    }

    @SuppressWarnings("unused")
    public boolean hasOverlay() {
        return hasOverlay;
    }

    @SuppressWarnings("unused")
    public boolean isAlex() {
        return isAlex;
    }

    public long getDuplicateOf() {
        return duplicateOf;
    }

    public boolean isDuplicate() {
        return duplicateOf > 0;
    }

    @SuppressWarnings("unused")
    public Timestamp getAdded() {
        return added;
    }
}