
alter table COMMENT add constraint fk_parent_id foreign key(parent_id) references question(id);
