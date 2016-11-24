<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Listing page</title>
<style>	
	body { margin: 25px; padding: 0; }
	#pageHeader { width: 1024px; margin-bottom: 30px; border-bottom: 3px solid black; }
	#pageHeader table { width: 100%; border-collapse: collapse; border: 0; }
	#pageHeader table thead tr { height: 50px; }
	#pageHeader table input[type=submit] { width: 100%; border: 0; outline: none; background: none; background-color: none; cursor: pointer; height: 30px; }
	#pageHeader table input[type=submit]:hover { background-color: rgba(0, 255, 255, 0.2); color: red; }
	
	#pageBody { width: 1024px; }
	#pageBody table { width: 100%; border-collapse: collapse; border: 0; }
	#pageBody table thead tr { border-bottom: 1px solid rgba(0, 0, 0, 0.2); }
	#pageBody table thead tr th, #pageBody table tbody tr td { padding-top: 5px; padding-bottom: 5px; }
</style>
</head>
<body>
	<!-- Header -->
	<div id="pageHeader">
		<table>
			<thead>
				<tr>
					<th colspan="3">
						<h1>Simple administration system</h1>
					</th>
				</tr>
			</thead>
			<tbody>
				<tr>
					<td>
						<form action="toolTransition" method="post">
							<input type="hidden" name="page" value="user" />
							<input type="submit" value="User" />
						</form>
					</td>
					<td>
						<form action="toolTransition" method="post">
							<input type="hidden" name="page" value="student" />
							<input type="submit" value="Student" />
						</form>
					</td>
					<td>
						<form action="toolTransition" method="post">
							<input type="hidden" name="page" value="person" />
							<input type="submit" value="Person" />
						</form>
					</td>
				</tr>
			</tbody>
		</table>
	</div>

	<!-- Table -->
	<div id="pageBody">
		<table>
			<thead>
				<tr>
					<c:forEach var="title" items="${ tableHeader }">
						<td>
							<span><c:out value="${ title }"/></span>
						</td>
					</c:forEach>
					<td>
						Actions
					</td>
				</tr>
			</thead>
			<tbody>
				<c:choose>
					<c:when test="${ empty list }">
						<tr>
							<td colspan="${ fn:length(tableHeader) + 1 }" style="font-style: italic; text-align: center;">
								There is no entry in this table.
							</td>
						</tr>
					</c:when>
					<c:otherwise>
						<c:forEach var="obj" items="${ list }">
							<tr>
								<c:forTokens var="col" items="${ obj.toString() }" delims=";">
									<td>
										<span><c:out value="${ col }"/></span>
									</td>
								</c:forTokens>
								<td style="text-align: right;">
									<form action="toolTransition" method="post" style="display: inline-block;">
										<input type="hidden" name="object" value="${ obj }">
										<input type="hidden" name="action" value="forwardEdit">
										<input type="hidden" name="page" value="modification">
										<input type="hidden" name="selected" value="${ page }">
										<input type="submit" value="Edit">
									</form>
									<form action="toolTransition" method="post" style="display: inline-block;">
										<input type="hidden" name="object" value="${ obj }">
										<input type="hidden" name="action" value="delete">
										<input type="hidden" name="page" value="${ page }">
										<input type="submit" value="Delete">
									</form>
								</td>
							</tr>
						</c:forEach>
					</c:otherwise>
				</c:choose>				
			</tbody>
			<tfoot>
				<tr>
					<td colspan="${ fn:length(tableHeader) + 1 }">
						<form action="toolTransition" method="post" style="display: inline-block;">
							<input type="hidden" name="action" value="forwardInsert">
							<input type="hidden" name="page" value="modification">
							<input type="hidden" name="selected" value="${ page }">
							<input type="submit" value="Insert">
						</form>
					</td>
				</tr>
			</tfoot>
		</table>
	</div>
</body>
</html>