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
 * Pop up for repaying or borrowing from/to loan shark
 *
 * Copyright (c) www.codecowboy.com 2001
 *
 * $RCSfile: Loan.java,v $
 * @author $Author: ccb $
 * @version $Revision: 1.5 $, $Date: 2001/11/27 10:03:32 $
 */
public class Loan extends JOptionPane
					  implements ActionListener
{
	private Dopewars _dw;
	private Player player;
	public JRadioButton borrow;
	public JRadioButton pay;
	public JTextField amt;
	public long bankBalance;
	public long cashBalance;
	public long loanBalance;
	public long maxLoanBalance;
	public TransactionController tc;
	public long maxBorrowAmt;

	public Loan()
	{
		super("");
	}

	public void init(Dopewars dw)
	{
		_dw = dw;
		JFrame parent = _dw.getFrame();
		player = _dw.getPlayer();
		tc = new TransactionController();
		maxLoanBalance = player.getMaxDebtAmount();
		maxBorrowAmt = maxLoanBalance - player.getDebt();
		JPanel panel = getContents();
		Object[] comp = {panel,"OK", "Cancel"};
		setOptions(comp);
    	JDialog dialog = createDialog(parent, "Loan Shark");
	    dialog.setVisible(true);
		Object result = getValue();
		String act = (String)result;
		
		if(act.equals("OK"))
		{
			if(Util.isLong(amt.getText()))
			{
				try
				{
					if(borrow.isSelected())
					{
						tc.borrow(player,
								new Long(amt.getText()).longValue(),
								player.getMaxDebtAmount()); 
					}
					else if(pay.isSelected())
					{
						double thres = 0.1 * player.getDebt();
						long rep = new Long(amt.getText()).longValue();

						if(rep > thres)
						{
							_dw.resetLoanDayCount();
						}

						tc.repay(player,rep);
					}
				}
				catch(TransactionException te)
				{
					JOptionPane.showMessageDialog(null, 
												te.getMessage(), 
												"Transaction Error", 
												JOptionPane.ERROR_MESSAGE); 
				}
			}
		}
	}

	public void actionPerformed(ActionEvent event)
	{
		String actionCmd = event.getActionCommand();

		if(borrow.isSelected())
		{
			amt.setText(new Long(maxBorrowAmt).toString());
		}
		else if(pay.isSelected())
		{	
			amt.setText(new Long(player.getDebt()).toString());
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
		String htmlStart = "<html>";
		String fontStart = "<font face=verdana size=1 color=black>";
		String fontBStart = "<font face=verdana size=1 color=black><b>";
		String htmlEnd = "</html>";
		String fontEnd = "</font>";
		String fontBEnd = "</b></font>";
		
		loanBalance = player.getDebt();
		JLabel loanBal = new JLabel(htmlStart + fontBStart 
						+ "Loan balance: " + fontBEnd 
						+ fontStart + Util.formatCurrency(loanBalance)
						+ " DR" + fontEnd + htmlEnd);
		loanBal.setBounds(5,05,160,20);

		bankBalance = player.getBank();
		JLabel bankBal = new JLabel(htmlStart + fontBStart 
						+ "Bank balance: " + fontBEnd 
						+ fontStart + Util.formatCurrency(bankBalance)
						+ fontEnd +	htmlEnd);
		bankBal.setBounds(5,25,160,20);

		cashBalance = player.getCash();
		JLabel cashBal = new JLabel(htmlStart + fontBStart
						+ "Cash balance: " + fontBEnd
						+ fontStart + Util.formatCurrency(cashBalance)
						+ fontEnd + htmlEnd);
		cashBal.setBounds(5,45,160,20);

		JLabel maxLoan = new JLabel(htmlStart + fontBStart
						+ "Maximum loan: " + fontBEnd
						+ fontStart + Util.formatCurrency(maxLoanBalance)
						+ fontEnd + htmlEnd);
		maxLoan.setBounds(5,65,160,20);
		
		JLabel amtLab = new JLabel("Amount: $");
		amtLab.setBounds(5, 175,80,20);

		amt = new JTextField();
		amt.setBounds(90,175, 80,20);
		amt.setText(new Long(maxBorrowAmt).toString());
		
		borrow = new JRadioButton("Borrow");
		borrow.setBounds(5, 100, 160, 20);
		borrow.addActionListener(this);
		borrow.setSelected(true);
		
		pay = new JRadioButton("Repay");
		pay.setBounds(5, 135, 160, 20);
		pay.addActionListener(this);

		ButtonGroup bg = new ButtonGroup();
		bg.add(borrow);
		bg.add(pay);

		panel.add(borrow);
		panel.add(pay);
		panel.add(cashBal);
		panel.add(bankBal);
		panel.add(loanBal);
		panel.add(maxLoan);
		panel.add(amtLab);
		panel.add(amt);
	
		return panel;
	}
}
