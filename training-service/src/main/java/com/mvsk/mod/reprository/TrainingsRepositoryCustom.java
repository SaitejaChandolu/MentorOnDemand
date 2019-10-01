package com.mvsk.mod.reprository;

import com.mvsk.mod.model.TrainingDtls;

public interface TrainingsRepositoryCustom {
	
	TrainingDtls findAvgRating(Long mentorId, Long skillId);

}
