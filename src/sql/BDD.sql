-- Creación del esquema
create schema if not exists mojarra_esquema;

-- Creación de la tabla usuario
create table mojarra_esquema.usuario (
    id serial not null,
    correo varchar(30) not null,
    contrasenia varchar(30) not null,
    token varchar(30)
);

-- Restricciones a la tabla usuario
alter table mojarra_esquema.usuario
add constraint pk_usuario primary key (id);

alter table mojarra_esquema.usuario
add constraint uq_usuario_correo unique (correo),
add constraint chk_usuario_correo_format check (correo like '%@%');

-- Comentarios para la tabla y columnas
comment on table mojarra_esquema.usuario is 'Tabla que almacena la información de los usuarios en la aplicación.';
comment on column mojarra_esquema.usuario.id is 'Identificador único del usuario.';
comment on column mojarra_esquema.usuario.correo is 'Correo electrónico del usuario. Debe ser único y contener un formato válido.';
comment on column mojarra_esquema.usuario.contrasenia is 'Contraseña del usuario.';
comment on column mojarra_esquema.usuario.token is 'Token de autenticación del usuario.';
