\c transacaodb;

alter table if exists tb_transaction drop constraint if exists FKp7mlynhxntm25ine7oed1bq5y;

drop table if exists tb_operation_type cascade;
drop table if exists tb_transaction cascade;

drop sequence if exists tb_operation_type_seq;
drop sequence if exists tb_transaction_seq;

create sequence tb_operation_type_seq start with 1 increment by 50;
create sequence tb_transaction_seq start with 1 increment by 50;

create table tb_operation_type (operation_id SERIAL not null, description varchar(255), transaction_type varchar(255) check (transaction_type in ('CREDIT','DEBIT')), primary key (operation_id));
create table tb_transaction (amount numeric(38,2), account_id bigint, created_at timestamp(6), operation_id SERIAL, transaction_id SERIAL not null, primary key (transaction_id));

alter table if exists tb_transaction add constraint FKp7mlynhxntm25ine7oed1bq5y foreign key (operation_id) references tb_operation_type;

INSERT INTO tb_operation_type(operation_id, description, transaction_type)
VALUES 
(1, 'PURCHASE', 'DEBIT'),
(2, 'INSTALLMENT PURCHASE', 'DEBIT'),
(3, 'WITHDRAWAL', 'DEBIT'),
(4, 'PAYMENT', 'CREDIT');

\c accountdb;

drop table if exists tb_accounts cascade;

drop sequence if exists tb_accounts_seq;

create sequence tb_accounts_seq start with 1 increment by 50;

create table tb_accounts (account_id SERIAL not null, document_number VARCHAR(255), primary key (account_id));