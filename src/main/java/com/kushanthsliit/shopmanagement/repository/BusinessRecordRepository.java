package com.kushanthsliit.shopmanagement.repository;

import com.kushanthsliit.shopmanagement.model.BusinessRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BusinessRecordRepository extends JpaRepository<BusinessRecord, Long> {
}
