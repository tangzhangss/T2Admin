-- 菜单
CREATE TABLE sys_menu (
  menu_id bigserial,
  parent_id int8,
  name varchar(50),
  url varchar(200),
  perms varchar(500),
  type int,
  icon varchar(50),
  order_num int,
  open_mode  varchar(10),
  PRIMARY KEY (menu_id)
);

-- 部门
CREATE TABLE sys_dept (
  dept_id bigserial,
  parent_id int8,
  name varchar(50),
  order_num int,
  del_flag int DEFAULT 0,
  PRIMARY KEY (dept_id)
);

-- 系统用户
CREATE TABLE sys_user (
  user_id bigserial,
  username varchar(50) NOT NULL,
  password varchar(100),
  salt varchar(20),
  email varchar(100),
  mobile varchar(100),
  status int,
  dept_id int8,
  create_time timestamp,
  PRIMARY KEY (user_id),
  UNIQUE (username)
);

-- 角色
CREATE TABLE sys_role (
  role_id bigserial,
  role_name varchar(100),
  remark varchar(100),
  dept_id int8,
  create_time timestamp,
  PRIMARY KEY (role_id)
);

-- 用户与角色对应关系
CREATE TABLE sys_user_role (
  id bigserial,
  user_id int8,
  role_id int8,
  PRIMARY KEY (id)
);

-- 角色与菜单对应关系
CREATE TABLE sys_role_menu (
  id bigserial,
  role_id int8,
  menu_id int8,
  PRIMARY KEY (id)
);

-- 角色与部门对应关系
CREATE TABLE sys_role_dept (
  id bigserial,
  role_id int8,
  dept_id int8,
  PRIMARY KEY (id)
);

-- 系统配置信息
CREATE TABLE sys_config (
  id bigserial,
  param_key varchar(50),
  param_value varchar(2000),
  status int DEFAULT 1,
  remark varchar(500),
  PRIMARY KEY (id),
  UNIQUE (param_key)
);

-- 数据字典
CREATE TABLE sys_dict (
  id bigserial,
  name varchar(100) NOT NULL,
  type varchar(100) NOT NULL,
  code varchar(100) NOT NULL,
  value varchar(1000) NOT NULL,
  order_num int DEFAULT 0,
  remark varchar(255),
  del_flag int DEFAULT 0,
  PRIMARY KEY (id),
  UNIQUE (type,code)
);

-- 系统日志
CREATE TABLE sys_log (
  id bigserial,
  username varchar(50),
  operation varchar(50),
  method varchar(200),
  params varchar(5000),
  time int8 NOT NULL,
  ip varchar(64),
  create_date timestamp,
  PRIMARY KEY (id)
);

-- 文件上传
CREATE TABLE sys_oss (
  id bigserial,
  url varchar(200),
  create_date timestamp,
  PRIMARY KEY (id)
);

-- 定时任务
CREATE TABLE schedule_job (
  job_id bigserial,
  bean_name varchar(200),
  method_name varchar(100),
  params varchar(2000),
  cron_expression varchar(100),
  status int,
  remark varchar(255),
  create_time timestamp,
  PRIMARY KEY (job_id)
);

-- 定时任务日志
CREATE TABLE schedule_job_log (
  log_id bigserial,
  job_id int8 NOT NULL,
  bean_name varchar(200),
  method_name varchar(100),
  params varchar(2000),
  status int NOT NULL,
  error varchar(2000),
  times int NOT NULL,
  create_time timestamp,
  PRIMARY KEY (log_id)
);
CREATE INDEX index_job_id on schedule_job_log(job_id);
CREATE TABLE sys_area (area_no CHARACTER VARYING(15) NOT NULL, area_name CHARACTER VARYING(80), lev CHARACTER VARYING(1), uplev CHARACTER VARYING(15), area_sts CHARACTER VARYING(1), if_leaf CHARACTER VARYING(2));
CREATE TABLE sys_way (way_no CHARACTER VARYING(6) NOT NULL, way_name CHARACTER VARYING(80), lev CHARACTER VARYING(1), uplev CHARACTER VARYING(6), CONSTRAINT pk_sys_way PRIMARY KEY (way_no));
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('110000', '北京市', '1', '0', '1', '');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('110100', '市辖区', '2', '110000', '0', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('110101', '东城区', '2', '110000', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('110102', '西城区', '2', '110000', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('110103', '崇文区', '2', '110000', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('110104', '宣武区', '2', '110000', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('110105', '朝阳区', '2', '110000', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('110106', '丰台区', '2', '110000', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('110107', '石景山区', '2', '110000', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('110108', '海淀区', '2', '110000', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('110109', '门头沟区', '2', '110000', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('110111', '房山区', '2', '110000', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('110112', '通州区', '2', '110000', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('110113', '顺义区', '2', '110000', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('110114', '昌平区', '2', '110000', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('110115', '大兴区', '2', '110000', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('110116', '怀柔区', '2', '110000', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('110117', '平谷区', '2', '110000', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('110228', '密云县', '2', '110000', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('110229', '延庆县', '2', '110000', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('120000', '天津市', '1', '0', '1', '');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('120100', '市辖区', '2', '120000', '0', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('120101', '和平区', '2', '120000', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('120102', '河东区', '2', '120000', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('120103', '河西区', '2', '120000', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('120104', '南开区', '2', '120000', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('120105', '河北区', '2', '120000', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('120106', '红桥区', '2', '120000', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('120107', '塘沽区', '2', '120000', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('120108', '汉沽区', '2', '120000', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('120109', '大港区', '2', '120000', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('120110', '东丽区', '2', '120000', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('120111', '西青区', '2', '120000', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('120112', '津南区', '2', '120000', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('120113', '北辰区', '2', '120000', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('120114', '武清区', '2', '120000', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('120115', '宝坻区', '2', '120000', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('120221', '宁河县', '2', '120000', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('120223', '静海县', '2', '120000', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('120225', '蓟县', '2', '120000', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('130000', '河北省', '1', '0', '1', '');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('130100', '石家庄市', '2', '130000', '1', '');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('130101', '市辖区', '3', '130100', '0', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('130102', '长安区', '3', '130100', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('130103', '桥东区', '3', '130100', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('130104', '桥西区', '3', '130100', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('130105', '新华区', '3', '130100', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('130107', '井陉矿区', '3', '130100', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('130108', '裕华区', '3', '130100', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('130121', '井陉县', '3', '130100', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('130123', '正定县', '3', '130100', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('130124', '栾城县', '3', '130100', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('130125', '行唐县', '3', '130100', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('130126', '灵寿县', '3', '130100', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('130127', '高邑县', '3', '130100', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('130128', '深泽县', '3', '130100', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('130129', '赞皇县', '3', '130100', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('130130', '无极县', '3', '130100', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('130131', '平山县', '3', '130100', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('130132', '元氏县', '3', '130100', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('130133', '赵县', '3', '130100', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('130181', '辛集市', '3', '130100', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('130182', '藁城市', '3', '130100', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('130183', '晋州市', '3', '130100', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('130184', '新乐市', '3', '130100', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('130185', '鹿泉市', '3', '130100', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('130200', '唐山市', '2', '130000', '1', '');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('130201', '市辖区', '3', '130200', '0', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('130202', '路南区', '3', '130200', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('130203', '路北区', '3', '130200', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('130204', '古冶区', '3', '130200', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('130205', '开平区', '3', '130200', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('130207', '丰南区', '3', '130200', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('130208', '丰润区', '3', '130200', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('130223', '滦县', '3', '130200', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('130224', '滦南县', '3', '130200', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('130225', '乐亭县', '3', '130200', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('130227', '迁西县', '3', '130200', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('130229', '玉田县', '3', '130200', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('130230', '唐海县', '3', '130200', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('130281', '遵化市', '3', '130200', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('130283', '迁安市', '3', '130200', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('130300', '秦皇岛市', '2', '130000', '1', '');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('130301', '市辖区', '3', '130300', '0', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('130302', '海港区', '3', '130300', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('130303', '山海关区', '3', '130300', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('130304', '北戴河区', '3', '130300', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('130321', '青龙满族自治县', '3', '130300', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('130322', '昌黎县', '3', '130300', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('130323', '抚宁县', '3', '130300', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('130324', '卢龙县', '3', '130300', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('130400', '邯郸市', '2', '130000', '1', '');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('130401', '市辖区', '3', '130400', '0', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('130402', '邯山区', '3', '130400', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('130403', '丛台区', '3', '130400', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('130404', '复兴区', '3', '130400', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('130406', '峰峰矿区', '3', '130400', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('130421', '邯郸县', '3', '130400', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('130423', '临漳县', '3', '130400', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('130424', '成安县', '3', '130400', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('130425', '大名县', '3', '130400', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('130426', '涉县', '3', '130400', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('130427', '磁县', '3', '130400', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('130428', '肥乡县', '3', '130400', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('130429', '永年县', '3', '130400', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('130430', '邱县', '3', '130400', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('130431', '鸡泽县', '3', '130400', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('130432', '广平县', '3', '130400', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('130433', '馆陶县', '3', '130400', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('130434', '魏县', '3', '130400', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('130435', '曲周县', '3', '130400', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('130481', '武安市', '3', '130400', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('130500', '邢台市', '2', '130000', '1', '');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('130501', '市辖区', '3', '130500', '0', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('130502', '桥东区', '3', '130500', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('130503', '桥西区', '3', '130500', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('130521', '邢台县', '3', '130500', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('130522', '临城县', '3', '130500', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('130523', '内丘县', '3', '130500', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('130524', '柏乡县', '3', '130500', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('130525', '隆尧县', '3', '130500', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('130526', '任县', '3', '130500', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('130527', '南和县', '3', '130500', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('130528', '宁晋县', '3', '130500', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('130529', '巨鹿县', '3', '130500', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('130530', '新河县', '3', '130500', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('130531', '广宗县', '3', '130500', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('130532', '平乡县', '3', '130500', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('130533', '威县', '3', '130500', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('130534', '清河县', '3', '130500', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('130535', '临西县', '3', '130500', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('130581', '南宫市', '3', '130500', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('130582', '沙河市', '3', '130500', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('130600', '保定市', '2', '130000', '1', '');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('130601', '市辖区', '3', '130600', '0', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('130602', '新市区', '3', '130600', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('130603', '北市区', '3', '130600', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('130604', '南市区', '3', '130600', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('130621', '满城县', '3', '130600', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('130622', '清苑县', '3', '130600', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('130623', '涞水县', '3', '130600', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('130624', '阜平县', '3', '130600', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('130625', '徐水县', '3', '130600', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('130626', '定兴县', '3', '130600', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('130627', '唐县', '3', '130600', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('130628', '高阳县', '3', '130600', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('130629', '容城县', '3', '130600', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('130630', '涞源县', '3', '130600', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('130631', '望都县', '3', '130600', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('130632', '安新县', '3', '130600', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('130633', '易县', '3', '130600', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('130634', '曲阳县', '3', '130600', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('130635', '蠡县', '3', '130600', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('130636', '顺平县', '3', '130600', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('130637', '博野县', '3', '130600', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('130638', '雄县', '3', '130600', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('130681', '涿州市', '3', '130600', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('130682', '定州市', '3', '130600', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('130683', '安国市', '3', '130600', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('130684', '高碑店市', '3', '130600', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('130700', '张家口市', '2', '130000', '1', '');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('130701', '市辖区', '3', '130700', '0', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('130702', '桥东区', '3', '130700', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('130703', '桥西区', '3', '130700', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('130705', '宣化区', '3', '130700', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('130706', '下花园区', '3', '130700', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('130721', '宣化县', '3', '130700', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('130722', '张北县', '3', '130700', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('130723', '康保县', '3', '130700', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('130724', '沽源县', '3', '130700', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('130725', '尚义县', '3', '130700', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('130726', '蔚县', '3', '130700', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('130727', '阳原县', '3', '130700', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('130728', '怀安县', '3', '130700', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('130729', '万全县', '3', '130700', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('130730', '怀来县', '3', '130700', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('130731', '涿鹿县', '3', '130700', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('130732', '赤城县', '3', '130700', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('130733', '崇礼县', '3', '130700', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('130800', '承德市', '2', '130000', '1', '');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('130801', '市辖区', '3', '130800', '0', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('130802', '双桥区', '3', '130800', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('130803', '双滦区', '3', '130800', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('130804', '鹰手营子矿区', '3', '130800', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('130821', '承德县', '3', '130800', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('130822', '兴隆县', '3', '130800', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('130823', '平泉县', '3', '130800', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('130824', '滦平县', '3', '130800', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('130825', '隆化县', '3', '130800', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('130826', '丰宁满族自治县', '3', '130800', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('130827', '宽城满族自治县', '3', '130800', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('130828', '围场满族蒙古族自治县', '3', '130800', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('130900', '沧州市', '2', '130000', '1', '');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('130901', '市辖区', '3', '130900', '0', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('130902', '新华区', '3', '130900', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('130903', '运河区', '3', '130900', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('130921', '沧县', '3', '130900', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('130922', '青县', '3', '130900', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('130923', '东光县', '3', '130900', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('130924', '海兴县', '3', '130900', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('130925', '盐山县', '3', '130900', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('130926', '肃宁县', '3', '130900', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('130927', '南皮县', '3', '130900', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('130928', '吴桥县', '3', '130900', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('130929', '献县', '3', '130900', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('130930', '孟村回族自治县', '3', '130900', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('130981', '泊头市', '3', '130900', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('130982', '任丘市', '3', '130900', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('130983', '黄骅市', '3', '130900', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('130984', '河间市', '3', '130900', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('131000', '廊坊市', '2', '130000', '1', '');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('131001', '市辖区', '3', '131000', '0', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('131002', '安次区', '3', '131000', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('131003', '广阳区', '3', '131000', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('131022', '固安县', '3', '131000', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('131023', '永清县', '3', '131000', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('131024', '香河县', '3', '131000', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('131025', '大城县', '3', '131000', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('131026', '文安县', '3', '131000', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('131028', '大厂回族自治县', '3', '131000', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('131081', '霸州市', '3', '131000', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('131082', '三河市', '3', '131000', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('131100', '衡水市', '2', '130000', '1', '');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('131101', '市辖区', '3', '131100', '0', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('131102', '桃城区', '3', '131100', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('131121', '枣强县', '3', '131100', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('131122', '武邑县', '3', '131100', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('131123', '武强县', '3', '131100', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('131124', '饶阳县', '3', '131100', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('131125', '安平县', '3', '131100', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('131126', '故城县', '3', '131100', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('131127', '景县', '3', '131100', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('131128', '阜城县', '3', '131100', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('131181', '冀州市', '3', '131100', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('131182', '深州市', '3', '131100', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('140000', '山西省', '1', '0', '1', '');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('140100', '太原市', '2', '140000', '1', '');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('140101', '市辖区', '3', '140100', '0', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('140105', '小店区', '3', '140100', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('140106', '迎泽区', '3', '140100', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('140107', '杏花岭区', '3', '140100', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('140108', '尖草坪区', '3', '140100', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('140109', '万柏林区', '3', '140100', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('140110', '晋源区', '3', '140100', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('140121', '清徐县', '3', '140100', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('140122', '阳曲县', '3', '140100', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('140123', '娄烦县', '3', '140100', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('140181', '古交市', '3', '140100', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('140200', '大同市', '2', '140000', '1', '');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('140201', '市辖区', '3', '140200', '0', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('140202', '城区', '3', '140200', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('140203', '矿区', '3', '140200', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('140211', '南郊区', '3', '140200', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('140212', '新荣区', '3', '140200', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('140221', '阳高县', '3', '140200', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('140222', '天镇县', '3', '140200', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('140223', '广灵县', '3', '140200', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('140224', '灵丘县', '3', '140200', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('140225', '浑源县', '3', '140200', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('140226', '左云县', '3', '140200', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('140227', '大同县', '3', '140200', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('140300', '阳泉市', '2', '140000', '1', '');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('140301', '市辖区', '3', '140300', '0', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('140302', '城区', '3', '140300', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('140303', '矿区', '3', '140300', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('140311', '郊区', '3', '140300', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('140321', '平定县', '3', '140300', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('140322', '盂县', '3', '140300', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('140400', '长治市', '2', '140000', '1', '');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('140401', '市辖区', '3', '140400', '0', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('140402', '城区', '3', '140400', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('140411', '郊区', '3', '140400', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('140421', '长治县', '3', '140400', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('140423', '襄垣县', '3', '140400', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('140424', '屯留县', '3', '140400', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('140425', '平顺县', '3', '140400', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('140426', '黎城县', '3', '140400', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('140427', '壶关县', '3', '140400', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('140428', '长子县', '3', '140400', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('140429', '武乡县', '3', '140400', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('140430', '沁县', '3', '140400', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('140431', '沁源县', '3', '140400', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('140481', '潞城市', '3', '140400', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('140500', '晋城市', '2', '140000', '1', '');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('140501', '市辖区', '3', '140500', '0', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('140502', '城区', '3', '140500', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('140521', '沁水县', '3', '140500', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('140522', '阳城县', '3', '140500', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('140524', '陵川县', '3', '140500', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('140525', '泽州县', '3', '140500', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('140581', '高平市', '3', '140500', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('140600', '朔州市', '2', '140000', '1', '');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('140601', '市辖区', '3', '140600', '0', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('140602', '朔城区', '3', '140600', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('140603', '平鲁区', '3', '140600', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('140621', '山阴县', '3', '140600', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('140622', '应县', '3', '140600', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('140623', '右玉县', '3', '140600', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('140624', '怀仁县', '3', '140600', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('140700', '晋中市', '2', '140000', '1', '');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('140701', '市辖区', '3', '140700', '0', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('140702', '榆次区', '3', '140700', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('140721', '榆社县', '3', '140700', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('140722', '左权县', '3', '140700', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('140723', '和顺县', '3', '140700', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('140724', '昔阳县', '3', '140700', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('140725', '寿阳县', '3', '140700', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('140726', '太谷县', '3', '140700', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('140727', '祁县', '3', '140700', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('140728', '平遥县', '3', '140700', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('140729', '灵石县', '3', '140700', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('140781', '介休市', '3', '140700', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('140800', '运城市', '2', '140000', '1', '');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('140801', '市辖区', '3', '140800', '0', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('140802', '盐湖区', '3', '140800', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('140821', '临猗县', '3', '140800', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('140822', '万荣县', '3', '140800', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('140823', '闻喜县', '3', '140800', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('140824', '稷山县', '3', '140800', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('140825', '新绛县', '3', '140800', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('140826', '绛县', '3', '140800', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('140827', '垣曲县', '3', '140800', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('140828', '夏县', '3', '140800', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('140829', '平陆县', '3', '140800', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('140830', '芮城县', '3', '140800', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('140881', '永济市', '3', '140800', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('140882', '河津市', '3', '140800', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('140900', '忻州市', '2', '140000', '1', '');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('140901', '市辖区', '3', '140900', '0', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('140902', '忻府区', '3', '140900', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('140921', '定襄县', '3', '140900', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('140922', '五台县', '3', '140900', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('140923', '代县', '3', '140900', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('140924', '繁峙县', '3', '140900', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('140925', '宁武县', '3', '140900', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('140926', '静乐县', '3', '140900', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('140927', '神池县', '3', '140900', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('140928', '五寨县', '3', '140900', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('140929', '岢岚县', '3', '140900', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('140930', '河曲县', '3', '140900', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('140931', '保德县', '3', '140900', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('140932', '偏关县', '3', '140900', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('140981', '原平市', '3', '140900', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('141000', '临汾市', '2', '140000', '1', '');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('141001', '市辖区', '3', '141000', '0', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('141002', '尧都区', '3', '141000', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('141021', '曲沃县', '3', '141000', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('141022', '翼城县', '3', '141000', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('141023', '襄汾县', '3', '141000', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('141024', '洪洞县', '3', '141000', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('141025', '古县', '3', '141000', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('141026', '安泽县', '3', '141000', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('141027', '浮山县', '3', '141000', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('141028', '吉县', '3', '141000', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('141029', '乡宁县', '3', '141000', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('141030', '大宁县', '3', '141000', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('141031', '隰县', '3', '141000', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('141032', '永和县', '3', '141000', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('141033', '蒲县', '3', '141000', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('141034', '汾西县', '3', '141000', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('141081', '侯马市', '3', '141000', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('141082', '霍州市', '3', '141000', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('141100', '吕梁市', '2', '140000', '1', '');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('141101', '市辖区', '3', '141100', '0', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('141102', '离石区', '3', '141100', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('141121', '文水县', '3', '141100', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('141122', '交城县', '3', '141100', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('141123', '兴县', '3', '141100', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('141124', '临县', '3', '141100', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('141125', '柳林县', '3', '141100', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('141126', '石楼县', '3', '141100', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('141127', '岚县', '3', '141100', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('141128', '方山县', '3', '141100', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('141129', '中阳县', '3', '141100', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('141130', '交口县', '3', '141100', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('141181', '孝义市', '3', '141100', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('141182', '汾阳市', '3', '141100', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('150000', '内蒙古自治区', '1', '0', '1', '');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('150100', '呼和浩特市', '2', '150000', '1', '');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('150101', '市辖区', '3', '150100', '0', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('150102', '新城区', '3', '150100', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('150103', '回民区', '3', '150100', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('150104', '玉泉区', '3', '150100', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('150105', '赛罕区', '3', '150100', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('150121', '土默特左旗', '3', '150100', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('150122', '托克托县', '3', '150100', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('150123', '和林格尔县', '3', '150100', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('150124', '清水河县', '3', '150100', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('150125', '武川县', '3', '150100', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('150200', '包头市', '2', '150000', '1', '');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('150201', '市辖区', '3', '150200', '0', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('150202', '东河区', '3', '150200', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('150203', '昆都仑区', '3', '150200', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('150204', '青山区', '3', '150200', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('150205', '石拐区', '3', '150200', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('150206', '白云矿区', '3', '150200', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('150207', '九原区', '3', '150200', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('150221', '土默特右旗', '3', '150200', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('150222', '固阳县', '3', '150200', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('150223', '达尔罕茂明安联合旗', '3', '150200', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('150300', '乌海市', '2', '150000', '1', '');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('150301', '市辖区', '3', '150300', '0', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('150302', '海勃湾区', '3', '150300', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('150303', '海南区', '3', '150300', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('150304', '乌达区', '3', '150300', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('150400', '赤峰市', '2', '150000', '1', '');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('150401', '市辖区', '3', '150400', '0', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('150402', '红山区', '3', '150400', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('150403', '元宝山区', '3', '150400', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('150404', '松山区', '3', '150400', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('150421', '阿鲁科尔沁旗', '3', '150400', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('150422', '巴林左旗', '3', '150400', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('150423', '巴林右旗', '3', '150400', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('150424', '林西县', '3', '150400', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('150425', '克什克腾旗', '3', '150400', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('150426', '翁牛特旗', '3', '150400', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('150428', '喀喇沁旗', '3', '150400', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('150429', '宁城县', '3', '150400', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('150430', '敖汉旗', '3', '150400', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('150500', '通辽市', '2', '150000', '1', '');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('150501', '市辖区', '3', '150500', '0', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('150502', '科尔沁区', '3', '150500', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('150521', '科尔沁左翼中旗', '3', '150500', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('150522', '科尔沁左翼后旗', '3', '150500', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('150523', '开鲁县', '3', '150500', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('150524', '库伦旗', '3', '150500', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('150525', '奈曼旗', '3', '150500', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('150526', '扎鲁特旗', '3', '150500', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('150581', '霍林郭勒市', '3', '150500', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('150600', '鄂尔多斯市', '2', '150000', '1', '');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('150601', '市辖区', '3', '150600', '0', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('150602', '  东胜区', '3', '150600', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('150621', '达拉特旗', '3', '150600', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('150622', '准格尔旗', '3', '150600', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('150623', '鄂托克前旗', '3', '150600', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('150624', '鄂托克旗', '3', '150600', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('150625', '杭锦旗', '3', '150600', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('150626', '乌审旗', '3', '150600', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('150627', '伊金霍洛旗', '3', '150600', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('150700', '呼伦贝尔市', '2', '150000', '1', '');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('150701', '市辖区', '3', '150700', '0', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('150702', '海拉尔区', '3', '150700', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('150721', '阿荣旗', '3', '150700', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('150722', '莫力达瓦达斡尔族自治旗', '3', '150700', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('150723', '鄂伦春自治旗', '3', '150700', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('150724', '鄂温克族自治旗', '3', '150700', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('150725', '陈巴尔虎旗', '3', '150700', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('150726', '新巴尔虎左旗', '3', '150700', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('150727', '新巴尔虎右旗', '3', '150700', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('150781', '满洲里市', '3', '150700', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('150782', '牙克石市', '3', '150700', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('150783', '扎兰屯市', '3', '150700', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('150784', '额尔古纳市', '3', '150700', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('150785', '根河市', '3', '150700', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('150800', '巴彦淖尔市', '2', '150000', '1', '');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('150801', '市辖区', '3', '150800', '0', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('150802', '临河区', '3', '150800', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('150821', '五原县', '3', '150800', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('150822', '磴口县', '3', '150800', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('150823', '乌拉特前旗', '3', '150800', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('150824', '乌拉特中旗', '3', '150800', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('150825', '乌拉特后旗', '3', '150800', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('150826', '杭锦后旗', '3', '150800', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('150900', '乌兰察布市', '2', '150000', '1', '');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('150901', '市辖区', '3', '150900', '0', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('150902', '集宁区', '3', '150900', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('150921', '卓资县', '3', '150900', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('150922', '化德县', '3', '150900', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('150923', '商都县', '3', '150900', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('150924', '兴和县', '3', '150900', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('150925', '凉城县', '3', '150900', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('150926', '察哈尔右翼前旗', '3', '150900', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('150927', '察哈尔右翼中旗', '3', '150900', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('150928', '察哈尔右翼后旗', '3', '150900', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('150929', '四子王旗', '3', '150900', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('150981', '丰镇市', '3', '150900', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('152200', '兴安盟', '2', '150000', '1', '');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('152201', '乌兰浩特市', '3', '152200', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('152202', '阿尔山市', '3', '152200', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('152221', '科尔沁右翼前旗', '3', '152200', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('152222', '科尔沁右翼中旗', '3', '152200', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('152223', '扎赉特旗', '3', '152200', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('152224', '突泉县', '3', '152200', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('152500', '锡林郭勒盟', '2', '150000', '1', '');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('152501', '二连浩特市', '3', '152500', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('152502', '锡林浩特市', '3', '152500', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('152522', '阿巴嘎旗', '3', '152500', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('152523', '苏尼特左旗', '3', '152500', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('152524', '苏尼特右旗', '3', '152500', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('152525', '东乌珠穆沁旗', '3', '152500', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('152526', '西乌珠穆沁旗', '3', '152500', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('152527', '太仆寺旗', '3', '152500', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('152528', '镶黄旗', '3', '152500', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('152529', '正镶白旗', '3', '152500', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('152530', '正蓝旗', '3', '152500', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('152531', '多伦县', '3', '152500', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('152900', '阿拉善盟', '2', '150000', '1', '');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('152921', '阿拉善左旗', '3', '152900', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('152922', '阿拉善右旗', '3', '152900', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('152923', '额济纳旗', '3', '152900', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('210000', '辽宁省', '1', '0', '1', '');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('210100', '沈阳市', '2', '210000', '1', '');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('210101', '市辖区', '3', '210100', '0', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('210102', '和平区', '3', '210100', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('210103', '沈河区', '3', '210100', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('210104', '大东区', '3', '210100', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('210105', '皇姑区', '3', '210100', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('210106', '铁西区', '3', '210100', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('210111', '苏家屯区', '3', '210100', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('210112', '东陵区', '3', '210100', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('210113', '沈北新区', '3', '210100', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('210114', '于洪区', '3', '210100', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('210122', '辽中县', '3', '210100', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('210123', '康平县', '3', '210100', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('210124', '法库县', '3', '210100', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('210181', '新民市', '3', '210100', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('210200', '大连市', '2', '210000', '1', '');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('210201', '市辖区', '3', '210200', '0', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('210202', '中山区', '3', '210200', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('210203', '西岗区', '3', '210200', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('210204', '沙河口区', '3', '210200', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('210211', '甘井子区', '3', '210200', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('210212', '旅顺口区', '3', '210200', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('210213', '金州区', '3', '210200', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('210224', '长海县', '3', '210200', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('210281', '瓦房店市', '3', '210200', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('210282', '普兰店市', '3', '210200', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('210283', '庄河市', '3', '210200', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('210300', '鞍山市', '2', '210000', '1', '');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('210301', '市辖区', '3', '210300', '0', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('210302', '铁东区', '3', '210300', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('210303', '铁西区', '3', '210300', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('210304', '立山区', '3', '210300', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('210311', '千山区', '3', '210300', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('210321', '台安县', '3', '210300', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('210323', '岫岩满族自治县', '3', '210300', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('210381', '海城市', '3', '210300', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('210400', '抚顺市', '2', '210000', '1', '');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('210401', '市辖区', '3', '210400', '0', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('210402', '新抚区', '3', '210400', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('210403', '东洲区', '3', '210400', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('210404', '望花区', '3', '210400', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('210411', '顺城区', '3', '210400', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('210421', '抚顺县', '3', '210400', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('210422', '新宾满族自治县', '3', '210400', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('210423', '清原满族自治县', '3', '210400', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('210500', '本溪市', '2', '210000', '1', '');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('210501', '市辖区', '3', '210500', '0', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('210502', '平山区', '3', '210500', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('210503', '溪湖区', '3', '210500', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('210504', '明山区', '3', '210500', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('210505', '南芬区', '3', '210500', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('210521', '本溪满族自治县', '3', '210500', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('210522', '桓仁满族自治县', '3', '210500', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('210600', '丹东市', '2', '210000', '1', '');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('210601', '市辖区', '3', '210600', '0', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('210602', '元宝区', '3', '210600', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('210603', '振兴区', '3', '210600', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('210604', '振安区', '3', '210600', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('210624', '宽甸满族自治县', '3', '210600', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('210681', '东港市', '3', '210600', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('210682', '凤城市', '3', '210600', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('210700', '锦州市', '2', '210000', '1', '');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('210701', '市辖区', '3', '210700', '0', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('210702', '古塔区', '3', '210700', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('210703', '凌河区', '3', '210700', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('210711', '太和区', '3', '210700', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('210726', '黑山县', '3', '210700', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('210727', '义县', '3', '210700', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('210781', '凌海市', '3', '210700', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('210782', '北镇市', '3', '210700', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('210800', '营口市', '2', '210000', '1', '');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('210801', '市辖区', '3', '210800', '0', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('210802', '站前区', '3', '210800', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('210803', '西市区', '3', '210800', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('210804', '鲅鱼圈区', '3', '210800', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('210811', '老边区', '3', '210800', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('210881', '盖州市', '3', '210800', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('210882', '大石桥市', '3', '210800', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('210900', '阜新市', '2', '210000', '1', '');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('210901', '市辖区', '3', '210900', '0', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('210902', '海州区', '3', '210900', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('210903', '新邱区', '3', '210900', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('210904', '太平区', '3', '210900', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('210905', '清河门区', '3', '210900', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('210911', '细河区', '3', '210900', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('210921', '阜新蒙古族自治县', '3', '210900', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('210922', '彰武县', '3', '210900', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('211000', '辽阳市', '2', '210000', '1', '');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('211001', '市辖区', '3', '211000', '0', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('211002', '白塔区', '3', '211000', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('211003', '文圣区', '3', '211000', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('211004', '宏伟区', '3', '211000', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('211005', '弓长岭区', '3', '211000', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('211011', '太子河区', '3', '211000', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('211021', '辽阳县', '3', '211000', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('211081', '灯塔市', '3', '211000', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('211100', '盘锦市', '2', '210000', '1', '');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('211101', '市辖区', '3', '211100', '0', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('211102', '双台子区', '3', '211100', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('211103', '兴隆台区', '3', '211100', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('211121', '大洼县', '3', '211100', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('211122', '盘山县', '3', '211100', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('211200', '铁岭市', '2', '210000', '1', '');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('211201', '市辖区', '3', '211200', '0', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('211202', '银州区', '3', '211200', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('211204', '清河区', '3', '211200', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('211221', '铁岭县', '3', '211200', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('211223', '西丰县', '3', '211200', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('211224', '昌图县', '3', '211200', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('211281', '调兵山市', '3', '211200', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('211282', '开原市', '3', '211200', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('211300', '朝阳市', '2', '210000', '1', '');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('211301', '市辖区', '3', '211300', '0', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('211302', '双塔区', '3', '211300', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('211303', '龙城区', '3', '211300', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('211321', '朝阳县', '3', '211300', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('211322', '建平县', '3', '211300', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('211324', '喀喇沁左翼蒙古族自治县', '3', '211300', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('211381', '北票市', '3', '211300', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('211382', '凌源市', '3', '211300', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('211400', '葫芦岛市', '2', '210000', '1', '');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('211401', '市辖区', '3', '211400', '0', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('211402', '连山区', '3', '211400', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('211403', '龙港区', '3', '211400', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('211404', '南票区', '3', '211400', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('211421', '绥中县', '3', '211400', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('211422', '建昌县', '3', '211400', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('211481', '兴城市', '3', '211400', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('220000', '吉林省', '1', '0', '1', '');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('220100', '长春市', '2', '220000', '1', '');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('220101', '市辖区', '3', '220100', '0', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('220102', '南关区', '3', '220100', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('220103', '宽城区', '3', '220100', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('220104', '朝阳区', '3', '220100', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('220105', '二道区', '3', '220100', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('220106', '绿园区', '3', '220100', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('220112', '双阳区', '3', '220100', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('220122', '农安县', '3', '220100', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('220181', '九台市', '3', '220100', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('220182', '榆树市', '3', '220100', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('220183', '德惠市', '3', '220100', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('220200', '吉林市', '2', '220000', '1', '');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('220201', '市辖区', '3', '220200', '0', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('220202', '昌邑区', '3', '220200', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('220203', '龙潭区', '3', '220200', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('220204', '船营区', '3', '220200', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('220211', '丰满区', '3', '220200', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('220221', '永吉县', '3', '220200', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('220281', '蛟河市', '3', '220200', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('220282', '桦甸市', '3', '220200', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('220283', '舒兰市', '3', '220200', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('220284', '磐石市', '3', '220200', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('220300', '四平市', '2', '220000', '1', '');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('220301', '市辖区', '3', '220300', '0', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('220302', '铁西区', '3', '220300', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('220303', '铁东区', '3', '220300', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('220322', '梨树县', '3', '220300', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('220323', '伊通满族自治县', '3', '220300', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('220381', '公主岭市', '3', '220300', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('220382', '双辽市', '3', '220300', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('220400', '辽源市', '2', '220000', '1', '');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('220401', '市辖区', '3', '220400', '0', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('220402', '龙山区', '3', '220400', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('220403', '西安区', '3', '220400', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('220421', '东丰县', '3', '220400', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('220422', '东辽县', '3', '220400', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('220500', '通化市', '2', '220000', '1', '');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('220501', '市辖区', '3', '220500', '0', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('220502', '东昌区', '3', '220500', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('220503', '二道江区', '3', '220500', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('220521', '通化县', '3', '220500', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('220523', '辉南县', '3', '220500', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('220524', '柳河县', '3', '220500', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('220581', '梅河口市', '3', '220500', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('220582', '集安市', '3', '220500', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('220600', '白山市', '2', '220000', '1', '');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('220601', '市辖区', '3', '220600', '0', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('220602', '八道江区', '3', '220600', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('220605', '  江源区', '3', '220600', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('220621', '抚松县', '3', '220600', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('220622', '靖宇县', '3', '220600', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('220623', '长白朝鲜族自治县', '3', '220600', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('220681', '临江市', '3', '220600', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('220700', '松原市', '2', '220000', '1', '');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('220701', '市辖区', '3', '220700', '0', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('220702', '宁江区', '3', '220700', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('220721', '前郭尔罗斯蒙古族自治县', '3', '220700', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('220722', '长岭县', '3', '220700', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('220723', '乾安县', '3', '220700', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('220724', '扶余县', '3', '220700', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('220800', '白城市', '2', '220000', '1', '');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('220801', '市辖区', '3', '220800', '0', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('220802', '洮北区', '3', '220800', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('220821', '镇赉县', '3', '220800', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('220822', '通榆县', '3', '220800', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('220881', '洮南市', '3', '220800', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('220882', '大安市', '3', '220800', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('222400', '延边朝鲜族自治州', '2', '220000', '1', '');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('222401', '延吉市', '3', '222400', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('222402', '图们市', '3', '222400', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('222403', '敦化市', '3', '222400', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('222404', '珲春市', '3', '222400', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('222405', '龙井市', '3', '222400', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('222406', '和龙市', '3', '222400', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('222424', '汪清县', '3', '222400', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('222426', '安图县', '3', '222400', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('230000', '黑龙江省', '1', '0', '1', '');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('230100', '哈尔滨市', '2', '230000', '1', '');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('230101', '市辖区', '3', '230100', '0', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('230102', '道里区', '3', '230100', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('230103', '南岗区', '3', '230100', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('230104', '道外区', '3', '230100', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('230108', '平房区', '3', '230100', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('230109', '松北区', '3', '230100', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('230110', '香坊区', '3', '230100', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('230111', '呼兰区', '3', '230100', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('230112', '阿城区', '3', '230100', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('230123', '依兰县', '3', '230100', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('230124', '方正县', '3', '230100', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('230125', '宾县', '3', '230100', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('230126', '巴彦县', '3', '230100', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('230127', '木兰县', '3', '230100', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('230128', '通河县', '3', '230100', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('230129', '延寿县', '3', '230100', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('230182', '双城市', '3', '230100', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('230183', '尚志市', '3', '230100', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('230184', '五常市', '3', '230100', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('230200', '齐齐哈尔市', '2', '230000', '1', '');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('230201', '市辖区', '3', '230200', '0', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('230202', '龙沙区', '3', '230200', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('230203', '建华区', '3', '230200', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('230204', '铁锋区', '3', '230200', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('230205', '昂昂溪区', '3', '230200', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('230206', '富拉尔基区', '3', '230200', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('230207', '碾子山区', '3', '230200', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('230208', '梅里斯达斡尔族区', '3', '230200', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('230221', '龙江县', '3', '230200', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('230223', '依安县', '3', '230200', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('230224', '泰来县', '3', '230200', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('230225', '甘南县', '3', '230200', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('230227', '富裕县', '3', '230200', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('230229', '克山县', '3', '230200', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('230230', '克东县', '3', '230200', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('230231', '拜泉县', '3', '230200', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('230281', '讷河市', '3', '230200', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('230300', '鸡西市', '2', '230000', '1', '');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('230301', '市辖区', '3', '230300', '0', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('230302', '鸡冠区', '3', '230300', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('230303', '恒山区', '3', '230300', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('230304', '滴道区', '3', '230300', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('230305', '梨树区', '3', '230300', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('230306', '城子河区', '3', '230300', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('230307', '麻山区', '3', '230300', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('230321', '鸡东县', '3', '230300', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('230381', '虎林市', '3', '230300', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('230382', '密山市', '3', '230300', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('230400', '鹤岗市', '2', '230000', '1', '');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('230401', '市辖区', '3', '230400', '0', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('230402', '向阳区', '3', '230400', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('230403', '工农区', '3', '230400', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('230404', '南山区', '3', '230400', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('230405', '兴安区', '3', '230400', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('230406', '东山区', '3', '230400', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('230407', '兴山区', '3', '230400', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('230421', '萝北县', '3', '230400', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('230422', '绥滨县', '3', '230400', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('230500', '双鸭山市', '2', '230000', '1', '');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('230501', '市辖区', '3', '230500', '0', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('230502', '尖山区', '3', '230500', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('230503', '岭东区', '3', '230500', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('230505', '四方台区', '3', '230500', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('230506', '宝山区', '3', '230500', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('230521', '集贤县', '3', '230500', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('230522', '友谊县', '3', '230500', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('230523', '宝清县', '3', '230500', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('230524', '饶河县', '3', '230500', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('230600', '大庆市', '2', '230000', '1', '');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('230601', '市辖区', '3', '230600', '0', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('230602', '萨尔图区', '3', '230600', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('230603', '龙凤区', '3', '230600', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('230604', '让胡路区', '3', '230600', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('230605', '红岗区', '3', '230600', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('230606', '大同区', '3', '230600', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('230621', '肇州县', '3', '230600', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('230622', '肇源县', '3', '230600', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('230623', '林甸县', '3', '230600', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('230624', '杜尔伯特蒙古族自治县', '3', '230600', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('230700', '伊春市', '2', '230000', '1', '');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('230701', '市辖区', '3', '230700', '0', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('230702', '伊春区', '3', '230700', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('230703', '南岔区', '3', '230700', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('230704', '友好区', '3', '230700', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('230705', '西林区', '3', '230700', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('230706', '翠峦区', '3', '230700', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('230707', '新青区', '3', '230700', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('230708', '美溪区', '3', '230700', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('230709', '金山屯区', '3', '230700', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('230710', '五营区', '3', '230700', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('230711', '乌马河区', '3', '230700', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('230712', '汤旺河区', '3', '230700', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('230713', '带岭区', '3', '230700', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('230714', '乌伊岭区', '3', '230700', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('230715', '红星区', '3', '230700', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('230716', '上甘岭区', '3', '230700', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('230722', '嘉荫县', '3', '230700', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('230781', '铁力市', '3', '230700', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('230800', '佳木斯市', '2', '230000', '1', '');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('230801', '市辖区', '3', '230800', '0', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('230803', '向阳区', '3', '230800', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('230804', '前进区', '3', '230800', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('230805', '东风区', '3', '230800', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('230811', '郊区', '3', '230800', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('230822', '桦南县', '3', '230800', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('230826', '桦川县', '3', '230800', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('230828', '汤原县', '3', '230800', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('230833', '抚远县', '3', '230800', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('230881', '同江市', '3', '230800', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('230882', '富锦市', '3', '230800', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('230900', '七台河市', '2', '230000', '1', '');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('230901', '市辖区', '3', '230900', '0', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('230902', '新兴区', '3', '230900', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('230903', '桃山区', '3', '230900', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('230904', '茄子河区', '3', '230900', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('230921', '勃利县', '3', '230900', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('231000', '牡丹江市', '2', '230000', '1', '');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('231001', '市辖区', '3', '231000', '0', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('231002', '东安区', '3', '231000', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('231003', '阳明区', '3', '231000', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('231004', '爱民区', '3', '231000', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('231005', '西安区', '3', '231000', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('231024', '东宁县', '3', '231000', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('231025', '林口县', '3', '231000', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('231081', '绥芬河市', '3', '231000', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('231083', '海林市', '3', '231000', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('231084', '宁安市', '3', '231000', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('231085', '穆棱市', '3', '231000', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('231100', '黑河市', '2', '230000', '1', '');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('231101', '市辖区', '3', '231100', '0', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('231102', '爱辉区', '3', '231100', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('231121', '嫩江县', '3', '231100', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('231123', '逊克县', '3', '231100', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('231124', '孙吴县', '3', '231100', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('231181', '北安市', '3', '231100', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('231182', '五大连池市', '3', '231100', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('231200', '绥化市', '2', '230000', '1', '');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('231201', '市辖区', '3', '231200', '0', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('231202', '北林区', '3', '231200', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('231221', '望奎县', '3', '231200', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('231222', '兰西县', '3', '231200', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('231223', '青冈县', '3', '231200', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('231224', '庆安县', '3', '231200', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('231225', '明水县', '3', '231200', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('231226', '绥棱县', '3', '231200', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('231281', '安达市', '3', '231200', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('231282', '肇东市', '3', '231200', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('231283', '海伦市', '3', '231200', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('232700', '大兴安岭地区', '2', '230000', '1', '');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('232701', '加格达奇区', '3', '232700', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('232702', '松岭区', '3', '232700', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('232703', '新林区', '3', '232700', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('232704', '呼中区', '3', '232700', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('232721', '呼玛县', '3', '232700', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('232722', '塔河县', '3', '232700', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('232723', '漠河县', '3', '232700', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('310000', '上海市', '1', '0', '1', '');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('310100', '市辖区', '2', '310000', '0', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('310101', '黄浦区', '2', '310000', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('310103', '卢湾区', '2', '310000', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('310104', '徐汇区', '2', '310000', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('310105', '长宁区', '2', '310000', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('310106', '静安区', '2', '310000', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('310107', '普陀区', '2', '310000', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('310108', '闸北区', '2', '310000', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('310109', '虹口区', '2', '310000', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('310110', '杨浦区', '2', '310000', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('310112', '闵行区', '2', '310000', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('310113', '宝山区', '2', '310000', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('310114', '嘉定区', '2', '310000', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('310115', '浦东新区', '2', '310000', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('310116', '金山区', '2', '310000', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('310117', '松江区', '2', '310000', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('310118', '青浦区', '2', '310000', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('310119', '南汇区', '2', '310000', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('310120', '奉贤区', '2', '310000', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('310230', '崇明县', '2', '310000', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('320000', '江苏省', '1', '0', '1', '');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('320100', '南京市', '2', '320000', '1', '');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('320101', '市辖区', '3', '320100', '0', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('320102', '玄武区', '3', '320100', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('320103', '白下区', '3', '320100', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('320104', '秦淮区', '3', '320100', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('320105', '建邺区', '3', '320100', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('320106', '鼓楼区', '3', '320100', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('320107', '下关区', '3', '320100', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('320111', '浦口区', '3', '320100', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('320113', '栖霞区', '3', '320100', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('320114', '雨花台区', '3', '320100', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('320115', '江宁区', '3', '320100', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('320116', '六合区', '3', '320100', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('320124', '溧水县', '3', '320100', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('320125', '高淳县', '3', '320100', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('320200', '无锡市', '2', '320000', '1', '');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('320201', '市辖区', '3', '320200', '0', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('320202', '崇安区', '3', '320200', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('320203', '南长区', '3', '320200', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('320204', '北塘区', '3', '320200', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('320205', '锡山区', '3', '320200', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('320206', '惠山区', '3', '320200', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('320211', '滨湖区', '3', '320200', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('320281', '江阴市', '3', '320200', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('320282', '宜兴市', '3', '320200', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('320300', '徐州市', '2', '320000', '1', '');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('320301', '市辖区', '3', '320300', '0', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('320302', '鼓楼区', '3', '320300', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('320303', '云龙区', '3', '320300', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('320304', '九里区', '3', '320300', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('320305', '贾汪区', '3', '320300', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('320311', '泉山区', '3', '320300', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('320321', '丰县', '3', '320300', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('320322', '沛县', '3', '320300', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('320323', '铜山县', '3', '320300', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('320324', '睢宁县', '3', '320300', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('320381', '新沂市', '3', '320300', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('320382', '邳州市', '3', '320300', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('320400', '常州市', '2', '320000', '1', '');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('320401', '市辖区', '3', '320400', '0', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('320402', '天宁区', '3', '320400', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('320404', '钟楼区', '3', '320400', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('320405', '戚墅堰区', '3', '320400', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('320411', '新北区', '3', '320400', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('320412', '武进区', '3', '320400', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('320481', '溧阳市', '3', '320400', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('320482', '金坛市', '3', '320400', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('320500', '苏州市', '2', '320000', '1', '');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('320501', '市辖区', '3', '320500', '0', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('320502', '沧浪区', '3', '320500', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('320503', '平江区', '3', '320500', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('320504', '金阊区', '3', '320500', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('320505', '虎丘区', '3', '320500', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('320506', '吴中区', '3', '320500', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('320507', '相城区', '3', '320500', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('320581', '常熟市', '3', '320500', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('320582', '张家港市', '3', '320500', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('320583', '昆山市', '3', '320500', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('320584', '吴江市', '3', '320500', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('320585', '太仓市', '3', '320500', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('320600', '南通市', '2', '320000', '1', '');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('320601', '市辖区', '3', '320600', '0', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('320602', '崇川区', '3', '320600', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('320611', '港闸区', '3', '320600', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('320621', '海安县', '3', '320600', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('320623', '如东县', '3', '320600', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('320681', '启东市', '3', '320600', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('320682', '如皋市', '3', '320600', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('320683', '通州市', '3', '320600', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('320684', '海门市', '3', '320600', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('320700', '连云港市', '2', '320000', '1', '');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('320701', '市辖区', '3', '320700', '0', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('320703', '连云区', '3', '320700', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('320705', '新浦区', '3', '320700', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('320706', '海州区', '3', '320700', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('320721', '赣榆县', '3', '320700', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('320722', '东海县', '3', '320700', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('320723', '灌云县', '3', '320700', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('320724', '灌南县', '3', '320700', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('320800', '淮安市', '2', '320000', '1', '');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('320801', '市辖区', '3', '320800', '0', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('320802', '清河区', '3', '320800', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('320803', '楚州区', '3', '320800', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('320804', '淮阴区', '3', '320800', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('320811', '清浦区', '3', '320800', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('320826', '涟水县', '3', '320800', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('320829', '洪泽县', '3', '320800', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('320830', '盱眙县', '3', '320800', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('320831', '金湖县', '3', '320800', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('320900', '盐城市', '2', '320000', '1', '');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('320901', '市辖区', '3', '320900', '0', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('320902', '亭湖区', '3', '320900', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('320903', '盐都区', '3', '320900', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('320921', '响水县', '3', '320900', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('320922', '滨海县', '3', '320900', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('320923', '阜宁县', '3', '320900', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('320924', '射阳县', '3', '320900', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('320925', '建湖县', '3', '320900', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('320981', '东台市', '3', '320900', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('320982', '大丰市', '3', '320900', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('321000', '扬州市', '2', '320000', '1', '');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('321001', '市辖区', '3', '321000', '0', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('321002', '广陵区', '3', '321000', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('321003', '邗江区', '3', '321000', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('321011', '维扬区', '3', '321000', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('321023', '宝应县', '3', '321000', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('321081', '仪征市', '3', '321000', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('321084', '高邮市', '3', '321000', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('321088', '江都市', '3', '321000', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('321100', '镇江市', '2', '320000', '1', '');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('321101', '市辖区', '3', '321100', '0', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('321102', '京口区', '3', '321100', '1', '1');
INSERT INTO sys_area (area_no, area_name, lev, uplev, area_sts, if_leaf) VALUES ('321111', '润州区', '3', '321100', '1', '1');

INSERT INTO sys_menu (menu_id, parent_id, name, url, perms, type, icon, order_num, open_mode) VALUES (1, 0, '系统管理', '', '', 0, 'fa fa-cog', 99, '_self');
INSERT INTO sys_menu (menu_id, parent_id, name, url, perms, type, icon, order_num, open_mode) VALUES (2, 1, '管理员管理', 'modules/sys/user.html', '', 1, 'fa fa-user', 1, '_self');
INSERT INTO sys_menu (menu_id, parent_id, name, url, perms, type, icon, order_num, open_mode) VALUES (3, 1, '角色管理', 'modules/sys/role.html', '', 1, 'fa fa-user-secret', 2, '_self');
INSERT INTO sys_menu (menu_id, parent_id, name, url, perms, type, icon, order_num, open_mode) VALUES (4, 1, '菜单管理', 'modules/sys/menu.html', '', 1, 'fa fa-th-list', 3, '_self');
INSERT INTO sys_menu (menu_id, parent_id, name, url, perms, type, icon, order_num, open_mode) VALUES (5, 1, 'SQL监控', 'druid/sql.html', '', 1, 'fa fa-bug', 4, '_self');
INSERT INTO sys_menu (menu_id, parent_id, name, url, perms, type, icon, order_num, open_mode) VALUES (15, 2, '查看', '', 'sys:user:list,sys:user:info', 2, '', 0, '_self');
INSERT INTO sys_menu (menu_id, parent_id, name, url, perms, type, icon, order_num, open_mode) VALUES (16, 2, '新增', '', 'sys:user:save,sys:role:select', 2, '', 0, '_self');
INSERT INTO sys_menu (menu_id, parent_id, name, url, perms, type, icon, order_num, open_mode) VALUES (17, 2, '修改', '', 'sys:user:update,sys:role:select', 2, '', 0, '_self');
INSERT INTO sys_menu (menu_id, parent_id, name, url, perms, type, icon, order_num, open_mode) VALUES (18, 2, '删除', '', 'sys:user:delete', 2, '', 0, '_self');
INSERT INTO sys_menu (menu_id, parent_id, name, url, perms, type, icon, order_num, open_mode) VALUES (19, 3, '查看', '', 'sys:role:list,sys:role:info', 2, '', 0, '_self');
INSERT INTO sys_menu (menu_id, parent_id, name, url, perms, type, icon, order_num, open_mode) VALUES (20, 3, '新增', '', 'sys:role:save,sys:menu:perms', 2, '', 0, '_self');
INSERT INTO sys_menu (menu_id, parent_id, name, url, perms, type, icon, order_num, open_mode) VALUES (21, 3, '修改', '', 'sys:role:update,sys:menu:perms', 2, '', 0, '_self');
INSERT INTO sys_menu (menu_id, parent_id, name, url, perms, type, icon, order_num, open_mode) VALUES (22, 3, '删除', '', 'sys:role:delete', 2, '', 0, '_self');
INSERT INTO sys_menu (menu_id, parent_id, name, url, perms, type, icon, order_num, open_mode) VALUES (23, 4, '查看', '', 'sys:menu:list,sys:menu:info', 2, '', 0, '_self');
INSERT INTO sys_menu (menu_id, parent_id, name, url, perms, type, icon, order_num, open_mode) VALUES (24, 4, '新增', '', 'sys:menu:save,sys:menu:select', 2, '', 0, '_self');
INSERT INTO sys_menu (menu_id, parent_id, name, url, perms, type, icon, order_num, open_mode) VALUES (25, 4, '修改', '', 'sys:menu:update,sys:menu:select', 2, '', 0, '_self');
INSERT INTO sys_menu (menu_id, parent_id, name, url, perms, type, icon, order_num, open_mode) VALUES (26, 4, '删除', '', 'sys:menu:delete', 2, '', 0, '_self');
INSERT INTO sys_menu (menu_id, parent_id, name, url, perms, type, icon, order_num, open_mode) VALUES (27, 1, '参数管理', 'modules/sys/config.html', 'sys:config:list,sys:config:info,sys:config:save,sys:config:update,sys:config:delete', 1, 'fa fa-sun-o', 6, '_self');
INSERT INTO sys_menu (menu_id, parent_id, name, url, perms, type, icon, order_num, open_mode) VALUES (29, 1, '系统日志', 'modules/sys/log.html', 'sys:log:list', 1, 'fa fa-file-text-o', 7, '_self');
INSERT INTO sys_menu (menu_id, parent_id, name, url, perms, type, icon, order_num, open_mode) VALUES (31, 1, '部门管理', 'modules/sys/dept.html', '', 1, 'fa fa-file-code-o', 1, '_self');
INSERT INTO sys_menu (menu_id, parent_id, name, url, perms, type, icon, order_num, open_mode) VALUES (32, 31, '查看', '', 'sys:dept:list,sys:dept:info', 2, '', 0, '_self');
INSERT INTO sys_menu (menu_id, parent_id, name, url, perms, type, icon, order_num, open_mode) VALUES (33, 31, '新增', '', 'sys:dept:save,sys:dept:select', 2, '', 0, '_self');
INSERT INTO sys_menu (menu_id, parent_id, name, url, perms, type, icon, order_num, open_mode) VALUES (34, 31, '修改', '', 'sys:dept:update,sys:dept:select', 2, '', 0, '_self');
INSERT INTO sys_menu (menu_id, parent_id, name, url, perms, type, icon, order_num, open_mode) VALUES (35, 31, '删除', '', 'sys:dept:delete', 2, '', 0, '_self');
INSERT INTO sys_menu (menu_id, parent_id, name, url, perms, type, icon, order_num, open_mode) VALUES (36, 1, '字典管理', 'modules/sys/dict.html', '', 1, 'fa fa-bookmark-o', 6, '_self');
INSERT INTO sys_menu (menu_id, parent_id, name, url, perms, type, icon, order_num, open_mode) VALUES (37, 36, '查看', '', 'sys:dict:list,sys:dict:info', 2, '', 6, '_self');
INSERT INTO sys_menu (menu_id, parent_id, name, url, perms, type, icon, order_num, open_mode) VALUES (38, 36, '新增', '', 'sys:dict:save', 2, '', 6, '_self');
INSERT INTO sys_menu (menu_id, parent_id, name, url, perms, type, icon, order_num, open_mode) VALUES (39, 36, '修改', '', 'sys:dict:update', 2, '', 6, '_self');
INSERT INTO sys_menu (menu_id, parent_id, name, url, perms, type, icon, order_num, open_mode) VALUES (40, 36, '删除', '', 'sys:dict:delete', 2, '', 6, '_self');
INSERT INTO sys_menu (menu_id, parent_id, name, url, perms, type, icon, order_num, open_mode) VALUES (92, 0, '设计器', '', '', 0, 'fa fa-paper-plane', 3, '_self');
INSERT INTO sys_menu (menu_id, parent_id, name, url, perms, type, icon, order_num, open_mode) VALUES (93, 92, '大屏设计器', './modules/bddp/home.html', '', 1, 'fa fa-pencil-square-o', 0, '_blank');
INSERT INTO sys_menu (menu_id, parent_id, name, url, perms, type, icon, order_num, open_mode) VALUES (94, 1, '授权信息', 'AsReport?authMessage=1', 'grant:info', 1, 'fa fa-shield', 99, '_self');
INSERT INTO sys_menu (menu_id, parent_id, name, url, perms, type, icon, order_num, open_mode) VALUES (95, 0, '大屏DEMO', '', '', 0, 'fa fa-tv', 100, '_self');
INSERT INTO sys_menu (menu_id, parent_id, name, url, perms, type, icon, order_num, open_mode) VALUES (96, 95, '三农大数据指挥舱', 'bddpshow/show/c99268a7bdf0a8c8dec37f4e5927910d', '', 1, 'fa fa-eye', 10, '_blank');
INSERT INTO sys_menu (menu_id, parent_id, name, url, perms, type, icon, order_num, open_mode) VALUES (97, 95, '信贷综合业务驾驶舱', 'bddpshow/show/044a1af39843779cde39678289c42240', '', 1, 'fa fa-eye', 10, '_blank');
INSERT INTO sys_menu (menu_id, parent_id, name, url, perms, type, icon, order_num, open_mode) VALUES (98, 95, '综合业务_模板1_首页', 'bddpshow/show/41284e70ef854b0bc215fe95ec9f6aae', '', 1, 'fa fa-eye', 1, '_blank');
INSERT INTO sys_menu (menu_id, parent_id, name, url, perms, type, icon, order_num, open_mode) VALUES (99, 95, '综合业务_模板3_三列样式', 'bddpshow/show/51284e70ef854b0bc215fe95ec9f6aae', '', 1, 'fa fa-eye', 4, '_blank');
INSERT INTO sys_menu (menu_id, parent_id, name, url, perms, type, icon, order_num, open_mode) VALUES (100, 95, '综合业务_模板2_三行模块', 'bddpshow/show/d76cbda028bebf896552816e981c3cc2', '', 1, 'fa fa-eye', 5, '_blank');
INSERT INTO sys_menu (menu_id, parent_id, name, url, perms, type, icon, order_num, open_mode) VALUES (101, 95, '综合业务_模板4_七块', 'bddpshow/show/61284e70ef854b0bc215fe95ec9f6aae', '', 1, 'fa fa-eye', 5, '_blank');
INSERT INTO sys_menu (menu_id, parent_id, name, url, perms, type, icon, order_num, open_mode) VALUES (102, 0, 'BI配置', '', '', 0, 'fa fa-bars', 98, '_self');
INSERT INTO sys_menu (menu_id, parent_id, name, url, perms, type, icon, order_num, open_mode) VALUES (103, 102, '数据集', 'developing.html?num=1', '', 1, '', 0, '_self');
INSERT INTO sys_menu (menu_id, parent_id, name, url, perms, type, icon, order_num, open_mode) VALUES (104, 102, '数据挖掘', 'developing.html?num=2', '', 1, '', 0, '_self');
INSERT INTO sys_menu (menu_id, parent_id, name, url, perms, type, icon, order_num, open_mode) VALUES (105, 95, '业务风险_模板1', 'bddpshow/show/4286e3f913e364025ddc91d74ab7a5ad', '', 1, 'fa fa-eye', 7, '_blank');
INSERT INTO sys_menu (menu_id, parent_id, name, url, perms, type, icon, order_num, open_mode) VALUES (106, 107, '左右轮播1', 'modules/bddp/swiper/swiper.html', '', 1, 'fa fa-eye', 99, '_blank');
INSERT INTO sys_menu (menu_id, parent_id, name, url, perms, type, icon, order_num, open_mode) VALUES (107, 0, '整套报表轮播效果', '', '', 0, 'fa fa-refresh', 101, '_self');
INSERT INTO sys_menu (menu_id, parent_id, name, url, perms, type, icon, order_num, open_mode) VALUES (108, 107, '左右轮播-淡入式', 'modules/bddp/swiper/swiperfade.html', '', 1, 'fa fa-eye', 0, '_blank');
INSERT INTO sys_menu (menu_id, parent_id, name, url, perms, type, icon, order_num, open_mode) VALUES (109, 107, '左右轮播-自动切换', 'modules/bddp/swiper/swiperauto.html', '', 1, 'fa fa-eye', 0, '_blank');
INSERT INTO sys_menu (menu_id, parent_id, name, url, perms, type, icon, order_num, open_mode) VALUES (110, 107, '不规则切换式1', 'modules/bddp/swiper/swiper.html', '', 1, 'fa fa-eye', 0, '_blank');
INSERT INTO sys_menu (menu_id, parent_id, name, url, perms, type, icon, order_num, open_mode) VALUES (111, 95, '公司预览图', 'bddpshow/show/e22d6f7344ea0645b84c60a0a5a57cda', '', 1, 'fa fa-eye', 2, '_blank');
INSERT INTO sys_menu (menu_id, parent_id, name, url, perms, type, icon, order_num, open_mode) VALUES (112, 95, '人力资源', 'bddpshow/show/45eef4da7a8f4b56235e24190acd800f', '', 1, 'fa fa-eye', 9, '_blank');
INSERT INTO sys_menu (menu_id, parent_id, name, url, perms, type, icon, order_num, open_mode) VALUES (113, 0, '报表管理', '', '', 0, 'fa fa-table', 4, '_self');
INSERT INTO sys_menu (menu_id, parent_id, name, url, perms, type, icon, order_num, open_mode) VALUES (114, 92, '网格式报表设计器', './modules/rdp/rdpDesign.html', '', 1, 'fa fa-braille', 0, '_blank');
INSERT INTO sys_menu (menu_id, parent_id, name, url, perms, type, icon, order_num, open_mode) VALUES (115, 113, '网格报表管理', 'modules/rdp/list.html', '', 1, 'fa fa-user', 0, '_self');
INSERT INTO sys_menu (menu_id, parent_id, name, url, perms, type, icon, order_num, open_mode) VALUES (116, 0, '公共数据源配置', 'modules/ser/config/rdpDataConfig.html', '', 1, 'fa fa-database', 0, '_self');
INSERT INTO sys_menu (menu_id, parent_id, name, url, perms, type, icon, order_num, open_mode) VALUES (117, 0, '网格报表DEMO', '', '', 0, 'fa fa-table', 99, '_self');
INSERT INTO sys_menu (menu_id, parent_id, name, url, perms, type, icon, order_num, open_mode) VALUES (118, 117, '交叉-客户与产品交叉报表', 'rdppage/show/1ea3e7ef2e8d9bd9a44ba3f24a1417de', '', 1, 'fa fa-eye', 2, '_self');
INSERT INTO sys_menu (menu_id, parent_id, name, url, perms, type, icon, order_num, open_mode) VALUES (119, 117, '分组-地域客户信息报表', 'rdppage/show/04c65e333d6c8cf1e006c054f8d6158b', '', 1, 'fa fa-eye', 2, '_self');
INSERT INTO sys_menu (menu_id, parent_id, name, url, perms, type, icon, order_num, open_mode) VALUES (120, 117, '分块-用户信息', 'rdppage/show/b0f44689bd804c43d59d85871a99711c', '', 1, 'fa fa-eye', 2, '_self');
INSERT INTO sys_menu (menu_id, parent_id, name, url, perms, type, icon, order_num, open_mode) VALUES (121, 117, '详情-入库通知书', 'rdppage/main/f001db5305e400fe28bb5f3ebac7e451', '', 1, 'fa fa-eye', 2, '_self');
INSERT INTO sys_menu (menu_id, parent_id, name, url, perms, type, icon, order_num, open_mode) VALUES (122, 117, '动态-水电费统计', 'rdppage/show/f004ff76e9e10b6b7d4ecb396608ee0a', '', 1, 'fa fa-eye', 2, '_self');
INSERT INTO sys_menu (menu_id, parent_id, name, url, perms, type, icon, order_num, open_mode) VALUES (123, 117, '对比-季度对比分析报表', 'rdppage/show/23a58db31668eef064370d9706a3896c', '', 1, 'fa fa-eye', 2, '_self');
INSERT INTO sys_menu (menu_id, parent_id, name, url, perms, type, icon, order_num, open_mode) VALUES (124, 117, '预警-客户风险预警报表', 'rdppage/show/8b9873d6fb7e14e93794ee7fc11cc3a0', '', 1, 'fa fa-eye', 2, '_self');
INSERT INTO sys_menu (menu_id, parent_id, name, url, perms, type, icon, order_num, open_mode) VALUES (125, 117, '一体化生成-查询列表', 'modules/rdp/demo/demo2.html', '', 1, 'fa fa-eye', 0, '_self');
INSERT INTO sys_menu (menu_id, parent_id, name, url, perms, type, icon, order_num, open_mode) VALUES (126, 117, '自定义查询条件-查询列表', 'modules/rdp/demo/demo1.html', '', 1, 'fa fa-eye', 0, '_self');
INSERT INTO sys_menu (menu_id, parent_id, name, url, perms, type, icon, order_num, open_mode) VALUES (129, 117, '动态列JAVABEAN', 'modules/rdp/javabean/c.html', '', 1, 'fa fa-eye', 2, '_self');
INSERT INTO sys_menu (menu_id, parent_id, name, url, perms, type, icon, order_num, open_mode) VALUES (131, 107, '盒模型切换', 'modules/bddp/swiper/swipercube.html', '', 1, 'fa fa-eye', 0, '_blank');
INSERT INTO sys_menu (menu_id, parent_id, name, url, perms, type, icon, order_num, open_mode) VALUES (132, 113, '大屏幕报表管理', 'modules/bddp/home.html', '', 1, '', 0, '_blank');
INSERT INTO sys_menu (menu_id, parent_id, name, url, perms, type, icon, order_num, open_mode) VALUES (133, 0, '填报报表DEMO', '', '', 0, 'fa fa-wpforms', 100, '_self');
INSERT INTO sys_menu (menu_id, parent_id, name, url, perms, type, icon, order_num, open_mode) VALUES (134, 133, '人员基本情况表', 'rdppage/main/e93657d429ea44bfe6f5b7872676b35f?deptId=${deptId}&amp;userId=${userId}', '', 1, 'fa fa-eye', 1, '_self');
INSERT INTO sys_menu (menu_id, parent_id, name, url, perms, type, icon, order_num, open_mode) VALUES (135, 133, '申报事项审批单', 'rdppage/main/6fbd2deabc701284edeb14003b26baea', '', 1, 'fa fa-eye', 2, '_self');
INSERT INTO sys_menu (menu_id, parent_id, name, url, perms, type, icon, order_num, open_mode) VALUES (136, 133, '申报事项审批单编辑', 'rdppage/main/5bd730f8f1b65b0908e383ad76d15642', '', 1, 'fa fa-eye', 3, '_self');
INSERT INTO sys_menu (menu_id, parent_id, name, url, perms, type, icon, order_num, open_mode) VALUES (137, 133, '填报添加验证DEMO', 'rdppage/main/b00eaf643bd2034ebc2a2e402a785667', '', 1, 'fa fa-eye', 4, '_self');
INSERT INTO sys_menu (menu_id, parent_id, name, url, perms, type, icon, order_num, open_mode) VALUES (138, 133, '填报详情明细', 'rdppage/main/3132737d6c808d35f2fbc60f6ec6e2a5?userId=${userId}', '', 1, 'fa fa-eye', 5, '_self');
INSERT INTO sys_menu (menu_id, parent_id, name, url, perms, type, icon, order_num, open_mode) VALUES (139, 117, '数据集过滤', 'rdppage/main/1cc54e0a1116c6c4dd06806b43621a25', '', 1, '', 0, '_self');

INSERT INTO sys_dept (dept_id, parent_id, name, order_num, del_flag) VALUES (1, 0, '总部', 0, 0);
INSERT INTO sys_dept (dept_id, parent_id, name, order_num, del_flag) VALUES (2, 1, '分公司', 1, 0);
INSERT INTO sys_dept (dept_id, parent_id, name, order_num, del_flag) VALUES (3, 1, '研发部', 2, 0);

INSERT INTO sys_user (user_id, username, password, salt, email, mobile, status, dept_id, create_time) VALUES ('1', 'admin', 'e1153123d7d180ceeb820d577ff119876678732a68eef4e6ffc0b1f06a01f91b', 'YzcmCZNvbXocrsz9dm8e', 'a@b.io', '', '1', '1', '2016-11-11 11:11:11');


INSERT INTO sys_dict(id, name, type, code, value, order_num, remark, del_flag) VALUES (1, '性别', 'sex', '0', '女', 0, NULL, 0);
INSERT INTO sys_dict(id, name, type, code, value, order_num, remark, del_flag) VALUES (2, '性别', 'sex', '1', '男', 1, NULL, 0);
INSERT INTO sys_dict(id, name, type, code, value, order_num, remark, del_flag) VALUES (3, '性别', 'sex', '2', '未知', 3, NULL, 0);

INSERT INTO sys_config (param_key, param_value, status, remark) VALUES ('CLOUD_STORAGE_CONFIG_KEY',  '{"aliyunAccessKeyId":"","aliyunAccessKeySecret":"","aliyunBucketName":"","aliyunDomain":"","aliyunEndPoint":"","aliyunPrefix":"","qcloudBucketName":"","qcloudDomain":"","qcloudPrefix":"","qcloudSecretId":"","qcloudSecretKey":"","qiniuAccessKey":"NrgMfABZxWLo5B-YYSjoE8-AZ1EISdi1Z3ubLOeZ","qiniuBucketName":"ios-app","qiniuDomain":"http://7xlij2.com1.z0.glb.clouddn.com","qiniuPrefix":"upload","qiniuSecretKey":"uIwJHevMRWU0VLxFvgy0tAcOdGqasdtVlJkdy6vV","type":1}', '0', '云存储配置信息');

INSERT INTO schedule_job (bean_name, method_name, params, cron_expression, status, remark, create_time) VALUES ('testTask', 'test', 'renren', '0 0/30 * * * ?', '0', '有参数测试', '2016-12-01 23:16:46');
INSERT INTO schedule_job (bean_name, method_name, params, cron_expression, status, remark, create_time) VALUES ('testTask', 'test2', NULL, '0 0/30 * * * ?', '1', '无参数测试', '2016-12-03 14:55:56');

INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('A', '农、林、牧、渔业', '1', '');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('A01', '农业', '2', 'A');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('A011', '谷物种植', '3', 'A01');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('A0111', '稻谷种植', '4', 'A011');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('A0112', '小麦种植', '4', 'A011');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('A0113', '玉米种植', '4', 'A011');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('A0119', '其他谷物种植', '4', 'A011');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('A012', '豆类、油料和薯类种植', '3', 'A01');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('A0121', '豆类种植', '4', 'A012');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('A0122', '油料种植', '4', 'A012');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('A0123', '薯类种植', '4', 'A012');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('A013', '棉、麻、糖、烟草种植', '3', 'A01');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('A0131', '棉花种植', '4', 'A013');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('A0132', '麻类种植', '4', 'A013');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('A0133', '糖料种植', '4', 'A013');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('A0134', '烟草种植', '4', 'A013');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('A014', '蔬菜、食用菌及园艺作物种植', '3', 'A01');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('A0141', '蔬菜种植', '4', 'A014');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('A0142', '食用菌种植', '4', 'A014');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('A0143', '花卉种植', '4', 'A014');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('A0149', '其他园艺作物种植', '4', 'A014');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('A015', '水果种植', '3', 'A01');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('A0151', '仁果类和核果类水果种植', '4', 'A015');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('A0152', '葡萄种植', '4', 'A015');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('A0153', '柑橘类种植', '4', 'A015');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('A0154', '香蕉等亚热带水果种植', '4', 'A015');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('A0159', '其他水果种植', '4', 'A015');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('A016', '坚果、含油果、香料和饮料作物种植', '3', 'A01');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('A0161', '坚果种植', '4', 'A016');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('A0162', '含油果种植', '4', 'A016');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('A0163', '香料作物种植', '4', 'A016');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('A0169', '茶及其他饮料作物种植', '4', 'A016');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('A017', '中药材种植', '3', 'A01');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('A0170', '中药材种植', '4', 'A017');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('A019', '其他农业', '3', 'A01');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('A0190', '其他农业', '4', 'A019');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('A02', '林业', '2', 'A');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('A021', '林木育种和育苗', '3', 'A02');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('A0211', '林木育种', '4', 'A021');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('A0212', '林木育苗', '4', 'A021');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('A022', '造林和更新', '3', 'A02');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('A0220', '造林和更新', '4', 'A022');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('A023', '森林经营和管护', '3', 'A02');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('A0230', '森林经营和管护', '4', 'A023');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('A024', '木材和竹材采运', '3', 'A02');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('A0241', '木材采运', '4', 'A024');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('A0242', '竹材采运', '4', 'A024');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('A025', '林产品采集', '3', 'A02');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('A0251', '木竹材林产品采集', '4', 'A025');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('A0252', '非木竹材林产品采集', '4', 'A025');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('A03', '畜牧业', '2', 'A');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('A031', '牲畜饲养', '3', 'A03');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('A0311', '牛的饲养', '4', 'A031');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('A0312', '马的饲养', '4', 'A031');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('A0313', '猪的饲养', '4', 'A031');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('A0314', '羊的饲养', '4', 'A031');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('A0315', '骆驼饲养', '4', 'A031');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('A0319', '其他牲畜饲养', '4', 'A031');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('A032', '家禽饲养', '3', 'A03');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('A0321', '鸡的饲养', '4', 'A032');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('A0322', '鸭的饲养', '4', 'A032');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('A0323', '鹅的饲养', '4', 'A032');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('A0329', '其他家禽饲养', '4', 'A032');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('A033', '狩猎和捕捉动物', '3', 'A03');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('A0330', '狩猎和捕捉动物', '4', 'A033');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('A039', '其他畜牧业', '3', 'A03');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('A0390', '其他畜牧业', '4', 'A039');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('A04', '渔业', '2', 'A');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('A041', '水产养殖', '3', 'A04');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('A0411', '海水养殖', '4', 'A041');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('A0412', '内陆养殖', '4', 'A041');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('A042', '水产捕捞', '3', 'A04');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('A0421', '海水捕捞', '4', 'A042');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('A0422', '内陆捕捞', '4', 'A042');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('A05', '农、林、牧、渔服务业', '2', 'A');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('A051', '农业服务业', '3', 'A05');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('A0511', '农业机械服务', '4', 'A051');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('A0512', '灌溉服务', '4', 'A051');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('A0513', '农产品初加工服务', '4', 'A051');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('A0519', '其他农业服务', '4', 'A051');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('A052', '林业服务业', '3', 'A05');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('A0521', '林业有害生物防治服务', '4', 'A052');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('A0522', '森林防火服务', '4', 'A052');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('A0523', '林产品初级加工服务', '4', 'A052');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('A0529', '其他林业服务', '4', 'A052');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('A053', '畜牧服务业', '3', 'A05');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('A0530', '畜牧服务业', '4', 'A053');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('A054', '渔业服务业', '3', 'A05');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('A0540', '渔业服务业', '4', 'A054');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('B', '采矿业', '1', '');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('B06', '煤炭开采和洗选业', '2', 'B');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('B061', '烟煤和无烟煤开采洗选', '3', 'B06');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('B0610', '烟煤和无烟煤开采洗选', '4', 'B061');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('B062', '褐煤开采洗选', '3', 'B06');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('B0620', '褐煤开采洗选', '4', 'B062');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('B069', '其他煤炭采选', '3', 'B06');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('B0690', '其他煤炭采选', '4', 'B069');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('B07', '石油和天然气开采业', '2', 'B');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('B071', '石油开采', '3', 'B07');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('B0710', '石油开采', '4', 'B071');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('B072', '天然气开采', '3', 'B07');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('B0720', '天然气开采', '4', 'B072');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('B08', '黑色金属矿采选业', '2', 'B');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('B081', '铁矿采选', '3', 'B08');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('B0810', '铁矿采选', '4', 'B081');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('B082', '锰矿、铬矿采选', '3', 'B08');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('B0820', '锰矿、铬矿采选', '4', 'B082');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('B089', '其他黑色金属矿采选', '3', 'B08');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('B0890', '其他黑色金属矿采选', '4', 'B089');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('B09', '有色金属矿采选业', '2', 'B');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('B091', '常用有色金属矿采选', '3', 'B09');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('B0911', '铜矿采选', '4', 'B091');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('B0912', '铅锌矿采选', '4', 'B091');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('B0913', '镍钴矿采选', '4', 'B091');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('B0914', '锡矿采选', '4', 'B091');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('B0915', '锑矿采选', '4', 'B091');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('B0916', '铝矿采选', '4', 'B091');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('B0917', '镁矿采选', '4', 'B091');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('B0919', '其他常用有色金属矿采选', '4', 'B091');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('B092', '贵金属矿采选', '3', 'B09');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('B0921', '金矿采选', '4', 'B092');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('B0922', '银矿采选', '4', 'B092');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('B0929', '其他贵金属矿采选', '4', 'B092');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('B093', '稀有稀土金属矿采选', '3', 'B09');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('B0931', '钨钼矿采选', '4', 'B093');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('B0932', '稀土金属矿采选', '4', 'B093');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('B0933', '放射性金属矿采选', '4', 'B093');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('B0939', '其他稀有金属矿采选', '4', 'B093');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('B10', '非金属矿采选业', '2', 'B');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('B101', '土砂石开采', '3', 'B10');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('B1011', '石灰石、石膏开采', '4', 'B101');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('B1012', '建筑装饰用石开采', '4', 'B101');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('B1013', '耐火土石开采', '4', 'B101');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('B1019', '粘土及其他土砂石开采', '4', 'B101');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('B102', '化学矿开采', '3', 'B10');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('B1020', '化学矿开采', '4', 'B102');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('B103', '采盐', '3', 'B10');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('B1030', '采盐', '4', 'B103');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('B109', '石棉及其他非金属矿采选', '3', 'B10');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('B1091', '石棉、云母矿采选', '4', 'B109');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('B1092', '石墨、滑石采选', '4', 'B109');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('B1093', '宝石、玉石采选', '4', 'B109');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('B1099', '其他未列明非金属矿采选', '4', 'B109');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('B11', '开采辅助活动', '2', 'B');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('B111', '煤炭开采和洗选辅助活动', '3', 'B11');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('B1110', '煤炭开采和洗选辅助活动', '4', 'B111');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('B112', '石油和天然气开采辅助活动', '3', 'B11');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('B1120', '石油和天然气开采辅助活动', '4', 'B112');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('B119', '其他开采辅助活动', '3', 'B11');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('B1190', '其他开采辅助活动', '4', 'B119');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('B12', '其他采矿业', '2', 'B');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('B120', '其他采矿业', '3', 'B12');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('B1200', '其他采矿业', '4', 'B120');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C', '制造业', '1', '');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C13', '农副食品加工业', '2', 'C');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C131', '谷物磨制', '3', 'C13');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C1310', '谷物磨制', '4', 'C131');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C132', '饲料加工', '3', 'C13');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C1320', '饲料加工', '4', 'C132');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C133', '植物油加工', '3', 'C13');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C1331', '食用植物油加工', '4', 'C133');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C1332', '非食用植物油加工', '4', 'C133');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C134', '制糖业', '3', 'C13');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C1340', '制糖业', '4', 'C134');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C135', '屠宰及肉类加工', '3', 'C13');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C1351', '牲畜屠宰', '4', 'C135');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C1352', '禽类屠宰', '4', 'C135');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C1353', '肉制品及副产品加工', '4', 'C135');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C136', '水产品加工', '3', 'C13');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C1361', '水产品冷冻加工', '4', 'C136');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C1362', '鱼糜制品及水产品干腌制加工', '4', 'C136');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C1363', '水产饲料制造', '4', 'C136');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C1364', '鱼油提取及制品制造', '4', 'C136');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C1369', '其他水产品加工', '4', 'C136');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C137', '蔬菜、水果和坚果加工', '3', 'C13');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C1371', '蔬菜加工', '4', 'C137');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C1372', '水果和坚果加工', '4', 'C137');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C139', '其他农副食品加工', '3', 'C13');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C1391', '淀粉及淀粉制品制造', '4', 'C139');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C1392', '豆制品制造', '4', 'C139');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C1393', '蛋品加工', '4', 'C139');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C1399', '其他未列明农副食品加工', '4', 'C139');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C14', '食品制造业', '2', 'C');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C141', '焙烤食品制造', '3', 'C14');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C1411', '糕点、面包制造', '4', 'C141');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C1419', '饼干及其他焙烤食品制造', '4', 'C141');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C142', '糖果、巧克力及蜜饯制造', '3', 'C14');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C1421', '糖果、巧克力制造', '4', 'C142');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C1422', '蜜饯制作', '4', 'C142');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C143', '方便食品制造', '3', 'C14');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C1431', '米、面制品制造', '4', 'C143');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C1432', '速冻食品制造', '4', 'C143');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C1439', '方便面及其他方便食品制造', '4', 'C143');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C144', '乳制品制造', '3', 'C14');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C1440', '乳制品制造', '4', 'C144');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C145', '罐头食品制造', '3', 'C14');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C1451', '肉、禽类罐头制造', '4', 'C145');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C1452', '水产品罐头制造', '4', 'C145');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C1453', '蔬菜、水果罐头制造', '4', 'C145');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C1459', '其他罐头食品制造', '4', 'C145');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C146', '调味品、发酵制品制造', '3', 'C14');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C1461', '味精制造', '4', 'C146');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C1462', '酱油、食醋及类似制品制造', '4', 'C146');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C1469', '其他调味品、发酵制品制造', '4', 'C146');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C149', '其他食品制造', '3', 'C14');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C1491', '营养食品制造', '4', 'C149');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C1492', '保健食品制造', '4', 'C149');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C1493', '冷冻饮品及食用冰制造', '4', 'C149');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C1494', '盐加工', '4', 'C149');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C1495', '食品及饲料添加剂制造', '4', 'C149');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C1499', '其他未列明食品制造', '4', 'C149');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C15', '酒、饮料和精制茶制造业', '2', 'C');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C151', '酒的制造', '3', 'C15');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C1511', '酒精制造', '4', 'C151');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C1512', '白酒制造', '4', 'C151');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C1513', '啤酒制造', '4', 'C151');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C1514', '黄酒制造', '4', 'C151');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C1515', '葡萄酒制造', '4', 'C151');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C1519', '其他酒制造', '4', 'C151');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C152', '饮料制造', '3', 'C15');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C1521', '碳酸饮料制造', '4', 'C152');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C1522', '瓶（罐）装饮用水制造', '4', 'C152');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C1523', '果菜汁及果菜汁饮料制造', '4', 'C152');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C1524', '含乳饮料和植物蛋白饮料制造', '4', 'C152');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C1525', '固体饮料制造', '4', 'C152');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C1529', '茶饮料及其他饮料制造', '4', 'C152');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C153', '精制茶加工', '3', 'C15');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C1530', '精制茶加工', '4', 'C153');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C16', '烟草制品业', '2', 'C');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C161', '烟叶复烤', '3', 'C16');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C1610', '烟叶复烤', '4', 'C161');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C162', '卷烟制造', '3', 'C16');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C1620', '卷烟制造', '4', 'C162');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C169', '其他烟草制品制造', '3', 'C16');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C1690', '其他烟草制品制造', '4', 'C169');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C17', '纺织业', '2', 'C');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C171', '棉纺织及印染精加工', '3', 'C17');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C1711', '棉纺纱加工', '4', 'C171');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C1712', '棉织造加工', '4', 'C171');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C1713', '棉印染精加工', '4', 'C171');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C172', '毛纺织及染整精加工', '3', 'C17');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C1721', '毛条和毛纱线加工', '4', 'C172');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C1722', '毛织造加工', '4', 'C172');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C1723', '毛染整精加工', '4', 'C172');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C173', '麻纺织及染整精加工', '3', 'C17');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C1731', '麻纤维纺前加工和纺纱', '4', 'C173');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C1732', '麻织造加工', '4', 'C173');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C1733', '麻染整精加工', '4', 'C173');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C174', '丝绢纺织及印染精加工', '3', 'C17');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C1741', '缫丝加工', '4', 'C174');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C1742', '绢纺和丝织加工', '4', 'C174');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C1743', '丝印染精加工', '4', 'C174');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C175', '化纤织造及印染精加工', '3', 'C17');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C1751', '化纤织造加工', '4', 'C175');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C1752', '化纤织物染整精加工', '4', 'C175');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C176', '针织或钩针编织物及其制品制造', '3', 'C17');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C1761', '针织或钩针编织物织造', '4', 'C176');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C1762', '针织或钩针编织物印染精加工', '4', 'C176');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C1763', '针织或钩针编织品制造', '4', 'C176');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C177', '家用纺织制成品制造', '3', 'C17');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C1771', '床上用品制造', '4', 'C177');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C1772', '毛巾类制品制造', '4', 'C177');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C1773', '窗帘、布艺类产品制造', '4', 'C177');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C1779', '其他家用纺织制成品制造', '4', 'C177');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C178', '非家用纺织制成品制造', '3', 'C17');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C1781', '非织造布制造', '4', 'C178');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C1782', '绳、索、缆制造', '4', 'C178');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C1783', '纺织带和帘子布制造', '4', 'C178');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C1784', '篷、帆布制造', '4', 'C178');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C1789', '其他非家用纺织制成品制造', '4', 'C178');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C18', '纺织服装、服饰业', '2', 'C');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C181', '机织服装制造', '3', 'C18');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C1810', '机织服装制造', '4', 'C181');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C182', '针织或钩针编织服装制造', '3', 'C18');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C1820', '针织或钩针编织服装制造', '4', 'C182');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C183', '服饰制造', '3', 'C18');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C1830', '服饰制造', '4', 'C183');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C19', '皮革、毛皮、羽毛及其制品和制鞋业', '2', 'C');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C191', '皮革鞣制加工', '3', 'C19');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C1910', '皮革鞣制加工', '4', 'C191');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C192', '皮革制品制造', '3', 'C19');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C1921', '皮革服装制造', '4', 'C192');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C1922', '皮箱、包（袋）制造', '4', 'C192');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C1923', '皮手套及皮装饰制品制造', '4', 'C192');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C1929', '其他皮革制品制造', '4', 'C192');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C193', '毛皮鞣制及制品加工', '3', 'C19');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C1931', '毛皮鞣制加工', '4', 'C193');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C1932', '毛皮服装加工', '4', 'C193');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C1939', '其他毛皮制品加工', '4', 'C193');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C194', '羽毛(绒)加工及制品制造', '3', 'C19');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C1941', '羽毛（绒）加工', '4', 'C194');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C1942', '羽毛（绒）制品加工', '4', 'C194');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C195', '制鞋业', '3', 'C19');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C1951', '纺织面料鞋制造', '4', 'C195');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C1952', '皮鞋制造', '4', 'C195');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C1953', '塑料鞋制造', '4', 'C195');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C1954', '橡胶鞋制造', '4', 'C195');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C1959', '其他制鞋业', '4', 'C195');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C20', '木材加工和木、竹、藤、棕、草制品业', '2', 'C');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C201', '木材加工', '3', 'C20');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C2011', '锯材加工', '4', 'C201');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C2012', '木片加工', '4', 'C201');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C2013', '单板加工', '4', 'C201');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C2019', '其他木材加工', '4', 'C201');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C202', '人造板制造', '3', 'C20');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C2021', '胶合板制造', '4', 'C202');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C2022', '纤维板制造', '4', 'C202');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C2023', '刨花板制造', '4', 'C202');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C2029', '其他人造板制造', '4', 'C202');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C203', '木制品制造', '3', 'C20');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C2031', '建筑用木料及木材组件加工', '4', 'C203');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C2032', '木门窗、楼梯制造', '4', 'C203');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C2033', '地板制造', '4', 'C203');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C2034', '木制容器制造', '4', 'C203');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C2039', '软木制品及其他木制品制造', '4', 'C203');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C204', '竹、藤、棕、草等制品制造', '3', 'C20');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C2041', '竹制品制造', '4', 'C204');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C2042', '藤制品制造', '4', 'C204');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C2043', '棕制品制造', '4', 'C204');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C2049', '草及其他制品制造', '4', 'C204');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C21', '家具制造业', '2', 'C');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C211', '木质家具制造', '3', 'C21');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C2110', '木质家具制造', '4', 'C211');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C212', '竹、藤家具制造', '3', 'C21');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C2120', '竹、藤家具制造', '4', 'C212');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C213', '金属家具制造', '3', 'C21');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C2130', '金属家具制造', '4', 'C213');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C214', '塑料家具制造', '3', 'C21');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C2140', '塑料家具制造', '4', 'C214');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C219', '其他家具制造', '3', 'C21');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C2190', '其他家具制造', '4', 'C219');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C22', '造纸和纸制品业', '2', 'C');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C221', '纸浆制造', '3', 'C22');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C2211', '木竹浆制造', '4', 'C221');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C2212', '非木竹浆制造', '4', 'C221');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C222', '造纸', '3', 'C22');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C2221', '机制纸及纸板制造', '4', 'C222');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C2222', '手工纸制造', '4', 'C222');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C2223', '加工纸制造', '4', 'C222');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C223', '纸制品制造', '3', 'C22');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C2231', '纸和纸板容器制造', '4', 'C223');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C2239', '其他纸制品制造', '4', 'C223');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C23', '印刷和记录媒介复制业', '2', 'C');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C231', '印刷', '3', 'C23');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C2311', '书、报刊印刷', '4', 'C231');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C2312', '本册印制', '4', 'C231');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C2319', '包装装潢及其他印刷', '4', 'C231');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C232', '装订及印刷相关服务', '3', 'C23');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C2320', '装订及印刷相关服务', '4', 'C232');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C233', '记录媒介复制', '3', 'C23');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C2330', '记录媒介复制', '4', 'C233');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C24', '文教、工美、体育和娱乐用品制造业', '2', 'C');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C241', '文教办公用品制造', '3', 'C24');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C2411', '文具制造', '4', 'C241');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C2412', '笔的制造', '4', 'C241');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C2413', '教学用模型及教具制造', '4', 'C241');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C2414', '墨水、墨汁制造', '4', 'C241');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C2419', '其他文教办公用品制造', '4', 'C241');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C242', '乐器制造', '3', 'C24');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C2421', '中乐器制造', '4', 'C242');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C2422', '西乐器制造', '4', 'C242');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C2423', '电子乐器制造', '4', 'C242');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C2429', '其他乐器及零件制造', '4', 'C242');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C243', '工艺美术品制造', '3', 'C24');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C2431', '雕塑工艺品制造', '4', 'C243');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C2432', '金属工艺品制造', '4', 'C243');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C2433', '漆器工艺品制造', '4', 'C243');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C2434', '花画工艺品制造', '4', 'C243');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C2435', '天然植物纤维编织工艺品制造', '4', 'C243');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C2436', '抽纱刺绣工艺品制造', '4', 'C243');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C2437', '地毯、挂毯制造', '4', 'C243');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C2438', '珠宝首饰及有关物品制造', '4', 'C243');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C2439', '其他工艺美术品制造', '4', 'C243');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C244', '体育用品制造', '3', 'C24');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C2441', '球类制造', '4', 'C244');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C2442', '体育器材及配件制造', '4', 'C244');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C2443', '训练健身器材制造', '4', 'C244');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C2444', '运动防护用具制造', '4', 'C244');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C2449', '其他体育用品制造', '4', 'C244');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C245', '玩具制造', '3', 'C24');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C2450', '玩具制造', '4', 'C245');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C246', '游艺器材及娱乐用品制造', '3', 'C24');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C2461', '露天游乐场所游乐设备制造', '4', 'C246');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C2462', '游艺用品及室内游艺器材制造', '4', 'C246');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C2469', '其他娱乐用品制造', '4', 'C246');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C25', '石油加工、炼焦和核燃料加工业', '2', 'C');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C251', '精炼石油产品制造', '3', 'C25');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C2511', '原油加工及石油制品制造', '4', 'C251');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C2512', '人造原油制造', '4', 'C251');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C252', '炼焦', '3', 'C25');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C2520', '炼焦', '4', 'C252');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C253', '核燃料加工', '3', 'C25');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C2530', '核燃料加工', '4', 'C253');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C26', '化学原料和化学制品制造业', '2', 'C');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C261', '基础化学原料制造', '3', 'C26');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C2611', '无机酸制造', '4', 'C261');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C2612', '无机碱制造', '4', 'C261');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C2613', '无机盐制造', '4', 'C261');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C2614', '有机化学原料制造', '4', 'C261');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C2619', '其他基础化学原料制造', '4', 'C261');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C262', '肥料制造', '3', 'C26');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C2621', '氮肥制造', '4', 'C262');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C2622', '磷肥制造', '4', 'C262');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C2623', '钾肥制造', '4', 'C262');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C2624', '复混肥料制造', '4', 'C262');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C2625', '有机肥料及微生物肥料制造', '4', 'C262');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C2629', '其他肥料制造', '4', 'C262');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C263', '农药制造', '3', 'C26');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C2631', '化学农药制造', '4', 'C263');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C2632', '生物化学农药及微生物农药制造', '4', 'C263');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C264', '涂料、油墨、颜料及类似产品制造', '3', 'C26');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C2641', '涂料制造', '4', 'C264');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C2642', '油墨及类似产品制造', '4', 'C264');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C2643', '颜料制造', '4', 'C264');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C2644', '染料制造', '4', 'C264');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C2645', '密封用填料及类似品制造', '4', 'C264');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C265', '合成材料制造', '3', 'C26');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C2651', '初级形态塑料及合成树脂制造', '4', 'C265');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C2652', '合成橡胶制造', '4', 'C265');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C2653', '合成纤维单（聚合）体制造', '4', 'C265');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C2659', '其他合成材料制造', '4', 'C265');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C266', '专用化学产品制造', '3', 'C26');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C2661', '化学试剂和助剂制造', '4', 'C266');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C2662', '专项化学用品制造', '4', 'C266');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C2663', '林产化学产品制造', '4', 'C266');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C2664', '信息化学品制造', '4', 'C266');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C2665', '环境污染处理专用药剂材料制造', '4', 'C266');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C2666', '动物胶制造', '4', 'C266');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C2669', '其他专用化学产品制造', '4', 'C266');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C267', '炸药、火工及焰火产品制造', '3', 'C26');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C2671', '炸药及火工产品制造', '4', 'C267');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C2672', '焰火、鞭炮产品制造', '4', 'C267');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C268', '日用化学产品制造', '3', 'C26');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C2681', '肥皂及合成洗涤剂制造', '4', 'C268');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C2682', '化妆品制造', '4', 'C268');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C2683', '口腔清洁用品制造', '4', 'C268');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C2684', '香料、香精制造', '4', 'C268');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C2689', '其他日用化学产品制造', '4', 'C268');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C27', '医药制造业', '2', 'C');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C271', '化学药品原料药制造', '3', 'C27');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C2710', '化学药品原料药制造', '4', 'C271');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C272', '化学药品制剂制造', '3', 'C27');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C2720', '化学药品制剂制造', '4', 'C272');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C273', '中药饮片加工', '3', 'C27');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C2730', '中药饮片加工', '4', 'C273');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C274', '中成药生产', '3', 'C27');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C2740', '中成药生产', '4', 'C274');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C275', '兽用药品制造', '3', 'C27');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C2750', '兽用药品制造', '4', 'C275');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C276', '生物药品制造', '3', 'C27');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C2760', '生物药品制造', '4', 'C276');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C277', '卫生材料及医药用品制造', '3', 'C27');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C2770', '卫生材料及医药用品制造', '4', 'C277');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C28', '化学纤维制造业', '2', 'C');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C281', '纤维素纤维原料及纤维制造', '3', 'C28');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C2811', '化纤浆粕制造', '4', 'C281');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C2812', '人造纤维（纤维素纤维）制造', '4', 'C281');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C282', '合成纤维制造', '3', 'C28');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C2821', '锦纶纤维制造', '4', 'C282');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C2822', '涤纶纤维制造', '4', 'C282');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C2823', '腈纶纤维制造', '4', 'C282');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C2824', '维纶纤维制造', '4', 'C282');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C2825', '丙纶纤维制造', '4', 'C282');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C2826', '氨纶纤维制造', '4', 'C282');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C2829', '其他合成纤维制造', '4', 'C282');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C29', '橡胶和塑料制品业', '2', 'C');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C291', '橡胶制品业', '3', 'C29');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C2911', '轮胎制造', '4', 'C291');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C2912', '橡胶板、管、带制造', '4', 'C291');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C2913', '橡胶零件制造', '4', 'C291');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C2914', '再生橡胶制造', '4', 'C291');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C2915', '日用及医用橡胶制品制造', '4', 'C291');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C2919', '其他橡胶制品制造', '4', 'C291');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C292', '塑料制品业', '3', 'C29');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C2921', '塑料薄膜制造', '4', 'C292');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C2922', '塑料板、管、型材制造', '4', 'C292');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C2923', '塑料丝、绳及编织品制造', '4', 'C292');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C2924', '泡沫塑料制造', '4', 'C292');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C2925', '塑料人造革、合成革制造', '4', 'C292');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C2926', '塑料包装箱及容器制造', '4', 'C292');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C2927', '日用塑料制品制造', '4', 'C292');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C2928', '塑料零件制造', '4', 'C292');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C2929', '其他塑料制品制造', '4', 'C292');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C30', '非金属矿物制品业', '2', 'C');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C301', '水泥、石灰和石膏制造', '3', 'C30');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C3011', '水泥制造', '4', 'C301');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C3012', '石灰和石膏制造', '4', 'C301');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C302', '石膏、水泥制品及类似制品制造', '3', 'C30');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C3021', '水泥制品制造', '4', 'C302');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C3022', '砼结构构件制造', '4', 'C302');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C3023', '石棉水泥制品制造', '4', 'C302');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C3024', '轻质建筑材料制造', '4', 'C302');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C3029', '其他水泥类似制品制造', '4', 'C302');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C303', '砖瓦、石材等建筑材料制造', '3', 'C30');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C3031', '粘土砖瓦及建筑砌块制造', '4', 'C303');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C3032', '建筑陶瓷制品制造', '4', 'C303');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C3033', '建筑用石加工', '4', 'C303');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C3034', '防水建筑材料制造', '4', 'C303');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C3035', '隔热和隔音材料制造', '4', 'C303');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C3039', '其他建筑材料制造', '4', 'C303');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C304', '玻璃制造', '3', 'C30');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C3041', '平板玻璃制造', '4', 'C304');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C3049', '其他玻璃制造', '4', 'C304');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C305', '玻璃制品制造', '3', 'C30');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C3051', '技术玻璃制品制造', '4', 'C305');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C3052', '光学玻璃制造', '4', 'C305');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C3053', '玻璃仪器制造', '4', 'C305');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C3054', '日用玻璃制品制造', '4', 'C305');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C3055', '玻璃包装容器制造', '4', 'C305');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C3056', '玻璃保温容器制造', '4', 'C305');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C3057', '制镜及类似品加工', '4', 'C305');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C3059', '其他玻璃制品制造', '4', 'C305');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C306', '玻璃纤维和玻璃纤维增强塑料制品制造', '3', 'C30');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C3061', '玻璃纤维及制品制造', '4', 'C306');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C3062', '玻璃纤维增强塑料制品制造', '4', 'C306');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C307', '陶瓷制品制造', '3', 'C30');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C3071', '卫生陶瓷制品制造', '4', 'C307');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C3072', '特种陶瓷制品制造', '4', 'C307');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C3073', '日用陶瓷制品制造', '4', 'C307');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C3079', '园林、陈设艺术及其他陶瓷制品制造', '4', 'C307');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C308', '耐火材料制品制造', '3', 'C30');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C3081', '石棉制品制造', '4', 'C308');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C3082', '云母制品制造', '4', 'C308');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C3089', '耐火陶瓷制品及其他耐火材料制造', '4', 'C308');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C309', '石墨及其他非金属矿物制品制造', '3', 'C30');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C3091', '石墨及碳素制品制造', '4', 'C309');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C3099', '其他非金属矿物制品制造', '4', 'C309');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C31', '黑色金属冶炼和压延加工业', '2', 'C');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C311', '炼铁', '3', 'C31');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C3110', '炼铁', '4', 'C311');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C312', '炼钢', '3', 'C31');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C3120', '炼钢', '4', 'C312');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C313', '黑色金属铸造', '3', 'C31');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C3130', '黑色金属铸造', '4', 'C313');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C314', '钢压延加工', '3', 'C31');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C3140', '钢压延加工', '4', 'C314');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C315', '铁合金冶炼', '3', 'C31');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C3150', '铁合金冶炼', '4', 'C315');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C32', '有色金属冶炼和压延加工业', '2', 'C');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C321', '常用有色金属冶炼', '3', 'C32');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C3211', '铜冶炼', '4', 'C321');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C3212', '铅锌冶炼', '4', 'C321');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C3213', '镍钴冶炼', '4', 'C321');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C3214', '锡冶炼', '4', 'C321');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C3215', '锑冶炼', '4', 'C321');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C3216', '铝冶炼', '4', 'C321');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C3217', '镁冶炼', '4', 'C321');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C3219', '其他常用有色金属冶炼', '4', 'C321');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C322', '贵金属冶炼', '3', 'C32');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C3221', '金冶炼', '4', 'C322');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C3222', '银冶炼', '4', 'C322');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C3229', '其他贵金属冶炼', '4', 'C322');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C323', '稀有稀土金属冶炼', '3', 'C32');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C3231', '钨钼冶炼', '4', 'C323');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C3232', '稀土金属冶炼', '4', 'C323');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C3239', '其他稀有金属冶炼', '4', 'C323');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C324', '有色金属合金制造', '3', 'C32');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C3240', '有色金属合金制造', '4', 'C324');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C325', '有色金属铸造', '3', 'C32');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C3250', '有色金属铸造', '4', 'C325');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C326', '有色金属压延加工', '3', 'C32');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C3261', '铜压延加工', '4', 'C326');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C3262', '铝压延加工', '4', 'C326');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C3263', '贵金属压延加工', '4', 'C326');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C3264', '稀有稀土金属压延加工', '4', 'C326');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C3269', '其他有色金属压延加工', '4', 'C326');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C33', '金属制品业', '2', 'C');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C331', '结构性金属制品制造', '3', 'C33');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C3311', '金属结构制造', '4', 'C331');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C3312', '金属门窗制造', '4', 'C331');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C332', '金属工具制造', '3', 'C33');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C3321', '切削工具制造', '4', 'C332');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C3322', '手工具制造', '4', 'C332');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C3323', '农用及园林用金属工具制造', '4', 'C332');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C3324', '刀剪及类似日用金属工具制造', '4', 'C332');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C3329', '其他金属工具制造', '4', 'C332');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C333', '集装箱及金属包装容器制造', '3', 'C33');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C3331', '集装箱制造', '4', 'C333');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C3332', '金属压力容器制造', '4', 'C333');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C3333', '金属包装容器制造', '4', 'C333');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C334', '金属丝绳及其制品制造', '3', 'C33');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C3340', '金属丝绳及其制品制造', '4', 'C334');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C335', '建筑、安全用金属制品制造', '3', 'C33');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C3351', '建筑、家具用金属配件制造', '4', 'C335');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C3352', '建筑装饰及水暖管道零件制造', '4', 'C335');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C3353', '安全、消防用金属制品制造', '4', 'C335');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C3359', '其他建筑、安全用金属制品制造', '4', 'C335');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C336', '金属表面处理及热处理加工', '3', 'C33');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C3360', '金属表面处理及热处理加工', '4', 'C336');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C337', '搪瓷制品制造', '3', 'C33');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C3371', '生产专用搪瓷制品制造', '4', 'C337');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C3372', '建筑装饰搪瓷制品制造', '4', 'C337');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C3373', '搪瓷卫生洁具制造', '4', 'C337');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C3379', '搪瓷日用品及其他搪瓷制品制造', '4', 'C337');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C338', '金属制日用品制造', '3', 'C33');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C3381', '金属制厨房用器具制造', '4', 'C338');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C3382', '金属制餐具和器皿制造', '4', 'C338');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C3383', '金属制卫生器具制造', '4', 'C338');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C3389', '其他金属制日用品制造', '4', 'C338');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C339', '其他金属制品制造', '3', 'C33');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C3391', '锻件及粉末冶金制品制造', '4', 'C339');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C3392', '交通及公共管理用金属标牌制造', '4', 'C339');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C3399', '其他未列明金属制品制造', '4', 'C339');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C34', '通用设备制造业', '2', 'C');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C341', '锅炉及原动设备制造', '3', 'C34');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C3411', '锅炉及辅助设备制造', '4', 'C341');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C3412', '内燃机及配件制造', '4', 'C341');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C3413', '汽轮机及辅机制造', '4', 'C341');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C3414', '水轮机及辅机制造', '4', 'C341');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C3415', '风能原动设备制造', '4', 'C341');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C3419', '其他原动设备制造', '4', 'C341');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C342', '金属加工机械制造', '3', 'C34');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C3421', '金属切削机床制造', '4', 'C342');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C3422', '金属成形机床制造', '4', 'C342');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C3423', '铸造机械制造', '4', 'C342');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C3424', '金属切割及焊接设备制造', '4', 'C342');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C3425', '机床附件制造', '4', 'C342');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C3429', '其他金属加工机械制造', '4', 'C342');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C343', '物料搬运设备制造', '3', 'C34');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C3431', '轻小型起重设备制造', '4', 'C343');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C3432', '起重机制造', '4', 'C343');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C3433', '生产专用车辆制造', '4', 'C343');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C3434', '连续搬运设备制造', '4', 'C343');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C3435', '电梯、自动扶梯及升降机制造', '4', 'C343');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C3439', '其他物料搬运设备制造', '4', 'C343');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C344', '泵、阀门、压缩机及类似机械制造', '3', 'C34');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C3441', '泵及真空设备制造', '4', 'C344');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C3442', '气体压缩机械制造', '4', 'C344');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C3443', '阀门和旋塞制造', '4', 'C344');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C3444', '液压和气压动力机械及元件制造', '4', 'C344');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C345', '轴承、齿轮和传动部件制造', '3', 'C34');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C3451', '轴承制造', '4', 'C345');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C3452', '齿轮及齿轮减、变速箱制造', '4', 'C345');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C3459', '其他传动部件制造', '4', 'C345');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C346', '烘炉、风机、衡器、包装等设备制造', '3', 'C34');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C3461', '烘炉、熔炉及电炉制造', '4', 'C346');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C3462', '风机、风扇制造', '4', 'C346');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C3463', '气体、液体分离及纯净设备制造', '4', 'C346');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C3464', '制冷、空调设备制造', '4', 'C346');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C3465', '风动和电动工具制造', '4', 'C346');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C3466', '喷枪及类似器具制造', '4', 'C346');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C3467', '衡器制造', '4', 'C346');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C3468', '包装专用设备制造', '4', 'C346');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C347', '文化、办公用机械制造', '3', 'C34');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C3471', '电影机械制造', '4', 'C347');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C3472', '幻灯及投影设备制造', '4', 'C347');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C3473', '照相机及器材制造', '4', 'C347');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C3474', '复印和胶印设备制造', '4', 'C347');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C3475', '计算器及货币专用设备制造', '4', 'C347');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C3479', '其他文化、办公用机械制造', '4', 'C347');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C348', '通用零部件制造', '3', 'C34');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C3481', '金属密封件制造', '4', 'C348');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C3482', '紧固件制造', '4', 'C348');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C3483', '弹簧制造', '4', 'C348');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C3484', '机械零部件加工', '4', 'C348');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C3489', '其他通用零部件制造', '4', 'C348');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C349', '其他通用设备制造业', '3', 'C34');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C3490', '其他通用设备制造业', '4', 'C349');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C35', '专用设备制造业', '2', 'C');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C351', '采矿、冶金、建筑专用设备制造', '3', 'C35');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C3511', '矿山机械制造', '4', 'C351');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C3512', '石油钻采专用设备制造', '4', 'C351');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C3513', '建筑工程用机械制造', '4', 'C351');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C3514', '海洋工程专用设备制造', '4', 'C351');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C3515', '建筑材料生产专用机械制造', '4', 'C351');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C3516', '冶金专用设备制造', '4', 'C351');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C352', '化工、木材、非金属加工专用设备制造', '3', 'C35');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C3521', '炼油、化工生产专用设备制造', '4', 'C352');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C3522', '橡胶加工专用设备制造', '4', 'C352');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C3523', '塑料加工专用设备制造', '4', 'C352');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C3524', '木材加工机械制造', '4', 'C352');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C3525', '模具制造', '4', 'C352');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C3529', '其他非金属加工专用设备制造', '4', 'C352');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C353', '食品、饮料、烟草及饲料生产专用设备制造　　　', '3', 'C35');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C3531', '食品、酒、饮料及茶生产专用设备制造', '4', 'C353');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C3532', '农副食品加工专用设备制造', '4', 'C353');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C3533', '烟草生产专用设备制造', '4', 'C353');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C3534', '饲料生产专用设备制造', '4', 'C353');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C354', '印刷、制药、日化及日用品生产专用设备制造', '3', 'C35');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C3541', '制浆和造纸专用设备制造', '4', 'C354');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C3542', '印刷专用设备制造', '4', 'C354');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C3543', '日用化工专用设备制造', '4', 'C354');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C3544', '制药专用设备制造', '4', 'C354');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C3545', '照明器具生产专用设备制造', '4', 'C354');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C3546', '玻璃、陶瓷和搪瓷制品生产专用设备制造', '4', 'C354');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C3549', '其他日用品生产专用设备制造', '4', 'C354');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C355', '纺织、服装和皮革加工专用设备制造', '3', 'C35');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C3551', '纺织专用设备制造', '4', 'C355');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C3552', '皮革、毛皮及其制品加工专用设备制造', '4', 'C355');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C3553', '缝制机械制造', '4', 'C355');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C3554', '洗涤机械制造', '4', 'C355');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C356', '电子和电工机械专用设备制造', '3', 'C35');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C3561', '电工机械专用设备制造', '4', 'C356');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C3562', '电子工业专用设备制造', '4', 'C356');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C357', '农、林、牧、渔专用机械制造', '3', 'C35');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C3571', '拖拉机制造', '4', 'C357');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C3572', '机械化农业及园艺机具制造', '4', 'C357');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C3573', '营林及木竹采伐机械制造', '4', 'C357');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C3574', '畜牧机械制造', '4', 'C357');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C3575', '渔业机械制造', '4', 'C357');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C3576', '农林牧渔机械配件制造', '4', 'C357');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C3577', '棉花加工机械制造', '4', 'C357');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C3579', '其他农、林、牧、渔业机械制造', '4', 'C357');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C358', '医疗仪器设备及器械制造', '3', 'C35');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C3581', '医疗诊断、监护及治疗设备制造', '4', 'C358');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C3582', '口腔科用设备及器具制造', '4', 'C358');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C3583', '医疗实验室及医用消毒设备和器具制造', '4', 'C358');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C3584', '医疗、外科及兽医用器械制造', '4', 'C358');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C3585', '机械治疗及病房护理设备制造', '4', 'C358');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C3586', '假肢、人工器官及植（介）入器械制造', '4', 'C358');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C3589', '其他医疗设备及器械制造', '4', 'C358');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C359', '环保、社会公共服务及其他专用设备制造', '3', 'C35');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C3591', '环境保护专用设备制造', '4', 'C359');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C3592', '地质勘查专用设备制造', '4', 'C359');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C3593', '邮政专用机械及器材制造', '4', 'C359');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C3594', '商业、饮食、服务专用设备制造', '4', 'C359');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C3595', '社会公共安全设备及器材制造', '4', 'C359');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C3596', '交通安全、管制及类似专用设备制造', '4', 'C359');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C3597', '水资源专用机械制造', '4', 'C359');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C3599', '其他专用设备制造', '4', 'C359');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C36', '汽车制造业', '2', 'C');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C361', '汽车整车制造', '3', 'C36');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C3610', '汽车整车制造', '4', 'C361');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C362', '改装汽车制造', '3', 'C36');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C3620', '改装汽车制造', '4', 'C362');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C363', '低速载货汽车制造', '3', 'C36');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C3630', '低速载货汽车制造', '4', 'C363');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C364', '电车制造', '3', 'C36');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C3640', '电车制造', '4', 'C364');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C365', '汽车车身、挂车制造', '3', 'C36');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C3650', '汽车车身、挂车制造', '4', 'C365');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C366', '汽车零部件及配件制造', '3', 'C36');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C3660', '汽车零部件及配件制造', '4', 'C366');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C37', '铁路、船舶、航空航天和其他运输设备制造业', '2', 'C');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C371', '铁路运输设备制造', '3', 'C37');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C3711', '铁路机车车辆及动车组制造', '4', 'C371');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C3712', '窄轨机车车辆制造', '4', 'C371');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C3713', '铁路机车车辆配件制造', '4', 'C371');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C3714', '铁路专用设备及器材、配件制造', '4', 'C371');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C3719', '其他铁路运输设备制造', '4', 'C371');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C372', '城市轨道交通设备制造', '3', 'C37');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C3720', '城市轨道交通设备制造', '4', 'C372');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C373', '船舶及相关装置制造', '3', 'C37');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C3731', '金属船舶制造', '4', 'C373');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C3732', '非金属船舶制造', '4', 'C373');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C3733', '娱乐船和运动船制造', '4', 'C373');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C3734', '船用配套设备制造', '4', 'C373');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C3735', '船舶改装与拆除', '4', 'C373');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C3739', '航标器材及其他相关装置制造', '4', 'C373');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C374', '航空、航天器及设备制造', '3', 'C37');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C3741', '飞机制造', '4', 'C374');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C3742', '航天器制造', '4', 'C374');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C3743', '航空、航天相关设备制造', '4', 'C374');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C3749', '其他航空航天器制造', '4', 'C374');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C375', '摩托车制造', '3', 'C37');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C3751', '摩托车整车制造', '4', 'C375');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C3752', '摩托车零部件及配件制造', '4', 'C375');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C376', '自行车制造', '3', 'C37');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C3761', '脚踏自行车及残疾人座车制造', '4', 'C376');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C3762', '助动自行车制造', '4', 'C376');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C377', '非公路休闲车及零配件制造', '3', 'C37');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C3770', '非公路休闲车及零配件制造', '4', 'C377');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C379', '潜水救捞及其他未列明运输设备制造', '3', 'C37');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C3791', '潜水及水下救捞装备制造', '4', 'C379');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C3799', '其他未列明运输设备制造', '4', 'C379');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C38', '电气机械和器材制造业', '2', 'C');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C381', '电机制造', '3', 'C38');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C3811', '发电机及发电机组制造', '4', 'C381');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C3812', '电动机制造', '4', 'C381');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C3819', '微电机及其他电机制造', '4', 'C381');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C382', '输配电及控制设备制造', '3', 'C38');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C3821', '变压器、整流器和电感器制造', '4', 'C382');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C3822', '电容器及其配套设备制造', '4', 'C382');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C3823', '配电开关控制设备制造', '4', 'C382');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C3824', '电力电子元器件制造', '4', 'C382');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C3825', '光伏设备及元器件制造', '4', 'C382');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C3829', '其他输配电及控制设备制造', '4', 'C382');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C383', '电线、电缆、光缆及电工器材制造', '3', 'C38');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C3831', '电线、电缆制造', '4', 'C383');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C3832', '光纤、光缆制造', '4', 'C383');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C3833', '绝缘制品制造', '4', 'C383');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C3839', '其他电工器材制造', '4', 'C383');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C384', '电池制造', '3', 'C38');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C3841', '锂离子电池制造', '4', 'C384');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C3842', '镍氢电池制造', '4', 'C384');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C3849', '其他电池制造', '4', 'C384');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C385', '家用电力器具制造', '3', 'C38');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C3851', '家用制冷电器具制造', '4', 'C385');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C3852', '家用空气调节器制造', '4', 'C385');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C3853', '家用通风电器具制造', '4', 'C385');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C3854', '家用厨房电器具制造', '4', 'C385');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C3855', '家用清洁卫生电器具制造', '4', 'C385');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C3856', '家用美容、保健电器具制造', '4', 'C385');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C3857', '家用电力器具专用配件制造', '4', 'C385');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C3859', '其他家用电力器具制造', '4', 'C385');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C386', '非电力家用器具制造', '3', 'C38');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C3861', '燃气、太阳能及类似能源家用器具制造', '4', 'C386');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C3869', '其他非电力家用器具制造', '4', 'C386');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C387', '照明器具制造', '3', 'C38');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C3871', '电光源制造', '4', 'C387');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C3872', '照明灯具制造', '4', 'C387');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C3879', '灯用电器附件及其他照明器具制造', '4', 'C387');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C389', '其他电气机械及器材制造', '3', 'C38');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C3891', '电气信号设备装置制造', '4', 'C389');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C3899', '其他未列明电气机械及器材制造', '4', 'C389');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C39', '计算机、通信和其他电子设备制造业', '2', 'C');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C391', '计算机制造', '3', 'C39');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C3911', '计算机整机制造', '4', 'C391');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C3912', '计算机零部件制造', '4', 'C391');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C3913', '计算机外围设备制造', '4', 'C391');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C3919', '其他计算机制造', '4', 'C391');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C392', '通信设备制造', '3', 'C39');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C3921', '通信系统设备制造', '4', 'C392');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C3922', '通信终端设备制造', '4', 'C392');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C393', '广播电视设备制造', '3', 'C39');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C3931', '广播电视节目制作及发射设备制造', '4', 'C393');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C3932', '广播电视接收设备及器材制造', '4', 'C393');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C3939', '应用电视设备及其他广播电视设备制造', '4', 'C393');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C394', '雷达及配套设备制造', '3', 'C39');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C3940', '雷达及配套设备制造', '4', 'C394');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C395', '视听设备制造', '3', 'C39');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C3951', '电视机制造', '4', 'C395');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C3952', '音响设备制造', '4', 'C395');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C3953', '影视录放设备制造', '4', 'C395');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C396', '电子器件制造', '3', 'C39');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C3961', '电子真空器件制造', '4', 'C396');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C3962', '半导体分立器件制造', '4', 'C396');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C3963', '集成电路制造', '4', 'C396');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C3969', '光电子器件及其他电子器件制造', '4', 'C396');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C397', '电子元件制造', '3', 'C39');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C3971', '电子元件及组件制造', '4', 'C397');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C3972', '印制电路板制造', '4', 'C397');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C399', '其他电子设备制造', '3', 'C39');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C3990', '其他电子设备制造', '4', 'C399');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C40', '仪器仪表制造业', '2', 'C');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C401', '通用仪器仪表制造', '3', 'C40');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C4011', '工业自动控制系统装置制造', '4', 'C401');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C4012', '电工仪器仪表制造', '4', 'C401');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C4013', '绘图、计算及测量仪器制造', '4', 'C401');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C4014', '实验分析仪器制造', '4', 'C401');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C4015', '试验机制造', '4', 'C401');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C4019', '供应用仪表及其他通用仪器制造', '4', 'C401');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C402', '专用仪器仪表制造', '3', 'C40');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C4021', '环境监测专用仪器仪表制造', '4', 'C402');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C4022', '运输设备及生产用计数仪表制造', '4', 'C402');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C4023', '导航、气象及海洋专用仪器制造', '4', 'C402');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C4024', '农林牧渔专用仪器仪表制造', '4', 'C402');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C4025', '地质勘探和地震专用仪器制造', '4', 'C402');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C4026', '教学专用仪器制造', '4', 'C402');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C4027', '核子及核辐射测量仪器制造', '4', 'C402');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C4028', '电子测量仪器制造', '4', 'C402');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C4029', '其他专用仪器制造', '4', 'C402');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C403', '钟表与计时仪器制造', '3', 'C40');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C4030', '钟表与计时仪器制造', '4', 'C403');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C404', '光学仪器及眼镜制造', '3', 'C40');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C4041', '光学仪器制造', '4', 'C404');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C4042', '眼镜制造', '4', 'C404');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C409', '其他仪器仪表制造业', '3', 'C40');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C4090', '其他仪器仪表制造业', '4', 'C409');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C41', '其他制造业', '2', 'C');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C411', '日用杂品制造', '3', 'C41');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C4111', '鬃毛加工、制刷及清扫工具制造', '4', 'C411');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C4119', '其他日用杂品制造', '4', 'C411');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C412', '煤制品制造', '3', 'C41');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C4120', '煤制品制造', '4', 'C412');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C413', '核辐射加工', '3', 'C41');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C4130', '核辐射加工', '4', 'C413');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C419', '其他未列明制造业', '3', 'C41');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C4190', '其他未列明制造业', '4', 'C419');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C42', '废弃资源综合利用业', '2', 'C');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C421', '金属废料和碎屑加工处理', '3', 'C42');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C4210', '金属废料和碎屑加工处理', '4', 'C421');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C422', '非金属废料和碎屑加工处理', '3', 'C42');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C4220', '非金属废料和碎屑加工处理', '4', 'C422');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C43', '金属制品、机械和设备修理业', '2', 'C');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C431', '金属制品修理', '3', 'C43');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C4310', '金属制品修理', '4', 'C431');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C432', '通用设备修理', '3', 'C43');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C4320', '通用设备修理', '4', 'C432');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C433', '专用设备修理', '3', 'C43');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C4330', '专用设备修理', '4', 'C433');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C434', '铁路、船舶、航空航天等运输设备修理', '3', 'C43');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C4341', '铁路运输设备修理', '4', 'C434');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C4342', '船舶修理', '4', 'C434');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C4343', '航空航天器修理', '4', 'C434');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C4349', '其他运输设备修理', '4', 'C434');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C435', '电气设备修理', '3', 'C43');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C4350', '电气设备修理', '4', 'C435');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C436', '仪器仪表修理', '3', 'C43');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C4360', '仪器仪表修理', '4', 'C436');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C439', '其他机械和设备修理业', '3', 'C43');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('C4390', '其他机械和设备修理业', '4', 'C439');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('D', '电力、热力、燃气及水生产和供应业', '1', '');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('D44', '电力、热力生产和供应业', '2', 'D');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('D441', '电力生产', '3', 'D44');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('D4411', '火力发电', '4', 'D441');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('D4412', '水力发电', '4', 'D441');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('D4413', '核力发电', '4', 'D441');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('D4414', '风力发电', '4', 'D441');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('D4415', '太阳能发电', '4', 'D441');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('D4419', '其他电力生产', '4', 'D441');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('D442', '电力供应', '3', 'D44');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('D4420', '电力供应', '4', 'D442');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('D443', '热力生产和供应', '3', 'D44');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('D4430', '热力生产和供应', '4', 'D443');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('D45', '燃气生产和供应业', '2', 'D');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('D450', '燃气生产和供应业', '3', 'D45');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('D4500', '燃气生产和供应业', '4', 'D450');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('D46', '水的生产和供应业', '2', 'D');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('D461', '自来水生产和供应', '3', 'D46');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('D4610', '自来水生产和供应', '4', 'D461');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('D462', '污水处理及其再生利用', '3', 'D46');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('D4620', '污水处理及其再生利用', '4', 'D462');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('D469', '其他水的处理、利用与分配', '3', 'D46');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('D4690', '其他水的处理、利用与分配', '4', 'D469');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('E', '建筑业', '1', '');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('E47', '房屋建筑业', '2', 'E');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('E470', '房屋建筑业', '3', 'E47');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('E4700', '房屋建筑业', '4', 'E470');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('E48', '土木工程建筑业', '2', 'E');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('E481', '铁路、道路、隧道和桥梁工程建筑', '3', 'E48');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('E4811', '铁路工程建筑', '4', 'E481');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('E4812', '公路工程建筑', '4', 'E481');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('E4813', '市政道路工程建筑', '4', 'E481');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('E4819', '其他道路、隧道和桥梁工程建筑', '4', 'E481');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('E482', '水利和内河港口工程建筑', '3', 'E48');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('E4821', '水源及供水设施工程建筑', '4', 'E482');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('E4822', '河湖治理及防洪设施工程建筑', '4', 'E482');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('E4823', '港口及航运设施工程建筑', '4', 'E482');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('E483', '海洋工程建筑', '3', 'E48');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('E4830', '海洋工程建筑', '4', 'E483');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('E484', '工矿工程建筑', '3', 'E48');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('E4840', '工矿工程建筑', '4', 'E484');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('E485', '架线和管道工程建筑', '3', 'E48');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('E4851', '架线及设备工程建筑', '4', 'E485');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('E4852', '管道工程建筑', '4', 'E485');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('E489', '其他土木工程建筑', '3', 'E48');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('E4890', '其他土木工程建筑', '4', 'E489');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('E49', '建筑安装业', '2', 'E');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('E491', '电气安装', '3', 'E49');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('E4910', '电气安装', '4', 'E491');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('E492', '管道和设备安装', '3', 'E49');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('E4920', '管道和设备安装', '4', 'E492');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('E499', '其他建筑安装业', '3', 'E49');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('E4990', '其他建筑安装业', '4', 'E499');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('E50', '建筑装饰和其他建筑业', '2', 'E');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('E501', '建筑装饰业', '3', 'E50');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('E5010', '建筑装饰业', '4', 'E501');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('E502', '工程准备活动', '3', 'E50');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('E5021', '建筑物拆除活动', '4', 'E502');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('E5029', '其他工程准备活动', '4', 'E502');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('E503', '提供施工设备服务', '3', 'E50');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('E5030', '提供施工设备服务', '4', 'E503');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('E509', '其他未列明建筑业', '3', 'E50');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('E5090', '其他未列明建筑业', '4', 'E509');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('F', '批发和零售业', '1', '');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('F51', '批发业', '2', 'F');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('F511', '农、林、牧产品批发', '3', 'F51');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('F5111', '谷物、豆及薯类批发', '4', 'F511');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('F5112', '种子批发', '4', 'F511');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('F5113', '饲料批发', '4', 'F511');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('F5114', '棉、麻批发', '4', 'F511');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('F5115', '林业产品批发', '4', 'F511');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('F5116', '牲畜批发', '4', 'F511');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('F5119', '其他农牧产品批发', '4', 'F511');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('F512', '食品、饮料及烟草制品批发', '3', 'F51');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('F5121', '米、面制品及食用油批发', '4', 'F512');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('F5122', '糕点、糖果及糖批发', '4', 'F512');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('F5123', '果品、蔬菜批发', '4', 'F512');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('F5124', '肉、禽、蛋、奶及水产品批发', '4', 'F512');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('F5125', '盐及调味品批发', '4', 'F512');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('F5126', '营养和保健品批发', '4', 'F512');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('F5127', '酒、饮料及茶叶批发', '4', 'F512');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('F5128', '烟草制品批发', '4', 'F512');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('F5129', '其他食品批发', '4', 'F512');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('F513', '纺织、服装及家庭用品批发', '3', 'F51');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('F5131', '纺织品、针织品及原料批发', '4', 'F513');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('F5132', '服装批发', '4', 'F513');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('F5133', '鞋帽批发', '4', 'F513');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('F5134', '化妆品及卫生用品批发', '4', 'F513');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('F5135', '厨房、卫生间用具及日用杂货批发', '4', 'F513');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('F5136', '灯具、装饰物品批发', '4', 'F513');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('F5137', '家用电器批发', '4', 'F513');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('F5139', '其他家庭用品批发', '4', 'F513');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('F514', '文化、体育用品及器材批发', '3', 'F51');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('F5141', '文具用品批发', '4', 'F514');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('F5142', '体育用品及器材批发', '4', 'F514');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('F5143', '图书批发', '4', 'F514');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('F5144', '报刊批发', '4', 'F514');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('F5145', '音像制品及电子出版物批发', '4', 'F514');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('F5146', '首饰、工艺品及收藏品批发', '4', 'F514');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('F5149', '其他文化用品批发', '4', 'F514');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('F515', '医药及医疗器材批发', '3', 'F51');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('F5151', '西药批发', '4', 'F515');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('F5152', '中药批发', '4', 'F515');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('F5153', '医疗用品及器材批发', '4', 'F515');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('F516', '矿产品、建材及化工产品批发', '3', 'F51');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('F5161', '煤炭及制品批发', '4', 'F516');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('F5162', '石油及制品批发', '4', 'F516');
INSERT INTO sys_way (way_no, way_name, lev, uplev) VALUES ('F5163', '非金属矿及制品批发', '4', 'F516');


alter sequence sys_menu_menu_id_seq restart with 41;
alter sequence sys_dept_dept_id_seq restart with 6;
alter sequence sys_dict_id_seq restart with 4;
alter sequence sys_user_user_id_seq restart with 2;


--  quartz自带表结构
CREATE TABLE qrtz_job_details
(
  SCHED_NAME VARCHAR(120) NOT NULL,
  JOB_NAME  VARCHAR(200) NOT NULL,
  JOB_GROUP VARCHAR(200) NOT NULL,
  DESCRIPTION VARCHAR(250) NULL,
  JOB_CLASS_NAME   VARCHAR(250) NOT NULL,
  IS_DURABLE BOOL NOT NULL,
  IS_NONCONCURRENT BOOL NOT NULL,
  IS_UPDATE_DATA BOOL NOT NULL,
  REQUESTS_RECOVERY BOOL NOT NULL,
  JOB_DATA BYTEA NULL,
  PRIMARY KEY (SCHED_NAME,JOB_NAME,JOB_GROUP)
);

CREATE TABLE qrtz_triggers
(
  SCHED_NAME VARCHAR(120) NOT NULL,
  TRIGGER_NAME VARCHAR(200) NOT NULL,
  TRIGGER_GROUP VARCHAR(200) NOT NULL,
  JOB_NAME  VARCHAR(200) NOT NULL,
  JOB_GROUP VARCHAR(200) NOT NULL,
  DESCRIPTION VARCHAR(250) NULL,
  NEXT_FIRE_TIME BIGINT NULL,
  PREV_FIRE_TIME BIGINT NULL,
  PRIORITY INTEGER NULL,
  TRIGGER_STATE VARCHAR(16) NOT NULL,
  TRIGGER_TYPE VARCHAR(8) NOT NULL,
  START_TIME BIGINT NOT NULL,
  END_TIME BIGINT NULL,
  CALENDAR_NAME VARCHAR(200) NULL,
  MISFIRE_INSTR SMALLINT NULL,
  JOB_DATA BYTEA NULL,
  PRIMARY KEY (SCHED_NAME,TRIGGER_NAME,TRIGGER_GROUP),
  FOREIGN KEY (SCHED_NAME,JOB_NAME,JOB_GROUP)
  REFERENCES QRTZ_JOB_DETAILS(SCHED_NAME,JOB_NAME,JOB_GROUP)
);

CREATE TABLE qrtz_simple_triggers
(
  SCHED_NAME VARCHAR(120) NOT NULL,
  TRIGGER_NAME VARCHAR(200) NOT NULL,
  TRIGGER_GROUP VARCHAR(200) NOT NULL,
  REPEAT_COUNT BIGINT NOT NULL,
  REPEAT_INTERVAL BIGINT NOT NULL,
  TIMES_TRIGGERED BIGINT NOT NULL,
  PRIMARY KEY (SCHED_NAME,TRIGGER_NAME,TRIGGER_GROUP),
  FOREIGN KEY (SCHED_NAME,TRIGGER_NAME,TRIGGER_GROUP)
  REFERENCES QRTZ_TRIGGERS(SCHED_NAME,TRIGGER_NAME,TRIGGER_GROUP)
);

CREATE TABLE qrtz_cron_triggers
(
  SCHED_NAME VARCHAR(120) NOT NULL,
  TRIGGER_NAME VARCHAR(200) NOT NULL,
  TRIGGER_GROUP VARCHAR(200) NOT NULL,
  CRON_EXPRESSION VARCHAR(120) NOT NULL,
  TIME_ZONE_ID VARCHAR(80),
  PRIMARY KEY (SCHED_NAME,TRIGGER_NAME,TRIGGER_GROUP),
  FOREIGN KEY (SCHED_NAME,TRIGGER_NAME,TRIGGER_GROUP)
  REFERENCES QRTZ_TRIGGERS(SCHED_NAME,TRIGGER_NAME,TRIGGER_GROUP)
);

CREATE TABLE qrtz_simprop_triggers
(
  SCHED_NAME VARCHAR(120) NOT NULL,
  TRIGGER_NAME VARCHAR(200) NOT NULL,
  TRIGGER_GROUP VARCHAR(200) NOT NULL,
  STR_PROP_1 VARCHAR(512) NULL,
  STR_PROP_2 VARCHAR(512) NULL,
  STR_PROP_3 VARCHAR(512) NULL,
  INT_PROP_1 INT NULL,
  INT_PROP_2 INT NULL,
  LONG_PROP_1 BIGINT NULL,
  LONG_PROP_2 BIGINT NULL,
  DEC_PROP_1 NUMERIC(13,4) NULL,
  DEC_PROP_2 NUMERIC(13,4) NULL,
  BOOL_PROP_1 BOOL NULL,
  BOOL_PROP_2 BOOL NULL,
  PRIMARY KEY (SCHED_NAME,TRIGGER_NAME,TRIGGER_GROUP),
  FOREIGN KEY (SCHED_NAME,TRIGGER_NAME,TRIGGER_GROUP)
  REFERENCES QRTZ_TRIGGERS(SCHED_NAME,TRIGGER_NAME,TRIGGER_GROUP)
);

CREATE TABLE qrtz_blob_triggers
(
  SCHED_NAME VARCHAR(120) NOT NULL,
  TRIGGER_NAME VARCHAR(200) NOT NULL,
  TRIGGER_GROUP VARCHAR(200) NOT NULL,
  BLOB_DATA BYTEA NULL,
  PRIMARY KEY (SCHED_NAME,TRIGGER_NAME,TRIGGER_GROUP),
  FOREIGN KEY (SCHED_NAME,TRIGGER_NAME,TRIGGER_GROUP)
  REFERENCES QRTZ_TRIGGERS(SCHED_NAME,TRIGGER_NAME,TRIGGER_GROUP)
);

CREATE TABLE qrtz_calendars
(
  SCHED_NAME VARCHAR(120) NOT NULL,
  CALENDAR_NAME  VARCHAR(200) NOT NULL,
  CALENDAR BYTEA NOT NULL,
  PRIMARY KEY (SCHED_NAME,CALENDAR_NAME)
);


CREATE TABLE qrtz_paused_trigger_grps
(
  SCHED_NAME VARCHAR(120) NOT NULL,
  TRIGGER_GROUP  VARCHAR(200) NOT NULL,
  PRIMARY KEY (SCHED_NAME,TRIGGER_GROUP)
);

CREATE TABLE qrtz_fired_triggers
(
  SCHED_NAME VARCHAR(120) NOT NULL,
  ENTRY_ID VARCHAR(95) NOT NULL,
  TRIGGER_NAME VARCHAR(200) NOT NULL,
  TRIGGER_GROUP VARCHAR(200) NOT NULL,
  INSTANCE_NAME VARCHAR(200) NOT NULL,
  FIRED_TIME BIGINT NOT NULL,
  SCHED_TIME BIGINT NOT NULL,
  PRIORITY INTEGER NOT NULL,
  STATE VARCHAR(16) NOT NULL,
  JOB_NAME VARCHAR(200) NULL,
  JOB_GROUP VARCHAR(200) NULL,
  IS_NONCONCURRENT BOOL NULL,
  REQUESTS_RECOVERY BOOL NULL,
  PRIMARY KEY (SCHED_NAME,ENTRY_ID)
);

CREATE TABLE qrtz_scheduler_state
(
  SCHED_NAME VARCHAR(120) NOT NULL,
  INSTANCE_NAME VARCHAR(200) NOT NULL,
  LAST_CHECKIN_TIME BIGINT NOT NULL,
  CHECKIN_INTERVAL BIGINT NOT NULL,
  PRIMARY KEY (SCHED_NAME,INSTANCE_NAME)
);

CREATE TABLE qrtz_locks
(
  SCHED_NAME VARCHAR(120) NOT NULL,
  LOCK_NAME  VARCHAR(40) NOT NULL,
  PRIMARY KEY (SCHED_NAME,LOCK_NAME)
);

create index idx_qrtz_j_req_recovery on qrtz_job_details(SCHED_NAME,REQUESTS_RECOVERY);
create index idx_qrtz_j_grp on qrtz_job_details(SCHED_NAME,JOB_GROUP);

create index idx_qrtz_t_j on qrtz_triggers(SCHED_NAME,JOB_NAME,JOB_GROUP);
create index idx_qrtz_t_jg on qrtz_triggers(SCHED_NAME,JOB_GROUP);
create index idx_qrtz_t_c on qrtz_triggers(SCHED_NAME,CALENDAR_NAME);
create index idx_qrtz_t_g on qrtz_triggers(SCHED_NAME,TRIGGER_GROUP);
create index idx_qrtz_t_state on qrtz_triggers(SCHED_NAME,TRIGGER_STATE);
create index idx_qrtz_t_n_state on qrtz_triggers(SCHED_NAME,TRIGGER_NAME,TRIGGER_GROUP,TRIGGER_STATE);
create index idx_qrtz_t_n_g_state on qrtz_triggers(SCHED_NAME,TRIGGER_GROUP,TRIGGER_STATE);
create index idx_qrtz_t_next_fire_time on qrtz_triggers(SCHED_NAME,NEXT_FIRE_TIME);
create index idx_qrtz_t_nft_st on qrtz_triggers(SCHED_NAME,TRIGGER_STATE,NEXT_FIRE_TIME);
create index idx_qrtz_t_nft_misfire on qrtz_triggers(SCHED_NAME,MISFIRE_INSTR,NEXT_FIRE_TIME);
create index idx_qrtz_t_nft_st_misfire on qrtz_triggers(SCHED_NAME,MISFIRE_INSTR,NEXT_FIRE_TIME,TRIGGER_STATE);
create index idx_qrtz_t_nft_st_misfire_grp on qrtz_triggers(SCHED_NAME,MISFIRE_INSTR,NEXT_FIRE_TIME,TRIGGER_GROUP,TRIGGER_STATE);

create index idx_qrtz_ft_trig_inst_name on qrtz_fired_triggers(SCHED_NAME,INSTANCE_NAME);
create index idx_qrtz_ft_inst_job_req_rcvry on qrtz_fired_triggers(SCHED_NAME,INSTANCE_NAME,REQUESTS_RECOVERY);
create index idx_qrtz_ft_j_g on qrtz_fired_triggers(SCHED_NAME,JOB_NAME,JOB_GROUP);
create index idx_qrtz_ft_jg on qrtz_fired_triggers(SCHED_NAME,JOB_GROUP);
create index idx_qrtz_ft_t_g on qrtz_fired_triggers(SCHED_NAME,TRIGGER_NAME,TRIGGER_GROUP);
create index idx_qrtz_ft_tg on qrtz_fired_triggers(SCHED_NAME,TRIGGER_GROUP);

DROP TABLE chart_test;
CREATE TABLE chart_test (id BIGINT NOT NULL, model CHARACTER VARYING(255), val CHARACTER VARYING(255), "type" CHARACTER VARYING(255), CONSTRAINT pk_chart_test PRIMARY KEY (id));
INSERT INTO chart_test (id, model, val, type) VALUES (1, '1', '100', '1');
INSERT INTO chart_test (id, model, val, type) VALUES (2, '2', '200', '2');
INSERT INTO chart_test (id, model, val, type) VALUES (3, '3', '300', '1');
INSERT INTO chart_test (id, model, val, type) VALUES (4, '4', '400', '2');
INSERT INTO chart_test (id, model, val, type) VALUES (5, '1', '500', '1');
INSERT INTO chart_test (id, model, val, type) VALUES (6, '2', '600', '2');
INSERT INTO chart_test (id, model, val, type) VALUES (7, '3', '700', '1');
INSERT INTO chart_test (id, model, val, type) VALUES (8, '4', '800', '2');
DROP TABLE data_set;
CREATE TABLE data_set (dt_id BIGINT NOT NULL, dt_name CHARACTER VARYING(80), ds_id BIGINT, type CHARACTER VARYING(10), sql CHARACTER VARYING(5000), data_type CHARACTER VARYING(10), sts CHARACTER VARYING(1), tx_time TIMESTAMP(6) WITHOUT TIME ZONE, up_time TIMESTAMP(6) WITHOUT TIME ZONE, tx_op CHARACTER VARYING(30), up_op CHARACTER VARYING(30), order_by_str CHARACTER VARYING(255), CONSTRAINT pk_data_set PRIMARY KEY (dt_id));
INSERT INTO data_set (dt_id, dt_name, ds_id, type, sql, data_type, sts, tx_time, up_time, tx_op, up_op, order_by_str) VALUES (4, '测试SQL', 107, 'mysql', 'select * from db_type', 'sql', '1', null, '2019-01-10 12:02:50', '', 'admin', '');
INSERT INTO data_set (dt_id, dt_name, ds_id, type, sql, data_type, sts, tx_time, up_time, tx_op, up_op, order_by_str) VALUES (11, '测试数据', 107, 'mysql', 'select * from (select * from chart_test order by id asc) a order by id desc', 'sql', '1', null, '2019-04-17 14:27:35', '', 'admin', '');
INSERT INTO data_set (dt_id, dt_name, ds_id, type, sql, data_type, sts, tx_time, up_time, tx_op, up_op, order_by_str) VALUES (12, 'dept_year', 121, 'rsdb', 'select * from dept_year', 'sql', '1', '2018-12-07 17:16:04', '2018-12-07 17:16:04', 'admin', 'admin', '');
INSERT INTO data_set (dt_id, dt_name, ds_id, type, sql, data_type, sts, tx_time, up_time, tx_op, up_op, order_by_str) VALUES (15, '列表', 121, 'mysql', 'select * from demo_user', 'sql', '1', '2019-03-05 09:50:05', '2019-03-05 09:50:05', 'admin', 'admin', '');
INSERT INTO data_set (dt_id, dt_name, ds_id, type, sql, data_type, sts, tx_time, up_time, tx_op, up_op, order_by_str) VALUES (7, '系统日志', 107, 'rsdb', 'select * from sys_log', 'sql', '1', null, '2019-04-19 10:22:39', '', 'admin', '');
DROP TABLE data_source;
CREATE TABLE data_source (id BIGINT NOT NULL, name CHARACTER VARYING(80), model CHARACTER VARYING(10), type CHARACTER VARYING(20), version CHARACTER VARYING(10), driver CHARACTER VARYING(100), addr CHARACTER VARYING(300), usr CHARACTER VARYING(100), password CHARACTER VARYING(200), icon CHARACTER VARYING(20), readonly CHARACTER VARYING(1), sts CHARACTER VARYING(1), tx_time TIMESTAMP(6) WITHOUT TIME ZONE, up_time TIMESTAMP(6) WITHOUT TIME ZONE, tx_op CHARACTER VARYING(30), up_op CHARACTER VARYING(30), CONSTRAINT pk_data_source PRIMARY KEY (id));
INSERT INTO data_source (id, name, model, type, version, driver, addr, usr, password, icon, readonly, sts, tx_time, up_time, tx_op, up_op) VALUES (131, 'Oracle1', 'rsdb', 'oracle', '', 'oracle.jdbc.driver.OracleDriver', 'jdbc:oracle:thin:@//<host>:<port>/ServiceName', '', '', 'Oracle', '1', '1', '2018-11-02 13:51:24', '2018-11-02 13:51:24', 'admin', 'admin');
INSERT INTO data_source (id, name, model, type, version, driver, addr, usr, password, icon, readonly, sts, tx_time, up_time, tx_op, up_op) VALUES (133, 'PostgreSQL', 'rsdb', 'postgresql', '', 'org.postgresql.Driver', 'jdbc:postgresql://128.1.13.24:5432/postgres', 'postgres', 'postgres', 'PostgreSQL', '1', '1', '2018-11-05 09:27:02', '2018-11-05 09:27:02', 'admin', 'admin');
INSERT INTO data_source (id, name, model, type, version, driver, addr, usr, password, icon, readonly, sts, tx_time, up_time, tx_op, up_op) VALUES (134, 'SQL Server 2005', 'rsdb', 'sqlserver', '', 'com.microsoft.sqlserver.jdbc.SQLServerDriver', 'jdbc:sqlserver://128.1.13.24:1433;DatabaseName=REPORT', 'REPORT', 'REPORT', 'SqlServer', '0', '1', null, '2018-12-17 11:36:10', '', 'admin');
INSERT INTO data_source (id, name, model, type, version, driver, addr, usr, password, icon, readonly, sts, tx_time, up_time, tx_op, up_op) VALUES (135, 'Db2', 'rsdb', 'db2', '', 'com.ibm.db2.jcc.DB2Driver', 'jdbc:db2://128.1.13.24:50000/REPORT', 'REPORT', 'REPORT', 'Db2 type2', '1', '1', null, '2018-11-08 09:44:29', '', 'admin');
INSERT INTO data_source (id, name, model, type, version, driver, addr, usr, password, icon, readonly, sts, tx_time, up_time, tx_op, up_op) VALUES (141, 'jsonaaa', 'json', 'json', '', '', 'https://api.github.com', '', '', 'Json', '', '1', '2018-11-09 14:59:32', '2018-11-09 14:59:32', 'admin', 'admin');
INSERT INTO data_source (id, name, model, type, version, driver, addr, usr, password, icon, readonly, sts, tx_time, up_time, tx_op, up_op) VALUES (146, 'MySql-test', 'rsdb', 'mysql', '', 'com.mysql.jdbc.Driver', 'jdbc:mysql://128.1.20.33:3306/rdp_server', 'root', 'root', 'MySql', '0', '1', null, '2018-11-14 16:29:52', '', 'admin');
INSERT INTO data_source (id, name, model, type, version, driver, addr, usr, password, icon, readonly, sts, tx_time, up_time, tx_op, up_op) VALUES (148, '天气', 'json', 'json', '', '', 'http://t.weather.sojson.com/api/weather/city/101030100', '', '', 'Json', '', '1', null, '2018-11-16 14:49:14', '', 'admin');
INSERT INTO data_source (id, name, model, type, version, driver, addr, usr, password, icon, readonly, sts, tx_time, up_time, tx_op, up_op) VALUES (149, 'jsonlocal', 'json', 'json', '', '', 'http://localhost', 'admin', 'admin', 'Json', '', '1', '2018-11-19 09:17:33', '2018-11-19 09:17:33', 'admin', 'admin');
INSERT INTO data_source (id, name, model, type, version, driver, addr, usr, password, icon, readonly, sts, tx_time, up_time, tx_op, up_op) VALUES (150, 'Oracle2', 'rsdb', 'oracle', '', 'oracle.jdbc.driver.OracleDriver', 'jdbc:oracle:thin://128.1.20.33:1521/orcl', 'zlxt', 'zlxt', 'Oracle', '0', '1', null, '2019-03-23 09:08:47', '', 'admin');
INSERT INTO data_source (id, name, model, type, version, driver, addr, usr, password, icon, readonly, sts, tx_time, up_time, tx_op, up_op) VALUES (151, 'jishiyu', 'rsdb', 'mysql', '', 'com.mysql.jdbc.Driver', 'jdbc:mysql://localhost:3366/factor_platform?useUnicode=true&characterEncoding=utf-8&useSSL=true', 'factor', 'admin', 'MySql', '1', '1', null, '2018-11-26 15:16:01', '', 'admin');
INSERT INTO data_source (id, name, model, type, version, driver, addr, usr, password, icon, readonly, sts, tx_time, up_time, tx_op, up_op) VALUES (152, 'wangqing', 'rsdb', 'oracle', '', 'oracle.jdbc.driver.OracleDriver', 'jdbc:oracle:thin:@128.1.13.22:1521:orcl', 'cmsii', 'cmsii', 'Oracle', '1', '1', null, '2018-11-27 14:32:46', '', 'admin');
INSERT INTO data_source (id, name, model, type, version, driver, addr, usr, password, icon, readonly, sts, tx_time, up_time, tx_op, up_op) VALUES (153, '中文', 'rsdb', 'mysql', '', 'com.mysql.jdbc.Driver', 'jdbc:mysql://128.1.13.24:3306/report_demo', 'root', 'dhccpass', 'MySql', '1', '1', '2018-11-27 16:09:12', '2018-11-27 16:09:12', 'admin', 'admin');
INSERT INTO data_source (id, name, model, type, version, driver, addr, usr, password, icon, readonly, sts, tx_time, up_time, tx_op, up_op) VALUES (158, 'asdasd', 'jndi', 'mysql', '', '', 'asdasd', '', '', 'Jndi', '1', '1', '2018-11-30 09:29:47', '2018-11-30 09:29:47', 'admin', 'admin');
INSERT INTO data_source (id, name, model, type, version, driver, addr, usr, password, icon, readonly, sts, tx_time, up_time, tx_op, up_op) VALUES (159, 'asdas22', 'jndi', 'mysql', '', '', 'asdasd', '', '', 'Jndi', '1', '1', '2018-11-30 09:37:04', '2018-11-30 09:37:04', 'admin', 'admin');
INSERT INTO data_source (id, name, model, type, version, driver, addr, usr, password, icon, readonly, sts, tx_time, up_time, tx_op, up_op) VALUES (160, 'asdas22eee', 'jndi', 'mysql', '', '', 'qweqwe', '', '', 'Jndi', '1', '1', '2018-11-30 09:37:39', '2018-11-30 09:37:39', 'admin', 'admin');
INSERT INTO data_source (id, name, model, type, version, driver, addr, usr, password, icon, readonly, sts, tx_time, up_time, tx_op, up_op) VALUES (161, 'jnreport_demo', 'jndi', 'mysql', '', '', 'java:comp/env/jdbc/report_demo', '', '', 'Jndi', '1', '1', null, '2018-11-30 10:44:48', '', 'admin');
INSERT INTO data_source (id, name, model, type, version, driver, addr, usr, password, icon, readonly, sts, tx_time, up_time, tx_op, up_op) VALUES (164, 'MYSQL', 'rsdb', 'mysql', '', 'com.mysql.jdbc.Driver', 'jdbc:mysql://localhost:3366/factor_platform?characterEncoding=utf8&useSSL=false&serverTimezone=UTC', 'factor', 'admin', 'MySql', '1', '1', null, '2018-12-18 13:43:01', '', 'admin');
INSERT INTO data_source (id, name, model, type, version, driver, addr, usr, password, icon, readonly, sts, tx_time, up_time, tx_op, up_op) VALUES (166, 'local', 'json', 'json', '', '', 'http://localhost/', 'admin', 'admin', 'Json', '', '1', '2018-12-14 16:44:03', '2018-12-14 16:44:03', 'admin', 'admin');
INSERT INTO data_source (id, name, model, type, version, driver, addr, usr, password, icon, readonly, sts, tx_time, up_time, tx_op, up_op) VALUES (167, 'ms_report', 'rsdb', 'sqlserver', '', 'com.microsoft.sqlserver.jdbc.SQLServerDriver', 'jdbc:sqlserver://128.1.13.24:1433;DatabaseName=report_demo', 'sa', 'sa', 'SqlServer', '0', '1', null, '2018-12-26 11:45:56', '', 'admin');
INSERT INTO data_source (id, name, model, type, version, driver, addr, usr, password, icon, readonly, sts, tx_time, up_time, tx_op, up_op) VALUES (168, 'rdp_test', 'rsdb', 'oracle', '', 'oracle.jdbc.driver.OracleDriver', 'jdbc:oracle:thin:@128.1.20.33:1521:orcl', 'rdp_admin', 'rdp_admin', 'Oracle', '1', '1', null, '2019-03-07 11:27:03', '', 'admin');
INSERT INTO data_source (id, name, model, type, version, driver, addr, usr, password, icon, readonly, sts, tx_time, up_time, tx_op, up_op) VALUES (107, 'MySql-24', 'rsdb', 'mysql', null, 'com.mysql.jdbc.Driver', 'jdbc:mysql://128.1.13.24:3306/rdp_server?characterEncoding=utf8&useSSL=false&serverTimezone=UTC', 'root', 'dhccpass', 'MySql', '0', '1', null, '2019-04-19 09:59:02', null, 'admin');
INSERT INTO data_source (id, name, model, type, version, driver, addr, usr, password, icon, readonly, sts, tx_time, up_time, tx_op, up_op) VALUES (121, 'report_demo', 'rsdb', 'mysql', '', 'com.mysql.jdbc.Driver', 'jdbc:mysql://128.1.13.24:3306/report_demo?characterEncoding=utf8&amp;useSSL=false&amp;serverTimezone=UTC', 'root', 'dhccpass', 'MySql', '0', '1', null, '2019-04-19 09:59:08', '', 'admin');
INSERT INTO data_source (id, name, model, type, version, driver, addr, usr, password, icon, readonly, sts, tx_time, up_time, tx_op, up_op) VALUES (123, 'cms_oracle', 'oracle', 'oracle', null, 'oracle.jdbc.driver.OracleDriver', 'jdbc:oracle:thin:@128.1.13.22:1521:orcl', 'cmsii', 'cmsii', '', '0', '1', null, '2019-04-19 10:22:30', null, 'admin');
INSERT INTO data_source (id, name, model, type, version, driver, addr, usr, password, icon, readonly, sts, tx_time, up_time, tx_op, up_op) VALUES (132, 'Json', 'file', 'Json', '', '', 'https://api.github.com/search/', '', '', 'Json', '1', '1', null, '2019-04-19 10:22:35', '', 'admin');
DROP TABLE db_type;
CREATE TABLE db_type (id BIGINT NOT NULL, name CHARACTER VARYING(80), model CHARACTER VARYING(20), type CHARACTER VARYING(20), driver CHARACTER VARYING(100), addr_demo CHARACTER VARYING(120), sts CHARACTER VARYING(1), icon CHARACTER VARYING(80), CONSTRAINT pk_db_type PRIMARY KEY (id));
INSERT INTO db_type (id, name, model, type, driver, addr_demo, sts, icon) VALUES (101, 'MySql', 'rsdb', 'mysql', 'com.mysql.jdbc.Driver', 'jdbc:mysql://<host>:<port>/<database_name>', '1', 'MySql');
INSERT INTO db_type (id, name, model, type, driver, addr_demo, sts, icon) VALUES (201, 'Oracle', 'rsdb', 'oracle', 'oracle.jdbc.driver.OracleDriver', 'jdbc:oracle:thin:@<host>:<port>:<ServiceName>', '1', 'Oracle');
INSERT INTO db_type (id, name, model, type, driver, addr_demo, sts, icon) VALUES (301, 'Db2 type2', 'rsdb', 'db2', 'com.ibm.db2.jcc.DB2Driver', 'jdbc:db2:<database_name>', '1', 'Db2 type2');
INSERT INTO db_type (id, name, model, type, driver, addr_demo, sts, icon) VALUES (302, 'Db2 type4', 'rsdb', 'db2', 'com.ibm.db2.jcc.DB2Driver', 'jdbc:db2://<host>[:<port>]/<database_name>', '1', 'Db2 type4');
INSERT INTO db_type (id, name, model, type, driver, addr_demo, sts, icon) VALUES (401, 'SQL Server 2000', 'rsdb', 'sqlserver', 'com.microsoft.jdbc.sqlserver.SQLServerDriver', 'jdbc:microsoft:sqlserver://:', '0', 'SqlServer');
INSERT INTO db_type (id, name, model, type, driver, addr_demo, sts, icon) VALUES (402, 'SQL Server 2005', 'rsdb', 'sqlserver', 'com.microsoft.sqlserver.jdbc.SQLServerDriver', 'jdbc:sqlserver://<server_name>:<port>;DatabaseName=<DatabaseName>', '1', 'SqlServer');
INSERT INTO db_type (id, name, model, type, driver, addr_demo, sts, icon) VALUES (501, 'Sybase', 'rsdb', 'sysbase', 'com.sybase.jdbc3.jdbc.SybDriver', 'jdbc:sybase:Tds::', '0', 'Sybase');
INSERT INTO db_type (id, name, model, type, driver, addr_demo, sts, icon) VALUES (601, 'PostgreSQL', 'rsdb', 'postgresql', 'org.postgresql.Driver', 'jdbc:postgresql://:/', '1', 'PostgreSQL');
INSERT INTO db_type (id, name, model, type, driver, addr_demo, sts, icon) VALUES (701, 'SQLite', 'rsdb', 'sqlite', 'org.sqlite.JDBC', 'jdbc:sqlite:D:\\xxxdatabase.db', '0', 'SQLite');
INSERT INTO db_type (id, name, model, type, driver, addr_demo, sts, icon) VALUES (801, 'MongoDB', 'nosqldb', 'mongodb', '', 'mongodb://userName:password@host/?authSource=databaseName&amp;ssh=false', '0', 'MongoDB');
INSERT INTO db_type (id, name, model, type, driver, addr_demo, sts, icon) VALUES (901, 'Xml', 'file', 'xml', '', '', '0', 'Xml');
INSERT INTO db_type (id, name, model, type, driver, addr_demo, sts, icon) VALUES (902, 'API请求', 'file', 'json', '', '', '1', 'Json');
INSERT INTO db_type (id, name, model, type, driver, addr_demo, sts, icon) VALUES (903, 'Csv', 'file', 'csv', '', '', '0', 'Csv');
INSERT INTO db_type (id, name, model, type, driver, addr_demo, sts, icon) VALUES (904, 'Excel', 'file', 'excel', '', '', '0', 'Excel');
INSERT INTO db_type (id, name, model, type, driver, addr_demo, sts, icon) VALUES (1001, 'JNDI', 'jndi', 'jndi', '', '', '1', 'Jndi');
DROP TABLE ds_showcol;
CREATE TABLE ds_showcol (show_id BIGINT NOT NULL, dt_id BIGINT, use_id BIGINT, is_show CHARACTER VARYING(1), data_type CHARACTER VARYING(10), alias CHARACTER VARYING(80), describe CHARACTER VARYING(200), tx_time TIMESTAMP(6) WITHOUT TIME ZONE, tx_op CHARACTER VARYING(255), CONSTRAINT pk_ds_showcol PRIMARY KEY (show_id));
DROP TABLE dt_filter;
CREATE TABLE dt_filter (filter_id BIGINT NOT NULL, dt_id BIGINT, table_name CHARACTER VARYING(80), "column" CHARACTER VARYING(30), col_type CHARACTER VARYING(1), operator CHARACTER VARYING(10), param_type CHARACTER VARYING(1), param CHARACTER VARYING(30), tx_time TIMESTAMP(6) WITHOUT TIME ZONE, up_time TIMESTAMP(6) WITHOUT TIME ZONE, tx_op CHARACTER VARYING(30), up_op CHARACTER VARYING(30), CONSTRAINT pk_dt_filter PRIMARY KEY (filter_id));
DROP TABLE dt_param;
CREATE TABLE dt_param (param_id BIGINT NOT NULL, param_name CHARACTER VARYING(80), dt_id BIGINT, param_type CHARACTER VARYING(255), data_type CHARACTER VARYING(255), if_values CHARACTER VARYING(1), tx_time TIMESTAMP(6) WITHOUT TIME ZONE, up_time TIMESTAMP(6) WITHOUT TIME ZONE, tx_op CHARACTER VARYING(30), up_op CHARACTER VARYING(30), CONSTRAINT pk_dt_param PRIMARY KEY (param_id));
DROP TABLE dt_table;
CREATE TABLE dt_table (use_id BIGINT NOT NULL, dt_id BIGINT NOT NULL, table_name CHARACTER VARYING(80), table_comments CHARACTER VARYING(120), level CHARACTER VARYING(1), show_name CHARACTER VARYING(80), px BIGINT, py BIGINT, tx_time TIMESTAMP(6) WITHOUT TIME ZONE, up_time TIMESTAMP(6) WITHOUT TIME ZONE, tx_op CHARACTER VARYING(30), up_op CHARACTER VARYING(30), CONSTRAINT pk_dt_table PRIMARY KEY (use_id, dt_id));
DROP TABLE dt_table_rs;
CREATE TABLE dt_table_rs (rs_id BIGINT NOT NULL, use_id BIGINT NOT NULL, rs_type CHARACTER VARYING(10), rs_model CHARACTER VARYING(1), tx_time TIMESTAMP(6) WITHOUT TIME ZONE, up_time TIMESTAMP(6) WITHOUT TIME ZONE, tx_op CHARACTER VARYING(30), up_op CHARACTER VARYING(30), CONSTRAINT pk_dt_table_rs PRIMARY KEY (rs_id, use_id));
DROP TABLE dt_table_rs_condition;
CREATE TABLE dt_table_rs_condition (cd_id BIGINT NOT NULL, cd_type CHARACTER VARYING(10), rename CHARACTER VARYING(255), left_table CHARACTER VARYING(255), left_col CHARACTER VARYING(255), operator CHARACTER VARYING(255), right_table CHARACTER VARYING(255), right_col CHARACTER VARYING(255), tx_time TIMESTAMP(6) WITHOUT TIME ZONE, tx_op CHARACTER VARYING(255), CONSTRAINT pk_dt_table_rs_condition PRIMARY KEY (cd_id));

DROP TABLE chart_test;
CREATE TABLE chart_test (id BIGINT NOT NULL, model CHARACTER VARYING(255), val CHARACTER VARYING(255), type CHARACTER VARYING(255), CONSTRAINT pk_chart_test PRIMARY KEY (id));
INSERT INTO chart_test (id, model, val, type) VALUES (1, '1', '100', '1');
INSERT INTO chart_test (id, model, val, type) VALUES (2, '2', '200', '2');
INSERT INTO chart_test (id, model, val, type) VALUES (3, '3', '300', '1');
INSERT INTO chart_test (id, model, val, type) VALUES (4, '4', '400', '2');
INSERT INTO chart_test (id, model, val, type) VALUES (5, '1', '500', '1');
INSERT INTO chart_test (id, model, val, type) VALUES (6, '2', '600', '2');
INSERT INTO chart_test (id, model, val, type) VALUES (7, '3', '700', '1');
INSERT INTO chart_test (id, model, val, type) VALUES (8, '4', '800', '2');
DROP TABLE data_set;
CREATE TABLE data_set (dt_id BIGINT NOT NULL, dt_name CHARACTER VARYING(80), ds_id BIGINT, type CHARACTER VARYING(10), sql CHARACTER VARYING(5000), data_type CHARACTER VARYING(10), sts CHARACTER VARYING(1), tx_time TIMESTAMP(6) WITHOUT TIME ZONE, up_time TIMESTAMP(6) WITHOUT TIME ZONE, tx_op CHARACTER VARYING(30), up_op CHARACTER VARYING(30), order_by_str CHARACTER VARYING(255), CONSTRAINT pk_data_set PRIMARY KEY (dt_id));
INSERT INTO data_set (dt_id, dt_name, ds_id, type, sql, data_type, sts, tx_time, up_time, tx_op, up_op, order_by_str) VALUES (4, '测试SQL', 107, 'mysql', 'select * from db_type', 'sql', '1', null, '2019-01-10 12:02:50', '', 'admin', '');
INSERT INTO data_set (dt_id, dt_name, ds_id, type, sql, data_type, sts, tx_time, up_time, tx_op, up_op, order_by_str) VALUES (11, '测试数据', 107, 'mysql', 'select * from (select * from chart_test order by id asc) a order by id desc', 'sql', '1', null, '2019-04-17 14:27:35', '', 'admin', '');
INSERT INTO data_set (dt_id, dt_name, ds_id, type, sql, data_type, sts, tx_time, up_time, tx_op, up_op, order_by_str) VALUES (12, 'dept_year', 121, 'rsdb', 'select * from dept_year', 'sql', '1', '2018-12-07 17:16:04', '2018-12-07 17:16:04', 'admin', 'admin', '');
INSERT INTO data_set (dt_id, dt_name, ds_id, type, sql, data_type, sts, tx_time, up_time, tx_op, up_op, order_by_str) VALUES (15, '列表', 121, 'mysql', 'select * from demo_user', 'sql', '1', '2019-03-05 09:50:05', '2019-03-05 09:50:05', 'admin', 'admin', '');
INSERT INTO data_set (dt_id, dt_name, ds_id, type, sql, data_type, sts, tx_time, up_time, tx_op, up_op, order_by_str) VALUES (7, '系统日志', 107, 'rsdb', 'select * from sys_log', 'sql', '1', null, '2019-04-19 10:22:39', '', 'admin', '');
DROP TABLE data_source;
CREATE TABLE data_source (id BIGINT NOT NULL, name CHARACTER VARYING(80), model CHARACTER VARYING(10), type CHARACTER VARYING(20), version CHARACTER VARYING(10), driver CHARACTER VARYING(100), addr CHARACTER VARYING(300), usr CHARACTER VARYING(100), password CHARACTER VARYING(200), icon CHARACTER VARYING(20), readonly CHARACTER VARYING(1), sts CHARACTER VARYING(1), tx_time TIMESTAMP(6) WITHOUT TIME ZONE, up_time TIMESTAMP(6) WITHOUT TIME ZONE, tx_op CHARACTER VARYING(30), up_op CHARACTER VARYING(30), CONSTRAINT pk_data_source PRIMARY KEY (id));
INSERT INTO data_source (id, name, model, type, version, driver, addr, usr, password, icon, readonly, sts, tx_time, up_time, tx_op, up_op) VALUES (131, 'Oracle1', 'rsdb', 'oracle', '', 'oracle.jdbc.driver.OracleDriver', 'jdbc:oracle:thin:@//<host>:<port>/ServiceName', '', '', 'Oracle', '1', '1', '2018-11-02 13:51:24', '2018-11-02 13:51:24', 'admin', 'admin');
INSERT INTO data_source (id, name, model, type, version, driver, addr, usr, password, icon, readonly, sts, tx_time, up_time, tx_op, up_op) VALUES (133, 'PostgreSQL', 'rsdb', 'postgresql', '', 'org.postgresql.Driver', 'jdbc:postgresql://128.1.13.24:5432/postgres', 'postgres', 'postgres', 'PostgreSQL', '1', '1', '2018-11-05 09:27:02', '2018-11-05 09:27:02', 'admin', 'admin');
INSERT INTO data_source (id, name, model, type, version, driver, addr, usr, password, icon, readonly, sts, tx_time, up_time, tx_op, up_op) VALUES (134, 'SQL Server 2005', 'rsdb', 'sqlserver', '', 'com.microsoft.sqlserver.jdbc.SQLServerDriver', 'jdbc:sqlserver://128.1.13.24:1433;DatabaseName=REPORT', 'REPORT', 'REPORT', 'SqlServer', '0', '1', null, '2018-12-17 11:36:10', '', 'admin');
INSERT INTO data_source (id, name, model, type, version, driver, addr, usr, password, icon, readonly, sts, tx_time, up_time, tx_op, up_op) VALUES (135, 'Db2', 'rsdb', 'db2', '', 'com.ibm.db2.jcc.DB2Driver', 'jdbc:db2://128.1.13.24:50000/REPORT', 'REPORT', 'REPORT', 'Db2 type2', '1', '1', null, '2018-11-08 09:44:29', '', 'admin');
INSERT INTO data_source (id, name, model, type, version, driver, addr, usr, password, icon, readonly, sts, tx_time, up_time, tx_op, up_op) VALUES (141, 'jsonaaa', 'json', 'json', '', '', 'https://api.github.com', '', '', 'Json', '', '1', '2018-11-09 14:59:32', '2018-11-09 14:59:32', 'admin', 'admin');
INSERT INTO data_source (id, name, model, type, version, driver, addr, usr, password, icon, readonly, sts, tx_time, up_time, tx_op, up_op) VALUES (146, 'MySql-test', 'rsdb', 'mysql', '', 'com.mysql.jdbc.Driver', 'jdbc:mysql://128.1.20.33:3306/rdp_server', 'root', 'root', 'MySql', '0', '1', null, '2018-11-14 16:29:52', '', 'admin');
INSERT INTO data_source (id, name, model, type, version, driver, addr, usr, password, icon, readonly, sts, tx_time, up_time, tx_op, up_op) VALUES (148, '天气', 'json', 'json', '', '', 'http://t.weather.sojson.com/api/weather/city/101030100', '', '', 'Json', '', '1', null, '2018-11-16 14:49:14', '', 'admin');
INSERT INTO data_source (id, name, model, type, version, driver, addr, usr, password, icon, readonly, sts, tx_time, up_time, tx_op, up_op) VALUES (149, 'jsonlocal', 'json', 'json', '', '', 'http://localhost', 'admin', 'admin', 'Json', '', '1', '2018-11-19 09:17:33', '2018-11-19 09:17:33', 'admin', 'admin');
INSERT INTO data_source (id, name, model, type, version, driver, addr, usr, password, icon, readonly, sts, tx_time, up_time, tx_op, up_op) VALUES (150, 'Oracle2', 'rsdb', 'oracle', '', 'oracle.jdbc.driver.OracleDriver', 'jdbc:oracle:thin://128.1.20.33:1521/orcl', 'zlxt', 'zlxt', 'Oracle', '0', '1', null, '2019-03-23 09:08:47', '', 'admin');
INSERT INTO data_source (id, name, model, type, version, driver, addr, usr, password, icon, readonly, sts, tx_time, up_time, tx_op, up_op) VALUES (151, 'jishiyu', 'rsdb', 'mysql', '', 'com.mysql.jdbc.Driver', 'jdbc:mysql://localhost:3366/factor_platform?useUnicode=true&characterEncoding=utf-8&useSSL=true', 'factor', 'admin', 'MySql', '1', '1', null, '2018-11-26 15:16:01', '', 'admin');
INSERT INTO data_source (id, name, model, type, version, driver, addr, usr, password, icon, readonly, sts, tx_time, up_time, tx_op, up_op) VALUES (152, 'wangqing', 'rsdb', 'oracle', '', 'oracle.jdbc.driver.OracleDriver', 'jdbc:oracle:thin:@128.1.13.22:1521:orcl', 'cmsii', 'cmsii', 'Oracle', '1', '1', null, '2018-11-27 14:32:46', '', 'admin');
INSERT INTO data_source (id, name, model, type, version, driver, addr, usr, password, icon, readonly, sts, tx_time, up_time, tx_op, up_op) VALUES (153, '中文', 'rsdb', 'mysql', '', 'com.mysql.jdbc.Driver', 'jdbc:mysql://128.1.13.24:3306/report_demo', 'root', 'dhccpass', 'MySql', '1', '1', '2018-11-27 16:09:12', '2018-11-27 16:09:12', 'admin', 'admin');
INSERT INTO data_source (id, name, model, type, version, driver, addr, usr, password, icon, readonly, sts, tx_time, up_time, tx_op, up_op) VALUES (158, 'asdasd', 'jndi', 'mysql', '', '', 'asdasd', '', '', 'Jndi', '1', '1', '2018-11-30 09:29:47', '2018-11-30 09:29:47', 'admin', 'admin');
INSERT INTO data_source (id, name, model, type, version, driver, addr, usr, password, icon, readonly, sts, tx_time, up_time, tx_op, up_op) VALUES (159, 'asdas22', 'jndi', 'mysql', '', '', 'asdasd', '', '', 'Jndi', '1', '1', '2018-11-30 09:37:04', '2018-11-30 09:37:04', 'admin', 'admin');
INSERT INTO data_source (id, name, model, type, version, driver, addr, usr, password, icon, readonly, sts, tx_time, up_time, tx_op, up_op) VALUES (160, 'asdas22eee', 'jndi', 'mysql', '', '', 'qweqwe', '', '', 'Jndi', '1', '1', '2018-11-30 09:37:39', '2018-11-30 09:37:39', 'admin', 'admin');
INSERT INTO data_source (id, name, model, type, version, driver, addr, usr, password, icon, readonly, sts, tx_time, up_time, tx_op, up_op) VALUES (161, 'jnreport_demo', 'jndi', 'mysql', '', '', 'java:comp/env/jdbc/report_demo', '', '', 'Jndi', '1', '1', null, '2018-11-30 10:44:48', '', 'admin');
INSERT INTO data_source (id, name, model, type, version, driver, addr, usr, password, icon, readonly, sts, tx_time, up_time, tx_op, up_op) VALUES (164, 'MYSQL', 'rsdb', 'mysql', '', 'com.mysql.jdbc.Driver', 'jdbc:mysql://localhost:3366/factor_platform?characterEncoding=utf8&useSSL=false&serverTimezone=UTC', 'factor', 'admin', 'MySql', '1', '1', null, '2018-12-18 13:43:01', '', 'admin');
INSERT INTO data_source (id, name, model, type, version, driver, addr, usr, password, icon, readonly, sts, tx_time, up_time, tx_op, up_op) VALUES (166, 'local', 'json', 'json', '', '', 'http://localhost/', 'admin', 'admin', 'Json', '', '1', '2018-12-14 16:44:03', '2018-12-14 16:44:03', 'admin', 'admin');
INSERT INTO data_source (id, name, model, type, version, driver, addr, usr, password, icon, readonly, sts, tx_time, up_time, tx_op, up_op) VALUES (167, 'ms_report', 'rsdb', 'sqlserver', '', 'com.microsoft.sqlserver.jdbc.SQLServerDriver', 'jdbc:sqlserver://128.1.13.24:1433;DatabaseName=report_demo', 'sa', 'sa', 'SqlServer', '0', '1', null, '2018-12-26 11:45:56', '', 'admin');
INSERT INTO data_source (id, name, model, type, version, driver, addr, usr, password, icon, readonly, sts, tx_time, up_time, tx_op, up_op) VALUES (168, 'rdp_test', 'rsdb', 'oracle', '', 'oracle.jdbc.driver.OracleDriver', 'jdbc:oracle:thin:@128.1.20.33:1521:orcl', 'rdp_admin', 'rdp_admin', 'Oracle', '1', '1', null, '2019-03-07 11:27:03', '', 'admin');
INSERT INTO data_source (id, name, model, type, version, driver, addr, usr, password, icon, readonly, sts, tx_time, up_time, tx_op, up_op) VALUES (107, 'MySql-24', 'rsdb', 'mysql', null, 'com.mysql.jdbc.Driver', 'jdbc:mysql://128.1.13.24:3306/rdp_server?characterEncoding=utf8&useSSL=false&serverTimezone=UTC', 'root', 'dhccpass', 'MySql', '0', '1', null, '2019-04-19 09:59:02', null, 'admin');
INSERT INTO data_source (id, name, model, type, version, driver, addr, usr, password, icon, readonly, sts, tx_time, up_time, tx_op, up_op) VALUES (121, 'report_demo', 'rsdb', 'mysql', '', 'com.mysql.jdbc.Driver', 'jdbc:mysql://128.1.13.24:3306/report_demo?characterEncoding=utf8&amp;useSSL=false&amp;serverTimezone=UTC', 'root', 'dhccpass', 'MySql', '0', '1', null, '2019-04-19 09:59:08', '', 'admin');
INSERT INTO data_source (id, name, model, type, version, driver, addr, usr, password, icon, readonly, sts, tx_time, up_time, tx_op, up_op) VALUES (123, 'cms_oracle', 'oracle', 'oracle', null, 'oracle.jdbc.driver.OracleDriver', 'jdbc:oracle:thin:@128.1.13.22:1521:orcl', 'cmsii', 'cmsii', '', '0', '1', null, '2019-04-19 10:22:30', null, 'admin');
INSERT INTO data_source (id, name, model, type, version, driver, addr, usr, password, icon, readonly, sts, tx_time, up_time, tx_op, up_op) VALUES (132, 'Json', 'file', 'Json', '', '', 'https://api.github.com/search/', '', '', 'Json', '1', '1', null, '2019-04-19 10:22:35', '', 'admin');
DROP TABLE db_type;
CREATE TABLE db_type (id BIGINT NOT NULL, name CHARACTER VARYING(80), model CHARACTER VARYING(20), type CHARACTER VARYING(20), driver CHARACTER VARYING(100), addr_demo CHARACTER VARYING(120), sts CHARACTER VARYING(1), icon CHARACTER VARYING(80), CONSTRAINT pk_db_type PRIMARY KEY (id));
INSERT INTO db_type (id, name, model, type, driver, addr_demo, sts, icon) VALUES (101, 'MySql', 'rsdb', 'mysql', 'com.mysql.jdbc.Driver', 'jdbc:mysql://<host>:<port>/<database_name>', '1', 'MySql');
INSERT INTO db_type (id, name, model, type, driver, addr_demo, sts, icon) VALUES (201, 'Oracle', 'rsdb', 'oracle', 'oracle.jdbc.driver.OracleDriver', 'jdbc:oracle:thin:@<host>:<port>:<ServiceName>', '1', 'Oracle');
INSERT INTO db_type (id, name, model, type, driver, addr_demo, sts, icon) VALUES (301, 'Db2 type2', 'rsdb', 'db2', 'com.ibm.db2.jcc.DB2Driver', 'jdbc:db2:<database_name>', '1', 'Db2 type2');
INSERT INTO db_type (id, name, model, type, driver, addr_demo, sts, icon) VALUES (302, 'Db2 type4', 'rsdb', 'db2', 'com.ibm.db2.jcc.DB2Driver', 'jdbc:db2://<host>[:<port>]/<database_name>', '1', 'Db2 type4');
INSERT INTO db_type (id, name, model, type, driver, addr_demo, sts, icon) VALUES (401, 'SQL Server 2000', 'rsdb', 'sqlserver', 'com.microsoft.jdbc.sqlserver.SQLServerDriver', 'jdbc:microsoft:sqlserver://:', '0', 'SqlServer');
INSERT INTO db_type (id, name, model, type, driver, addr_demo, sts, icon) VALUES (402, 'SQL Server 2005', 'rsdb', 'sqlserver', 'com.microsoft.sqlserver.jdbc.SQLServerDriver', 'jdbc:sqlserver://<server_name>:<port>;DatabaseName=<DatabaseName>', '1', 'SqlServer');
INSERT INTO db_type (id, name, model, type, driver, addr_demo, sts, icon) VALUES (501, 'Sybase', 'rsdb', 'sysbase', 'com.sybase.jdbc3.jdbc.SybDriver', 'jdbc:sybase:Tds::', '0', 'Sybase');
INSERT INTO db_type (id, name, model, type, driver, addr_demo, sts, icon) VALUES (601, 'PostgreSQL', 'rsdb', 'postgresql', 'org.postgresql.Driver', 'jdbc:postgresql://:/', '1', 'PostgreSQL');
INSERT INTO db_type (id, name, model, type, driver, addr_demo, sts, icon) VALUES (701, 'SQLite', 'rsdb', 'sqlite', 'org.sqlite.JDBC', 'jdbc:sqlite:D:\\xxxdatabase.db', '0', 'SQLite');
INSERT INTO db_type (id, name, model, type, driver, addr_demo, sts, icon) VALUES (801, 'MongoDB', 'nosqldb', 'mongodb', '', 'mongodb://userName:password@host/?authSource=databaseName&amp;ssh=false', '0', 'MongoDB');
INSERT INTO db_type (id, name, model, type, driver, addr_demo, sts, icon) VALUES (901, 'Xml', 'file', 'xml', '', '', '0', 'Xml');
INSERT INTO db_type (id, name, model, type, driver, addr_demo, sts, icon) VALUES (902, 'API请求', 'file', 'json', '', '', '1', 'Json');
INSERT INTO db_type (id, name, model, type, driver, addr_demo, sts, icon) VALUES (903, 'Csv', 'file', 'csv', '', '', '0', 'Csv');
INSERT INTO db_type (id, name, model, type, driver, addr_demo, sts, icon) VALUES (904, 'Excel', 'file', 'excel', '', '', '0', 'Excel');
INSERT INTO db_type (id, name, model, type, driver, addr_demo, sts, icon) VALUES (1001, 'JNDI', 'jndi', 'jndi', '', '', '1', 'Jndi');
DROP TABLE ds_showcol;
CREATE TABLE ds_showcol (show_id BIGINT NOT NULL, dt_id BIGINT, use_id BIGINT, is_show CHARACTER VARYING(1), data_type CHARACTER VARYING(10), alias CHARACTER VARYING(80), describe CHARACTER VARYING(200), tx_time TIMESTAMP(6) WITHOUT TIME ZONE, tx_op CHARACTER VARYING(255), CONSTRAINT pk_ds_showcol PRIMARY KEY (show_id));
DROP TABLE dt_filter;
CREATE TABLE dt_filter (filter_id BIGINT NOT NULL, dt_id BIGINT, table_name CHARACTER VARYING(80), "column" CHARACTER VARYING(30), col_type CHARACTER VARYING(1), operator CHARACTER VARYING(10), param_type CHARACTER VARYING(1), param CHARACTER VARYING(30), tx_time TIMESTAMP(6) WITHOUT TIME ZONE, up_time TIMESTAMP(6) WITHOUT TIME ZONE, tx_op CHARACTER VARYING(30), up_op CHARACTER VARYING(30), CONSTRAINT pk_dt_filter PRIMARY KEY (filter_id));
DROP TABLE dt_param;
CREATE TABLE dt_param (param_id BIGINT NOT NULL, param_name CHARACTER VARYING(80), dt_id BIGINT, param_type CHARACTER VARYING(255), data_type CHARACTER VARYING(255), if_values CHARACTER VARYING(1), tx_time TIMESTAMP(6) WITHOUT TIME ZONE, up_time TIMESTAMP(6) WITHOUT TIME ZONE, tx_op CHARACTER VARYING(30), up_op CHARACTER VARYING(30), CONSTRAINT pk_dt_param PRIMARY KEY (param_id));
DROP TABLE dt_table;
CREATE TABLE dt_table (use_id BIGINT NOT NULL, dt_id BIGINT NOT NULL, table_name CHARACTER VARYING(80), table_comments CHARACTER VARYING(120), level CHARACTER VARYING(1), show_name CHARACTER VARYING(80), px BIGINT, py BIGINT, tx_time TIMESTAMP(6) WITHOUT TIME ZONE, up_time TIMESTAMP(6) WITHOUT TIME ZONE, tx_op CHARACTER VARYING(30), up_op CHARACTER VARYING(30), CONSTRAINT pk_dt_table PRIMARY KEY (use_id, dt_id));
DROP TABLE dt_table_rs;
CREATE TABLE dt_table_rs (rs_id BIGINT NOT NULL, use_id BIGINT NOT NULL, rs_type CHARACTER VARYING(10), rs_model CHARACTER VARYING(1), tx_time TIMESTAMP(6) WITHOUT TIME ZONE, up_time TIMESTAMP(6) WITHOUT TIME ZONE, tx_op CHARACTER VARYING(30), up_op CHARACTER VARYING(30), CONSTRAINT pk_dt_table_rs PRIMARY KEY (rs_id, use_id));
DROP TABLE dt_table_rs_condition;
CREATE TABLE dt_table_rs_condition (cd_id BIGINT NOT NULL, cd_type CHARACTER VARYING(10), "rename" CHARACTER VARYING(255), left_table CHARACTER VARYING(255), left_col CHARACTER VARYING(255), operator CHARACTER VARYING(255), right_table CHARACTER VARYING(255), right_col CHARACTER VARYING(255), tx_time TIMESTAMP(6) WITHOUT TIME ZONE, tx_op CHARACTER VARYING(255), CONSTRAINT pk_dt_table_rs_condition PRIMARY KEY (cd_id));


CREATE SEQUENCE data_set_seq
    START WITH 100
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
    
alter table data_set alter column dt_id set default nextval('data_set_seq');

CREATE SEQUENCE data_source_seq
    START WITH 200
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
    
alter table data_source alter column id set default nextval('data_source_seq');

commit;
