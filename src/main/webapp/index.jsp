<%@ page contentType="text/html;charset=UTF-8" 
    language="java"  
    pageEncoding="UTF-8" %>
<%@ taglib prefix="c" 
           uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Document</title>
</head>
<body>
    <h1>hello</h1>
    <%= request.getServerName() %><br />
    <% out.println("你的 IP 地址 " + request.getRemoteAddr() + "<br />"); %>
    <jsp:scriptlet>
        out.println("你的 IP 地址 " + request.getRemoteAddr());
    </jsp:scriptlet>
</body>
</html>