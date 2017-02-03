@echo off
@echo STARTUP App
@echo ���û�������,ѭ����ǰĿ¼�µ�libĿ¼������jar�ļ�,������CLASSPATH
rem create by glenn pan

cd ..

set APP_HOME=%cd%

set INSTANCE_DIR=instance%1%

md %APP_HOME%\%INSTANCE_DIR%\logs
md %APP_HOME%\%INSTANCE_DIR%\temp
md %APP_HOME%\%INSTANCE_DIR%\conf

rem mkdir -p $EXEC_LOG_DIR
rem mkdir -p $EXEC_TMP_DIR
rem mkdir -p $EXEC_CONFCENTER_DIR

for %%c in (%APP_HOME%\lib\*.jar) do call :addcp %%c
goto extlibe
:addcp
set CLASSPATH=%CLASSPATH%;%1
goto :eof
:extlibe

cd bin

@echo ��Ҫ���е�jar���õ�CLASSPATH��
set CLASSPATH=%CLASSPATH%;%APP_HOME%\conf
@echo CLASSPATH:
@echo %CLASSPATH%
@echo ����Ӧ�ó���
start javaw -classpath %CLASSPATH% pan.glenn.codegen.App
rem call java -classpath %CLASSPATH%
exit