package com.codecowboy.dopewar.application;

import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.lang.*;

import com.codecowboy.dopewar.player.*;
import com.codecowboy.dopewar.*;
import com.codecowboy.dopewar.util.*;

/**
 * File description
 * High scores pop up
 *
 * Copyright (c) www.codecowboy.com 2001
 *
 * $RCSfile: HighScores.java,v $
 * @author $Author: ccb $
 * @version $Revision: 1.4 $, $Date: 2001/11/27 10:03:56 $
 */
public class HighScores extends JOptionPane
					  implements ActionListener
{
	private Dopewars _dw;
	private Player player;
	private HighScoreController hsc;

	public HighScores()
	{
		super("");
	}

	public void init(Dopewars dw)
	{
		_dw = dw;
		JFrame parent = _dw.getFrame();
		player = _dw.getPlayer();
		hsc = new HighScoreController(_dw);
		JPanel panel = getContents();
		Object[] comp = {panel,"OK"};
		setOptions(comp);
    	JDialog dialog = createDialog(parent, "High Scores");
	    dialog.setVisible(true);
		Object result = getValue();
		String act = (String)result;
	}

	public void actionPerformed(ActionEvent event)
	{
		String actionCmd = event.getActionCommand();
	}
	
	private JPanel getContents()
	{
		JPanel panel = new JPanel();
		panel.setLayout(null);
		Dimension dim = new Dimension(300,350);
		panel.setMaximumSize(dim);
		panel.setMinimumSize(dim);
		panel.setPreferredSize(dim);

		JTabbedPane tabbedPane = new JTabbedPane();

		Component panel1 = makeTextPanel(hsc.getList(HighScoreStore.dataFile30));
        tabbedPane.addTab("30 Days", icon, panel1, "Top Scores for 30 Days");
        tabbedPane.setSelectedIndex(0);

        Component panel2 = makeTextPanel(hsc.getList(HighScoreStore.dataFile60));
        tabbedPane.addTab("60 Days", icon, panel2, "Top Scores for 60 Days");

        Component panel3 = makeTextPanel(hsc.getList(HighScoreStore.dataFile90));
        tabbedPane.addTab("90 Days", icon, panel3, "Top Scores for 90 Days");

        Component panel4 = makeTextPanel(hsc.getList(HighScoreStore.dataFile120));
        tabbedPane.addTab("120 Days", icon, panel4, "Top Scores for 120 Days");

		tabbedPane.setSelectedIndex(getSelectedTab());
	
		panel.add(tabbedPane);
		panel.setLayout(new GridLayout(1, 1)); 
		return panel;
	}

    protected Component makeTextPanel(ArrayList list)
	{
		StringBuffer content = new StringBuffer();

		String htmlHStart = "<font face=verdana size=2 color=black><b>";
		String htmlHEnd = "</b></font>";

		String htmlStart = "<font face=verdana color=black size=2>";
		String htmlEnd = "</font>";
		
		content.append("<html>");
		if(list.size() > 0)
		{
			content.append("<table align=top width=260>");
			content.append("<tr><td width=30></td><td width=115>" + htmlHStart 
									+ "Name" + htmlHEnd 
									+ "</td><td width=115>" + htmlHStart + "Score" 
									+ htmlHEnd + "</td></tr>");
			int place = 1;
			for(int i = 0; i < list.size(); i++)
			{
				ScoreItem thisSI = (ScoreItem) list.get(i);
				content.append("<tr>");
				content.append("<td>" + htmlStart + place + htmlEnd + "</td>");
				content.append("<td>" + htmlStart + thisSI.getName() + htmlEnd + "</td>");
				content.append("<td>" + htmlStart + Util.formatCurrency(thisSI.getScore()) + htmlEnd + "</td></tr>");
				place++;
			}

			content.append("</table>");
		}
		else
		{
			content.append("<table width=260><tr><td>");
			content.append(htmlStart + " No Scores " + htmlEnd + "</td></tr></table>");
		}

		content.append("</html>");
		
		
		
        JPanel panel = new JPanel(false);
        JLabel filler = new JLabel(content.toString());
        filler.setHorizontalAlignment(JLabel.CENTER);
		filler.setVerticalAlignment(JLabel.TOP);
        panel.setLayout(new GridLayout(1, 1));
        panel.add(filler);
        return panel;
    }

	public int getSelectedTab()
	{
		int rv = 0;
		int len = _dw.getGameLength();
		
		if(len == 60)
		{
			rv = 1;
		}
		else if(len == 90)
		{
			rv = 2;
		}
		else if(len == 120)
		{
			rv = 3;
		}
		return rv;
	}
}

