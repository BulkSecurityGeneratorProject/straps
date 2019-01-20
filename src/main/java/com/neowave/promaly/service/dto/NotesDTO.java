package com.neowave.promaly.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Notes entity.
 */
public class NotesDTO implements Serializable {

    private Long id;

    private String note;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        NotesDTO notesDTO = (NotesDTO) o;
        if (notesDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), notesDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "NotesDTO{" +
            "id=" + getId() +
            ", note='" + getNote() + "'" +
            "}";
    }
}
