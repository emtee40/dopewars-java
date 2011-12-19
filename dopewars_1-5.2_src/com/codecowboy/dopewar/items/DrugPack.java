
package com.codecowboy.dopewar.items;

/**
 * File description
 * Repesents a set of drugs owned by the player.
 *
 * Copyright (c) www.codecowboy.com 2001
 *
 * $RCSfile: DrugPack.java,v $
 * @author $Author: ccb $
 * @version $Revision: 1.3 $, $Date: 2001/11/27 10:03:33 $
 */
public class DrugPack
{
	private Drug _theDrug;
	private int _qty;
	private long _avg;

	public DrugPack()
	{
	
	}
	
	public DrugPack(Drug theDrug, int qty, long avg)
	{
		this._theDrug = theDrug;
		this._qty = qty;
		this._avg = avg;
	}

	public void setDrug(Drug theDrug)
	{
		this._theDrug = theDrug;
	}

	public void setQty(int qty)
	{
		this._qty = qty;
	}

	public Drug getDrug()
	{
		return _theDrug;
	}

	public int getQty()
	{	
		return _qty;
	}

	public long getAverage()
	{
		return _avg;
	}

	public String getDrugName()
	{
		return _theDrug.getName();
	}
}
