package de.sprax2013.skindb.backend.queue;

@SuppressWarnings("unused")
public enum QueueStatus {
    QUEUED, UNKNOWN_ERROR, SUCCESS, DUPLICATE, INVALID_DATA, WRONG_DIMENSIONS, NON_WHITELISTED_URL;
}