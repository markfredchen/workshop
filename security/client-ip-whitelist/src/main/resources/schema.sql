-- used in tests that use HSQL
create table oauth_client_detail (
  client_id                      varchar(100) primary key,
  client_secret                  varchar(100) not null,
  access_token_validity_seconds  int,
  refresh_token_validity_seconds int,
  resource_ids                   varchar(256),
  scopes                         varchar(256),
  authorized_grant_types         varchar(256),
  registered_redirect_uri        varchar(256),
  authorities                    varchar(256),
  auto_approve_scopes            varchar(256),
  additional_information         varchar(4096),
  ip_whitelist                   varchar(100)
);


create table oauth_client_token (
  token_id          VARCHAR(256),
  token             LONGVARBINARY,
  authentication_id VARCHAR(256) PRIMARY KEY,
  user_name         VARCHAR(256),
  client_id         VARCHAR(256)

);

create table oauth_access_token (
  token_id          VARCHAR(256),
  token LONGVARBINARY,
  authentication_id VARCHAR(256) PRIMARY KEY,
  user_name         VARCHAR(256),
  client_id         VARCHAR(256),
  authentication LONGVARBINARY,
  refresh_token     VARCHAR(256)

);

create table oauth_refresh_token (
  token_id VARCHAR(256),
  token LONGVARBINARY,
  authentication LONGVARBINARY

);

create table oauth_code (
  code VARCHAR(256),
  authentication LONGVARBINARY
);

create table oauth_approvals (
  userId         VARCHAR(256),
  clientId       VARCHAR(256),
  scope          VARCHAR(256),
  status         VARCHAR(10),
  expiresAt      TIMESTAMP,
  lastModifiedAt TIMESTAMP
);

create table org_user (
  id       int primary key,
  username varchar(100),
  password varchar(100)
);

create table org_authority (
  id             int primary key,
  authority_name varchar(100)
);

create table org_user_authority (
  user_id      int,
  authority_id int
);

insert into org_user values (1, 'test', 'test');
insert into org_authority values (1, 'ROLE_USER');
insert into org_user_authority values (1, 1);
insert into oauth_client_detail values
  ('demo_client', '$2a$05$puS11vuYRXt4zconpANJgethRIebuu2LAeaIb7UPIX4CryMrS1hTa', 86400, 86400, NULL, 'read', 'password,client_credentials', NULL, 'ROLE_API', NULL,
                  NULL, '127.0.0.1');
