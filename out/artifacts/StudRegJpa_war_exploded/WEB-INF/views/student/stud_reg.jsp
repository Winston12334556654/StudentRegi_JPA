<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<c:import url="/WEB-INF/views/header.jsp" >
    <c:param name="title" value="Student Registration" />
</c:import>
<head>


</head>


<%--<button id="button" type="button" class="btn btn-secondary col-md-2" >--%>
<%--    Student Registrant--%>
<%--</button>--%>



<div id ="main_contents" class="main_contents" >



    <div id="sub_content" >

        <form:form action="/studReg" method="post" modelAttribute="student" enctype="multipart/form-data" >

        <h2 class="col-md-6 offset-md-2 mb-5 mt-4">Student Registration</h2>

        <div class="row mb-4">
            <div class="col-md-2"></div>
            <label for="id" class="col-md-2 col-form-label">id</label>
            <div class="col-md-4">
                <form:input type="text" class="form-control" id="id" path="id" readonly="true"/>
            </div>
        </div>

        <div class="row mb-4">
            <div class="col-md-2"></div>
            <label for="name" class="col-md-2 col-form-label">Name</label>
            <div class="col-md-4">
                <form:input type="text" class="form-control" id="name" placeholder="Harry" path="name" />
                <div id="name_errormessage" class="text-danger"  style="color : red"></div>
            </div>
        </div>

        <div class="row mb-4">
            <div class="col-md-2"></div>
            <label for="dob" class="col-md-2 col-form-label">DOB</label>
            <div class="col-md-4">
                <form:input type="date" class="form-control" id="dob"  path="dob" />
                <div id="dob_errormessage" class="text-danger"  style="color : red"></div>
            </div>
        </div>

        <fieldset class="row mb-4">
            <div class="col-md-2"></div>
            <legend class="col-form-label col-md-2 pt-0">Gender</legend>
            <div class="col-md-4">
                <div class="form-check-inline">
                    <form:radiobutton class="form-check-input"   id="option1" value="Male"
                                      path="gender" checked="checked"/>
                    <label class="form-check-label" for="option1">
                        Male
                    </label>
                </div>
                <div class="form-check-inline">
                    <form:radiobutton class="form-check-input"  id="option2" value="Female" path="gender" />
                    <label class="form-check-label" for="option2">
                        Female
                    </label>
                </div>
                <div id="gender_errormessage" class="text-danger"  style="color : red"></div>

            </div>
        </fieldset>

        <div class="row mb-4">
            <div class="col-md-2"></div>
            <label for="phone" class="col-md-2 col-form-label">Phone</label>
            <div class="col-md-4">
                <form:input type="text" class="form-control" id="phone" path="phone" />
                <div id="phone_errormessage" class="text-danger"  style="color : red"></div>
            </div>
        </div>

        <div class="row mb-4">
            <div class="col-md-2"></div>
            <label for="education" class="col-md-2 col-form-label">Education</label>
            <div class="col-md-4">
                <form:select class="form-select" aria-label="Education" id="education" name="education" path="education">
                    <form:option  value="">Bachelor of Information Technology</form:option>
                    <form:option value="Diploma in IT">Diploma in IT</form:option>
                    <form:option value="Beachelor of Computer Science">Bachelor of Computer Science</form:option>
                </form:select>
            </div>
        </div>

        <fieldset class="row mb-4">
            <div class="col-md-2"></div>
            <legend class="col-form-label col-md-2 pt-0">Attend</legend>
            <div class="col-md-4">
                <c:forEach items="${courses}" var="course">
                    <div class="form-check-inline col-md-2">
                        <form:checkbox class="form-check-input"  path="courseIds" id="gridRadios1" value="${course.id}"  />
                        <label class="form-check-label" for="gridRadios1">
                                ${course.name}
                        </label>
                    </div>
                </c:forEach>
            </div>
            <div id="course_errormessage" class="text-danger"  style="color : red"></div>
        </fieldset>




        <div class="row mb-4">
            <div class="col-md-2"></div>
            <label for="photo" class="col-md-2 col-form-label">Photo</label>
            <div class="col-md-4">
                <form:input type="file" class="form-control" id="photo" path="file" />
                <div id="photo_errormessage" class="text-danger"  style="color : red"></div>
            </div>
        </div>

        <div class="row mb-4">
            <div class="col-md-4"></div>

            <div class="col-md-4">
                <button type="button" class="btn btn-danger mb-3" data-bs-toggle="modal" data-bs-target="#exampleModal3">Rest</button>
                <!--model-->
                <div class="modal fade" id="exampleModal3" tabindex="-1" aria-labelledby="exampleModalLabel"
                     aria-hidden="true">
                    <div class="modal-dialog modal-dialog modal-dialog-centered">
                        <div class="modal-content">
                            <div class="modal-header">
                                <h5 class="modal-title" id="exampleModalLabel3">My Apologize</h5>
                                <button type="button" class="btn-close" data-bs-dismiss="modal"
                                        aria-label="Close"></button>
                            </div>
                            <div class="modal-body">
                                Sorry , Doesn't Support this Feature Yet! <br><br>
                                Developing in progress.... :)
                            </div>
                        </div>
                    </div>
                </div>
                <button type="submit" class="btn btn-secondary col-md-2" data-bs-toggle="modal" data-bs-target="#exampleModal">
                    Add
                </button>
            </div>

            <div class="modal fade" id="exampleModal" tabindex="-1" aria-labelledby="exampleModalLabel"
                 aria-hidden="true">
                <div class="modal-dialog modal-dialog-centered">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h5 class="modal-title" id="exampleModalLabel">Student Registration</h5>
                            <button type="button" class="btn-close" data-bs-dismiss="modal"
                                    aria-label="Close"></button>
                        </div>
                        <div class="modal-body">
                            <h5 style="color: rgb(127, 209, 131);">Registered Succesfully !</h5>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-success col-md-2" data-bs-dismiss="modal">Ok</button>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    </form:form>
</div>
</div>
<jsp:include page="/WEB-INF/views/footer.jsp" />

<script src="${pageContext.request.contextPath}/assets/js/stu_reg.js"></script>
