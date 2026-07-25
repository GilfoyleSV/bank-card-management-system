package com.bank.cardmanagement.repository;

import com.bank.cardmanagement.model.Card;
import com.bank.cardmanagement.model.CardStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CardRepository extends JpaRepository<Card, Long> {

    Page<Card> findByOwnerId(Long ownerId, Pageable pageable);

    Page<Card> findByOwnerIdAndStatus(Long ownerId, CardStatus status, Pageable pageable);

    Page<Card> findByStatus(CardStatus status, Pageable pageable);

    Optional<Card> findByIdAndOwnerId(Long id, Long ownerId);

    boolean existsByCardNumber(String cardNumber);
}
