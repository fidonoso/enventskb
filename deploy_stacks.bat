@echo off
REM Desplegar el stack db
echo "--------------------------------------------------------------------------"
echo "Desplegando stack jdbkb para postgres y pgadmin (docker-compose-db.yml)"
docker stack deploy -c docker-compose-db.yml dbkb
echo "__________________________________________________________________________"

echo "--------------------------------------------------------------------------"
REM Desplegar el stack ELK_stack
echo "Desplegando stack elk_stack para monitoreo de logs (docker-compose-elk.yml)"
docker stack deploy -c docker-compose-elk.yml elk_stack
echo "__________________________________________________________________________"

echo "--------------------------------------------------------------------------"
REM Desplegar el stack node_stack
echo "Desplegando stack node_stack para aplicacion Node js (docker-compose-node.yml)"
docker stack deploy -c docker-compose-node.yml node_stack
echo "__________________________________________________________________________"

echo "--------------------------------------------------------------------------"
REM Desplegar el stack java_stack
echo "Desplegando stack java_stack para la aplicacion JAVA (docker-compose-java.yml)"
docker stack deploy -c docker-compose-java.yml java_stack
echo "__________________________________________________________________________"


echo "__________________________________________________________________________"
echo "Stacks desplegados con exito"
echo "__________________________________________________________________________"
REM deploy_stacks.bat