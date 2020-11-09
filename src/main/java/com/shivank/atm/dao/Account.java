package com.shivank.atm.dao;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@NamedQuery(name="findAllAccount", query="SELECT a FROM Account a")
public class Account implements Serializable {
        @Id
        private String accountNumber;
        private String name;
        @Temporal(TemporalType.DATE)
        private Date createDate;
        private Float balance;
        private String pan;

        @Version
        private Integer version;

        public Account() {
        }

        public Account(String accountNumber, String name, Date createDate, Float balance, String pan, Integer version) {
                this.accountNumber = accountNumber;
                this.name = name;
                this.createDate = createDate;
                this.balance = balance;
                this.pan = pan;
                this.version = version;
        }

        public String getPan() {
                return pan;
        }

        public void setPan(String pan) {
                this.pan = pan;
        }

        public String getAccountNumber() {
                return accountNumber;
        }

        public void setAccountNumber(String accountNumber) {
                this.accountNumber = accountNumber;
        }

        public String getName() {
                return name;
        }

        public void setName(String name) {
                this.name = name;
        }

        public Date getCreateDate() {
                return createDate;
        }

        public void setCreateDate(Date createDate) {
                this.createDate = createDate;
        }

        public Float getBalance() {
                return balance;
        }

        public void setBalance(Float balance) {
                this.balance = balance;
        }
}