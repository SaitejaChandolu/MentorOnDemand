package com.mvsk.mod.reprository;

import com.mvsk.mod.model.PaymentDtls;

public interface PaymentRepositoryCustom {

    PaymentDtls aggregateByMentorId(Long mentorId, Long trainingId);
    
}
