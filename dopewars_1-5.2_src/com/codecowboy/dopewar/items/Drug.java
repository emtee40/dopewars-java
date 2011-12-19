
package com.codecowboy.dopewar.items;

/**
 * File description
 * represents a drug. 
 *
 * Copyright (c) www.codecowboy.com 2001
 *
 * $RCSfile: Drug.java,v $
 * @author $Author: ccb $
 * @version $Revision: 1.4 $, $Date: 2001/11/11 21:40:06 $
 */
public class Drug
{
	private String _name;
	private int _price;
	private String _address;

	public Drug(String name, 
				String address) 
	{
		setName(name);
		setAddress(address);
	}

	private void setName(String name)
	{
		this._name = name;
	}

	public void setPrice(int price)
	{
		this._price = price;
	}

	public String getName()
	{
		return this._name;
	}

	public int getPrice()
	{
		return this._price;
	}

	private void setAddress(String address)
	{
		this._address = address;
	}

	public String getAddress()
	{
		return this._address;
	}
}
