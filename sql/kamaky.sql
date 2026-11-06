/*
  Kamaky Products - Script de base de datos
  Se ejecuta completo en MySQL Workbench con un usuario administrador (root).
  Cada integrante agrega sus tablas y datos de prueba en la sección indicada.
*/

-- ================== ADMINISTRACIÓN ==================
drop database if exists kamaky;
drop user if exists 'usuario_kamaky'@'%';

create database kamaky
  default character set utf8mb4
  default collate utf8mb4_unicode_ci;

create user 'usuario_kamaky'@'%' identified by 'Kamaky_2026.';
grant all privileges on kamaky.* to 'usuario_kamaky'@'%';
flush privileges;

use kamaky;

-- ================== TABLAS COMPARTIDAS ==================
create table cliente (
  id_cliente int not null auto_increment,
  nombre varchar(80) not null,
  telefono varchar(20) not null,
  correo varchar(100) not null,
  fecha_creacion timestamp default current_timestamp,
  primary key (id_cliente),
  unique (correo)
) engine = InnoDB;

-- ================== KATHERINE: usuario, informacion_negocio ==================

create table usuario (
    id_usuario int not null auto_increment,
    nombre varchar(80) not null,
    correo varchar(100) not null,
    contrasena varchar(100) not null,
    rol varchar(20) not null,
    primary key (id_usuario),
    unique (correo)
);

create table informacion_negocio (
    id_informacion int not null auto_increment,
    nombre varchar(100) not null,
    descripcion text,
    telefono varchar(20),
    correo varchar(100),
    ubicacion varchar(150),
    whatsapp varchar(150),
    instagram varchar(150),
    facebook varchar(150),
    primary key (id_informacion)
);

insert into informacion_negocio
(nombre, descripcion, telefono, correo, ubicacion,
 whatsapp, instagram, facebook)
values
('Kamaky Products',
 'Reposteria artesanal para toda ocasion',
 '8888-1234',
 'contacto@kamaky.com',
 'Costa Rica',
 '8888-8888',
 'https://instagram.com/',
 'https://facebook.com/');



-- ================== MINOR: categoria, producto ==================

create table categoria (
  id_categoria int not null auto_increment,
  nombre varchar(60) not null,
  descripcion varchar(255),
  activa boolean not null default true,
  primary key (id_categoria),
  unique key uk_categoria_nombre (nombre)
) engine = InnoDB;

create table producto (
  id_producto int not null auto_increment,
  nombre varchar(100) not null,
  descripcion varchar(500),
  precio decimal(10,2) not null,
  imagen varchar(1024),
  disponible boolean not null default true,
  id_categoria int not null,
  primary key (id_producto),
  key idx_producto_categoria (id_categoria),
  constraint fk_producto_categoria
    foreign key (id_categoria) references categoria (id_categoria)
    on update cascade
    on delete restrict
) engine = InnoDB;


insert into categoria (nombre, descripcion, activa) values
('Pasteles', 'Pasteles para celebraciones y ocasiones especiales', true),
('Postres', 'Postres preparados artesanalmente', true),
('Galletas', 'Galletas y bocadillos dulces', true);

insert into producto
  (nombre, descripcion, precio, imagen, disponible, id_categoria)
values
('Pastel de chocolate', 'Pastel de chocolate para compartir', 13500.00, null, true, 1),
('Red velvet', 'Pastel red velvet con cubierta cremosa', 15500.00, null, true, 1),
('Flan casero', 'Flan elaborado artesanalmente', 2800.00, null, true, 2),
('Galleta con chispas', 'Galleta con chispas de chocolate', 1200.00, null, true, 3);



-- ================== ROBERTO: pedido, detalle_pedido ==================
create table pedido (
    id_pedido int not null auto_increment,
    fecha timestamp default current_timestamp,
    estado varchar(20) not null default 'Pendiente',
    total decimal(10,2) not null,
    id_cliente int not null,
    primary key (id_pedido),
    foreign key (id_cliente) references cliente(id_cliente)
);

create table detalle_pedido (
    id_detalle int not null auto_increment,
    id_pedido int not null,
    id_producto int not null,
    cantidad int not null,
    precio_unitario decimal(10,2) not null,
    subtotal decimal(10,2) not null,
    primary key (id_detalle),
    foreign key (id_pedido) references pedido(id_pedido),
    foreign key (id_producto) references producto(id_producto)
);

-- ================== LUIS: promocion, solicitud_personalizada ==================
create table promocion (
  id_promocion int not null auto_increment,
  titulo varchar(60) not null,
  descripcion varchar(255) not null,
  porcentaje_descuento int not null check (porcentaje_descuento between 1 and 100),
  fecha_inicio date not null,
  fecha_fin date not null,
  activa boolean not null default true,
  primary key (id_promocion),
  check (fecha_fin >= fecha_inicio)
) engine = InnoDB;

create table solicitud_personalizada (
  id_solicitud int not null auto_increment,
  id_cliente int not null,
  descripcion varchar(500) not null,
  cantidad int not null check (cantidad > 0),
  fecha_entrega date not null,
  hora_entrega time not null,
  preferencias varchar(255),
  estado varchar(20) not null default 'Pendiente',
  fecha_solicitud timestamp default current_timestamp,
  primary key (id_solicitud),
  foreign key (id_cliente) references cliente(id_cliente)
) engine = InnoDB;


-- ================== DATOS DE PRUEBA ==================
insert into cliente (nombre, telefono, correo) values
('María Fernández', '8888-1234', 'maria.fernandez@correo.com'),
('Andrés Solís', '8777-5678', 'andres.solis@correo.com'),
('Daniela Mora', '8666-9012', 'daniela.mora@correo.com');

insert into promocion (titulo, descripcion, porcentaje_descuento, fecha_inicio, fecha_fin, activa) values
('Mes del chocolate', 'Todos los queques de chocolate con descuento durante el mes.', 15, curdate(), date_add(curdate(), interval 30 day), true),
('Cupcakes 2x1 los viernes', 'Lleve dos cupcakes del mismo sabor y pague uno.', 50, curdate(), date_add(curdate(), interval 60 day), true),
('Pavlovas de temporada', 'Descuento en pavlovas con frutas de temporada.', 10, date_add(curdate(), interval 10 day), date_add(curdate(), interval 40 day), true),
('Navidad dulce', 'Promoción de galletas navideñas (inactiva hasta diciembre).', 20, '2026-12-01', '2026-12-31', false);

insert into solicitud_personalizada (id_cliente, descripcion, cantidad, fecha_entrega, hora_entrega, preferencias) values
(1, 'Pastel de cumpleaños de dos pisos con tema de flores y el nombre Sofía.', 1, date_add(curdate(), interval 7 day), '15:00:00', 'Relleno de fresa, sin nueces'),
(2, 'Caja de 12 cupcakes decorados para un baby shower en tonos celeste.', 12, date_add(curdate(), interval 10 day), '10:30:00', 'Mitad vainilla, mitad chocolate');
