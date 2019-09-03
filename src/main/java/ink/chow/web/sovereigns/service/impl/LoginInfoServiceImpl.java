package ink.chow.web.sovereigns.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

import ink.chow.web.sovereigns.entity.LoginInfo;
import ink.chow.web.sovereigns.mapper.LoginInfoMapper;
import ink.chow.web.sovereigns.service.ILoginInfoService;

@Service
public class LoginInfoServiceImpl implements ILoginInfoService {
    @Autowired
    private LoginInfoMapper loginInfoMapper;

    @Override
    @Transactional
    public void addLoginInfo(LoginInfo loginInfo) {
        loginInfo.setDate(new Date());
        loginInfoMapper.insert(loginInfo);
    }
}
