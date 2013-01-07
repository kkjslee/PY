package com.inforstack.openstack.api.keystone;


public class TokenRequest {
	
	public static final class Auth {
		
		public static final class Credentials {
			
			private String username;
			
			private String password;

			public String getUsername() {
				return username;
			}

			public void setUsername(String username) {
				this.username = username;
			}

			public String getPassword() {
				return password;
			}

			public void setPassword(String password) {
				this.password = password;
			}
			
		}
		
		private Credentials passwordCredentials;
		
		private String tenantId;

		public Credentials getPasswordCredentials() {
			return passwordCredentials;
		}

		public void setPasswordCredentials(Credentials passwordCredentials) {
			this.passwordCredentials = passwordCredentials;
		}

		public String getTenantId() {
			return tenantId;
		}

		public void setTenantId(String tenantId) {
			this.tenantId = tenantId;
		}
		
	}

	private Auth auth;

	public Auth getAuth() {
		return auth;
	}

	public void setAuth(Auth auth) {
		this.auth = auth;
	}
	
}
