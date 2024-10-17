
  //import { fileURLToPath } from 'url';
  import { dirname, join } from 'path';
  
  //const __filename = fileURLToPath(import.meta.url);
  //const __dirname = dirname(__filename);
  //import { join } from 'node:path';
  
  import fs from "node:fs";
  import { Router } from "express";
  
  const router = Router();
  
  
  
  const eliminarExtension = (fileName)=> {
    return fileName.split(".").shift() || "";
  };
  
  
  const routesDinamicos = async () => {
    // const rutaCarpeta = join(__dirname, 'routes');
    const rutaCarpeta = __dirname;
    const archivos = fs
      .readdirSync(rutaCarpeta)
      .filter((file) => file.endsWith(".js") && file !== "index.js");
  
    archivos.forEach(async (file) => {
      //const rutaArchivo = join(rutaCarpeta, file);
      
      const modulo = await import('./'+file);
  
      console.log("RUTA==>", "/"+file.replace(/^routes\.|\.js$/g, ''));
  
      if (typeof modulo === "function") {
        modulo.default(router);
      } else if (modulo.default && typeof modulo.default === "function") {
        modulo.default(router);
      }
    });
  };
  
  routesDinamicos();
  
  export default router;
  
  /* 
USO
Si va a crear otro enrutador, por ejemplo para usuarios, el nombre del archivo debe ser con sufijo 'routes' (routes.users.js) en esta carpeta 'routes'

en cualquier otro enrutador, por ejemplo routes.users.js, la exportacion del router de ese archivo debe ser como:

`
  import { Router } from "express";

  const router=Router();

  router.get('/hola', (req, res)=>{
      res.send('esta es la ruta hola')
  });

  export default (users) => {
      users.use('/users', router);
    };
`

esto hablitaria la ruta localhost:<puerto>/users/hola


*/
