package ink.chow.web.sovereigns.mapper;

import ink.chow.web.sovereigns.entity.PushMessageReceiver;
import org.springframework.stereotype.Repository;

@Repository
public interface PushMessageReceiverMapper {
    int deleteByPrimaryKey(String id);

    int insert(PushMessageReceiver record);

    int insertSelective(PushMessageReceiver record);

    PushMessageReceiver selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(PushMessageReceiver record);

    int updateByPrimaryKey(PushMessageReceiver record);
}