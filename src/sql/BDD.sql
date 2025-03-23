-- Creación del esquema
drop schema if exists mojarra_esquema cascade;
create schema mojarra_esquema;

-- Creación de la tabla usuario
create table mojarra_esquema.usuario (
    id_usuario serial not null,
    nombre text not null,
    apellido_p text not null,
    apellido_m text not null,
    mail text not null,
    password text not null,
    token text
);

-- Restricciones a la tabla usuario
alter table mojarra_esquema.usuario
add constraint pk_usuario primary key (id_usuario);

alter table mojarra_esquema.usuario
add constraint uq_usuario_mail unique (mail),
add constraint chk_usuario_mail_format check (mail like '%@%');

-- Comentarios para la tabla y columnas
comment on table mojarra_esquema.usuario is 'Tabla que almacena la información de los usuarios en la aplicación.';
comment on column mojarra_esquema.usuario.id_usuario is 'Identificador único del usuario.';
comment on column mojarra_esquema.usuario.mail is 'mail electrónico del usuario. Debe ser único y contener un formato válido.';
comment on column mojarra_esquema.usuario.password is 'password del usuario.';
comment on column mojarra_esquema.usuario.token is 'Token de autenticación del usuario.';

-- Creación de la tabla incidente
create table mojarra_esquema.incidente (
	id_incidente serial not null,
	id_usuario serial not null,
	descripcion text, 
	tipo text not null, 
	estado text not null, 
	longitud float8 not null,
	latitud float8 not null, 
	fecha date not null
); 

-- Restricciones a la tabla incidente 
alter table mojarra_esquema.incidente 
add constraint pk_incidente primary key (id_incidente);

alter table mojarra_esquema.incidente
add constraint incidente_fk foreign key (id_usuario)
references mojarra_esquema.usuario(id_usuario)
on update cascade on delete no action;

--Creación de la tabla imagen_incidente
create table mojarra_esquema.imagen_incidente (
	id_incidente serial not null,
	id_imagen text not null,
	imagen OID not null
); 
-- Restricciones a la tabla imagen_incidente
alter table mojarra_esquema.imagen_incidente
add constraint pk_imagen_incidente primary key (id_imagen);

alter table mojarra_esquema.imagen_incidente
add constraint fk_imagen_incidente foreign key (id_incidente)
references mojarra_esquema.incidente(id_incidente)
on update cascade on delete no action;

-- Creación de la tabla actualización
create table mojarra_esquema.actualizacion (
	id_actualizacion serial not null,
	id_usuario serial not null,
	id_incidente serial not null,
	nuevo_estado text not null, 
	fecha date not null, 
	descripción text
); 

-- Restricciones de la tabla actualización.
alter table mojarra_esquema.actualizacion
add constraint pk_actualizacion primary key (id_actualizacion);

alter table mojarra_esquema.actualizacion
add constraint fk_actualizacion_1 foreign key (id_usuario)
references mojarra_esquema.usuario(id_usuario) 
on update cascade on delete no action;

alter table mojarra_esquema.actualizacion
add constraint fk_actualizacion_2 foreign key (id_incidente)
references mojarra_esquema.incidente(id_incidente)
on update cascade on delete no action;

--Creación de la tabla imagen actualización
create table mojarra_esquema.imagen_actualizacion (
	id_actualizacion serial not null,
	id_imagenAct text not null,
	imagen OID not null
);

-- Restricciones a la tabla imagen_incidente
alter table mojarra_esquema.imagen_actualizacion
add constraint pk_imagen_actualizacion primary key (id_imagenAct);

alter table mojarra_esquema.imagen_actualizacion
add constraint fk_imagen_actualizacion foreign key (id_actualizacion)
references mojarra_esquema.actualizacion(id_actualizacion)
on update cascade on delete no action;

