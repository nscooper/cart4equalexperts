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

public class AddAdditionalProductsOfTheSameTypeToTheShoppingCart extends ShoppingCartStepDefinitionsBase {

	@Given("An empty cart")
	public void an_empty_shopping_cart() {
		cart = new Cart();
		assertTrue(cart.getProductsFromCart().size()==0);
	}
	
	@Given("a product, Dove Soap with unit price of {double}")
	public void a_product_Dove_Soap_with_a_unit_price_of(Double price) {
		product = new Product("Dove Soap", new BigDecimal(price, MathContext.DECIMAL64));
		assertThat(product.getName()).containsOnlyOnce("Dove Soap");
		assertTrue(product.getPrice().equals(new BigDecimal(price, MathContext.DECIMAL64)));
	}
	
	@When("The user adds {int} Dove Soaps to the shopping cart")
	public void the_user_adds_Dove_Soaps_to_the_shopping_cart(Integer numberOfProducts) {
		cart.addProduct(product, numberOfProducts);
		assertTrue(cart.howManyItemsAreInTheCart()==numberOfProducts.intValue());
	}

	@When("then adds another {int} Dove Soaps to the shopping cart")
	public void then_adds_another_Dove_Soaps_to_the_shopping_cart(Integer numberOfProducts) {
		int originalCountOfProducts = cart.howManyItemsAreInTheCart();
		cart.addProduct(product, numberOfProducts);
		assertTrue(cart.howManyItemsAreInTheCart()==(numberOfProducts.intValue()+originalCountOfProducts));
	}

	@Then("The cart should contain {int} Dove Soaps each with a unit price of {double}")
	public void the_shopping_cart_should_contain_Dove_Soaps_each_with_a_unit_price_of(Integer int1, Double price) {
		assertTrue(cart.getProductsFromCart()
		.stream()
		.filter(f->f.getName().equals("Dove Soap"))
		.filter(f->f.getPrice().equals(new BigDecimal(price, MathContext.DECIMAL64)))
		.count()==int1.intValue());
	}
	
	@Then("the cart’s total price should equal {double}")
	public void the_cart_s_total_price_should_equal(Double price) {
		assertTrue(cart.getCartNettPrice().toString().equals(price.toString()));
	}

}
