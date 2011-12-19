
package com.codecowboy.dopewar.application;

import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.lang.*;

/**
 * File description
 * represents a drug. 
 *
 * Copyright (c) www.codecowboy.com 2001
 *
 * $RCSfile: About.java,v $
 * @author $Author: ccb $
 * @version $Revision: 1.4 $, $Date: 2001/12/01 00:38:50 $
 */
public class About extends JOptionPane
{
	public Dopewars _dw;
	private final String version = "v.1.51";

	public About()
	{
		super("");
	}

	public void init(Dopewars dw)
	{
		_dw = dw;
		JFrame parent = _dw.getFrame();
		JPanel panel = getContents();
		Object[] comp = {panel,"OK"};
		setOptions(comp);
    	JDialog dialog = createDialog(parent, "About Dopewars " + version);
	    dialog.setVisible(true);
	}

	public JPanel getContents()
	{
		JPanel panel = new JPanel();
		panel.setLayout(null);
		Dimension dim = new Dimension(350,220);
		panel.setMaximumSize(dim);
		panel.setMinimumSize(dim);
		panel.setPreferredSize(dim);

		

		StringBuffer txt = new StringBuffer();

		txt.append("<html><table align=top border=0><tr><td><font face=verdana size=1 color=black>");
		txt.append("<b>Dopewars " + version  + "</b><p>");
		txt.append("A game of pure commerce. Buy and sell drugs as much as you can in the time limit.");
		txt.append("<p><p>This game has been developed in many ways, but (to the best of my knowledge) not in Swing.");
		txt.append(" I therefore decided to learn swing whilst making it. For detailed version information please ");
		txt.append("consult the readme.txt in the accompanying jar file.");
		txt.append("<p><p><b>Credits</b><p>Programming: Rupert Jones,</b>Jeremy Leach");
		txt.append("<p>Suggestions and help: Andy Marks and <a href=http://www.bouncycastle.org target=_blank>Jon Eaves</a>.");
		txt.append("<p>Play testers: Jon Eaves, Warner Godfrey, Andy Marks, Travis Winters, Nick Jones, Gary Jarrel, Steve Jones");
		txt.append("</td></tr></table></html>");
		JLabel warn = new JLabel(txt.toString());
        warn.setHorizontalAlignment(JLabel.CENTER);
		warn.setVerticalAlignment(JLabel.TOP);
        panel.setLayout(new GridLayout(1, 1));
		
		panel.add(warn);
		
		return panel;
	}
}
