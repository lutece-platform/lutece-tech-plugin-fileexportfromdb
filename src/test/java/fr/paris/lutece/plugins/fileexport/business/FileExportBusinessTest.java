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

import fr.paris.lutece.test.LuteceTestCase;
import fr.paris.lutece.plugins.fileexport.business.FileExport;
import fr.paris.lutece.plugins.fileexport.business.FileExportHome;
import fr.paris.lutece.portal.service.plugin.PluginService;
import fr.paris.lutece.portal.service.plugin.Plugin;

public class FileExportBusinessTest extends LuteceTestCase
{
    private final static int IDEXPORT1 = 1;
    private final static int IDEXPORT2 = 2;
    private final static String DESC1 = "Desc1";
    private final static String DESC2 = "Desc2";
    private final static int POOLNAME1 = 1;
    private final static int POOLNAME2 = 2;
    private final static String SQLQUERY1 = "SqlQuery1";
    private final static String SQLQUERY2 = "SqlQuery2";
    private final static String FILESQLFIELD1 = "FileSqlField1";
    private final static String FILESQLFIELD2 = "FileSqlField2";
    private final static String DIRECTORYFIELD1 = "DirectoryField1";
    private final static String DIRECTORYFIELD2 = "DirectoryField2";
    private final static String FILENAMEINDEX1 = "FilenameIndex1";
    private final static String FILENAMEINDEX2 = "FilenameIndex2";

    public void testBusiness(  )
    {
        Plugin plugin = PluginService.getPlugin( "fileexport" );
        
        // Initialize an object
        FileExport fileExport = new FileExport();
        fileExport.setIdExport( IDEXPORT1 );
        fileExport.setDesc( DESC1 );
        fileExport.setPoolName( POOLNAME1 );
        fileExport.setSqlQuery( SQLQUERY1 );
        fileExport.setFileSqlField( FILESQLFIELD1 );
        fileExport.setDirectoryField( DIRECTORYFIELD1 );
        fileExport.setFilenameIndex( FILENAMEINDEX1 );

        // Create test
        FileExportHome.create( fileExport , plugin );
        FileExport fileExportStored = FileExportHome.findByPrimaryKey( fileExport.getIdExport() , plugin );
        assertEquals( fileExportStored.getIdExport() , fileExport.getIdExport() );
        assertEquals( fileExportStored.getDesc() , fileExport.getDesc() );
        assertEquals( fileExportStored.getPoolName() , fileExport.getPoolName() );
        assertEquals( fileExportStored.getSqlQuery() , fileExport.getSqlQuery() );
        assertEquals( fileExportStored.getFileSqlField() , fileExport.getFileSqlField() );
        assertEquals( fileExportStored.getDirectoryField() , fileExport.getDirectoryField() );
        assertEquals( fileExportStored.getFilenameIndex() , fileExport.getFilenameIndex() );

        // Update test
        fileExport.setIdExport( IDEXPORT2 );
        fileExport.setDesc( DESC2 );
        fileExport.setPoolName( POOLNAME2 );
        fileExport.setSqlQuery( SQLQUERY2 );
        fileExport.setFileSqlField( FILESQLFIELD2 );
        fileExport.setDirectoryField( DIRECTORYFIELD2 );
        fileExport.setFilenameIndex( FILENAMEINDEX2 );
        FileExportHome.update( fileExport , plugin );
        fileExportStored = FileExportHome.findByPrimaryKey( fileExport.getIdExport() , plugin );
        assertEquals( fileExportStored.getIdExport() , fileExport.getIdExport() );
        assertEquals( fileExportStored.getDesc() , fileExport.getDesc() );
        assertEquals( fileExportStored.getPoolName() , fileExport.getPoolName() );
        assertEquals( fileExportStored.getSqlQuery() , fileExport.getSqlQuery() );
        assertEquals( fileExportStored.getFileSqlField() , fileExport.getFileSqlField() );
        assertEquals( fileExportStored.getDirectoryField() , fileExport.getDirectoryField() );
        assertEquals( fileExportStored.getFilenameIndex() , fileExport.getFilenameIndex() );

        // List test
        FileExportHome.getFileExportsList( plugin );

        // Delete test
        FileExportHome.remove( fileExport.getIdExport() , plugin );
        fileExportStored = FileExportHome.findByPrimaryKey( fileExport.getIdExport() , plugin );
        assertNull( fileExportStored );
        
    }

}