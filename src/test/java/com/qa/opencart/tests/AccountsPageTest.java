package com.qa.opencart.tests;

import java.util.Arrays;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.qa.opencart.base.BaseTest;

public class AccountsPageTest extends BaseTest {

	@BeforeClass
	public void accPageSetup() {
		accPage = loginPage.doLogin(prop.getProperty("username"), prop.getProperty("password"));
	}

	@Test
	public void accPageTitleTest() {
		String actTitle = accPage.getAccPageTitle();
		Assert.assertEquals(actTitle, "My Account");
	}

	@Test
	public void isLogoutLinkExistTest() {
		Assert.assertTrue(accPage.isLogoutLinkExist());
	}

	@Test
	public void isMyAccLinkExistTest() {
		Assert.assertTrue(accPage.isMyAccountLinkExist());
	}

	@Test
	public void accPageHeadersCountTest() {
		List<String> actAccHeadersList = accPage.getAccountPageHeadersList();
		Assert.assertEquals(actAccHeadersList.size(), 4);
	}

	@Test
	public void accPageHeadersTest() {
		List<String> actAccHeadersList = accPage.getAccountPageHeadersList();
		List<String> expAccHeadersList = Arrays.asList("My Account", "My Orders", "My Affiliate Account", "Newsletter");
		Assert.assertEquals(actAccHeadersList, expAccHeadersList);
	}

}