@echo off
@echo STARTUP App
@echo 设置环境变量,循环当前目录下的lib目录下所有jar文件,并设置CLASSPATH
rem create by myron pan

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

@echo 当要运行的jar设置到CLASSPATH中
set CLASSPATH=%CLASSPATH%;%APP_HOME%\conf
@echo CLASSPATH:
@echo %CLASSPATH%
@echo 运行应用程序
java -classpath %CLASSPATH% com.bluding.codegen.App
rem call java -classpath %CLASSPATH%
cd bin
pause