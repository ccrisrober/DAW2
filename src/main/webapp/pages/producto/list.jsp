<%@taglib prefix="t" uri="http://java.sun.com/jsp/jstl/core" %>
        <h1>List</h1>
        <t:forEach items="${products}" var="item">
            ${item.getId()}
            <hr/>
        </t:forEach>