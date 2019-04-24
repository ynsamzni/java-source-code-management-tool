--
-- Table structure for table `user_usr`
--

CREATE TABLE user_usr (
  usr_id NUMBER(4),
  usr_username VARCHAR2(20) CONSTRAINT usr_username_nn NOT NULL,
  usr_password VARCHAR2(100) CONSTRAINT usr_password_nn NOT NULL,
  usr_access_level NUMBER(1) CONSTRAINT usr_access_level_nn NOT NULL,
  CONSTRAINT usr_pk PRIMARY KEY(usr_id),
  CONSTRAINT usr_username_un UNIQUE(usr_username)
);

--
-- Trigger and sequence for inserting new usr_id before insert on user_usr
--

CREATE SEQUENCE user_usr_seq START WITH 1 INCREMENT BY 1;

CREATE OR REPLACE TRIGGER user_usr_on_insert
  BEFORE INSERT ON user_usr 
  FOR EACH ROW
BEGIN
  :new.usr_id := user_usr_seq.NEXTVAL;
END;
/

--
-- Table structure for table `javasourcefile_jsf`
--

CREATE TABLE javasourcefile_jsf (
  jsf_id NUMBER(4),
  jsf_path_fs VARCHAR2(4000) CONSTRAINT jsf_path_fs_nn NOT NULL,
  jsf_content CLOB,
  CONSTRAINT jsf_pk PRIMARY KEY(jsf_id),
  CONSTRAINT jsf_path_fs_un UNIQUE(jsf_path_fs)
);

--
-- Trigger and sequence for inserting new jsf_id before insert on javasourcefile_jsf
--

CREATE SEQUENCE javasourcefile_jsf_seq START WITH 1 INCREMENT BY 1;

CREATE OR REPLACE TRIGGER javasourcefile_jsf_on_insert
  BEFORE INSERT ON javasourcefile_jsf 
  FOR EACH ROW
BEGIN
  :new.jsf_id := javasourcefile_jsf_seq.NEXTVAL;
END;
/

--
-- Table structure for table `version_ver`
--

CREATE TABLE version_ver (
  ver_id NUMBER(4),
  ver_major_number NUMBER(2) CONSTRAINT ver_major_number_nn NOT NULL,
  ver_minor_number NUMBER(2),
  ver_revision_number NUMBER(6),
  ver_build_number NUMBER(16), 
  ver_date DATE CONSTRAINT ver_date_nn NOT NULL,
  ver_jsf_id NUMBER(4) CONSTRAINT ver_jsf_id_nn NOT NULL,
  ver_usr_id NUMBER(4) CONSTRAINT ver_usr_id_nn NOT NULL,
  CONSTRAINT ver_pk PRIMARY KEY(ver_id),
  CONSTRAINT ver_jsf_id_fk FOREIGN KEY(ver_jsf_id) REFERENCES javasourcefile_jsf(jsf_id),
  CONSTRAINT ver_usr_id_fk FOREIGN KEY(ver_usr_id) REFERENCES user_usr(usr_id),
  CONSTRAINT ver_numbers_and_ver_jsf_id_un UNIQUE(ver_major_number, ver_minor_number, ver_revision_number, ver_build_number, ver_jsf_id)
);

--
-- Trigger and sequence for inserting new ver_id before insert on version_ver
--

CREATE SEQUENCE version_ver_seq START WITH 1 INCREMENT BY 1;

CREATE OR REPLACE TRIGGER version_ver_on_insert
  BEFORE INSERT ON version_ver
  FOR EACH ROW
BEGIN
  :new.ver_id := version_ver_seq.NEXTVAL;
END;
/

--
-- Table structure for table `description_descr`
--

CREATE TABLE description_descr (
  descr_id NUMBER(4),
  descr_description VARCHAR2(300) CONSTRAINT descr_description_nn NOT NULL,
  descr_ver_id NUMBER(4) CONSTRAINT descr_ver_id_nn NOT NULL,
  CONSTRAINT descr_pk PRIMARY KEY(descr_id),
  CONSTRAINT descr_ver_id_fk FOREIGN KEY(descr_ver_id) REFERENCES version_ver(ver_id)
);

--
-- Trigger and sequence for inserting new descr_id before insert on description_descr
--

CREATE SEQUENCE description_descr_seq START WITH 1 INCREMENT BY 1;

CREATE OR REPLACE TRIGGER description_descr_on_insert
  BEFORE INSERT ON description_descr
  FOR EACH ROW
BEGIN
  :new.descr_id := description_descr_seq.NEXTVAL;
END;
/
