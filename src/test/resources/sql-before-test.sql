delete from heroes;
delete from users;
insert into users (id, password, password_confirm, username, role_id, email, nickname) values ('4028805f7cd234d4017cd23535cd0000', '$2a$10$RHhWqYZNCDAxEfw7YJ75Kegc9ufejXRbbsYR9.dY5BxvAazSgniP6', '', 'maksmerfy', null, 'maksmerfy@chatrpg.ru', 'MaksMerfy');
insert into users (id, password, password_confirm, username, role_id, email, nickname) values ('4028805f7cdb44b4017cdb477e6a0000', '$2a$10$dbZM/RGz5JpJ5UmyVm1GkuLbTBlrxllfR54h98ewg/sSEY5Wg78Yi', '', 'testuser', null, 'testuser@testuser.ru', 'testUser');
insert into heroes (id, user_id, hp, mana, damage, armor, critical_chance, experience) values ('4028805f7cf50dcd017cf50dea000001', '4028805f7cd234d4017cd23535cd0000', 100, 10, 1, 1, 0, 0);
