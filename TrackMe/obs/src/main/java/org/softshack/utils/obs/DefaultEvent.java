package org.softshack.utils.obs;

import java.util.ArrayList;
import java.util.List;

/**
 * The basic implementation for the event interface.
 * 
 * @param <T>
 *            The type that is used to hold the event information.
 */
public class DefaultEvent<T extends EventArgs> implements Event<T> {

	// The list of registered event handler for this event.
	private List<EventHandler<T>> _eventHandlers;

	public DefaultEvent()
	{
		_eventHandlers = new ArrayList<EventHandler<T>>();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see Event#addHandler(EventHandler)
	 */
	@Override
	public void addHandler(EventHandler<T> handler)
	{
		_eventHandlers.add(handler);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see Event#removeHandler(EventHandler)
	 */
	@Override
	public void removeHandler(EventHandler<T> handler)
	{
		_eventHandlers.remove(handler);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see Event#fire(java.lang.Object, T)
	 */
	@Override
	public void fire(Object sender, T args)
	{
		for (EventHandler<T> handler : _eventHandlers)
		{
			handler.handle(sender, args);
		}
	}
}