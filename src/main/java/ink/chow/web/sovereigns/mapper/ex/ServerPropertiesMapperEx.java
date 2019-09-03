package ink.chow.web.sovereigns.mapper.ex;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import ink.chow.web.sovereigns.entity.ServerProperties;

@Repository
public interface ServerPropertiesMapperEx {
    ServerProperties selectByKey(@Param("propertyKey") String propertyKey, @Param("type") int type);
}
