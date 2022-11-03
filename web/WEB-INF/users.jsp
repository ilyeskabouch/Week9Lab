<%-- 
    Document   : users
    Created on : Oct 27, 2022, 12:10:02 PM
    Author     : ilyes
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Manage Users</title>
    </head>
    <body>
        <h1>Manage Users</h1>
        ${message}
        <c:choose>
            <c:when test="${empty users}">
                <b>No users found. Please add a user.</b>
            </c:when>
            <c:otherwise>
                <table>
                    <tr>
                        <th>Email</th>
                        <th>First Name</th>
                        <th>Last Name</th>
                        <th>Role</th>
                        <th></th>
                        <th></th>
                    </tr>
                    <c:forEach items="${users}" var="user">
                        <tr>
                            <td>${user.email}</td>
                            <td>${user.firstName}</td>
                            <td>${user.lastName}</td>
                            <td>${user.role.roleName}</td>
                            <td>
                                <c:url value="user" var="url">
                                    <c:param name="action" value="edit"/>
                                    <c:param name="email" value="${user.email}"/>
                                </c:url>
                                <a href="${url}">Edit</a>
                            </td>
                            <td>
                                <c:url value="user" var="url">
                                    <c:param name="action" value="delete"/>
                                    <c:param name="email" value="${user.email}"/>
                                </c:url>
                                <a href="${url}">Delete</a>
                            </td>
                        </tr>
                    </c:forEach>
                </table>
            </c:otherwise>
        </c:choose>
        <c:choose>
            <c:when test="${action=='edit'}">
                <h2>Edit User</h2>
                <c:url value="user" var="url">
                    <c:param name="action" value="edit"/>
                    <c:param name="email" value="${email}"/>
                </c:url>
                <form action="${url}" method="post">
                    Email: ${email}<br>
                    First name: <input type="text" name="firstname" value="${firstname}"><br>
                    Last name: <input type="text" name="lastname" value="${lastname}"><br>
                    Password: <input type="password" name="password"><br>
                    Role: <select name="role">
                        <option value="admin" ${ role == "system admin" ? "selected" : " "}>system admin</option>
                        <option value="user" ${ role == "regular user" ? "selected" : " "}>regular user</option>
                    </select> <br>
                    <input type="submit" value="Edit user"> <button type="submit" formaction="user">Cancel</button>
                </form>
            </c:when>
            <c:otherwise>
                <h2>Add User</h2>
                <form action="user?action=add" method="post">
                    Email: <input type="text" name="email"><br>
                    First name: <input type="text" name="firstname"><br>
                    Last name: <input type="text" name="lastname"><br>
                    Password: <input type="password" name="password"><br>
                    Role: <select name="role">
                        <option value="admin">system admin</option>
                        <option value="user">regular user</option>
                    </select> <br>
                    <input type="submit" value="Add user">
                </form>
            </c:otherwise>
        </c:choose>
        ${error}
    </body>
</html>
