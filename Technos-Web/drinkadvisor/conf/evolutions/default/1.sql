# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table bar (
  idbar                     varchar(255) not null,
  lon                       varchar(255),
  lat                       varchar(255),
  name                      varchar(255),
  note                      integer,
  mail                      varchar(255),
  adresse                   varchar(255),
  telephone                 varchar(255),
  website                   varchar(255),
  constraint pk_bar primary key (idbar))
;

create table beer_in_bar (
  id                        integer auto_increment not null,
  drinkname                 varchar(255),
  prix                      integer,
  categorie                 varchar(255),
  constraint pk_beer_in_bar primary key (id))
;

create table comment (
  id                        bigint auto_increment not null,
  userid                    varchar(255),
  username                  varchar(255),
  barname                   varchar(255),
  comment                   varchar(255),
  date                      datetime,
  constraint pk_comment primary key (id))
;

create table drink (
  id                        integer auto_increment not null,
  label                     varchar(255),
  description               longtext,
  categorie                 varchar(255),
  drinktype                 varchar(255),
  country                   varchar(255),
  city                      varchar(255),
  constraint pk_drink primary key (id))
;

create table friend (
  idfacebook                varchar(255) not null,
  constraint pk_friend primary key (idfacebook))
;

create table user (
  idfacebook                varchar(255) not null,
  name                      varchar(255),
  username                  varchar(255),
  birthday                  varchar(255),
  gender                    varchar(255),
  email                     varchar(255),
  hometown                  varchar(255),
  location                  varchar(255),
  link                      varchar(255),
  constraint pk_user primary key (idfacebook))
;


create table bar_beer_in_bar (
  bar_idbar                      varchar(255) not null,
  beer_in_bar_id                 integer not null,
  constraint pk_bar_beer_in_bar primary key (bar_idbar, beer_in_bar_id))
;

create table bar_user (
  bar_idbar                      varchar(255) not null,
  user_idfacebook                varchar(255) not null,
  constraint pk_bar_user primary key (bar_idbar, user_idfacebook))
;

create table bar_comment (
  bar_idbar                      varchar(255) not null,
  comment_id                     bigint not null,
  constraint pk_bar_comment primary key (bar_idbar, comment_id))
;

create table user_friend (
  user_idfacebook                varchar(255) not null,
  friend_idfacebook              varchar(255) not null,
  constraint pk_user_friend primary key (user_idfacebook, friend_idfacebook))
;

create table user_bar (
  user_idfacebook                varchar(255) not null,
  bar_idbar                      varchar(255) not null,
  constraint pk_user_bar primary key (user_idfacebook, bar_idbar))
;

create table FRIENDSHIP (
  FRIEND                         varchar(255) not null,
  FRIENDOF                       varchar(255) not null,
  constraint pk_FRIENDSHIP primary key (FRIEND, FRIENDOF))
;



alter table bar_beer_in_bar add constraint fk_bar_beer_in_bar_bar_01 foreign key (bar_idbar) references bar (idbar) on delete restrict on update restrict;

alter table bar_beer_in_bar add constraint fk_bar_beer_in_bar_beer_in_bar_02 foreign key (beer_in_bar_id) references beer_in_bar (id) on delete restrict on update restrict;

alter table bar_user add constraint fk_bar_user_bar_01 foreign key (bar_idbar) references bar (idbar) on delete restrict on update restrict;

alter table bar_user add constraint fk_bar_user_user_02 foreign key (user_idfacebook) references user (idfacebook) on delete restrict on update restrict;

alter table bar_comment add constraint fk_bar_comment_bar_01 foreign key (bar_idbar) references bar (idbar) on delete restrict on update restrict;

alter table bar_comment add constraint fk_bar_comment_comment_02 foreign key (comment_id) references comment (id) on delete restrict on update restrict;

alter table user_friend add constraint fk_user_friend_user_01 foreign key (user_idfacebook) references user (idfacebook) on delete restrict on update restrict;

alter table user_friend add constraint fk_user_friend_friend_02 foreign key (friend_idfacebook) references friend (idfacebook) on delete restrict on update restrict;

alter table user_bar add constraint fk_user_bar_user_01 foreign key (user_idfacebook) references user (idfacebook) on delete restrict on update restrict;

alter table user_bar add constraint fk_user_bar_bar_02 foreign key (bar_idbar) references bar (idbar) on delete restrict on update restrict;

alter table FRIENDSHIP add constraint fk_FRIENDSHIP_user_01 foreign key (FRIEND) references user (idfacebook) on delete restrict on update restrict;

alter table FRIENDSHIP add constraint fk_FRIENDSHIP_user_02 foreign key (FRIENDOF) references user (idfacebook) on delete restrict on update restrict;

# --- !Downs

SET FOREIGN_KEY_CHECKS=0;

drop table bar;

drop table bar_beer_in_bar;

drop table bar_user;

drop table bar_comment;

drop table beer_in_bar;

drop table comment;

drop table drink;

drop table friend;

drop table user;

drop table user_friend;

drop table user_bar;

drop table FRIENDSHIP;

SET FOREIGN_KEY_CHECKS=1;

