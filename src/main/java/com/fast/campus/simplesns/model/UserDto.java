package com.fast.campus.simplesns.model;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fast.campus.simplesns.model.entity.UserEntity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
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


	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return List.of(new SimpleGrantedAuthority(role.name()));
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return userName;
	}

	@Override
	public boolean isAccountNonExpired() {
		return removedAt == null;
	}

	@Override
	public boolean isAccountNonLocked() {
		return removedAt == null;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return removedAt == null;
	}

	@Override
	public boolean isEnabled() {
		return removedAt == null;
	}
}
