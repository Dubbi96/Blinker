package com.blinker.atom;

import com.blinker.atom.service.AuthService;
import com.blinker.atom.service.CustomUserDetailsService;
import com.blinker.atom.service.SensorService;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
@SpringBootTest
@RequiredArgsConstructor
class AtomApplicationTests {

	@Mock
	private AuthService	authService;
	@Mock
	private CustomUserDetailsService customUserDetailsService;
	@Mock
	private SensorService sensorService;

	@Test
	void contextLoads() {
	}

}
