
package com.codecowboy.dopewar;

import java.io.*;
import java.util.*;
import java.text.*;
import java.lang.*;
import com.codecowboy.dopewar.*;
import com.codecowboy.dopewar.items.*;
import com.codecowboy.dopewar.player.*;
import com.codecowboy.dopewar.exception.*;

/**
 * File description
 * Controls transactions for drugs, coats and loans.
 *
 * Copyright (c) www.codecowboy.com 2001
 *
 * $RCSfile: TransactionController.java,v $
 * @author $Author: ccb $
 * @version $Revision: 1.9 $, $Date: 2001/11/27 10:03:34 $
 */
public class TransactionController implements Transaction
{
	public TransactionController()
	{}
	
	public Player buy(Player thisPlayer, Drug thisDrug, int quantity)
		throws TransactionException
	{
		Coat thisCoat = thisPlayer.getCoat();
		int spacesRem = thisCoat.getSpacesAvailable();
		long costPerDrug = thisDrug.getPrice();
		long totalCost = costPerDrug * quantity;
		long cashAvailable = thisPlayer.getCash();
		
		if(quantity > spacesRem)
		{
			throw new TransactionException("You dont have enough spaces to buy that many.");			
		}

		if(totalCost > cashAvailable)
		{
			throw new TransactionException("You cannot afford that transaction.");
		}
		
		thisPlayer.removeCash(totalCost);

		int count = 0;
		for(int i=0; i < quantity; i++)
		{
			thisCoat.addDrug(thisDrug);
			count++;
		}

		return thisPlayer;
	}
	
	public Player sell(Player thisPlayer, Drug thisDrug, int quantity, long salePrice)
		throws TransactionException
	{
		Coat thisCoat = thisPlayer.getCoat();
		long totalRev = quantity * salePrice;
		thisPlayer.addCash(totalRev);

		for(int i=0; i < quantity; i++)
		{
			thisCoat.removeDrug(thisDrug.getName());
		}
		
		return thisPlayer;		
	}

	public Player deposit(Player thisPlayer, long amt)
		throws TransactionException		
	{
		long cash = thisPlayer.getCash();
		
		if(amt <= cash)
		{
			thisPlayer.addBankCash(amt);
			thisPlayer.removeCash(amt);
		}
		else
		{
			throw new TransactionException("You do not have sufficient funds to deposit that amount");
		}
			
		return thisPlayer;
	}

	public Player withdrawl(Player thisPlayer, long amt)
		throws TransactionException
	{
		long bank = thisPlayer.getBank();

		if(bank >= amt)
		{
			thisPlayer.addCash(amt);
			thisPlayer.removeBankCash(amt);
		}
		else
		{
			throw new TransactionException("You do not have sufficient funds to withdraw that amount");
		}

		return thisPlayer;
	}

	public Player borrow(Player thisPlayer, long amt, long maxLoan)
		throws TransactionException
	{
		long currentDebt = thisPlayer.getDebt();

		if((currentDebt + amt) > maxLoan)
		{
			throw new TransactionException("You cannot borrow more than $" + maxLoan + " in total");	
		}
		else
		{
			thisPlayer.addDebtAmount(amt);
			thisPlayer.addCash(amt);
		}
		return thisPlayer;
	}

	public Player repay(Player thisPlayer, long amt)
		throws TransactionException
	{
		if(amt > thisPlayer.getCash())
		{
			throw new TransactionException("You do have sufficient funds to repay that amount");
		}
		else if(amt > thisPlayer.getDebt())
		{
			throw new TransactionException("You cannot have a loan in credit");
		}		
		else
		{
			thisPlayer.removeCash(amt);
			thisPlayer.reduceDebtAmount(amt);
		}
			
		return thisPlayer;
	}

	public void purchaseCoat(Player thisPlayer, long coatPrice)
		throws TransactionException
	{
		long cash = thisPlayer.getCash();
		
		if(cash >= coatPrice)
		{
			thisPlayer.removeCash(coatPrice);
		}	
		else
		{
			throw new TransactionException("You do not have sufficient funds to purchase that coat.");
		}
	}
}
