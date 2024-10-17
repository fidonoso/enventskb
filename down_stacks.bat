@echo off
REM Bajar todos los stacks
echo "Bajando todos los stacks.."
docker stack rm dbkb java_stack node_stack elk_stack

echo "Stacks eliminados con exito"