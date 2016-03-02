package com.softserve.hotels.service;

import java.io.File;
import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.softserve.hotels.utils.FileUtils;
import com.softserve.hotels.utils.ZipUtils;

public class PgWindowsDumper implements PgDumper {
    
    private static final Logger LOG = LogManager.getLogger(PgWindowsDumper.class);
	
	private String url;
	private String userName;
	private String password;
	private String postgresBinPath;
	private String host;
	private String port;
	private String database;
	
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getPostgresBinPath() {
		return postgresBinPath;
	}

	public void setPostgresBinPath(String postgresBinPath) {
		this.postgresBinPath = postgresBinPath;
	}

	@Override
	public void dump() {
		String data = url.split("//")[1];
		host = data.split(":")[0];
		data = data.split(":")[1];
		port = data.split("/")[0];
		database = data.split("/")[1];
		String pgDump = postgresBinPath + "\\pg_dump"; 
		String backupPath = FileUtils.ROOT_PATH + File.separator 
				+ FileUtils.ROOT_DIR + File.separator + database + ".backup"; 
		String[] dump = { 
				pgDump, 
				"-d", database,
				"-h", host, 
				"-p", port, 
				"-U", userName, 
				"-f", backupPath
				};
		try {
			ProcessBuilder pb = new ProcessBuilder(dump);
			pb.environment().put("PGPASSWORD", password);
			pb.start();
		} catch (IOException e) {
			LOG.error("can't make dump of DB");
			LOG.error(e);
		}
	}
	
	private void dropDataBase(String database, String host, String port) { 
		String[] dropDB = {
				postgresBinPath + "\\dropdb",
				"-h", host, 
				"-p", port, 
				"-U", userName,
				database
				};
		try {
			ProcessBuilder pb = new ProcessBuilder(dropDB);
			pb.environment().put("PGPASSWORD", password);
			pb.start();
		} catch (Exception e) {
			LOG.error("can't drop DB");
			LOG.error(e);
		}
	}
	
	private void createDataBase(String database, String host, String port) {
		String[] createDB = {
				postgresBinPath + "\\createdb",
				"-h", host, 
				"-p", port, 
				"-U", userName,
				database
				};
		try {
			ProcessBuilder pb = new ProcessBuilder(createDB);
			pb.environment().put("PGPASSWORD", password);
			pb.start();
		} catch (Exception e) {
			LOG.error("can't create DB");
			LOG.error(e);
		}
	}

	@Override
	public void restore() {
		String data = url.split("//")[1];
		host = data.split(":")[0];
		data = data.split(":")[1];
		port = data.split("/")[0];
		database = data.split("/")[1];
		String backupPath = FileUtils.ROOT_PATH + File.separator 
				+ FileUtils.ROOT_DIR + File.separator + database + ".backup"; 
		
		dropDataBase(database, host, port);
		createDataBase(database, host, port);
		
		String[] restore = {
				postgresBinPath + "\\psql",
				"-d", database,
				"-h", host, 
				"-p", port, 
				"-U", userName,  
				"-f", backupPath
				};
		try {
			ProcessBuilder pb = new ProcessBuilder(restore);
			pb.environment().put("PGPASSWORD", password);
			pb.start();
		} catch (IOException e) {
			LOG.error("can't make restore of DB");
			LOG.error(e);
		}
	}

    @Override
    public void compress() {
        String folderToCompress = FileUtils.ROOT_PATH + File.separator + FileUtils.ROOT_DIR;
        String compressedFile = FileUtils.ROOT_PATH + File.separator + FileUtils.ROOT_DIR + ".zip";
        ZipUtils.compress(folderToCompress, compressedFile);
    }

    @Override
    public void decompress() {
        String folderToUncompress = FileUtils.ROOT_PATH + File.separator + FileUtils.ROOT_DIR;
        String compressedFile = FileUtils.ROOT_PATH + File.separator + FileUtils.ROOT_DIR + ".zip";
        ZipUtils.decompress(compressedFile, folderToUncompress);
    }

    @Override
    public void fullBackup() {
        dump();
        compress();
    }

    @Override
    public void fullRestore() {
        decompress();
        restore();
        File file = new File(FileUtils.ROOT_PATH + File.separator + FileUtils.ROOT_DIR 
                + File.separator + database + ".backup");
        file.delete();
    }

}
