
package com.codecowboy.dopewar;

import java.util.*;
import java.io.*;

/**
 * File description
 * Provides file access (for data text files) for high scores.
 *
 * Copyright (c) www.codecowboy.com 2001
 *
 * $RCSfile: HighScoreStore.java,v $
 * @author $Author: ccb $
 * @version $Revision: 1.5 $, $Date: 2001/11/27 10:03:34 $
 */
public class HighScoreStore
{
	public static final String dataFile30 = "highscores_30.dat";
	public static final String dataFile60 = "highscores_60.dat";
	public static final String dataFile90 = "highscores_90.dat";
	public static final String dataFile120 = "highscores_120.dat";
	private ArrayList list30 = new ArrayList();
	private ArrayList list60 = new ArrayList();
	private ArrayList list90 = new ArrayList();
	private ArrayList list120 = new ArrayList();

	public HighScoreStore()
	{
		load();
	}

	public void load()
	{
		try
		{
			list30 = loadFile(dataFile30);
			list60 = loadFile(dataFile60);
			list90 = loadFile(dataFile90);
			list120 = loadFile(dataFile120);
			sortAll();
		}
		catch(FileNotFoundException fnfe)
		{}
		catch(IOException ioe)
		{}
	}

	public ArrayList getList(String file)
	{
		ArrayList rv = new ArrayList();
		
		if(file.equals(dataFile30))
		{
			rv = list30;	
		}
		else if(file.equals(dataFile60))
		{
			rv = list60;
		}
		else if(file.equals(dataFile90))
		{
			rv = list90;
		}
		else if(file.equals(dataFile120))
		{
			rv = list120;
		}

		return rv;
	}

	private void addScore(ScoreItem si, String file)
	{
		if(file.equals(dataFile30))
		{
			list30.add(si);
			commit(list30,dataFile30);
		}
		else if(file.equals(dataFile60))
		{
			list60.add(si);
			commit(list60, dataFile60);
		}
		else if(file.equals(dataFile90))
		{
			list90.add(si);
			commit(list90,dataFile90);
		}
		else if(file.equals(dataFile120))
		{
			list120.add(si);
			commit(list120,dataFile120);
		}
	}

	public boolean submit(ScoreItem si, String file)
	{

		boolean rv = false;
		ArrayList thisList = getList(file);
		
		if(thisList.size() >= 10)
		{
			for(int i=0; i < thisList.size(); i++)
			{
				ScoreItem tempSI = (ScoreItem)thisList.get(i);
				
				if(si.getScore() > tempSI.getScore())
				{
					rv = true;
					addScore(si,file);
					break;
				}
			}
		}
		else
		{
			rv = true;
			addScore(si,file);
		}

		return rv;
	}

	public void sortAll()
	{
		sort(list30);
		sort(list60);
		sort(list90);
		sort(list120);
	}

	public void resetAllScores()
	{
		list30 = new ArrayList();
		list60 = new ArrayList();
		list90 = new ArrayList();
		list120 = new ArrayList();

		commit(list30,dataFile30);
		commit(list60,dataFile60);
		commit(list90,dataFile90);
		commit(list120,dataFile120);
	}

	public void sort(ArrayList thisList)
	{
		ScoreComparator comp = new ScoreComparator();
		Collections.sort(thisList,comp);
	}

	private void commit(ArrayList thisList, String file)
	{
		sort(thisList);	
		try
		{
			PrintWriter out = new PrintWriter(
							new BufferedWriter(
							new FileWriter(file)));

			for(int i =0; i < thisList.size() && i < 10; i++)
			{
				ScoreItem si = (ScoreItem) thisList.get(i);
				out.println(si.getName() + "|" + si.getScore());
			}

			out.close();
		}
		catch(IOException ioe)
		{ioe.printStackTrace();}		
	}

	private ArrayList loadFile(String fName)
		throws FileNotFoundException, IOException
	{
		BufferedReader scoreListReader = 
					new BufferedReader(new FileReader(fName));
		ArrayList rv = new ArrayList();
	
		while(scoreListReader.ready())
		{
			String lineFromFile = scoreListReader.readLine();
			
			if(lineFromFile == null)
			{
				break;
			}
			else
			{
				StringTokenizer hsTok = new StringTokenizer(lineFromFile, "|");
				String playerName = hsTok.nextToken();
				String playerScore = hsTok.nextToken();
				ScoreItem sc = new ScoreItem(playerName, playerScore);
				rv.add(sc);
			}
		}

		return rv;
	}
}
