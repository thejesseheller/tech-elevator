package com.techelevator.controller;

import javax.validation.Valid;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.techelevator.dao.UserDAO;
import com.techelevator.model.Location;
import com.techelevator.model.LoginDTO;
import com.techelevator.model.RegisterUserDTO;
import com.techelevator.model.User;
import com.techelevator.model.UserAlreadyExistsException;
import com.techelevator.security.jwt.JWTFilter;
import com.techelevator.security.jwt.TokenProvider;

@RestController
@CrossOrigin
public class AuthenticationController {

	private final TokenProvider tokenProvider;
	private final AuthenticationManagerBuilder authenticationManagerBuilder;
	private UserDAO userDAO;

	public AuthenticationController(TokenProvider tokenProvider,
			AuthenticationManagerBuilder authenticationManagerBuilder, UserDAO userDAO) {
		this.tokenProvider = tokenProvider;
		this.authenticationManagerBuilder = authenticationManagerBuilder;
		this.userDAO = userDAO;
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginDTO loginDto) {

		UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
				loginDto.getUsername(), loginDto.getPassword());

		Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = tokenProvider.createToken(authentication, false);

		User user = userDAO.findByUsername(loginDto.getUsername());

		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add(JWTFilter.AUTHORIZATION_HEADER, "Bearer " + jwt);
		return new ResponseEntity<>(new LoginResponse(jwt, user), httpHeaders, HttpStatus.OK);
	}

	@ResponseStatus(HttpStatus.CREATED)
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public void register(@Valid @RequestBody RegisterUserDTO newUser) {
		try {
			User user = userDAO.findByUsername(newUser.getUsername());
			throw new UserAlreadyExistsException();
		} catch (UsernameNotFoundException e) {
			String API_URL = "https://www.zipcodeapi.com/rest/49Qsr3uBTRmojAoZ1SyehLeysfu7ynj50rc6lYkGSffhhW0dFjjbY1saE9idYwOo/info.json/";
			RestTemplate restTemplate = new RestTemplate();
			Location loc = restTemplate.getForObject(API_URL + newUser.getZip() + "/degrees", Location.class);
			userDAO.create(newUser.getUsername(), newUser.getPassword(), newUser.getRole(), newUser.getZip(),
					loc.getLat(), loc.getLng());
		}
	}

	@ResponseStatus(HttpStatus.NO_CONTENT)
	@RequestMapping(value = "/update/{userId}", method = RequestMethod.PUT)
	public void updateZipCode(@PathVariable Long userId, @RequestBody User dummy) {
		try {
			String API_URL = "https://www.zipcodeapi.com/rest/49Qsr3uBTRmojAoZ1SyehLeysfu7ynj50rc6lYkGSffhhW0dFjjbY1saE9idYwOo/info.json/";
			RestTemplate restTemplate = new RestTemplate();
			Location loc = restTemplate.getForObject(API_URL + dummy.getZip() + "/degrees", Location.class);
			userDAO.updateZip(userId, dummy.getZip(), loc.getLat(), loc.getLng());
		} catch (UsernameNotFoundException e) {
			System.out.println(e.getMessage());
		}
	}
	
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/pull/{zip}", method = RequestMethod.GET)
	public Location returnZip(@PathVariable String zip) {
		Location loc = null;
		try {
			String API_URL = "https://www.zipcodeapi.com/rest/49Qsr3uBTRmojAoZ1SyehLeysfu7ynj50rc6lYkGSffhhW0dFjjbY1saE9idYwOo/info.json/";
			RestTemplate restTemplate = new RestTemplate();
			loc = restTemplate.getForObject(API_URL + zip + "/degrees", Location.class);	
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} return loc;
	}

	/**
	 * Object to return as body in JWT Authentication.
	 */
	static class LoginResponse {

		private String token;
		private User user;

		LoginResponse(String token, User user) {
			this.token = token;
			this.user = user;
		}

		@JsonProperty("token")
		String getToken() {
			return token;
		}

		void setToken(String token) {
			this.token = token;
		}

		@JsonProperty("user")
		public User getUser() {
			return user;
		}

		public void setUser(User user) {
			this.user = user;
		}
	}
}
