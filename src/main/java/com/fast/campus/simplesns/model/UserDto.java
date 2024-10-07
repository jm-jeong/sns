package com.fast.campus.simplesns.model;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fast.campus.simplesns.model.entity.UserEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserDto implements UserDetails {

	private Integer id;
	private String userName;
	private String password;
	private UserRole role;
	private LocalDateTime registeredAt;
	private LocalDateTime updatedAt;
	private LocalDateTime removedAt;

	public static UserDto fromEntity(UserEntity entity) {
		return new UserDto(
			entity.getId(),
			entity.getUserName(),
			entity.getPassword(),
			entity.getRole(),
			entity.getRegisteredAt(),
			entity.getUpdatedAt(),
			entity.getRemovedAt()
		);
	}

	@JsonIgnore
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return List.of(new SimpleGrantedAuthority(role.name()));
	}

	@JsonIgnore
	@Override
	public String getPassword() {
		return password;
	}


	@Override
	public String getUsername() {
		return userName;
	}

	@JsonIgnore
	@Override
	public boolean isAccountNonExpired() {
		return removedAt == null;
	}

	@JsonIgnore
	@Override
	public boolean isAccountNonLocked() {
		return removedAt == null;
	}

	@JsonIgnore
	@Override
	public boolean isCredentialsNonExpired() {
		return removedAt == null;
	}

	@JsonIgnore
	@Override
	public boolean isEnabled() {
		return removedAt == null;
	}
}
