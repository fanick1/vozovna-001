<%@ include file="/WEB-INF/jspf/taglibs.jspf"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><fmt:message key="vehicles.title" /></title>
</head>
<body>

    <h1>
        <fmt:message key="vehicles.title" />
    </h1>

    <br />Tady si bude uživatel rezervovat vozidla. Na stránce budou dvě pole, do kterých uživatel zadá datum, v jakém
    časovém rozmezí se mu mají vozidla vypsat. Tato pole by mohla být formou kalendáře, ale pro začátek nám bude stačit
    zadávat to textově. Pod tím tabulka s odpovídajícími vozidly (filtr na datum a na uživatelovu třídu). Třídu
    uživatele si můžem dát jako hidden atribut. (Lepší by bylo získávat to z přihlašovacích údají, ale na to ještě
    nejsme dost Spring Security mistři)








</body>
</html>