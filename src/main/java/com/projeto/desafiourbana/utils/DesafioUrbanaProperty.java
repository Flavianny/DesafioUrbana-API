package com.projeto.desafiourbana.utils;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("desafiourbana")
public class DesafioUrbanaProperty {
	
	private final Mail mail = new Mail();
	
	private final Configuration configuration = new Configuration();
	
	public Mail getMail() {
		return mail;
	}
	
	public Configuration getConfiguration() {
		return configuration;
	}
	
	public static class Configuration {
		private String linkLocal;
		
		private String linkProd;

		public String getLinkLocal() {
			return linkLocal;
		}

		public void setLinkLocal(String linkLocal) {
			this.linkLocal = linkLocal;
		}

		public String getLinkProd() {
			return linkProd;
		}

		public void setLinkProd(String linkProd) {
			this.linkProd = linkProd;
		}
		
		
	}
	
	public static class Mail {
		private String host;
		
		private Integer port;
		
		private String username;
		
		private String password;

		public String getHost() {
			return host;
		}

		public void setHost(String host) {
			this.host = host;
		}

		public Integer getPort() {
			return port;
		}

		public void setPort(Integer port) {
			this.port = port;
		}

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

}
