package com.clinic.dental.security.auth;

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
	private List<UserAuthority> authorities;
	private UserEntity user;
	
	public ApplicationUser(UserEntity userEntity) {
		this.user = userEntity;
	}
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		 authorities.add(new UserAuthority(user.getRole().name()));
		 return authorities;
	}

	@Override
	public String getPassword() {
		return user.getPassword();
	}

	@Override
	public String getUsername() {
		return user.getFirstName().toLowerCase().concat(user.getLastName().toLowerCase());
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
		return true;
	}

}
