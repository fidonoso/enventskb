# Etapa 1: Construcción
FROM node:22-alpine3.19 AS builder

# Establecer el directorio de trabajo
WORKDIR /app

# Copiar los archivos necesarios para instalar dependencias
COPY package*.json ./

# Instalar solo las dependencias de producción
RUN npm install

# Copiar el resto del código de la aplicación
COPY . .

# Ejecutar el build de Babel para compilar el código
RUN npm run build


# Etapa 2: Producción
FROM node:22-alpine3.19

# Establecer el directorio de trabajo
WORKDIR /app

# Exponer el puerto definido en el archivo .env o por defecto 4001
ARG PORT=4001
ENV PORT=${PORT}
EXPOSE ${PORT}

# Copiar las dependencias instaladas y el código compilado desde la etapa anterior
COPY --from=builder /app/node_modules /app/node_modules
COPY --from=builder /app/dist /app/dist
COPY --from=builder /app/package*.json /app/



# Iniciar la aplicación desde el archivo compilado en dist
CMD ["node", "./dist/index.js"]

# crear la imagen  =>> docker build -t send-mail-kb .
# correr un contenedor =>> docker run --name send-mail --env-file ./src/.env -p 4001:$PORT send-mail-kb
# correr un contenedor =>> docker run --name send-mail -p 4001:4001 send-mail-kb

# CONSTRUIR LA IMAGEN POR SEPARADO PARA DOCKER STACK
# docker image rmi node-mail-service

# docker build -t node-mail-service:latest .