INSERT INTO tb_client(name,email) VALUES ('João Silva','joao@gmail.com')
INSERT INTO tb_client(name,email) VALUES ('Marcos Doria','marcos@gmail.com')
INSERT INTO tb_client(name,email) VALUES ('Pedro Costa','pedro@gmail.com')

INSERT INTO tb_product(name,price) VALUES('TV',1220.00)
INSERT INTO tb_product(name,price) VALUES('Notbook',1000.00)
INSERT INTO tb_product(name,price) VALUES('Smartphone',2220.50)
INSERT INTO tb_product(name,price) VALUES('Headfone',220.50)


INSERT INTO tb_order(moment,status,client_id) VALUES ('2022-08-23',1,1)

INSERT INTO tb_order_item(quantity,price,product_id,order_id) VALUES(2,1000,2,1)
INSERT INTO tb_order_item(quantity,price,product_id,order_id) VALUES(1,1220,1,1)
INSERT INTO tb_order_item(quantity,price,product_id,order_id) VALUES(1,2220.50,3,1)