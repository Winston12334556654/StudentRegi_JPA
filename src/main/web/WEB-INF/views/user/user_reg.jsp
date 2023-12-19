<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<c:import url="/WEB-INF/views/header.jsp" >
    <c:param name="title" value="User Registration" />
</c:import>
<div class="main_contents">
    <div id="sub_content">

        <form:form action="/userReg" method="post" modelAttribute="user">

            <h2 class="col-md-6 offset-md-2 mb-5 mt-4">User Registration</h2>

            <div class="row mb-4">
                <div class="col-md-2"></div>
                <label for="id" class="col-md-2 col-form-label">ID</label>
                <div class="col-md-4">
                    <form:input type="text" class="form-control" id="id" readonly="true"   path="id" />
                </div>
            </div>

            <div class="row mb-4">
                <div class="col-md-2"></div>
                <label for="name" class="col-md-2 col-form-label">User Name</label>
                <div class="col-md-4">
                    <form:input type="text" class="form-control" id="name"  path="name" />
                    <div id="name-error-message" class="text-danger"></div>
                </div>
            </div>

            <div class="row mb-4">
                <div class="col-md-2"></div>
                <label for="email" class="col-md-2 col-form-label">Email</label>
                <div class="col-md-4">
                    <form:input type="email" class="form-control" id="email" path="email" />
                    <div id="email-error-message" class="text-danger"></div>
                </div>
            </div>

            <div class="row mb-4">
                <div class="col-md-2"></div>
                <label for="password" class="col-md-2 col-form-label">Password</label>
                <div class="col-md-4">
                    <form:input type="password" class="form-control" id="password"  path="password" />
                    <div id="password-error-message" class="text-danger"></div>

                </div>
            </div>

            <div class="row mb-4">
                <div class="col-md-2"></div>
                <label for="confirmPassword" class="col-md-2 col-form-label">Confirm Password</label>
                <div class="col-md-4">
                    <input type="password" class="form-control" id="confirmPassword" name="confirmPassword" oninput="validatePassword()" />
                    <div id="error-message" class="text-danger"></div>

                </div>
            </div>

            <div class="row mb-4">
                <div class="col-md-2"></div>
                <label for="role" class="col-md-2 col-form-label">User Role</label>
                <div class="col-md-4">
                    <form:select class="form-select" aria-label="Education" id="role"  path="roleName">
                        <form:option value="ADMIN">Admin</form:option>
                        <form:option value="USER">User</form:option>
                    </form:select>
                </div>
            </div>

            <div class="row mb-4">
                <div class="col-md-4"></div>
                <div class="col-md-6">
                    <button type="submit" class="btn btn-secondary col-md-2" data-bs-toggle="modal" data-bs-target="#exampleModal">Add</button>
                        <%--                    <div class="modal fade" id="exampleModal" tabindex="-1" aria-labelledby="exampleModalLabel"--%>
                        <%--                         aria-hidden="true">--%>
                        <%--                        <div class="modal-dialog modal-dialog-centered">--%>
                        <%--                            <div class="modal-content">--%>
                        <%--                                <div class="modal-header">--%>
                        <%--                                    <h5 class="modal-title" id="exampleModalLabel">Student Registration</h5>--%>
                        <%--                                    <button type="button" class="btn-close" data-bs-dismiss="modal"--%>
                        <%--                                            aria-label="Close"></button>--%>
                        <%--                                </div>--%>
                        <%--                                <div class="modal-body">--%>

                        <%--                                    <h5 style="color: rgb(127, 209, 131);">Registered Successfully !</h5>--%>
                        <%--                                </div>--%>
                        <%--                                <div class="modal-footer">--%>
                        <%--                                    <button type="button" class="btn btn-success col-md-2" data-bs-dismiss="modal">Ok</button>--%>
                        <%--                                </div>--%>
                        <%--                            </div>--%>
                        <%--                        </div>--%>
                        <%--                    </div>--%>
                </div>
            </div>
        </form:form>
    </div>
</div>

<script src="${pageContext.request.contextPath}/assets/js/user_reg.js"></script>






<jsp:include page="/WEB-INF/views/footer.jsp" />