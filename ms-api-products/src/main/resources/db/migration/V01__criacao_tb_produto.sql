CREATE TABLE tb_produto (
    id CHAR(32) NOT NULL PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    preco Double(10, 4),
    ativo BOOLEAN NOT NULL DEFAULT TRUE,
    data_criacao DATETIME NOT NULL,
    data_alteracao DATETIME NULL
);