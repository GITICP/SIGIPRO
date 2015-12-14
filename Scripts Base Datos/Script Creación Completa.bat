SET PGCLIENTENCODING=utf-8
chcp 1252

set PGPASSWORD=Solaris2014
set PGUSER=postgres

@echo on
for %%G in (Scripts/*.sql) do "C:\Program Files\PostgreSQL\9.4\bin\psql.exe" -h localhost -d sigipro -f "Scripts/%%G"
pause