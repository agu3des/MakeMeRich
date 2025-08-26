/*contas*/

-- 1. Conta Corrente
INSERT INTO conta (numero, descricao, tipo, dia_fechamento, correntista_id)
VALUES ('1001-1', 'Conta Corrente Principal', 'CORRENTE', NULL, 1);

-- 2. Conta Cartão
INSERT INTO conta (numero, descricao, tipo, dia_fechamento, correntista_id)
VALUES ('2001-2', 'Cartão Nubank', 'CARTAO', 10, 1);

-- 3. Conta Corrente
INSERT INTO conta (numero, descricao, tipo, dia_fechamento, correntista_id)
VALUES ('1002-3', 'Conta Universitária', 'CORRENTE', NULL, 2);

-- 4. Conta Cartão
INSERT INTO conta (numero, descricao, tipo, dia_fechamento, correntista_id)
VALUES ('2002-4', 'Cartão Inter', 'CARTAO', 15, 2);

-- 5. Conta Corrente
INSERT INTO conta (numero, descricao, tipo, dia_fechamento, correntista_id)
VALUES ('1003-5', 'Conta Poupança Família', 'CORRENTE', NULL, 3);

-- 6. Conta Cartão
INSERT INTO conta (numero, descricao, tipo, dia_fechamento, correntista_id)
VALUES ('2003-6', 'Cartão Itaú Gold', 'CARTAO', 20, 3);

-- 7. Conta Corrente
INSERT INTO conta (numero, descricao, tipo, dia_fechamento, correntista_id)
VALUES ('1004-7', 'Conta Empresa', 'CORRENTE', NULL, 4);

-- 8. Conta Cartão
INSERT INTO conta (numero, descricao, tipo, dia_fechamento, correntista_id)
VALUES ('2004-8', 'Cartão C6 Bank', 'CARTAO', 12, 4);

-- 9. Conta Corrente
INSERT INTO conta (numero, descricao, tipo, dia_fechamento, correntista_id)
VALUES ('1005-9', 'Conta Conjunta', 'CORRENTE', NULL, 5);

-- 10. Conta Cartão
INSERT INTO conta (numero, descricao, tipo, dia_fechamento, correntista_id)
VALUES ('2005-0', 'Cartão Bradesco Prime', 'CARTAO', 28, 5);

-- 11. Conta Corrente
INSERT INTO conta (numero, descricao, tipo, dia_fechamento, correntista_id)
VALUES ('1006-1', 'Conta Digital Nubank', 'CORRENTE', NULL, 6);

-- 12. Conta Cartão
INSERT INTO conta (numero, descricao, tipo, dia_fechamento, correntista_id)
VALUES ('2006-2', 'Cartão Santander Free', 'CARTAO', 5, 6);

-- 13. Conta Corrente
INSERT INTO conta (numero, descricao, tipo, dia_fechamento, correntista_id)
VALUES ('1007-3', 'Conta Poupança Ricardo', 'CORRENTE', NULL, 7);

-- 14. Conta Cartão
INSERT INTO conta (numero, descricao, tipo, dia_fechamento, correntista_id)
VALUES ('2007-4', 'Cartão XP Visa', 'CARTAO', 18, 7);

-- 15. Conta Corrente
INSERT INTO conta (numero, descricao, tipo, dia_fechamento, correntista_id)
VALUES ('1008-5', 'Conta Salário Juliana', 'CORRENTE', NULL, 8);

-- 16. Conta Cartão
INSERT INTO conta (numero, descricao, tipo, dia_fechamento, correntista_id)
VALUES ('2008-6', 'Cartão Porto Seguro', 'CARTAO', 25, 8);

-- 17. Conta Corrente
INSERT INTO conta (numero, descricao, tipo, dia_fechamento, correntista_id)
VALUES ('1009-7', 'Conta Corrente Bruno', 'CORRENTE', NULL, 9);

-- 18. Conta Cartão
INSERT INTO conta (numero, descricao, tipo, dia_fechamento, correntista_id)
VALUES ('2009-8', 'Cartão Digio', 'CARTAO', 8, 9);

-- 19. Conta Corrente
INSERT INTO conta (numero, descricao, tipo, dia_fechamento, correntista_id)
VALUES ('1010-9', 'Conta Camila Ferreira', 'CORRENTE', NULL, 10);

-- 20. Conta Cartão
INSERT INTO conta (numero, descricao, tipo, dia_fechamento, correntista_id)
VALUES ('2010-0', 'Cartão Pan', 'CARTAO', 17, 10);

-- 21. Conta Corrente
INSERT INTO conta (numero, descricao, tipo, dia_fechamento, correntista_id)
VALUES ('1011-1', 'Conta Corrente Fernanda', 'CORRENTE', NULL, 6);

-- 22. Conta Cartão
INSERT INTO conta (numero, descricao, tipo, dia_fechamento, correntista_id)
VALUES ('2011-2', 'Cartão Black João Silva', 'CARTAO', 22, 1);

-- 23. Conta Corrente
INSERT INTO conta (numero, descricao, tipo, dia_fechamento, correntista_id)
VALUES ('1012-3', 'Conta Empresarial Maria', 'CORRENTE', NULL, 2);

-- 24. Conta Cartão
INSERT INTO conta (numero, descricao, tipo, dia_fechamento, correntista_id)
VALUES ('2012-4', 'Cartão Credicard Zero', 'CARTAO', 9, 3);

-- 25. Conta Corrente
INSERT INTO conta (numero, descricao, tipo, dia_fechamento, correntista_id)
VALUES ('1013-5', 'Conta Premium Paulo', 'CORRENTE', NULL, 5);



/*correntistas*/

-- 1. Admin principal
INSERT INTO correntista (nome, senha, email, admin)
VALUES ('João Silva', 'senha123', 'joao.silva@email.com', TRUE);

-- 2. Usuário comum
INSERT INTO correntista (nome, senha, email, admin)
VALUES ('Maria Oliveira', 'maria2025', 'maria.oliveira@email.com', FALSE);

-- 3. Usuário comum
INSERT INTO correntista (nome, senha, email, admin)
VALUES ('Carlos Santos', 'carlos321', 'carlos.santos@email.com', FALSE);

-- 4. Usuário comum
INSERT INTO correntista (nome, senha, email, admin)
VALUES ('Ana Costa', 'ana456', 'ana.costa@email.com', FALSE);

-- 5. Usuário comum
INSERT INTO correntista (nome, senha, email, admin)
VALUES ('Paulo Pereira', 'paulo789', 'paulo.pereira@email.com', FALSE);

-- 6. Admin secundário
INSERT INTO correntista (nome, senha, email, admin)
VALUES ('Fernanda Lima', 'admin2025', 'fernanda.lima@email.com', TRUE);

-- 7. Usuário comum
INSERT INTO correntista (nome, senha, email, admin)
VALUES ('Ricardo Almeida', 'ricardo555', 'ricardo.almeida@email.com', FALSE);

-- 8. Usuário comum
INSERT INTO correntista (nome, senha, email, admin)
VALUES ('Juliana Rocha', 'juliana123', 'juliana.rocha@email.com', FALSE);

-- 9. Usuário comum
INSERT INTO correntista (nome, senha, email, admin)
VALUES ('Bruno Martins', 'bruno2025', 'bruno.martins@email.com', FALSE);

-- 10. Usuário comum
INSERT INTO correntista (nome, senha, email, admin)
VALUES ('Camila Ferreira', 'camila999', 'camila.ferreira@email.com', FALSE);

-- 11
INSERT INTO correntista (nome, senha, email, admin)
VALUES ('Sofia Mendes', 'sofia123', 'sofia.mendes@email.com', FALSE);

-- 12
INSERT INTO correntista (nome, senha, email, admin)
VALUES ('Lucas Ribeiro', 'lucas321', 'lucas.ribeiro@email.com', FALSE);

-- 13
INSERT INTO correntista (nome, senha, email, admin)
VALUES ('Mariana Silva', 'mari2025', 'mariana.silva@email.com', FALSE);

-- 14
INSERT INTO correntista (nome, senha, email, admin)
VALUES ('Gabriel Souza', 'gabriel456', 'gabriel.souza@email.com', FALSE);

-- 15
INSERT INTO correntista (nome, senha, email, admin)
VALUES ('Patrícia Gomes', 'patricia789', 'patricia.gomes@email.com', FALSE);

-- 16
INSERT INTO correntista (nome, senha, email, admin)
VALUES ('Thiago Fernandes', 'thiago555', 'thiago.fernandes@email.com', FALSE);

-- 17
INSERT INTO correntista (nome, senha, email, admin)
VALUES ('Larissa Alves', 'larissa999', 'larissa.alves@email.com', FALSE);

-- 18
INSERT INTO correntista (nome, senha, email, admin)
VALUES ('Diego Costa', 'diego123', 'diego.costa@email.com', FALSE);

-- 19
INSERT INTO correntista (nome, senha, email, admin)
VALUES ('Isabela Rocha', 'isabela456', 'isabela.rocha@email.com', FALSE);

-- 20
INSERT INTO correntista (nome, senha, email, admin)
VALUES ('Felipe Martins', 'felipe2025', 'felipe.martins@email.com', FALSE);

-- 21
INSERT INTO correntista (nome, senha, email, admin)
VALUES ('Aline Teixeira', 'aline321', 'aline.teixeira@email.com', FALSE);

-- 22
INSERT INTO correntista (nome, senha, email, admin)
VALUES ('Rafael Carvalho', 'rafa789', 'rafael.carvalho@email.com', FALSE);

-- 23
INSERT INTO correntista (nome, senha, email, admin)
VALUES ('Beatriz Lima', 'bia2025', 'beatriz.lima@email.com', FALSE);

-- 24
INSERT INTO correntista (nome, senha, email, admin)
VALUES ('Eduardo Nunes', 'edu555', 'eduardo.nunes@email.com', FALSE);

-- 25
INSERT INTO correntista (nome, senha, email, admin)
VALUES ('Vanessa Moreira', 'vanessa123', 'vanessa.moreira@email.com', FALSE);


/*transacoes*/
-- 1. Salário recebido (entrada crédito)
INSERT INTO transacao (descricao, data, valor, movimento, natureza, categoria_id, conta_id)
VALUES ('Salário Mensal', '2025-08-01', 4500.00, 'CREDITO', 'ENTRADA', 1, 1);

-- 2. Compra supermercado (saída débito)
INSERT INTO transacao (descricao, data, valor, movimento, natureza, categoria_id, conta_id)
VALUES ('Supermercado Carrefour', '2025-08-03', 320.75, 'DEBITO', 'SAIDA', 2, 1);

-- 3. Conta de energia (saída débito)
INSERT INTO transacao (descricao, data, valor, movimento, natureza, categoria_id, conta_id)
VALUES ('Conta de Luz Enel', '2025-08-05', 210.50, 'DEBITO', 'SAIDA', 3, 1);

-- 4. Investimento Tesouro Direto (crédito investimento)
INSERT INTO transacao (descricao, data, valor, movimento, natureza, categoria_id, conta_id)
VALUES ('Aplicação Tesouro Selic', '2025-08-07', 1000.00, 'CREDITO', 'INVESTIMENTO', 4, 2);

-- 5. Saque em dinheiro (saída débito)
INSERT INTO transacao (descricao, data, valor, movimento, natureza, categoria_id, conta_id)
VALUES ('Saque Caixa 24h', '2025-08-08', 500.00, 'DEBITO', 'SAIDA', 5, 2);

-- 6. Recebimento de cliente (entrada crédito)
INSERT INTO transacao (descricao, data, valor, movimento, natureza, categoria_id, conta_id)
VALUES ('Pagamento Cliente A', '2025-08-10', 1200.00, 'CREDITO', 'ENTRADA', 6, 3);

-- 7. Compra combustível (saída débito)
INSERT INTO transacao (descricao, data, valor, movimento, natureza, categoria_id, conta_id)
VALUES ('Posto Shell Gasolina', '2025-08-12', 180.00, 'DEBITO', 'SAIDA', 2, 3);

-- 8. Mensalidade academia (saída débito)
INSERT INTO transacao (descricao, data, valor, movimento, natureza, categoria_id, conta_id)
VALUES ('Academia SmartFit', '2025-08-15', 120.00, 'DEBITO', 'SAIDA', 7, 4);

-- 9. Rendimento de investimento (entrada crédito)
INSERT INTO transacao (descricao, data, valor, movimento, natureza, categoria_id, conta_id)
VALUES ('Rendimento Tesouro Selic', '2025-08-20', 45.25, 'CREDITO', 'ENTRADA', 4, 2);

-- 10. Compra online (saída débito)
INSERT INTO transacao (descricao, data, valor, movimento, natureza, categoria_id, conta_id)
VALUES ('Amazon - Eletrônicos', '2025-08-22', 899.90, 'DEBITO', 'SAIDA', 8, 5);

-- 11
INSERT INTO transacao (descricao, data, valor, movimento, natureza, categoria_id, conta_id)
VALUES ('Pagamento Restaurante', '2025-08-23', 150.00, 'DEBITO', 'SAIDA', 9, 6);

-- 12
INSERT INTO transacao (descricao, data, valor, movimento, natureza, categoria_id, conta_id)
VALUES ('Depósito Cliente B', '2025-08-24', 2500.00, 'CREDITO', 'ENTRADA', 6, 7);

-- 13
INSERT INTO transacao (descricao, data, valor, movimento, natureza, categoria_id, conta_id)
VALUES ('Netflix Mensalidade', '2025-08-25', 55.90, 'DEBITO', 'SAIDA', 8, 8);

-- 14
INSERT INTO transacao (descricao, data, valor, movimento, natureza, categoria_id, conta_id)
VALUES ('Compra Shopping', '2025-08-26', 780.40, 'DEBITO', 'SAIDA', 5, 9);

-- 15
INSERT INTO transacao (descricao, data, valor, movimento, natureza, categoria_id, conta_id)
VALUES ('Transferência recebida João', '2025-08-27', 900.00, 'CREDITO', 'ENTRADA', 1, 10);

-- 16
INSERT INTO transacao (descricao, data, valor, movimento, natureza, categoria_id, conta_id)
VALUES ('Seguro Automóvel', '2025-08-28', 350.00, 'DEBITO', 'SAIDA', 7, 11);

-- 17
INSERT INTO transacao (descricao, data, valor, movimento, natureza, categoria_id, conta_id)
VALUES ('Compra Farmácia', '2025-08-29', 89.90, 'DEBITO', 'SAIDA', 3, 12);

-- 18
INSERT INTO transacao (descricao, data, valor, movimento, natureza, categoria_id, conta_id)
VALUES ('Rendimento Poupança', '2025-08-30', 30.50, 'CREDITO', 'ENTRADA', 4, 13);

-- 19
INSERT INTO transacao (descricao, data, valor, movimento, natureza, categoria_id, conta_id)
VALUES ('Mensalidade Escola', '2025-09-01', 1200.00, 'DEBITO', 'SAIDA', 7, 14);

-- 20
INSERT INTO transacao (descricao, data, valor, movimento, natureza, categoria_id, conta_id)
VALUES ('Freelance Projeto Web', '2025-09-02', 3000.00, 'CREDITO', 'ENTRADA', 6, 15);

-- 21
INSERT INTO transacao (descricao, data, valor, movimento, natureza, categoria_id, conta_id)
VALUES ('Conta de Água SABESP', '2025-09-03', 95.20, 'DEBITO', 'SAIDA', 3, 16);

-- 22
INSERT INTO transacao (descricao, data, valor, movimento, natureza, categoria_id, conta_id)
VALUES ('Lucro Ações PETR4', '2025-09-04', 560.00, 'CREDITO', 'ENTRADA', 4, 17);

-- 23
INSERT INTO transacao (descricao, data, valor, movimento, natureza, categoria_id, conta_id)
VALUES ('Cinema', '2025-09-05', 70.00, 'DEBITO', 'SAIDA', 5, 18);

-- 24
INSERT INTO transacao (descricao, data, valor, movimento, natureza, categoria_id, conta_id)
VALUES ('Venda Marketplace', '2025-09-06', 450.00, 'CREDITO', 'ENTRADA', 1, 19);

-- 25
INSERT INTO transacao (descricao, data, valor, movimento, natureza, categoria_id, conta_id)
VALUES ('Viagem Final de Semana', '2025-09-07', 1200.00, 'DEBITO', 'SAIDA', 9, 20);



/*comentarios*/
-- 1
INSERT INTO comentario (texto, transacao_id)
VALUES ('Esse pagamento refere-se ao aluguel de agosto.', 1);

-- 2
INSERT INTO comentario (texto, transacao_id)
VALUES ('Supermercado mais caro do que no mês passado.', 2);

-- 3
INSERT INTO comentario (texto, transacao_id)
VALUES ('Conta de energia veio com valor menor, devido ao desconto.', 3);

-- 4
INSERT INTO comentario (texto, transacao_id)
VALUES ('Investimento programado no Tesouro Direto.', 4);

-- 5
INSERT INTO comentario (texto, transacao_id)
VALUES ('Saque realizado para despesas pessoais.', 5);

-- 6
INSERT INTO comentario (texto, transacao_id)
VALUES ('Cliente pagou antes do prazo, ótimo!', 6);

-- 7
INSERT INTO comentario (texto, transacao_id)
VALUES ('Consumo maior de combustível nesta semana.', 7);

-- 8
INSERT INTO comentario (texto, transacao_id)
VALUES ('Academia debitou corretamente a mensalidade.', 8);

-- 9
INSERT INTO comentario (texto, transacao_id)
VALUES ('Rendimento aplicado novamente no mesmo investimento.', 9);

-- 10
INSERT INTO comentario (texto, transacao_id)
VALUES ('Compra parcelada em 3x sem juros.', 10);

-- 11
INSERT INTO comentario (texto, transacao_id)
VALUES ('Jantar especial em família.', 11);

-- 12
INSERT INTO comentario (texto, transacao_id)
VALUES ('Cliente B fez pagamento integral.', 12);

-- 13
INSERT INTO comentario (texto, transacao_id)
VALUES ('Serviço de streaming pago automaticamente.', 13);

-- 14
INSERT INTO comentario (texto, transacao_id)
VALUES ('Compras parceladas no shopping.', 14);

-- 15
INSERT INTO comentario (texto, transacao_id)
VALUES ('Transferência recebida de João para viagem.', 15);

-- 16
INSERT INTO comentario (texto, transacao_id)
VALUES ('Seguro do carro debitado corretamente.', 16);

-- 17
INSERT INTO comentario (texto, transacao_id)
VALUES ('Remédio comprado na farmácia.', 17);

-- 18
INSERT INTO comentario (texto, transacao_id)
VALUES ('Juros da poupança creditados.', 18);

-- 19
INSERT INTO comentario (texto, transacao_id)
VALUES ('Mensalidade escolar do filho.', 19);

-- 20
INSERT INTO comentario (texto, transacao_id)
VALUES ('Pagamento do projeto freelance concluído.', 20);

-- 21
INSERT INTO comentario (texto, transacao_id)
VALUES ('Conta de água com valor dentro do esperado.', 21);

-- 22
INSERT INTO comentario (texto, transacao_id)
VALUES ('Lucro obtido com ações PETR4.', 22);

-- 23
INSERT INTO comentario (texto, transacao_id)
VALUES ('Cinema de sábado à noite.', 23);

-- 24
INSERT INTO comentario (texto, transacao_id)
VALUES ('Venda concluída no marketplace.', 24);

-- 25
INSERT INTO comentario (texto, transacao_id)
VALUES ('Viagem com amigos no fim de semana.', 25);
