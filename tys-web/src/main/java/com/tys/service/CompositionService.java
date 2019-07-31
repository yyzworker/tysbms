package com.tys.service;

import com.tys.entity.es.Composition;
import com.tys.entity.vo.CompositionVo;
import com.tys.util.es.vo.Page;

public interface CompositionService {

    Page<Composition> search(CompositionVo composition);

    Composition save(CompositionVo composition);

    Composition update(CompositionVo composition);

    Composition queryById(Integer id);

    int delete(Integer id);
}
