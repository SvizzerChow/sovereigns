package ink.chow.web.sovereigns.mapper;

import ink.chow.web.sovereigns.entity.LoginInfo;
import org.springframework.stereotype.Repository;

@Repository
public interface LoginInfoMapper {
    int deleteByPrimaryKey(Long id);

    int insert(LoginInfo record);

    int insertSelective(LoginInfo record);

    LoginInfo selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(LoginInfo record);

    int updateByPrimaryKey(LoginInfo record);
}