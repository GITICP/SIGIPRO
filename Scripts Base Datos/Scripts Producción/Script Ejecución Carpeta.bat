REM Este archivo se coloca en la carpeta en donde se deseen ejecutar todos los scripts de la misma

SET PGCLIENTENCODING=utf-8
chcp 1252
set PGPASSWORD=Solaris2014
set PGUSER=postgres
@echo on
for %%G in (*.sql) do "C:\Program Files\PostgreSQL\9.4\bin\psql.exe" -h localhost -d sigipro -f "%%G"
pause