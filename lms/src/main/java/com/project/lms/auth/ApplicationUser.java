package com.project.lms.auth;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.project.lms.entity.RoleEntity;
import com.project.lms.entity.UserEntity;
import com.project.lms.security.ApplicationUserRole;
import com.project.lms.security.UserAuthority;

public class ApplicationUser implements UserDetails{

	private static final long serialVersionUID = 1L;
	
	private final String username;
    private final String password;
    private List<UserAuthority> authorities;
    private final boolean isAccountNonExpired;
    private final boolean isAccountNonLocked;
    private final boolean isCredentialsNonExpired;
    private final boolean isEnabled;

    public ApplicationUser(UserEntity user, List<RoleEntity> roles) {
        this.username = user.getUsername();
        this.password = user.getPassword();
        authorities = new ArrayList<>();
        for(RoleEntity re : roles) {
        	Set<SimpleGrantedAuthority> permissions = ApplicationUserRole.valueOf(re.getName()).getGrantedAuthorities();	
        	for(SimpleGrantedAuthority sga : permissions) {
        		authorities.add(new UserAuthority(sga.toString()));
        	}
        }
        this.isAccountNonExpired = true;
        this.isAccountNonLocked = true;
        this.isCredentialsNonExpired = true;
        this.isEnabled = user.isActivated();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return isAccountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return isAccountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return isCredentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return isEnabled;
    }

}
