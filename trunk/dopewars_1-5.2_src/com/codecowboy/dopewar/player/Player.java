package com.codecowboy.dopewar.player;

import com.codecowboy.dopewar.items.*;
import com.codecowboy.dopewar.exception.*;

/**
 * File description
 * represents a player of the game.
 *
 * Copyright (c) www.codecowboy.com 2001
 *
 * $RCSfile: Player.java,v $
 * @author $Author: ccb $
 * @version $Revision: 1.12 $, $Date: 2001/11/19 13:56:55 $
 */
public class Player
{
	private String _name = "";
	private Coat _coat;
	private long _cashAmount = 0;
	private long _bankAmount = 0;
	private long _debtAmount = 0;
	private String DEF_NAME = "Player";
	private Coat DEF_COAT = new Coat();
	private long DEF_CASH_AMT = 2200;
	private long DEF_BANK_AMT = 0;
	private long DEF_DEBT_AMT = 5000;
	private long _startCashAmount = DEF_CASH_AMT;
	private long _startBankAmount = DEF_BANK_AMT;
	private long _startDebtAmount = DEF_DEBT_AMT;
	private long _maxDebtAmount = 100000;
	private double DEF_DEBT_INTEREST = 0.1;
	private double DEF_BANK_INTEREST = 0.01;
	private final int DEF_HEALTH = 100;
	private int _health = 100;
	private double _bankInterest = DEF_BANK_INTEREST;
	private double _debtInterest = DEF_DEBT_INTEREST;
	

	public Player(String name, 
				  Coat coat, 
				  long cashAmount, 
				  long bankAmount, 
				  long debtAmount)
	{
		setName(name);
		setCoat(coat);
		setCashAmount(cashAmount);
		setBankAmount(bankAmount);
		setDebtAmount(debtAmount);
	}

	public Player()
	{
		setName(DEF_NAME);
		setCoat(DEF_COAT);
		setCashAmount(DEF_CASH_AMT);
		setBankAmount(DEF_BANK_AMT);
		setDebtAmount(DEF_DEBT_AMT);		
	}

	public Player(String name)
	{
		setName(name);
		setCoat(DEF_COAT);
		setCashAmount(DEF_CASH_AMT);
		setBankAmount(DEF_BANK_AMT);
		setDebtAmount(DEF_DEBT_AMT);		
	}

	public void setName(String name)
	{
		this._name = name;
	}

	private void setCoat(Coat coat)
	{
		this._coat = coat;
	}

	public Coat getCoat()
	{
		return this._coat;
	}

	public void setCashAmount(long cashAmount)
	{
		this._cashAmount = cashAmount;
	}

	public void setStartCashAmount(long cashAmount)
	{
		this._startCashAmount = cashAmount;
	}

	public void setBankAmount(long bankAmount)
	{
		this._bankAmount = bankAmount;
	}

	public void setStartBankAmount(long bankAmount)
	{
		this._startBankAmount = bankAmount;
	}

	public void setDebtAmount(long debtAmount)
	{
		this._debtAmount = debtAmount;
	}

	public void setStartDebtAmount(long debtAmount)
	{
		this._startDebtAmount = debtAmount;
	}

	public void setHealth(int h)
	{
		this._health = h;
	}

	public void reset()
	{
		this._cashAmount = _startCashAmount;
		this._bankAmount = _startBankAmount;
		this._debtAmount = _startDebtAmount;
		this._coat.removeAllItems();
		this._health = DEF_HEALTH;
	}

	public void addCash(long cash)
	{
		_cashAmount += cash;
	}

	public void removeCash(long cash)
	{
		_cashAmount -= cash;
	}

	public void addBankCash(long cash)
	{
		_bankAmount += cash;
	}

	public void removeBankCash(long amt)
	{
		_bankAmount -= amt;	
	}

	public void removeHealth(int amt)
	{
		_health -= amt;
	}

	public void addHealth(int amt)
	{
		_health += amt;
	}

	public boolean isDead()
	{
		boolean rv = false;
		
		if(_health <= 0)
		{
			rv = true;
		}

		return rv;
	}

	public long getCash()
	{
		return _cashAmount;
	}

	public long getStartCash()
	{
		return _startCashAmount;
	}

	public long getBank()
	{
		return _bankAmount;
	}

	public long getStartBank()
	{
		return _startBankAmount;
	}

	public long getDebt()
	{
		return _debtAmount;
	}

	public long getStartDebt()
	{
		return _startDebtAmount;
	}

	public long getMaxDebtAmount()
	{
		return _maxDebtAmount;
	}

	public int getHealth()
	{
		return _health;
	}

	public void interestOnDebt()
	{
		double interest = _debtInterest * _debtAmount;		
		long rv = _debtAmount + (long)interest;
		
		this._debtAmount = rv;
	}
	
	public void interestOnBank()
	{
		double interest = _bankInterest * _bankAmount;
		long rv = _bankAmount + (long)interest;

		this._bankAmount = rv;
	}

	public String getName()
	{
		return _name;
	}

	public void setBankInterest(double num)
	{
		this._bankInterest = num;
	}

	public double getBankInterest()
	{
		return this._bankInterest;
	}

	public void setDebtInterest(double num)
	{
		this._debtInterest = num;
	}

	public double getDebtInterest()
	{
		return this._debtInterest;
	}

	public void addDebtAmount(long amount)
	{
		_debtAmount += amount;
	}

	public void reduceDebtAmount(long amount)
	{
		_debtAmount -= amount;
	}

	public long getWorth()
	{
		long rv = (_cashAmount + _bankAmount) - _debtAmount;
		return rv;
	}

	public String getCoatInfo()
	{
		String rv  = _coat.getItemCount() + "/" + _coat.getCapacity();

		return rv;
	}
}
