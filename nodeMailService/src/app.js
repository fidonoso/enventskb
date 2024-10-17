// importaciones

import express from 'express'
import morgan from 'morgan'
import cors from 'cors'
//enrutador
import indexRoute from './routes/index.js'
import {logger} from './helpers/winston.js'


// importaciones de session
// importaciones de passport

const app = express();
// configuracion app


// middlewares principales

app.use(cors())
app.use(express.urlencoded({ extended: false }));
app.use(morgan("dev"));
app.use(express.json())
app.use(indexRoute)


// middlewares secundarios
//session

//passport

// routes


// variables globales


// archivos estaticos
app.use(express.static(__dirname + '/public'));





// export app

export default app;


