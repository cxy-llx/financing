package com.wulingqi.lightning.portal.controller;

import org.springframework.beans.factory.annotation.Autowired;

import com.wulingqi.lightning.portal.service.FinanceService;

//@Api(tags = "FinanceController", description = "财务管理")
//@RestController
//@RequestMapping("api/finance")
public class FinanceController {
	
	@Autowired
	private FinanceService financeService;
	

}
