
update public.posts as p set content = regexp_replace(content, '(?i)<(?!(/?(a)))[^>]*>', '', 'g');

select content from public.posts as p where p.id=751040;