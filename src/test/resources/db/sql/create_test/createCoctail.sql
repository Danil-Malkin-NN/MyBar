insert into test.public.cocktail (strength, volume, description, name)
VALUES (0, 0,
        'Вам нравятся простые классические миксы? Тогда попробуйте этот крепкий кислый, цитрусовый и сухой коктейль на основе джина.',
        'Гимлет');

insert into test.public.ingredient (description, name)
VALUES ('Крепкий алкоголь изготавливается в вертикальных перегонных кубах, зерновой спирт настаивают в течение суток на можжевельнике и десятке других трав и специй, а затем дистиллируют и разливают в бутылки.',
        'Лондонский сухой джин'),
       ('Старинный цитрусовый сироп производят методом пастеризации сока лайма, смешанного с сахарным сиропом, винным камнем и водой.',
        'Лаймовый кордиал'),
       ('Верхний слой кожуры лайма, который срезают специальным острым ножом. Содержит эфирные масла, определяющие аромат. Цедра может быть срезана как с части, так и со всего лайма целиком, мелко натерта и даже закручена в спираль, называемую твистом.',
        'Лаймовая цедра'),
       ('При производстве этого незаменимого ингредиента основное внимание уделяют чистоте воды и скорости заморозки. Чем медленнее замораживается вода, тем прозрачнее получается продукт. Идеальные образцы не имеют полостей и медленнее тают в бокале.',
        'Лед в кубиках')
on conflict DO NOTHING;

insert into test.public.ingredient_and_count (count, ingredient_id, unit_type)
    (select 60, i.id, 1 from test.public.ingredient as i where name = 'Лондонский сухой джин');

insert into test.public.ingredient_and_count (count, ingredient_id, unit_type)
    (select 30, i.id, 1 from test.public.ingredient as i where name = 'Лаймовый кордиал');

insert into test.public.ingredient_and_count (count, ingredient_id, unit_type)
    (select 1, i.id, 2 from test.public.ingredient as i where name = 'Лаймовая цедра');

insert into test.public.ingredient_and_count (count, ingredient_id, unit_type)
    (select 200, i.id, 0 from test.public.ingredient as i where name = 'Лед в кубиках');

insert into cocktail_ingredients (cocktail_id, ingredients_id)
select ci.id, iaci.id
from (select c.id from test.public.cocktail as c where name = 'Гимлет') as ci,
     (select id
      from test.public.ingredient_and_count
      where unit_type = 1
        and count = 60
        and ingredient_id in
            (select i.id from test.public.ingredient as i where name = 'Лондонский сухой джин')) as iaci;

insert into cocktail_ingredients (cocktail_id, ingredients_id)
select ci.id, iaci.id
from (select c.id from test.public.cocktail as c where name = 'Гимлет') as ci,
     (select id
      from test.public.ingredient_and_count
      where unit_type = 1
        and count = 30
        and ingredient_id in
            (select i.id from test.public.ingredient as i where name = 'Лаймовый кордиал')) as iaci;

insert into cocktail_ingredients (cocktail_id, ingredients_id)
select ci.id, iaci.id
from (select c.id from test.public.cocktail as c where name = 'Гимлет') as ci,
     (select id
      from test.public.ingredient_and_count
      where unit_type = 2
        and count = 1
        and ingredient_id in
            (select i.id from test.public.ingredient as i where name = 'Лаймовая цедра')) as iaci;
