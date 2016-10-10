package com.smetnerassosiates.repositories;

import com.smetnerassosiates.domain.Theme;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface ThemeRepository extends PagingAndSortingRepository<Theme, Long>{


}
