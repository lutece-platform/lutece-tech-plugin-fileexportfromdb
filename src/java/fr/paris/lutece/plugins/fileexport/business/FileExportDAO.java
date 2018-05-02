/*
 * Copyright (c) 2002-2017, Mairie de Paris
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *  1. Redistributions of source code must retain the above copyright notice
 *     and the following disclaimer.
 *
 *  2. Redistributions in binary form must reproduce the above copyright notice
 *     and the following disclaimer in the documentation and/or other materials
 *     provided with the distribution.
 *
 *  3. Neither the name of 'Mairie de Paris' nor 'Lutece' nor the names of its
 *     contributors may be used to endorse or promote products derived from
 *     this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDERS OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 *
 * License 1.0
 */
package fr.paris.lutece.plugins.fileexport.business;

import fr.paris.lutece.plugins.fileexport.service.FileExportConnectionService;
import fr.paris.lutece.portal.service.plugin.Plugin;
import fr.paris.lutece.portal.service.util.AppException;
import fr.paris.lutece.util.sql.DAOUtil;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * This class provides Data Access methods for FileExport objects
 */

public final class FileExportDAO implements IFileExportDAO {

	// Constants
	public static final String DESTINATION_DIRECTORY = "destdir";
	public static final String SQL_QUERY = "sqlquery";
	public static final String BLOB_FIELD = "blobfield";
	public static final String DIRECTORY_FIELD = "directoryfield";
	public static final String PHOTO_NAME_INDEXES = "photonameindex";

	private static final String SQL_QUERY_NEW_PK = "SELECT max( id_export ) FROM file_export";
	private static final String SQL_QUERY_SELECT = "SELECT id_export, description, pool_name, sql_query, file_sql_field, directory_field, filename_index FROM file_export WHERE id_export = ?";
	private static final String SQL_QUERY_INSERT = "INSERT INTO file_export ( id_export, description, pool_name, sql_query, file_sql_field, directory_field, filename_index ) VALUES ( ?, ?, ?, ?, ?, ?, ? ) ";
	private static final String SQL_QUERY_DELETE = "DELETE FROM file_export WHERE id_export = ? ";
	private static final String SQL_QUERY_UPDATE = "UPDATE file_export SET id_export = ?, description = ?, pool_name = ?, sql_query = ?, file_sql_field = ?, directory_field = ?, filename_index = ? WHERE id_export = ?";
	private static final String SQL_QUERY_SELECTALL = "SELECT id_export, description, pool_name, sql_query, file_sql_field, directory_field, filename_index FROM file_export";

	/**
	 * Generates a new primary key
	 * 
	 * @param plugin
	 *            The Plugin
	 * @return The new primary key
	 */

	public int newPrimaryKey(Plugin plugin) {
		DAOUtil daoUtil = new DAOUtil(SQL_QUERY_NEW_PK, plugin);
		daoUtil.executeQuery();

		int nKey;

		if (!daoUtil.next()) {
			// if the table is empty
			nKey = 1;
		}

		nKey = daoUtil.getInt(1) + 1;
		daoUtil.free();

		return nKey;
	}

	/**
	 * Insert a new record in the table.
	 * 
	 * @param fileExport
	 *            instance of the FileExport object to insert
	 * @param plugin
	 *            The plugin
	 */

	public void insert(FileExport fileExport, Plugin plugin) {
		DAOUtil daoUtil = new DAOUtil(SQL_QUERY_INSERT, plugin);

		fileExport.setIdExport(newPrimaryKey(plugin));

		daoUtil.setInt(1, fileExport.getIdExport());
		daoUtil.setString(2, fileExport.getDesc());
		daoUtil.setString(3, fileExport.getPoolName());
		daoUtil.setString(4, fileExport.getSqlQuery());
		daoUtil.setString(5, fileExport.getFileSqlField());
		daoUtil.setString(6, fileExport.getDirectoryField());
		daoUtil.setString(7, fileExport.getFilenameIndex());

		daoUtil.executeUpdate();
		daoUtil.free();
	}

	/**
	 * Load the data of the fileExport from the table
	 * 
	 * @param nId
	 *            The identifier of the fileExport
	 * @param plugin
	 *            The plugin
	 * @return the instance of the FileExport
	 */

	public FileExport load(int nId, Plugin plugin) {
		DAOUtil daoUtil = new DAOUtil(SQL_QUERY_SELECT, plugin);
		daoUtil.setInt(1, nId);
		daoUtil.executeQuery();

		FileExport fileExport = null;

		if (daoUtil.next()) {
			fileExport = new FileExport();

			fileExport.setIdExport(daoUtil.getInt(1));
			fileExport.setDesc(daoUtil.getString(2));
			fileExport.setPoolName(daoUtil.getString(3));
			fileExport.setSqlQuery(daoUtil.getString(4));
			fileExport.setFileSqlField(daoUtil.getString(5));
			fileExport.setDirectoryField(daoUtil.getString(6));
			fileExport.setFilenameIndex(daoUtil.getString(7));
		}

		daoUtil.free();
		return fileExport;
	}

	/**
	 * Delete a record from the table
	 * 
	 * @param nFileExportId
	 *            The identifier of the fileExport
	 * @param plugin
	 *            The plugin
	 */

	public void delete(int nFileExportId, Plugin plugin) {
		DAOUtil daoUtil = new DAOUtil(SQL_QUERY_DELETE, plugin);
		daoUtil.setInt(1, nFileExportId);
		daoUtil.executeUpdate();
		daoUtil.free();
	}

	/**
	 * Update the record in the table
	 * 
	 * @param fileExport
	 *            The reference of the fileExport
	 * @param plugin
	 *            The plugin
	 */

	public void store(FileExport fileExport, Plugin plugin) {
		DAOUtil daoUtil = new DAOUtil(SQL_QUERY_UPDATE, plugin);

		daoUtil.setInt(1, fileExport.getIdExport());
		daoUtil.setString(2, fileExport.getDesc());
		daoUtil.setString(3, fileExport.getPoolName());
		daoUtil.setString(4, fileExport.getSqlQuery());
		daoUtil.setString(5, fileExport.getFileSqlField());
		daoUtil.setString(6, fileExport.getDirectoryField());
		daoUtil.setString(7, fileExport.getFilenameIndex());
		daoUtil.setInt(8, fileExport.getIdExport());

		daoUtil.executeUpdate();
		daoUtil.free();
	}

	/**
	 * Load the data of all the fileExports and returns them as a collection
	 * 
	 * @param plugin
	 *            The plugin
	 * @return The Collection which contains the data of all the fileExports
	 */

	public Collection<FileExport> selectFileExportsList(Plugin plugin) {
		Collection<FileExport> fileExportList = new ArrayList<FileExport>();
		DAOUtil daoUtil = new DAOUtil(SQL_QUERY_SELECTALL, plugin);
		daoUtil.executeQuery();

		while (daoUtil.next()) {
			FileExport fileExport = new FileExport();

			fileExport.setIdExport(daoUtil.getInt(1));
			fileExport.setDesc(daoUtil.getString(2));
			fileExport.setPoolName(daoUtil.getString(3));
			fileExport.setSqlQuery(daoUtil.getString(4));
			fileExport.setFileSqlField(daoUtil.getString(5));
			fileExport.setDirectoryField(daoUtil.getString(6));
			fileExport.setFilenameIndex(daoUtil.getString(7));

			fileExportList.add(fileExport);
		}

		daoUtil.free();
		return fileExportList;
	}

	public static File createTempDir() {
		final String baseTempPath = System.getProperty("java.io.tmpdir");

		Random rand = new Random();
		int randomInt = 1 + rand.nextInt();

		File tempDir = new File(baseTempPath + File.separator + "tempDir"
				+ randomInt);
		if (tempDir.exists() == false) {
			tempDir.mkdir();
		}

		// tempDir.deleteOnExit();

		return tempDir;
	}

	public static byte[] exportZip(String strRequest,String strPoolName,String strDirectoryName,String strBlobField,String strPhotoIndexes
			) throws SQLException, IOException {
		
		File tempDir = createTempDir();
		Connection connection = null;
		Statement statement = null;
		String strSQL = strRequest;
		

		try {
			connection = FileExportConnectionService.getConnectionService( strPoolName ).getConnection();
			statement = connection
					.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
							ResultSet.CONCUR_READ_ONLY);

			ResultSet resultSet = statement.executeQuery(strSQL);
            while (resultSet.next()) {
            	
                // Create directory
                boolean success = (new File(tempDir.getAbsolutePath() + "\\" +resultSet.getString(strDirectoryName)).mkdir());
                if (success) {
                    System.out.println("Directory: " + resultSet.getString(strDirectoryName) + " created");
                }
                InputStream readImg = resultSet.getBinaryStream(strBlobField);

                String[] array = getIndexesImageName(strPhotoIndexes);
                String strPhotoName = "";
                int size = array.length;
                for (int i = 0; i < size; i++) {
                    strPhotoName = strPhotoName + resultSet.getString(Integer.parseInt(array[i])).trim().replace(':', '-');
                }

                File f = new File(tempDir.getAbsolutePath() + resultSet.getString(strDirectoryName) + "\\"
                        + strPhotoName );

                ByteArrayOutputStream out = new ByteArrayOutputStream();
                final int BUF_SIZE = 1 << 8; 
                byte[] buffer = new byte[BUF_SIZE];
                int bytesRead = -1;
                while ((bytesRead = readImg.read(buffer)) > -1) {
                    out.write(buffer, 0, bytesRead);
                }
                readImg.close();
                byte[] imageBytes = out.toByteArray();

                FileOutputStream fos = new FileOutputStream(tempDir.getAbsolutePath() +"\\"+ resultSet.getString(strDirectoryName) + "\\"
                        + strPhotoName );
                fos.write(imageBytes);
                fos.flush();
                fos.close();
                fos=null;
                
                

            }
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				if (statement != null) {
					statement.close();
				}
			} catch (SQLException e) {
				throw new AppException("SQL Error executing command : "
						+ e.toString());
			}

			
		}
		return getZip(tempDir);
	}

	private static String[] getIndexesImageName(String strPhotoIndexes) {
		String[] array = null;
		if (strPhotoIndexes != null && !strPhotoIndexes.equalsIgnoreCase("")) {
			array = strPhotoIndexes.split(",");
		}
		return array;
	}

	private static byte[] getZip(File fDirectory) throws IOException {
		ByteArrayOutputStream fos = new ByteArrayOutputStream();

		ZipOutputStream zipOutputStream = new ZipOutputStream(fos);
		zipOutputStream.setLevel(9);

		zipDir(fDirectory, zipOutputStream);
		zipOutputStream.close();

		return fos.toByteArray();
	}

	public static void zipDir(File fTempDirectory, ZipOutputStream zos) {
		try {
			String[] dirList = fTempDirectory.list();
			byte[] readBuffer = new byte[2156];
			int bytesIn = 0;
			for (int i = 0; i < dirList.length; i++) {
				File f = new File(fTempDirectory, dirList[i]);
				if (f.isDirectory()) {
					zipDir(f, zos);
					continue;
				}
				FileInputStream fis = new FileInputStream(f);
				ZipEntry anEntry = new ZipEntry(f.getAbsolutePath().substring(f.getAbsolutePath().indexOf(fTempDirectory.getName()), f.getAbsolutePath().length()));//
				zos.putNextEntry(anEntry);
				while ((bytesIn = fis.read(readBuffer)) != -1) {
					zos.write(readBuffer, 0, bytesIn);
				}
				fis.close();
			}
		} catch (Exception e) {
		}
	}

}
