package com.yryz.quanhu.user;

import org.junit.Test;

import com.yryz.quanhu.user.utils.TokenUtils;

/**
* @Description: 用户token
* @author wangheng
* @date 2018年1月30日 下午2:25:33
*/
public class TokenUtilsTest {

    @Test
    public void test001() {
        try {
            System.out.println(
                    TokenUtils.getDesToken("738422165156233200", "738422165156233216-OuW3nAkD2GSr1517293287713"));
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}

