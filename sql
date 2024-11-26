CREATE DATABASE av2;

USE av2;

CREATE TABLE alunos (
    cpf VARCHAR(14) PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    data_nascimento DATE NOT NULL,
    peso DOUBLE NOT NULL,
    altura DOUBLE NOT NULL
);


select * from alunos;