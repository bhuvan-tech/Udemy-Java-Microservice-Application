package com.dailycodebuffer.PaymentService.repository;

import com.dailycodebuffer.PaymentService.entity.TransactionDetails;
import jakarta.persistence.Entity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionDetailsRepository extends JpaRepository<TransactionDetails, Long> {
}
