package com.equalexperts.cart;

 
import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;

@RunWith(Cucumber.class)
@CucumberOptions(features="src/test/resources", glue="com.equalexperts.cart.stepdefs")
public class TestRunner_CartTest {

}
