<%@taglib prefix="t" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="col-md-12 column">
    <h2>Registro usuarios</h2>
<t:choose>
    <t:when test="${not empty ok}">
        <div class="alert alert-success">
            ${ok}
        </div>
        Pincha <a href="Login">aquí</a> para loguearse.
    </t:when>
    <t:otherwise>
        <t:if test="${not empty error}">
        <div class="alert alert-danger">
            ${error}
        </div>
        </t:if>
        <form action="Register" class="form-horizontal" method="POST">
            <div class="form-group">
                <label for="userfield">Username:</label><input class="form-control" id="userfield" type="text" name="userfield" value="${userfield}"/>
            </div>
            <div class="form-group">
                <label for="passfield">Password:</label><input class="form-control" id="passfield" type="password" name="passfield"  value="${passfield}"/>
            </div>
            <div class="form-group">
                <label for="passfield2">Password 2:</label><input class="form-control" id="passfield2" type="password" name="passfield2" />
            </div>
            <button type="submit" class="btn btn-default">Submit</button>
        </form>
    </t:otherwise>
</t:choose>
        <br/>
</div>

