CREATE TABLE usuario(
	codigo BIGINT(20) PRIMARY KEY AUTO_INCREMENT,
	nome VARCHAR(100) NOT NULL,
	email VARCHAR(200) NOT NULL,
	senha VARCHAR(200) NOT NULL
)engine=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO usuario
(nome, email, senha) values
('Flavianny', 'flavianny@gmail.com', '$2a$10$bGv8AUaD0MmvXtcJc9mP5OsiIx19lPnc8nKuCpksUb10XuAc/LWu.');