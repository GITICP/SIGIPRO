set PGPASSWORD=Solaris2014
set PGUSER=postgres
@echo off
"C:\Program Files\PostgreSQL\9.4\bin\psql.exe" -h localhost -d sigipro -f Batch_Fechas_Notificaciones_Ratonera.sql > Fechas_Notificaciones_Ratonera.log
endlocal