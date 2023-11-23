package uz.pdp.govqueue.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import uz.pdp.govqueue.model.Operator;

import java.util.Collection;
import java.util.List;

@RequiredArgsConstructor
public class MyUserDetails implements UserDetails {

    private final Operator operator;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return operator.isStatus();
    }

    @Override
    public boolean isAccountNonLocked() {
        return operator.isStatus();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return operator.isStatus();
    }

    @Override
    public boolean isEnabled() {
        return operator.isStatus();
    }

    public Operator getOperator() {
        return operator;
    }

    public Integer getOperatorId() {
        return operator.getId();
    }
}
