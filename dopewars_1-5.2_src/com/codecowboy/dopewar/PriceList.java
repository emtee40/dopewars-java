
package com.codecowboy.dopewar;

import java.io.*;
import java.util.*;
import java.text.*;
import java.lang.*;
import com.codecowboy.dopewar.util.*;
import com.codecowboy.dopewar.*;
import com.codecowboy.dopewar.items.*;

/**
 * File description
 * represents a list of drugs and their prices
 * also reacts to special events..
 *
 * Copyright (c) www.codecowboy.com 2001
 *
 * $RCSfile: PriceList.java,v $
 * @author $Author: ccb $
 * @version $Revision: 1.14 $, $Date: 2001/11/19 13:56:54 $
 */
public class PriceList
{
	private ArrayList _drugs = new ArrayList();
	private Properties drugProp = null;
	private final String PROP_DRUGS_FILE = "/drugs.properties";
	private final String PROP_NO_DRUGS = "dw.drugCount";
	public final String MED = "medium";
	public final String LOW = "low";
	public final String HIGH = "high";
        private final int prob = 10;
	
	public static void main(String args[])
		throws IOException
	{
		new PriceList();
	}
	
	public PriceList()
	{
		init();
	}

	private void init()
	{
		try
		{
			InputStream in = getClass().getResourceAsStream(PROP_DRUGS_FILE);
			drugProp = new Properties();
			drugProp.load(in);
		}
		catch(IOException ioe)
		{
			ioe.printStackTrace();
		}
		load();
	}

    @SuppressWarnings("unchecked")
	private void load()
	{
	/*
         * this bit here i (jeremy leach) added to cause the druglist to 
         * randomize every time the druglist has been reparsed.
         * as i'm just learning java now, it's a wonder it works.
         * i'd like to add a probability parameter to each drug, but we'll get 
         * to that in the future.
         */ 	
                int apProb = 10;
                for(int i=1; ; i++)
		{
                    int appears = (int) (Math.random() * prob);
                    if (i==1){apProb = 2;} else if (i>10) {apProb = 15;} else {apProb = 5;}
                    
                    if ( appears>=apProb/i ) 
                    
                    {
                        String propName = "dw.drug_" + i + ".name";
			String name = drugProp.getProperty(propName);
			
			if(name == null)
			{
				break;
			}
			
			Drug thisDrug = new Drug(name,"dw.drug_" + i);
			thisDrug = getPriceForDrug(thisDrug, MED);
			_drugs.add(thisDrug);
                    }
		}
	}

	private int size()
	{
		return _drugs.size();
	}

	public String toString()
	{
		StringBuffer rv = new StringBuffer();
		for(int i=0;i<_drugs.size();i++)
		{
			Drug thisDrug = (Drug) _drugs.get(i);
			rv.append(thisDrug.getName() + "\t\t " + thisDrug.getPrice());
			
			if(i < (_drugs.size() - 1))
			{
				rv.append("\n");
			}
		}

		return rv.toString();
	}

	public ArrayList getList()
	{
		return _drugs;
	}

	public String[][] toArray()
	{
		String[][] rv = new String[_drugs.size()][2];

		for(int i=0; i < rv.length; i++)
		{
			Drug thisDrug = (Drug) _drugs.get(i);
			rv[i][0] = thisDrug.getName();
			rv[i][1] = Util.formatCurrency(new Long(thisDrug.getPrice()).longValue());
		}

		return rv;
	}

	public Drug getDrug(int index)
	{	
            return (Drug) _drugs.get(index);
	}

	public Drug getPriceForDrug(Drug thisDrug, String level)
	{
		String baseProp = thisDrug.getAddress();
		String propUpper = "";
		String propLower = "";
		
		int price = 0;
		
		if(level.equals(LOW))
		{
			propLower = baseProp + ".LLPL";
			propUpper = baseProp + ".ULPL";
		}
		else if(level.equals(MED))
		{
			propLower = baseProp + ".LMPL";
			propUpper = baseProp + ".UMPL";
		}
		else if(level.equals(HIGH))
		{
			propLower = baseProp + ".LHPL";
			propUpper = baseProp + ".UHPL";
		}

		int l = new Integer(drugProp.getProperty(propLower)).intValue();
		int u = new Integer(drugProp.getProperty(propUpper)).intValue();
		
		while(price < l || price > u)
		{
			price = (int) (Math.random() * u);
		}
		
		thisDrug.setPrice(price);

		return thisDrug;	
	}

	public Properties getProperties()
	{
		return drugProp;
	}
}
