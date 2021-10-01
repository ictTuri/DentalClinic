package com.clinic.dental.security.auth;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.clinic.dental.model.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.var;

@RequiredArgsConstructor
@Service
public class ApplicationUserService implements UserDetailsService{
	
	private final UserRepository userRepo;

	@Override
	public UserDetails loadUserByUsername(String credential) throws UsernameNotFoundException {
		
		String NIDPattern = "^[a-zA-Z]{1}[0-9]{8}[a-zA-Z]{1}$";
		String phonePattern = "^06[7-9]{1}[0-9]{7}$";
		
		if(credential != null) {
			if(credential.trim().toUpperCase().matches(NIDPattern)) {
				var userEntity = userRepo.findByNID(credential);
				return new ApplicationUser(userEntity);
			}else if(credential.trim().matches(phonePattern)) {
				var userEntity = userRepo.findByPhone(credential);
				return new ApplicationUser(userEntity);
			}else {
				var userEntity = userRepo.findByEmail(credential);
				return new ApplicationUser(userEntity);
			}
		}
		throw new UsernameNotFoundException("User: "+credential+" not found or not activated!");
	}

}
