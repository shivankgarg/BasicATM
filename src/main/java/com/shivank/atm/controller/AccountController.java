package com.shivank.atm.controller;

import com.shivank.atm.dao.Account;
import com.shivank.atm.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/atm")
public class AccountController {
    
    @Autowired
    private AccountService accountService;

    @GetMapping(value = "/withdraw/{accNum}/{amount}")
    public ResponseEntity withdraw(@PathVariable("accNum") String accNum,
                                           @PathVariable("amount") String amount ) {
        System.out.println("Request to withdraw from Acc "+accNum+" | Amount"+amount);
       return accountService.withdraw(accNum, Float.parseFloat(amount));
    }

    @GetMapping(value = "/deposit/{accNum}/{amount}")
    public ResponseEntity deposit(@PathVariable("accNum") String accNum, @PathVariable("amount") String amount ) {
        System.out.println("Request to Deposit from Acc "+accNum+" | Amount"+amount);

        return accountService.deposit(accNum, Float.parseFloat(amount));
    }

    @GetMapping(value = "/openAcc/{name}/{pan}")
    public Account newAcc(@PathVariable("name") String name, @PathVariable("pan") String pan ) {
        System.out.println("Request to open Acc- "+name+" | "+pan);
        return accountService.createAccount(name, pan);
    }

    @GetMapping(value = "/checkBal/{acc}")
    public ResponseEntity checkBal(@PathVariable("acc") String acc ) {
        System.out.println("Request to check Bal from Acc "+acc);
        return accountService.findAccount(acc);
    }
}
