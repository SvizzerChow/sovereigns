package ink.chow.web.sovereigns.mapper;

import org.springframework.stereotype.Repository;

import ink.chow.web.sovereigns.entity.User;

@Repository
public interface UserMapper {
    int deleteByPrimaryKey(String userId);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(String userId);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);
}