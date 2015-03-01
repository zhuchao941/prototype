package com.zhu.prototype.shiro.credential;

import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.springframework.stereotype.Component;

@Component
public class PlainPasswordMatcher implements CredentialsMatcher {

	@Override
	public boolean doCredentialsMatch(AuthenticationToken token,
			AuthenticationInfo info) {
		if (token.getCredentials() == null || info.getCredentials() == null) {
			return false;
		}
		char[] submitPassword = (char[]) token.getCredentials();
		char[] storedPassword = (char[]) info.getCredentials();
		if (submitPassword.length != storedPassword.length) {
			return false;
		}
		for (int i = 0; i < submitPassword.length; i++) {
			if (submitPassword[i] != storedPassword[i]) {
				return false;
			}
		}
		return true;
	}

}
