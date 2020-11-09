package com.shivank.atm;

import com.shivank.atm.controller.AccountController;
import com.shivank.atm.dao.Account;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@SpringBootTest
public class ApplicationTests {

	@Autowired
	AccountController accountController;

	@Test
	public void contextLoads() {
		System.out.println(accountController);
	}

	@Test
	public void createNewAccountandcheckbalance(){
		Account newAccRes = accountController.newAcc("Shivank","PAN123");
		assertThat(newAccRes.getAccountNumber()).isNotBlank();
		ResponseEntity<Account> checkBanalce = accountController.checkBal(newAccRes.getAccountNumber());
		assertThat(checkBanalce.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(checkBanalce.getBody().getBalance()).isEqualTo(0f);
	}

	@Test
	public void createNewAccountanDepositTest(){
		Account newAccRes = accountController.newAcc("Pooja","9976");
		assertThat(newAccRes.getAccountNumber()).isNotBlank();
		ResponseEntity<Account> checkBanalce = accountController.deposit(newAccRes.getAccountNumber(), "500");
		assertThat(checkBanalce.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(checkBanalce.getBody().getBalance()).isEqualTo(500f);
	}

	@Test
	public void WithdrawTest(){
		/*Account newAccRes = accountController.newAcc("Pooja","9976");
		assertThat(newAccRes.getAccountNumber()).isNotBlank();*/
		ResponseEntity<Account> checkBanalce = accountController.withdraw("1", "20");
		assertThat(checkBanalce.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(checkBanalce.getBody().getBalance()).isEqualTo(80f);
	}

	@Test
	public void InsufficentBalanceTest(){
		ResponseEntity<Account> checkBanalce = accountController.withdraw("1", "200");
		assertThat(checkBanalce.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
	}

	@Test
	public void InvalidAccNumberTest(){
		ResponseEntity<Account> checkBanalce = accountController.withdraw("56ft54", "200");
		assertThat(checkBanalce.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
	}
}
