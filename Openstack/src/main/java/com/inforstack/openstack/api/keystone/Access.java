package com.inforstack.openstack.api.keystone;

import java.util.Date;

import org.codehaus.jackson.annotate.JsonProperty;

public class Access {
	
	public static final class Token {
		
		private String id;
		
		@JsonProperty("issued_at")
		private Date issued;
		
		@JsonProperty("expires")
		private Date expires;
		
		private Tenant tenant;
		
		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}

		public Date getIssued() {
			return issued;
		}

		public void setIssued(Date issued) {
			this.issued = issued;
		}

		public Date getExpires() {
			return expires;
		}

		public void setExpires(Date expires) {
			this.expires = expires;
		}

		public Tenant getTenant() {
			return tenant;
		}

		public void setTenant(Tenant tenant) {
			this.tenant = tenant;
		}
		
	}
	
	public static final class Service {
		
		public static final class EndPoint {
			
			private String id;
			
			private String region;
			
			private String adminURL;
			
			private String internalURL;
			
			private String publicURL;

			public String getId() {
				return id;
			}

			public void setId(String id) {
				this.id = id;
			}

			public String getRegion() {
				return region;
			}

			public void setRegion(String region) {
				this.region = region;
			}

			public String getAdminURL() {
				return adminURL;
			}

			public void setAdminURL(String adminURL) {
				this.adminURL = adminURL;
			}

			public String getInternalURL() {
				return internalURL;
			}

			public void setInternalURL(String internalURL) {
				this.internalURL = internalURL;
			}

			public String getPublicURL() {
				return publicURL;
			}

			public void setPublicURL(String publicURL) {
				this.publicURL = publicURL;
			}

		}
		
		private String name;
		
		private String type;
		
		private EndPoint[] endpoints;
		
		@JsonProperty("endpoints_links")
		private String[] endpointsLinks;

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getType() {
			return type;
		}

		public void setType(String type) {
			this.type = type;
		}

		public EndPoint[] getEndpoints() {
			return endpoints;
		}

		public void setEndpoints(EndPoint[] endpoints) {
			this.endpoints = endpoints;
		}

		public String[] getEndpointsLinks() {
			return endpointsLinks;
		}

		public void setEndpointsLinks(String[] endpointsLinks) {
			this.endpointsLinks = endpointsLinks;
		}

	}
	
	public static final class User {
		
		private String id;
		
		private String name;
		
		private String username;
		
		private Role[] roles;
		
		@JsonProperty("roles_links")
		private String[] rolesLinks;

		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getUsername() {
			return username;
		}

		public void setUsername(String username) {
			this.username = username;
		}

		public Role[] getRoles() {
			return roles;
		}

		public void setRoles(Role[] roles) {
			this.roles = roles;
		}

		public String[] getRolesLinks() {
			return rolesLinks;
		}

		public void setRoles_links(String[] rolesLinks) {
			this.rolesLinks = rolesLinks;
		}
		
	}
	
	public class Metadata {
		
		@JsonProperty("is_admin")
		private int isAdmin;
		
		private String[] roles;

		public int getIsAdmin() {
			return isAdmin;
		}

		public void setIsAdmin(int isAdmin) {
			this.isAdmin = isAdmin;
		}

		public String[] getRoles() {
			return roles;
		}

		public void setRoles(String[] roles) {
			this.roles = roles;
		}

	}
	
	private Token token;
	
	private Service[] serviceCatalog;
	
	private User user;
	
	private Metadata metadata;

	public Token getToken() {
		return token;
	}

	public void setToken(Token token) {
		this.token = token;
	}

	public Service[] getServiceCatalog() {
		return serviceCatalog;
	}

	public void setServiceCatalog(Service[] serviceCatalog) {
		this.serviceCatalog = serviceCatalog;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Metadata getMetadata() {
		return metadata;
	}

	public void setMetadata(Metadata metadata) {
		this.metadata = metadata;
	}

}
