import com.alibaba.dubbo.config.annotation.Reference;
import com.yryz.quanhu.support.ApplicationSupportService;
import com.yryz.quanhu.support.id.api.IdAPI;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

/**
 * @author zhangkun
 * @version 1.0
 * @date 2018/1/18
 * @description
 */

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = ApplicationSupportService.class)
@ImportResource
@WebAppConfiguration
public class IdTest {
    @Reference
    IdAPI idAPI;

    @Test
    public void getIdTest() {
        String quanhuUser = idAPI.getId("quanhu_user");
    }
}
