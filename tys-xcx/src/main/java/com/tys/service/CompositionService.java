package com.tys.service;

import com.tys.entity.es.Composition;
import com.tys.util.es.vo.Page;

public interface CompositionService {

    Page<Composition> search(String name, int pageNumber);

}

