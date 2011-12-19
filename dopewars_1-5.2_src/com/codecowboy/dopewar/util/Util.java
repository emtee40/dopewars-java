package com.codecowboy.dopewar.util;

import java.text.*;

/**
 * File description
 * Uitlity class
 *
 * Copyright (c) www.codecowboy.com 2001
 *
 * $RCSfile: Util.java,v $
 * @author $Author: ccb $
 * @version $Revision: 1.3 $, $Date: 2001/11/27 10:03:33 $
 */
public class Util
{
	public static boolean isLong(String num)
	{
		boolean rv = true;
		
		try
		{
			long tmp = Long.parseLong(num);
		}
		catch(NumberFormatException nfe)
		{
			rv = false;
		}
		finally
		{
			return rv;
		}
	}

	public static boolean isDouble(String num)
	{
		boolean rv = true;

		try
		{
			Double.parseDouble(num);
		}
		catch(NumberFormatException nfe)
		{
			rv = false;
		}
		finally
		{
			return rv;
		}
	}

	public static String formatCurrency(long money)
	{
		NumberFormat nf = NumberFormat.getCurrencyInstance();
		nf.setMaximumFractionDigits(0);

		String rv = nf.format(money);

		return rv;		
	}
}
