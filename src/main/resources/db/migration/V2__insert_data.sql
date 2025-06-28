
INSERT INTO usuarios (nome, email, senha) VALUES
('João Silva', 'joao@gmail.com', '$2a$10$7zW1dGRQ4D8gK9qZ5RtZpeRZo4PyxSjk6vNh3oBoZPM8T85iVDH4e'), -- 123456
('Maria Oliveira', 'maria@gmail.com', '$2a$10$7zW1dGRQ4D8gK9qZ5RtZpeRZo4PyxSjk6vNh3oBoZPM8T85iVDH4e'), -- 123456
('Carlos Lima', 'carlos@gmail.com', '$2a$10$7zW1dGRQ4D8gK9qZ5RtZpeRZo4PyxSjk6vNh3oBoZPM8T85iVDH4e'); -- 123456


INSERT INTO produtos (nome, preco, descricao) VALUES
('Pizza Calabresa', 45.90, 'Pizza de calabresa com queijo mussarela e cebola'),
('Hambúrguer Artesanal', 29.90, 'Hambúrguer com carne artesanal, bacon e cheddar'),
('Refrigerante 2L', 9.50, 'Refrigerante cola 2 litros'),
('Batata Frita Média', 15.00, 'Batata frita crocante'),
('Açaí na Tigela', 20.00, 'Açaí com granola, banana e leite condensado');

INSERT INTO pedidos (data_criacao, status, usuario_id) VALUES
(NOW(), 'PENDENTE', 1),
(NOW(), 'ENTREGUE', 2),
(NOW(), 'CANCELADO', 1);

INSERT INTO pedido_produto (pedido_id, produto_id) VALUES
(1, 1), 
(1, 3), 
(2, 2), 
(2, 4), 
(3, 5); 
