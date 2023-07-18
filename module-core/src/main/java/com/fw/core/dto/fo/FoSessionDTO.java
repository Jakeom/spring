package com.fw.core.dto.fo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Session DTO
 * 
 * @author jung
 */
@Slf4j
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class FoSessionDTO implements UserDetails, Serializable {

	private static final long serialVersionUID = 1L;

	private String dType;
	private String id;
	private String createdAt;
	private String updatedAt;
	private String agreeMarketing;
	private String agreeMarketingAt;
	private String deleted;
	private String email;
	private String isTemp;
	private String loginId;
	private String name;
	private String password;
	private String passwordChangeDate;
	private String phone;
	private String privacyExpire;
	private String privacyExpireDate;
	private String profilePictureFileId;
	private String deletedAt;
	private String lastChangePassword;
	private String pushToken;
	private String useAppPush;
	private String isStop;
	private String passwordFailCount;
	private String prevPage;
	private String roleName;
	private String profileUrl;
	private String balance;
	private String resumeRestricted;
	private String hhBalance;

	private String autoLogin;
	private String autoLoginToken;

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		List<GrantedAuthority> authorities = new ArrayList<>();
		authorities.add(new SimpleGrantedAuthority("ROLE_" + roleName));
		return authorities;
	}

	@Override
	public String getPassword() {
		return this.password;
	}

	@Override
	public String getUsername() {

		return this.loginId;
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
		return StringUtils.equals(this.deleted, "0");
	}

}
