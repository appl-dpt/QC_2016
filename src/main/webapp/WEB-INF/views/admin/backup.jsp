<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<div class="dump">
	<h3>Dump</h3>
	<a href="<c:url value='/admin/dump'/>" class="btn btn-primary">dump only</a>
    <a href="<c:url value='/admin/restore'/>" class="btn btn-primary">restore only</a>
    <a href="<c:url value='/admin/compress'/>" class="btn btn-primary">compress only</a>
    <a href="<c:url value='/admin/decompress'/>" class="btn btn-primary">decompress only</a>
    <a href="<c:url value='/admin/fullBackup'/>" class="btn btn-primary">full backup</a>
    <a href="<c:url value='/admin/fullRestore'/>" class="btn btn-primary">full restore</a>
</div>