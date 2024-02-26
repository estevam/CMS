
INSERT INTO user_blog (username, password,created) VALUES ('estevam@gmail.com','$2a$10$wAUr3pLHVlCKUBp2nv8aHe0GkX.YwDcErceB5v3GaMglLUtqGXR7u',CURRENT_TIMESTAMP());
INSERT INTO user_blog (username, password,created) VALUES ('ilse@gmail.com' ,'$2a$10$CCabgPB.KlDvBqE7i0.5kuWa4Y1YTlP.1tAXlfMolDXT8ZtGC.RpG', CURRENT_TIMESTAMP());
INSERT INTO user_blog (username, password,created) VALUES ('clara@gmail.com','$2a$10$7lAVoYnQVgqmQySWL4cyauWpq.tVr.3lzJUZGarO34Jhypwh.3/ge',CURRENT_TIMESTAMP());
INSERT INTO user_blog (username, password,created) VALUES ('isabella@gmail.com','$2a$10$hI/25ZBLc/aulJKtGOu.KeZvNbAuv1/fiJx2cjxTfY5.wZsOdbZgO',CURRENT_TIMESTAMP());

INSERT INTO user_role (created, role,id_user) VALUES (CURRENT_TIMESTAMP(),'ROLE_ADMIN',1);
INSERT INTO user_role (created, role,id_user) VALUES (CURRENT_TIMESTAMP(),'ROLE_EDITOR',2);
INSERT INTO user_role (created, role,id_user) VALUES (CURRENT_TIMESTAMP(),'ROLE_MANAGER',3);
INSERT INTO user_role (created, role,id_user) VALUES (CURRENT_TIMESTAMP(),'ROLE_USER',4);

INSERT INTO article (created, status, text,title, id_user) VALUES (CURRENT_TIMESTAMP(),1,'A guide to method-level security using the Spring Security framework.','Introduction to Spring Method Security',1);
INSERT INTO article (created, status, text,title, id_user) VALUES (CURRENT_TIMESTAMP(),1,'Java is a high-level, class-based, object-oriented programming language that is designed to have as few implementation dependencies as possible.','Introduction to Java',2);
INSERT INTO article (created, status, text,title, id_user) VALUES (CURRENT_TIMESTAMP(),1,'C++ is a powerful general-purpose programming language.','C++ Introduction',3);
INSERT INTO article (created, status, text,title, id_user) VALUES (CURRENT_TIMESTAMP(),1,'Hibernate ORM (or simply Hibernate) is an object–relational mapping tool for the Java programming language.','Hibernate ORM',4);

INSERT INTO user_blog_article (user_blog_id_user,article_id_articles) VALUES (1,1);
INSERT INTO user_blog_article (user_blog_id_user,article_id_articles) VALUES (2,2);
INSERT INTO user_blog_article (user_blog_id_user,article_id_articles) VALUES (3,3);
INSERT INTO user_blog_article (user_blog_id_user,article_id_articles) VALUES (4,4);
