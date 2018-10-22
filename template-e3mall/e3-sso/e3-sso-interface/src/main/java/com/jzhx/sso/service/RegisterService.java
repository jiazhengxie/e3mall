package com.jzhx.sso.service;

import com.jzhx.common.utils.E3Result;
import com.jzhx.pojo.TbUser;

public interface RegisterService {

	E3Result checkData(String param, int type);
	E3Result register(TbUser user);
}
