import winston from 'winston';
import LogstashTransport from 'winston-logstash/lib/winston-logstash-latest';

const logstashOptions = {
  port: 5000,
  // host: 'http://localhost:5000',
  host: 'elk_stack_logstash',
  node_name: 'node-app',
  max_connect_retries: -1, // Intentos infinitos
  timeout_connect_retries: 5000, // 5 segundos entre intentos
  ssl_enable: false
};

export const logger = winston.createLogger({
  format: winston.format.combine(
    winston.format.timestamp(),
    winston.format.json()
  ),
  transports: [
    new LogstashTransport(logstashOptions)
  ]
});

// Manejo de errores en el transporte
logger.on('error', function(err) {
  console.error('Error en el transporte de Logstash:', err);
});

// Ejemplo de uso de logging
// logger.info('Aplicación Node.js iniciada', { logsource: 'node-app' });
// logger.error('Error al procesar una solicitud', { logsource: 'node-app' });