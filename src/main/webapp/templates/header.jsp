<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <title>En ca' Paqui</title>
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <meta name="description" content="">
        <meta name="author" content="">

        <!--link rel="stylesheet/less" href="less/bootstrap.less" type="text/css" /-->
        <!--link rel="stylesheet/less" href="less/responsive.less" type="text/css" /-->
        <!--script src="js/less-1.3.3.min.js"></script-->
        <!--append ‘#!watch’ to the browser URL, then refresh the page. -->

        <link href="assets/css/bootstrap.min.css" rel="stylesheet">
        <link href="assets/css/style.css" rel="stylesheet">

        <!-- HTML5 shim, for IE6-8 support of HTML5 elements -->
        <!--[if lt IE 9]>
          <script src="js/html5shiv.js"></script>
        <![endif]-->

        <!-- Fav and touch icons -->
        <link rel="apple-touch-icon-precomposed" sizes="144x144" href="assets/img/apple-touch-icon-144-precomposed.png">
        <link rel="apple-touch-icon-precomposed" sizes="114x114" href="assets/img/apple-touch-icon-114-precomposed.png">
        <link rel="apple-touch-icon-precomposed" sizes="72x72" href="assets/img/apple-touch-icon-72-precomposed.png">
        <link rel="apple-touch-icon-precomposed" href="assets/img/apple-touch-icon-57-precomposed.png">
        <link rel="shortcut icon" href="assets/img/favicon.png">

        <script type="text/javascript" src="assets/js/jquery.min.js"></script>
        <script type="text/javascript" src="assets/js/bootstrap.min.js"></script>
        
        
        <style>
            .glyphicon { margin-right:10px; }
            .panel-body { padding:0px; }
            .panel-body table tr td { padding-left: 15px }
            .panel-body .table {margin-bottom: 0px; }    
        </style>
    </head>

    <body>
        <div class="container">
            <div class="row clearfix">
                <div class="col-md-12 column">
                    <header>
                        <div class="page-header">
                            <h1>
                                En ca' Paqui <small>Tu tienda de barrio online</small>
                            </h1>
                        </div>
                        <nav class="navbar navbar-inverse navbar-static-top" role="navigation">
                            <div class="container-fluid">
                                <!-- Brand and toggle get grouped for better mobile display -->
                                <div class="navbar-header">
                                    <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1">
                                        <span class="sr-only">Toggle navigation</span>
                                        <span class="icon-bar"></span>
                                        <span class="icon-bar"></span>
                                        <span class="icon-bar"></span>
                                    </button>
                                    <!--<a class="navbar-brand" href="#">Brand</a>-->
                                </div>

                                <!-- Collect the nav links, forms, and other content for toggling -->
                                <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
                                    <ul class="nav navbar-nav">
                                        <li<t:choose><t:when test="${templatepage.getSelected().equalsIgnoreCase('index')}"><% out.println(" class=\"active\"");%></t:when></t:choose>>
                                                    <a href="Index">Home</a>
                                                </li>
                                                <li<t:choose><t:when test="${templatepage.getSelected().equalsIgnoreCase('aboutus')}"><% out.println(" class=\"active\"");%></t:when></t:choose>>
                                                    <a href="AboutUs">Sobre nosotros</a>
                                                </li>
                                                <li<t:choose><t:when test="${templatepage.getSelected().equalsIgnoreCase('contact')}"><% out.println(" class=\"active\"");%></t:when></t:choose>>
                                            <a href="Contact">Contacto</a>
                                        </li>
                                        <li class="dropdown pull-right">
                                            <a href="#" data-toggle="dropdown" class="dropdown-toggle">Conectado<strong class="caret"></strong></a>
                                            <ul class="dropdown-menu">
                                                <li>
                                                    <a href="ProductoController?action=list">Administrar productos</a>
                                                </li>
                                                <li>
                                                    <a href="PedidoController?action=list">Administrar pedidos</a>
                                                </li>
                                                <li class="divider">
                                                </li>
                                                <li>
                                                    <a href="Logout">Desconectar</a>
                                                </li>
                                            </ul>
                                        </li>
                                            </ul>
                                        </div><!-- /.navbar-collapse -->
                                    </div><!-- /.container-fluid -->
                                </nav>
                            </header>
                            <div class="row clearfix">
                                <div class="col-md-3 column">
                                    <div class="panel-group" id="accordion">
                                        <div class="panel panel-default">
                                            <div class="panel-heading">
                                                <h4 class="panel-title">
                                                    <a data-toggle="collapse" data-parent="#accordion" href="#collapseOne"><span class="glyphicon glyphicon-folder-close">
                                                        </span>Content</a>
                                                </h4>
                                            </div>
                                            <div id="collapseOne" class="panel-collapse collapse in">
                                                <div class="panel-body">
                                                    <table class="table">
                                                        <tr>
                                                            <td>
                                                                <span class="glyphicon glyphicon-pencil text-primary"></span><a href="http://www.jquery2dotnet.com">Articles</a>
                                                            </td>
                                                        </tr>
                                                        <tr>
                                                            <td>
                                                                <span class="glyphicon glyphicon-flash text-success"></span><a href="http://www.jquery2dotnet.com">News</a>
                                                            </td>
                                                        </tr>
                                                        <tr>
                                                            <td>
                                                                <span class="glyphicon glyphicon-file text-info"></span><a href="http://www.jquery2dotnet.com">Newsletters</a>
                                                            </td>
                                                        </tr>
                                                        <tr>
                                                            <td>
                                                                <span class="glyphicon glyphicon-comment text-success"></span><a href="http://www.jquery2dotnet.com">Comments</a>
                                                                <span class="badge">42</span>
                                                            </td>
                                                        </tr>
                                                    </table>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="panel panel-default">
                                            <div class="panel-heading">
                                                <h4 class="panel-title">
                                                    <a data-toggle="collapse" data-parent="#accordion" href="#collapseTwo"><span class="glyphicon glyphicon-th">
                                                        </span>Modules</a>
                                                </h4>
                                            </div>
                                            <div id="collapseTwo" class="panel-collapse collapse">
                                                <div class="panel-body">
                                                    <table class="table">
                                                        <tr>
                                                            <td>
                                                                <a href="http://www.jquery2dotnet.com">Orders</a> <span class="label label-success">$ 320</span>
                                                            </td>
                                                        </tr>
                                                        <tr>
                                                            <td>
                                                                <a href="http://www.jquery2dotnet.com">Invoices</a>
                                                            </td>
                                                        </tr>
                                                        <tr>
                                                            <td>
                                                                <a href="http://www.jquery2dotnet.com">Shipments</a>
                                                            </td>
                                                        </tr>
                                                        <tr>
                                                            <td>
                                                                <a href="http://www.jquery2dotnet.com">Tex</a>
                                                            </td>
                                                        </tr>
                                                    </table>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="panel panel-default">
                                            <div class="panel-heading">
                                                <h4 class="panel-title">
                                                    <a data-toggle="collapse" data-parent="#accordion" href="#collapseThree"><span class="glyphicon glyphicon-user">
                                                        </span>Account</a>
                                                </h4>
                                            </div>
                                            <div id="collapseThree" class="panel-collapse collapse">
                                                <div class="panel-body">
                                                    <table class="table">
                                                        <tr>
                                                            <td>
                                                                <a href="http://www.jquery2dotnet.com">Change Password</a>
                                                            </td>
                                                        </tr>
                                                        <tr>
                                                            <td>
                                                                <a href="http://www.jquery2dotnet.com">Notifications</a> <span class="label label-info">5</span>
                                                            </td>
                                                        </tr>
                                                        <tr>
                                                            <td>
                                                                <a href="http://www.jquery2dotnet.com">Import/Export</a>
                                                            </td>
                                                        </tr>
                                                        <tr>
                                                            <td>
                                                                <span class="glyphicon glyphicon-trash text-danger"></span><a href="http://www.jquery2dotnet.com" class="text-danger">
                                                                    Delete Account</a>
                                                            </td>
                                                        </tr>
                                                    </table>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="panel panel-default">
                                            <div class="panel-heading">
                                                <h4 class="panel-title">
                                                    <a data-toggle="collapse" data-parent="#accordion" href="#collapseFour"><span class="glyphicon glyphicon-file">
                                                        </span>Reports</a>
                                                </h4>
                                            </div>
                                            <div id="collapseFour" class="panel-collapse collapse">
                                                <div class="panel-body">
                                                    <table class="table">
                                                        <tr>
                                                            <td>
                                                                <span class="glyphicon glyphicon-usd"></span><a href="http://www.jquery2dotnet.com">Sales</a>
                                                            </td>
                                                        </tr>
                                                        <tr>
                                                            <td>
                                                                <span class="glyphicon glyphicon-user"></span><a href="http://www.jquery2dotnet.com">Customers</a>
                                                            </td>
                                                        </tr>
                                                        <tr>
                                                            <td>
                                                                <span class="glyphicon glyphicon-tasks"></span><a href="http://www.jquery2dotnet.com">Products</a>
                                                            </td>
                                                        </tr>
                                                        <tr>
                                                            <td>
                                                                <span class="glyphicon glyphicon-shopping-cart"></span><a href="http://www.jquery2dotnet.com">Shopping Cart</a>
                                                            </td>
                                                        </tr>
                                                    </table>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div class="col-md-9 column">
                                    <ol class="breadcrumb">
                                        ${templatepage.getTV()}
                                    </ol>

                            <!--<div class="row clearfix">-->