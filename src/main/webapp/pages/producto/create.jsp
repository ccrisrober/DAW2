        <h1>Hello World!</h1>
        <% out.println(request.getAttribute("error")); %>
        <% out.println(request.getAttribute("ok")); %>
        
        <form action="ProductoController" method="post">
            <button type="submit"></button>
        </form>
