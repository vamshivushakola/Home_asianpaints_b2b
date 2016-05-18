/**
 *
 */
package com.braintree.command.result;

public class BrainTreeCreatePaymentMethodResult extends BrainTreeAbstractResult
{
	private String paymentMethodToken;
	private String imageSource;
	private String cardType;
	private String cardNumber;
	private String expirationMonth;
	private String expirationYear;
	private String email;

	public String getPaymentMethodToken()
	{
		return paymentMethodToken;
	}

	public void setPaymentMethodToken(final String paymentMethodToken)
	{
		this.paymentMethodToken = paymentMethodToken;
	}

	public String getImageSource()
	{
		return imageSource;
	}

	public void setImageSource(final String imageSource)
	{
		this.imageSource = imageSource;
	}

	public String getCardType()
	{
		return cardType;
	}

	public void setCardType(final String cardType)
	{
		this.cardType = cardType;
	}

	public String getCardNumber()
	{
		return cardNumber;
	}

	public void setCardNumber(final String cardNumber)
	{
		this.cardNumber = cardNumber;
	}

	public String getExpirationMonth()
	{
		return expirationMonth;
	}

	public void setExpirationMonth(final String expirationMonth)
	{
		this.expirationMonth = expirationMonth;
	}

	public String getExpirationYear()
	{
		return expirationYear;
	}

	public void setExpirationYear(final String expirationYear)
	{
		this.expirationYear = expirationYear;
	}

	public void setEmail(final String email)
	{
		this.email = email;
	}

	public String getEmail()
	{
		return email;
	}

}
