package ink.chow.web.sovereigns.mapper;

import ink.chow.web.sovereigns.entity.PushLog;
import org.springframework.stereotype.Repository;

@Repository
public interface PushLogMapper {
    int deleteByPrimaryKey(String id);

    int insert(PushLog record);

    int insertSelective(PushLog record);

    PushLog selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(PushLog record);

    int updateByPrimaryKey(PushLog record);
}