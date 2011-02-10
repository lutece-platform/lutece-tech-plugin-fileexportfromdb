

<%@ page errorPage="../../ErrorPage.jsp" %>

<jsp:useBean id="fileexport" scope="session" class="fr.paris.lutece.plugins.fileexport.web.FileexportJspBean" />

<%
    fileexport.init( request, fileexport.RIGHT_MANAGE_FILEEXPORT );
    response.sendRedirect( fileexport.doCreateFileExport( request ) );
%>

