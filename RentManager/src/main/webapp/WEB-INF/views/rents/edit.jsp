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
                <c:if test="${not empty errorMessage}">
                <a class="btn btn-danger" href="${pageContext.request.contextPath}/rents">
                    Retour
                </a>
              </c:if>
            </h1>
        </section>

        <!-- Main content -->
        <section class="content">
            <div class="row">
                <div class="col-md-12">
                    <!-- Horizontal Form -->
                    <div class="box">
                        <!-- form start -->
                        <form class="form-horizontal" method="post" action="${pageContext.request.contextPath}/rents/edit">
                            <div class="box-body">
                                <div class="form-group">
                                    <label for="vehicle_id" class="col-sm-2 control-label">Voiture</label>

                                    <div class="col-sm-10">
                                        <select class="form-control" id="vehicle_id" name="vehicle_id">
                                          <c:forEach items = "${rents}" var = "rent">
                                            <c:if test="${rent.id == reservationid}">
                                              <c:forEach items = "${vehicles}" var = "vehicle" varStatus = "status">
                                                <c:if test="${rent.id == reservationid && rent.vehicle_id == vehicle.id}">
                                                  <option value="${vehicle.id}" selected>${vehicle.id} - ${vehicle.constructeur} ${vehicle.modele}</option>
                                                </c:if>
                                                <option value="${vehicle.id}">${vehicle.id} - ${vehicle.constructeur} ${vehicle.modele}</option>
                                              </c:forEach>
                                            </c:if>
                                          </c:forEach>
                                        </select>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label for="client_id" class="col-sm-2 control-label">Client</label>

                                    <div class="col-sm-10">
                                        <select class="form-control" id="client_id" name="client_id">
                                          <c:forEach items = "${rents}" var = "rent">
                                            <c:if test="${rent.id == reservationid}">
                                              <c:forEach items = "${users}" var = "user" varStatus = "status">
                                                <c:if test="${rent.id == reservationid && rent.client_id == user.id}">
                                                  <option value="${user.id}" selected>${user.id} - ${user.nom} ${user.prenom}</option>
                                                </c:if>
                                                <option value="${user.id}">${user.id} - ${user.nom} ${user.prenom}</option>
                                              </c:forEach>
                                            </c:if>
                                          </c:forEach>
                                        </select>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label for="debut" class="col-sm-2 control-label">Date de debut</label>

                                    <div class="col-sm-10">
                                      <c:forEach items = "${rents}" var = "rent">
                                        <c:if test="${rent.id == reservationid}">
                                        <input type="date" class="form-control" id="debut" name="debut" value="${rent.debut}" required>
                                        </c:if>
                                      </c:forEach>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label for="fin" class="col-sm-2 control-label">Date de fin</label>

                                    <div class="col-sm-10">
                                      <c:forEach items = "${rents}" var = "rent">
                                        <c:if test="${rent.id == reservationid}">
                                        <input type="date" class="form-control" id="fin" name="fin" value="${rent.fin}" required>
                                        </c:if>
                                      </c:forEach>
                                    </div>
                                </div>
                                <div class="form-group">
                                  <div class="col-sm-10">
                                     <input type="hidden" class="form-control" id="idreservation" name="idreservation" value="${reservationid}">
                                  </div>
                                </div>
                                <div>
                          			${errorMessage}
                          		</div>
                            </div>
                            <!-- /.box-body -->
                            <div class="box-footer">
                                <button type="submit" class="btn btn-info pull-right">Modifier</button>
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

    <%@ include file="/WEB-INF/views/common/footer.jsp" %>
</div>
<!-- ./wrapper -->

<%@ include file="/WEB-INF/views/common/js_imports.jsp" %>
<script src="${pageContext.request.contextPath}/resources/plugins/input-mask/jquery.inputmask.js"></script>
<script src="${pageContext.request.contextPath}/resources/plugins/input-mask/jquery.inputmask.date.extensions.js"></script>
<script src="${pageContext.request.contextPath}/resources/plugins/input-mask/jquery.inputmask.extensions.js"></script>
<script>
    $(function () {
        $('[data-mask]').inputmask()
    });
</script>
</body>
</html>
