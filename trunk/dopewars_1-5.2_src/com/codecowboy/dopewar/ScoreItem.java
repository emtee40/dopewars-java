
package com.codecowboy.dopewar;


/**
 * File description
 * Repesents a score, ready for adding to the
 * high score list. A snap shot of the end game.
 *
 * Copyright (c) www.codecowboy.com 2001
 *
 * $RCSfile: ScoreItem.java,v $
 * @author $Author: ccb $
 * @version $Revision: 1.2 $, $Date: 2001/11/27 10:03:34 $
 */
public class ScoreItem
{
	private String _name;
	private long _score;
	
	public ScoreItem(String name, String score)
	{
		setName(name);
		setScore(score);
	}

	public ScoreItem(String name, long score)
	{
		setName(name);
		setScore(score);
	}

	private void setName(String name)
	{
		_name = name;			
	}

	private void setScore(long score)
	{
		_score = score;
	}

	private void setScore(String score)
	{
		try
		{
			Long s = new Long(score);
			_score = s.longValue();
		}
		catch(NumberFormatException nfe)
		{
			nfe.printStackTrace();
		}
	}

	public String getName()
	{
		return _name;
	}

	public long getScore()
	{
		return _score;
	}
}
