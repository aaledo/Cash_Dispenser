-- phpMyAdmin SQL Dump
-- version 4.6.4
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 09-10-2018 a las 13:57:14
-- Versión del servidor: 5.7.14
-- Versión de PHP: 5.6.25

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `cajerov1`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `clientes`
--

CREATE TABLE `clientes` (
  `idCiente` varchar(10) COLLATE utf8_bin NOT NULL,
  `nombre` varchar(20) COLLATE utf8_bin NOT NULL,
  `apellidos` varchar(30) COLLATE utf8_bin NOT NULL,
  `direccion` varchar(100) COLLATE utf8_bin NOT NULL,
  `telefono` varchar(20) COLLATE utf8_bin NOT NULL,
  `email` varchar(30) COLLATE utf8_bin NOT NULL,
  `VIP` tinyint(1) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

--
-- Volcado de datos para la tabla `clientes`
--

INSERT INTO `clientes` (`idCiente`, `nombre`, `apellidos`, `direccion`, `telefono`, `email`, `VIP`) VALUES
('1', 'Gonzalo', 'Gonzalo Camarero', 'Mendibide, 6\r\n36500 - Aretxabaleta', '943223344', 'g.g.camarero@yahoo.es', 1);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `cuentas`
--

CREATE TABLE `cuentas` (
  `idCuenta` varchar(20) COLLATE utf8_bin NOT NULL,
  `tipoCuenta` varchar(10) COLLATE utf8_bin NOT NULL,
  `oficina` int(4) NOT NULL,
  `cliente` varchar(10) COLLATE utf8_bin NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

--
-- Volcado de datos para la tabla `cuentas`
--

INSERT INTO `cuentas` (`idCuenta`, `tipoCuenta`, `oficina`, `cliente`) VALUES
('12345678901234567890', 'ahorro', 1000, '1');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `movimientos`
--

CREATE TABLE `movimientos` (
  `idMovimiento` double NOT NULL,
  `fecha` datetime NOT NULL,
  `tipoMovimiento` varchar(10) COLLATE utf8_bin NOT NULL,
  `cuenta` varchar(20) COLLATE utf8_bin NOT NULL,
  `importe` float NOT NULL,
  `descripcion` varchar(30) COLLATE utf8_bin NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

--
-- Volcado de datos para la tabla `movimientos`
--

INSERT INTO `movimientos` (`idMovimiento`, `fecha`, `tipoMovimiento`, `cuenta`, `importe`, `descripcion`) VALUES
(8, '2018-09-02 00:00:00', 'debe', '12345678901234567890', 400, 'Reintegro 400.0'),
(3, '2018-08-25 06:23:24', 'haber', '12345678901234567890', 2000, 'Deposito 1000'),
(7, '2018-09-02 00:00:00', 'debe', '12345678901234567890', 500, 'Reintegro 500.0'),
(6, '2018-09-02 00:00:00', 'debe', '12345678901234567890', 400, 'Reintegro 400.0'),
(5, '2018-09-02 00:00:00', 'debe', '12345678901234567890', 200, 'Reintegro 200.0'),
(4, '2018-09-02 00:00:00', 'debe', '12345678901234567890', 100, 'Reintegro 100.0'),
(9, '2018-09-02 00:00:00', 'debe', '12345678901234567890', 20, 'Reintegro 20.0'),
(10, '2018-09-02 00:00:00', 'debe', '12345678901234567890', 80, 'Reintegro 80.0'),
(11, '2018-09-02 00:00:00', 'debe', '12345678901234567890', 20, 'Reintegro 20.0'),
(12, '2018-09-02 00:00:00', 'debe', '12345678901234567890', 20, 'Reintegro 20.0'),
(13, '2018-09-02 00:00:00', 'debe', '12345678901234567890', 20, 'Reintegro 20.0'),
(14, '2018-09-02 00:00:00', 'debe', '12345678901234567890', 20, 'Reintegro 20.0'),
(15, '2018-09-02 00:00:00', 'debe', '12345678901234567890', 60, 'Reintegro 60.0');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `tarjetas`
--

CREATE TABLE `tarjetas` (
  `idTarjeta` varchar(20) COLLATE utf8_bin NOT NULL,
  `bloqueo` tinyint(1) NOT NULL,
  `PIN` varchar(4) COLLATE utf8_bin NOT NULL,
  `importeMaxAutorizado` int(4) NOT NULL,
  `cuenta` varchar(20) COLLATE utf8_bin NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

--
-- Volcado de datos para la tabla `tarjetas`
--

INSERT INTO `tarjetas` (`idTarjeta`, `bloqueo`, `PIN`, `importeMaxAutorizado`, `cuenta`) VALUES
('11111111111111111111', 0, '2018', 500, '12345678901234567890');

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `clientes`
--
ALTER TABLE `clientes`
  ADD PRIMARY KEY (`idCiente`);

--
-- Indices de la tabla `cuentas`
--
ALTER TABLE `cuentas`
  ADD PRIMARY KEY (`idCuenta`);

--
-- Indices de la tabla `movimientos`
--
ALTER TABLE `movimientos`
  ADD PRIMARY KEY (`cuenta`,`idMovimiento`);

--
-- Indices de la tabla `tarjetas`
--
ALTER TABLE `tarjetas`
  ADD PRIMARY KEY (`idTarjeta`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `movimientos`
--
ALTER TABLE `movimientos`
  MODIFY `idMovimiento` double NOT NULL AUTO_INCREMENT;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
