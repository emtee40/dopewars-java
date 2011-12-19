
package com.codecowboy.dopewar.items;

import com.codecowboy.dopewar.items.*;
import java.util.*;
import com.codecowboy.dopewar.util.*;
/**
 * File description
 * represents a coat.
 *
 * Copyright (c) www.codecowboy.com 2001
 *
 * $RCSfile: Coat.java,v $
 * @author $Author: ccb $
 * @version $Revision: 1.14 $, $Date: 2001/11/19 13:56:55 $
 */
public class Coat
{
	private int _capacity = 0;
	private ArrayList _drugs = new ArrayList();
	private ArrayList _weapons = new ArrayList();
	private ArrayList _drugPacks = new ArrayList();
	private int itemCount = 0;
	private int DEF_CAPACITY = 100;
	private long _price = 200;
	
	public Coat(int capacity)
	{
		setCapacity(capacity);		
	}

	public Coat()
	{
		setCapacity(DEF_CAPACITY);
	}

	public void setCapacity(int capacity)
	{
		this._capacity = capacity;
	}

	public int getSpacesAvailable()
	{
		return _capacity - itemCount;
	}

	public void setPrice(long p)
	{
		this._price = p;
	}

	public long getPrice()
	{
		return this._price;
	}

	public void addDrug(Drug drug)
	{
		if((itemCount + 1) <= this._capacity)
		{
			_drugs.add(drug);
			itemCount++;
		}
	}

	public void addWeapon(Weapon weapon)
	{
		if((itemCount + 1) <= this._capacity)
		{
			_weapons.add(weapon);
			itemCount++;
		}
	}	

	public void removeDrug(String name)
	{
		for(int i=0; i < _drugs.size(); i++)
		{
			Drug thisDrug = (Drug) _drugs.get(i);
				
			if(name.equals(thisDrug.getName()))
			{
				_drugs.remove(i);
				itemCount--;
				break;
			}
		}
	}

	public void removeWeapon(String name)
	{
		for(int i=0; i < _weapons.size(); i++)
		{
			Weapon thisWeapon = (Weapon) _weapons.get(i);

			if(thisWeapon.getName().equals(name))
			{
				_weapons.remove(i);
				itemCount--;
			}
		}
	}

	public Drug getDrug(int index)
	{
		return (Drug)_drugs.get(index);
	}

	public ArrayList getStash()
	{
		return _drugs;
	}

	public String[][] drugsToArray()
	{
		String[][] drugArr = null;
		
		if(_drugs.size() > 0)
		{
			//create drug pack representation
			_drugPacks = new ArrayList();
			
			for(int i=0; i < _drugs.size();i++)
			{
				Drug thisDrug = (Drug) _drugs.get(i);
				
				if(! isDrugInList(_drugPacks,thisDrug))
				{
					int q = getDrugQty(thisDrug);
					long a = getAverageDrugPrice(thisDrug);
					DrugPack dp = new DrugPack(thisDrug,q,a);
					_drugPacks.add(dp);
				}							
			}

			drugArr = new String[_drugPacks.size()][3];
			
			for(int i=0; i < drugArr.length; i++)
			{
				DrugPack thisPack = (DrugPack)_drugPacks.get(i);
				drugArr[i][0] = thisPack.getDrugName();
				drugArr[i][1] = new Integer(thisPack.getQty()).toString();
				drugArr[i][2] = Util.formatCurrency(new Long(thisPack.getAverage()).longValue());
			}
		}

		return drugArr;
	}

	public Drug getDrugFromPackList(int index)
	{
		DrugPack dp = (DrugPack)_drugPacks.get(index);
		return dp.getDrug();
	}

	private int getDrugQty(Drug checkDrug)
	{
		int rv = 0;
		
		for(int i=0; i < _drugs.size(); i++)
		{
			Drug thisDrug = (Drug)_drugs.get(i);
			
			if(checkDrug.getName().equals(thisDrug.getName()))
			{
				++rv;
			}
		}
		return rv;
	}

	private long getAverageDrugPrice(Drug checkDrug)
	{
		long rv = 0;
		long totalPrice = 0;
		int qty = 0;
		
		for(int i=0; i < _drugs.size(); i++)
		{
			Drug thisDrug = (Drug)_drugs.get(i);
			
			if(checkDrug.getName().equals(thisDrug.getName()))
			{
				totalPrice += thisDrug.getPrice();
				qty++;
			}
		}

		rv = (long) totalPrice / qty;

		return rv;
	}

	public boolean isDrugInList(ArrayList drugPacks, Drug theDrug)
	{
		boolean rv = false;
		for(int i=0; i < drugPacks.size(); i++)
		{
			DrugPack dPack = (DrugPack)drugPacks.get(i);
			String packName = dPack.getDrugName();

			if(packName.equals(theDrug.getName()))
			{
				rv = true;
				break;
			}			
		}

		return rv;
	}
	

	public ArrayList getWeapons()
	{
		return _weapons;
	}

	public int getCapacity()
	{
		return this._capacity;
	}

	public int getItemCount()
	{
		return itemCount;
	}

	public void removeAllItems()
	{
		_drugs.removeAll(_drugs);
		_weapons.removeAll(_weapons);
	}

	public int getDrugCount(Drug thisDrug)
	{
		String dName = thisDrug.getName();
		int rv = 0;
		
		for(int i=0; i < _drugPacks.size(); i++)
		{
			DrugPack thisPack = (DrugPack) _drugPacks.get(i);
			String pName = thisPack.getDrugName();

			if(pName.equals(dName))
			{
				rv = thisPack.getQty();
				break;
			}
		}
		return rv;
	}
}
