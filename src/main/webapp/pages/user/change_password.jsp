<%@taglib prefix="t" uri="http://java.sun.com/jsp/jstl/core" %>
<t:choose>
    <t:when test="${not empty ok}">
        <div class="alert alert-success">
            ${ok}
        </div>
    </t:when>
    <t:otherwise>
        <t:if test="${not empty error}">
        <div class="alert alert-danger">
            ${error}
        </div>
        </t:if>
        <form action="UserController" method="POST" class="form-inline">
            <input type="hidden" value="editPassword" name="action" />
            <div class="form-group">
                <label for="password1">Password</label><input class="form-control" id="password1" type="password" name="password1" />
            </div>
            <div class="form-group">
                <label for="password2">Password 2</label><input class="form-control" id="password2" type="password" name="password2" />
            </div>
            <button type="submit" class="btn btn-default">Submit</button>
        </form>
    </t:otherwise>
</t:choose>