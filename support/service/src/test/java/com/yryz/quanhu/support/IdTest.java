package com.yryz.quanhu.support;

import com.alibaba.dubbo.config.annotation.Reference;
import com.yryz.common.response.Response;
import com.yryz.quanhu.support.id.api.IdAPI;
import com.yryz.quanhu.support.id.snowflake.utils.DateUtils;
import org.joda.time.DateTime;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * @author zhangkun
 * @version 1.0
 * @date 2018/1/18
 * @description
 */

@RunWith(SpringRunner.class)
@SpringBootTest
public class IdTest {
    @Reference
    IdAPI idAPI;


    @Test
    public void getIdTest() {
        Response<String> orderId = idAPI.getOrderId();
        System.out.println("orderId: " + orderId);
//
        Response<Long> quanhuUser = idAPI.getKid("quanhu_id_adjust");
        System.out.println("quanhuUser " + quanhuUser.getData());
        for (int i = 0; i < 1000; i++) {
            Response<Long> snowflakeId = idAPI.getSnowflakeId();
            System.out.println("get snowflakeId " + snowflakeId.getData());
        }


        long timestampBits = 29;
        long workerIdBits = 10;
        long sequenceBits = 13;

        // initialize max value
        long maxDeltaSeconds = ~(-1L << timestampBits);
        long maxWorkerId = ~(-1L << workerIdBits);
        long maxSequence = ~(-1L << sequenceBits);
        System.out.println("====maxWorkerId" + maxWorkerId + " maxSequence" + maxSequence);

        // initialize shift
        long timestampShift = workerIdBits + sequenceBits;
        long workerIdShift = sequenceBits;

        long epochSeconds = TimeUnit.MILLISECONDS.toSeconds(DateUtils.parseByDayPattern("2016-09-20").getTime());

        long jsMax = 4503599627370495L;

//        long currentSecond = TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis());
        DateTime now = DateTime.now();
        int i = 1;
        int sum = 0;
        while (true) {
            i++;
            Date newDate = now.plusMinutes(i).toDate();
            long currentSecond = TimeUnit.MILLISECONDS.toSeconds(newDate.getTime());

            long deltaSeconds = currentSecond - epochSeconds;
            if (deltaSeconds > maxDeltaSeconds) {
                System.out.println("11111dead time " + DateUtils.formatByDateTimePattern(newDate));
                break;
//            throw new UidGenerateException("Timestamp bits is exhausted. Refusing UID generate. Now: " + currentSecond);
            }
            long num = ((deltaSeconds << timestampShift) | (24 << workerIdShift) | 25);
//            System.out.println("num====" + num);

            if (num > jsMax || num < 0) {
                System.out.println("num too long");
                System.out.println("22222dead time " + DateUtils.formatByDateTimePattern(newDate));
                break;
            }
            sum++;
        }


    }

    @Test
    public void getSnowflakeIdTest() {
        for (int i = 1; i <= 1000000000; i++) {
            Response<Long> snowflakeId = idAPI.getSnowflakeId();
            System.out.println("get id" + snowflakeId.getData());
        }

    }

}
