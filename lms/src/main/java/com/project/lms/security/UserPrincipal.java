package com.project.lms.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.project.lms.entity.RoleEntity;
import com.project.lms.entity.UserEntity;

import lombok.Data;

@Data
public class UserPrincipal implements UserDetails{
	private static final long serialVersionUID = 1L;
	private static final Logger logger = LogManager.getLogger(UserPrincipal.class);
	private String id;

	private String firstName;

	private String lastName;

	private String username;
	
	private boolean enabled;

	@JsonIgnore
	private String password;

	private List<UserAuthority> authorities;

	public static UserPrincipal build(UserEntity user, List<RoleEntity> roles) {
		UserPrincipal userPrincipal = new UserPrincipal();
		List<UserAuthority> grantedAuthorities = new ArrayList<>();
		for(RoleEntity re : roles) {
			grantedAuthorities.add(new UserAuthority(re.getName()));
		}
		userPrincipal.setAuthorities(grantedAuthorities);
		userPrincipal.setId(user.getId().toString());
		logger.info("built method {}",user.getId());
		userPrincipal.setFirstName(user.getFirstName());
		userPrincipal.setUsername(user.getUsername());
		userPrincipal.setLastName(user.getLastName());
		userPrincipal.setPassword(user.getPassword());
		userPrincipal.setEnabled(user.isActivated());
		return userPrincipal;
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return enabled;
	}

	private UserPrincipal(String id, String username, List<UserAuthority> authorities) {
		this.id = id;
		this.username = username;
		this.authorities = authorities;

	}

	public UserPrincipal() {
		
	}
	
	public static class Builder {

		private String id;
		private String username;
		private List<UserAuthority> authorities;

		public Builder withId(String id) {
			this.id = id;
			return this;
		}

		public Builder withUsername(String username) {
			this.username = username;
			return this;
		}

		public Builder withAuthorities(List<UserAuthority> authorities) {
			this.authorities = authorities == null ? Collections.emptyList() : authorities;
			return this;
		}

		public UserPrincipal build() {
			return new UserPrincipal(id, username, authorities);
		}
	}
}
