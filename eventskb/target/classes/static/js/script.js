let quill = new Quill('#editor-container', {
    theme: 'snow',
    modules: {
        toolbar: [
            [{ 'font': [] }, { 'size': [] }],
            ['bold', 'italic', 'underline', 'strike'],
            [{ 'align': [] }],
            [{ 'list': 'ordered'}, { 'list': 'bullet' }],
            [{ 'color': [] }, { 'background': [] }]
        ]
    }
});

    // Al enviar el formulario, poner el contenido HTML del editor en el campo oculto
//     document.getElementById('emailForm').onsubmit = function() {
//     document.getElementById('messageInput').value = quill.root.innerHTML;
// };

const createNewContact=document.getElementById('createNewContact')
createNewContact.addEventListener('click', async(e)=>{
    
    const name=document.getElementById('nameNewContact')
    const email=document.getElementById('emailNewContact')
    const description=document.getElementById('descriptionNewContact')
    const userId=document.getElementById('userIdNewContact')

    
    
    if(name.value.trim() !='' && email.value.trim() !=''){
        const payload={
            nombre: name.value.trim(),
            email:email.value.trim(),
            description:description.value.trim()
        }
        const URI=`/contacts/user/${userId.value}`

        try {
            const response=await fetch(URI, {
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
                const tbody=document.getElementById('listGuest')

                const tr=document.createElement('tr')
                const tdName=document.createElement('td')
                tdName.innerHTML=data.nombre
                tr.appendChild(tdName)

                const tdEmail=document.createElement('td')
                tdEmail.innerHTML=data.email
                tr.appendChild(tdEmail)

                const tdInput=document.createElement('td')
                const span=document.createElement('span')
                span.classList.add('form-check-label')
                span.innerHTML="Enviar invitación"
                tdInput.appendChild(span)
                const input=document.createElement('input')
                input.classList.add('form-check-input','ms-2')
                input.setAttribute('type', 'checkbox')
                tdInput.appendChild(input)
                tr.appendChild(tdInput)

                const tdId=document.createElement('td')
                tdId.classList.add('d-none')
                tdId.innerHTML=data.id
                tr.appendChild(tdId)

                tbody.appendChild(tr)

                // console.log('data=>',data)
                alert('Contacto creado correctamente')
            }else{
                alert('Error al crear el contacto')
            }
        } catch (error) {
            
        }
       
    }else{
        alert('Complete todos los campos')
    }
})

const btnCheckAll=document.getElementById('checkAllFriends')
btnCheckAll.addEventListener('change', function() {
    const checkboxes = document.querySelectorAll('#listGuest input[type="checkbox"]');
    checkboxes.forEach(checkbox => {
        if(!checkbox.disabled){
            checkbox.checked = this.checked;
        }
    });
});

const offcanvasElement = document.getElementById('offcanvasSendMail');
const offcanvas = bootstrap.Offcanvas.getInstance(offcanvasElement) || new bootstrap.Offcanvas(offcanvasElement);

const btnSendGuest=document.getElementById('btnSendGuest')
btnSendGuest.addEventListener('click', async()=>{
    activeLoader()
    const selectedGuests = [];
    const rows = document.querySelectorAll('#listGuest tr');

    rows.forEach(row => {
        // Seleccionamos el checkbox de la tercera columna de cada fila
        const checkbox = row.querySelector('td:nth-child(3) input[type="checkbox"]');
        // Verificamos si está marcado
        if (checkbox && checkbox.checked && !checkbox.disabled) {
            // Capturamos el valor del input de tipo hidden de la última columna
            const hiddenInput = row.querySelector('td:nth-child(4)').textContent.trim();
            if (hiddenInput) {
                selectedGuests.push(hiddenInput); // Añadimos el valor al array
            }
        }
    });

    const messageInput=document.getElementById('messageInput')
    messageInput.value = quill.root.innerHTML;
    const userId=document.querySelector('#offcanvasSendMail table thead #idUserOwn').innerHTML
    const subject=document.querySelector('#offcanvasSendMail #subject')
    const eventId=document.querySelector('#offcanvasSendMail #enventid')

    if(userId != '' && eventId != '' && selectedGuests.length > 0 && subject.value.trim() != '') {
        const payload={
            sender:userId,
            eventId:eventId.textContent,
            recipients:selectedGuests,
            subject:subject.value.trim(),
            message: messageInput.value
        }

        // console.log('payload=>', payload)
        const response=await fetch('/api/emails/send', {
            method:'POST',
            headers: {
                'Content-Type': 'application/json',
                'Accept': 'application/json'
            },
            credentials: 'include',
            body:JSON.stringify(payload)
        })

        if(response.ok){
            const result=await response.json()
            // console.log(result)
            if(result.status=='ok'){
                Swal.fire({
                    title: "Buen trabajo!",
                    text: "Invitaciones enviadas correctamente!",
                    icon: "success"
                    });
    
            }else{
                Swal.fire({
                    icon: "error",
                    title: "Oops...",
                    text: "Algo salió mal!",
                    footer: '<a href="#">Quieres reportar el problema?</a>'
                    });
            }
                
        }else{
            Swal.fire({
                icon: "error",
                title: "Oops...",
                text: "Algo salió mal!",
                footer: '<a href="#">Quieres reportar el problema?</a>'
                });
        }

        offcanvas.hide();
    }else {
        Swal.fire({
            icon: "error",
            title: "Oops...",
            text: "Debes completar los campos 'Asunto' y/o Seleccionar contactos que no hayas invitado previamente a este evento",
            footer: '<a href="#">Why do I have this issue?</a>'
          });
    }


    desactiveLoader()
        
});

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
        button.disabled = false;  // Reactiva los botones
        button.classList.remove('disabled');  // Elimina la clase 'disabled'
    });

    // Habilitar todos los enlaces nuevamente
    document.querySelectorAll('a').forEach(link => {
        link.classList.remove('disabled');  // Elimina la clase 'disabled'
        link.style.pointerEvents = 'auto';  // Reactiva los clics
    });
};

document.addEventListener('DOMContentLoaded', ()=>{
    setTimeout(()=>{
        desactiveLoader()

    },1000)
})