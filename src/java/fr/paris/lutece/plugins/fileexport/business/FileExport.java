
/*
 * Copyright (c) 2002-2011, Mairie de Paris
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

/**
 * This is the business class for the object FileExport
 */ 
public class FileExport
{
	// Variables declarations 
	private int _nIdExport;
	private String _strDesc;
	private String _strPoolName;
	private String _strSqlQuery;
	private String _strFileSqlField;
	private String _strDirectoryField;
	private String _strFilenameIndex;
	/**
	 * Returns the IdExport
	 * @return The IdExport
	 */
	public int getIdExport()
	{
		return _nIdExport;
	}

	/**
	 * Sets the IdExport
	 * @param nIdExport The IdExport
	 */ 
	public void setIdExport( int nIdExport )
	{
		_nIdExport = nIdExport;
	}
	/**
	 * Returns the Desc
	 * @return The Desc
	 */
	public String getDesc()
	{
		return _strDesc;
	}

	/**
	 * Sets the Desc
	 * @param strDesc The Desc
	 */ 
	public void setDesc( String strDesc )
	{
		_strDesc = strDesc;
	}
	/**
	 * Returns the PoolName
	 * @return The PoolName
	 */
	public String getPoolName()
	{
		return _strPoolName;
	}

	/**
	 * Sets the PoolName
	 * @param strPoolName The PoolName
	 */ 
	public void setPoolName( String strPoolName )
	{
		_strPoolName = strPoolName;
	}
	/**
	 * Returns the SqlQuery
	 * @return The SqlQuery
	 */
	public String getSqlQuery()
	{
		return _strSqlQuery;
	}

	/**
	 * Sets the SqlQuery
	 * @param strSqlQuery The SqlQuery
	 */ 
	public void setSqlQuery( String strSqlQuery )
	{
		_strSqlQuery = strSqlQuery;
	}
	/**
	 * Returns the FileSqlField
	 * @return The FileSqlField
	 */
	public String getFileSqlField()
	{
		return _strFileSqlField;
	}

	/**
	 * Sets the FileSqlField
	 * @param strFileSqlField The FileSqlField
	 */ 
	public void setFileSqlField( String strFileSqlField )
	{
		_strFileSqlField = strFileSqlField;
	}
	/**
	 * Returns the DirectoryField
	 * @return The DirectoryField
	 */
	public String getDirectoryField()
	{
		return _strDirectoryField;
	}

	/**
	 * Sets the DirectoryField
	 * @param strDirectoryField The DirectoryField
	 */ 
	public void setDirectoryField( String strDirectoryField )
	{
		_strDirectoryField = strDirectoryField;
	}
	/**
	 * Returns the FilenameIndex
	 * @return The FilenameIndex
	 */
	public String getFilenameIndex()
	{
		return _strFilenameIndex;
	}

	/**
	 * Sets the FilenameIndex
	 * @param strFilenameIndex The FilenameIndex
	 */ 
	public void setFilenameIndex( String strFilenameIndex )
	{
		_strFilenameIndex = strFilenameIndex;
	}
}