<%@taglib prefix="t" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="col-md-12 column">
    <h2>Login usuarios</h2>
<t:choose>
    <t:when test="${not empty ok}">
        <div class="alert alert-success">
            ${ok}
        </div>
        Pincha <a href="Index">aquí</a> para volver al inicio.
    </t:when>
    <t:otherwise>
        <t:if test="${not empty error}">
        <div class="alert alert-danger">
            ${error}
        </div>
        </t:if>
        <form id="loginform" name="loginform" method="post" action="Login">
            <div class="form-group">
                <label for="userfield">Username:</label><input class="form-control" id="userfield" type="text" name="userfield" />
            </div>
            <div class="form-group">
                <label for="passfield">Password:</label><input class="form-control" id="passfield" type="password" name="passfield" />
            </div>
            <button type="submit" class="btn btn-default">Submit</button>
        </form>
    </t:otherwise>
</t:choose>
        <br/>
</div>