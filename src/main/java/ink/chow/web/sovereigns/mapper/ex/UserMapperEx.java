package ink.chow.web.sovereigns.mapper.ex;

import org.springframework.stereotype.Repository;

import ink.chow.web.sovereigns.entity.User;

@Repository
public interface UserMapperEx {

    User selectByUserName(String name);
}