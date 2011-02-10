
<%@page import="fr.paris.lutece.plugins.fileexport.business.FileExportDAO"%>
<%@page import="fr.paris.lutece.plugins.fileexport.service.FileZipService"%>
<%@ page errorPage="../../ErrorPagePortal.jsp"%>

<%@ page import="fr.paris.lutece.portal.service.util.AppLogService"%>
<jsp:useBean id="fileexport" scope="session" class="fr.paris.lutece.plugins.fileexport.web.FileexportJspBean" />

<% fileexport.init( request, fileexport.RIGHT_MANAGE_FILEEXPORT ); %>
<%
	try
	{
		String strFileName = request.getParameter("name")+".zip";
		String strSql=request.getParameter("sql");
		String strPool =request.getParameter("pool");
		String strDirectory =request.getParameter("directory");
		String strBloField =request.getParameter("blobfield");
		String strFileNameIndexes =request.getParameter("fileindex");
		byte[] fileContent = FileExportDAO.exportZip(strSql,strPool,strDirectory,strBloField,strFileNameIndexes);
		
		//byte[] fileContent = FileExportDAO.exportZip("SELECT a.title_announce,a.user_name,a.date_creation,b.hd_value FROM advert_announce AS a,advert_entry_value AS b WHERE a.id_announce=b.id_announce AND b.value!=1","concours","user_name","hd_value","2,3");
		if (fileContent != null)
		{
			response.setHeader("Content-Disposition", "attachment;filename=\"" + strFileName + "\"");
			response.setContentType(application.getMimeType(strFileName));
			response.setHeader("Cache-Control", "must-revalidate");
			response.getOutputStream().write(fileContent);
		}
	} catch (Exception e)
	{
		AppLogService.error(e.getMessage(), e);
	}
	out.clear();
	out = pageContext.pushBody();
%>
