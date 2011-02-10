
--
-- Structure for table file_export
--

DROP TABLE IF EXISTS file_export;
CREATE TABLE file_export (		
id_export int(11) NOT NULL default '0',
description varchar(50) NOT NULL default '',
pool_name int(11) NOT NULL default '0',
sql_query varchar(50) NOT NULL default '',
file_sql_field varchar(50) NOT NULL default '',
directory_field varchar(50) NOT NULL default '',
filename_index varchar(50) NOT NULL default ''
);
