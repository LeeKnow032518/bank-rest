package com.example.bank.rest.repository;

import com.example.bank.rest.entity.BankCards;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CardRepository extends JpaRepository<BankCards, Integer>, PagingAndSortingRepository<BankCards, Integer> {

    @Query("SELECT c FROM BankCards c JOIN FETCH c.user WHERE c.user.id= :id")
    List<BankCards> findByUserId_Id(@Param("id") Integer id);

    Optional<BankCards> findByEncryptedCardNumber(String number);
}
