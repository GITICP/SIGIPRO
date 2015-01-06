set PGPASSWORD=Solaris2014
set PGUSER=postgres
@echo off
"C:\Program Files\PostgreSQL\9.3\bin\psql.exe" -h localhost -d sigipro -f Batch_fechas.sql
endlocal