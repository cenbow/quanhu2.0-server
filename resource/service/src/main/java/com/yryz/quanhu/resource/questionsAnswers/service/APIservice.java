package com.yryz.quanhu.resource.questionsAnswers.service;

import com.yryz.quanhu.coterie.coterie.vo.CoterieInfo;
import com.yryz.quanhu.user.vo.UserSimpleVO;

public interface APIservice {

    public Long getKid();

    public UserSimpleVO getUser(Long kid);

    CoterieInfo getCoterieinfo(Long citeriaId);
}
