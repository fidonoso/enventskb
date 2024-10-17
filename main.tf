terraform {
  required_providers {
    docker = {
      source  = "kreuzwerker/docker"
      version = "~> 2.21.0"  # Puedes ajustar la versión según sea necesario
    }
  }
}

resource "docker_network" "app_network" {
  name = "app_network"
}

# # ELK Stack
# resource "docker_container" "elasticsearch" {
#   image = "elasticsearch:7.10.0"
#   name  = "elasticsearch"
#   networks = [docker_network.app_network.id]
#   ports {
#     internal = 9200
#     external = 9200
#   }
#   environment = {
#     "discovery.type" = "single-node"
#   }
# }

# resource "docker_container" "logstash" {
#   image = "logstash:7.10.0"
#   name  = "logstash"
#   networks = [docker_network.app_network.id]
#   ports {
#     internal = 5000
#     external = 5000
#   }
# }

# resource "docker_container" "kibana" {
#   image = "kibana:7.10.0"
#   name  = "kibana"
#   networks = [docker_network.app_network.id]
#   ports {
#     internal = 5601
#     external = 5601
#   }
# }

# # Jenkins
# resource "docker_container" "jenkins" {
#   image = "jenkins/jenkins:lts"
#   name  = "jenkins"
#   networks = [docker_network.app_network.id]
#   ports {
#     internal = 8080
#     external = 8080
#   }
# }

# # SonarQube
# resource "docker_container" "sonarqube" {
#   image = "sonarqube:latest"
#   name  = "sonarqube"
#   networks = [docker_network.app_network.id]
#   ports {
#     internal = 9000
#     external = 9000
#   }
# }

# Desplegar los servicios Java y Node.js con Docker Swarm
# resource "null_resource" "deploy_stacks" {
#   provisioner "local-exec" {
#     command = <<EOT
#       docker stack deploy -c docker-compose-db.yml dbkb &
#       docker stack deploy -c docker-compose-java.yml java_stack &
#       docker stack deploy -c docker-compose-node.yml node_stack &
#       docker stack deploy -c docker-compose-elk.yml elk_stack
#     EOT
#   }
# }


# Despliegue de la base de datos
resource "null_resource" "deploy_db_stack" {
  provisioner "local-exec" {
    command = "docker stack deploy -c docker-compose-db.yml dbkb"
  }
}

# Despliegue de la aplicación Node.js
resource "null_resource" "deploy_node_stack" {
  provisioner "local-exec" {
    command = "docker stack deploy -c docker-compose-node.yml node_stack"
  }
  depends_on = [null_resource.deploy_db_stack]  # Espera a que la app Java esté desplegada
}

# Despliegue de la aplicación Java
resource "null_resource" "deploy_java_stack" {
  provisioner "local-exec" {
    command = "docker stack deploy -c docker-compose-java.yml java_stack"
  }
  depends_on = [null_resource.deploy_node_stack]  # Espera a que la DB esté desplegada
}



# Despliegue del stack ELK
resource "null_resource" "deploy_elk_stack" {
  provisioner "local-exec" {
    command = "docker stack deploy -c docker-compose-elk.yml elk_stack"
  }
  depends_on = [null_resource.deploy_java_stack]  # Espera a que la app Node.js esté desplegada
}

#docker network rm app-network

## bajar los servicios desplegados en docker swarm
# docker stack rm dbkb java_stack node_stack elk_stack


# docker network create --driver overlay app-network        # crear la red para teraform 

# docker swarm init                  # Inicializar Docker Swarm
# terraform init                     # Inicializar Terraform
# terraform plan                     # Revisar el plan de infraestructura (opcional)
# terraform apply                    # Aplicar la configuración de infraestructura
# docker service ls                  # Verificar los servicios en Docker Swarm