package com.codecowboy.dopewar.application;

import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;
import java.awt.font.*;
import java.awt.event.*;
import java.util.*;
import java.lang.*;
import com.codecowboy.dopewar.*;
import com.codecowboy.dopewar.items.*;
import com.codecowboy.dopewar.player.*;
import com.codecowboy.dopewar.exception.*;
import com.codecowboy.dopewar.util.*;

/**
 * File description
 * Buy drugs pop up
 *
 * Copyright (c) www.codecowboy.com 2001
 *
 * $RCSfile: Buy.java,v $
 * @author $Author: ccb $
 * @version $Revision: 1.11 $, $Date: 2001/11/27 10:03:31 $
 */
public class Buy extends JOptionPane
				 implements ActionListener
{
	public Dopewars _dw;
	public Drug _thisDrug;
	public Player player;
	public TransactionController tc;
	public Font labFontBold = new Font("Verdana",Font.BOLD,12);
	public Font labFontPlain = new Font("Verdana",Font.PLAIN,10);
	public int maxQty = 0;
	public Integer[] maxQtyData;
	public JComboBox qtyCombo;
	public JRadioButton qty;
	public JRadioButton max;
	public String maxQtyName;
	public int purchQty = 0;
	
	public Buy()
	{
		super("");
	}

	public void init(Dopewars dw, Drug thisDrug)
	{
		_dw = dw;
		_thisDrug = thisDrug;
		JFrame parent = _dw.getFrame();
		player = _dw.getPlayer();
		tc = new TransactionController();
		JPanel panel = getContents();
		Object[] comp = {panel,"OK", "Cancel"};
		setOptions(comp);
    	JDialog dialog = createDialog(parent, "Buy Drugs!");
	   dialog.setVisible(true);
		Object result = getValue();
		String act = (String)result;
		
		if(act.equals("OK"))
		{	
			if(max.isSelected())
			{
				purchQty = maxQty;
			}			
			doPurchase();
		}
	}

	private void doPurchase()
	{
		try
		{
			tc.buy(player,_thisDrug,purchQty);

			if(purchQty > 0)
			{
				_dw.addMessage("Purchased " 
						+ purchQty + " unit(s) of "	
						+ _thisDrug.getName() 
						+ " for " + Util.formatCurrency(_thisDrug.getPrice()));	
			}
		}
		catch(TransactionException te)
		{
			te.printStackTrace();
		}
	}
	

	public void actionPerformed(ActionEvent event)
	{
		String actionCmd = event.getActionCommand();

		if(actionCmd.equals("comboBoxChanged"))
		{
			qty.setSelected(true);	
		}

		if(qty.isSelected())
		{
			Integer intVal = (Integer)qtyCombo.getSelectedItem();
			purchQty = intVal.intValue();
		}
		else if(max.isSelected())
		{
			purchQty = maxQty;
		}
	}

	private JPanel getContents()
	{
		JPanel panel = new JPanel();
		panel.setLayout(null);
		Dimension dim = new Dimension(200,120);
		panel.setMaximumSize(dim);
		panel.setMinimumSize(dim);
		panel.setPreferredSize(dim);
		String htmlPre = "<html><font face=verdana size=2 color=black><b>";
		String htmlPost = "</b></font></html>";
		JLabel dName = new JLabel(htmlPre + _thisDrug.getName() 
						+ " at " + Util.formatCurrency(_thisDrug.getPrice()) + htmlPost);
		dName.setBounds(5,5,150,20);
		maxQty = calcMaxQty();
		qty = new JRadioButton("Qty");
		qty.addActionListener(this);
		maxQtyName = "Max = " + maxQty + " units";
		max = new JRadioButton(maxQtyName);
		max.setSelected(true);
		max.addActionListener(this);
		ButtonGroup ops = new ButtonGroup();

		maxQtyData = getQtyData();
		qtyCombo = new JComboBox(maxQtyData);
		qtyCombo.addActionListener(this);
		
		
		qty.setBounds(5, 40, 50, 20);
		max.setBounds(5, 75, 160, 20);
		qtyCombo.setBounds(75,40, 50,20);
			
		ops.add(qty);
		ops.add(max);

		panel.add(qty);
		panel.add(max);
		panel.add(dName);
		panel.add(qtyCombo);
		return panel;
	}

	private int calcMaxQty()
	{
		int rv = 0;
		long availCash = player.getCash();
		int price = _thisDrug.getPrice();
		Coat thisCoat = player.getCoat();
		int max = (int)availCash/price;
		int space = thisCoat.getSpacesAvailable();

		if(max > space)
		{
			rv = space;
		}
		else
		{
			rv = max;
		}
		
		return rv;
	}

	private Integer[] getQtyData()
	{
		int max = calcMaxQty();
		Integer[] rv = null;

		if(max > 0)
		{
			rv = new Integer[max];
			int j = 1;
			for(int i=0; i < max; i++)
			{
				Integer thisInt = new Integer(j);
				rv[i] = thisInt;
				j++;
			}
		}
		else
		{
			rv = new Integer[1];
			rv[0] = new Integer(0);
		}

		return rv;
	}
}

