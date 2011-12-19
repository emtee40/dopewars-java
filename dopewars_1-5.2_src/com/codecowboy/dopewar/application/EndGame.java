package com.codecowboy.dopewar.application;

import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;
import java.awt.geom.*;
import java.awt.event.*;
import java.util.*;
import java.lang.*;
import com.codecowboy.dopewar.*;
import com.codecowboy.dopewar.util.*;
import com.codecowboy.dopewar.items.*;
import com.codecowboy.dopewar.player.*;
import com.codecowboy.dopewar.exception.*;
import com.codecowboy.dopewar.util.Util;

/**
 * File description
 * End game show score pop up
 *
 * Copyright (c) www.codecowboy.com 2001
 *
 * $RCSfile: EndGame.java,v $
 * @author $Author: ccb $
 * @version $Revision: 1.4 $, $Date: 2001/11/27 10:03:31 $
 */
public class EndGame extends JOptionPane
					  implements ActionListener
{
	private Dopewars _dw;
	private Player player;
	private boolean _topTen = false;

	public EndGame()
	{
		super("");
	}

	public void init(Dopewars dw, boolean topTen)
	{
		_dw = dw;
		JFrame parent = _dw.getFrame();
		player = _dw.getPlayer();
		_topTen = topTen;
		JPanel panel = getContents();
		Object[] comp = {panel,"OK"};
		setOptions(comp);
    	JDialog dialog = createDialog(parent, "Game Over");
	    dialog.setVisible(true);
		Object result = getValue();
		String act = (String)result;
		
		if(act.equals("OK"))
		{
		}
	}

	public void actionPerformed(ActionEvent event)
	{
	}
	
	private JPanel getContents()
	{
		JPanel panel = new JPanel();
		panel.setLayout(null);
		Dimension dim = new Dimension(240,220);
		panel.setMaximumSize(dim);
		panel.setMinimumSize(dim);
		panel.setPreferredSize(dim);

		String htmlBStart = "<html><font face=verdana size=2 color=black><b>";
		String htmlBEnd = "</b></font></html>";
		String htmlStart = "<html><font face=verdana size=2 color=black>";
		String htmlEnd = "</font></html>";

		String cash = Util.formatCurrency(player.getCash());
		String bank = Util.formatCurrency(player.getBank());
		String debt = Util.formatCurrency(player.getDebt());
		String sub = Util.formatCurrency(player.getCash() + player.getBank());
		String worth = Util.formatCurrency(player.getWorth());

		JLabel title = new JLabel("<html><font face=verdana color=black size=3><b>Score Sheet</b></font></html>");

		JLabel cashName = new JLabel(htmlBStart + "Cash: " + htmlBEnd);
		JLabel cashValue = new JLabel(htmlStart + cash + htmlEnd);

		JLabel bankName = new JLabel(htmlBStart + "Bank: " + htmlBEnd);
		JLabel bankValue = new JLabel(htmlStart + bank + htmlEnd);

		JLabel subName = new JLabel(htmlBStart + "Sub: " + htmlBEnd);
		JLabel subValue = new JLabel(htmlStart + sub + htmlEnd);

		JLabel debtName = new JLabel(htmlBStart + "Debt: " + htmlBEnd);
		JLabel debtValue = new JLabel(htmlStart + debt + htmlEnd);

		JLabel worthName = new JLabel(htmlBStart + "Worth: " + htmlBEnd);
		JLabel worthValue = new JLabel(htmlStart + worth + htmlEnd);

		JLabel topTenLabel = new JLabel(htmlBStart + "You made the top 10!" + htmlBEnd);

		Rectangle titleRec = new Rectangle(5,5,100,50);
		Rectangle cashNameRec = new Rectangle(5,25,100,50);
		Rectangle cashValueRec = new Rectangle(105,25,100,50);
		Rectangle bankNameRec = new Rectangle(5,45,100,50);
		Rectangle bankValueRec = new Rectangle(105,45,100,50);
		Rectangle subNameRec = new Rectangle(5,65,100,50);
		Rectangle subValueRec = new Rectangle(105,65,100,50);
		Rectangle debtNameRec = new Rectangle(5,85,100,50);
		Rectangle debtValueRec = new Rectangle(105,85,100,50);
		Rectangle worthNameRec = new Rectangle(5,105,100,50);
		Rectangle worthValueRec = new Rectangle(105,105,100,50);
		Rectangle topTenLabelRec = new Rectangle(5,135,200,50);
		
		title.setBounds(titleRec);
		cashName.setBounds(cashNameRec);
		cashValue.setBounds(cashValueRec);
		bankName.setBounds(bankNameRec);
		bankValue.setBounds(bankValueRec);
		subValue.setBounds(subValueRec);
		subName.setBounds(subNameRec);
		debtValue.setBounds(debtValueRec);
		debtName.setBounds(debtNameRec);
		worthName.setBounds(worthNameRec);
		worthValue.setBounds(worthValueRec);
		topTenLabel.setBounds(topTenLabelRec);
	
		panel.add(title);
		panel.add(cashName);
		panel.add(cashValue);
		panel.add(bankName);
		panel.add(bankValue);
		panel.add(subName);
		panel.add(subValue);
		panel.add(debtValue);
		panel.add(debtName);
		panel.add(worthName);
		panel.add(worthValue);
		
		if(_topTen)
		{
			panel.add(topTenLabel);
		}
		return panel;
	}
}

