
package com.codecowboy.dopewar.util;

import javax.swing.*;
import javax.swing.table.*;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.*;

/**
 * File description
 * Model for JTable
 *
 * Copyright (c) www.codecowboy.com 2001
 *
 * $RCSfile: SellTableModel.java,v $
 * @author $Author: ccb $
 * @version $Revision: 1.2 $, $Date: 2001/11/27 10:03:32 $
 */
public class SellTableModel extends AbstractTableModel
{
	private String[] columnNames = {"Drug", "Qty", "Avg Price"};
	private String[][] data;

	public void setData(String[][] d)
	{
		data = d;
	}

	public int getColumnCount()
	{
		return columnNames.length;
	}

	public int getRowCount()
	{
		return data.length;
	}

	public String getColumnName(int index)
	{
		return columnNames[index];
	}

	public Object getValueAt(int row, int col)
	{
		return data[row][col];
	}

	public void setValueAt(Object val, int row, int col)
	{
		data[row][col] = (String) val;
	}

	public String toString()
	{
		StringBuffer rv = new StringBuffer();

		for(int i = 0; i < data.length; i++)
		{
			for(int j = 0; j < 2; j++)
			{
				rv.append(data[i][j] + " ");
			}

			rv.append("\n");
		}

		return rv.toString();
		
	}
}
