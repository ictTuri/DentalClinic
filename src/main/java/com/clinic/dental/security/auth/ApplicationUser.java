package com.clinic.dental.security.auth;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.clinic.dental.model.user.UserEntity;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Data
public class ApplicationUser implements UserDetails {
	private static final long serialVersionUID = 1L;
	
	private final String username;
    private final String password;
	private List<UserAuthority> authorities;
    private final boolean isAccountNonExpired;
    private final boolean isAccountNonLocked;
    private final boolean isCredentialsNonExpired;
    private final boolean isEnabled;
	
	public ApplicationUser(UserEntity userEntity) {
		this.username = userEntity.getUsername();
		this.password = userEntity.getPassword();
		authorities = new ArrayList<>();
		authorities.add(new UserAuthority(userEntity.getRole().name()));
		this.isAccountNonExpired = true;
		this.isAccountNonLocked = true;
		this.isCredentialsNonExpired = true;
		this.isEnabled = userEntity.isActive();
	}
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		 return this.authorities;
	}

	@Override
	public String getPassword() {
		return this.password;
	}

	@Override
	public String getUsername() {
		return this.username;
	}

	@Override
	public boolean isAccountNonExpired() {
		return this.isAccountNonExpired;
	}

	@Override
	public boolean isAccountNonLocked() {
		return this.isAccountNonLocked;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return this.isCredentialsNonExpired;
	}

	@Override
	public boolean isEnabled() {
		return this.isEnabled;
	}

}
