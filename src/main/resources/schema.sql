

DROP TABLE IF EXISTS villagemaster;

CREATE TABLE villagemaster(
	statecode INT,
	statename varchar(500) NOT NULL,
	districtcode INT,
	districtname varchar(100) NOT NULL,
	villagecode INT,
	villagename varchar(500) NOT NULL,
	subdistrictcode INT,
	subdistrictname varchar(500) NOT NULL,
	ns INT,
	ps INT,
	ks INT,
	ph INT,
	oc INT,
	bs INT,
	cus INT,
	fes INT,
	mns INT,
	ss INT,
	zns INT,
	id INT AUTO_INCREMENT NOT NULL,
	CONSTRAINT villagemaster_pk PRIMARY KEY (id)
);
CREATE UNIQUE INDEX villagemaster_id_idx ON villagemaster (id);

DROP TABLE IF EXISTS fertilizerslist;

CREATE TABLE fertilizerslist(
	id INT,
	fertilizersources varchar(500) NOT NULL,
	n varchar(500)  NOT NULL,
	p varchar(500)  NOT NULL,
	k varchar(500)  NOT NULL,
	pricebag INT,
	pricecart INT,
	pricetractor INT,
	type varchar(500)  NOT NULL
);
