import winston from 'winston';
import LogstashTransport from 'winston-logstash/lib/winston-logstash-latest';

// Configuración de Winston
export const logger = winston.createLogger({
    transports: [
      new LogstashTransport({
        port: 5000,
        host: 'elk_stack_logstash',    // Nombre del contenedor de Logstash en la red
        node_name: 'node-app', // Identificador para los logs de esta aplicación
        max_connect_retries: 10,
      })
    ]
  });

  // Manejo de errores en el transporte
logger.on('error', function(err) {
    console.error('Error en el transporte de Logstash:', err);
  });

    // Ejemplo de uso de logging
logger.info('Aplicación Node.js iniciada');
logger.error('Error al procesar una solicitud');