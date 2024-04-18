package com.themoah.themoah.domain.customer.repository;

import com.themoah.themoah.domain.customer.entity.Customer;
import com.themoah.themoah.domain.customer.entity.CustomerId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, CustomerId> {

    List<Customer> findByIndustry_IndustCode(String industCode);

}
