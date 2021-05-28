package com.project.lms.auth;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import com.project.lms.model.RoleEntity;
import com.project.lms.model.UserEntity;
import com.project.lms.repository.RoleRepository;
import com.project.lms.repository.UserRepository;
import com.project.lms.utils.RoleUtil;
import com.project.lms.utils.UserUtil;


@SpringBootTest
class ApplicationUserServiceTest {

	@InjectMocks
	private ApplicationUserService applicationUserService;
	
	@Mock
	private UserRepository userRepository;
	
	@Mock
	private RoleRepository roleRepository;
	
	@Test
	void givenUsername_whenAskeForIt_getUserDetails() {
		List<RoleEntity> roleList = new ArrayList<>();
		UserEntity user = UserUtil.userAdmin();
		roleList.add(RoleUtil.adminRole());
		
		when(userRepository.getActivatedUserByUsername(user.getUsername())).thenReturn(null);
		assertThrows(UsernameNotFoundException.class, ()->applicationUserService.loadUserByUsername(user.getUsername()));
		
		when(userRepository.getActivatedUserByUsername(user.getUsername())).thenReturn(user);
		when(roleRepository.getUserRole(user)).thenReturn(roleList);
		
		ApplicationUser appUser = (ApplicationUser) applicationUserService.loadUserByUsername(user.getUsername());
		assertNotNull(appUser);
	}

}
