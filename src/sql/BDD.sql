-- Creación del esquema
drop schema if exists mojarra_esquema cascade;
create schema mojarra_esquema;

-- Creación de la tabla usuario
create table mojarra_esquema.usuario (
    idUsuario serial not null,
    nombre text not null,
    apellidoP text not null,
    apellidoM text not null,
    correo text not null,
    contrasenia text not null,
    token text
);

-- Restricciones a la tabla usuario
alter table mojarra_esquema.usuario
add constraint pk_usuario primary key (idUsuario);

alter table mojarra_esquema.usuario
add constraint uq_usuario_correo unique (correo),
add constraint chk_usuario_correo_format check (correo like '%@%');

-- Comentarios para la tabla y columnas
comment on table mojarra_esquema.usuario is 'Tabla que almacena la información de los usuarios en la aplicación.';
comment on column mojarra_esquema.usuario.idUsuario is 'Identificador único del usuario.';
comment on column mojarra_esquema.usuario.correo is 'Correo electrónico del usuario. Debe ser único y contener un formato válido.';
comment on column mojarra_esquema.usuario.contrasenia is 'Contraseña del usuario.';
comment on column mojarra_esquema.usuario.token is 'Token de autenticación del usuario.';

-- Creación de la tabla incidente
create table mojarra_esquema.incidente (
	IDIncidente serial not null,
	IDUsuario serial not null,
	descripcion text, 
	tipo text not null, 
	estado text not null, 
	longitud float8 not null,
	latitud float8 not null, 
	fecha date not null
); 

-- Restricciones a la tabla incidente 
alter table mojarra_esquema.incidente 
add constraint pk_incidente primary key (IDIncidente);

alter table mojarra_esquema.incidente
add constraint incidente_fk foreign key (IDUsuario)
references mojarra_esquema.usuario(IDUsuario) 
on update cascade on delete no action;

--Creación de la tabla imagen_incidente
create table mojarra_esquema.imagen_incidente (
	IDIncidente serial not null, 
	IDImagen text not null,
	Imagen OID not null
); 
-- Restricciones a la tabla imagen_incidente
alter table mojarra_esquema.imagen_incidente
add constraint pk_imagen_incidente primary key (IDImagen);

alter table mojarra_esquema.imagen_incidente
add constraint fk_imagen_incidente foreign key (IDIncidente)
references mojarra_esquema.incidente(IDIncidente)
on update cascade on delete no action;

-- Creación de la tabla actualización
create table mojarra_esquema.actualizacion (
	IDActualizacion serial not null,
	IDUsuario serial not null,
	IDIncidente serial not null,
	NuevoEstado text not null, 
	fecha date not null, 
	descripción text
); 

-- Restricciones de la tabla actualización.
alter table mojarra_esquema.actualizacion
add constraint pk_actualizacion primary key (IDActualizacion);

alter table mojarra_esquema.actualizacion
add constraint fk_actualizacion_1 foreign key (IDUsuario)
references mojarra_esquema.usuario(IDUsuario) 
on update cascade on delete no action;

alter table mojarra_esquema.actualizacion
add constraint fk_actualizacion_2 foreign key (IDIncidente)
references mojarra_esquema.incidente(IDIncidente) 
on update cascade on delete no action;

--Creación de la tabla imagen actualización
create table mojarra_esquema.imagen_actualizacion (
	IDActualizacion serial not null, 
	IDImagenAct text not null,
	Imagen OID not null
);

-- Restricciones a la tabla imagen_incidente
alter table mojarra_esquema.imagen_actualizacion
add constraint pk_imagen_actualizacion primary key (IDImagenAct);

alter table mojarra_esquema.imagen_actualizacion
add constraint fk_imagen_actualizacion foreign key (IDActualizacion)
references mojarra_esquema.actualizacion(IDActualizacion)
on update cascade on delete no action;

