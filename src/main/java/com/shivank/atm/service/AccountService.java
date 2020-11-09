package com.shivank.atm.service;
 
//... import statements

import com.shivank.atm.dao.Account;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class AccountService {

@PersistenceContext
protected EntityManager entityManager;
 
public AccountService(EntityManager entityManager) {
                this.entityManager = entityManager;
}
 
public Account createAccount(String name, String pan) {
                Account a = new Account();
                a.setAccountNumber(UUID.randomUUID().toString().replaceAll("-",""));
                a.setName(name);
                a.setCreateDate(new Date());
                a.setBalance(0F);
                a.setPan(pan);
                entityManager.persist(a);
                return a;
        }
 
       /* public void closeAccount(Long accno) {
                Account a = findAccount(accno);
                if (a != null) {
                        entityManager.remove(a);
                }
        }*/

    public ResponseEntity deposit(String accno, Float amount) {
        Account a = entityManager.find(Account.class, accno);
        if (a != null) {
            entityManager.lock(a, LockModeType.OPTIMISTIC);
            a.setBalance(a.getBalance() + amount);
            return new ResponseEntity(a, null, HttpStatus.OK);
        }
        else{
            return new ResponseEntity("Invalid Account Number", null, HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity withdraw(String accno, Float amount) {
        Account a = entityManager.find(Account.class, accno);
        if (a != null) {
            entityManager.lock(a, LockModeType.OPTIMISTIC);
            if(a.getBalance() >= amount){
                a.setBalance(a.getBalance() - amount);
                return new ResponseEntity(a, null, HttpStatus.OK);
            }
            else
                return new ResponseEntity("Insufficent Balace", null, HttpStatus.BAD_REQUEST);
        }
        else{
            return new ResponseEntity("Invalid Account Number", null, HttpStatus.NOT_FOUND);
        }

    }
 
        public ResponseEntity findAccount(String accno) {
            Account a = entityManager.find(Account.class, accno);
            if (a != null) {
                entityManager.lock(a, LockModeType.OPTIMISTIC);
                return new ResponseEntity(a, null, HttpStatus.OK);
            }
            else{
                return new ResponseEntity("Invalid Account Number", null, HttpStatus.NOT_FOUND);
            }
        }
 
        public List<Account> listAllAccount() {
                TypedQuery<Account> query = entityManager.createNamedQuery(
                               "findAllAccount", Account.class);
                return query.getResultList();
        }

    public AccountService() {
    }
}
 
