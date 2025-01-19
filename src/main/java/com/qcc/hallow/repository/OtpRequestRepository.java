package com.qcc.hallow.repository;

import com.qcc.hallow.entity.OTPRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OtpRequestRepository extends JpaRepository<OTPRequest, String> {
}
