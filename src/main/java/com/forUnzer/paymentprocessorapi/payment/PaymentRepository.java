package com.forUnzer.paymentprocessorapi.payment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {


    @Query("SELECT p FROM Payment p WHERE p.approvalCode = ?1")
    Optional<Payment> findByApprovalCode(String approvalCode);


}
