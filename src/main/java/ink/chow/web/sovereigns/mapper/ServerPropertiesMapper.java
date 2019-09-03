package ink.chow.web.sovereigns.mapper;

import ink.chow.web.sovereigns.entity.ServerProperties;
import org.springframework.stereotype.Repository;

@Repository
public interface ServerPropertiesMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ServerProperties record);

    int insertSelective(ServerProperties record);

    ServerProperties selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ServerProperties record);

    int updateByPrimaryKey(ServerProperties record);
}