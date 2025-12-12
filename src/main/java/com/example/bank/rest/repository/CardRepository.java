package com.example.bank.rest.repository;

import com.example.bank.rest.entity.BankCards;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface CardRepository extends JpaRepository<BankCards, Integer>, PagingAndSortingRepository<BankCards, Integer> {
}
