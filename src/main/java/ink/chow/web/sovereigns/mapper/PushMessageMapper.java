package ink.chow.web.sovereigns.mapper;

import ink.chow.web.sovereigns.entity.PushMessage;
import org.springframework.stereotype.Repository;

@Repository
public interface PushMessageMapper {
    int deleteByPrimaryKey(String id);

    int insert(PushMessage record);

    int insertSelective(PushMessage record);

    PushMessage selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(PushMessage record);

    int updateByPrimaryKeyWithBLOBs(PushMessage record);

    int updateByPrimaryKey(PushMessage record);
}