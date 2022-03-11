<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<!DOCTYPE html>
<html>
<%@include file="/WEB-INF/views/common/head.jsp"%>
<body class="hold-transition skin-blue sidebar-mini">
<div class="wrapper">

    <%@ include file="/WEB-INF/views/common/header.jsp" %>
    <!-- Left side column. contains the logo and sidebar -->
    <%@ include file="/WEB-INF/views/common/sidebar.jsp" %>

    <!-- Content Wrapper. Contains page content -->
    <div class="content-wrapper">
        <!-- Content Header (Page header) -->
        <section class="content-header">
            <h1>
                Reservations
                <a class="btn btn-primary" href="${pageContext.request.contextPath}/rents/create">Ajouter</a>
            </h1>
        </section>

        <!-- Main content -->
        <section class="content">
            <div class="row">
                <div class="col-md-12">
                    <div class="box">
                        <div class="box-body no-padding">
                            <table class="table table-striped">
                                <tr>
                                    <th style="width: 10px">#</th>
                                    <th>Voiture</th>
                                    <th>Client</th>
                                    <th>Debut</th>
                                    <th>Fin</th>
                                    <th>Action</th>
                                </tr>
                                <c:forEach items="${rents}" var="rent">
										<tr>
											<td><c:out value="${rent.id}" /></td>
											<td><c:out value="${rent.vehicle}" /></td>
											<td><c:out value="${rent.client}" /></td>
											<td><c:out value="${rent.dateStart}" /></td>
											<td><c:out value="${rent.dateEnd}" /></td>
											<td>
												<form method="post">
													<a class="btn btn-success"
														href="${pageContext.request.contextPath}/rents/updates?id=${rent.id}">
														<i class="fa fa-edit"></i>
													</a>
													<button class="btn btn-danger" name="delete"
														value="${rent.id}"
														onclick="return window.confirm('Attention, Cette action est irréversible.')">
														<i class="fa fa-trash"></i>
													</button>
												</form>
											</td>
										</tr>
									</c:forEach>
                            </table>
                        </div>
                        <!-- /.box-body -->
                    </div>
                    <!-- /.box -->
                </div>
                <!-- /.col -->
            </div>
        </section>
        <!-- /.content -->
    </div>

    <%@ include file="/WEB-INF/views/common/footer.jsp" %>
</div>
<!-- ./wrapper -->

<%@ include file="/WEB-INF/views/common/js_imports.jsp" %>
</body>
</html>
