import app from './app.js';
import config from './config.js';
import { logger } from './helpers/winston';

// otras importaciones de arranque

import {sequelize} from './database/database.js';

async function main(){
 try {

    await sequelize.authenticate();
    console.log('sequelize: conexión con la base de datos establecida exitosamente.');
    logger.info('Conexión con la base de datos establecida exitosamente.', {
      logsource: 'node-app',
      service: 'sequelize'
    });

    await sequelize.sync({ force: false }); // Cambia a true si quieres que los modelos se sincronicen y se sobrescriban en cada inicio
    console.log('sequelize: Database synchronized successfully.');        
    logger.info('sequelize: Database synchronized successfully.', {
      logsource: 'node-app',
      service: 'sequelize'
    });            
     app.listen(config.PORT, ()=>{ 
      console.log('Escuchando en el puerto ' + config.PORT)
      logger.info(`Escuchando en el puerto ' + ${config.PORT}`, {
        logsource: 'node-app',
        service: 'sequelize'
      });
    })
    } catch (error) {
      console.log('error en la conexion', error)
      logger.error("Error en la conexión", {
        logsource: 'node-app',
        service: 'sequelize',
        error: err.message
      });
    }
  }
  main();


