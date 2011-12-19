package com.codecowboy.dopewar.application;

import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;
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
 * Sell drugs pop up.
 *
 * Copyright (c) www.codecowboy.com 2001
 *
 * $RCSfile: Sell.java,v $
 * @author $Author: ccb $
 * @version $Revision: 1.9 $, $Date: 2001/11/27 10:03:32 $
 */
public class Sell extends JOptionPane
				  implements ActionListener
{
	public Dopewars _dw;
	public Drug _thisDrug;
	public Player player;
	public PriceList pl;
	public TransactionController tc;
	public Font labFontBold = new Font("Verdana",Font.BOLD,10);
	public Font labFontPlain = new Font("Verdana",Font.PLAIN,10);
	public JComboBox qtyList;
	public JRadioButton qtyBtn;
	public JRadioButton allBtn;
	
	public int sellQty = 0;
	public String maxSellQtyText;
	public int maxSellQty = 0;

	public Sell()
	{
		super("");
	}
	
	public void init(Dopewars dw, Drug sellThis)
	{
		_dw = dw;
		_thisDrug = sellThis;
		JFrame parent = _dw.getFrame();
		player = _dw.getPlayer();
		tc = new TransactionController();
		pl = _dw.getPriceList();
		JPanel panel = getContents();
		Object[] comp = {panel,"OK", "Cancel"};
		setOptions(comp);
    	JDialog dialog = createDialog(parent, "Sell Drugs!");
	    dialog.setVisible(true);
		Object result = getValue();
		String act = (String)result;
		
		if(act.equals("OK"))
		{
			if(allBtn.isSelected())
			{
				sellQty = maxSellQty;
			}
			doSale();
		}
	}

	public void doSale()
	{
		try
		{
			long price = getDrugPrice();
			tc.sell(player,_thisDrug,sellQty, getDrugPrice());
			_dw.addMessage("Sold " + sellQty 
					+ " unit(s) of " 
					+ _thisDrug.getName() 
					+ " for " + Util.formatCurrency(price) + " each.");	
		}
		catch(TransactionException te)
		{
			
		}
	}

	public void actionPerformed(ActionEvent event)
	{
		String actionCmd = event.getActionCommand();

		if(actionCmd.equals("comboBoxChanged"))
		{
			qtyBtn.setSelected(true);	
		}

		if(qtyBtn.isSelected())
		{
			Integer intVal = (Integer)qtyList.getSelectedItem();
			sellQty = intVal.intValue();
		}
		else if(allBtn.isSelected())
		{
			sellQty = maxSellQty;
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
		JLabel dName = new JLabel(htmlPre + _thisDrug.getName() + " at " 
						+ Util.formatCurrency(getDrugPrice()) + htmlPost);
		dName.setBounds(5,5,150,20);
		
		maxSellQty = getMaxDrugQty();
		maxSellQtyText = "All - " + maxSellQty + " units";
		
		qtyBtn = new JRadioButton("Qty");
		qtyBtn.addActionListener(this);
		allBtn = new JRadioButton(maxSellQtyText);
		allBtn.addActionListener(this);
		allBtn.setSelected(true);
		ButtonGroup grp = new ButtonGroup();

		qtyList = new JComboBox(getSellQtyListData());
		qtyList.addActionListener(this);

		qtyBtn.setBounds(5, 40, 50, 20);
		allBtn.setBounds(5, 75, 160, 20);
		qtyList.setBounds(75, 40, 50, 20);		
		
		grp.add(qtyBtn);
		grp.add(allBtn);
		panel.add(qtyBtn);
		panel.add(allBtn);
		panel.add(qtyList);
	
		panel.add(dName);

		return panel;
	}

	public long getDrugPrice()
	{
		String sellDrugName = _thisDrug.getName();
		ArrayList drugs = pl.getList();
		long rv = 0;

		for(int i=0; i < drugs.size();i++)
		{
			Drug d = (Drug)drugs.get(i);
			
			if(sellDrugName.equals(d.getName()))
			{
				rv = d.getPrice();
				break;
			}
		}

		return rv;
	}

	public int getMaxDrugQty()
	{
		int rv = 0;
		Coat thisCoat = player.getCoat();
		rv = thisCoat.getDrugCount(_thisDrug);

		return rv;
	}

	public Integer[] getSellQtyListData()
	{
		int max = player.getCoat().getDrugCount(_thisDrug);
		Integer[] rv = new Integer[max];

	    int content = 1;
		
		for(int i = 0; content <= max; i++)
		{
			Integer num = new Integer(content);
			rv[i] = num;
			content++;
		}
		return rv;
	}
}
