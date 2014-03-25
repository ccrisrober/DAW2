<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="t" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<t:if test="${empty requestScope.templatepage}">
    <% //response.sendRedirect("../error/401.jsp");%>
</t:if>
<t:if test="${templatepage.isCheckLogin()}">
    <t:if test="${sessionScope['user_active']==null}">
       <% //response.sendRedirect("Login");%>
    </t:if>
</t:if>
<jsp:include page="header.jsp" />
<jsp:include page="../pages/${templatepage.getFile()}" />
<jsp:include page="footer.jsp" />