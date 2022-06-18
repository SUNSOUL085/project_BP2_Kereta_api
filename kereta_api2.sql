-- phpMyAdmin SQL Dump
-- version 5.1.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Waktu pembuatan: 18 Jun 2022 pada 06.24
-- Versi server: 10.4.22-MariaDB
-- Versi PHP: 8.0.15

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `kereta_api2`
--

-- --------------------------------------------------------

--
-- Struktur dari tabel `gerbong`
--

CREATE TABLE `gerbong` (
  `id_gerbong` char(10) NOT NULL,
  `kelas_gerbong` enum('EKSEKUTIF','BISNIS','EKONOMI') NOT NULL,
  `kapasitas` int(11) NOT NULL,
  `harga` int(11) NOT NULL,
  `id_kereta` char(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data untuk tabel `gerbong`
--

INSERT INTO `gerbong` (`id_gerbong`, `kelas_gerbong`, `kapasitas`, `harga`, `id_kereta`) VALUES
('GB0001', 'EKSEKUTIF', 20, 10, 'KAI0002');

-- --------------------------------------------------------

--
-- Struktur dari tabel `jadwal`
--

CREATE TABLE `jadwal` (
  `id_jadwal` int(11) NOT NULL,
  `id_kereta` char(10) NOT NULL,
  `stasiun_awal` varchar(30) NOT NULL,
  `stasiun_tujuan` varchar(30) NOT NULL,
  `kedatangan` char(5) NOT NULL,
  `keberangkatan` char(5) NOT NULL,
  `harga_tiket` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data untuk tabel `jadwal`
--

INSERT INTO `jadwal` (`id_jadwal`, `id_kereta`, `stasiun_awal`, `stasiun_tujuan`, `kedatangan`, `keberangkatan`, `harga_tiket`) VALUES
(1, 'KAI0001', 'manggarai', 'sedayu', '07:45', '08:00', 67000);

-- --------------------------------------------------------

--
-- Struktur dari tabel `kereta`
--

CREATE TABLE `kereta` (
  `id_kereta` char(10) NOT NULL,
  `nama_kereta` varchar(30) NOT NULL,
  `gerbong` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data untuk tabel `kereta`
--

INSERT INTO `kereta` (`id_kereta`, `nama_kereta`, `gerbong`) VALUES
('KAI0001', 'JAKA BA', 7),
('KAI0002', 'asmika', 5);

-- --------------------------------------------------------

--
-- Struktur dari tabel `penumpang`
--

CREATE TABLE `penumpang` (
  `id_penumpang` char(10) NOT NULL,
  `nama_penumpang` varchar(30) NOT NULL,
  `alamat` varchar(200) NOT NULL,
  `gender` char(10) NOT NULL,
  `no_tlp` char(12) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data untuk tabel `penumpang`
--

INSERT INTO `penumpang` (`id_penumpang`, `nama_penumpang`, `alamat`, `gender`, `no_tlp`) VALUES
('1', 'sarutobi sasuke', 'desa daun tersembunyi, di dekat rumah kasuga', 'L', '09876543'),
('USER0001', 'iman', 'cengal', 'L', '09876533'),
('USER0002', 'bayu', 'cengal', 'L', '0987'),
('USER0003', 'rangga', 'salareuma', 'L', '7865');

-- --------------------------------------------------------

--
-- Struktur dari tabel `reservasi`
--

CREATE TABLE `reservasi` (
  `id_reservasi` char(10) NOT NULL,
  `id_penumpang` char(10) NOT NULL,
  `id_jadwal` int(11) NOT NULL,
  `id_gerbong` char(10) NOT NULL,
  `tgl_berangkat` date NOT NULL,
  `tgl_pesan` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Struktur dari tabel `stasiun`
--

CREATE TABLE `stasiun` (
  `id_stasiun` char(10) NOT NULL,
  `nama_stasiun` varchar(30) NOT NULL,
  `Kota` varchar(30) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data untuk tabel `stasiun`
--

INSERT INTO `stasiun` (`id_stasiun`, `nama_stasiun`, `Kota`) VALUES
('ST0001', 'Manggarai', 'Jakarta'),
('ST0002', 'Sedayu', 'Podomoro');

-- --------------------------------------------------------

--
-- Struktur dari tabel `tiket`
--

CREATE TABLE `tiket` (
  `id_tiket` char(10) NOT NULL,
  `id_kereta` char(10) NOT NULL,
  `id_jadwal` char(10) NOT NULL,
  `no_kursi` char(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Struktur dari tabel `transaksi`
--

CREATE TABLE `transaksi` (
  `id_transaksi` char(10) NOT NULL,
  `tgl_bayar` date NOT NULL,
  `jumlah` int(11) NOT NULL,
  `total_bayar` int(11) NOT NULL,
  `id_reservasi` char(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Indexes for dumped tables
--

--
-- Indeks untuk tabel `gerbong`
--
ALTER TABLE `gerbong`
  ADD PRIMARY KEY (`id_gerbong`),
  ADD KEY `FK-krt-gb` (`id_kereta`);

--
-- Indeks untuk tabel `jadwal`
--
ALTER TABLE `jadwal`
  ADD PRIMARY KEY (`id_jadwal`),
  ADD KEY `F-krt` (`id_kereta`);

--
-- Indeks untuk tabel `kereta`
--
ALTER TABLE `kereta`
  ADD PRIMARY KEY (`id_kereta`);

--
-- Indeks untuk tabel `penumpang`
--
ALTER TABLE `penumpang`
  ADD PRIMARY KEY (`id_penumpang`);

--
-- Indeks untuk tabel `reservasi`
--
ALTER TABLE `reservasi`
  ADD PRIMARY KEY (`id_reservasi`),
  ADD KEY `FK-rs-p` (`id_penumpang`),
  ADD KEY `Fk-rs-jd` (`id_jadwal`),
  ADD KEY `FK-rs-gb` (`id_gerbong`);

--
-- Indeks untuk tabel `stasiun`
--
ALTER TABLE `stasiun`
  ADD PRIMARY KEY (`id_stasiun`);

--
-- Indeks untuk tabel `tiket`
--
ALTER TABLE `tiket`
  ADD PRIMARY KEY (`id_tiket`) USING BTREE;

--
-- Indeks untuk tabel `transaksi`
--
ALTER TABLE `transaksi`
  ADD PRIMARY KEY (`id_transaksi`),
  ADD KEY `FK-tk-rs` (`id_reservasi`);

--
-- Ketidakleluasaan untuk tabel pelimpahan (Dumped Tables)
--

--
-- Ketidakleluasaan untuk tabel `gerbong`
--
ALTER TABLE `gerbong`
  ADD CONSTRAINT `FK-krt-gb` FOREIGN KEY (`id_kereta`) REFERENCES `kereta` (`id_kereta`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Ketidakleluasaan untuk tabel `jadwal`
--
ALTER TABLE `jadwal`
  ADD CONSTRAINT `F-krt` FOREIGN KEY (`id_kereta`) REFERENCES `kereta` (`id_kereta`);

--
-- Ketidakleluasaan untuk tabel `reservasi`
--
ALTER TABLE `reservasi`
  ADD CONSTRAINT `FK-rs-gb` FOREIGN KEY (`id_gerbong`) REFERENCES `gerbong` (`id_gerbong`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `FK-rs-p` FOREIGN KEY (`id_penumpang`) REFERENCES `penumpang` (`id_penumpang`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `Fk-rs-jd` FOREIGN KEY (`id_jadwal`) REFERENCES `jadwal` (`id_jadwal`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Ketidakleluasaan untuk tabel `transaksi`
--
ALTER TABLE `transaksi`
  ADD CONSTRAINT `FK-tk-rs` FOREIGN KEY (`id_reservasi`) REFERENCES `reservasi` (`id_reservasi`) ON DELETE CASCADE ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
