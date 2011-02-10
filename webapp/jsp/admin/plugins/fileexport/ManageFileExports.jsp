<%@ page errorPage="../../ErrorPage.jsp" %>

<jsp:include page="../../AdminHeader.jsp" />

<jsp:useBean id="fileexport" scope="session" class="fr.paris.lutece.plugins.fileexport.web.FileexportJspBean" />

<% fileexport.init( request, fileexport.RIGHT_MANAGE_FILEEXPORT ); %>
<%= fileexport.getManageFileExports ( request ) %>

<%@ include file="../../AdminFooter.jsp" %>


