package com.equalexperts.cart.stepdefs;

import java.math.BigDecimal;
import java.math.MathContext;

import com.equalexperts.cart.Cart;
import com.equalexperts.cart.Product;

public class ShoppingCartStepDefinitionsBase {

	Cart cart = null; 
	Product product = null;
	Product anotherProduct = null;
	BigDecimal salesTaxPercentage = new BigDecimal(12.5, MathContext.DECIMAL64);
}
