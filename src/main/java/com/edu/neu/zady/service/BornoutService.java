package com.edu.neu.zady.service;

import com.edu.neu.zady.pojo.Bornout;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public interface BornoutService {

    List<Bornout> selectBySprint(Integer sprintId);

    Integer addBornout(Integer sprintId, Date createDate);

}
