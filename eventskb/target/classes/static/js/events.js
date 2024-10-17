
const nomalizedForm=()=>{
    var myModal = new bootstrap.Modal(document.getElementById('staticBackdrop'));

    const modal=document.getElementById('staticBackdrop')
    const title_modal=modal.querySelector('#staticBackdropLabel')
    const title_event=modal.querySelector('#title')
    const description=modal.querySelector('#description')
    const location=modal.querySelector('#location')
    const dateTime=modal.querySelector('#dateTime')
    const actionForm=modal.querySelector('form')

    title_modal.innerText='Nuevo Evento'
    title_event.value=''
    description.value=''
    location.value=''
    dateTime.value=new Date()
    actionForm.method='post'
    actionForm.action='/events/new'
}

const updateEvent=async (eventID, userID, userLoguedID)=>{
    // console.log('event=>',eventID)
    // console.log('userOwner=>',userID)
    // console.log('userLoguedId=>',userLoguedID)

    const response = await fetch(`/eventos/${eventID}`, {
        method: 'Get',
        credentials: 'include',
    });

    var myModal = new bootstrap.Modal(document.getElementById('staticBackdrop'));

    const modal=document.getElementById('staticBackdrop')
    const title_modal=modal.querySelector('#staticBackdropLabel')
    const title_event=modal.querySelector('#title')
    const description=modal.querySelector('#description')
    const location=modal.querySelector('#location')
    const dateTime=modal.querySelector('#dateTime')
    const actionForm=modal.querySelector('form')
    if (response.ok) {
        const data = await response.json();
        // console.log('data==>',data);
        title_modal.innerText='Editar Evento'
        title_event.value=data[0].title
        description.value=data[0].description
        location.value=data[0].location
        dateTime.value=data[0].dateTime
        actionForm.method='post';
        actionForm.action='/events/edit/' + eventID;

        const hiddenInput = document.createElement('input');
            hiddenInput.setAttribute('type', 'hidden');
            hiddenInput.setAttribute('name', '_method');
            hiddenInput.setAttribute('value', 'put');

// Añadir el input oculto al formulario
actionForm.appendChild(hiddenInput);

        const hiddenId=document.createElement('input')
            hiddenId.setAttribute('type', 'hidden')
            hiddenId.setAttribute('name', 'id')
            hiddenId.setAttribute('value', eventID)
            hiddenId.id="eventId"
            actionForm.appendChild(hiddenId)
        myModal.show()
    } else {
        title_modal.innerText='Nuevo Evento'
        title_event.value=''
        description.value=''
        location.value=''
        dateTime.value=''
        actionForm.method='post'

        alert('ups! Algo salió mal')
        console.error('Failed to fetch data');
    }
}

const deleteEvent=async(eventID, userID, userLoguedID)=>{

    const response = await fetch(`/eventos/${eventID}`, {
        method: 'delete',
        headers: {
            'Content-Type': 'application/json',
            'Accept': 'application/json'
        },
        credentials: 'include',
    });

    if(response.status == 200 && response.ok){
        return true
    }else{
        return false
    }
}
function handleEventClick(event) {
    // console.log(event.target)
    const eventId = event.currentTarget.getAttribute("data-event-id");
    const userId = event.currentTarget.getAttribute("data-user-id");
    const userLoguedId = event.currentTarget.getAttribute("data-user-logued_id");
    if(userId === userLoguedId){

        updateEvent(eventId, userId, userLoguedId);
    }else{
        alert('No eres el propietario del evento')
    }
}

const handleDeleteEventClick=(e)=>{
    const eventId = e.currentTarget.getAttribute("data-event-id");
    const userId = e.currentTarget.getAttribute("data-user-id");
    const userLoguedId = e.currentTarget.getAttribute("data-user-logued_id");
    if(userId === userLoguedId){

       const status= deleteEvent(eventId, userId, userLoguedId);
       if(status){
         alert('Evento eliminado correctamente');
        const event_card =e.target.closest('.event-card');
        event_card.remove();
       }
    }else{
        alert('No eres el propietario del evento')
    }
}

const handleSetOffCanvas=(e)=>{
    const eventId = e.currentTarget.getAttribute("data-event-id");
    const userId = e.currentTarget.getAttribute("data-user-id");
    const userLoguedId = e.currentTarget.getAttribute("data-user-logued_id")
    if(userId === userLoguedId){

        setOffCanvas(eventId, userId, userLoguedId);
    }else{
        alert('No eres el propietario del evento')
    }
}

const setOffCanvas=async(idEvent, idUser, udUserLoged)=>{
    const offCanvas=document.getElementById('offcanvasSendMail')
    const eventId=offCanvas.querySelector('#enventid')
    const response = await fetch(`/eventos/${idEvent}`, {
        method: 'Get',
        credentials: 'include',
    });
    if (response.ok) {
        const data=await response.json()
        const invitados=data[0]["invitados"]
        // console.log('dataEvents=>', data)
        // console.log('invitados=>', invitados)
        if(data.length >0){
            offCanvas.querySelector('#subject').value=`Invitación a evento - ${data[0].title}`;

            eventId.innerText=idEvent

            const dateEvent=converFechaHora(data[0].dateTime)
            const fecha=dateEvent.fecha
            const hora=dateEvent.hora

           const editor=offCanvas.querySelector('#editor-container')
           editor.querySelector('.ql-editor').innerHTML=`<p>Hola, soy ${data[0].user.name} ${data[0].user.last_name} y te quiero invitar a mi evento ${data[0].title}.</p><p><br></p><p>Fecha: <strong>${fecha}</strong></p><p>Hora: <strong>${hora}</strong></p><p>Lugar: <strong>${data[0].location}</strong></p><p>Descripción:</p><p>  _ ${data[0].description}</p><p><br></p><p>Espero contar con tu participación, va a ser genial tenerte por ahí.</p><p>¡Nos vemos!</p>
           `

           const rows = document.querySelectorAll('#listGuest tr');

           rows.forEach((row, index) => {
            // para ver si ya fue invitado el contacto.
            const checkbox = row.querySelector('td:nth-child(3) input[type="checkbox"]');
            const nombre = row.querySelector('td:nth-child(1)');
            const email = row.querySelector('td:nth-child(2)');

            const yainvitado=invitados.some(x=>x.nombre ==nombre.textContent && x.email== email.textContent)

            if(yainvitado){
                checkbox.checked = true;
                checkbox.disabled=true
                checkbox.closest('td').querySelector('span').innerHTML='Invitación enviada'
            }else{
                checkbox.checked = false;
                checkbox.disabled=false
            }
           
            })
        }
    }
};

const handleRequestParticipation=(e)=>{
    const eventId = e.currentTarget.getAttribute("data-event-id");
    const userId = e.currentTarget.getAttribute("data-user-id");
    const userLoguedId = e.currentTarget.getAttribute("data-user-logued_id")
    if(userId !== userLoguedId){

        requestPartipation(eventId, userId, userLoguedId);
    }else{
        alert('Eres el propietario de este evento')
    }
}

const requestPartipation=async(eventId, userId, userLoguedId)=>{

    const eventActual=events.find(x=>x.id==eventId) 
    const eventOwner=eventActual.user
    const dateEvent=converFechaHora(eventActual.dateTime)
    const fecha=dateEvent.fecha
    const hora=dateEvent.hora
    try {
        Swal.fire({
            title: `¿ Quieres solicitar participar en el evento '${eventActual.title}' de ${eventActual.user.name} ${eventActual.user.last_name} ?`,
            showDenyButton: true,
            showCancelButton: true,
            confirmButtonText: "Si, quiero participar",
            denyButtonText: `Mejor no!`
        }).then(async(result) => {
            /* Read more about isConfirmed, isDenied below */
            const tpl=`<p>Hola, soy ${user.name} ${user.last_name} y me gustaría participar en tu evento '${eventActual.title}'</p><p><br></p><p>Fecha: <strong>${fecha}</strong></p><p>Hora: <strong>${hora}</strong></p><p>Lugar: <strong>${eventActual.location}</strong></p><p>Descripción:</p><p>  _ ${eventActual.description}</p><p><br></p><p>Si estás de acuerdo, por fa envíame una invitación a ${user.email}</p><p><a href="${window.location.href}" target="_blank">Link a la app</a></p><p>¡Gracias!</p><p></p>`

            // const link=`<a href="${window.location.href}" target="_blank">Link a la app</a>`
            if (result.isConfirmed) {
                const payload={
                    sender:user.email,
                    eventId:eventId,
                    recipients:[eventOwner.email],
                    subject:`Hola, ${user.name} ${user.last_name} ha solicitado particiar en tu evento ${eventActual.title}`,
                    message: tpl
                }
                const response= await fetch(`/api/emails/request-practicing`, {
                    method:'POST',
                    headers: {
                        'Content-Type': 'application/json',
                        'Accept': 'application/json'
                    },
                    credentials: 'include',
                    body:JSON.stringify(payload)
                })
                if(response.ok){
                    const data=await response.json()
                    if(data.status == 'ok'){

                        Swal.fire("Solicitud enviada!", `Hemos enviado un correo a ${eventActual.user.name} ${eventActual.user.last_name} con tu nombre y correo para que te envíe una invitación`, "success");
                    }else{
                        Swal.fire({
                            icon: "error",
                            title: "Oops...",
                            text: "Algo salió mal.",
                            footer: '<a href="#">Quieres reportar un problema?</a>'
                          });
                    }

                }           
                
                } else if (result.isDenied) {
                    Swal.fire("Ok, no hay problema", "", "info");
                }
        });
        
    } catch (error) {
        console.log(error)
        Swal.fire({
            icon: "error",
            title: "Oops...",
            text: "Algo salió mal.",
            footer: '<a href="#">Quieres reportar un problema?</a>'
          });
    }
   
}

const converFechaHora=(timeDate)=>{
    const dateObject = new Date(timeDate);
    const fecha = dateObject.toLocaleDateString('es-ES')
    const hora = dateObject.toLocaleTimeString('es-ES', { hour: '2-digit', minute: '2-digit' });
    return{
        fecha,
        hora
    }
}

const eventDetails=async(e)=>{
    const card=e.target.closest('.card')
    const btnsContainer = card.querySelector('.container-btn-actions');
    const button = btnsContainer.querySelector('button[data-event-id]');

    if (button) {
        const eventId = button.getAttribute('data-event-id'); 
        const evento=events.find(x=>x.id==eventId)
        const dateEvent=converFechaHora(evento.dateTime)
        const fecha=dateEvent.fecha
        const hora=dateEvent.hora
        Swal.fire({
            title: `<strong><u>${evento.title}</u></strong>`,
            icon: "info",
            html: `
            <div class="border border-primary ms-0 p-3 class="event-detail-swal">
                <ul>
                    <li class="text-start">Creador del evento: <strong>${evento.user.name} ${evento.user.last_name}</strong></li>
                    <li class="text-start">Fecha de realización: <strong>${fecha}, comenzando a las ${hora}</strong></li>
                    <li class="text-start">Lugar: <strong>${evento.location}</strong></li>
                    <li class="text-start"><strong>Descripción del evento:</strong></li>
                </ul>
                <div class="ms-3">
                    <p class="text-start ms-5">${evento.description}</p>
                </div>

            </div>
              
            `,
            showCloseButton: true,
            showCancelButton: false,
            focusConfirm: false,
            confirmButtonText: `
              <i class="fa fa-thumbs-up"></i> Lo tengo!
            `,
            confirmButtonAriaLabel: "Thumbs up, great!",
            cancelButtonText: `
              <i class="fa fa-thumbs-down"></i>
            `,
            cancelButtonAriaLabel: "Thumbs down"
          });
    } else {
        console.log('No se encontró ningún botón con el atributo data-event-id');
    }
}