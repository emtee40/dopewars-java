
package com.codecowboy.dopewar;

/**
 * File description
 * represents an event, such as a price hike or drop etc.
 *
 * Copyright (c) www.codecowboy.com 2001
 *
 * $RCSfile: Event.java,v $
 * @author $Author: ccb $
 * @version $Revision: 1.9 $, $Date: 2001/11/27 10:03:33 $
 */
public class Event
{
	private int _noDrugs = 0;
	private boolean _priceEvent = false;
	private boolean _coatEvent = false;
	private boolean _policeEvent = false;
	private boolean _priceUp = false;
	public static final int NO_EVENTS = 2;
	public static final String PRICE_EVENT = "price_event";
	public static final String COAT_EVENT = "coat_event";
	public static final String POLICE_EVENT = "police_event";
	//probability of a price event
	private double _typeProb = 0.8;
	//probability of a price hike
	private double _priceSpikeEventProb = 0.5;
	
	public Event()
	{
		init();
	}
	public Event(String eventType)
	{
		init(eventType);
	}

	public void init(String eventType)
	{
		if(eventType.equals(PRICE_EVENT))
		{
			setPriceEvent();
		}
		else if(eventType.equals(COAT_EVENT))
		{
			setCoatEvent();
		}
		else if(eventType.equals(POLICE_EVENT))
		{
			setPoliceEvent();
		}
	}

	public void init()
	{
		double rand = Math.random();

		if(rand <= 0.6)
		{
			setPriceEvent();
		}
		else if(rand > 0.6 && rand < 0.8)
		{
			setPoliceEvent();
		}
		else
		{
			setCoatEvent();
		}
	}
	
	public void setPriceEvent()
	{
		this._priceEvent = true;

		if(_priceSpikeEventProb >= (Math.random()))
		{
			_priceUp = true;
		}
	}

	public void setPoliceEvent()
	{
		this._policeEvent = true;
	}

	public boolean isPriceSpike()
	{
		return _priceUp;
	}

	public void setCoatEvent()
	{
		this._coatEvent = true;
	}

	public boolean isCoatEvent()
	{
		return _coatEvent;
	}

	public boolean isPriceEvent()
	{
		return _priceEvent;
	}

	public boolean isPoliceEvent()
	{
		return _policeEvent;
	}

	public String toString()
	{
		StringBuffer rv = new StringBuffer();
		
		if(_priceEvent)
		{
			rv.append("PriceEvent\n");
		}
		else if(_coatEvent)
		{
			rv.append("CoatEvent\n");
		}
		else if(_policeEvent)
		{
			rv.append("PoliceEvent\n");
		}
		
		return rv.toString();
	}
}
