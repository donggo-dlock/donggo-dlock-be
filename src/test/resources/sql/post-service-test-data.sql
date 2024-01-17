insert into `users` (`id`,`email`, `address`, `nickname`, `status`, `certification_code`)
values (1,'jeohyoo1229@gmail.com', '서울시 강남구', 'jeohyoo1229', 'ACTIVE', '1234-1234-1234-1234');

insert into `posts` (`id`, `content`, `created_at`, `modified_at`, `user_id`)
values (1, 'helloworld', 1678530673958, 0, 1);

insert into `posts` (`id`, `content`, `created_at`, `modified_at`, `user_id`)
values (2, 'hello, riize', 1678530673584, 0, 1);
