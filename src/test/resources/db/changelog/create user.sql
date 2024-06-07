insert into test.public.authorities(authorities)
values ('test');

insert into test.public.custom_user (enabled, name, password)
values (true, 'TestUserName', '$2a$10$JFKqbthOE9zNj4sd6W.ASuxTyGNhYouujy/qSKZ5MWHjnV2OdBx1C');

insert into test.public.user_info(user_id, email, first_name, last_name)
values (3, 'test.test@test.ru', 'TestName', 'TestLastName');

insert into test.public.custom_user_authorities(authorities_id, user_id)
select a_id.id, u_id.id
from (select id
      from test.public.authorities
      limit 1) as a_id,
    (select id from test.public.custom_user where name = 'TestName') as u_id;
