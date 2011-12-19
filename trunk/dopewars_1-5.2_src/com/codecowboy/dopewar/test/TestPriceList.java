
package com.codecowboy.dopewar.test;

import java.io.*;
import java.util.*;
import com.codecowboy.dopewar.items.*;
import com.codecowboy.dopewar.*;

public class TestPriceList
{
	public static void main(String args[])
		throws IOException
	{
		new TestPriceList();
	}

	public TestPriceList()
	{
		PriceListController plc = new PriceListController();
		PriceList pl = plc.getPriceList();
		System.out.println(pl.toString());
	}
}
