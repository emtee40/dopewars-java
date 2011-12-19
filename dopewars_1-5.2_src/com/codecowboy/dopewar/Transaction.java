
package com.codecowboy.dopewar;

import com.codecowboy.dopewar.*;
import com.codecowboy.dopewar.items.*;
import com.codecowboy.dopewar.player.*;
import com.codecowboy.dopewar.exception.*;
/**
 * File description
 * Interface describing a transaction class.
 * Copyright (c) www.codecowboy.com 2001
 *
 * $RCSfile: Transaction.java,v $
 * @author $Author: ccb $
 * @version $Revision: 1.7 $, $Date: 2001/11/27 10:03:34 $
 */
public interface Transaction
{
	public Player buy(Player thisPlayer, Drug thisDrug, int quantity) 
		throws TransactionException;

	public Player sell(Player thisPlayer, Drug thisDrug, int quantity, long salePrice)
		throws TransactionException;

	public Player deposit(Player thisPlayer, long amt)
		throws TransactionException;

	public Player withdrawl(Player thisPlayer, long amt)
		throws TransactionException;

	public Player borrow(Player thisPlayer, long amt, long maxLoan)
		throws TransactionException;

	public Player repay(Player thisPlayer, long amt)
		throws TransactionException;

	public void purchaseCoat(Player thisPlayer, long coatPrice)
		throws TransactionException;
}
