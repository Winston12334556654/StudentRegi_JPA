<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags/form" %>
<c:import url="/WEB-INF/views/header.jsp" >
    <c:param name="title" value="Student Detail" />
</c:import>
<div class="main_contents">
    <div id="sub_content">
        <form:form action="/studUpdate" method="post" modelAttribute="student" enctype="multipart/form-data">
            <h2 class="col-md-6 offset-md-2 mb-5 mt-4">Student Details</h2>

            <div class="row mb-4">
                <div class="col-md-2"></div>
                <label for="studentID" class="col-md-2 col-form-label">Student ID</label>
                <div class="col-md-4">
                    <form:input type="text" class="form-control" id="studentID"  readonly="true" path="id" />
                </div>
            </div>

            <div class="row mb-4">
                <div class="col-md-2"></div>
                <label for="studname" class="col-md-2 col-form-label">Name</label>
                <div class="col-md-4">
                    <form:input type="text" class="form-control" id="studname"  path="name" />
                </div>
            </div>
            <div class="row mb-4">
                <div class="col-md-2"></div>
                <label for="dob" class="col-md-2 col-form-label">DOB</label>
                <div class="col-md-4">
                    <input type="date" class="form-control" id="dob" value="${student.dob}" name="dob">
                </div>
            </div>

            <fieldset class="row mb-4">
                <div class="col-md-2"></div>
                <legend class="col-form-label col-md-2 pt-0">Gender</legend>
                <div class="col-md-4">
                    <div class="form-check-inline">
                        <form:radiobutton class="form-check-input"   id="option1" value="Male"
                                          path="gender"/>
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
                </div>
            </fieldset>

            <div class="row mb-4">
                <div class="col-md-2"></div>
                <label for="phone" class="col-md-2 col-form-label">Phone</label>
                <div class="col-md-4">
                    <form:input type="text" class="form-control" id="phone"  path="phone" />
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

<%--                    &lt;%&ndash;courses by course table&ndash;%&gt;--%>
<%--                    <c:forEach items="${courses}" var="course">--%>
<%--                        <div class="form-check-inline col-md-2">--%>
<%--                            <form:checkbox class="form-check-input"  path="courses" id="gridRadios1" value="${course.id}"  />--%>
<%--                            <label class="form-check-label" for="gridRadios1">--%>
<%--                                    ${course.name}--%>
<%--                            </label>--%>
<%--                        </div>--%>
<%--                    </c:forEach>--%>

<%--                    &lt;%&ndash;courses form student&ndash;%&gt;--%>
<%--                    <c:forEach items="${student.courses}" var="course">--%>
<%--                        <div class="form-check-inline col-md-2">--%>
<%--                            <form:checkbox class="form-check-input"  path="courses" id="gridRadios1" value="${course.id}"  />--%>
<%--                            <label class="form-check-label" for="gridRadios1">--%>
<%--                                    ${course.name}--%>
<%--                            </label>--%>
<%--                        </div>--%>
<%--                    </c:forEach>--%>

        <%-- Combine courses from course table and student's selected courses --%>
        <c:set var="allCourses" value="${courses}" />
        <c:forEach items="${student.courses}" var="selectedCourse">
            <c:if test="${!fn:contains(allCourses, selectedCourse)}">
                <c:set var="allCourses" value="${allCourses + selectedCourse}" />
            </c:if>
        </c:forEach>

        <%-- Display checkboxes for all courses --%>
        <c:forEach items="${courses}" var="course">
            <div class="form-check-inline col-md-2">
                <input type="checkbox" class="form-check-input" name="courseIds" id="gridRadios1" value="${course.id}"
                    ${fn:contains(student.courses, course) ? 'checked' : ''} />
                <label class="form-check-label" for="gridRadios1">${course.name}</label>
            </div>
        </c:forEach>

                </div>
            </fieldset>

            <div class="row mb-4">
                <div class="col-md-2"></div>
                <label for="photo" class="col-md-2 col-form-label">Photo</label>
                <div class="col-md-4">
                    <form:input type="file" class="form-control" id="photo" path="file" />
                    <form:input type="hidden"  id="image"   path="photo"/>
                </div>
            </div>

            <div class="row mb-4">
                <div class="col-md-4"></div>

                <div class="col-md-4">
                    <button type="submit" class="btn btn-secondary">
                        <span>Update</span>
                    </button>
                </div>
                <div class="col-md-4">
                    <button type="button" class="btn btn-danger " onclick="location.href = '/studDelete?id=${student.id}'">
                        Delete
                    </button>
                </div>
            </div>
        </form:form>
    </div>
</div>
<jsp:include page="/WEB-INF/views/footer.jsp" />