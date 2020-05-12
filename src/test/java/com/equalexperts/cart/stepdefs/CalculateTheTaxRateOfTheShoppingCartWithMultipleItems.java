package com.equalexperts.cart.stepdefs;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.math.MathContext;

import com.equalexperts.cart.Cart;
import com.equalexperts.cart.Product;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class CalculateTheTaxRateOfTheShoppingCartWithMultipleItems extends ShoppingCartStepDefinitionsBase {

	@Given("A cart that's empty")
	public void an_empty_shopping_cart() {
		cart = new Cart();
		assertTrue(cart.howManyItemsAreInTheCart() == 0);
	}

	@Given("a product called Dove Soap with a unit price of {double}")
	public void a_product_Dove_Soap_with_a_unit_price_of(Double price) {
		product = new Product("Dove Soap", new BigDecimal(price, MathContext.DECIMAL64));
		assertThat(product.getName()).containsOnlyOnce("Dove Soap");
		assertTrue(product.getPrice().equals(new BigDecimal(price, MathContext.DECIMAL64)));
	}

	@Given("another product, Axe Deo with a unit price of {double}")
	public void another_product_Axe_Deo_with_a_unit_price_of(Double price) {
		anotherProduct = new Product("Axe Deo", new BigDecimal(price, MathContext.DECIMAL64));
		assertThat(anotherProduct.getName()).containsOnlyOnce("Axe Deo");
		assertTrue(anotherProduct.getPrice().equals(new BigDecimal(price, MathContext.DECIMAL64)));
	}

	@Given("a sales tax rate of {double}%")
	public void A_sales_tax_rate_of(Double taxRate) {
		cart.setSalesTax(BigDecimal.valueOf(taxRate.doubleValue()));
		assertThat(cart.getSalesTax()).isNotNull();
	}


	@When("the user adds {int} Dove Soaps to their cart")
	public void a_user_adds_Dove_Soaps_to_the_shopping_cart(Integer int1) {
		cart.addProduct(product, int1);
		assertTrue(cart.howManyItemsAreInTheCart() == int1.intValue());
	}
	
	@When("then adds {int} Axe Deos to the shopping cart") 
	public void then_adds_Axe_Deos_to_the_shopping_cart(Integer numberOfProducts) {
		int originalCountOfProducts = cart.howManyItemsAreInTheCart();
		cart.addProduct(anotherProduct, numberOfProducts);
		assertTrue(cart.howManyItemsAreInTheCart()==(numberOfProducts.intValue()+originalCountOfProducts));
	}
	
	@Then("the shopping cart should contain {int} Dove Soaps each with a unit price of {double}")
	public void the_shopping_cart_should_contain_Dove_Soaps_each_with_a_unit_price_of(Integer int1, Double price) {
		assertTrue(cart.getProductsFromCart()
		.stream()
		.filter(f->f.getName().equals("Dove Soap"))
		.filter(f->f.getPrice().equals(new BigDecimal(price, MathContext.DECIMAL64)))
		.count()==int1.intValue());
	}

	@Then("the shopping cart should contain {int} Axe Deos each with a unit price of {double}")
	public void the_shopping_cart_should_contain_Axe_Deos_each_with_a_unit_price_of(Integer int1, Double price) {
		assertTrue(cart.getProductsFromCart()
		.stream()
		.filter(f->f.getName().equals("Axe Deo"))
		.filter(f->f.getPrice().equals(new BigDecimal(price, MathContext.DECIMAL64)))
		.count()==int1.intValue());
	}
	
	@Then("the total sales tax amount for the shopping cart should equal {double}")
	public void the_total_sales_tax_amount_for_the_shopping_cart_should_equal(Double totalSalesTax) {
	    assertThat(cart.getCartTaxAmount()).isEqualByComparingTo(new BigDecimal(totalSalesTax, MathContext.DECIMAL64));
	}

	@Then("the shopping cart’s total price should equal {double}")
	public void the_shopping_cart_s_total_price_should_equal(Double totalPrice) {
	    assertThat(cart.getCartGrossPrice()).isEqualByComparingTo(new BigDecimal(totalPrice, MathContext.DECIMAL64));
	}

}
