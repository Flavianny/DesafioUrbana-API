CREATE TABLE cartao(
	codigo BIGINT(50) PRIMARY KEY AUTO_INCREMENT,
	numero_cartao int(30) NOT NULL,
	nome VARCHAR(50) NOT NULL,
	status boolean NOT NULL,
	tipo_cartao VARCHAR(200) NOT NULL,
	usuario BIGINT(50) NOT NULL,
	FOREIGN KEY (usuario) REFERENCES usuario(codigo)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;