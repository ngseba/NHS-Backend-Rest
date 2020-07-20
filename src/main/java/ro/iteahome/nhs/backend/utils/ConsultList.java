package ro.iteahome.nhs.backend.utils;

import ro.iteahome.nhs.backend.model.nhs.dto.ConsultDTO;

import java.util.ArrayList;

public class ConsultList {

    private ArrayList<ConsultDTO> consultDTOList;

    public ConsultList() {
        consultDTOList = new ArrayList<>();
    }

    public ArrayList<ConsultDTO> getConsultDTOList() {
        return consultDTOList;
    }

    public void setConsultDTOList(ArrayList<ConsultDTO> consultDTOList) {
        this.consultDTOList = consultDTOList;
    }
}
