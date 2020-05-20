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
        <!-- Main content -->
        <section class="content">

            <div class="row">
                <div class="col-md-3">

                    <!-- Profile Image -->
                    <div class="box box-primary">
                        <div class="box-body box-profile">
                            <h3 class="profile-username text-center">Reservation n-${rent_number}</h3>

                            <ul class="list-group list-group-unbordered">
                                <li class="list-group-item">
                                    <b>Client de la reservation</b>
                                    <a class="pull-right">
                                      <c:forEach items = "${rents}" var = "rent">
                                        <c:if test="${rent.id == rent_number}">
                                          <c:forEach items = "${clients}" var = "client">
                                              <c:if test="${rent.client_id == client.id}">
                                                ${client.nom} ${client.prenom}
                                              </c:if>
                                            </c:forEach>
                                          </c:if>
                                      </c:forEach>
                                    </a>
                                </li>
                                <li class="list-group-item">
                                    <b>Voiture de la reservation</b>
                                    <a class="pull-right">
                                      <c:forEach items = "${rents}" var = "rent">
                                        <c:if test="${rent.id == rent_number}">
                                          <c:forEach items = "${vehicles}" var = "vehicle">
                                              <c:if test="${rent.vehicle_id == vehicle.id}">
                                                ${vehicle.constructeur} ${vehicle.modele}
                                              </c:if>
                                          </c:forEach>
                                        </c:if>
                                      </c:forEach>
                                    </a>
                                </li>
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
                            <li class="active"><a href="#rents" data-toggle="tab">Information - Client</a></li>
                            <li><a href="#cars" data-toggle="tab">Information - Voitures</a></li>
                        </ul>
                        <div class="tab-content">
                            <div class="active tab-pane" id="rents">
                                <div class="box-body no-padding">
                                    <table class="table table-striped">
                                        <tr>
                                            <th style="width: 10px">#</th>
                                            <th>Nom</th>
                                            <th>Prenom</th>
                                            <th>Mail</th>
                                            <th>Naissance</th>
                                        </tr>
                                        <c:forEach items = "${rents}" var = "rent">
                                          <c:if test="${rent.id == rent_number}">
                                            <c:forEach items = "${clients}" var = "client">
                                                <c:if test="${rent.client_id == client.id}">
                                                  <tr>
                                                    <td>${client.id}</td>
                                                    <td>${client.nom}</td>
                                                    <td>${client.prenom}</td>
                                                    <td>${client.email}</td>
                                                    <td>${client.naissance}</td>
                                                  </tr>
                                                </c:if>
                                            </c:forEach>
                                          </c:if>
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
                                            <th style=>Nombre de places</th>
                                        </tr>
                                        <c:forEach items = "${rents}" var = "rent">
                                          <c:if test="${rent.id == rent_number}">
                                            <c:forEach items = "${vehicles}" var = "vehicle">
                                                <c:if test="${rent.vehicle_id == vehicle.id}">
                                                  <tr>
                                                    <td>${vehicle.id}</td>
                                                    <td>${vehicle.constructeur}</td>
                                                    <td>${vehicle.modele}</td>
                                                    <td>${vehicle.nb_places}</td>
                                                  </tr>
                                                </c:if>
                                            </c:forEach>
                                          </c:if>
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
            <div >
              <a class="btn btn-danger pull-right" href="${pageContext.request.contextPath}/rents">
                  Retour
              </a>
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
