package com.wulingqi.lightning.portal.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wulingqi.lightning.portal.mapper.PortalMapper;
import com.wulingqi.lightning.portal.mapper.PortalMemberMapper;
import com.wulingqi.lightning.portal.service.CommonService;
import com.wulingqi.lightning.portal.service.FinanceService;
import com.wulingqi.lightning.portal.service.MemberService;

//@Service
public class FinanceServiceImpl implements FinanceService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(FinanceServiceImpl.class);
	
	@Autowired
	private CommonService commonService;
	
	@Autowired
	private MemberService memberService;
	
	@Autowired
	private PortalMemberMapper portalMemberMapper;
	
	@Autowired
	private PortalMapper portalMapper;

}
