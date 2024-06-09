insert into test.public.user_info_ingredient
select a_id.id, u_id.id
from (select id from test.public.ingredient where name = 'Лондонский сухой джин') as a_id,
     (select ui.id
      from test.public.custom_user
               left join user_info as ui on ui.user_id = custom_user.id
      where name = 'TestUserName') as u_id;

insert into test.public.user_info_ingredient
select a_id.id, u_id.id
from (select id from test.public.ingredient where name = 'Лаймовый кордиал') as a_id,
     (select ui.id
      from test.public.custom_user
               left join user_info as ui on ui.user_id = custom_user.id
      where name = 'TestUserName') as u_id;

insert into test.public.user_info_ingredient
select a_id.id, u_id.id
from (select id from test.public.ingredient where name = 'Лед в кубиках') as a_id,
     (select ui.id
      from test.public.custom_user
               left join user_info as ui on ui.user_id = custom_user.id
      where name = 'TestUserName') as u_id;
