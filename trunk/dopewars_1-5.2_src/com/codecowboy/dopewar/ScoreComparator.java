package com.codecowboy.dopewar;

import java.util.*;
/**
 * File description
 * Compares two Score Items.
 *
 * Copyright (c) www.codecowboy.com 2001
 *
 * $RCSfile: ScoreComparator.java,v $
 * @author $Author: ccb $
 * @version $Revision: 1.3 $, $Date: 2001/11/27 10:03:34 $
 */
public class ScoreComparator implements Comparator
{
	public int compare(Object a, Object b)
	{
		ScoreItem sc_1 = (ScoreItem) a;
		ScoreItem sc_2 = (ScoreItem) b;

		return (new Long(sc_2.getScore()).compareTo(new Long(sc_1.getScore())));
	}
}
