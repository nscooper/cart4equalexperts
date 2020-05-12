package com.equalexperts.cart;

import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;

import org.junit.Before;
import org.junit.Test;

public class ProductTest {
	
	private Product product=null;
	private String prodName = "TEST-product-name";
	private BigDecimal price = new BigDecimal(23.45);
	
	@Before
	public void setup() {
		product = new Product(prodName,price);
	}
	
	@Test
	public void checkProductName() {
		assertTrue(product.getName().equals(prodName));
	}
	
	@Test
	public void checkProductPrice() {
		assertTrue(product.getPrice().equals(price));
	}

}
