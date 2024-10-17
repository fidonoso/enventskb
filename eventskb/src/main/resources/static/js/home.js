   const qtEvents=events.length
   const myEvents=events.filter(x=>x.user.id==user.id).length
   const qtContacts= contacts.length
   const countIamGuest = events.filter(event => 
    event.invitados.some(invitado => invitado.email === user.email)
  ).length;
  
  
const ctx = document.getElementById('myChart').getContext('2d');

 const graph1 = new Chart(ctx, {
    type: 'doughnut',
    data: {
        labels: ['Eventos Totales',  'Mis Eventos', 'He sido invitado'],
        datasets: [{
            label: 'n° Eventos',
            data: [qtEvents, myEvents, countIamGuest],
            borderWidth: 1
        }]
    },
    options: {
        responsive: true,
        maintainAspectRatio: false,  // Desactiva la relación de aspecto fija
        plugins: {
            legend: {
                position:'right',
                labels: {
                    color:'white',
                    font: {
                        family: 'Verdana', // Cambia la fuente de la leyenda
                        size: 18,        // Tamaño de la fuente de la leyenda
                        weight: '600'   // Peso de la fuente
                    }
                }
            },
            title: {
                display: true,
                text: `Estadísticas de ${user.name} ${user.last_name}`,
                color: 'white',
                font: {
                    family: 'Verdana', // Cambia la fuente del título
                    size: 30,           // Tamaño de la fuente del título
                    weight: '500'      // Peso de la fuente
                }
            }
        }
    }
});


// Agrupar los eventos por usuario y contar cuántos eventos tiene cada uno

const userEventCount = events.reduce((acc, event) => {
    const userName = `${event.user.name} ${event.user.last_name}`;
    acc[userName] = (acc[userName] || 0) + 1;
    return acc;
}, {});

// Obtener los nombres de los usuarios y la cantidad de eventos que han creado
const userNames = Object.keys(userEventCount);
const eventCounts = Object.values(userEventCount);

const userEventsChartctx = document.getElementById('userEventsChart').getContext('2d');

const userEventsChart = new Chart(userEventsChartctx, {
    type: 'bar',  // Tipo de gráfico de barras
    data: {
        labels: userNames,  // Nombres de los usuarios
        datasets: [{
            label: 'Cantidad de eventos',
            data: eventCounts,  // Cantidad de eventos por usuario
            backgroundColor: 'rgba(75, 192, 192, 0.5)',  // Color de fondo de las barras
            borderColor: 'rgba(75, 192, 192, 1)',        // Color de borde de las barras
            borderWidth: 1
        }]
    },
    options: {
        responsive: true,
        plugins: {
            legend: {
                display: false  // Ocultar leyenda
            },
            title: {
                display: true,
                text: 'Cantidad de Eventos por Usuario',
                color: 'white',
                font: {
                    family: 'Verdana',
                    size: 20,
                    weight: '300'
                }
            }
        },
        scales: {
            x: {
                ticks: {
                    color: 'white'  // Cambiar color de las etiquetas del eje X a blanco
                },
                grid: {
                    color: 'rgba(255, 255, 255, 0.2)'  // Cambiar color de las líneas de la cuadrícula del eje X
                }
            },
            y: {
                ticks: {
                    color: 'white',  // Cambiar color de las etiquetas del eje Y a blanco
                    stepSize: 1, // Gradúa en números enteros
                    precision: 0 // Sin decimales
                },
                grid: {
                    color: 'rgba(255, 255, 255, 0.2)'  // Cambiar color de las líneas de la cuadrícula del eje Y
                },
                beginAtZero: true  // Asegura que el eje Y comience en 0
            }
        }
    }
});

// Obtener los títulos de los eventos y contar cuántos invitados tiene cada uno
const eventTitles = events.map(event => event.title);
const guestCounts = events.map(event => event.invitados.length);

const guestCountChartctx = document.getElementById('guestCountChart').getContext('2d');

const guestCountChart = new Chart(guestCountChartctx, {
    type: 'line',  // Tipo de gráfico de barras
    data: {
        labels: eventTitles,  // Títulos de los eventos
        datasets: [{
            label: 'Cantidad de invitados',
            data: guestCounts,  // Cantidad de invitados por evento
            backgroundColor: 'rgba(153, 102, 255, 0.9)',  // Color de fondo de las barras
            borderColor: 'rgba(153, 102, 255, 1)',        // Color de borde de las barras
            borderWidth: 1
        }]
    },
    options: {
        responsive: true,
        plugins: {
            legend: {
                display: false  // Ocultar leyenda
            },
            title: {
                display: true,
                text: 'Cantidad de Invitados por Evento',
                color: 'white',
                font: {
                    family: 'Verdana',
                    size: 20,
                    weight: '300'
                }
            }
        },
        scales: {
            x: {
                ticks: {
                    color: 'white'  // Cambiar color de las etiquetas del eje X a blanco
                },
                grid: {
                    color: 'rgba(255, 255, 255, 0.2)'  // Cambiar color de las líneas de la cuadrícula del eje X
                }
            },
            y: {
                ticks: {
                    color: 'white',  // Cambiar color de las etiquetas del eje Y a blanco
                    stepSize: 1, // Gradúa en números enteros
                    precision: 0 // Sin decimales
                },
                grid: {
                    color: 'rgba(255, 255, 255, 0.2)'  // Cambiar color de las líneas de la cuadrícula del eje Y
                },
                beginAtZero: true  // Asegura que el eje Y comience en 0
            }
        }
    }
});


// LOADER
const activeLoader=()=>{
    const containerLoader=document.getElementById('containerLoader')
    containerLoader.classList.remove('d-none')
    containerLoader.classList.add('d-flex')
    // Deshabilitar todos los botones
    document.querySelectorAll('button').forEach(button => {
        button.disabled = true;  // Desactiva los botones
        button.classList.add('disabled');  // Aplica clase de Bootstrap para feedback visual
    });

    // Deshabilitar todos los enlaces
    document.querySelectorAll('a').forEach(link => {
        link.classList.add('disabled');  // Aplica clase de Bootstrap para desactivar
        link.style.pointerEvents = 'none';  // Evita que sean clicables
    });
};
const desactiveLoader=()=>{

    const containerLoader=document.getElementById('containerLoader')
    containerLoader.classList.remove('d-flex')
    containerLoader.classList.add('d-none')
    // Habilitar todos los botones nuevamente
    document.querySelectorAll('button').forEach(button => {
        button.disabled = false;  
        button.classList.remove('disabled');  
    });

    // Habilitar todos los enlaces nuevamente
    document.querySelectorAll('a').forEach(link => {
        link.classList.remove('disabled');  
        link.style.pointerEvents = 'auto';  // Reactiva los clics
    });
};

document.addEventListener('DOMContentLoaded', ()=>{   
        desactiveLoader()

})