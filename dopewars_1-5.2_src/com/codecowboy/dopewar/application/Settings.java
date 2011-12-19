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
import com.codecowboy.dopewar.util.Util;

/**
 * File description
 * Settings pop up
 *
 * Copyright (c) www.codecowboy.com 2001
 *
 * $RCSfile: Settings.java,v $
 * @author $Author: ccb $
 * @version $Revision: 1.7 $, $Date: 2001/11/27 10:03:32 $
 */
public class Settings extends JOptionPane
					  implements ActionListener
{
	public Dopewars _dw;
	public JTextField cashStart;
	public JTextField bankStart;
	public JTextField debtStart;
	public JTextField playerName;
	public JTextField bankInt;
	public JTextField debtInt;
	public JCheckBox resetScores;
	public String lenSetting;	
	public Font labFontBold = new Font("Verdana",Font.BOLD,10);
	public Font labFontPlain = new Font("Verdana",Font.PLAIN,10);
	
	public Settings(String msg)
	{
		super(msg);
	}

	public void init(Dopewars dw)
	{
		_dw = dw;
		JFrame parent = _dw.getFrame();
		JPanel panel = getContents();
		Object[] comp = {panel,"OK", "Cancel"};
		setOptions(comp);
    	JDialog dialog = createDialog(parent, "Settings");
	    dialog.setVisible(true);
		Object result = getValue();
		String act = (String)result;
		
		if(act.equals("OK"))
		{
			_dw.restart();
			setCashStart(cashStart.getText());
			setBankStart(bankStart.getText());
			setDebtStart(debtStart.getText());
			setPlayerName(playerName.getText());
			setBankInterest(bankInt.getText());
			setDebtInterest(debtInt.getText());
			commitResetScores();
			commitGameLength();
			_dw.addMessage("Settings have been changed.");
			_dw.addMessage("Game reset");
			
		}
	}

	private void commitGameLength()
	{
		if(lenSetting.equals("30 Days"))
		{
			_dw.setGameLength(30);
			_dw.setProgressBar(30);
		}
		else if(lenSetting.equals("60 Days"))
		{
			_dw.setGameLength(60);
			_dw.setProgressBar(60);
		}
		else if(lenSetting.equals("90 Days"))
		{
			_dw.setGameLength(90);
			_dw.setProgressBar(90);
		}
		else if(lenSetting.equals("120 Days"))
		{
			_dw.setGameLength(120);
			_dw.setProgressBar(120);
		}
		else
		{
			_dw.setGameLength(5);
			_dw.setProgressBar(5);
		}
	}

	public void commitResetScores()
	{
		if(resetScores.isSelected())
		{
			HighScoreController hss = new HighScoreController(_dw);
			hss.resetAllScores();
		}
	}

	public void actionPerformed(ActionEvent event)
	{
		String actionCmd = event.getActionCommand();
		
		if(actionCmd.equals("30 Days") 
				|| actionCmd.equals("60 Days") 
				|| actionCmd.equals("90 Days") 
				|| actionCmd.equals("120 Days"))
		{
			lenSetting = actionCmd;
		}
		else
		{
			lenSetting = "";
		}
	}

	private void setCashStart(String cash)
	{
		if(Util.isLong(cash))
		{
			Player p = _dw.getPlayer();
			p.setStartCashAmount(Long.parseLong(cash));
			p.reset();
		}
	}

	private void setBankStart(String bank)
	{
		if(Util.isLong(bank))
		{
			Player p = _dw.getPlayer();
			p.setStartBankAmount(Long.parseLong(bank));
			p.reset();
		}
	}

	private void setDebtStart(String debt)
	{
		if(Util.isLong(debt))
		{
			Player p = _dw.getPlayer();
			p.setStartDebtAmount(Long.parseLong(debt));
			p.reset();
		}
	}

	private void setDebtInterest(String num)
	{
		if(Util.isDouble(num))
		{
			Player p = _dw.getPlayer();
			p.setDebtInterest(Double.parseDouble(num));
		}
	}

	private void setBankInterest(String num)
	{
		if(Util.isDouble(num))
		{
			Player p = _dw.getPlayer();
			p.setBankInterest(Double.parseDouble(num));
		}
	}

	private void setPlayerName(String name)
	{
		Player p = _dw.getPlayer();
		p.setName(name);
	}

	private JPanel getContents()
	{
		JPanel panel = new JPanel();
		panel.setLayout(null);
		Dimension dim = new Dimension(400,400);
		panel.setMaximumSize(dim);
		panel.setMinimumSize(dim);
		panel.setPreferredSize(dim);	
	
		//game length
		JLabel gameLen = new JLabel("Game Length");
		gameLen.setBounds(5,5,100,20);
		gameLen.setFont(labFontBold);
		JRadioButton d30 = new JRadioButton("30 Days");
		d30.addActionListener(this);
		d30.setBounds(5,25,100,20);
		
		if(30 == _dw.getGameLength())
		{
			d30.setSelected(true);
			lenSetting = "30 Days";
		}
		
		JRadioButton d60 = new JRadioButton("60 Days");
		d60.addActionListener(this);
		d60.setBounds(5,50,100,20);

		if(60 == _dw.getGameLength())
		{
			d60.setSelected(true);
			lenSetting = "60 Days";
		}
		
		JRadioButton d90 = new JRadioButton("90 Days");
		d90.addActionListener(this);
		d90.setBounds(5,75,100,20);

		if(90 == _dw.getGameLength())
		{
			d90.setSelected(true);
			lenSetting = "90 Days";
		}
		
		JRadioButton d120 = new JRadioButton("120 Days");
		d120.addActionListener(this);
		d120.setBounds(5,100,100,20);

		if(120 == _dw.getGameLength())
		{
			d120.setSelected(true);
			lenSetting = "120 Days";
		}

		if(5 == _dw.getGameLength())
		{
			lenSetting = "5 Days";
		}
		
		ButtonGroup bg = new ButtonGroup();
		bg.add(d30);
		bg.add(d60);
		bg.add(d90);
		bg.add(d120);
		panel.add(gameLen);
		panel.add(d30);
		panel.add(d60);
		panel.add(d90);
		panel.add(d120);

		Player p = _dw.getPlayer();
		
		//cash value
		JLabel cashStartLab = new JLabel("Starting Cash:");
		cashStartLab.setBounds(5, 140,120,20);
		cashStartLab.setFont(labFontBold);
		cashStart = new JTextField();
		cashStart.setText(new Long(p.getStartCash()).toString());
		cashStart.setBounds(150,140,100,20);
		cashStart.setActionCommand("StartCash");
		cashStart.addActionListener(this);

		//bank value
		JLabel bankStartLab = new JLabel("Starting Bank:");
		bankStartLab.setBounds(5,165,120,20);
		bankStartLab.setFont(labFontBold);
		bankStart = new JTextField();
		bankStart.setText(new Long(p.getStartBank()).toString());
		bankStart.setBounds(150,165,100,20);
		bankStart.setActionCommand("StartBank");
		bankStart.addActionListener(this);

		//debt value
		JLabel debtStartLab = new JLabel("Starting Debt:");
		debtStartLab.setBounds(5,190,120,20);
		debtStartLab.setFont(labFontBold);
		debtStart = new JTextField();
		debtStart.setText(new Long(p.getStartDebt()).toString());
		debtStart.setBounds(150,190,100,20);
		debtStart.setActionCommand("StartDebt");
		debtStart.addActionListener(this);

		//name value
		JLabel nameLab = new JLabel("Player Name:");
		nameLab.setFont(labFontBold);
		nameLab.setBounds(5,215,120,20);
		playerName = new JTextField();
		playerName.setText(p.getName());
		playerName.setBounds(150,215,100,20);
		playerName.setActionCommand("PlayerName");
		playerName.addActionListener(this);

		//bank interest value
		JLabel bankIntLab = new JLabel("Bank Interest Rate:");
		bankIntLab.setFont(labFontBold);
		bankIntLab.setBounds(5,240,120,20);
		bankInt = new JTextField();
		bankInt.setText(new Double(p.getBankInterest()).toString());
		bankInt.setBounds(150,240,100,20);
		bankInt.addActionListener(this);

		//debt interest value
		JLabel debtIntLab = new JLabel("Debt Interest Rate:");
		debtIntLab.setFont(labFontBold);
		debtIntLab.setBounds(5,265,120,20);
		debtInt = new JTextField();
		debtInt.setText(new Double(p.getDebtInterest()).toString());
		debtInt.setBounds(150,265,100,20);
		debtInt.addActionListener(this);
	
		//reset high scores

		resetScores = new JCheckBox("Reset High Scores", false);
		resetScores.setBounds(5,300,200,50);
		
	
		//warning
		String msg = "Warning!\nChanging these settings will reset your current game.\nPress cancel to avoid doing this";
		JTextArea warn = new JTextArea(msg);
		warn.setBounds(5,350,600,50);
		warn.setBackground(new Color(204,204,204));
		warn.setFont(labFontPlain);
		warn.setEditable(false);
		

		panel.add(cashStartLab);
		panel.add(cashStart);
		panel.add(bankStartLab);
		panel.add(bankStart);
		panel.add(debtStartLab);
		panel.add(debtStart);
		panel.add(nameLab);
		panel.add(playerName);
		panel.add(bankIntLab);
		panel.add(bankInt);
		panel.add(debtIntLab);
		panel.add(debtInt);
		panel.add(resetScores);
		panel.add(warn);
		
		return panel;
	}
}
