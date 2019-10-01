package com.mvsk;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.mvsk.mod.exception.ServiceUnavailableException;
import com.mvsk.mod.model.UserDtls;
import com.mvsk.mod.proxy.UserServiceProxy;
import com.mvsk.mod.util.Translator;

@RestController
public class HelloClientApplication {

	@Autowired
	private UserServiceProxy userProxy;

	@GetMapping("/findByName/{userName}")
	public UserDtls findByName(
			@PathVariable(value = "userName", required = true) String userName) {
		UserDtls userDtlsObj = userProxy.findByName(userName);
		if (userDtlsObj == null)
			throw new ServiceUnavailableException(Translator.toLocale("error.service.unavailable", "user-service"));
		else
			return userDtlsObj;
	}
}
