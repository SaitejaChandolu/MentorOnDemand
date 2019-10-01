package com.mvsk.mod.reprository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.mvsk.mod.entity.PaymentCommission;

public interface PaymentCommissionRepository extends MongoRepository<PaymentCommission, Long> {

}
