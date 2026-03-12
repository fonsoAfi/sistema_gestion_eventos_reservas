CREATE TABLE administradores (
    id_usuario   INTEGER NOT NULL,
    nivel_acceso VARCHAR(40)
);

ALTER TABLE administradores ADD CONSTRAINT administradores_pk PRIMARY KEY ( id_usuario );

CREATE TABLE asistentes (
    id_usuario       INTEGER NOT NULL,
    puntos_fidelidad INTEGER
);

ALTER TABLE asistentes ADD CONSTRAINT asistentes_pk PRIMARY KEY ( id_usuario );

CREATE TABLE cancelaciones (
    id_cancelacion      INTEGER NOT NULL,
    motivo_cancelacion  VARCHAR(255) NOT NULL,
    fecha_cancelacion   DATE NOT NULL,
    reservas_id_reserva INTEGER NOT NULL
);

CREATE UNIQUE INDEX cancelaciones__idx ON
    cancelaciones (
        reservas_id_reserva
    ASC );

ALTER TABLE cancelaciones ADD CONSTRAINT cancelaciones_pk PRIMARY KEY ( id_cancelacion );

CREATE TABLE categorias (
    id_categoria     INTEGER NOT NULL,
    nombre_categoria VARCHAR(40),
    descripcion      VARCHAR(200)
);

ALTER TABLE categorias ADD CONSTRAINT categorias_pk PRIMARY KEY ( id_categoria );

ALTER TABLE categorias ADD CONSTRAINT categorias_nombre_categoria_un UNIQUE ( nombre_categoria );

CREATE TABLE checkout (
    id_checkout           INTEGER NOT NULL,
    monto_total           DECIMAL(10, 2),
    estado                VARCHAR(20),
    fecha_creacion        DATE,
    fecha_pago            DATE,
    ip_origen             VARCHAR(60),
    moneda                VARCHAR(10),
    observaciones         VARCHAR(60),
    tiempo_expiracion     DATE,
    reservas_id_reserva   INTEGER NOT NULL,
    asistentes_id_usuario INTEGER NOT NULL
);

ALTER TABLE checkout ADD CONSTRAINT checkout_pk PRIMARY KEY ( id_checkout );

CREATE TABLE eventos (
    id_evento                INTEGER NOT NULL,
    titulo_evento            VARCHAR(60),
    descripcion              VARCHAR(500),
    limite_plazas            INTEGER,
    fecha_inicio             DATE,
    fecha_fin                DATE,
    plazas_disponibles       INTEGER,
    precio_base              DECIMAL(10, 2),
    url_imagen               VARCHAR(500),
    organizadores_id_usuario INTEGER NOT NULL,
    ubicaciones_id_ubicacion INTEGER,
    categorias_id_categoria  INTEGER NOT NULL
);

CREATE UNIQUE INDEX eventos__idx ON
    eventos (
        organizadores_id_usuario
    ASC );

ALTER TABLE eventos ADD CONSTRAINT eventos_pk PRIMARY KEY ( id_evento );

CREATE TABLE eventos_productos (
    eventos_id_evento      INTEGER NOT NULL,
    productos_id_productos INTEGER NOT NULL
);

ALTER TABLE eventos_productos ADD CONSTRAINT eventos_productos_pk PRIMARY KEY ( eventos_id_evento,
                                                                                productos_id_productos );

CREATE TABLE historial_reservas (
    id_historial          INTEGER NOT NULL,
    estado_anterior       VARCHAR(40),
    estado_nuevo          VARCHAR(40),
    fecha_cambio          DATE,
    accion                VARCHAR(40),
    motivo                VARCHAR(60),
    ip_origen             VARCHAR(60),
    observaciones         VARCHAR(60),
    asistentes_id_usuario INTEGER NOT NULL,
    reservas_id_reserva   INTEGER NOT NULL
);

ALTER TABLE historial_reservas ADD CONSTRAINT historial_reservas_pk PRIMARY KEY ( id_historial );

CREATE TABLE organizadores (
    id_usuario         INTEGER NOT NULL,
    nombre_organizador VARCHAR(60) NOT NULL,
    tipo               VARCHAR(60),
    limite_eventos     INTEGER NOT NULL
);

ALTER TABLE organizadores ADD CONSTRAINT organizadores_pk PRIMARY KEY ( id_usuario );

ALTER TABLE organizadores ADD CONSTRAINT organizadores__un UNIQUE ( nombre_organizador );

CREATE TABLE productos (
    id_productos               INTEGER NOT NULL,
    nombre_producto            VARCHAR(40),
    precio                     DECIMAL(10, 2),
    stock                      INTEGER,
    modelo                     VARCHAR(40),
    descripcion                VARCHAR(100),
    material                   VARCHAR(40),
    peso                       DECIMAL(5, 2),
    codigo_barras              VARCHAR(60),
    fecha_alta                 DATE,
    estado                     VARCHAR(40),
    descuento                  DECIMAL(5, 2),
    administradores_id_usuario INTEGER NOT NULL
);

ALTER TABLE productos ADD CONSTRAINT productos_pk PRIMARY KEY ( id_productos );

CREATE TABLE productos_reservas (
    productos_id_productos INTEGER NOT NULL,
    reservas_id_reserva    INTEGER NOT NULL
);

ALTER TABLE productos_reservas ADD CONSTRAINT productos_reservas_pk PRIMARY KEY ( productos_id_productos,
                                                                                  reservas_id_reserva );

CREATE TABLE reembolsos (
    id_reembolso          INTEGER NOT NULL,
    cantidad              DECIMAL(6, 2),
    moneda                VARCHAR(10),
    estado                VARCHAR(40),
    fecha_solicitud       DATE,
    fecha_reembolso       DATE,
    reservas_id_reserva   INTEGER NOT NULL,
    asistentes_id_usuario INTEGER NOT NULL
);

CREATE UNIQUE INDEX reembolsos__idx ON
    reembolsos (
        reservas_id_reserva
    ASC );

ALTER TABLE reembolsos ADD CONSTRAINT reembolsos_pk PRIMARY KEY ( id_reembolso );

CREATE TABLE reservas (
    id_reserva            INTEGER NOT NULL,
    titular               VARCHAR(40),
    plazas_reservadas     INTEGER,
    precio_base           DECIMAL(10, 2),
    subtotal              DECIMAL(10, 2),
    precio_total          DECIMAL(10, 2),
    gastos_gestion        DECIMAL(4, 2),
    fecha_reserva         DATE,
    asistentes_id_usuario INTEGER NOT NULL,
    eventos_id_evento     INTEGER NOT NULL
);

CREATE UNIQUE INDEX reservas__idx ON
    reservas (
        eventos_id_evento
    ASC );

ALTER TABLE reservas ADD CONSTRAINT reservas_pk PRIMARY KEY ( id_reserva );

CREATE TABLE tickets (
    id_ticket           INTEGER NOT NULL,
    codigo_qr           VARCHAR(40),
    fecha_compra        DATE,
    tipo_ticket         VARCHAR(40),
    precio              DECIMAL(10, 2),
    estado              VARCHAR(40),
    canal_venta         VARCHAR(40),
    eventos_id_evento   INTEGER NOT NULL,
    reservas_id_reserva INTEGER NOT NULL
);

CREATE UNIQUE INDEX tickets__idx ON
    tickets (
        eventos_id_evento
    ASC );

CREATE UNIQUE INDEX tickets__idxv1 ON
    tickets (
        reservas_id_reserva
    ASC );

ALTER TABLE tickets ADD CONSTRAINT tickets_pk PRIMARY KEY ( id_ticket );

CREATE TABLE tickets_reembolsos (
    tickets_id_ticket       INTEGER NOT NULL,
    reembolsos_id_reembolso INTEGER NOT NULL
);

ALTER TABLE tickets_reembolsos ADD CONSTRAINT tickets_reembolsos_pk PRIMARY KEY ( tickets_id_ticket,
                                                                                  reembolsos_id_reembolso );

CREATE TABLE ubicaciones (
    id_ubicacion  INTEGER NOT NULL,
    pais          VARCHAR(40),
    localidad     VARCHAR(40),
    calle         VARCHAR(100),
    codigo_postal CHAR(9),
    nombre_sala   VARCHAR(40),
    capacidad     INTEGER
);

ALTER TABLE ubicaciones ADD CONSTRAINT ubicaciones_pk PRIMARY KEY ( id_ubicacion );

CREATE TABLE usuarios (
    id_usuario       INTEGER NOT NULL,
    nombre_real      VARCHAR(40) NOT NULL,
    nombre_perfil    VARCHAR(40) NOT NULL,
    apellido1        VARCHAR(40),
    apellido2        VARCHAR(40),
    mail             VARCHAR(40) NOT NULL,
    telefono         VARCHAR(60),
    fecha_nacimiento DATE,
    pais             VARCHAR(40),
    clave            VARCHAR(255),
    estado_cuenta    VARCHAR(40),
    ultimo_acceso    DATE NOT NULL,
    rol_usuario      VARCHAR(40) NOT NULL
);

ALTER TABLE usuarios ADD CONSTRAINT usuarios_pk PRIMARY KEY ( id_usuario );

ALTER TABLE usuarios ADD CONSTRAINT usuarios_nombre_perfil_un UNIQUE ( nombre_perfil );

ALTER TABLE usuarios ADD CONSTRAINT usuarios_mail_un UNIQUE ( mail );

CREATE TABLE ventas (
    id_venta              INTEGER NOT NULL,
    fecha_venta           DATE,
    estado                VARCHAR(20),
    monto_total           DECIMAL(10, 2),
    eventos_id_evento     INTEGER NOT NULL,
    asistentes_id_usuario INTEGER NOT NULL
);

CREATE UNIQUE INDEX ventas__idx ON
    ventas (
        asistentes_id_usuario
    ASC );

CREATE UNIQUE INDEX ventas__idxv1 ON
    ventas (
        eventos_id_evento
    ASC );

ALTER TABLE ventas ADD CONSTRAINT ventas_pk PRIMARY KEY ( id_venta );

ALTER TABLE administradores
    ADD CONSTRAINT administradores_usuarios_fk FOREIGN KEY ( id_usuario )
        REFERENCES usuarios ( id_usuario );

ALTER TABLE asistentes
    ADD CONSTRAINT asistentes_usuarios_fk FOREIGN KEY ( id_usuario )
        REFERENCES usuarios ( id_usuario );

ALTER TABLE cancelaciones
    ADD CONSTRAINT cancelaciones_reservas_fk FOREIGN KEY ( reservas_id_reserva )
        REFERENCES reservas ( id_reserva );

ALTER TABLE checkout
    ADD CONSTRAINT checkout_asistentes_fk FOREIGN KEY ( asistentes_id_usuario )
        REFERENCES asistentes ( id_usuario );

ALTER TABLE checkout
    ADD CONSTRAINT checkout_reservas_fk FOREIGN KEY ( reservas_id_reserva )
        REFERENCES reservas ( id_reserva );

ALTER TABLE eventos
    ADD CONSTRAINT eventos_categorias_fk FOREIGN KEY ( categorias_id_categoria )
        REFERENCES categorias ( id_categoria );

ALTER TABLE eventos
    ADD CONSTRAINT eventos_organizadores_fk FOREIGN KEY ( organizadores_id_usuario )
        REFERENCES organizadores ( id_usuario );

ALTER TABLE eventos_productos
    ADD CONSTRAINT eventos_productos_eventos_fk FOREIGN KEY ( eventos_id_evento )
        REFERENCES eventos ( id_evento );

ALTER TABLE eventos_productos
    ADD CONSTRAINT eventos_productos_productos_fk FOREIGN KEY ( productos_id_productos )
        REFERENCES productos ( id_productos );

ALTER TABLE eventos
    ADD CONSTRAINT eventos_ubicaciones_fk FOREIGN KEY ( ubicaciones_id_ubicacion )
        REFERENCES ubicaciones ( id_ubicacion );

--  ERROR: FK name length exceeds maximum allowed length(30) 
ALTER TABLE historial_reservas
    ADD CONSTRAINT historial_reservas_asistentes_fk FOREIGN KEY ( asistentes_id_usuario )
        REFERENCES asistentes ( id_usuario );

ALTER TABLE historial_reservas
    ADD CONSTRAINT historial_reservas_reservas_fk FOREIGN KEY ( reservas_id_reserva )
        REFERENCES reservas ( id_reserva );

ALTER TABLE organizadores
    ADD CONSTRAINT organizadores_usuarios_fk FOREIGN KEY ( id_usuario )
        REFERENCES usuarios ( id_usuario );

ALTER TABLE productos
    ADD CONSTRAINT productos_administradores_fk FOREIGN KEY ( administradores_id_usuario )
        REFERENCES administradores ( id_usuario );

--  ERROR: FK name length exceeds maximum allowed length(30) 
ALTER TABLE productos_reservas
    ADD CONSTRAINT productos_reservas_productos_fk FOREIGN KEY ( productos_id_productos )
        REFERENCES productos ( id_productos );

ALTER TABLE productos_reservas
    ADD CONSTRAINT productos_reservas_reservas_fk FOREIGN KEY ( reservas_id_reserva )
        REFERENCES reservas ( id_reserva );

ALTER TABLE reembolsos
    ADD CONSTRAINT reembolsos_asistentes_fk FOREIGN KEY ( asistentes_id_usuario )
        REFERENCES asistentes ( id_usuario );

ALTER TABLE reembolsos
    ADD CONSTRAINT reembolsos_reservas_fk FOREIGN KEY ( reservas_id_reserva )
        REFERENCES reservas ( id_reserva );

ALTER TABLE reservas
    ADD CONSTRAINT reservas_asistentes_fk FOREIGN KEY ( asistentes_id_usuario )
        REFERENCES asistentes ( id_usuario );

ALTER TABLE reservas
    ADD CONSTRAINT reservas_eventos_fk FOREIGN KEY ( eventos_id_evento )
        REFERENCES eventos ( id_evento );

ALTER TABLE tickets
    ADD CONSTRAINT tickets_eventos_fk FOREIGN KEY ( eventos_id_evento )
        REFERENCES eventos ( id_evento );

--  ERROR: FK name length exceeds maximum allowed length(30) 
ALTER TABLE tickets_reembolsos
    ADD CONSTRAINT tickets_reembolsos_reembolsos_fk FOREIGN KEY ( reembolsos_id_reembolso )
        REFERENCES reembolsos ( id_reembolso );

ALTER TABLE tickets_reembolsos
    ADD CONSTRAINT tickets_reembolsos_tickets_fk FOREIGN KEY ( tickets_id_ticket )
        REFERENCES tickets ( id_ticket );

ALTER TABLE tickets
    ADD CONSTRAINT tickets_reservas_fk FOREIGN KEY ( reservas_id_reserva )
        REFERENCES reservas ( id_reserva );

ALTER TABLE ventas
    ADD CONSTRAINT ventas_asistentes_fk FOREIGN KEY ( asistentes_id_usuario )
        REFERENCES asistentes ( id_usuario );

ALTER TABLE ventas
    ADD CONSTRAINT ventas_eventos_fk FOREIGN KEY ( eventos_id_evento )
        REFERENCES eventos ( id_evento );
