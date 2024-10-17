import { Router } from "express";
import {sendEmail, requestPracticing} from '../controllers/sendMail.controller.js'
import {logger} from '../helpers/winston.js'

const router=Router();

router.post('/send-email', sendEmail);
router.post('/request-practicing', requestPracticing);
router.get('/ping', (req, res)=>{
  logger.info("pong de prueba", {
    logsource: 'node-app',
    service: 'ping',
  });
  return res.status(200).send('pong')
})

export default (users) => {
    users.use('/', router);
  };

  // pipeline.ecs_compatibility: disabled
  // log.level: debug

  // file {
  //   path => "/var/log/logstash/processed_messages.log"
  //   codec => line { format => "Processed Message: %{message}"}
  // }