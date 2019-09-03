package ink.chow.web.sovereigns.domain;

import com.alibaba.fastjson.annotation.JSONField;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

import ink.chow.web.sovereigns.entity.User;

/**
 * Description:
 *
 * @author ZhouS
 * @date 2018/11/28 17:23
 */
public class UserWithAuthorities implements UserDetails {
    private User user;
    private Collection<? extends GrantedAuthority> authorities;

    public UserWithAuthorities() {
    }

    public UserWithAuthorities(User user, Collection<? extends GrantedAuthority> authorities) {
        this.user = user;
        this.authorities = authorities;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public String getPassword() {
        return user==null?null:user.getPassword();
    }

    @Override
    public String getUsername() {
        return user==null?null:user.getName();
    }

    @JSONField(serialize = false)
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }
    @JSONField(serialize = false)
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }
    @JSONField(serialize = false)
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
    @JSONField(serialize = false)
    @Override
    public boolean isEnabled() {
        return true;
    }
}
