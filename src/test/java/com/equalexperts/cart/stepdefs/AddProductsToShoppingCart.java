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

public class AddProductsToShoppingCart extends ShoppingCartStepDefinitionsBase {

	@Given("An empty shopping cart")
	public void an_empty_shopping_cart() {
		cart = new Cart();
		assertTrue(cart.howManyItemsAreInTheCart()==0);
	}

	@Given("a product, Dove Soap with a unit price of {double}")
	public void a_product_Dove_Soap_with_a_unit_price_of(Double price) {
		product = new Product("Dove Soap", new BigDecimal(price, MathContext.DECIMAL64));
		assertThat(product.getName()).containsOnlyOnce("Dove Soap");
		assertTrue(product.getPrice().equals(new BigDecimal(price, MathContext.DECIMAL64)));

	}

	@When("a user adds {int} Dove Soaps to the shopping cart")
	public void a_user_adds_Dove_Soaps_to_the_shopping_cart(Integer int1) {
		cart.addProduct(product, int1);
		assertTrue(cart.howManyItemsAreInTheCart()==int1.intValue());
	}

	@Then("The shopping cart should contain {int} Dove Soaps each with a unit price of {double}")
	public void the_shopping_cart_should_contain_Dove_Soaps_each_with_a_unit_price_of(Integer int1, Double price) {
		assertTrue(cart.getProductsFromCart()
		.stream()
		.filter(f->f.getName().equals("Dove Soap"))
		.filter(f->f.getPrice().equals(new BigDecimal(price, MathContext.DECIMAL64)))
		.count()==int1.intValue());

	}

	@Then("the shopping cart's total price should equal {double}")
	public void the_shopping_cart_s_total_price_should_equal(Double price) {
		assertThat(cart.getCartNettPrice()).isEqualByComparingTo(new BigDecimal(price, MathContext.DECIMAL64));
	}


}
