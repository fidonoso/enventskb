package com.events.kibernum.dto;

import com.events.kibernum.model.Events;
import com.events.kibernum.model.Users;

public class EventUserDTO {
    private Events event;
    private Users user;

    public EventUserDTO(Events event, Users user) {
        this.event = event;
        this.user = user;
    }

    public Events getEvent() {
        return event;
    }

    public void setEvent(Events event) {
        this.event = event;
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }
}
