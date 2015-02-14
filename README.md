#Proyecto SIGIPRO

Sistema para el manejo de:

* Bodegas
* Producción
* Seguridad
* Serpentario
* Bioterio
* Caballeriza
* Ventas
* Control de Calidad

##Realizado por:

* Esteban Aguilar Valverde <a href="https://github.com/Estebav8">estebav8</a>
* Jose Daniel Chacón Bogarín <a href="https://github.com/jchaconbogarin">jchaconbogarin</a>
* Walter Cordero Ureña <a href="https://github.com/waltercoru">waltercoru</a>
* Amed Espinoza Castro <a href="https://github.com/ametico">ametico</a>
* Daniel Jara Jiménez <a href="https://github.com/DNJJ1">DNJJ1</a>
* Isaac López Delgado <a href="https://github.com/isaaclpez">isaaclpez</a>


#Instalación (para Desarrollo)

##PREREQUISITOS

-JDK instalado

###NetBeans 8: 

https://netbeans.org/downloads/

Descargar el Java EE o el ALL.

A la hora de la instalación, asegurarse de descargar el server APACHE TOMCAT (el Glassfish es el default, así que hay que meterse a Customize en las pantallas de 
instalación para seleccionar Apache).

###Apache Tomcat

Incluido en la descarga de NetBeans. 

En caso de error a la hora de intentar iniciar el servidor, ver el siguiente video:

https://www.youtube.com/watch?v=guslgqUMe2A

###PostgreSQL

http://www.postgresql.org/download/

Descargar el servidor de la BD. Poner de usuario "postgre" y contraseña "Solaris2014". No modificar el puerto (5432). 

Para abrir la UI del servicio, abrir pgAdmin III. Crear una nueva "Database" llamada "sigipro". Luego, en dicho esquema, correr el código que se encuentra en
Scripts Base Datos/ScriptBD - Creacion de Esquemas y Tablas. 

##Despliegue

Ir a NetBeans, abrir el proyecto SIGIPRO, y darle Run. Revisar la Consola para ver cual puerto está asignado (8084). Abrirlo con el localhost.
