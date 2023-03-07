package com.ubo.tp.twitub.datamodel;

/**
 * Interface des observateurs des modifications de la base de données.
 *
 * @author S.Lucas
 */
public interface IDatabaseObserver {
    /**
     * Notification lorsqu'un Twit est ajouté en base de données.
     *
     * @param addedTwit
     */
    default void notifyTwitAdded(Twit addedTwit) {
    }

    /**
     * Notification lorsqu'un Twit est supprimé de la base de données.
     *
     * @param deletedTwit
     */
    default void notifyTwitDeleted(Twit deletedTwit) {
    }

    /**
     * Notification lorsqu'un Twit est modifié en base de données.
     *
     * @param modifiedTwit
     */
    default void notifyTwitModified(Twit modifiedTwit) {
    }

    /**
     * Notification lorsqu'un utilisateur est ajouté en base de données.
     *
     * @param addedUser
     */
    default void notifyUserAdded(User addedUser) {
    }

    /**
     * Notification lorsqu'un utilisateur est supprimé de la base de données.
     *
     * @param deletedUser
     */
    default void notifyUserDeleted(User deletedUser) {
    }

    /**
     * Notification lorsqu'un utilisateur est modifié en base de données.
     *
     * @param modifiedUser
     */
    default void notifyUserModified(User modifiedUser) {
    }
}
