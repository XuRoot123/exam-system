package cn.com.xuroot.service.impl;

import cn.com.xuroot.entity.Test;
import cn.com.xuroot.service.TestService;
import org.springframework.stereotype.Service;

@Service
public class TestServiceImpl implements TestService {

    @Override
    public Test testButton(String test) {
        Test test1 = new Test();
        if (test.length()>=500 || test.isEmpty()) {
            return null;
        }
        test1.setText(test);
        test1.setType("test");
        return test1;
    }
}
