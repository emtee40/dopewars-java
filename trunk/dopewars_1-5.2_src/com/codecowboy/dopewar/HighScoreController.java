
package com.codecowboy.dopewar;

import java.util.*;
import java.io.*;
import com.codecowboy.dopewar.player.*;
import com.codecowboy.dopewar.application.*;

/**
 * File description
 * Controller for high scores.
 *
 * Copyright (c) www.codecowboy.com 2001
 *
 * $RCSfile: HighScoreController.java,v $
 * @author $Author: ccb $
 * @version $Revision: 1.6 $, $Date: 2001/11/27 10:03:33 $
 */
public class HighScoreController
{
	private Dopewars _dw;
	private HighScoreStore hss;
	private Player player;
	
	public HighScoreController(Dopewars thisGame)
	{
		init(thisGame);
	}

	private void init(Dopewars dw)
	{
		hss = new HighScoreStore();
		_dw = dw;
		player = _dw.getPlayer();
	}

	public void resetAllScores()
	{
		hss.resetAllScores();
	}

	public boolean submit()
	{
		ScoreItem si = new ScoreItem(player.getName(),player.getWorth());

		if(_dw.getGameLength() == 30)
		{
			return hss.submit(si,HighScoreStore.dataFile30);
		}
		else if(_dw.getGameLength() == 60)
		{
			return hss.submit(si,HighScoreStore.dataFile60);
		}
		else if(_dw.getGameLength() == 90)
		{
			return hss.submit(si,HighScoreStore.dataFile90);
		}
		else if(_dw.getGameLength() == 120)
		{
			return hss.submit(si,HighScoreStore.dataFile120);
		}
		else
		{	
			return hss.submit(si,HighScoreStore.dataFile30);
		}		
	}

	public ArrayList getList(String file)
	{
		return hss.getList(file);
	}

	
}
