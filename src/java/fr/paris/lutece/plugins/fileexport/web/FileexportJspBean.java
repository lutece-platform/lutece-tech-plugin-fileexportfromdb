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
 
package fr.paris.lutece.plugins.fileexport.web;


import fr.paris.lutece.plugins.fileexport.business.FileExport;
import fr.paris.lutece.plugins.fileexport.business.FileExportHome;
import fr.paris.lutece.portal.service.message.AdminMessage;
import fr.paris.lutece.portal.service.message.AdminMessageService;
import fr.paris.lutece.portal.service.template.AppTemplateService;
import fr.paris.lutece.portal.service.util.AppPropertiesService;
import fr.paris.lutece.portal.web.admin.PluginAdminPageJspBean;
import fr.paris.lutece.portal.web.constants.Messages;
import fr.paris.lutece.util.html.HtmlTemplate;
import fr.paris.lutece.util.html.Paginator;

import fr.paris.lutece.util.url.UrlItem;

import java.util.Collection;
import java.util.HashMap;

import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;


/**
 * This class provides the user interface to manage  FileExport  
 features ( manage, create, modify, remove )
 */
public class FileexportJspBean extends PluginAdminPageJspBean
{

    ////////////////////////////////////////////////////////////////////////////
    // Constants

    // Right
    public static final String RIGHT_MANAGE_FILEEXPORT = "FILEEXPORT_MANAGEMENT";
    
    // Parameters
    private static final String PARAMETER_FILEEXPORT_ID_EXPORT="fileexport_id_export";
    private static final String PARAMETER_FILEEXPORT_DESC="fileexport_desc";
    private static final String PARAMETER_FILEEXPORT_POOL_NAME="fileexport_pool_name";
    private static final String PARAMETER_FILEEXPORT_SQL_QUERY="fileexport_sql_query";
    private static final String PARAMETER_FILEEXPORT_FILE_SQL_FIELD="fileexport_file_sql_field";
    private static final String PARAMETER_FILEEXPORT_DIRECTORY_FIELD="fileexport_directory_field";
    private static final String PARAMETER_FILEEXPORT_FILENAME_INDEX="fileexport_filename_index";
    private static final String PARAMETER_PAGE_INDEX = "page_index";   

    // templates
    private static final String TEMPLATE_MANAGE_FILEEXPORTS="/admin/plugins/fileexport/manage_fileexport.html";
    private static final String TEMPLATE_CREATE_FILEEXPORT="/admin/plugins/fileexport/create_fileexport.html";
    private static final String TEMPLATE_MODIFY_FILEEXPORT="/admin/plugins/fileexport/modify_fileexport.html";
   


    // Properties for page titles
    private static final String PROPERTY_PAGE_TITLE_MANAGE_FILEEXPORTS = "fileexport.manage_fileexports.pageTitle";
    private static final String PROPERTY_PAGE_TITLE_MODIFY_FILEEXPORT = "fileexport.modify_fileexport.pageTitle";
    private static final String PROPERTY_PAGE_TITLE_CREATE_FILEEXPORT = "fileexport.create_fileexport.pageTitle";

    // Markers
    private static final String MARK_FILEEXPORT_LIST = "fileexport_list";
    private static final String MARK_FILEEXPORT = "fileexport";
    private static final String MARK_PAGINATOR = "paginator";
    private static final String MARK_NB_ITEMS_PER_PAGE = "nb_items_per_page";    
    
    
    // Jsp Definition
    private static final String JSP_DO_REMOVE_FILEEXPORT = "jsp/admin/plugins/fileexport/DoRemoveFileExport.jsp";
    private static final String JSP_MANAGE_FILEEXPORTS = "jsp/admin/plugins/fileexport/ManageFileExports.jsp";
    private static final String JSP_REDIRECT_TO_MANAGE_FILEEXPORTS = "ManageFileExports.jsp";

    // Properties
    private static final String PROPERTY_DEFAULT_LIST_FILEEXPORT_PER_PAGE = "fileexport.listFileExports.itemsPerPage";

    // Messages
    private static final String MESSAGE_CONFIRM_REMOVE_FILEEXPORT = "fileexport.message.confirmRemoveFileExport";
  
    //Variables
    private int _nDefaultItemsPerPage;
    private String _strCurrentPageIndex;
    private int _nItemsPerPage;    

    /**
     * Returns the list of fileexport
     *
     * @param request The Http request
     * @return the fileexports list
     */
    public String getManageFileExports( HttpServletRequest request )
    {
        setPageTitleProperty( PROPERTY_PAGE_TITLE_MANAGE_FILEEXPORTS );

        _strCurrentPageIndex = Paginator.getPageIndex( request, Paginator.PARAMETER_PAGE_INDEX , _strCurrentPageIndex );
        _nDefaultItemsPerPage = AppPropertiesService.getPropertyInt( PROPERTY_DEFAULT_LIST_FILEEXPORT_PER_PAGE, 50 );
        _nItemsPerPage = Paginator.getItemsPerPage( request, Paginator.PARAMETER_ITEMS_PER_PAGE, _nItemsPerPage,
                _nDefaultItemsPerPage );
        UrlItem url = new UrlItem(  JSP_MANAGE_FILEEXPORTS);
        String strUrl = url.getUrl(  );
        Collection<FileExport> listFileExports = FileExportHome.getFileExportsList( getPlugin(  ) );
        Paginator paginator = new Paginator( (List<FileExport>) listFileExports, _nItemsPerPage, strUrl , PARAMETER_PAGE_INDEX, _strCurrentPageIndex );

        Map<String, Object> model = new HashMap<String, Object>(  );

        model.put( MARK_NB_ITEMS_PER_PAGE, "" + _nItemsPerPage );
        model.put( MARK_PAGINATOR, paginator );
        model.put( MARK_FILEEXPORT_LIST, paginator.getPageItems(  ));
        
        HtmlTemplate templateList = AppTemplateService.getTemplate( TEMPLATE_MANAGE_FILEEXPORTS, getLocale(  ), model );

        return getAdminPage( templateList.getHtml(  ) );
    }

    /**
     * Returns the form to create a fileexport
     *
     * @param request The Http request
     * @return the html code of the fileexport form
     */
    public String getCreateFileExport( HttpServletRequest request )
    {
        setPageTitleProperty( PROPERTY_PAGE_TITLE_CREATE_FILEEXPORT  );

        Map<String, Object> model = new HashMap<String, Object>(  );

        HtmlTemplate template = AppTemplateService.getTemplate( TEMPLATE_CREATE_FILEEXPORT, getLocale(  ), model );

        return getAdminPage( template.getHtml(  ) );
    }

     /**
     * Process the data capture form of a new fileexport
     *
     * @param request The Http Request
     * @return The Jsp URL of the process result
     */
    public String doCreateFileExport( HttpServletRequest request )
    {
        FileExport fileexport = new FileExport(  );
             
                   	 
        
        if ( request.getParameter( PARAMETER_FILEEXPORT_DESC ).equals( "" ) )
        {
            return AdminMessageService.getMessageUrl( request, Messages.MANDATORY_FIELDS, AdminMessage.TYPE_STOP );
        }
			fileexport.setDesc( request.getParameter( PARAMETER_FILEEXPORT_DESC ) );
			
        
        if ( request.getParameter( PARAMETER_FILEEXPORT_POOL_NAME ).equals( "" ) )
        {
            return AdminMessageService.getMessageUrl( request, Messages.MANDATORY_FIELDS, AdminMessage.TYPE_STOP );
        }
			      String strPoolName = request.getParameter( PARAMETER_FILEEXPORT_POOL_NAME ) ;
			      fileexport.setPoolName( strPoolName );
			
        
        if ( request.getParameter( PARAMETER_FILEEXPORT_SQL_QUERY ).equals( "" ) )
        {
            return AdminMessageService.getMessageUrl( request, Messages.MANDATORY_FIELDS, AdminMessage.TYPE_STOP );
        }
			fileexport.setSqlQuery( request.getParameter( PARAMETER_FILEEXPORT_SQL_QUERY ) );
			
        
        if ( request.getParameter( PARAMETER_FILEEXPORT_FILE_SQL_FIELD ).equals( "" ) )
        {
            return AdminMessageService.getMessageUrl( request, Messages.MANDATORY_FIELDS, AdminMessage.TYPE_STOP );
        }
			fileexport.setFileSqlField( request.getParameter( PARAMETER_FILEEXPORT_FILE_SQL_FIELD ) );
			
        
        if ( request.getParameter( PARAMETER_FILEEXPORT_DIRECTORY_FIELD ).equals( "" ) )
        {
            return AdminMessageService.getMessageUrl( request, Messages.MANDATORY_FIELDS, AdminMessage.TYPE_STOP );
        }
			fileexport.setDirectoryField( request.getParameter( PARAMETER_FILEEXPORT_DIRECTORY_FIELD ) );
			
        
        if ( request.getParameter( PARAMETER_FILEEXPORT_FILENAME_INDEX ).equals( "" ) )
        {
            return AdminMessageService.getMessageUrl( request, Messages.MANDATORY_FIELDS, AdminMessage.TYPE_STOP );
        }
			fileexport.setFilenameIndex( request.getParameter( PARAMETER_FILEEXPORT_FILENAME_INDEX ) );
			
      FileExportHome.create( fileexport, getPlugin(  ) );
      return JSP_REDIRECT_TO_MANAGE_FILEEXPORTS;
    }
    
    /**
     * Manages the removal form of a fileexport whose identifier is in the http request
     *
     * @param request The Http request
     * @return the html code to confirm
     */
    public String getConfirmRemoveFileExport( HttpServletRequest request )
    {
        	int nId = Integer.parseInt( request.getParameter( PARAMETER_FILEEXPORT_ID_EXPORT ) );
          UrlItem url = new UrlItem( JSP_DO_REMOVE_FILEEXPORT );
          url.addParameter( PARAMETER_FILEEXPORT_ID_EXPORT, nId );
          return AdminMessageService.getMessageUrl( request, MESSAGE_CONFIRM_REMOVE_FILEEXPORT, url.getUrl(  ),AdminMessage.TYPE_CONFIRMATION );
    }
    /**
     * Handles the removal form of a fileexport
     *
     * @param request The Http request
     * @return the jsp URL to display the form to manage fileexports
     */
    public String doRemoveFileExport( HttpServletRequest request )
    {
        int nId = Integer.parseInt( request.getParameter( PARAMETER_FILEEXPORT_ID_EXPORT ) );
        FileExportHome.remove( nId, getPlugin(  ) );
        
        return JSP_REDIRECT_TO_MANAGE_FILEEXPORTS;
    }
    
    /**
     * Returns the form to update info about a fileexport
     *
     * @param request The Http request
     * @return The HTML form to update info
     */
    public String getModifyFileExport( HttpServletRequest request )
    {
        setPageTitleProperty( PROPERTY_PAGE_TITLE_MODIFY_FILEEXPORT );
        int nId = Integer.parseInt( request.getParameter( PARAMETER_FILEEXPORT_ID_EXPORT ) );
        FileExport fileexport =FileExportHome.findByPrimaryKey( nId, getPlugin(  ) );

        Map<String, Object> model = new HashMap<String, Object>(  );
        model.put( MARK_FILEEXPORT, fileexport );

        HtmlTemplate template = AppTemplateService.getTemplate( TEMPLATE_MODIFY_FILEEXPORT, getLocale(  ), model );

        return getAdminPage( template.getHtml(  ) );
    }

    /**
     * Process the change form of a fileexport
     *
     * @param request The Http request
     * @return The Jsp URL of the process result
     */
    public String doModifyFileExport( HttpServletRequest request )
    {
      int nId = Integer.parseInt( request.getParameter( PARAMETER_FILEEXPORT_ID_EXPORT ) ); 
      FileExport fileexport =FileExportHome.findByPrimaryKey( nId, getPlugin(  ) );
        if ( request.getParameter( PARAMETER_FILEEXPORT_ID_EXPORT ).equals( "" ) )
        {
          return AdminMessageService.getMessageUrl( request, Messages.MANDATORY_FIELDS, AdminMessage.TYPE_STOP );
        }
      int nIdExport = Integer.parseInt( request.getParameter( PARAMETER_FILEEXPORT_ID_EXPORT ) );
			fileexport.setIdExport( nIdExport );
        if ( request.getParameter( PARAMETER_FILEEXPORT_DESC ).equals( "" ) )
        {
          return AdminMessageService.getMessageUrl( request, Messages.MANDATORY_FIELDS, AdminMessage.TYPE_STOP );
        }
  			 fileexport.setDesc( request.getParameter( PARAMETER_FILEEXPORT_DESC ) );
        if ( request.getParameter( PARAMETER_FILEEXPORT_POOL_NAME ).equals( "" ) )
        {
          return AdminMessageService.getMessageUrl( request, Messages.MANDATORY_FIELDS, AdminMessage.TYPE_STOP );
        }
      String strPoolName = request.getParameter( PARAMETER_FILEEXPORT_POOL_NAME ) ;
			fileexport.setPoolName( strPoolName );
        if ( request.getParameter( PARAMETER_FILEEXPORT_SQL_QUERY ).equals( "" ) )
        {
          return AdminMessageService.getMessageUrl( request, Messages.MANDATORY_FIELDS, AdminMessage.TYPE_STOP );
        }
  			 fileexport.setSqlQuery( request.getParameter( PARAMETER_FILEEXPORT_SQL_QUERY ) );
        if ( request.getParameter( PARAMETER_FILEEXPORT_FILE_SQL_FIELD ).equals( "" ) )
        {
          return AdminMessageService.getMessageUrl( request, Messages.MANDATORY_FIELDS, AdminMessage.TYPE_STOP );
        }
  			 fileexport.setFileSqlField( request.getParameter( PARAMETER_FILEEXPORT_FILE_SQL_FIELD ) );
        if ( request.getParameter( PARAMETER_FILEEXPORT_DIRECTORY_FIELD ).equals( "" ) )
        {
          return AdminMessageService.getMessageUrl( request, Messages.MANDATORY_FIELDS, AdminMessage.TYPE_STOP );
        }
  			 fileexport.setDirectoryField( request.getParameter( PARAMETER_FILEEXPORT_DIRECTORY_FIELD ) );
        if ( request.getParameter( PARAMETER_FILEEXPORT_FILENAME_INDEX ).equals( "" ) )
        {
          return AdminMessageService.getMessageUrl( request, Messages.MANDATORY_FIELDS, AdminMessage.TYPE_STOP );
        }
  			 fileexport.setFilenameIndex( request.getParameter( PARAMETER_FILEEXPORT_FILENAME_INDEX ) );
        FileExportHome.update( fileexport, getPlugin(  ) );
        return JSP_REDIRECT_TO_MANAGE_FILEEXPORTS;
    }
}