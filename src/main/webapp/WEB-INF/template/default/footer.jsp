<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<div class="footer-placeholder"></div>
<footer class="bg-info">
    <div class="container">
        <div class="col-md-4">
            <h4>
                <spring:message code="footer.authors" />
                (Ch-036)
            </h4>
            <ul>
                <li>Bogdan Gats</li>
                <li>Olexandr Huldin</li>
                <li>Mykola Ilashchuk</li>
                <li>Serhiy Makhov</li>
                <li>Volodymyr Rogulya</li>
                <li>Bogdan Shtepuliak</li>
            </ul>
        </div>
        <div class="col-md-12 text-right">
            <h6>
                
                <spring:message code="footer.contactUs" />: 
                <a href="mailto:booking.it.university@gmail.com"><span class="glyphicon glyphicon-envelope"></span> booking.it.university@gmail.com</a>
            </h6>
            <h6>
                <spring:message code="footer.rightsReserved" /> &copy;
            </h6>
        </div>
    </div>
</footer>