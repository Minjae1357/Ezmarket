package com.ez.market.service;


import com.ez.market.dto.Coments;
import com.ez.market.repository.ComentsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ComentsService {
    @Autowired
    ComentsRepository comentRepo;

    public List<Coments> findByPnum(int pnum){
        return comentRepo.findByPnum(pnum);
    }

    public boolean saveComents(Coments coment){
        java.util.Date currentDate = new java.util.Date();
        java.sql.Date sqlDate = new java.sql.Date(currentDate.getTime());
        coment.setConDate(sqlDate);
        return comentRepo.save(coment)!=null;
    }
}
