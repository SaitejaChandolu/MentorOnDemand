package com.mvsk.mod.proxy;

import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.mvsk.mod.model.SkillDtls;

@FeignClient(value = "technology-service", fallback = SkillServiceFallback.class)
@RibbonClient(value = "technology-service")
public interface SkillServiceProxy {

	@GetMapping("/skills/findById/{id}")
	public SkillDtls findById(
			@PathVariable(value = "id", required = true) Long id);

}

@Component
class SkillServiceFallback implements SkillServiceProxy {

	@Override
	public SkillDtls findById(Long skillId) {
		return new SkillDtls();
	}

}
