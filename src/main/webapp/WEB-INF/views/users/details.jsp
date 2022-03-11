<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<%@include file="/WEB-INF/views/common/head.jsp"%>
<body class="hold-transition skin-blue sidebar-mini">
	<div class="wrapper">

		<%@ include file="/WEB-INF/views/common/header.jsp"%>
		<!-- Left side column. contains the logo and sidebar -->
		<%@ include file="/WEB-INF/views/common/sidebar.jsp"%>

		<!-- Content Wrapper. Contains page content -->
		<div class="content-wrapper">
			<!-- Main content -->
			<section class="content">

				<div class="row">
					<div class="col-md-3">

						<!-- Profile Image -->
						<div class="box box-primary">
							<div class="box-body box-profile">
								<h3 class="profile-username text-center">${userName}
									(${userEmail})</h3>

								<ul class="list-group list-group-unbordered">
									<li class="list-group-item"><b>Reservation(s)</b> <a
										class="pull-right">${nbResa}</a></li>
									<li class="list-group-item"><b>Voiture(s)</b> <a
										class="pull-right">${nbCar}</a></li>
								</ul>
							</div>
							<!-- /.box-body -->
						</div>
						<!-- /.box -->
					</div>
					<!-- /.col -->
					<div class="col-md-9">
						<div class="nav-tabs-custom">
							<ul class="nav nav-tabs">
								<li class="active"><a href="#rents" data-toggle="tab">Reservations</a></li>
								<li><a href="#cars" data-toggle="tab">Voitures</a></li>
							</ul>
							<div class="tab-content">
								<div class="active tab-pane" id="rents">
									<div class="box-body no-padding">
										<table class="table table-striped">
											<tr>
												<th style="width: 10px">#</th>
												<th>Voiture</th>
												<th>Date de debut</th>
												<th>Date de fin</th>
											</tr>
											<c:forEach items="${rents}" var="rent">
												<tr>
													<td><c:out value="${rent.id}" /></td>
													<td><c:out value="${rent.vehicle}" /></td>
													<td><c:out value="${rent.dateStart}" /></td>
													<td><c:out value="${rent.dateEnd}" /></td>
												</tr>
											</c:forEach>
										</table>
									</div>
								</div>
								<!-- /.tab-pane -->
								<div class="tab-pane" id="cars">
									<!-- /.box-header -->
									<div class="box-body no-padding">
										<table class="table table-striped">
											<tr>
												<th style="width: 10px">#</th>
												<th>Modele</th>
												<th>Constructeur</th>
												<th>Nombre de places</th>
											</tr>
											<c:forEach items="${cars}" var="car">
												<tr>
													<td><c:out value="${car.id}" /></td>
													<td><c:out value="${car.constructor}" /></td>
													<td><c:out value="${car.model}" /></td>
													<td><c:out value="${car.nbPlace}" /></td>
												</tr>
											</c:forEach>
										</table>
									</div>
								</div>
								<!-- /.tab-pane -->
							</div>
							<!-- /.tab-content -->
						</div>
						<!-- /.nav-tabs-custom -->
					</div>
					<!-- /.col -->
				</div>
				<!-- /.row -->

			</section>
			<!-- /.content -->
		</div>

		<%@ include file="/WEB-INF/views/common/footer.jsp"%>
	</div>
	<!-- ./wrapper -->

	<%@ include file="/WEB-INF/views/common/js_imports.jsp"%>
</body>
</html>