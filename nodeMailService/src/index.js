import app from './app.js';
import config from './config.js';

// otras importaciones de arranque

import {sequelize} from './database/database.js';

async function main(){
 try {

    await sequelize.authenticate();
    console.log('sequelize: conexión con la base de datos establecida exitosamente.');
    await sequelize.sync({ force: false }); // Cambia a true si quieres que los modelos se sincronicen y se sobrescriban en cada inicio
    console.log('sequelize: Database synchronized successfully.');                    
     app.listen(config.PORT, ()=>{ console.log('Escuchando en el puerto ' + config.PORT)})
    } catch (error) {
      console.log('error en la conexion', error)
    }
  }
  main();


