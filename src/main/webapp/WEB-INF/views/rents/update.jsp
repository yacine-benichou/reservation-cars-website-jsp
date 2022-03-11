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
			<!-- Content Header (Page header) -->
			<section class="content-header">
				<h1>Reservation (Id = ${rentId})</h1>
			</section>

			<!-- Main content -->
			<section class="content">
				<div class="row">
					<div class="col-md-12">
						<!-- Horizontal Form -->
						<div class="box">
							<!-- form start -->
							<form class="form-horizontal" method="post">
								<div class="box-body">
									<div class="form-group">
										<label for="car" class="col-sm-2 control-label">Voiture</label>

										<div class="col-sm-10">
											<select class="form-control" id="car" name="car">
												<c:forEach items="${listVehicles}" var="vehicle">
													<option value="${vehicle.id}"
														${vehicle.id == vehicleId ? 'selected="selected"' : ''}>${vehicle.constructor}
														${vehicle.model}</option>
												</c:forEach>
											</select>
										</div>
									</div>
									<div class="form-group">
										<label for="client" class="col-sm-2 control-label">Client</label>

										<div class="col-sm-10">
											<select class="form-control" id="client" name="client">
												<c:forEach items="${listClients}" var="client">
													<option value="${client.id}"
														${client.id == clientId ? 'selected="selected"' : ''}>${client.lastname}
														${client.firstname}</option>
												</c:forEach>
											</select>
										</div>
									</div>
									<div class="form-group">
										<label for="begin" class="col-sm-2 control-label">Date
											de debut</label>

										<div class="col-sm-10">
											<input type="date" class="form-control" id="begin"
												name="begin" placeholder="begin" value="${startDate}"
												Onchange="minMaxEndDate()" required>
										</div>
									</div>
									<div class="form-group">
										<label for="end" class="col-sm-2 control-label">Date
											de fin</label>

										<div class="col-sm-10">
											<input type="date" class="form-control" id="end" name="end"
												placeholder="end" value="${endDate}"
												Onclick="minMaxEndDate()" required>
										</div>
									</div>
								</div>
								<!-- /.box-body -->
								<div class="box-footer">
									<a class="btn btn-danger pull-right" href="${pageContext.request.contextPath}/rents">Annuler</a>
									<button type="reset" class="btn btn-warning pull-right">Réinitialiser</button>
									<button type="submit" value="submit" class="btn btn-info pull-right">Modifier</button>
								</div>
								<!-- /.box-footer -->
							</form>
						</div>
						<!-- /.box -->
					</div>
					<!-- /.col -->
				</div>
			</section>
			<!-- /.content -->
		</div>

		<%@ include file="/WEB-INF/views/common/footer.jsp"%>
	</div>
	<!-- ./wrapper -->

	<%@ include file="/WEB-INF/views/common/js_imports.jsp"%>
	<script
		src="${pageContext.request.contextPath}/resources/plugins/input-mask/jquery.inputmask.js"></script>
	<script
		src="${pageContext.request.contextPath}/resources/plugins/input-mask/jquery.inputmask.date.extensions.js"></script>
	<script
		src="${pageContext.request.contextPath}/resources/plugins/input-mask/jquery.inputmask.extensions.js"></script>
	<script>
		$(function() {
			$('[data-mask]').inputmask()
		});
	</script>
	<script>
		function minMaxEndDate() {
			var inputDate;
			var minDate;
			var minDateString;
			var maxDate;
			var maxDateString;
			inputDate = document.getElementById('begin').value;
			minDate = new Date(inputDate);
			minDate.setDate(minDate.getDate() + 1);
			minDateString = minDate.getFullYear() + "-"
					+ ("0" + (minDate.getMonth() + 1)).slice(-2) + "-"
					+ (minDate.getDate());
			maxDate = new Date(inputDate);
			maxDate.setDate(maxDate.getDate() + 7);
			maxDateString = maxDate.getFullYear() + "-"
					+ ("0" + (maxDate.getMonth() + 1)).slice(-2) + "-"
					+ (maxDate.getDate());
			document.getElementById('end').setAttribute('min', minDateString);
			document.getElementById('end').setAttribute('max', maxDateString);
		}
	</script>
</body>
</html>