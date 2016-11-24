<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Data modification page</title>
</head>
<body>
	<!-- body -->
	<div>
		<form action="toolTransition" method="post">
			<table>
				<thead>
					<tr>
						<th colspan="2">
							<span>
								<c:choose>
									<c:when test="${ type eq 'user' }">
										User registration form
									</c:when>
									<c:when test="${ type eq 'student' }">
										Student registration form
									</c:when>
									<c:when test="${ type eq 'person' }">
										Person registration form
									</c:when>
								</c:choose>
							</span>
						</th>
					</tr>
				</thead>
				<tbody>
					<c:choose>
						<c:when test="${ type eq 'user' }">
							<!-- User -->
							<tr>
								<td><label for="username">Username:</label></td>
								<td>
									<input id="page" type="hidden" name="page" value="user" />
									<c:choose>
										<c:when test="${ mode eq 'insert' }">
											<input type="text" name="username" value="${ obj.getUser() }" required />
										</c:when>
										<c:when test="${ mode eq 'edit' }">
											<input type="text" name="username" value="${ obj.getUser() }" required readonly />
										</c:when>
									</c:choose>
								</td>
							</tr>
							<tr>
								<td><label for="username">Password:</label></td>
								<td><input type="password" name="password" value="${ obj.getPassword() }" required /></td>
							</tr>
							<tr>
								<td><label for="username">Confirm Password:</label></td>
								<td><input type="password" name="cpassword" value="${ obj.getPassword() }" required /></td>
							</tr>
							<tr>
								<td><label for="username">Person ID:</label></td>
								<td>
									<select name="person_id">
										<c:forEach var="p" items="${ allPerson }">
											<c:choose>
												<c:when test="${ obj.getPersonID() eq p.getID() }">
													<option value="${ p.getID() }" selected><c:out value="${ p.getID() }" /></option>
												</c:when>
												<c:otherwise>
													<option value="${ p.getID() }"><c:out value="${ p.getID() }" /></option>
												</c:otherwise>
											</c:choose>
										</c:forEach>
									</select>
								</td>
							</tr>
							<tr>
								<td><label for="username">Authority:</label></td>
								<td>
									<select name="authority">
										<c:forEach var="i" begin="1" end="5" step="1">
											<c:choose>
												<c:when test="${ obj.getAuthority() eq i }">
													<option value="${ i }" selected><c:out value="${ i }" /></option>
												</c:when>
												<c:otherwise>
													<option value="${ i }"><c:out value="${ i }" /></option>
												</c:otherwise>
											</c:choose>
										</c:forEach>
									</select>
								</td>
							</tr>
						</c:when>
						<c:when test="${ type eq 'student' }">
							<!-- Student -->
							<tr>
								<td>
									<label for="person_id">Person ID:</label>
								</td>
								<td>
									<input id="page" type="hidden" name="page" value="student" />
									<c:choose>
										<c:when test="${ mode eq 'insert' }">
											<select name="person_id">
												<c:forEach var="p" items="${ allPerson }">
													<c:choose>
														<c:when test="${ obj.getPersonID() eq p.getID() }">
															<option value="${ p.getID() }" selected><c:out value="${ p.getID() }" /></option>
														</c:when>
														<c:otherwise>
															<option value="${ p.getID() }"><c:out value="${ p.getID() }" /></option>
														</c:otherwise>
													</c:choose>
												</c:forEach>
											</select>
										</c:when>
										<c:when test="${ mode eq 'edit' }">
											<select name="person_id" disabled>
												<c:forEach var="p" items="${ allPerson }">
													<c:choose>
														<c:when test="${ obj.getPersonID() eq p.getID() }">
															<option value="${ p.getID() }" selected><c:out value="${ p.getID() }" /></option>
														</c:when>
														<c:otherwise>
															<option value="${ p.getID() }"><c:out value="${ p.getID() }" /></option>
														</c:otherwise>
													</c:choose>
												</c:forEach>
											</select>
										</c:when>
									</c:choose>
								</td>
							</tr>
							<tr>
								<td>
									<label for="admission_status">Admission Status:</label>
								</td>
								<td>
									<select name="admission_status">
										<c:forTokens var="p" items="pending,received,accepted,rejected" delims=",">
											<c:choose>
												<c:when test="${ obj.getAdmissionStatus() eq p }">
													<option value="${ p }" selected><c:out value="${ p }" /></option>
												</c:when>
												<c:otherwise>
													<option value="${ p }"><c:out value="${ p }" /></option>
												</c:otherwise>
											</c:choose>
										</c:forTokens>
									</select>
								</td>
							</tr>
							<tr>
								<td><label for="major">Major:</label></td>
								<td><input type="text" name="major" value="${ obj.getMajor() }" required /></td>
							</tr>
							<tr>
								<td><label for="minor">Minor:</label></td>
								<td><input type="text" name="minor" value="${ obj.getMinor() }" /></td>
							</tr>
							<tr>
								<td><label for="credit">Credit:</label></td>
								<td><input type="number" name="credit" value="${ obj.getCredit() }" required /></td>
							</tr>
						</c:when>
						<c:when test="${ type eq 'person' }">
							<!-- Person -->
							<tr>
								<td><label for="first_name">First name:</label></td>
								<td>
									<input type="hidden" name="id" value="${ obj.getID() }" />
									<input id="page" type="hidden" name="page" value="person" />
									<input type="text" name="first_name" value="${ obj.getFirstName() }" required />
								</td>
							</tr>
							<tr>
								<td><label for="last_name">Last name:</label></td>
								<td><input type="text" name="last_name" value="${ obj.getLastName() }" required /></td>
							</tr>
							<tr>
								<td><label for="street_address">Street address:</label></td>
								<td><input type="text" name="street_address" value="${ obj.getStreetAddress() }" /></td>
							</tr>
							<tr>
								<td><label for="city">City:</label></td>
								<td><input type="text" name="city" value="${ obj.getCity() }" /></td>
							</tr>
							<tr>
								<td><label for="state">State:</label></td>
								<td><input type="text" name="state" value="${ obj.getState() }" /></td>
							</tr>
							<tr>
								<td><label for="zip">Postal Code:</label></td>
								<td><input type="text" name="zip" value="${ obj.getPostal() }" /></td>
							</tr>
							<tr>
								<td><label for="country">Country:</label></td>
								<td><input type="text" name="country" value="${ obj.getCountry() }" /></td>
							</tr>
							<tr>
								<td><label for="telephone">Phone:</label></td>
								<td><input type="text" name="telephone" value="${ obj.getTelephone() }" /></td>
							</tr>
							<tr>
								<td><label for="email">E-mail:</label></td>
								<td><input type="text" name="email" value="${ obj.getEmail() }" /></td>
							</tr>
							<tr>
								<td><label for="gender">Gender:</label></td>
								<td><input type="text" name="gender" value="${ obj.getGender() }" /></td>
							</tr>
							<tr>
								<td><label for="sin">SIN:</label></td>
								<td><input type="text" name="sin" value="${ obj.getSocialInsuranceNumber() }" /></td>
							</tr>
						</c:when>
						<c:otherwise>
							<!-- Do nothing -->
						</c:otherwise>
					</c:choose>
				</tbody>
				<tfoot>
					<tr>
						<td></td>
						<td>
							<c:choose>
								<c:when test="${ mode eq 'insert' }">
									<input type="hidden" name="action" value="insert" />
									<input type="submit" value="Insert" />
								</c:when>
								<c:when test="${ mode eq 'edit' }">
									<input type="hidden" name="action" value="edit" />
									<input type="submit" value="Modify" />
								</c:when>
							</c:choose>
							<input type="reset" value="Back" onclick="returnToList()" />
						</td>
					</tr>
				</tfoot>
			</table>
		</form>
	</div>
	
	<!-- Scripts -->
	<script>
		function returnToList()
		{
			window.location = '/JSPProject/toolTransition?page=' + document.getElementById('page').value;
		}
	</script>
</body>
</html>