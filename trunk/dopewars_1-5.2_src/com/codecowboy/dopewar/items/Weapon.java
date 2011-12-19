
package com.codecowboy.dopewar.items;

/**
 * File description
 * represents a weapon 
 *
 * Copyright (c) www.codecowboy.com 2001
 *
 * $RCSfile: Weapon.java,v $
 * @author $Author: ccb $
 * @version $Revision: 1.2 $, $Date: 2001/10/31 02:52:41 $
 */
public class Weapon
{
	private String _name;
	private double _price;
	private int _damage;

	public Weapon(String name, double price, int damage)
	{
		
	}

	private void setName(String name)
	{
		this._name = name;
	}

	private void setPrice(double price)
	{
		this._price = price;
	}

	private void setDamage(int damage)
	{
		this._damage = damage;
	}

	public String getName()
	{
		return this._name;
	}

	public double getPrice()
	{
		return this._price;
	}

	public int getDamage()
	{
		return this._damage;
	}
}
