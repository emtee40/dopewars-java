
package com.codecowboy.dopewar.locations;

/**
 * File description
 * represents a location.
 *
 * Copyright (c) www.codecowboy.com 2001
 *
 * $RCSfile: Location.java,v $
 * @author $Author: ccb $
 * @version $Revision: 1.5 $, $Date: 2001/11/19 13:56:55 $
 */
public class Location
{
	private String _name;
	private boolean _canBank;
	private boolean _canLoan;

	public Location(String name, boolean canLoan, boolean canBank)
	{
		setName(name);
		setCanLoan(canLoan);
		setCanBank(canBank);
	}

	public Location(String name)
	{
		setName(name);
		setCanLoan(false);
		setCanBank(false);
	}

	public Location()
	{

	}

	private void setName(String name)
	{
		this._name = name;
	}

	public String getName()
	{
		return this._name;
	}

	public void setCanBank(boolean canBank)
	{
		_canBank = canBank;
	}

	public boolean canBank()
	{
		return _canBank;
	}

	public void setCanLoan(boolean canLoan)
	{
		_canLoan = canLoan;
	}

	public boolean canLoan()
	{
		return _canLoan;
	}

	public String toString()
	{
		StringBuffer rv = new StringBuffer();
		rv.append("Name: " + this._name);
		return rv.toString();
	}
}
