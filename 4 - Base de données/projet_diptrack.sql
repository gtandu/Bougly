-- phpMyAdmin SQL Dump
-- version 4.6.4
-- https://www.phpmyadmin.net/
--
-- Client :  127.0.0.1
-- Généré le :  Ven 23 Juin 2017 à 16:50
-- Version du serveur :  5.7.14
-- Version de PHP :  5.6.25

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de données :  `projet_diptrack`
--

-- --------------------------------------------------------

--
-- Structure de la table `authority`
--

CREATE TABLE `authority` (
  `id` bigint(20) NOT NULL,
  `role` varchar(255) DEFAULT NULL,
  `account_mail` varchar(255) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Contenu de la table `authority`
--

INSERT INTO `authority` (`id`, `role`, `account_mail`) VALUES
(1, 'Administrator', 'glodie.tandu@diptrack.fr'),
(2, 'Student', 'mapella.corentin@diptrack.fr'),
(3, 'Teacher', 'julien.hairapian@diptrack.fr'),
(4, 'Responsible', 'judith.benzakki@diptrack.fr'),
(5, 'Student', 'mapella.corentin@gmail.com');

-- --------------------------------------------------------

--
-- Structure de la table `class`
--

CREATE TABLE `class` (
  `id` bigint(20) NOT NULL,
  `average` float NOT NULL,
  `date` int(11) NOT NULL,
  `formation` varchar(255) DEFAULT NULL,
  `level` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `responsible` tinyblob,
  `course_id` bigint(20) DEFAULT NULL,
  `subject_id` bigint(20) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Contenu de la table `class`
--

INSERT INTO `class` (`id`, `average`, `date`, `formation`, `level`, `name`, `responsible`, `course_id`, `subject_id`) VALUES
(1, 0, 0, 'APPRENTISSAGE', 'M1', 'M1MIAA', NULL, 1, NULL),
(2, 0, 0, 'INITIALE', 'L3', 'L3ERGO', NULL, NULL, NULL);

-- --------------------------------------------------------

--
-- Structure de la table `course`
--

CREATE TABLE `course` (
  `id` bigint(20) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `threshold` int(11) NOT NULL,
  `responsible_mail` varchar(255) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Contenu de la table `course`
--

INSERT INTO `course` (`id`, `name`, `threshold`, `responsible_mail`) VALUES
(1, 'MIAGE - Ingénierie logicielle sur le WEB', 0, 'judith.benzakki@diptrack.fr');

-- --------------------------------------------------------

--
-- Structure de la table `mark`
--

CREATE TABLE `mark` (
  `id` bigint(20) NOT NULL,
  `mark` float NOT NULL,
  `mark_type_enum` varchar(255) DEFAULT NULL,
  `student_mail` varchar(255) DEFAULT NULL,
  `subject_id` bigint(20) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Structure de la table `mccrule`
--

CREATE TABLE `mccrule` (
  `id` bigint(20) NOT NULL,
  `coefficient` int(11) NOT NULL,
  `mark_type` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `subject_id` bigint(20) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Structure de la table `semester`
--

CREATE TABLE `semester` (
  `id` bigint(20) NOT NULL,
  `ects` int(11) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `number` int(11) NOT NULL,
  `resit` bit(1) NOT NULL,
  `course_id` bigint(20) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Contenu de la table `semester`
--

INSERT INTO `semester` (`id`, `ects`, `name`, `number`, `resit`, `course_id`) VALUES
(1, 0, NULL, 1, b'0', 1);

-- --------------------------------------------------------

--
-- Structure de la table `subject`
--

CREATE TABLE `subject` (
  `id` bigint(20) NOT NULL,
  `average` float NOT NULL,
  `coefficient` int(11) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `resit` bit(1) NOT NULL,
  `threshold` int(11) NOT NULL,
  `year` int(11) NOT NULL,
  `ue_id` bigint(20) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Contenu de la table `subject`
--

INSERT INTO `subject` (`id`, `average`, `coefficient`, `description`, `name`, `resit`, `threshold`, `year`, `ue_id`) VALUES
(1, 0, 4, NULL, 'Base de données avancées', b'1', 7, 2017, 1),
(2, 0, 4, NULL, 'Gestion financière', b'1', 7, 2017, 1),
(3, 0, 3, NULL, 'Statistiques', b'1', 7, 2017, 1),
(4, 0, 4, NULL, 'Recherche Opérationnelle', b'1', 7, 2017, 1),
(5, 0, 2, NULL, 'Anglais', b'1', 7, 2017, 2),
(6, 0, 4, NULL, 'Conception Orientée Objet de logiciels', b'1', 7, 2017, 2),
(7, 0, 3, NULL, 'Droit numérique', b'1', 7, 2017, 2),
(8, 0, 3, NULL, 'Conception d\'applications réparties', b'1', 7, 2017, 2),
(9, 0, 3, NULL, 'TER - Projet', b'0', 10, 2017, 2);

-- --------------------------------------------------------

--
-- Structure de la table `subject_list_classes`
--

CREATE TABLE `subject_list_classes` (
  `subject_id` bigint(20) NOT NULL,
  `list_classes_id` bigint(20) NOT NULL,
  `list_subjects_id` bigint(20) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Structure de la table `subject_list_marks`
--

CREATE TABLE `subject_list_marks` (
  `subject_id` bigint(20) NOT NULL,
  `list_marks_id` bigint(20) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Structure de la table `subject_list_mcc_rules`
--

CREATE TABLE `subject_list_mcc_rules` (
  `subject_id` bigint(20) NOT NULL,
  `list_mcc_rules_id` bigint(20) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Structure de la table `subject_list_students`
--

CREATE TABLE `subject_list_students` (
  `subject_id` bigint(20) NOT NULL,
  `list_students_mail` varchar(255) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Structure de la table `subject_list_teachers`
--

CREATE TABLE `subject_list_teachers` (
  `subject_id` bigint(20) NOT NULL,
  `list_teachers_mail` varchar(255) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Structure de la table `ue`
--

CREATE TABLE `ue` (
  `id` bigint(20) NOT NULL,
  `average` int(11) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `number` int(11) NOT NULL,
  `threshold` int(11) NOT NULL,
  `ue_coefficient` int(11) NOT NULL,
  `semester_id` bigint(20) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Contenu de la table `ue`
--

INSERT INTO `ue` (`id`, `average`, `name`, `number`, `threshold`, `ue_coefficient`, `semester_id`) VALUES
(1, 0, '', 1, 0, 15, 1),
(2, 0, '', 2, 0, 16, 1);

-- --------------------------------------------------------

--
-- Structure de la table `ue_list_subject`
--

CREATE TABLE `ue_list_subject` (
  `ue_id` bigint(20) NOT NULL,
  `list_subject_id` bigint(20) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Contenu de la table `ue_list_subject`
--

INSERT INTO `ue_list_subject` (`ue_id`, `list_subject_id`) VALUES
(1, 3),
(1, 1),
(1, 2),
(1, 4),
(2, 8),
(2, 6),
(2, 5),
(2, 7),
(2, 9);

-- --------------------------------------------------------

--
-- Structure de la table `user_account`
--

CREATE TABLE `user_account` (
  `dtype` varchar(31) NOT NULL,
  `mail` varchar(255) NOT NULL,
  `enabled` bit(1) NOT NULL,
  `first_name` varchar(255) DEFAULT NULL,
  `last_name` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `average` float DEFAULT NULL,
  `student_number` varchar(255) DEFAULT NULL,
  `course_id` bigint(20) DEFAULT NULL,
  `student_class_id` bigint(20) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Contenu de la table `user_account`
--

INSERT INTO `user_account` (`dtype`, `mail`, `enabled`, `first_name`, `last_name`, `password`, `average`, `student_number`, `course_id`, `student_class_id`) VALUES
('Administrator', 'glodie.tandu@diptrack.fr', b'1', 'Glodie', 'TANDU', '$2a$10$FY/EUGJPLu0rdFiToU9zA.AWAzVNOSsoV5/XKTnbUDAx4lRxaTzFu', NULL, NULL, NULL, NULL),
('Student', 'mapella.corentin@diptrack.fr', b'1', 'Corentin', 'MAPELLA', '$2a$10$87tjSBGfxkDoisWLt.LW1.nuEuyOfMJHroSpUVb6j/PbYOeu/QPA2', 0, '20170101', NULL, NULL),
('Teacher', 'julien.hairapian@diptrack.fr', b'1', 'Julien', 'HAIRAPIAN', '$2a$10$C3g9qsXKNcxp/qD1EsmEtuMepjMUoflSpTLbvc8/fSWSppl.WnY6K', NULL, NULL, NULL, NULL),
('Responsible', 'judith.benzakki@diptrack.fr', b'1', 'Judith', 'BENZAKKI', '$2a$10$apt65rdWfV/mkpPr6Amu0.PxE80ijMNNvTCzGzTbAcY3mEk454vii', NULL, NULL, NULL, NULL),
('Student', 'mapella.corentin@gmail.com', b'1', 'Corentin', 'Mapella', '$2a$10$fUarX1Kj5aIPgQFHNPQuAOtiR1KMC..ibV14MP5lo4Axb5onIOkU6', 0, '20170202', NULL, NULL);

-- --------------------------------------------------------

--
-- Structure de la table `user_account_list_classes`
--

CREATE TABLE `user_account_list_classes` (
  `list_teachers_mail` varchar(255) NOT NULL,
  `list_classes_id` bigint(20) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Structure de la table `user_account_list_marks`
--

CREATE TABLE `user_account_list_marks` (
  `student_mail` varchar(255) NOT NULL,
  `list_marks_id` bigint(20) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Structure de la table `user_account_list_subjects`
--

CREATE TABLE `user_account_list_subjects` (
  `student_mail` varchar(255) NOT NULL,
  `list_subjects_id` bigint(20) NOT NULL,
  `teacher_mail` varchar(255) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Structure de la table `verification_token`
--

CREATE TABLE `verification_token` (
  `id` bigint(20) NOT NULL,
  `expired` bit(1) NOT NULL,
  `expiry_date` datetime DEFAULT NULL,
  `token` varchar(255) DEFAULT NULL,
  `user_id` varchar(255) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Contenu de la table `verification_token`
--

INSERT INTO `verification_token` (`id`, `expired`, `expiry_date`, `token`, `user_id`) VALUES
(3, b'1', '2017-06-29 16:00:34', 'f2af81a3-81ad-449f-b6c6-fefccf24c022', 'mapella.corentin@gmail.com');

--
-- Index pour les tables exportées
--

--
-- Index pour la table `authority`
--
ALTER TABLE `authority`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKkd6gpt302uglapof86d5lf7v8` (`account_mail`);

--
-- Index pour la table `class`
--
ALTER TABLE `class`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKh0eyrgvqpfux7dvr8elhhdaf6` (`subject_id`),
  ADD KEY `FKlsxcyh4sq20727qj0clvah8dg` (`course_id`);

--
-- Index pour la table `course`
--
ALTER TABLE `course`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKcy3op4k1nmfv0ymoqdih77192` (`responsible_mail`);

--
-- Index pour la table `mark`
--
ALTER TABLE `mark`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKt71b705vpgb280uflqktsyieq` (`student_mail`),
  ADD KEY `FKt6kc1aolba30ld4m8fqmcrt1q` (`subject_id`);

--
-- Index pour la table `mccrule`
--
ALTER TABLE `mccrule`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKaywfv9ueamvlh9oyk8ujpbsmh` (`subject_id`);

--
-- Index pour la table `semester`
--
ALTER TABLE `semester`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKqlk0pjw10qitxcslw1sild4go` (`course_id`);

--
-- Index pour la table `subject`
--
ALTER TABLE `subject`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKlrl2a29a9jqa3vu2xusfc62g5` (`ue_id`);

--
-- Index pour la table `subject_list_classes`
--
ALTER TABLE `subject_list_classes`
  ADD UNIQUE KEY `UK_nuvtohi1vvj2lo2p8l2ypo29` (`list_classes_id`),
  ADD KEY `FKai2n4dx2oqsiflu4vyxo69h4r` (`subject_id`),
  ADD KEY `FKgjt4a3av00bb8mvp2x47628q` (`list_subjects_id`);

--
-- Index pour la table `subject_list_marks`
--
ALTER TABLE `subject_list_marks`
  ADD UNIQUE KEY `UK_qf4q21o1d5bsx3dvh7n8v552u` (`list_marks_id`),
  ADD KEY `FKirwa36b6joyjpcxjuke7mgl3c` (`subject_id`);

--
-- Index pour la table `subject_list_mcc_rules`
--
ALTER TABLE `subject_list_mcc_rules`
  ADD UNIQUE KEY `UK_ngjx8mch0ochrvdmmufganrux` (`list_mcc_rules_id`),
  ADD KEY `FKl3ly6ogs46x31v9cupylwl4i1` (`subject_id`);

--
-- Index pour la table `subject_list_students`
--
ALTER TABLE `subject_list_students`
  ADD KEY `FKqxladum19an828tugh88k1y1f` (`list_students_mail`),
  ADD KEY `FKckxp80fok6w1keon9fcskv3gm` (`subject_id`);

--
-- Index pour la table `subject_list_teachers`
--
ALTER TABLE `subject_list_teachers`
  ADD UNIQUE KEY `UK_4bkabojc2j601wsy1t78hivu6` (`list_teachers_mail`),
  ADD KEY `FK3r6ft210f8r4k0fy47wuwsgsd` (`subject_id`);

--
-- Index pour la table `ue`
--
ALTER TABLE `ue`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKitbwhbwltfqn97cg2n7bnaj55` (`semester_id`);

--
-- Index pour la table `ue_list_subject`
--
ALTER TABLE `ue_list_subject`
  ADD UNIQUE KEY `UK_7jmfxkt4ca8vv504lyff0eyfk` (`list_subject_id`),
  ADD KEY `FK7es09xvewyspkjx17im9yevar` (`ue_id`);

--
-- Index pour la table `user_account`
--
ALTER TABLE `user_account`
  ADD PRIMARY KEY (`mail`),
  ADD UNIQUE KEY `UK_gxcxax4s2jkegjkle07h48hkg` (`student_number`),
  ADD KEY `FK5tfhwap289ybdwga4xmi55ooy` (`course_id`),
  ADD KEY `FK2qtr4srchror2st0ef9q3w3qm` (`student_class_id`);

--
-- Index pour la table `user_account_list_classes`
--
ALTER TABLE `user_account_list_classes`
  ADD KEY `FKlrqmh8l847bwlg9yu0yi5o900` (`list_classes_id`),
  ADD KEY `FKkqdpr80jyuqagnvqeflmxjbrf` (`list_teachers_mail`);

--
-- Index pour la table `user_account_list_marks`
--
ALTER TABLE `user_account_list_marks`
  ADD UNIQUE KEY `UK_r0mjql4xe6eep7usghtkfnrwh` (`list_marks_id`),
  ADD KEY `FKe1buqr8st88el2ku6vvy1jbsq` (`student_mail`);

--
-- Index pour la table `user_account_list_subjects`
--
ALTER TABLE `user_account_list_subjects`
  ADD KEY `FKr7wtg6b1nl1lf51rygi67nbns` (`list_subjects_id`),
  ADD KEY `FKsdggnq1dwg2go31fcfh9hq57a` (`student_mail`),
  ADD KEY `FKm1r5yebv0x0swsq27sw3qese7` (`teacher_mail`);

--
-- Index pour la table `verification_token`
--
ALTER TABLE `verification_token`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKlrihnx4hd69b8ao34kof74j70` (`user_id`);

--
-- AUTO_INCREMENT pour les tables exportées
--

--
-- AUTO_INCREMENT pour la table `authority`
--
ALTER TABLE `authority`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;
--
-- AUTO_INCREMENT pour la table `class`
--
ALTER TABLE `class`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;
--
-- AUTO_INCREMENT pour la table `course`
--
ALTER TABLE `course`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;
--
-- AUTO_INCREMENT pour la table `mark`
--
ALTER TABLE `mark`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT pour la table `mccrule`
--
ALTER TABLE `mccrule`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT pour la table `semester`
--
ALTER TABLE `semester`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;
--
-- AUTO_INCREMENT pour la table `subject`
--
ALTER TABLE `subject`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;
--
-- AUTO_INCREMENT pour la table `ue`
--
ALTER TABLE `ue`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;
--
-- AUTO_INCREMENT pour la table `verification_token`
--
ALTER TABLE `verification_token`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
