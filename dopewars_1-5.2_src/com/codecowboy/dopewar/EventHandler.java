
package com.codecowboy.dopewar;

import java.util.*;
import com.codecowboy.dopewar.items.*;

/**
 * File description
 * Handles events.
 *
 * Copyright (c) www.codecowboy.com 2001
 *
 * $RCSfile: EventHandler.java,v $
 * @author $Author: ccb $
 * @version $Revision: 1.8 $, $Date: 2001/11/27 10:03:33 $
 */
public class EventHandler
{
	private ArrayList _events = new ArrayList();
	private int _howMany;
	private final int MAX_EVENTS = 4;
	private final double eventProb = 0.4;
	private boolean hasEvent = false;
	private String priceEventMsg = "";
	private ArrayList priceEvents = new ArrayList();

	public EventHandler()
	{
		init();	
	}

	public void init()
	{
		determineEvent();

		if(hasEvent)
		{
			setUpNumberOfEvents();
			setUpEvents();
		}
	}

	private void setUpNumberOfEvents()
	{
		int result = 0;
		
		while(result > MAX_EVENTS || result < 1)
		{
			result = (int) (Math.random() * MAX_EVENTS);
		}
		
		this._howMany = result;
	}

	private void determineEvent()
	{
		double result = Math.random();		
		if(eventProb >= result)
		{
			hasEvent = true;
		}
	}

	public void resetPriceEvents()
	{
		priceEvents.clear();
	}

    @SuppressWarnings("unchecked")
	private void setUpEvents()
	{
		int MAX_COAT_EVENTS = 1;
		int MAX_POLICE_EVENTS = 1;
		int coatEventCount = 0;
		int priceEventCount = 0;
		int policeEventCount = 0;
		
		for(int i=1; i<=this._howMany; i++)
		{
			Event thisEvent = new Event();
			boolean addEvent = false;
			
			if(thisEvent.isPriceEvent())
			{
				addEvent = true;
			}
			else if(thisEvent.isCoatEvent())
			{
				if(coatEventCount < MAX_COAT_EVENTS)
				{
					addEvent = true;
					coatEventCount++;
				}
			}
			else if(thisEvent.isPoliceEvent())
			{
				if(policeEventCount < MAX_POLICE_EVENTS)
				{
					addEvent = false;
					policeEventCount++;
				}
			}
			
			if(addEvent)
			{
				_events.add(thisEvent);
			}
			else
			{
				i--;
			}
		}
	}

	public boolean hasEvents()
	{
		return hasEvent;
	}

	public int getEventCount()
	{
		return _howMany;
	}
	
	public ArrayList getList()
	{
		return _events;
	}

	private void setPriceEventMessage(String msg)
	{
		this.priceEventMsg = msg;
	}

	public String getPriceEventMessage()
	{
		return this.priceEventMsg;
	}

	/**
	 * Once the user has accepted to purchase the new coat
	 * it should be passed through this method to make the new
	 * settings.
	 */
	public Coat getNewCoat(Coat currentCoat, long newPrice, int newCapacity)
	{
		currentCoat.setPrice(newPrice);
		currentCoat.setCapacity(newCapacity);
		return currentCoat;	
	}

	/**
	 * the new coat should cost between 200 and
	 * 400 dollars more than the current coat
	 */
	public long getNewCoatPrice(long currentPrice)
	{	
		long rv = 0;

		while(rv < (currentPrice + 200) || rv > (currentPrice + 400))
		{
			rv = (int)  (Math.random() * (currentPrice + 400));
		}
				
		return rv;
	}

	/**
	 * The new coat capacity should be between 50 and 100 units larger
	 * than the current coat.
	 */
	public int getNewCoatCapacity(long currentCapacity)
	{
		int rv = 0;

		while(rv < (currentCapacity + 50) || rv > (currentCapacity + 100))
		{
			rv = (int) (Math.random() * (currentCapacity + 100));
		}

		return rv;
	}

    @SuppressWarnings("unchecked")
	public PriceList applyPriceEvent(PriceList pl, Event thisEvent)
	{
		ArrayList drugList = pl.getList();
		int max = drugList.size();
		Properties prop = pl.getProperties();
		boolean found = true;
		Drug thisDrug;
		int tgt = 0;

		while(found)
		{
			tgt = (int) (Math.random()*max);
			found = false;
			for(int i = 0; i < priceEvents.size();i++)
			{
				Integer history = (Integer) priceEvents.get(i);

				if(history.intValue() == tgt)
				{
					found = true;			
				}	
			}
		}

		if(!found)
		{
			priceEvents.add(new Integer(tgt));
			thisDrug = (Drug) drugList.get(tgt);
			thisDrug = getNewPrice(thisDrug,prop,thisEvent);
		}
		
		return pl;
	}

	private Drug getNewPrice(Drug thisDrug, Properties prop, Event thisEvent)
	{
		String upperAdd = "0";
		Integer upperVal;
		String lowerAdd = "0";
		Integer lowerVal;
		String msgAdd = "";
                String nullDetectorA = null;
                String nullDetectorB = null;
		int newPrice = 0;
		boolean spike = thisEvent.isPriceSpike();
		
		if(spike)
		
                {
			upperAdd = thisDrug.getAddress() + ".UHPL";
			lowerAdd = thisDrug.getAddress() + ".LHPL";
			msgAdd =   thisDrug.getAddress() + ".Spike_Msg";
		}
		else
		{
			upperAdd = thisDrug.getAddress() + ".ULPL";
			lowerAdd = thisDrug.getAddress() + ".LLPL";
			msgAdd =   thisDrug.getAddress() + ".Fall_Msg";
		}
                upperVal = new Integer(prop.getProperty(upperAdd));
		lowerVal = new Integer(prop.getProperty(lowerAdd));
                setPriceEventMessage(prop.getProperty(msgAdd));
		
		while(newPrice < lowerVal.intValue() || newPrice > upperVal.intValue())
		{	
			newPrice = (int) ((Math.random()) * (upperVal.intValue()));
		}
                
		thisDrug.setPrice(newPrice);
                
		return thisDrug;
            
        }
}
