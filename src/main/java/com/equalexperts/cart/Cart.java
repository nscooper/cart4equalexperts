package com.equalexperts.cart;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public final class Cart {
	private static final BigDecimal ONE_HUNDRED = new BigDecimal(100);
	private final List<Product> basket = new ArrayList<>();
	private BigDecimal runningTotal = new BigDecimal(0);
	private Optional<BigDecimal> salesTax = Optional.empty();
	
	public void addProduct(final Product product, int quantity ) {
		while(quantity-->0) {
			basket.add(product);
			runningTotal = runningTotal.add(product.getPrice()).setScale(2, RoundingMode.HALF_UP);
		}
	}
	
	/**
	 * Nett here means before sales tax is added.
	 * All totals should be rounded up to 2 decimal places
	 * @return
	 */
	public BigDecimal getCartNettPrice() {
		return runningTotal.setScale(2, RoundingMode.HALF_UP);
	}
	
	/**
	 * All totals should be rounded up to 2 decimal places
	 * @return
	 */
	public BigDecimal getCartTaxAmount() {
		return 
				runningTotal.multiply(getSalesTax().divide(ONE_HUNDRED))
			.setScale(2,RoundingMode.HALF_UP);
	}
	
	/**
	 * All totals should be rounded up to 2 decimal places
	 * @return
	 */
	public BigDecimal getCartGrossPrice() {
		return runningTotal.add(
				getCartTaxAmount()).setScale(2,RoundingMode.HALF_UP);
	}

	public int howManyItemsAreInTheCart() {
		return basket.size();
	}
	
	public List<Product> getProductsFromCart() {
		List<Product> copy = new ArrayList<>(basket.size());
		copy.addAll(basket);
		return copy;
	}

	public BigDecimal getSalesTax() {
		return salesTax.orElse(ONE_HUNDRED);
	}

	public void setSalesTax(BigDecimal salesTax) {
		this.salesTax = Optional.of(salesTax);
		
	}
}
