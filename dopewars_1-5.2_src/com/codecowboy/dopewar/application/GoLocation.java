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
import com.codecowboy.dopewar.locations.*;

/**
 * File description
 * Get a cab pop up
 *
 * Copyright (c) www.codecowboy.com 2001
 *
 * $RCSfile: GoLocation.java,v $
 * @author $Author: ccb $
 * @version $Revision: 1.4 $, $Date: 2001/11/27 10:03:32 $
 */
public class GoLocation extends JOptionPane implements ActionListener
{
	public Dopewars _dw;
	public Location _currentLocation;
	public LocationController lc;
	public String currentSelection;
	JComboBox locList;
	public GoLocation(String msg)
	{
		super(msg);
	}

	public void init(Dopewars dw, Location currentLocation)
	{
		_dw = dw;
		_currentLocation = currentLocation;
		lc = new LocationController();
		JFrame parent = _dw.getFrame();
		JPanel panel = getContents();
		Object[] comp = {panel,"OK", "Cancel"};
		setOptions(comp);
    	JDialog dialog = createDialog(parent, "Get a cab somewhere...");
	    dialog.setVisible(true);
		Object result = getValue();
		String act = (String)result;
		
		if(act.equals("OK"))
		{
			Location loc = lc.getLocation(currentSelection);
			
			_dw.setCurrentLocation(loc);
			_dw.goNextDay();
		}
	}

	public void actionPerformed(ActionEvent event)
	{
        currentSelection = (String)locList.getSelectedItem();
	}

	public JPanel getContents()
	{
		JPanel panel = new JPanel();
		panel.setLayout(null);
		Dimension dim = new Dimension(210,100);
		panel.setMaximumSize(dim);
		panel.setMinimumSize(dim);
		panel.setPreferredSize(dim);
		String[] locs = lc.toArray(_currentLocation);
		currentSelection = locs[0];
		locList = new JComboBox(locs);
		locList.setSelectedIndex(0);
		locList.setBounds(5,50,200,20);		
		locList.addActionListener(this);

		panel.add(locList);

		return panel;
	}
}
