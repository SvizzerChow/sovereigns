package ink.chow.web.sovereigns.domain.dto.user;

import ink.chow.web.sovereigns.entity.User;

/**
 * Description: 用户信息返回
 *
 * @author ZhouS
 * @date 2018/11/29 14:34
 */
public class DtoUser {

    private User user;

    public DtoUser(User user) {
        this.user = user;
    }

    public String getUserId(){
        return user.getUserId();
    }

    public String getUserName(){
        return user.getName();
    }

    public String getPhone(){
        return user.getPhone();
    }

}
