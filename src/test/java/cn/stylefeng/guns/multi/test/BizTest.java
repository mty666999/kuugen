package com.kuugen.multi.test;

import com.kuugen.base.BaseJunit;
import com.kuugen.multi.service.TestService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 业务测试
 *
 * @author fengshuonan
 * @date 2017-06-23 23:12
 */
public class BizTest extends BaseJunit {

    @Autowired
    private TestService testService;

    @Test
    public void test() {
        testService.testGuns();

        testService.testBiz();
    }
}
