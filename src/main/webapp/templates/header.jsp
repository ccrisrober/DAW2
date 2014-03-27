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
                        <ul class="nav nav-tabs">
                            <li<t:choose><t:when test="${!templatepage.getSelected().equalsIgnoreCase('index')}"><% out.println(" class=\"active\"");%></t:when></t:choose>>
                                <a href="Index">Home</a>
                            </li>
                            <li<t:choose><t:when test="${!templatepage.getSelected().equalsIgnoreCase('aboutus')}"><% out.println(" class=\"active\"");%></t:when></t:choose>>
                                <a href="AboutUs">Sobre nosotros</a>
                            </li>
                            <li<t:choose><t:when test="${!templatepage.getSelected().equalsIgnoreCase('contact')}"><% out.println(" class=\"active\"");%></t:when></t:choose>>
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
                    </header>
                    <div class="row clearfix">
                        <div class="col-md-3 column">
                            <ul class="nav nav-tabs nav-stacked">
                                <li class="active">
                                    <a href="#">Home</a>
                                </li>
                                <li>
                                    <a href="#">Profile</a>
                                </li>
                                <li class="disabled">
                                    <a href="#">Messages</a>
                                </li>
                            </ul>
                        </div>
                        <div class="col-md-9 column">
                            <div class="row clearfix">