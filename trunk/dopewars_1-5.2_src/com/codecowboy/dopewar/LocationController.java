package com.codecowboy.dopewar;

import java.util.*;
import java.io.*;
import com.codecowboy.dopewar.locations.*;
/**
 * File description
 * A handler for locations.
 * Copyright (c) www.codecowboy.com 2001
 *
 * $RCSfile: LocationController.java,v $
 * @author $Author: ccb $
 * @version $Revision: 1.3 $, $Date: 2001/11/27 10:03:34 $
 */

public class LocationController
{
	private ArrayList _locs = new ArrayList();
	private final String PROP_LOC_FILE = "/locations.properties";
	private Properties locProp;
	
	public LocationController()
	{
		init();
	}

	private void init()
	{
		try
		{
			InputStream in = getClass().getResourceAsStream(PROP_LOC_FILE);
			locProp = new Properties();
			locProp.load(in);
		}
		catch(IOException ioe)
		{
			ioe.printStackTrace();
		}
		load();
	}

	private void load()
	{
		for(int i=1; ; i++)
		{
			String propName = "dw.location_" + i + ".name";
			String name = locProp.getProperty(propName);
			
			if(name == null)
			{
				break;
			}
			
			Location thisLocation = new Location(name);
			_locs.add(thisLocation);
		}		
	}

	public Location getStartLocation()
	{
		return getLocation("Manhattan");
	}

	public ArrayList toList(Location currentLoc)
	{
		String currentName = currentLoc.getName();
		ArrayList rv = new ArrayList();
		
		for(int i=0; i < _locs.size(); i++)
		{
			Location thisLoc = (Location) _locs.get(i);

			if(! currentName.equals(thisLoc.getName()))
			{
				rv.add(thisLoc);
			}
		}

		return rv;
	}

	public String[] toArray(Location currentLoc)
	{
		String[] rv = new String[_locs.size()-1];
		String currentName = currentLoc.getName();
		int j = 0;

		for(int i=0; i < _locs.size();i++)
		{
			Location thisLocation = (Location) _locs.get(i);
			String name  = thisLocation.getName();
			if(! currentName.equals(name))
			{
				rv[j] = name;
				j++;
			}
		}
		return rv;	
	}

	public Location getLocation(String locName)
	{
		Location rv = new Location();
		String pre = "dw.location_";
		int place = 1;
		for(int i=0; i < _locs.size();i++)
		{
			String name = locProp.getProperty(pre + place + ".name");
			String bank = locProp.getProperty(pre + place + ".bank");
			String loan = locProp.getProperty(pre + place + ".loan");
			place++;

			if(name.equals(locName))
			{
				boolean bankBool = false;
				boolean loanBool = false;
				
				if("true".equals(bank))
				{
					bankBool = true;
				}

				if("true".equals(loan))
				{
					loanBool = true;
				}
				
				rv = new Location(name, loanBool, bankBool);

				break;
			}
		}

		return rv;
	}
	
}
