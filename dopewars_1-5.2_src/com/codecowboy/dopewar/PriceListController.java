
package com.codecowboy.dopewar;

import java.util.*;
/**
 * File description
 * Acts as a handler for pricelist.
 *
 * Copyright (c) www.codecowboy.com 2001
 *
 * $RCSfile: PriceListController.java,v $
 * @author $Author: ccb $
 * @version $Revision: 1.5 $, $Date: 2001/11/27 10:03:34 $
 */
public class PriceListController
{
	private PriceList _pl;
	
	public PriceListController()
	{
		init();	
	}

	private void init()
	{
		createPriceList();
	}

	private void createPriceList()
	{
		_pl = new PriceList();	
	}

	public PriceList getPriceList()
	{
		return _pl;
	}
}
