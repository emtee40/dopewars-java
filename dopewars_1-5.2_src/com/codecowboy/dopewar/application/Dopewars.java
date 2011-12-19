package com.codecowboy.dopewar.application;

import javax.swing.*;
import javax.swing.event.*;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.text.*;
import com.codecowboy.dopewar.Event;
import com.codecowboy.dopewar.*;
import com.codecowboy.dopewar.items.*;
import com.codecowboy.dopewar.player.*;
import com.codecowboy.dopewar.locations.*;
import com.codecowboy.dopewar.exception.*;
import com.codecowboy.dopewar.util.*;

/**
 * File description
 * Main game flow GUI file.
 *
 * Copyright (c) www.codecowboy.com 2001
 *
 * $RCSfile: Dopewars.java,v $
 * @author $Author: ccb $
 * @version $Revision: 1.37 $, $Date: 2001/11/27 10:03:31 $
 */
public class Dopewars implements 
			 ActionListener, 
			 MouseListener, 
			 TableModelListener, 
			 FocusListener,
			 ListSelectionListener
{
	public JFrame frame;
	public JProgressBar progressBar;
	public JTextArea story;
	public int currentDay = 1;	
	public int gameLength = 30;
	private int loanDayCount = 0;
	private String[] buyData;
	private String[] sellData;
	private JList dataListBuy;
	private JTable sellTbl;
	private JTable buyTbl;
	private SellTableModel stm;
	private BuyTableModel btm;
	private int indexBuy = -1;
	private int indexSell = -1;	
	private PriceListController plc;
	private PriceList pl;
	private Player player;
	private Coat coat;
	private JLabel nameLabel;
	private JLabel cashLabel;
	private JLabel bankLabel;
	private JLabel debtLabel;
	private JLabel lengthLabel;
	private JLabel dayLabel;
	private JLabel locLabel;
	private JLabel worthLabel;
	private JLabel coatLabel;
	private JLabel healthLabel;
	private JLabel loanSharkLabel;
	private JButton bankButton;
	private JButton loanButton;
	private Location currentLocation;
	private LocationController lc;
	private TransactionController tc;
	private boolean gameOver = false;
	private ListSelectionModel rowSMBuy;
	private ListSelectionModel rowSMSell;
	
    public static void main(String[] args)
	{
		Dopewars dw = new Dopewars();
    }
	
	public Dopewars()
	{
		init();
		refreshLabels();
	}

	public void init()
	{
		try
		{
            UIManager.setLookAndFeel(
                UIManager.getCrossPlatformLookAndFeelClassName());
        }
		catch (Exception e) {}
		//Create the top-level container and add contents to it.
        frame = new JFrame("Dopewars");
		plc = new PriceListController();
		lc = new LocationController();
		tc = new TransactionController();
		currentLocation = lc.getStartLocation();
		frame.setResizable(false);
        Component contents = createComponents();
        frame.getContentPane().add(contents);
		frame = buildMenus(frame);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
		player = new Player();
		refreshLabels();
		setLocaleAbility();
        frame.setVisible(true);
	}

	public void actionPerformed(ActionEvent event)
	{
		String actionCmd = event.getActionCommand();
		try
		{
			if(! gameOver)
			{
				if(actionCmd.equals("Cab"))
				{
					doCab();
				}
				else if(actionCmd.equals("Buy"))
				{
					doBuy();
				}
				else if(actionCmd.equals("Sell"))
				{
					doSell();
				}
				else if(actionCmd.equals("Bank"))
				{
					doBank();
				}
				else if(actionCmd.equals("Loan"))
				{
					doLoan();
				}
			}
			
			if(actionCmd.equals("About Dopewars"))
			{
				doAboutDW();
			}
			else if(actionCmd.equals("High Scores"))
			{
				doHighScores();
			}
			else if(actionCmd.equals("Exit Game"))
			{
				System.exit(0);
			}
			else if(actionCmd.equals("New Game"))
			{
				restart();
			}
			else if(actionCmd.equals("Options"))
			{
				showSettings();			
			}
		}
		catch(java.lang.Throwable t)
		{
			t.printStackTrace();	
		}
	}

	public void doHighScores()
	{
		HighScores hs = new HighScores();
		hs.init(this);
	}
	
	public void doBuy()
	{
		if(indexBuy != -1)
		{
			Drug buyThis = pl.getDrug(indexBuy);
			Buy b = new Buy();
			b.init(this,buyThis);
			newSellData();
			refreshLabels();
		}
	}

	public PriceList getPriceList()
	{
		return pl;
	}

	public void doSell()
	{
		if(indexSell != -1)
		{	
			Coat c = player.getCoat();
			Drug sellThis = c.getDrugFromPackList(indexSell);
			Sell s = new Sell();
			s.init(this, sellThis);
			indexSell = -1;
			newSellData();
			refreshLabels();
		}
	}

	public void doBank()
	{
		Bank b = new Bank();
		b.init(this);
		refreshLabels();
	}

	public void doCab()
	{
		GoLocation gl = new GoLocation("");
		gl.init(this, currentLocation);
	}

	public void doLoan()
	{
		Loan l = new Loan();
		l.init(this);
		refreshLabels();
	}

	public void mouseClicked(MouseEvent event)
	{
		if (event.getClickCount() == 2)
		{
			 JTable src = (JTable)event.getSource();
			 
			 if("buy".equals(src.getName()))
			 {
			 	doBuy();	
			 }
			 else if("sell".equals(src.getName()))
			 {
			 	doSell();
			 }
		}
	}

	public void doAboutDW()
	{
		About ab = new About();
		ab.init(this);
	}

	public void resetLoanDayCount()
	{
		loanDayCount = 0;
	}

	public int getLoanDayCount()
	{
		return loanDayCount;
	}

	public void goNextDay()
	{
		if(currentDay < gameLength)
		{
			this.currentDay++;
			setProgress(currentDay);
			refreshLabels();
			addMessage("Took a cab to " + currentLocation.getName());
			newBuyData();
			newSellData();
			player.interestOnDebt();
			player.interestOnBank();			
			setLocaleAbility();
			checkLoanShark();	
			handleEvents();			
		}
		else
		{
			doEndGame();
		}
	}

	public void checkLoanShark()
	{
		if(player.getDebt() > 0)
		{
			this.loanDayCount++;
			
			if((loanDayCount%5) == 0)
			{
				doLoanShark();
			}
		}
		else
		{
			resetLoanDayCount();
		}
	}

	public int daysToLoanShark()
	{
		int rv = 0;
		int now = loanDayCount;
		
		if(loanDayCount == 0)
		{
			return 5;
		}
		
		for(int i = now; ; i++)
		{
			if(i % 5 == 0)
			{
				break;
			}
			rv++;
		}

		if(rv == 0)
		{
			rv = 5;
		}

		return rv;
		
	}

	public void doLoanShark()
	{
		LoanShark ls = new LoanShark();
		ls.init(this);
		refreshLabels();

		if(player.getHealth() <= 0)
		{
			doEndGame();	
		}
	}

	public void handleEvents()
	{
		EventHandler eh = new EventHandler();
		
		if(eh.hasEvents())
		{
			ArrayList events = eh.getList();
			
			for(int i=0; i < events.size(); i++)
			{
				Event thisEvent = (Event) events.get(i);

				if(thisEvent.isPriceEvent())
				{
					pl = eh.applyPriceEvent(pl, thisEvent);
					String msg = eh.getPriceEventMessage();
					JOptionPane.showMessageDialog(frame, msg);
					addMessage(msg);
					newBuyData(getBuyData(pl));
				}
				else if(thisEvent.isPoliceEvent())
				{
				}
				else if(thisEvent.isCoatEvent())
				{
					Coat currentCoat = player.getCoat();
					long offerPrice = eh.getNewCoatPrice(currentCoat.getPrice());
					int offerCap = eh.getNewCoatCapacity(currentCoat.getCapacity());
					int result = JOptionPane.showConfirmDialog(null,
								"Would you like to purchase a new coat with a capacity of " 
								+ offerCap + " units, costing $" 
								+ offerPrice,"Wanna new Coat?", 
								JOptionPane.YES_NO_OPTION);

					if(result == JOptionPane.YES_OPTION)
					{
						
						try
						{
							tc.purchaseCoat(player,offerPrice);
							currentCoat = eh.getNewCoat(currentCoat, offerPrice, offerCap);
							addMessage("Purchased new coat (" 
										+ offerCap + ") for $" 
										+ offerPrice);
							refreshLabels();
						}
						catch(TransactionException te)
						{
							JOptionPane.showMessageDialog(null,te.getMessage(),"Transaction Error",JOptionPane.ERROR_MESSAGE);
						}
					}
				}
			}
			eh.resetPriceEvents();
		}
	}

	public void newBuyData()
	{
		btm.setData(getBuyData());
		btm.fireTableDataChanged();
	}

	public void newBuyData(String[][] p)
	{
		btm.setData(p);
		btm.fireTableDataChanged();
	}

	public void doEndGame()
	{
		addMessage("Game Over");
		HighScoreController hsc = new HighScoreController(this);
		boolean topTen = hsc.submit();
		EndGame eg = new EndGame();
		eg.init(this,topTen);
		gameOver = true;
	}
	
	public void setProgress(int day)
	{
		this.progressBar.setValue(day);
	}

	public void setCurrentLocation(Location here)
	{
		this.currentLocation = here;
	}

	public void restart()
	{
		this.currentDay = 1;
		story.setText("");
		setProgress(currentDay);
		currentLocation = lc.getStartLocation();

		if(player == null)
		{
			player = new Player();
		}
		else
		{
			player = new Player(player.getName());
		}
		player.reset();
		newBuyData();
		newSellData();
		resetLoanDayCount();
		refreshLabels();		
		gameOver = false;
	}

	public void refreshLabels()
	{
		String htmlValue = "<html><font face=Verdana color=black size=2>";	
		String htmlEnd = "</font></html>";
		progressBar.setString("Day " + currentDay + "/" + gameLength);
		nameLabel.setText(htmlValue + player.getName() + htmlEnd);
		cashLabel.setText(htmlValue + Util.formatCurrency(player.getCash()) + htmlEnd);
		bankLabel.setText(htmlValue + Util.formatCurrency(player.getBank()) + htmlEnd);
		debtLabel.setText(htmlValue + Util.formatCurrency(player.getDebt()) + htmlEnd);
		lengthLabel.setText(htmlValue + new Integer(gameLength).toString() + " Days" + htmlEnd);
		dayLabel.setText(htmlValue + new Integer(currentDay).toString() + htmlEnd);
		locLabel.setText(htmlValue + currentLocation.getName() + htmlEnd);
		worthLabel.setText(htmlValue + Util.formatCurrency(player.getWorth()) + htmlEnd);
		coatLabel.setText(htmlValue + player.getCoatInfo() + htmlEnd);
		healthLabel.setText(htmlValue + player.getHealth() + "%" + htmlEnd);
		loanSharkLabel.setText(htmlValue + daysToLoanShark() + " days" + htmlEnd);
	}

	public void addMessage(String msg)
	{
		story.append(msg + "\n");
	}
	
	public void newSellData()
	{
		stm.setData(getSellData());
		stm.fireTableDataChanged();
	}

	public String[][] getSellData()
	{
		Coat thisCoat = player.getCoat();
		String[][] rv;
		
		rv = thisCoat.drugsToArray();

		if(rv == null)
		{
			rv = getBlankArray();
		}
		
		return rv;
	}

	public String[][] getBlankArray()
	{
		String[][] rv = {};
		return rv;
	}
	public String[][] getBuyData()
	{
		plc = new PriceListController();
		pl = plc.getPriceList();
		return pl.toArray();
	}

	public String[][] getBuyData(PriceList p)
	{
		return p.toArray();
	}

	public void showSettings()
	{
		Settings chgSettings = new Settings("");
		chgSettings.init(this);
		refreshLabels();
	}

	public JFrame getFrame()
	{
		return frame;
	}

	public int getGameLength()
	{
		return gameLength;
	}

	public void setGameLength(int len)
	{
		this.gameLength = len;
	}

	public Player getPlayer()
	{
		return player;
	}

	public Coat getCoat()
	{
		return coat;
	}

	public void setProgressBar(int max)
	{
		this.progressBar.setMaximum(max);
	}

	private JFrame buildMenus(JFrame frame)
	{
		JMenuBar menu = new JMenuBar();
		frame.setJMenuBar(menu);
		
		JMenu gameMenu = new JMenu("Game");
		JMenu optionMenu = new JMenu("Settings");
		JMenu helpMenu = new JMenu("Help");
		
		JMenuItem newGame = new JMenuItem("New Game",KeyEvent.VK_T);
		newGame.addActionListener(this);
		JMenuItem exitGame = new JMenuItem("Exit Game", KeyEvent.VK_T);
		exitGame.addActionListener(this);
		JMenuItem highScore = new JMenuItem("High Scores",KeyEvent.VK_T);
		highScore.addActionListener(this);
		gameMenu.add(newGame);
		gameMenu.add(exitGame);
		gameMenu.add(highScore);
		
		JMenuItem settings = new JMenuItem("Options", KeyEvent.VK_T);
		settings.addActionListener(this);
		optionMenu.add(settings);
		

		JMenuItem about = new JMenuItem("About Dopewars", KeyEvent.VK_T);
		about.addActionListener(this);

		helpMenu.add(about);

		menu.add(gameMenu);
		menu.add(optionMenu);
		menu.add(helpMenu);

		return frame;
	}

	private void setLocaleAbility()
	{
		loanButton.setEnabled(currentLocation.canLoan());
		bankButton.setEnabled(currentLocation.canBank());
	}
	
	public Component createComponents()
	{
        //master pane
		JPanel pane = new JPanel();
        pane.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
        pane.setLayout(null);

		Insets insets = pane.getInsets();
				
		//buttons
		JButton buyButton = new JButton("Buy");
		pane.add(buyButton);
		buyButton.addActionListener(this);
		JButton sellButton = new JButton("Sell");
		pane.add(sellButton);
		sellButton.addActionListener(this);
		JButton cabButton = new JButton("Cab");
		pane.add(cabButton);
		cabButton.addActionListener(this);
		bankButton = new JButton("Bank");
		pane.add(bankButton);
		bankButton.addActionListener(this);
		loanButton = new JButton("Loan");
		loanButton.addActionListener(this);
		pane.add(loanButton);
		
		//placement and dim
		Rectangle one = new Rectangle(310,300, 80, 30);
		Rectangle two = new Rectangle(310,335, 80,30);
		Rectangle three = new Rectangle(310,370, 80, 30);
		Rectangle four = new Rectangle(310,405,80,30);
		Rectangle five = new Rectangle(310,440,80,30);
		
		bankButton.setBounds(three);
		buyButton.setBounds(one);		
		sellButton.setBounds(two);
		cabButton.setBounds(five);
		loanButton.setBounds(four);

		//labels
		String htmlName = "<html><font color=black face=Verdana size=2><b>";
		String htmlEnd = "</b></font></html>";
		JLabel buyLabel = new JLabel(htmlName + "Stuff to buy" + htmlEnd);
		JLabel sellLabel = new JLabel(htmlName + "Your stash" + htmlEnd);
		JLabel labName = new JLabel(htmlName + "Name:" + htmlEnd);
		JLabel labCash = new JLabel(htmlName + "Cash:" + htmlEnd);
		JLabel labBank = new JLabel(htmlName + "Bank:" + htmlEnd);
		JLabel labDebt = new JLabel(htmlName + "Debt:" + htmlEnd);
		JLabel labLength = new JLabel(htmlName + "Game Length:" + htmlEnd);
		JLabel labDay = new JLabel(htmlName + "Current day:" + htmlEnd);
		JLabel labLoc = new JLabel(htmlName + "Location:" + htmlEnd);
		JLabel labWorth = new JLabel(htmlName + "Worth:" + htmlEnd);
		JLabel labCoat = new JLabel(htmlName + "Coat:" + htmlEnd);
		JLabel labHealth = new JLabel(htmlName + "Health:" + htmlEnd);
		JLabel labLoanShark = new JLabel(htmlName + "Tony visit:" + htmlEnd);
		
		nameLabel = new JLabel();
		cashLabel = new JLabel();
		bankLabel = new JLabel();
		debtLabel = new JLabel();
		lengthLabel = new JLabel(gameLength + " Days");
		dayLabel = new JLabel(new Integer(currentDay).toString());
		locLabel = new JLabel();
		worthLabel = new JLabel();
		coatLabel = new JLabel();
		healthLabel = new JLabel();
		loanSharkLabel = new JLabel();

		Rectangle buyLabelRect = new Rectangle(5,230,80,100);
		Rectangle sellLabelRect = new Rectangle(397,230,80,100);
		buyLabel.setBounds(buyLabelRect);
		sellLabel.setBounds(sellLabelRect);
		pane.add(buyLabel);
		pane.add(sellLabel);

		//name labels
		Rectangle labNameRect = new Rectangle(5,5,100,18);	
		Rectangle labGameLenRect = new Rectangle(5,25,100,18);
		Rectangle labDayRect = new Rectangle(5,45,100,18);
		Rectangle labLocRect = new Rectangle(530,5,100,18);
		Rectangle labCoatRect = new Rectangle(5,65,100,18);
			
		Rectangle labCashRect = new Rectangle(5,105,100,18);
		Rectangle labBankRect = new Rectangle(5,125,100,18);
		Rectangle labDebtRect = new Rectangle(5,145,100,18);
		Rectangle labWorthRect = new Rectangle(5,165,100,18);
		Rectangle labHealthRect = new Rectangle(5,205,100,18);
		Rectangle labLoanSharkRect = new Rectangle(5,225,100,18);
			
		//value labels
		Rectangle nameLabelRect = new Rectangle(101,5,180,18);
		Rectangle gameLenLabelRect = new Rectangle(101,25,180,18);
		Rectangle dayLabelRect = new Rectangle(101,45,180,18);
		Rectangle locLabelRect = new Rectangle(600,5,180,18);
		Rectangle coatLabelRect = new Rectangle(101,65,180,18);
		Rectangle cashLabelRect = new Rectangle(101,105,180,18);
		Rectangle bankLabelRect = new Rectangle(101,125,180,18);
		Rectangle debtLabelRect = new Rectangle(101,145,180,18);
		Rectangle worthLabelRect = new Rectangle(101,165,180,18);
		Rectangle healthLabelRect = new Rectangle(101,205,180,18);
		Rectangle loanSharkLabelRect = new Rectangle(101,225,180,18);
		
		//value labels.
		nameLabel.setBounds(nameLabelRect);
		cashLabel.setBounds(cashLabelRect);
		debtLabel.setBounds(debtLabelRect);
		bankLabel.setBounds(bankLabelRect);
		lengthLabel.setBounds(gameLenLabelRect);
		dayLabel.setBounds(dayLabelRect);
		locLabel.setBounds(locLabelRect);
		worthLabel.setBounds(worthLabelRect);
		coatLabel.setBounds(coatLabelRect);
		healthLabel.setBounds(healthLabelRect);
		loanSharkLabel.setBounds(loanSharkLabelRect);
		
		//name labels
		labName.setBounds(labNameRect);
		labCash.setBounds(labCashRect);
		labBank.setBounds(labBankRect);
		labDebt.setBounds(labDebtRect);
		labLength.setBounds(labGameLenRect);
		labDay.setBounds(labDayRect);
		labLoc.setBounds(labLocRect);
		labWorth.setBounds(labWorthRect);
		labCoat.setBounds(labCoatRect);
		labHealth.setBounds(labHealthRect);
		labLoanShark.setBounds(labLoanSharkRect);
		
		// value labels
		pane.add(nameLabel);
		pane.add(cashLabel);
		pane.add(bankLabel);
		pane.add(debtLabel);
		pane.add(lengthLabel);
		pane.add(dayLabel);
		pane.add(locLabel);
		pane.add(worthLabel);
		pane.add(coatLabel);
		pane.add(healthLabel);
		pane.add(loanSharkLabel);
		
		//name labels
		pane.add(labName);
		pane.add(labCash);
		pane.add(labBank);
		pane.add(labDebt);
		pane.add(labLength);
		pane.add(labDay);
		pane.add(labLoc);
		pane.add(labWorth);
		pane.add(labCoat);
		pane.add(labHealth);
		pane.add(labLoanShark);

		//progress bar
		//TODO : text format this somehow
		Font prog = new Font("Verdana",Font.PLAIN,10);
		progressBar = new JProgressBar(1, gameLength);
		progressBar.setFont(prog);
        progressBar.setValue(1);
		progressBar.setString("Day 1");
        progressBar.setStringPainted(true);
		pane.add(progressBar);
		progressBar.setBounds(200, 5, 300, 18);

		//text area
		story = new JTextArea(8,40);
		JScrollPane scrollPane = new JScrollPane(story);
		story.setLineWrap(true);
		story.setBackground(new Color(204,204,204));
		story.setFont(new Font("Courier",Font.PLAIN,11));
		story.setEditable(false);
		scrollPane.setBounds(200,30,497,228);
		pane.add(scrollPane);
	
		//lists

		stm = new SellTableModel();
		stm.setData(getBlankArray());
		sellTbl = new JTable(stm);
		sellTbl.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		stm.addTableModelListener(this);
		sellTbl.addFocusListener(this);
		rowSMSell = sellTbl.getSelectionModel();
		rowSMSell.addListSelectionListener(this);
		sellTbl.addMouseListener(this);
		sellTbl.setName("sell");

		btm = new BuyTableModel();
		btm.setData(getBlankArray());
		buyTbl = new JTable(btm);
		buyTbl.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		btm.addTableModelListener(this);
		buyTbl.addFocusListener(this);
		rowSMBuy = buyTbl.getSelectionModel();
		rowSMBuy.addListSelectionListener(this);
		buyTbl.addMouseListener(this);
		buyTbl.setName("buy");

		newBuyData();

		//panes
		JScrollPane scrollPaneSell = new JScrollPane(sellTbl);
		JScrollPane scrollPaneBuy = new JScrollPane(buyTbl);
		
		//add to master pane
		pane.add(scrollPaneSell);
		pane.add(scrollPaneBuy);

		//placement and dimensions
		Rectangle buyPaneRect = new Rectangle(5, 300, 300, 192);
		Rectangle sellPaneRect = new Rectangle(397, 300, 300, 192);
		scrollPaneSell.setBounds(sellPaneRect);
		scrollPaneBuy.setBounds(buyPaneRect);

		Dimension dim = new Dimension(700,500);
		pane.setMaximumSize(dim);
		pane.setMinimumSize(dim);
		pane.setPreferredSize(dim);

        return pane;
    }

	public void valueChanged(ListSelectionEvent event)
	{
		ListSelectionModel lsm = (ListSelectionModel)event.getSource();
		if(event.getValueIsAdjusting()) return;

		if(!lsm.isSelectionEmpty())
		{
			if(lsm.equals(rowSMBuy))
			{
				indexBuy = lsm.getMinSelectionIndex();
			}
			else if(lsm.equals(rowSMSell))
			{
				indexSell = lsm.getMinSelectionIndex();
			}
		}
	}

	public void tableChanged(TableModelEvent event)
	{}	

	public void focusGained(FocusEvent event)
	{}

	public void focusLost(FocusEvent event)
	{}

	public void mousePressed(MouseEvent event)
	{}

	public void mouseReleased(MouseEvent event)
	{}

	public void mouseEntered(MouseEvent event)
	{}

	public void mouseExited(MouseEvent event)
	{}
}
