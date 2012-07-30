package com.infosense.ibilling.server.ws;

/**
 * Interface that marks a Web Service object as being "secure" within an entity. <code>WSSecured</code> objects
 * may only be accessed and modified by web service users (callers) within the same entity as the object being
 * accessed/modified.
 *
 * Implementing classes must be able to provide <strong>either</strong> an entity id or
 * user id for the owner of the object.
 */
public interface WSSecured {

    /**
     * Returns the entity ID of the company owning the secure object, or null
     * if the entity ID is not available.
     *
     * @return owning entity ID
     */
    public Integer getOwningEntityId();

    /**
     * Returns the user ID of the user owning the secure object, or null if the
     * user ID is not available.
     *
     * @return owning user ID
     */
    public Integer getOwningUserId();

}
