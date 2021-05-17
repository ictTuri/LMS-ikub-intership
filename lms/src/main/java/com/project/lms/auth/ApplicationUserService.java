package com.project.lms.auth;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.project.lms.entity.RoleEntity;
import com.project.lms.entity.UserEntity;
import com.project.lms.repository.RoleRepository;
import com.project.lms.repository.UserRepository;

@Service
public class ApplicationUserService implements UserDetailsService{
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private RoleRepository roleRepository;

	private static final Logger logger = LogManager.getLogger(ApplicationUserService.class);
	
	
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserEntity userEntity = userRepository.getActivatedUserByUsername(username);
		if (userEntity == null) {
			throw new UsernameNotFoundException("User: "+username+" not found or not activated!");
		}
		List<RoleEntity> roleList = roleRepository.getUserRole(userEntity);
		
		logger.info("getting user {}",userEntity);
		
		return new ApplicationUser(userEntity, roleList);
	}

}
