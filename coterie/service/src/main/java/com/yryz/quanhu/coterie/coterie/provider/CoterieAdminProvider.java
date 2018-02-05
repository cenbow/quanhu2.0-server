package com.yryz.quanhu.coterie.coterie.provider;

import com.alibaba.dubbo.config.annotation.Service;
import com.yryz.quanhu.coterie.coterie.service.CoterieAdminAPI;

@Service(interfaceClass=CoterieAdminAPI.class)
public class CoterieAdminProvider implements CoterieAdminAPI {
	
}
