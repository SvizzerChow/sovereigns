package ink.chow.web.sovereigns.service.impl;


import ink.chow.web.sovereigns.common.ServerPropertiesEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ink.chow.web.sovereigns.config.blog.GitAccount;
import ink.chow.web.sovereigns.entity.ServerProperties;
import ink.chow.web.sovereigns.mapper.ex.ServerPropertiesMapperEx;
import ink.chow.web.sovereigns.service.GitAccountService;

@Service
public class GitHubAccountServiceImpl implements GitAccountService {
    private String NAME_KEY = "git_name";
    private String PASSWORD_KEY = "git_password";
    @Autowired
    private ServerPropertiesMapperEx serverPropertiesMapperEx;
    private Logger logger = LoggerFactory.getLogger(GitHubAccountServiceImpl.class);

    @Override
    public GitAccount getAccount(int type) {
        ServerProperties name = serverPropertiesMapperEx.selectByKey(NAME_KEY, type);
        ServerProperties password = serverPropertiesMapperEx.selectByKey(PASSWORD_KEY, type);
        logger.info("name: {}, pas:{}", name, password);
        if (name == null || password == null){
            return null;
        }
        GitAccount account = new GitAccount();
        account.setUsername(name.getPropertyValue());
        account.setPassword(password.getPropertyValue());
        return account;
    }
}
