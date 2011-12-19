
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

/**
 * File description
 * A visit from the loan shark pop up
 *
 * Copyright (c) www.codecowboy.com 2001
 *
 * $RCSfile: LoanShark.java,v $
 * @author $Author: ccb $
 * @version $Revision: 1.2 $, $Date: 2001/11/27 10:03:32 $
 */
public class LoanShark extends JOptionPane
				  implements ActionListener
{
	public Dopewars _dw;
	public Player player;
	public int _damage;
	public String _msg;
	public TransactionController tc;
	public Font labFontBold = new Font("Verdana",Font.BOLD,10);
	public Font labFontPlain = new Font("Verdana",Font.PLAIN,10);

	public LoanShark()
	{
		super("");
	}
	
	public void init(Dopewars dw)
	{
		_dw = dw;
		JFrame parent = _dw.getFrame();
		player = _dw.getPlayer();
		tc = new TransactionController();
		JPanel panel = getContents();
		Object[] comp = {panel,"OK"};
		setOptions(comp);
    	JDialog dialog = createDialog(parent, "A visit from the loan shark...");
	    dialog.setVisible(true);
		player.removeHealth(_damage);
		_dw.refreshLabels();
		Object result = getValue();
		String act = (String)result;
	}

	private JPanel getContents()
	{
		JPanel panel = new JPanel();
		panel.setLayout(null);
		Dimension dim = new Dimension(250,220);
		panel.setMaximumSize(dim);
		panel.setMinimumSize(dim);
		panel.setPreferredSize(dim);
		setMessage(_dw.getLoanDayCount());
		
		JTextArea story = new JTextArea(8,40);
		JScrollPane scrollPane = new JScrollPane(story);
		story.setWrapStyleWord(true);
		story.setLineWrap(true);
		story.setBackground(new Color(204,204,204));
		story.setFont(new Font("Courier",Font.PLAIN,11));
		story.setEditable(false);
		story.append(_msg);
		scrollPane.setBounds(5,5,200,200);
		panel.add(scrollPane);

		return panel;
	}

	public void actionPerformed(ActionEvent event)
	{
		
	}

	public void setMessage(int dayCount)
	{
		switch(dayCount)
		{
			case 5:
				_msg = "Fat Tony has dropped by to see how your going. He wants you to make a payment in the next 5 days";
				_damage = 0;
				break;
			case 10:
				_msg = "Fat Tony has come to visit again.He's not happy with you and has Vito lean on you some with his fists";
				_damage = 25;
				break;
			case 15:
				_msg = "Fat Tony brings 4 of his boys round to your house to 'help' you with a repayment problem.";
				_damage = 30;
				break;
			case 20:
				_msg = "Fat Tony is furious does some serious damage to your health. 'REPAY NOW!' he bellows.";
				_damage = 35;
				break;
			case 25:
				_msg = "Fat Tony ends your repayment problems for good";
				_damage = 100;
				break;
			default:
				_msg = "Fat Tony ends your repayment problems for good";
				_damage = 100;
				break;
		}
	}
}
