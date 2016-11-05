package org.softshack.utils.obs;

/**
 * The event handler interface
 *
 * @param <T>
 *            The type that is used to hold the event information.
 */

public interface EventHandler<T extends EventArgs> {
	void handle(Object sender, T args);
}