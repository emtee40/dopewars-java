
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
import com.codecowboy.dopewar.util.Util;

/**
 * File description
 * Deposit/Withdraw money pop up
 *
 * Copyright (c) www.codecowboy.com 2001
 *
 * $RCSfile: Bank.java,v $
 * @author $Author: ccb $
 * @version $Revision: 1.5 $, $Date: 2001/11/27 10:03:31 $
 */
public class Bank extends JOptionPane
				 implements ActionListener
{
	public Dopewars _dw;
	public Player _player;
	public JRadioButton wd;
	public JRadioButton dep;
	public JTextField amt;
	public long bankBalance;
	public long cashBalance;
	public TransactionController tc;

	public Bank()
	{
		super("");
	}

	public void init(Dopewars dw)
	{
		this._dw = dw;
		this._player = _dw.getPlayer();
		cashBalance = _player.getCash();
		JFrame parent = _dw.getFrame();
		JPanel panel = getContents();
		Object[] comp = {panel,"OK", "Cancel"};
		setOptions(comp);
		JDialog dialog = createDialog(parent,"Bank");
		dialog.setVisible(true);
		tc = new TransactionController();
		Object result = getValue();
		String act = (String) result;
		String amtStr = "";
		
		if(act.equals("OK"))
		{
			amtStr = amt.getText();
			try
			{
				if(wd.isSelected())
				{
					if(Util.isLong(amtStr))
					{
						long a = Long.parseLong(amtStr);
						tc.withdrawl(_player,a);
						_dw.addMessage("Withdrew $" + a + " from the bank");
					}
				}
				else if(dep.isSelected())
				{
					if(Util.isLong(amtStr))
					{
						long a = Long.parseLong(amtStr);
						tc.deposit(_player,a);
						_dw.addMessage("Deposited $" + a + " into the bank");
					}				
				}
			}
			catch(TransactionException te)
			{
				JOptionPane.showMessageDialog(null, te.getMessage(), "Transaction Error", JOptionPane.ERROR_MESSAGE); 
			}
		}
	}

	public void actionPerformed(ActionEvent event)
	{
		String actionCmd = event.getActionCommand();

		if(wd.isSelected())
		{
			amt.setText(new Long(bankBalance).toString());
			amt.selectAll();
		}
		else if(dep.isSelected())
		{
			amt.setText(new Long(cashBalance).toString());
			amt.selectAll();
		}
	}

	private JPanel getContents()
	{
		JPanel panel = new JPanel();
		panel.setLayout(null);
		Dimension dim = new Dimension(240,220);
		panel.setMaximumSize(dim);
		panel.setMinimumSize(dim);
		panel.setPreferredSize(dim);

		JLabel dName = new JLabel("The Bank");
		dName.setBounds(5,5,100,20);
		panel.add(dName);
		
		JLabel cashBal = new JLabel("Cash balance: $" + cashBalance);
		cashBal.setBounds(5,45,160,20);

		bankBalance = _player.getBank();
		JLabel bankBal = new JLabel("Bank balance: $" + bankBalance);
		bankBal.setBounds(5,65,160,20);

		JLabel amtLab = new JLabel("Amount: $");
		amtLab.setBounds(5, 175,80,20);

		amt = new JTextField();
		amt.setBounds(90,175, 80,20);
		
		wd = new JRadioButton("Widthdrawl");
		wd.setBounds(5, 100, 160, 20);
		wd.addActionListener(this);
		
		dep = new JRadioButton("Deposit");
		dep.setBounds(5, 135, 160, 20);
		dep.addActionListener(this);
		dep.setSelected(true);
		amt.setText(new Long(cashBalance).toString());

		ButtonGroup bg = new ButtonGroup();
		bg.add(wd);
		bg.add(dep);

		panel.add(wd);
		panel.add(dep);
		panel.add(cashBal);
		panel.add(bankBal);
		panel.add(amtLab);
		panel.add(amt);

		return panel;
	}
}
