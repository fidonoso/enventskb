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
    console.log('event=>',eventID)
    console.log('userOwner=>',userID)
    console.log('userLoguedId=>',userLoguedID)

    const response = await fetch(`/eventos/${eventID}`, {
        method: 'Get',
        // headers: {
        //     'Content-Type': 'application/json',
        //     'Accept': 'application/json'
        // },
        // body: new URLSearchParams({ userId: userID })
        credentials: 'include',
        // body: JSON.stringify({ userId: userID })
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
        console.log('data==>',data);
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

const handleRequestParticipation=(e)=>{
    alert('Solicitar participar')
}