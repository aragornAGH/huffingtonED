/*10 najpopularniejszych autorow postow*/
select count(*) as post_num, a.name as Name from public.posts as p join public.authors as a on (a.id=p.author_id) group by p.author_id, a.name order by count(*) desc limit 10;

/*posty, ktore nie maja tytulu lub tresci*/
select * from public.posts as p where p.title = '' or p.title is null or p.content = '' or p.content is null;

/*10 najpopularniejszych kategorii w postach*/
select count(*) as post_num, c.category_name as Name from public.posts as p join public.category as c on (c.id=p.category_id) group by p.category_id, c.category_name order by count(*) desc limit 10;

/*10 najpopularniejszych tagow w postach*/
select count(*) as post_num, t.name as Name from public.posts as p join public.post_tags as pt on (pt.post_id=p.id) join public.tags as t on (t.id=pt.tag_id) group by t.id, t.name order by count(*) desc limit 10;

/*10 najpopularniejszych topicow w postach*/
select count(*) as post_num, t.keywords as Name from public.posts as p join public.post_topic as pt on (pt.post_id=p.id) join public.topics as t on (t.id=pt.topic_id) group by t.id, t.keywords order by count(*) desc limit 10;

/*ilosc postow dla kazdej z kategorii*/
select c.category_name as Name, count(p.id) from category as c join public.posts as p on (c.id=p.category_id) group by c.category_name;

/*10 najpopularniejszych autorow komentarzy*/
select count(*) as post_num, a.name as Name from public.comments as c join public.authors as a on (a.id=c.author_id) group by c.author_id, a.name order by count(*) desc limit 10;

/*10 postow z najwieksza liczba komentarzy*/
select count(*) as post_num, p.title as Name from public.comments as c join public.posts as p on (p.id=c.post_id) group by c.post_id, p.title order by count(*) desc limit 10;

/*10 komentarzy, ktore maja najwiecej odpowiedzi*/
select count(*) as post_num, ca.content as Name from public.comments as c join public.comments as ca on (ca.id=c.parent_comment) group by c.parent_comment, ca.content order by count(*) desc limit 10;

/*10 najpopularniejszych topicow w komentarzach*/
select count(*) as post_num, t.keywords as Name from public.comments as c join public.comment_topic as ct on (ct.comment_id=c.id) join public.topics as t on (t.id=ct.topic_id) group by t.id, t.keywords order by count(*) desc limit 10;