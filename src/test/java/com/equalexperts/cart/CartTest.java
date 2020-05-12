package com.equalexperts.cart;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class CartTest {

	private List<Product> products = null;
	private Cart cart = null;

	private String prodName1 = "TEST-product-name1";
	private BigDecimal price1 = new BigDecimal(23.45, MathContext.DECIMAL64);
	private String prodName2 = "TEST-product-name2";
	private BigDecimal price2 = new BigDecimal(33.45, MathContext.DECIMAL64);
	private String prodName3 = "TEST-product-name3";
	private BigDecimal price3 = new BigDecimal(43.45, MathContext.DECIMAL64);
	private BigDecimal salesTax = new BigDecimal(12.5, MathContext.DECIMAL64);

	@Before
	public void setup() {
		cart = new Cart();
		cart.setSalesTax(new BigDecimal(12.5d));
		products = new ArrayList<>(Arrays.asList(new Product(prodName1, price1), new Product(prodName2, price2),
				new Product(prodName3, price3)));

		products.forEach(m -> cart.addProduct(m, 1));
	}

	@Test
	public void testGetCartTotalPrice() {
		BigDecimal totalProductPrices = products.stream()
				.map(p -> p.getPrice().add(p.getPrice().multiply(cart.getSalesTax().divide(new BigDecimal(100, MathContext.DECIMAL64)))).setScale(2, RoundingMode.HALF_UP))
				.reduce(BigDecimal.ZERO, BigDecimal::add);

		assertThat(cart.getCartGrossPrice())
				.isEqualByComparingTo((totalProductPrices));
	}

	@Test
	public void testHowManyProductsAreInTheCart() {
		assertTrue(cart.howManyItemsAreInTheCart() == products.size());
	}

	@Test
	public void testGetProductsFromCart() {
		int numberOfProducts = cart.howManyItemsAreInTheCart();
		List<Product> cartProducts = cart.getProductsFromCart();
		assertTrue(numberOfProducts == cartProducts.size());
		assertThat(cartProducts).containsAll(Arrays.asList(new Product(prodName1, price1),
				new Product(prodName2, price2), new Product(prodName3, price3)));
	}

	@Test
	public void testGetCartNettPrice() {
		BigDecimal manualSales = price1.add(price2).add(price3);
		assertThat(cart.getCartNettPrice()).isEqualByComparingTo(manualSales);
	}
	
	@Test
	public void testGetCartTaxAmount() {
		BigDecimal manualSalesTax = price1.add(price2).add(price3).multiply(salesTax.divide(new BigDecimal(100, MathContext.DECIMAL64)));
		assertThat(cart.getCartTaxAmount()).isEqualByComparingTo(manualSalesTax.setScale(2, RoundingMode.HALF_UP));
	}
	
	@Test
	public void testGetCartGrossPrice() {
		BigDecimal totalSalesTax = cart.getCartGrossPrice();
		BigDecimal manualSales = price1.add(price2).add(price3);
		BigDecimal manualSalesTax = manualSales.multiply(salesTax.divide(new BigDecimal(100, MathContext.DECIMAL64))).add(manualSales);
		assertThat(totalSalesTax).isEqualByComparingTo(manualSalesTax.setScale(2, RoundingMode.HALF_UP));
	}

}
