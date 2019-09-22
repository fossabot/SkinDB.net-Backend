package de.sprax2013.skindb.backend.queue;

import java.sql.Timestamp;

public class QueueObject {
    private final long ID;

    private final String skinURL, textureValue, textureSignature,
            userAgent;

    private final QueueStatus status;

    private final long skinID;

    private final Timestamp added;

    public QueueObject(long ID, String skinURL, String textureValue, String textureSignature, String userAgent,
                       QueueStatus status, long skinID, Timestamp added) {
        this.ID = ID;
        this.skinURL = skinURL;
        this.textureValue = textureValue;
        this.textureSignature = textureSignature;
        this.userAgent = userAgent;
        this.status = status;
        this.skinID = skinID;
        this.added = added;
    }

    long getID() {
        return ID;
    }

    String getSkinURL() {
        return skinURL;
    }

    String getTextureValue() {
        return textureValue;
    }

    String getTextureSignature() {
        return textureSignature;
    }

    @SuppressWarnings("unused")
    String getUserAgent() {
        return userAgent;
    }

    @SuppressWarnings("unused")
    QueueStatus getStatus() {
        return status;
    }

    long getSkinID() {
        return skinID;
    }

    @SuppressWarnings("unused")
    Timestamp getAdded() {
        return added;
    }
}