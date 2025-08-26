
-------------------------------------------------------
INSERT INTO correntista (nome, email, ativo, username) VALUES
('João da Silva', 'joao@makemerich.com', true, 'user1'),
('Maria Oliveira', 'maria@makemerich.com', true, 'user2'),
('Carlos Souza', 'carlos@makemerich.com', true, 'user3'),
('Ana Lima', 'ana@makemerich.com', true, 'user4'),
('Pedro Santos', 'pedro@makemerich.com', true, 'user5'),
('Fernanda Costa', 'fernanda@makemerich.com', true, 'user6'),
('Lucas Almeida', 'lucas@makemerich.com', true, 'user7'),
('Juliana Rocha', 'juliana@makemerich.com', true, 'user8'),
('Rafael Martins', 'rafael@makemerich.com', true, 'user9'),
('Beatriz Carvalho', 'beatriz@makemerich.com', true, 'user10');



/*contas*/

-- 1. Conta Corrente
INSERT INTO conta (numero, descricao, tipo, dia_fechamento, id_correntista)
VALUES ('100111', 'Conta Corrente Principal', 'CORRENTE', NULL, 1);

-- 2. Conta Cartão
INSERT INTO conta (numero, descricao, tipo, dia_fechamento, id_correntista)
VALUES ('200000', 'Cartão Nubank', 'CARTAO', 10, 1);

-- 3. Conta Corrente
INSERT INTO conta (numero, descricao, tipo, dia_fechamento, id_correntista)
VALUES ('100234', 'Conta Universitária', 'CORRENTE', NULL, 2);

-- 4. Conta Cartão
INSERT INTO conta (numero, descricao, tipo, dia_fechamento, id_correntista)
VALUES ('200245', 'Cartão Inter', 'CARTAO', 15, 2);

-- 5. Conta Corrente
INSERT INTO conta (numero, descricao, tipo, dia_fechamento, id_correntista)
VALUES ('100355', 'Conta Poupança Família', 'CORRENTE', NULL, 3);

-- 6. Conta Cartão
INSERT INTO conta (numero, descricao, tipo, dia_fechamento, id_correntista)
VALUES ('200365', 'Cartão Itaú Gold', 'CARTAO', 20, 3);

-- 7. Conta Corrente
INSERT INTO conta (numero, descricao, tipo, dia_fechamento, id_correntista)
VALUES ('100477', 'Conta Empresa', 'CORRENTE', NULL, 4);

-- 8. Conta Cartão
INSERT INTO conta (numero, descricao, tipo, dia_fechamento, id_correntista)
VALUES ('200488', 'Cartão C6 Bank', 'CARTAO', 12, 4);

-- 9. Conta Corrente
INSERT INTO conta (numero, descricao, tipo, dia_fechamento, id_correntista)
VALUES ('100599', 'Conta Conjunta', 'CORRENTE', NULL, 5);

-- 10. Conta Cartão
INSERT INTO conta (numero, descricao, tipo, dia_fechamento, id_correntista)
VALUES ('200500', 'Cartão Bradesco Prime', 'CARTAO', 28, 5);

-- 11. Conta Corrente
INSERT INTO conta (numero, descricao, tipo, dia_fechamento, id_correntista)
VALUES ('100610', 'Conta Digital Nubank', 'CORRENTE', NULL, 6);

-- 12. Conta Cartão
INSERT INTO conta (numero, descricao, tipo, dia_fechamento, id_correntista)
VALUES ('200600', 'Cartão Santander Free', 'CARTAO', 5, 6);

-- 13. Conta Corrente
INSERT INTO conta (numero, descricao, tipo, dia_fechamento, id_correntista)
VALUES ('100700', 'Conta Poupança Ricardo', 'CORRENTE', NULL, 7);

-- 14. Conta Cartão
INSERT INTO conta (numero, descricao, tipo, dia_fechamento, id_correntista)
VALUES ('200700', 'Cartão XP Visa', 'CARTAO', 18, 7);

-- 15. Conta Corrente
INSERT INTO conta (numero, descricao, tipo, dia_fechamento, id_correntista)
VALUES ('100800', 'Conta Salário Juliana', 'CORRENTE', NULL, 8);

-- 16. Conta Cartão
INSERT INTO conta (numero, descricao, tipo, dia_fechamento, id_correntista)
VALUES ('200800', 'Cartão Porto Seguro', 'CARTAO', 25, 8);

-- 17. Conta Corrente
INSERT INTO conta (numero, descricao, tipo, dia_fechamento, id_correntista)
VALUES ('100900', 'Conta Corrente Bruno', 'CORRENTE', NULL, 9);

-- 18. Conta Cartão
INSERT INTO conta (numero, descricao, tipo, dia_fechamento, id_correntista)
VALUES ('200900', 'Cartão Digio', 'CARTAO', 8, 9);

-- 19. Conta Corrente
INSERT INTO conta (numero, descricao, tipo, dia_fechamento, id_correntista)
VALUES ('101000', 'Conta Camila Ferreira', 'CORRENTE', NULL, 10);

-- 20. Conta Cartão
INSERT INTO conta (numero, descricao, tipo, dia_fechamento, id_correntista)
VALUES ('201000', 'Cartão Pan', 'CARTAO', 17, 10);

-- 21. Conta Corrente
INSERT INTO conta (numero, descricao, tipo, dia_fechamento, id_correntista)
VALUES ('101100', 'Conta Corrente Fernanda', 'CORRENTE', NULL, 6);

-- 22. Conta Cartão
INSERT INTO conta (numero, descricao, tipo, dia_fechamento, id_correntista)
VALUES ('201100', 'Cartão Black João Silva', 'CARTAO', 22, 1);

-- 23. Conta Corrente
INSERT INTO conta (numero, descricao, tipo, dia_fechamento, id_correntista)
VALUES ('101200', 'Conta Empresarial Maria', 'CORRENTE', NULL, 2);

-- 24. Conta Cartão
INSERT INTO conta (numero, descricao, tipo, dia_fechamento, id_correntista)
VALUES ('201200', 'Cartão Credicard Zero', 'CARTAO', 9, 3);

-- 25. Conta Corrente
INSERT INTO conta (numero, descricao, tipo, dia_fechamento, id_correntista)
VALUES ('101300', 'Conta Premium Paulo', 'CORRENTE', NULL, 5);




/* 25 Transações de exemplo com enum Movimento (DEBITO/CREDITO) */
INSERT INTO transacao (data, descricao, valor, movimento, categoria_id, conta_id) VALUES
('2025-08-01', 'Venda de produtos', 1500.00, 'CREDITO', 1, 1),
('2025-08-02', 'Compra de materiais', 300.00, 'DEBITO', 2, 2),
('2025-08-03', 'Pagamento de serviço', 450.50, 'DEBITO', 3, 3),
('2025-08-04', 'Recebimento de clientes', 1200.00, 'CREDITO', 4, 4),
('2025-08-05', 'Pagamento de fornecedores', 700.00, 'DEBITO', 5, 5),
('2025-08-06', 'Investimento financeiro', 2000.00, 'CREDITO', 6, 6),
('2025-08-07', 'Compra equipamentos', 850.00, 'DEBITO', 7, 7),
('2025-08-08', 'Venda de serviços', 1750.00, 'CREDITO', 8, 8),
('2025-08-09', 'Despesas administrativas', 400.00, 'DEBITO', 9, 9),
('2025-08-10', 'Recebimento de cliente VIP', 3000.00, 'CREDITO', 10, 10),
('2025-08-11', 'Pagamento folha de pagamento', 2500.00, 'DEBITO', 11, 11),
('2025-08-12', 'Venda de produtos online', 900.00, 'CREDITO', 12, 12),
('2025-08-13', 'Compra de materiais escritório', 150.00, 'DEBITO', 13, 13),
('2025-08-14', 'Recebimento de comissão', 600.00, 'CREDITO', 14, 14),
('2025-08-15', 'Pagamento de energia', 200.00, 'DEBITO', 15, 15),
('2025-08-16', 'Investimento em ações', 1000.00, 'CREDITO', 16, 16),
('2025-08-17', 'Compra suprimentos', 320.00, 'DEBITO', 17, 17),
('2025-08-18', 'Venda parcelada', 1800.00, 'CREDITO', 18, 18),
('2025-08-19', 'Pagamento internet e telefone', 120.00, 'DEBITO', 19, 19),
('2025-08-20', 'Recebimento de cliente', 750.00, 'CREDITO', 20, 20),
('2025-08-21', 'Pagamento impostos', 500.00, 'DEBITO', 21, 21),
('2025-08-22', 'Investimento fundo renda fixa', 1300.00, 'CREDITO', 22, 22),
('2025-08-23', 'Compra material promocional', 250.00, 'DEBITO', 21, 23),
('2025-08-24', 'Venda presencial', 950.00, 'CREDITO', 1, 1),
('2025-08-25', 'Pagamento manutenção equipamentos', 400.00, 'DEBITO', 2, 2),
('2025-08-26', 'Recebimento de clientes especiais', 2200.00, 'CREDITO', 3, 1),
('2025-08-27', 'Compra de materiais de escritório', 180.00, 'DEBITO', 4, 1),
('2025-08-28', 'Pagamento de fornecedor local', 750.00, 'DEBITO', 5, 1),
('2025-08-29', 'Recebimento de vendas online', 1600.00, 'CREDITO', 6, 1),
('2025-08-30', 'Investimento em CDB', 1200.00, 'CREDITO', 7, 1),
('2025-08-31', 'Compra de softwares', 500.00, 'DEBITO', 8, 1),
('2025-09-01', 'Venda de serviços premium', 2500.00, 'CREDITO', 9, 1),
('2025-09-02', 'Pagamento de manutenção', 350.00, 'DEBITO', 10, 1),
('2025-09-03', 'Recebimento de comissão de vendas', 800.00, 'CREDITO', 11, 1),
('2025-09-04', 'Pagamento aluguel escritório', 1000.00, 'DEBITO', 12, 1);
